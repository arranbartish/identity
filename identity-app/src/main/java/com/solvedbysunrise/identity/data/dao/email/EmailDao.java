package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.data.entity.jpa.email.Email;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailDao<T extends Email>  extends PagingAndSortingRepository<T, Long> {


}
