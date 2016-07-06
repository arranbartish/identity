package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.data.dao.account.RegisteredEntityDao;
import com.solvedbysunrise.identity.data.dao.email.EmailActivationDao;
import com.solvedbysunrise.identity.data.dao.exception.ActivationEmailNotFoundException;
import com.solvedbysunrise.identity.data.dao.exception.DeactivationEmailNotFoundException;
import com.solvedbysunrise.identity.data.entity.jpa.account.RegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.email.ActivationEmail;
import com.solvedbysunrise.identity.service.exception.AccountCannotBeActivatedException;
import com.solvedbysunrise.identity.service.exception.AccountCannotBeDeactivatedException;
import com.solvedbysunrise.identity.service.exception.ActivationEmailCannotBeUsedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static com.solvedbysunrise.identity.data.entity.jpa.email.ActivationResultType.ACTIVATED;
import static java.lang.String.format;
import static org.joda.time.DateTime.now;

@Service
@Transactional
public class CompositeAccountManagementService implements AccountManagementService {

    private final EmailActivationDao activationEmailDao;
    private final RegisteredEntityDao registeredEntityDao;

    @Autowired
    public CompositeAccountManagementService(final EmailActivationDao activationEmailDao,
                                             final RegisteredEntityDao registeredEntityDao) {
        this.activationEmailDao = activationEmailDao;
        this.registeredEntityDao = registeredEntityDao;
    }

    @Override
    public void activateAccountWithGuid(final String activationGuid) {
        final ActivationEmail email = activationEmailDao.findByActivationGuid(activationGuid);

        final RegisteredEntity registeredEntity = registeredEntityDao.findOne(email.getEntityId());

        if(registeredEntity.isPendingActivation()
           && email.isPending()){
            Timestamp now = new Timestamp(now().getMillis());
            email.setResult(ACTIVATED);
            email.setResultDate(now);
            email.setUpdateBy(registeredEntity.getId().toString());

            registeredEntity.activate();
        } else if (registeredEntity.isNotPendingActivation()){
            throw new AccountCannotBeActivatedException(format("Could not activate account with activation guid [%s] because the account has an invalid state[%s] for activation", activationGuid, registeredEntity.getActivationState().toString()));
        } else if (email.isNotPending()) {
            throw new ActivationEmailCannotBeUsedException(format("Could not activated account with activation guid [%s] because email has invalid state[%s] for activation", activationGuid, email.getResult().toString()));
        }
    }

    @Override
    public void deactivateAccountWithGuid(String activationGuid) {
        try{
            final ActivationEmail email = activationEmailDao.findByActivationGuid(activationGuid);
            final RegisteredEntity registeredEntity = registeredEntityDao.findOne(email.getEntityId());
            if (registeredEntity.isActive()){
                throw new AccountCannotBeDeactivatedException(format("Could not deactivate account with activation guid [%s] because the account has an invalid state[%s] for deactivation", activationGuid, registeredEntity.getActivationState().toString()));
            } else {
                registeredEntity.deactivate();
            }
        } catch (ActivationEmailNotFoundException e){
            throw new DeactivationEmailNotFoundException(e);
        }

    }
}
