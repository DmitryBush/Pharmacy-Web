package com.bush.pharmacy_web_app.controllers.http.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class CustomerOrderController {

    @GetMapping("/place")
    public String placeOrder(Model model) {
        return "order/placing-order";
    }
}
