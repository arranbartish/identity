package com.solvedbysunrise.identity.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static com.solvedbysunrise.identity.util.RequestUtil.FORWARD_HEADER;
import static com.solvedbysunrise.identity.util.RequestUtil.getClientAddress;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestUtilTest {


    private static final String SINGLE_ADDRESS = "298.304.293.298";

    private static final String SINGLE_ADDRESS_WITHLOAD_BALANCERS = "298.304.293.298, 10.384.382.384, 10.392.39.27";

    private static final String HEADER_BLANK = "";

    @Mock
    HttpServletRequest httpServletRequest;

    @Test
    public void getClientAddress_Will_Return_The_Address_When_Provided_Just_A_Single_Address_In_The_Header() throws Exception {
        when(httpServletRequest.getHeader(FORWARD_HEADER)).thenReturn(SINGLE_ADDRESS);
        String clientAddress = getClientAddress(httpServletRequest);
        assertThat(clientAddress, is(SINGLE_ADDRESS));
    }

    @Test
    public void getClientAddress_Will_Return_The_Address_When_Provided_A_Single_Address_And_Load_Balancers_In_The_Header() throws Exception {
        when(httpServletRequest.getHeader(FORWARD_HEADER)).thenReturn(SINGLE_ADDRESS_WITHLOAD_BALANCERS);
        String clientAddress = getClientAddress(httpServletRequest);
        assertThat(clientAddress, is(SINGLE_ADDRESS));
    }

    @Test
    public void getClientAddress_Will_Return_The_Address_When_Provided_A_Blank_In_The_Header() throws Exception {
        when(httpServletRequest.getHeader(FORWARD_HEADER)).thenReturn(HEADER_BLANK);
        when(httpServletRequest.getRemoteAddr()).thenReturn(SINGLE_ADDRESS);
        String clientAddress = getClientAddress(httpServletRequest);
        assertThat(clientAddress, is(SINGLE_ADDRESS));
    }

    @Test
    public void getClientAddress_Will_Return_The_Address_When_Provided_A_Null_In_The_Header() throws Exception {
        when(httpServletRequest.getHeader(FORWARD_HEADER)).thenReturn(null);
        when(httpServletRequest.getRemoteAddr()).thenReturn(SINGLE_ADDRESS);
        String clientAddress = getClientAddress(httpServletRequest);
        assertThat(clientAddress, is(SINGLE_ADDRESS));
    }
}
