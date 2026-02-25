package com.bush.search.event.kafka.index.handler.strategy.crud;

import com.bush.search.domain.index.CrudOperationConstants;

/**
 * CRUD operation processing strategy for the target object
 */
interface CrudOperationStrategy {
    /**
     * Method checks whether the strategy is applicable to target object with the required operation type
     * @param crudOperationConstantsType Required operation type
     * @param requiredService Required service for processing
     * @param requiredPayloadClazz Target object type
     * @return true if strategy is applicable to target object, otherwise false
     */
    boolean isProcessingSupport(CrudOperationConstants crudOperationConstantsType, Class<?> requiredService,
                                Class<?> requiredPayloadClazz);

    /**
     * Method processes target object with the required operation type
     * @param payload Target Object
     */
    void process(Object payload);
}
