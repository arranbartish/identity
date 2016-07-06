package com.solvedbysunrise.identity.service.internationalisation;

import org.apache.commons.lang3.LocaleUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.LocaleUtils.languagesByCountry;

@Service
public class LocaleUtilsLocaleService implements LocaleService {

    @Override
    public Locale getDefaultLocaleForCountyCode(String countryCode) {
        List<Locale> locales = languagesByCountry(countryCode);
        Optional<Locale> firstLocale = locales.stream().sorted((left, right) -> right.toString().compareTo(left.toString())).findFirst();
        return firstLocale.orElse(Locale.CANADA_FRENCH);
    }

    @Override
    public SupportedLocaleSummary[] getAllSupportedLocalesForCountyCode(String countryCode) {
        List<Locale> locales = LocaleUtils.languagesByCountry(countryCode);
        List<SupportedLocaleSummary> supportedLocaleSummary = locales
                .stream()
                .sorted((left, right) -> left.toString().compareTo(right.toString()))
                .map(SupportedLocaleSummary::createSummary)
                .collect(toList());
        return supportedLocaleSummary.toArray(new SupportedLocaleSummary[supportedLocaleSummary.size()]);
    }
}