package com.bush.pharmacy_web_app.repository.mapper.admin.handler.factory;

import com.bush.pharmacy_web_app.repository.mapper.admin.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FactoryOpeningHoursHandler {
    @Bean
    public AbstractOpeningHoursHandler getOpeningHoursChain() {
        AbstractOpeningHoursHandler chainBeginning = new MondayOpeningHoursHandler();

        var chain = chainBeginning.setNext(new TuesdayOpeningHoursHandler());
        chain = chain.setNext(new WednesdayOpeningHoursHandler());
        chain = chain.setNext(new ThursdayOpeningHoursHandler());
        chain = chain.setNext(new FridayOpeningHoursHandler());
        chain = chain.setNext(new SaturdayOpeningHoursHandler());
        chain.setNext(new SundayOpeningHoursHandler());

        return chainBeginning;
    }
}
