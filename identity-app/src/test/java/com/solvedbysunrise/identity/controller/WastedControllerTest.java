package com.solvedbysunrise.identity.controller;

import com.google.common.collect.Lists;
import com.solvedbysunrise.identity.data.dto.WastedTime;
import com.solvedbysunrise.identity.service.WastedTimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WastedControllerTest {

    @Mock
    private WastedTime wastedTime;

    @Mock
    private WastedTimeService wastedTimeService;

    @InjectMocks
    private WastedController wastedController;

    @Test
    public void wastedTime_will_return_list_when_recording() throws Exception {
        when(wastedTimeService.recordWastedTime(wastedTime)).thenReturn(Lists.newArrayList(wastedTime));
        Collection<WastedTime> wastedTimes = wastedController.wastedTime(wastedTime);
        assertThat(wastedTimes, contains(wastedTime));
    }

    @Test
    public void wastedTime_will_return_list_when_requested() throws Exception {
        when(wastedTimeService.getAllWastedTime()).thenReturn(Lists.newArrayList(wastedTime));
        Collection<WastedTime> wastedTimes = wastedController.wastedTime();
        assertThat(wastedTimes, contains(wastedTime));

    }

}