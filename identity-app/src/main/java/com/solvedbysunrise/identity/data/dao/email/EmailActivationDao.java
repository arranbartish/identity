package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.data.entity.jpa.email.ActivationEmail;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailActivationDao extends
        EmailDao<ActivationEmail>{

    ActivationEmail findByGuid(String guid);

    ActivationEmail findByConfirmationId(String confirmationId);

    ActivationEmail findByActivationGuid(String ActivationGuid);

}
