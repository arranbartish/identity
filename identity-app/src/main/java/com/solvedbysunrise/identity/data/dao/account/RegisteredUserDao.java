package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.data.entity.jpa.user.RegisteredUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredUserDao extends PagingAndSortingRepository<RegisteredUser, Long> {



    RegisteredUser findByPrimaryEmail(String primaryEmail);

}
