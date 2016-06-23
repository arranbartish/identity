package com.solvedbysunrise.identity.service.internationalisation;

import com.solvedbysunrise.identity.service.geoip.GeoLocationService;
import com.solvedbysunrise.identity.service.geoip.dto.GeoLocationContext;
import com.solvedbysunrise.identity.util.RequestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static com.solvedbysunrise.identity.internationalization.LocaleUtil.convertToLocale;
import static com.solvedbysunrise.identity.service.internationalisation.GeographicLocalisationService.APPLIED_LOCALE;
import static java.lang.String.format;
import static java.util.Locale.CANADA_FRENCH;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestContextHolder.class,ServletRequestAttributes.class, RequestUtil.class, RequestContextUtils.class})
public class GeographicLocalisationServiceTest {

    private static final String AUSTRALIAN_COUNTRY_CODE = "AU";
    private static final String COUNTRY_CODE = "CA";

    private static final Locale LOCALE = convertToLocale("fr", COUNTRY_CODE);
    private static final Locale AUSTRALIAN_LOCALE = convertToLocale("en", AUSTRALIAN_COUNTRY_CODE);
    private static final String CLIENT_IP = "client_IP";
    private static final String SESSION_LOCALE_RESOLVER_ATTRIBUTE_NAME = format("%s.LOCALE", MockLocaleResolver.class.getName());

    private GeographicLocalisationService urlDrivenLocalisationService;

    private ServletRequestAttributes requestAttributes;

    @Mock
    private GeoLocationService geoLocationService;

    @Mock
    private LocaleService localeService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        urlDrivenLocalisationService = new GeographicLocalisationService(localeService, geoLocationService);

        PowerMockito.mockStatic(RequestContextHolder.class);
        requestAttributes = PowerMockito.mock(ServletRequestAttributes.class);
        PowerMockito.when(RequestContextHolder.currentRequestAttributes()).thenReturn(requestAttributes);
        PowerMockito.when(requestAttributes.getRequest()).thenReturn(request);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void applyLocale_Will_Put_The_Locale_In_The_Session_When_One_Is_Not_Already_Set() throws Exception {
        PowerMockito.mockStatic(RequestContextUtils.class);
        PowerMockito.when(RequestContextUtils.getLocaleResolver(request)).thenReturn(new MockLocaleResolver());
        PowerMockito.mockStatic(RequestUtil.class);
        PowerMockito.when(RequestUtil.getClientAddress(request)).thenReturn(CLIENT_IP);
        when(session.getAttribute(APPLIED_LOCALE)).thenReturn(null);
        when(geoLocationService.getGeoLocationContext(CLIENT_IP)).thenReturn(buildLocationConext());
        when(localeService.getDefaultLocaleForCountyCode(COUNTRY_CODE)).thenReturn(LOCALE);
        urlDrivenLocalisationService.applyLocale();
        verify(session).setAttribute(APPLIED_LOCALE, LOCALE);
        verify(session).setAttribute(SESSION_LOCALE_RESOLVER_ATTRIBUTE_NAME, LOCALE);
    }

    @Test
    public void applyLocale_Will_Put_Australian_Locale_In_The_Session_When_Location_Service_Fails() throws Exception {
        PowerMockito.mockStatic(RequestContextUtils.class);
        PowerMockito.when(RequestContextUtils.getLocaleResolver(request)).thenReturn(new MockLocaleResolver());
        PowerMockito.mockStatic(RequestUtil.class);
        PowerMockito.when(RequestUtil.getClientAddress(request)).thenReturn(CLIENT_IP);
        when(session.getAttribute(APPLIED_LOCALE)).thenReturn(null);
        when(geoLocationService.getGeoLocationContext(CLIENT_IP)).thenThrow(new RuntimeException("Something goes wrong"));
        when(localeService.getDefaultLocaleForCountyCode(AUSTRALIAN_COUNTRY_CODE)).thenReturn(AUSTRALIAN_LOCALE);
        urlDrivenLocalisationService.applyLocale();
        verify(session).setAttribute(APPLIED_LOCALE, AUSTRALIAN_LOCALE);
        verify(session).setAttribute(SESSION_LOCALE_RESOLVER_ATTRIBUTE_NAME, AUSTRALIAN_LOCALE);
    }

    @Test
    public void applyLocale_Will_Do_Nothing_When_One_Is_Already_Set() throws Exception {
        when(session.getAttribute(APPLIED_LOCALE)).thenReturn(CANADA_FRENCH);
        urlDrivenLocalisationService.applyLocale();
        verify(session).getAttribute(APPLIED_LOCALE);
        verifyNoMoreInteractions(session);
    }

    private GeoLocationContext buildLocationConext() {
        GeoLocationContext geoLocationContext = new GeoLocationContext();
        geoLocationContext.setCountryCode(COUNTRY_CODE);
        return geoLocationContext;
    }

    private class MockLocaleResolver implements LocaleResolver {
        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            return AUSTRALIAN_LOCALE;
        }

        @Override
        public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        }
    }

}
