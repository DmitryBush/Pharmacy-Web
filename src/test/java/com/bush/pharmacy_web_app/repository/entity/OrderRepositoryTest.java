package com.bush.pharmacy_web_app.repository.entity;

import com.bush.pharmacy_web_app.repository.UserRepository;
import com.bush.pharmacy_web_app.repository.OrderRepository;
import com.bush.pharmacy_web_app.repository.PharmacyBranchRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;

// Test temporarily disabled for CI due to local database
@Tag("DatabaseRequired")
@SpringBootTest
@Transactional
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PharmacyBranchRepository pharmacyBranchRepository;
    @Autowired
    private UserRepository userRepository;

    private final Pageable page = PageRequest.of(0, 2);

    @Test
    public void findOrderById() {
        var page = PageRequest.of(0, 2);
        var orders = orderRepository.findAll(page);
        orders.stream().forEach(System.out::println);

        Assertions.assertNotNull(orders);
    }
    @Test
    public void findCustomerOrdersByPhone() {
        var orders = orderRepository.findByUserMobilePhone("+79162345678", page);
        orders.stream()
                .flatMap(lamb -> lamb.getCartItems().stream())
                .forEach(System.out::println);

        Assertions.assertNotNull(orders);
    }
    @Test
    public void createOrder() {
        var pharmacyBranch = pharmacyBranchRepository.findById(1).orElseThrow();
        var customer = userRepository.findById("+79192345678").orElseThrow();
        Order order = Order.builder()
                .statusOrder((short) 1)
                .date(Instant.now())
                .branch(pharmacyBranch)
                .user(customer)
                .build();
        Assertions.assertEquals(orderRepository.save(order), order);
    }
    @Test
    public void updateOrder() {
        var order = orderRepository.findById(5L).orElseThrow();

        order.setStatusOrder((short) 0);
        Assertions.assertEquals(order, orderRepository.save(order));
    }
}
