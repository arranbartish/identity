package com.solvedbysunrise.identity.service.validator;

import com.solvedbysunrise.identity.data.dto.TimeSlice;
import com.solvedbysunrise.identity.service.TimeSliceService;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TimeSliceValidatorTest {

    private static final TimeSlice FIVE_MINUTES = new TimeSlice(Duration.standardMinutes(5), "0:05");
    private static final TimeSlice TEN_MINUTES = new TimeSlice(Duration.standardMinutes(10), "0:10");

    private static final TimeSlice[] TIMES = new TimeSlice[] {FIVE_MINUTES, TEN_MINUTES};

    @Mock
    private TimeSliceService timeSliceService;

    @InjectMocks
    private TimeSliceValidator timeSliceValidator;


    @Before
    public void init(){
        timeSliceValidator.initialize(null);
    }
//http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#validation-beanvalidation
    @Test
    public void isValid_will_return_true_when_duration_is_valid() {
        when(timeSliceService.getTimeSlices()).thenReturn(TIMES);
        assertThat(timeSliceValidator.isValid(FIVE_MINUTES.getSlice(), null), is(true));
    }

    @Test
    public void isValid_will_return_false_when_duration_is_not_valid() {
        when(timeSliceService.getTimeSlices()).thenReturn(TIMES);
        assertThat(timeSliceValidator.isValid(Duration.ZERO, null), is(false));
    }

}