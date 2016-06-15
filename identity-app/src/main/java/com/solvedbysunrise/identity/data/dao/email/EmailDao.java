package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.data.entity.jpa.email.Email;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailDao<T extends Email>  extends PagingAndSortingRepository<T, Long> {

//    private static final Logger LOGGER = getLogger(EmailDao.class);
//    private final String shortClassName;
//    private final String getEmailByGuid;
//    private final String getEmailByConfirmationId;
//
//    protected EmailDao(SessionFactory sessionFactory, Class<T> targetType) {
//        super(sessionFactory, targetType);
//        shortClassName = getShortClassName(targetType);
//        getEmailByGuid = format("from %s email where email.guid = :guid", shortClassName);
//        getEmailByConfirmationId = format("from %s email where email.confirmationId = :confirmationId", shortClassName);
//    }
//
//    public T getByEmailGuid(final String emailGuid) {
//        return getByQuery(getEmailByGuid, "guid", emailGuid);
//    }
//
//    public T getByEmailConfirmationId(String emailConfirmationId) {
//        return getByQuery(getEmailByConfirmationId, "confirmationId", emailConfirmationId);
//    }
//
//    private T getByQuery(final String query, final String placeholderName, final String identifier) {
//        final String finalIdentifier = checkNotNull(identifier);
//        T email = findUnique(
//                query,
//                getStringArray(placeholderName), getObjectArray(finalIdentifier));
//        if (email != null) {
//            return email;
//        } else {
//            String msg = format("Email %s [%s] not found.", placeholderName, finalIdentifier);
//            LOGGER.info(msg);
//            throw new EmailNotFoundException(msg);
//        }
//    }

}
