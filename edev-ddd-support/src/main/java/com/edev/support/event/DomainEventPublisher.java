package com.edev.support.event;

import com.edev.support.utils.DateUtils;
import org.springframework.messaging.support.MessageBuilder;

public class DomainEventPublisher {
    private DomainEventOutput domainEventOutput;
    private String eventId;
    public DomainEventPublisher(String eventId, DomainEventOutput output) {
        this.eventId = eventId;
        this.domainEventOutput = output;
    }
    public void publish(String publisher, Object data) {
        DomainEventObject event = new DomainEventObject(eventId, publisher, DateUtils.getNow(), data);
        domainEventOutput.output().send(MessageBuilder.withPayload(event).build());
    }
}
