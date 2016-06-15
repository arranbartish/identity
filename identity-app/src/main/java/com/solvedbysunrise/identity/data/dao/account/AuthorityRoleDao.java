package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.data.entity.jpa.user.AuthorityRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthorityRoleDao extends CrudRepository<AuthorityRole, Long> {

    @Query(value = "SELECT ar FROM AuthorityRole ar WHERE ar.role IN :roles AND ar.isActive = true")
    Set<AuthorityRole> findAuthoritiesByRole(@Param("roles") String... roles);

}
