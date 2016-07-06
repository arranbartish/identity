package com.solvedbysunrise.identity.data.dao.email;

import com.solvedbysunrise.identity.data.entity.jpa.email.PasswordResetResultType;
import com.solvedbysunrise.identity.data.entity.jpa.email.ResetPasswordEmail;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ResetPasswordEmailDao extends
        EmailDao<ResetPasswordEmail> {

    ResetPasswordEmail findByGuid(String guid);

    ResetPasswordEmail findByConfirmationId(String confirmationId);

    ResetPasswordEmail findByResetPasswordGuid(String resetPasswordGuid);

    Collection<ResetPasswordEmail> findByToAddressAndResult(String toAddress, PasswordResetResultType result);
}
