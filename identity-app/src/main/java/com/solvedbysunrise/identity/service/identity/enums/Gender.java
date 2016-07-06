package com.solvedbysunrise.identity.service.identity.enums;

import com.solvedbysunrise.identity.service.identity.enums.exception.UnknownGenderException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.String.format;
import static org.apache.commons.beanutils.BeanUtils.getProperty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.slf4j.LoggerFactory.getLogger;

public enum Gender {

    MALE("M"),
    FEMALE("F");

    private static final Logger LOGGER = getLogger(Gender.class);

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender fromValue(final String value) {
        return fromProperty("value", value);
    }

    private static Gender fromProperty(final String propertyName,
                                       final String propertyValue) {
        if (isBlank(propertyValue)) {
            return MALE;
        }

        try {
            for (Gender gender : Gender.values()) {
                if (StringUtils.equals(propertyValue,
                        getProperty(gender, propertyName))) {
                    return gender;
                }
            }
            throw new UnknownGenderException(format("Could not identify gender %s", propertyValue));
        } catch (Exception e) {
            LOGGER.warn(
                    format("Unable to identify %s with value %s defaulting",
                            propertyName, propertyValue), e);
        }
        return MALE;
    }

    public String getValue() {
        return value;
    }
}
