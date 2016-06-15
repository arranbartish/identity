package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.data.entity.jpa.email.ActivationEmail;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailActivationDao extends
        EmailDao<ActivationEmail>{

//    final static Logger LOGGER = LoggerFactory.getLogger(EmailActivationDao.class);
//
//    @Autowired
//    public EmailActivationDao(SessionFactory sessionFactory) {
//        super(sessionFactory, ActivationEmail.class);
//    }
//
//    @Override
//    public ActivationEmail getByActivationGuid(String activationGuid) {
//        final String finalActivationGuid = Preconditions.checkNotNull(activationGuid);
//        ActivationEmail activationEmail = findUnique(
//                "from ActivationEmail ea where ea.activationGuid = :activationGuid",
//                getStringArray("activationGuid"), getObjectArray(finalActivationGuid));
//        if (activationEmail != null) {
//            return activationEmail;
//        } else {
//            String msg = String.format("Activation guid [%s] not found.", finalActivationGuid);
//            LOGGER.info(msg);
//            throw new ActivationEmailNotFoundException(msg);
//        }
//    }
}
