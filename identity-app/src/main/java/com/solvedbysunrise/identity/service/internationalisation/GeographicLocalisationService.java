package com.solvedbysunrise.identity.service.internationalisation;

import com.solvedbysunrise.identity.service.geoip.GeoLocationService;
import com.solvedbysunrise.identity.service.geoip.dto.GeoLocationContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static com.solvedbysunrise.identity.util.RequestUtil.getClientAddress;
import static org.slf4j.LoggerFactory.getLogger;

@Service
public class GeographicLocalisationService implements LocalisationService {

    public static final Logger LOGGER = getLogger(GeographicLocalisationService.class);

    public static final String APPLIED_LOCALE = "APPLIED_LOCALE";
    private static final String DEFAULT_COUNTRY_CODE = "AU";

    private final LocaleService localeService;
    private final GeoLocationService geoLocationService;

    @Autowired
    public GeographicLocalisationService(final LocaleService localeService, final GeoLocationService geoLocationService) {
        this.localeService = localeService;
        this.geoLocationService = geoLocationService;
    }

    @Override
    public void applyLocale() {

        ServletRequestAttributes attribs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attribs.getRequest();
        HttpSession session = request.getSession();
        Locale locale = (Locale)session.getAttribute(APPLIED_LOCALE);
        if(locale == null){
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            String localeResolverAttributeName = String.format("%s.LOCALE",localeResolver.getClass().getName());
            locale = deriveAppropriateLocale(request);
            session.setAttribute(APPLIED_LOCALE, locale);
            session.setAttribute(localeResolverAttributeName, locale);
        }
    }

    private Locale deriveAppropriateLocale(HttpServletRequest request) {
        String clientIpAddress = getClientAddress(request);
        String countryCode;
        try{
            GeoLocationContext geoLocationContext = geoLocationService.getGeoLocationContext(clientIpAddress);
            countryCode = geoLocationContext.getCountryCode();
        }catch(Exception e) {
            LOGGER.info("Geo location failed defaulting to Australia", e);
            countryCode = DEFAULT_COUNTRY_CODE;
        }
        return localeService.getDefaultLocaleForCountyCode(countryCode);
    }
}
