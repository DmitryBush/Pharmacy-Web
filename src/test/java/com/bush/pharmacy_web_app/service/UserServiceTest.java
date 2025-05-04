package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.dto.orders.CustomerCreateDto;
import com.bush.pharmacy_web_app.repository.dto.orders.CustomerReadDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Tag("DatabaseRequired")
@SpringBootTest
@Rollback
public class UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    public void findAllCustomers() {
        var customers = service.findAll();

        Assertions.assertNotNull(customers);
        Assertions.assertFalse(customers.contains(null));
    }
    @Test
    public void findCustomerById() {
        var customer = service.findById("+79123456789");

        Assertions.assertNotNull(customer);
    }
    @Test
    public void createCustomer() {
        String id = "+796010712089";
        var expectedDto = new CustomerReadDto("Камилла", "Казакова",
                "Егоровна", id, Collections.emptyList());
        var readDto = service.create(new CustomerCreateDto("Камилла", "Казакова",
                "Егоровна", id, id));
        Assertions.assertEquals(expectedDto, readDto);
    }
    @Test
    public void updateCustomer() {
        String id = "+796010712089";
        service.create(new CustomerCreateDto("Камилла","Казакова", "Егоровна", id, id));

        var expected = new CustomerReadDto(id,"Камилла","Казакова", "Викторовна",
                Collections.emptyList());
        var actual = service.update(id,
                new CustomerCreateDto("Камилла","Казакова", "Викторовна", id, id));

        actual.ifPresent(lamb -> Assertions.assertEquals(expected, actual.orElseThrow()));
    }
    @Test
    @Transactional
    public void deleteCustomer() {
        String id = "+796010712089";
        service.create(new CustomerCreateDto("Камилла","Казакова", "Егоровна", id, id));
        Assertions.assertTrue(service.delete(id));
    }
}
