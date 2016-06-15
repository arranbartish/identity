package com.solvedbysunrise.identity.data.dao;

import com.solvedbysunrise.identity.data.entity.jpa.WastedTimeEvent;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WastedTimeDao extends PagingAndSortingRepository<WastedTimeEvent, Long> {

}
