package com.solvedbysunrise.identity.service.internationalisation;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Locale;

import static com.solvedbysunrise.identity.service.internationalisation.SupportedLocaleSummary.createSummary;
import static java.util.Locale.CANADA;
import static java.util.Locale.CANADA_FRENCH;
import static java.util.Locale.FRENCH;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.*;

public class LocaleUtilsLocaleServiceTest {

    private LocaleUtilsLocaleService localeUtilsLocaleService = new LocaleUtilsLocaleService();

    private SupportedLocaleSummary[] EXPECTED_SUPPORTED_LOCALES = new SupportedLocaleSummary[] {
            createSummary(CANADA),
            createSummary(CANADA_FRENCH)
    };

    @Test
    public void getDefaultLocaleForCountyCode_will_return_french_for_canada() throws Exception {
        Locale defaultLocaleForCanada = localeUtilsLocaleService.getDefaultLocaleForCountyCode(CANADA.getCountry());
        assertThat(defaultLocaleForCanada.getLanguage(), CoreMatchers.is(FRENCH.getLanguage()));
    }

    @Test
    public void getAllSupportedLocalesForCountyCode() throws Exception {
        SupportedLocaleSummary[] locales = localeUtilsLocaleService.getAllSupportedLocalesForCountyCode(CANADA.getCountry());
        assertThat(locales, arrayContaining(EXPECTED_SUPPORTED_LOCALES));
    }

}