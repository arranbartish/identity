package com.solvedbysunrise.identity.service.validator;

import com.solvedbysunrise.identity.data.dto.validation.CorrectTimeSlice;
import com.solvedbysunrise.identity.service.TimeSliceService;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static java.util.Arrays.stream;

public class TimeSliceValidator implements ConstraintValidator<CorrectTimeSlice, Duration> {

    private final TimeSliceService timeSliceService;

    @Autowired
    public TimeSliceValidator(final TimeSliceService timeSliceService) {
        this.timeSliceService = timeSliceService;
    }

    @Override
    public void initialize(final CorrectTimeSlice constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Duration value, final ConstraintValidatorContext context) {
        return stream(timeSliceService.getTimeSlices())
                .parallel()
                .filter(timeSlice -> timeSlice.getSlice().isEqual(value))
                .findFirst()
                .isPresent();
    }
}
