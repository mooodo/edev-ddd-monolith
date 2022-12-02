package com.edev.support.event;

public interface DomainEventReceiver {
    void apply(DomainEventObject event);
}
