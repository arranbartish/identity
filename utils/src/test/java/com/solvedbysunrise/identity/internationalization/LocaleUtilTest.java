package com.solvedbysunrise.identity.internationalization;

import org.junit.Test;

import java.util.Locale;

import static com.solvedbysunrise.identity.internationalization.LocaleUtil.convertToLocale;
import static java.util.Locale.CANADA_FRENCH;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LocaleUtilTest {

    private static final String CANADA_COUNTRY_CODE = "CA";
    private static final String CANADA_COUNTRY_CODE_WRONG_CASE = "cA";

    private static final String FRENCH_LANGUAGE_CODE = "fr";
    private static final String FRENCH_LANGUAGE_CODE_WRONG_CASE = "Fr";

    @Test
    public void convertToLocale_Will_Return_A_Locale_When_Given_Valid_Values() throws Exception {
        Locale locale = convertToLocale(FRENCH_LANGUAGE_CODE, CANADA_COUNTRY_CODE);

        assertThat(locale, is(CANADA_FRENCH));

    }

    @Test
    public void convertToLocale_Will_Return_A_Locale_When_GIven_Valid_Values_In_The_Wrong_Case() throws Exception {
        Locale locale = convertToLocale(FRENCH_LANGUAGE_CODE_WRONG_CASE, CANADA_COUNTRY_CODE_WRONG_CASE);

        assertThat(locale, is(CANADA_FRENCH));

    }

}