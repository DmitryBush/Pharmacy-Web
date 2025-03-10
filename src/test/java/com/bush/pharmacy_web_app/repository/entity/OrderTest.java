package com.bush.pharmacy_web_app.repository.entity;

import com.bush.pharmacy_web_app.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;


@SpringBootTest
@Transactional
public class OrderTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findOrderById() {
        var page = PageRequest.of(1, 2);
        var orders = orderRepository.findAll(page);
        orders.stream().forEach(System.out::println);

        Assertions.assertNotNull(orders);
    }
    @Test
    public void findCustomerOrdersByPhone() {
        var page = PageRequest.of(1, 2);
        var orders = orderRepository.findByCustomerMobilePhone("+79123456789", page);
        orders.stream()
                .flatMap(lamb -> lamb.getCartItems().stream())
                .forEach(System.out::println);

        Assertions.assertNotNull(orders);
    }
}
