package com.solvedbysunrise.identity.controller;

import com.solvedbysunrise.identity.data.dto.MailEvent;
import com.solvedbysunrise.identity.service.EmailService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.*;
import static org.joda.time.DateTimeZone.UTC;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/email/event")
public class MailEventController {

    private static final String MAIL_GUN_ID = "Message-Id";
    private static final String EMAIL_GUID = "guid";
    private static final String EVENT_DESCRIPTION = "event";
    private static final String EVENT_TIMESTAMP = "timestamp";

    private final EmailService emailService;

    @Autowired
    public MailEventController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping(method = POST)
    @ResponseStatus(OK)
    public void handleMailEvent(@RequestParam(MAIL_GUN_ID) String confirmationId,
                                @RequestParam(EMAIL_GUID) String guid,
                                @RequestParam(EVENT_DESCRIPTION) String event,
                                @RequestParam(EVENT_TIMESTAMP) String timestamp) {
        String populatedTimestamp =  isEmpty(timestamp) ? generateNowInSecondsAndTruncateMillis() : timestamp;

        MailEvent mailEvent = new MailEvent(confirmationId, guid, event, new DateTime(convertToMilliseconds(populatedTimestamp), UTC));
        // TODO pass DTO to service
        emailService.handleEmailEvent(mailEvent.getGuid(), mailEvent.getConfirmationId(), mailEvent.getEvent(), mailEvent.getTimestamp().toDate());
    }


    public static String generateNowInSecondsAndTruncateMillis() {
        long nowInMilliseconds = DateTime.now().getMillis();
        String nowInMillisecondsString = format("%s", nowInMilliseconds);
        return substring(nowInMillisecondsString, 0, length(nowInMillisecondsString)-3);
    }


    private Long convertToMilliseconds(String eventtimeStampInSeconds) {
        return parseLong(format("%s000", eventtimeStampInSeconds));
    }
}
