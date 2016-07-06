package com.solvedbysunrise.identity.service.internationalisation;

import java.util.Locale;

public interface LocaleService {

    Locale getDefaultLocaleForCountyCode(String countryCode);

    SupportedLocaleSummary[] getAllSupportedLocalesForCountyCode(String countryCode);

}
