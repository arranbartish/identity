package com.solvedbysunrise.identity.data.dto.validation;

import com.solvedbysunrise.identity.service.validator.TimeSliceValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy=TimeSliceValidator.class)
public @interface CorrectTimeSlice {

    String message() default "{com.solvedbysunrise.identity.data.dto.validation.constraints.CorrectTimeSlice.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        CorrectTimeSlice[] value();
    }
}
