package com.bush.pharmacy_web_app.repository.mapper.orders.handler.factory;

import com.bush.pharmacy_web_app.repository.mapper.orders.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FactoryStatusHandler {
    @Bean
    public StatusHandler getStatusChain() {
        StatusHandler chainBeginning = new PaymentAwaitStatusHandler();

        var chain = chainBeginning.setNext(new CanceledStatusHandler());
        chain = chain.setNext(new DeferredStatusHandler());
        chain = chain.setNext(new DecoratedStatusHandler());
        chain = chain.setNext(new AssemblyStatusHandler());
        chain = chain.setNext(new TransitStatusHandler());
        chain = chain.setNext(new DeliveredStatusHandler());
        chain = chain.setNext(new CompletedStatusHandler());
        chain = chain.setNext(new NotDemandStatusHandler());
        chain.setNext(new ReturnStatusHandler());

        return chainBeginning;
    }
}
