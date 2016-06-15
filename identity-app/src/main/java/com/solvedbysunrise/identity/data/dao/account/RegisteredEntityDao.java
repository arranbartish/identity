package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredEntityDao extends PagingAndSortingRepository<BasicRegisteredEntity, Long> {


//    @Autowired
//    public RegisteredEntityDao(SessionFactory sessionFactory) {
//        super(sessionFactory, BasicRegisteredEntity.class);
//    }
//
//    @Override
//    public List<BasicRegisteredEntity> findAllOutOfDateEntitiesWithoutAnOutStandingPasswordResetRequest(String specificPasswordVersion) {
//        String query = "select re from BasicRegisteredEntity as re, ResetPasswordEmail as rpe " +
//                "where re.id != rpe.entityId and rpe.result = :result " +
//                "and re.passwordVersion != :passwordVersion";
//        final List<BasicRegisteredEntity> result = findList(query,
//                getStringArray("result", "passwordVersion"),
//                getObjectArray(PENDING, specificPasswordVersion));
//        return (result != null)? result:emptyList;
//    }
//
//    @Override
//    public BasicRegisteredEntity getByUsername(final String username) {
//        final String finalUsername = Preconditions.checkNotNull(username);
//        BasicRegisteredEntity entity = findUnique(
//                "from BasicRegisteredEntity re where re.username = :username",
//                getStringArray("username"), getObjectArray(finalUsername));
//        if (entity != null) {
//            return entity;
//        } else {
//            String msg = String.format("Username [%s] not found.", finalUsername);
//            LOGGER.info(msg);
//            throw new RegisteredUserNotFoundException(msg);
//        }
//    }
//
//    @Override
//    public Collection<String> findAllRolesForAccount(Long accountId) {
//        String hql = "select rer.roleName from RegisteredEntityRole as rer where rer.registeredEntity.id = :accountId";
//        return findRestrictedList(hql, new String[]{"accountId"}, new Object[]{accountId}, 0, String.class);
//    }
}
