package com.solvedbysunrise.identity.data.factory;

import com.solvedbysunrise.identity.data.dto.WastedTime;
import com.solvedbysunrise.identity.data.entity.jpa.WastedTimeEvent;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class BeanCopyWastedTimeFactory implements WastedTimeFactory {


    @Override
    public WastedTimeEvent fromDto(final WastedTime wastedTime) {
        WastedTimeEvent wastedTimeEvent = new WastedTimeEvent();
        copyProperties(wastedTime, wastedTimeEvent, "duration", "date");
        wastedTimeEvent.setDuration(wastedTime.getDuration().getMillis());
        wastedTimeEvent.setDate(new Timestamp(wastedTime.getDate().getMillis()));
        return wastedTimeEvent;
    }

    @Override
    public WastedTime fromEntity(final WastedTimeEvent wastedTimeEvent) {
        WastedTime wastedTime = new WastedTime(
                wastedTimeEvent.getWho(),
                Duration.millis(wastedTimeEvent.getDuration()),
                wastedTimeEvent.getActivity(),
                new DateTime(wastedTimeEvent.getDate())
        );
        return wastedTime;
    }
}
