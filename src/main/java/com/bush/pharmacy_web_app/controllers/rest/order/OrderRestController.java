package com.bush.pharmacy_web_app.controllers.rest.order;

import com.bush.pharmacy_web_app.model.dto.orders.OrderCreateDto;
import com.bush.pharmacy_web_app.model.dto.orders.OrderReadDto;
import com.bush.pharmacy_web_app.model.dto.orders.OrderStateChangeDto;
import com.bush.pharmacy_web_app.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<OrderReadDto>> getAllCustomerOrders(@AuthenticationPrincipal UserDetails userDetails,
                                                                      @PageableDefault(size = 15, sort = "id") Pageable pageable,
                                                                      PagedResourcesAssembler<OrderReadDto> assembler) {
        return assembler.toModel(orderService.findAllUserOrders(userDetails.getUsername(), pageable));
    }

    @GetMapping(value = "/me/{orderUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderReadDto> getCustomerOrder(@PathVariable UUID orderUuid) {
        return ResponseEntity.ok(orderService.findUserOrderById(orderUuid));
    }

    @PostMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderReadDto> createOrder(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody @Validated OrderCreateDto createDto) {
        return ResponseEntity.ok(orderService.createOrder(createDto, userDetails.getUsername()));
    }

    @PatchMapping(value = "/me/{orderUuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeOrderStatus(@PathVariable UUID orderUuid,
                                                  @AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestBody @Validated OrderStateChangeDto stateChangeDto) {
        if (orderService.changeOrderStatusByEvent(orderUuid, userDetails.getUsername(), stateChangeDto.event())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
