package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.data.entity.jpa.email.BasicEmail;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicEmailDao extends EmailDao<BasicEmail> {


//    @Override
//    public List<BasicEmail> findNumberOfUnsentEmails(Integer numberOfEmailsToFind) {
//        return findNumberOfUnsentEmailsOlderThanDate(numberOfEmailsToFind, null);
//    }
//
//    @Override
//    public List<BasicEmail> findNumberOfUnsentEmailsOlderThanDate(Integer numberOfEmailsToFind, Date olderThan) {
//        final Integer finalNumberOfEmailsToFind = checkNotNull(numberOfEmailsToFind);
//        final Date finalOlderThan = applyDefaultIfNull(olderThan, now());
//        List<BasicEmail> basicEmails = findRestrictedList(
//                "from BasicEmail be where be.sentDate is null and be.createDate <= :olderThanDate",
//                getStringArray("olderThanDate"), getObjectArray(finalOlderThan), finalNumberOfEmailsToFind);
//        if (basicEmails != null) {
//            return basicEmails;
//        } else {
//            return Lists.newArrayList();
//        }
//    }
//
//    private static <T> T applyDefaultIfNull(T original, T defaultValue ) {
//        return (original == null)? defaultValue : original;
//    }
}
