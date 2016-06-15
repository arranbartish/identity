package com.solvedbysunrise.identity.data.dao.account;

import com.solvedbysunrise.identity.data.entity.jpa.user.RegisteredUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisteredUserDao extends PagingAndSortingRepository<RegisteredUser, Long> {

//    private static final Logger LOGGER = getLogger(RegisteredUserDao.class);
//
//    @Autowired
//    public RegisteredUserDao(SessionFactory sessionFactory) {
//        super(sessionFactory, RegisteredUser.class);
//    }
//
//    @Override
//    public RegisteredUser getByEmail(final String emailAddress) {
//        final String finalEmail = Preconditions.checkNotNull(emailAddress);
//        RegisteredUser user = findUnique(
//                "from RegisteredUser ru where ru.primaryEmail = :email",
//                getStringArray("email"), getObjectArray(finalEmail));
//        if (user != null) {
//            return user;
//        } else {
//            String msg = String.format("Address [%s] not found.", emailAddress);
//            LOGGER.info(msg);
//            throw new RegisteredUserNotFoundException(msg);
//        }
//    }

}
