package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dao.WastedTimeDao;
import com.solvedbysunrise.identity.data.dto.WastedTime;
import com.solvedbysunrise.identity.data.entity.jpa.WastedTimeEvent;
import com.solvedbysunrise.identity.data.factory.WastedTimeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;
import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

@Transactional
@Service
public class EntityMappingWastedTimeService implements WastedTimeService {

    private static final boolean NON_PARALLEL = false;
    private final WastedTimeDao wastedTimeDao;

    private final WastedTimeFactory wastedTimeFactory;

    @Autowired
    public EntityMappingWastedTimeService(WastedTimeDao wastedTimeDao, WastedTimeFactory wastedTimeFactory) {
        this.wastedTimeDao = wastedTimeDao;
        this.wastedTimeFactory = wastedTimeFactory;
    }

    @Override
    public Collection<WastedTime> recordWastedTime(WastedTime wastedTime) {
        WastedTimeEvent wastedTimeEvent = wastedTimeFactory.fromDto(wastedTime);
        wastedTimeDao.save(wastedTimeEvent);
        return getAllWastedTime();
    }

    @Override
    public Collection<WastedTime> getAllWastedTime() {
        return stream(wastedTimeDao.findAll().spliterator(), NON_PARALLEL)
                .map(wastedTimeFactory::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<String> getAllWastedTimeActivities() {
        return stream(wastedTimeDao.findAll().spliterator(), NON_PARALLEL)
                .map(wastedTimeEvent -> capitalizeFully(wastedTimeEvent.getActivity()))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Collection<String> getEveryoneWhoHasWastedTime() {
        return stream(wastedTimeDao.findAll().spliterator(), NON_PARALLEL)
                .map(wastedTimeEvent -> capitalizeFully(wastedTimeEvent.getWho()))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
