package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.WastedtimeApplication;
import com.solvedbysunrise.identity.config.TestConfiguration;
import com.solvedbysunrise.identity.data.dao.IntegrationTestForBasicDao;
import com.solvedbysunrise.identity.data.entity.jpa.user.AuthorityRole;
import com.solvedbysunrise.identity.data.entity.jpa.user.AuthorityRoleId;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {WastedtimeApplication.class, TestConfiguration.class})
@IntegrationTest
public class AuthorityRoleDaoIntegrationTest extends IntegrationTestForBasicDao<AuthorityRoleDao, AuthorityRole, AuthorityRoleId> {

    @Autowired
    private AuthorityRoleDao authorityRoleDao;


    private final AuthorityRole roleToCreate = new AuthorityRole();
    private final AuthorityRole roleToLookup = new AuthorityRole();

    @Before
    public void setup() {
        AuthorityRoleId roleToCreateId = new AuthorityRoleId();
        roleToCreateId.setAuthority("AUTH_CREATE");
        roleToCreateId.setRole("ROLE_CREATE");
        roleToCreate.setIsActive(true);
        roleToCreate.setAuthorityRoleId(roleToCreateId);

        AuthorityRoleId roleToLookupId = new AuthorityRoleId();

        roleToLookupId.setAuthority("AUTH_LOOKUP");
        roleToLookupId.setRole("ROLE_LOOKUP");
        roleToLookup.setIsActive(true);
        roleToLookup.setAuthorityRoleId(roleToCreateId);

        authorityRoleDao.save(roleToLookup);

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
    public AuthorityRoleId entityIdToLookup() {
        return roleToLookup.getAuthorityRoleId();
    }
}