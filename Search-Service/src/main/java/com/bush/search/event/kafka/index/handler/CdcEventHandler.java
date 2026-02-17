package com.bush.search.event.kafka.index.handler;

import com.bush.search.domain.index.CdcEvent;

public interface CdcEventHandler<T> {
    void handle(CdcEvent<T> event);
}
