package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface RegisteredEntityDao extends PagingAndSortingRepository<BasicRegisteredEntity, Long> {



    // findAllOutOfDateEntitiesWithoutAnOutStandingPasswordResetRequest
    @Query(value = "select re from BasicRegisteredEntity as re, ResetPasswordEmail as rpe" +
            " where re.id != rpe.entityId and rpe.result = 'PENDING' " +
            "and re.passwordVersion != :passwordVersion")
    Collection<BasicRegisteredEntity> findAllAccountsWithPasswordVersionAndNoResetPassword(@Param("passwordVersion") String specificPasswordVersion);

    BasicRegisteredEntity findByUsername(String username);

    @Query(value = "SELECT rer.roleName FROM RegisteredEntityRole AS rer where rer.registeredEntity.id = :accountId")
    Set<String> findAllRolesForAccount(@Param("accountId") Long accountId);
}
