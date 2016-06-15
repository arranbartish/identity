package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dto.WastedTime;

import java.util.Collection;

public interface WastedTimeService {

    Collection<WastedTime> recordWastedTime(WastedTime wastedTime);

    Collection<WastedTime> getAllWastedTime();

    Collection<String> getAllWastedTimeActivities();

    Collection<String> getEveryoneWhoHasWastedTime();
}
