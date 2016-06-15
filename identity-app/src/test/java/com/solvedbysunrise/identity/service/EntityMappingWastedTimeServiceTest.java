package com.solvedbysunrise.identity.service;

import com.google.common.collect.Lists;
import com.solvedbysunrise.identity.data.dao.WastedTimeDao;
import com.solvedbysunrise.identity.data.dto.WastedTime;
import com.solvedbysunrise.identity.data.entity.jpa.WastedTimeEvent;
import com.solvedbysunrise.identity.data.factory.BeanCopyWastedTimeFactory;
import com.solvedbysunrise.identity.data.factory.WastedTimeFactory;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EntityMappingWastedTimeServiceTest {

    private static final String ARRAN = "Arran";
    private static final String JAMES = "James";
    private static final String JOHN = "John";

    private static final Duration DURATION = Duration.standardMinutes(30);
    private static final String ACTIVITY = "This app";
    private static final DateTime DATE_TIME = DateTime.now();
    private static final WastedTime WASTED_TIME = new WastedTime(ARRAN, DURATION, ACTIVITY, DATE_TIME);


    private static final WastedTime WASTED_TIME_OTHER = new WastedTime(JAMES, DURATION, "Something else", DATE_TIME);
    private static final WastedTime WASTED_TIME_ANOTHER = new WastedTime(JOHN, DURATION, "Yeah it sucks", DATE_TIME);

    private static final String[] EXPECTED_ACTIVITIES = new String[] {"Something Else", "This App", "Yeah It Sucks"};
    private static final String[] EXPECTED_TIME_WASTERS = new String[] {ARRAN, JAMES, JOHN};


    @Mock
    private WastedTimeDao wastedTimeDao;

    @Mock
    private WastedTimeFactory wastedTimeFactory;

    @InjectMocks
    private EntityMappingWastedTimeService entityMappingWastedTimeService;

    @Before
    public void setupMocks() {
        WastedTimeEvent event = (new BeanCopyWastedTimeFactory()).fromDto(WASTED_TIME);
        when(wastedTimeFactory.fromDto(WASTED_TIME)).thenReturn(event);
        when(wastedTimeDao.save(event)).thenReturn(event);
        when(wastedTimeDao.findAll()).thenReturn(Lists.newArrayList(event));
        when(wastedTimeFactory.fromEntity(event)).thenReturn(WASTED_TIME);
    }

    @Test
    public void recordWastedTime_will_return_events_including_the_one_recorded() throws Exception {
        Collection<WastedTime> wastedTimes = entityMappingWastedTimeService.recordWastedTime(WASTED_TIME);
        assertThat(wastedTimes, hasItem(WASTED_TIME));
    }

    @Test
    public void getAllWastedTime_will_return_all_events() throws Exception {
        Collection<WastedTime> wastedTimes = entityMappingWastedTimeService.getAllWastedTime();
        assertThat(wastedTimes, hasItem(WASTED_TIME));
    }

    @Test
    public void getAllWastedTimeActivities_will_return_all_activites_with_duplicates_removed() throws Exception {
        BeanCopyWastedTimeFactory beanCopyWastedTimeFactory = new BeanCopyWastedTimeFactory();
        when(wastedTimeDao.findAll()).thenReturn(Lists.newArrayList(
                beanCopyWastedTimeFactory.fromDto(WASTED_TIME),
                beanCopyWastedTimeFactory.fromDto(WASTED_TIME),
                beanCopyWastedTimeFactory.fromDto(WASTED_TIME_OTHER),
                beanCopyWastedTimeFactory.fromDto(WASTED_TIME_ANOTHER)
                ));
        Collection<String> crapActivities = entityMappingWastedTimeService.getAllWastedTimeActivities();
        assertThat(crapActivities, hasItems(EXPECTED_ACTIVITIES));
    }

    @Test
    public void getEveryoneWhoHasWastedTime_will_return_all_time_wasters_with_duplicates_removed() throws Exception {
        BeanCopyWastedTimeFactory beanCopyWastedTimeFactory = new BeanCopyWastedTimeFactory();
        when(wastedTimeDao.findAll()).thenReturn(Lists.newArrayList(
                beanCopyWastedTimeFactory.fromDto(WASTED_TIME),
                beanCopyWastedTimeFactory.fromDto(WASTED_TIME),
                beanCopyWastedTimeFactory.fromDto(WASTED_TIME_OTHER),
                beanCopyWastedTimeFactory.fromDto(WASTED_TIME_ANOTHER)
        ));
        Collection<String> timeWasters = entityMappingWastedTimeService.getEveryoneWhoHasWastedTime();
        assertThat(timeWasters, hasItems(EXPECTED_TIME_WASTERS));
    }
}