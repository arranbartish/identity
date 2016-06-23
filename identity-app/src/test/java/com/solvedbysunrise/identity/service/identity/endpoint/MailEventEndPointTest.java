package com.solvedbysunrise.identity.service.identity.endpoint;

import com.receiptdrop.identity.email.EmailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static com.receiptdrop.helper.date.DateUtil.isDateWithinLastXMinutes;
import static com.receiptdrop.helper.date.DateUtil.isSameDay;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MailEventEndPointTest {

    private static Map<String, String> VALID_POST_WITHOUT_TIMESTAMP;
    private static Map<String, String> VALID_POST_WITH_TIMESTAMP;
    private static Date EXPECTED_DATE;

    private static final String MESSAGE_ID = "MESSAGE-ID";
    private static final String GUID = "A_GUID";
    private static final String EVENT = "A_EVENT";
    private static final String TIMESTAMP = "1375186139";   // timestamp from mailgun Tue, 30 Jul 2013 12:08:59 +0000


    @Mock
    private EmailService emailService;

    @InjectMocks
    private MailEventEndPoint mailEventEndPoint;

    @Captor
    private ArgumentCaptor<String> guidCaptor;

    @Captor
    private ArgumentCaptor<String> confirmationIdCaptor;

    @Captor
    private ArgumentCaptor<String> eventCaptor;

    @Captor
    private ArgumentCaptor<Date> eventTimestampDateCaptor;

    @Before
    public void setup() {
        VALID_POST_WITHOUT_TIMESTAMP = newHashMap();
        VALID_POST_WITHOUT_TIMESTAMP.put("Message-Id", MESSAGE_ID);
        VALID_POST_WITHOUT_TIMESTAMP.put("guid", GUID);
        VALID_POST_WITHOUT_TIMESTAMP.put("event", EVENT);

        VALID_POST_WITH_TIMESTAMP = newHashMap(VALID_POST_WITHOUT_TIMESTAMP);
        VALID_POST_WITH_TIMESTAMP.put("timestamp", TIMESTAMP);

        SimpleDateFormat simpleDateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            EXPECTED_DATE = simpleDateFormater.parse("30/07/2013 12:08:59");
        } catch (ParseException e) {
            throw new RuntimeException("could not parse date", e);
        }
    }

    @Test
    public void handleEvent_Will_Call_Service_With_Parameters_When_Provided_Valid_Json_With_Timestamp() {
        mailEventEndPoint.handleEvent(VALID_POST_WITH_TIMESTAMP);
        verify(emailService).handleEmailEvent(guidCaptor.capture(),
                confirmationIdCaptor.capture(),
                eventCaptor.capture(),
                eventTimestampDateCaptor.capture());

        assertThat(guidCaptor.getValue(), is(GUID));
        assertThat(confirmationIdCaptor.getValue(), is(MESSAGE_ID));
        assertThat(eventCaptor.getValue(), is(EVENT));
        assertThat(isSameDay(eventTimestampDateCaptor.getValue(), EXPECTED_DATE), is(true));
    }

    @Test
    public void handleEvent_Will_Call_Service_With_Parameters_When_Provided_Valid_Json_Without_Timestamp() {
        mailEventEndPoint.handleEvent(VALID_POST_WITHOUT_TIMESTAMP);
        verify(emailService).handleEmailEvent(guidCaptor.capture(),
                confirmationIdCaptor.capture(),
                eventCaptor.capture(),
                eventTimestampDateCaptor.capture());

        assertThat(guidCaptor.getValue(), is(GUID));
        assertThat(confirmationIdCaptor.getValue(), is(MESSAGE_ID));
        assertThat(eventCaptor.getValue(), is(EVENT));
        assertThat(isDateWithinLastXMinutes(eventTimestampDateCaptor.getValue(), 2), is(true));
    }
}
