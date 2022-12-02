package com.edev.support.event;

import org.springframework.messaging.SubscribableChannel;

public interface DomainEventInput {
    SubscribableChannel input();
}
