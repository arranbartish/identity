package com.solvedbysunrise.identity.data.dao.email;


import com.solvedbysunrise.identity.data.entity.jpa.email.EmailProperty;
import com.solvedbysunrise.identity.data.entity.jpa.email.EmailPropertyId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailPropertyDao extends PagingAndSortingRepository<EmailProperty, EmailPropertyId> {

//    @Overriøde
//    public List<EmailProperty> getEmailPropertyListByEmailType(EmailType emailType) {
//        final EmailType finalEmailType = checkNotNull(emailType);
//        List<EmailProperty> propertyList = findList(
//                "from EmailProperty ep where ep.emailPropertyId.type = :emailType",
//                getStringArray("emailType"), getObjectArray(finalEmailType));
//
//        if (propertyList == null) {
//            return newArrayList();
//        }
//        return propertyList;
//    }
}
