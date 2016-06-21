package com.solvedbysunrise.identity.data.dao.email;


import com.solvedbysunrise.identity.data.entity.jpa.email.EmailProperty;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EmailPropertyDao extends PagingAndSortingRepository<EmailProperty, Long> {

    Collection<EmailProperty> findByType(EmailType type);

}
