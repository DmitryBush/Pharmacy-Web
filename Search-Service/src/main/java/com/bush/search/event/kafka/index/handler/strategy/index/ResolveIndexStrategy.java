package com.bush.search.event.kafka.index.handler.strategy.index;

import com.bush.search.domain.index.CrudOperationConstants;

/**
 * Strategy interface used to processing target object with different types
 * <p>Example: different handlers for different types of messages in the outbox service</p>
 */
public interface ResolveIndexStrategy {
    /**
     * Method checks whether the strategy is applicable to target object with the specified scope
     * @param objectName Target object scope
     * @return True if strategy is applicable to target object, otherwise false
     */
    boolean isProcessingSupported(String objectName);

    /**
     * Method executes target object preprocessing before indexing
     * @param jsonPayload Raw target object
     * @param crudOperationConstantsType Type of CDC operation that occurred
     */
    void indexObject(String jsonPayload, CrudOperationConstants crudOperationConstantsType);
}
