package com.solvedbysunrise.identity.data.dao;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.entity.jpa.WastedTimeEvent;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class WastedTimeDaoIntegrationTest {

    private static final WastedTimeEvent EXPECTED_EVENT = buildEvent();

    @Autowired
    private WastedTimeDao wastedTimeDao;

    @Before
    public void entity_will_be_saved() {
        wastedTimeDao.save(EXPECTED_EVENT);
    }

    @Test
    public void entity_will_be_retrieved_from_dao() {
        WastedTimeEvent event = ofNullable(wastedTimeDao.findOne(EXPECTED_EVENT.getId()))
                        .orElseThrow(
                                () -> new RuntimeException(
                                        format("Entity <%s> not in Database",
                                                EXPECTED_EVENT.toString())));
        assertThat(event, is(EXPECTED_EVENT));
    }

    private static WastedTimeEvent buildEvent() {
        WastedTimeEvent event = new WastedTimeEvent();
        event.setWho("Arran");
        event.setDuration(Duration.standardMinutes(30).getMillis());
        event.setActivity("this app");
        event.setDate(new Timestamp(DateTime.now().getMillis()));
        return event;
    }
}