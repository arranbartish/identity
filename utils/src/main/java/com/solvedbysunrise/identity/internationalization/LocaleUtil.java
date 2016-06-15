package com.solvedbysunrise.identity.internationalization;

import java.util.Locale;

import static java.lang.String.format;
import static org.apache.commons.lang3.LocaleUtils.toLocale;
import static org.apache.commons.lang3.StringUtils.lowerCase;
import static org.apache.commons.lang3.StringUtils.upperCase;

public final class LocaleUtil {

    private final static String LOCALE_CODE_PATTERN = "%s_%s";

    public static Locale convertToLocale(final String languageCode, final String countryCode) {
        final String localCode = format(LOCALE_CODE_PATTERN, lowerCase(languageCode), upperCase(countryCode));
        return toLocale(localCode);
    }
}
