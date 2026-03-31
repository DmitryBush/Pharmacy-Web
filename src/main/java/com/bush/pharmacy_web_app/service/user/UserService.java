package com.bush.pharmacy_web_app.service.user;

import com.bush.pharmacy_web_app.model.dto.user.AdminUserReadDto;
import com.bush.pharmacy_web_app.model.entity.user.role.Role;
import com.bush.pharmacy_web_app.model.entity.user.role.RoleType;
import com.bush.pharmacy_web_app.repository.user.UserFilter;
import com.bush.pharmacy_web_app.repository.user.UserRepository;
import com.bush.pharmacy_web_app.model.dto.user.CustomerCreateDto;
import com.bush.pharmacy_web_app.model.dto.user.CustomerReadDto;
import com.bush.pharmacy_web_app.service.user.mapper.AdminUserReadMapper;
import com.bush.pharmacy_web_app.service.user.mapper.UserCreateMapper;
import com.bush.pharmacy_web_app.service.user.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final RoleService roleService;

    private final AdminUserReadMapper adminUserReadMapper;
    private final UserReadMapper readMapper;
    private final UserCreateMapper createMapper;

    public List<CustomerReadDto> findAll() {
        return userRepository.findAll().stream().map(readMapper::map).toList();
    }

    public Page<AdminUserReadDto> findAllByFilter(Pageable pageable, UserFilter filter) {
        List<RoleType> roleType =  filter.role().stream()
                .filter(s -> !s.isBlank())
                .map(RoleType::valueOf)
                .toList();
        return userRepository.findAllByMobilePhoneAndRole(filter.mobilePhone(), roleType, pageable)
                .map(adminUserReadMapper::map);
    }

    public Optional<CustomerReadDto> findById(String s) {
        return userRepository.findById(s)
                .map(readMapper::map);
    }

    @Transactional
    public CustomerReadDto create(CustomerCreateDto customerCreateDto) {
        return Optional.ofNullable(customerCreateDto)
                .map(createMapper::map)
                .map(userRepository::save)
                .map(readMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CustomerReadDto> update(String s, CustomerCreateDto customerCreateDto) {
        return userRepository.findById(s)
                .map(lamb -> createMapper.map(customerCreateDto, lamb))
                .map(userRepository::saveAndFlush)
                .map(readMapper::map);
    }

    @Transactional
    public boolean delete(String s) {
        return userRepository.findById(s)
                .map(lamb -> {
                    userRepository.delete(lamb);
                    userRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .map(customer -> new User(customer.getMobilePhone(),
                        customer.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + customer.getRole().getType().name()))))
                .orElseThrow(() -> new UsernameNotFoundException("Mistake in username or password"));
    }

    @Transactional
    public AdminUserReadDto updateRole(String mobilePhone, String roleName) {
        Role role = roleService.findRoleByRoleType(RoleType.valueOf(roleName));
        return userRepository.findById(mobilePhone)
                .map(user -> createMapper.updateRole(user, role))
                .map(userRepository::saveAndFlush)
                .map(adminUserReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}
