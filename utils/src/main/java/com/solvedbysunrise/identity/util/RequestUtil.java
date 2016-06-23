package com.solvedbysunrise.identity.util;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.*;

public final class RequestUtil {

    static final String FORWARD_HEADER = "X-Forwarded-For";

    private RequestUtil() {}

    public static String getClientAddress(HttpServletRequest request){
        final String forwardHeaders = request.getHeader(FORWARD_HEADER);
        final String address =(isNotBlank(forwardHeaders))? stripLoadBalancers(forwardHeaders):request.getRemoteAddr();
        return trim(address);
    }

    private static String stripLoadBalancers(final String forwardHeaders) {
        String[] addressComponents = split(forwardHeaders, ",");
        return addressComponents[0];
    }
}
