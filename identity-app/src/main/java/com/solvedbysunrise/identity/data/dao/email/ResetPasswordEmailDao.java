package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.data.entity.jpa.email.ResetPasswordEmail;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordEmailDao extends
        EmailDao<ResetPasswordEmail> {

//    final static Logger LOGGER = LoggerFactory.getLogger(ResetPasswordEmailDao.class);
//
//    private final List<ResetPasswordEmail> emptyList = newArrayList();
//
//    @Autowired
//    public ResetPasswordEmailDao(final SessionFactory sessionFactory) {
//        super(sessionFactory, ResetPasswordEmail.class);
//    }
//
//    @Override
//    public ResetPasswordEmail getByResetPasswordGuid(final String resetPasswordGuid) {
//        final String finalResetPasswordGuid = Preconditions.checkNotNull(resetPasswordGuid);
//        ResetPasswordEmail resetPasswordEmail = findUnique(
//                "from ResetPasswordEmail rpe where rpe.resetPasswordGuid = :guid",
//                getStringArray("guid"), getObjectArray(finalResetPasswordGuid));
//        if (resetPasswordEmail != null) {
//            return resetPasswordEmail;
//        } else {
//            String msg = String.format("Email guid [%s] not found.", resetPasswordGuid);
//            LOGGER.info(msg);
//            throw new ActivationEmailNotFoundException(msg);
//        }
//    }
//
//    @Override
//    public List<ResetPasswordEmail> getAllUnexpiredResetPasswordEmails(final String toAddress) {
//        final String finalToAddress = Preconditions.checkNotNull(toAddress);
//        List<ResetPasswordEmail> emailList = findList("from ResetPasswordEmail rpe where rpe.toAddress = :toAddress and rpe.result = :result",
//                getStringArray("toAddress", "result"), getObjectArray(finalToAddress, PENDING));
//        return (emailList != null)? emailList: emptyList;
//    }
}
