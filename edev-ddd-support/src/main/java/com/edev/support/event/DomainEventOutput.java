package com.edev.support.event;

import org.springframework.messaging.MessageChannel;

public interface DomainEventOutput {
    MessageChannel output();
}
