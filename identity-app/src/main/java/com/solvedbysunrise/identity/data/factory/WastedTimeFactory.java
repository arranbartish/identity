package com.solvedbysunrise.identity.data.factory;

import com.solvedbysunrise.identity.data.dto.WastedTime;
import com.solvedbysunrise.identity.data.entity.jpa.WastedTimeEvent;

public interface WastedTimeFactory {


    WastedTimeEvent fromDto (WastedTime wastedTime);

    WastedTime fromEntity (WastedTimeEvent wastedTimeEvent);
}
