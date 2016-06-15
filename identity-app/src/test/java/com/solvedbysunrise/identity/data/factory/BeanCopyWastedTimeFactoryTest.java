package com.solvedbysunrise.identity.data.factory;

import com.solvedbysunrise.identity.data.dto.WastedTime;
import com.solvedbysunrise.identity.data.entity.jpa.WastedTimeEvent;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;

import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.beans.BeanUtils.copyProperties;

public class BeanCopyWastedTimeFactoryTest {

    private final static String WHO = "Arran";
    private final static String ACTIVITY = "this app";
    private final static Duration DURATION = Duration.standardMinutes(30);
    private final static DateTime TODAY = DateTime.now().withTimeAtStartOfDay();

    private final static WastedTime WASTED_TIME_DTO = new WastedTime(WHO, DURATION, ACTIVITY, TODAY);
    private final static WastedTimeEvent WASTED_TIME_ENTITY = new ComparableWastedTimeEvent();{
        WASTED_TIME_ENTITY.setActivity(ACTIVITY);
        WASTED_TIME_ENTITY.setDate(new Timestamp(TODAY.getMillis()));
        WASTED_TIME_ENTITY.setDuration(DURATION.getMillis());
        WASTED_TIME_ENTITY.setWho(WHO);
    }

    private final WastedTimeFactory beanFactory = new BeanCopyWastedTimeFactory();

    @Test
    public void fromDto_will_return_entity() throws Exception {
        WastedTimeEvent event = new ComparableWastedTimeEvent(beanFactory.fromDto(WASTED_TIME_DTO));
        assertThat(event, is(WASTED_TIME_ENTITY));
    }

    @Test
    public void fromEntity_will_return_dto() throws Exception {
        WastedTime wastedTime = beanFactory.fromEntity(WASTED_TIME_ENTITY);
        assertThat(wastedTime, is(WASTED_TIME_DTO));
    }


    private static class ComparableWastedTimeEvent extends WastedTimeEvent {

        ComparableWastedTimeEvent() {
        }

        ComparableWastedTimeEvent(WastedTimeEvent event) {
            copyProperties(event, this);
        }

        @Override
        public String[] excludedFields() {
            return new String[] {"id"};
        }
    }
}