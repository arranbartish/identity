package com.solvedbysunrise.identity.data.dto;


import org.joda.time.DateTime;

public class MailEvent {

    private final String confirmationId;
    private final String guid;
    private final String event;
    private final DateTime timestamp;

    public MailEvent(String confirmationId, String guid, String event, DateTime timestamp) {
        this.confirmationId = confirmationId;
        this.guid = guid;
        this.event = event;
        this.timestamp = timestamp;
    }

    public String getConfirmationId() {
        return confirmationId;
    }

    public String getGuid() {
        return guid;
    }

    public String getEvent() {
        return event;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }
}
