package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.data.entity.jpa.user.AuthorityRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRoleDao extends CrudRepository<AuthorityRole, Long> {

//    @Override
//    public Set<AuthorityRole> findAuthoritiesByRole(final String... roles) {
//        if (isEmpty(roles)) {
//            return ImmutableSet.of();
//        }
//
//        Query query = getSessionFactory()
//                .getCurrentSession()
//                .createQuery(
//                        "from AuthorityRole ar where ar.role in (:roles) and ar.isActive = 'T'");
//        query.setParameterList("roles", roles);
//
//        @SuppressWarnings("unchecked")
//        List<AuthorityRole> authorities = query.list();
//
//        if (isEmpty(authorities)) {
//            LOGGER.info(String.format("No authorities found for roles [%s] ",
//                    (Object[]) roles));
//        }
//
//        return copyOf(authorities);
//    }

}
