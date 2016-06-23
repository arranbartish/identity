package com.solvedbysunrise.identity.service.internationalisation;

import com.solvedbysunrise.bean.RefelctiveBean;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.FRENCH;

public class SupportedLocaleSummary extends RefelctiveBean {

    private final Locale locale;
    private final Map<String, String> localisedNames;

    private SupportedLocaleSummary(Locale locale, Map<String, String> localisedNames) {
        this.locale = locale;
        this.localisedNames = localisedNames;
    }

    public static SupportedLocaleSummary createSummary(Locale locale) {
        Map<String, String> names = new HashMap<>();
        names.put(locale.getLanguage(), locale.getDisplayLanguage(locale));
        names.put(ENGLISH.getLanguage(), locale.getDisplayLanguage(ENGLISH));
        names.put(FRENCH.getLanguage(), locale.getDisplayLanguage(FRENCH));
        locale.getDisplayName(locale);
        return new SupportedLocaleSummary(locale, names);
    }

    public Locale getLocale() {
        return locale;
    }

    public Map<String, String> getLocalisedNames() {
        return localisedNames;
    }
}
