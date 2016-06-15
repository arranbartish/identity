package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.user.AuthorityRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class AuthorityRoleDaoIntegrationTest extends IntegrationTestForBasicDao<AuthorityRoleDao, AuthorityRole, Long> {

    private static final String AUTH_LOOKUP = "AUTH_LOOKUP";
    private static final String ROLE_LOOKUP = "ROLE_LOOKUP";
    private static final String AUTH_CREATE = "AUTH_CREATE";
    private static final String ROLE_CREATE = "ROLE_CREATE";

    @Autowired
    private AuthorityRoleDao authorityRoleDao;


    private final AuthorityRole roleToCreate = new AuthorityRole();
    private final AuthorityRole roleToLookup = new AuthorityRole();

    @Before
    public void setup() {
        roleToCreate.setAuthority(AUTH_CREATE);
        roleToCreate.setRole(ROLE_CREATE);
        roleToCreate.setIsActive(true);

        roleToLookup.setAuthority(AUTH_LOOKUP);
        roleToLookup.setRole(ROLE_LOOKUP);
        roleToLookup.setIsActive(true);

        authorityRoleDao.save(roleToLookup);

    }

    @Test
    public void findAuthoritiesByRole_will_return_authorities_by_role() throws Exception {
        Set<AuthorityRole> authoritiesByRole = authorityRoleDao.findAuthoritiesByRole(ROLE_LOOKUP);
        assertThat(authoritiesByRole, contains(roleToLookup));
    }

    @Override
    public AuthorityRoleDao getDao() {
        return authorityRoleDao;
    }

    @Override
    public AuthorityRole entityToSave() {
        return roleToCreate;
    }

    @Override
    public AuthorityRole entityToDelete() {
        return roleToLookup;
    }

    @Override
    public AuthorityRole entityToCompare() {
        return roleToLookup;
    }

    @Override
    public Long entityIdToLookup() {
        return roleToLookup.getId();
    }
}