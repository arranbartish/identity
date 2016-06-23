package com.solvedbysunrise.identity.service.internationalisation;

import com.receiptdrop.identity.dao.LocaleDefinitionDao;
import com.receiptdrop.identity.domain.internationalisation.Country;
import com.receiptdrop.identity.domain.internationalisation.Language;
import com.receiptdrop.identity.domain.internationalisation.LocaleDefinition;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.immutableEntry;
import static com.receiptdrop.helper.internationalisation.LocaleUtil.convertToLocale;
import static com.receiptdrop.unit.matcher.ArrayMatchers.hasSize;
import static com.receiptdrop.unit.matcher.MapMatchers.containsElement;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseDrivenLocaleServiceTest {

    private static final String CRAZY_COUNTRY_CODE = "CRAZY";
    private static final String AUSTRALIA_COUNTRY_CODE = "AU";
    private static final String CANDADA_COUNTRY_CODE = "CA";
    private static final String ENGLISH_LANGUAGE_CODE = "en";
    private static final String FRENCH_LANGUAGE_CODE = "fr";
    private static final String AUSTRALIA = "Australia";
    private static final String CANADA = "Canada";
    private static final String ENGLISH_ENGLISH = "english";
    private static final String ENGLISH_FRENCH = "anglais";
    private static final String FRENCH_ENGLISH = "french";
    private static final String FRENCH_FRENCH = "français";
    private static final Locale AUSTRALIAN_LOCALE = convertToLocale(ENGLISH_LANGUAGE_CODE, AUSTRALIA_COUNTRY_CODE);
    private static final Locale FRENCH_CANADIAN_LOCALE = convertToLocale(FRENCH_LANGUAGE_CODE, CANDADA_COUNTRY_CODE);
    private static final Locale ENGLISH_CANADIAN_LOCALE = convertToLocale(ENGLISH_LANGUAGE_CODE, CANDADA_COUNTRY_CODE);

    @Mock
    private LocaleDefinitionDao localeDefinitionDao;

    @InjectMocks
    private DatabaseDrivenLocaleService databaseDrivenLocaleService;

    private LocaleDefinition australianDefinition;

    private LocaleDefinition frenchCanadianDefinition;

    private LocaleDefinition englishCanadianDefinition;


    @Before
    public void setUp() throws Exception {
        australianDefinition = buildDefinition(AUSTRALIA, AUSTRALIA_COUNTRY_CODE, ENGLISH_LANGUAGE_CODE, ENGLISH_ENGLISH, ENGLISH_FRENCH);
        englishCanadianDefinition = buildDefinition(CANADA, CANDADA_COUNTRY_CODE, ENGLISH_LANGUAGE_CODE, ENGLISH_ENGLISH, ENGLISH_FRENCH);
        frenchCanadianDefinition = buildDefinition(CANADA, CANDADA_COUNTRY_CODE, FRENCH_LANGUAGE_CODE, FRENCH_ENGLISH, FRENCH_FRENCH);
    }

    @Test
    public void getDefaultLocaleForCountyCode_Will_Return_Australian_Loacale_When_Provided_AU_Country_Code() throws Exception {
        when(localeDefinitionDao.findDefaultDefinitionForCountryCode(AUSTRALIA_COUNTRY_CODE)).thenReturn(australianDefinition);
        Locale defaultLocaleForCountyCode = databaseDrivenLocaleService.getDefaultLocaleForCountyCode(AUSTRALIA_COUNTRY_CODE);

        assertThat(defaultLocaleForCountyCode, is(AUSTRALIAN_LOCALE));
    }

    @Test
    public void getDefaultLocaleForCountyCode_Will_Return_Australian_Loacale_When_Provided_Unsupported_Country_Code() throws Exception {
        when(localeDefinitionDao.findDefaultDefinitionForCountryCode(CRAZY_COUNTRY_CODE)).thenReturn(null);
        Locale defaultLocaleForCountyCode = databaseDrivenLocaleService.getDefaultLocaleForCountyCode(CRAZY_COUNTRY_CODE);

        assertThat(defaultLocaleForCountyCode, is(AUSTRALIAN_LOCALE));
    }

    @Test
    public void getAllSupportedLocalesForCountyCode_Will_Return_Multiple_Locale_Summaries_For_Country_When_They_Exist() throws Exception {
        when(localeDefinitionDao.findAllSupportedLocalesForCountryCode(CANDADA_COUNTRY_CODE)).thenReturn(newArrayList(frenchCanadianDefinition, englishCanadianDefinition));

        SupportedLocaleSummary[] allSupportedLocalesForCountyCode = databaseDrivenLocaleService.getAllSupportedLocalesForCountyCode(CANDADA_COUNTRY_CODE);
        assertThat(allSupportedLocalesForCountyCode, hasSize(2));
        SupportedLocaleSummary frenchCanadianLocale = findSummaryByLocale(allSupportedLocalesForCountyCode, FRENCH_CANADIAN_LOCALE);
        SupportedLocaleSummary englishCanadianLocale = findSummaryByLocale(allSupportedLocalesForCountyCode, ENGLISH_CANADIAN_LOCALE);

        assertThat(frenchCanadianLocale.getLocalisedNames(), containsElement(immutableEntry(ENGLISH_LANGUAGE_CODE, FRENCH_ENGLISH)));
        assertThat(frenchCanadianLocale.getLocalisedNames(), containsElement(immutableEntry(FRENCH_LANGUAGE_CODE, FRENCH_FRENCH)));

        assertThat(englishCanadianLocale.getLocalisedNames(), containsElement(immutableEntry(ENGLISH_LANGUAGE_CODE, ENGLISH_ENGLISH)));
        assertThat(englishCanadianLocale.getLocalisedNames(), containsElement(immutableEntry(FRENCH_LANGUAGE_CODE, ENGLISH_FRENCH)));
    }

    @Test
    public void getAllSupportedLocalesForCountyCode_Will_Return_Australian_Summary_For_Country_When_No_Other_Exist() throws Exception {
        List<LocaleDefinition> emptyDefinitions = newArrayList();
        when(localeDefinitionDao.findAllSupportedLocalesForCountryCode(CANDADA_COUNTRY_CODE)).thenReturn(emptyDefinitions);
        when(localeDefinitionDao.findDefaultDefinitionForCountryCode(AUSTRALIA_COUNTRY_CODE)).thenReturn(australianDefinition);

        SupportedLocaleSummary[] allSupportedLocalesForCountyCode = databaseDrivenLocaleService.getAllSupportedLocalesForCountyCode(CANDADA_COUNTRY_CODE);
        assertThat(allSupportedLocalesForCountyCode, hasSize(1));
        SupportedLocaleSummary australianLocaleSummary = findSummaryByLocale(allSupportedLocalesForCountyCode, AUSTRALIAN_LOCALE);

        assertThat(australianLocaleSummary.getLocalisedNames(), containsElement(immutableEntry(ENGLISH_LANGUAGE_CODE, ENGLISH_ENGLISH)));
        assertThat(australianLocaleSummary.getLocalisedNames(), containsElement(immutableEntry(FRENCH_LANGUAGE_CODE, ENGLISH_FRENCH)));

    }

    private SupportedLocaleSummary findSummaryByLocale(SupportedLocaleSummary[] summaries, Locale targetLocale) {
        for (SupportedLocaleSummary summary : summaries) {
            if (StringUtils.equals(summary.getLocale().getCountry(), targetLocale.getCountry())
            && StringUtils.equals(summary.getLocale().getLanguage(), targetLocale.getLanguage())){
               return summary;
            }
        }
        Assert.fail(String.format("locale %s not found in summaries {%s}", targetLocale.toString(), Arrays.toString(summaries)));
        return null;
    }

    private LocaleDefinition buildDefinition(String countryName, String countryCode, String languageCode, String languageNameInEnglish, String languageNameInFrench) {
        Country country = new Country();
        Language language = new Language();
        LocaleDefinition localeDefinition = new LocaleDefinition();

        country.setName(countryName);
        country.setCountryCode(countryCode);
        country.setLocaleDefinitions(newHashSet(localeDefinition));

        language.setEnglishName(languageNameInEnglish);
        language.setFrenchName(languageNameInFrench);
        language.setLanguageCode(languageCode);
        language.setLocaleDefinitions(newHashSet(localeDefinition));

        localeDefinition.setCountryDefaultLocale(true);
        localeDefinition.setCountry(country);
        localeDefinition.setLanguage(language);

        return localeDefinition;
    }
}
