package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.data.entity.jpa.email.BasicEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Repository
public interface BasicEmailDao extends EmailDao<BasicEmail> {

    public static final Pageable FIRST_TEN_EMAILS = new PageRequest(0, 10);

    BasicEmail findByGuid(String guid);

    BasicEmail findByConfirmationId(String confirmationId);

    @Query("SELECT be FROM BasicEmail be WHERE be.sentDate IS NULL")
    Page<BasicEmail> findUnsentEmails(Pageable pageable);
}
