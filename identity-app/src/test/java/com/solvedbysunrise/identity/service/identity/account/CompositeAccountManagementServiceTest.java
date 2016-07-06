package com.solvedbysunrise.identity.service.identity.account;

import com.solvedbysunrise.identity.data.dao.account.ActivationState;
import com.solvedbysunrise.identity.data.dao.account.RegisteredEntityDao;
import com.solvedbysunrise.identity.data.dao.email.EmailActivationDao;
import com.solvedbysunrise.identity.data.dao.exception.ActivationEmailNotFoundException;
import com.solvedbysunrise.identity.data.dao.exception.DeactivationEmailNotFoundException;
import com.solvedbysunrise.identity.data.entity.jpa.account.BasicRegisteredEntity;
import com.solvedbysunrise.identity.data.entity.jpa.email.ActivationEmail;
import com.solvedbysunrise.identity.data.entity.jpa.email.ActivationResultType;
import com.solvedbysunrise.identity.service.CompositeAccountManagementService;
import com.solvedbysunrise.identity.service.exception.AccountCannotBeActivatedException;
import com.solvedbysunrise.identity.service.exception.AccountCannotBeDeactivatedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Timestamp;

import static com.solvedbysunrise.identity.data.dao.account.ActivationState.PENDING_ACTIVATION;
import static com.solvedbysunrise.identity.data.entity.jpa.email.ActivationResultType.ACTIVATED;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompositeAccountManagementServiceTest {

    private static final String GUID = "GUID";
    private static final Long ACCOUNT_ID = 1L;

    @Mock
    private EmailActivationDao activationEmailDao;

    @Mock
    private RegisteredEntityDao registeredEntityDao;

    @InjectMocks
    private CompositeAccountManagementService compositeAccountManagementService;

    @Mock
    private ActivationEmail activationEmail;

    @Mock
    private BasicRegisteredEntity registeredEntity;

    @Before
    public void setup() {
        when(activationEmailDao.findByActivationGuid(GUID)).thenReturn(activationEmail);
        when(registeredEntityDao.findOne(anyLong())).thenReturn(registeredEntity);
        when(activationEmail.getEntityId()).thenReturn(ACCOUNT_ID);
    }

    @Test
    public void activateAccountWithGuid_Will_Activate_An_Account_When_Provided_With_A_Guid() {

        setupMockedDtoForHappyResult();
        compositeAccountManagementService.activateAccountWithGuid(GUID);

        verify(activationEmail).setResult(ACTIVATED);
        verify(activationEmail).setResultDate(any(Timestamp.class));
        verify(activationEmail).setUpdateBy(ACCOUNT_ID.toString());
        verify(registeredEntity).activate();
    }


    @Test(expected = AccountCannotBeActivatedException.class)
    public void activateAccountWithGuid_Will_Throw_Exception_When_Account_Is_Activated() {
        setupMockedDtoForFailureBecauseEntityIsAlreadyActive();

        compositeAccountManagementService.activateAccountWithGuid(GUID);
    }


    @Test(expected = DeactivationEmailNotFoundException.class)
    public void testDeactivateAccountWithGuid_Will_Throw_Exception_When_GUID_Is_Invalid() {
        when(activationEmailDao.findByActivationGuid(GUID)).thenThrow(new ActivationEmailNotFoundException("mock exception"));
        compositeAccountManagementService.deactivateAccountWithGuid(GUID);
        verify(activationEmailDao).findByActivationGuid(GUID);
        verify(registeredEntityDao, times(0)).findOne(anyLong());
        verify(registeredEntity, times(0)).getActivationState();
        verify(registeredEntity, times(0)).isActive();
        verify(registeredEntity, times(0)).deactivate();
    }

    @Test(expected = AccountCannotBeDeactivatedException.class)
    public void testDeactivateAccountWithGuid_Will_Throw_Exception_When_Account_Already_Active() {
        when(registeredEntity.isActive()).thenReturn(true);
        when(registeredEntity.getActivationState()).thenReturn(ActivationState.ACTIVATED);
        compositeAccountManagementService.deactivateAccountWithGuid(GUID);
        verify(activationEmailDao).findByActivationGuid(GUID);
        verify(registeredEntityDao).findOne(anyLong());
        verify(registeredEntity).getActivationState();
        verify(registeredEntity).isActive();
        verify(registeredEntity, times(0)).deactivate();
    }

    @Test
    public void testDeactivateAccountWithGuid_Will_Deactivate_Account_When_Success() {
        when(registeredEntity.isActive()).thenReturn(false);
        when(registeredEntity.getActivationState()).thenReturn(PENDING_ACTIVATION);
        compositeAccountManagementService.deactivateAccountWithGuid(GUID);
        verify(activationEmailDao).findByActivationGuid(GUID);
        verify(registeredEntityDao).findOne(anyLong());
        verify(registeredEntity).isActive();
        verify(registeredEntity).deactivate();
    }


    private void setupMockedDtoForHappyResult() {
        when(registeredEntity.isPendingActivation()).thenReturn(true);
        when(activationEmail.isPending()).thenReturn(true);
        when(registeredEntity.getId()).thenReturn(ACCOUNT_ID);
    }

    private void setupMockedDtoForFailureBecauseEmailIsExpired() {
        when(registeredEntity.isPendingActivation()).thenReturn(true);
        when(registeredEntity.isNotPendingActivation()).thenReturn(false);
        when(activationEmail.isPending()).thenReturn(false);
        when(activationEmail.isNotPending()).thenReturn(true);
        when(activationEmail.getResult()).thenReturn(ActivationResultType.TIMED_OUT);

    }

    private void setupMockedDtoForFailureBecauseEntityIsAlreadyActive() {
        when(registeredEntity.isPendingActivation()).thenReturn(false);
        when(registeredEntity.isNotPendingActivation()).thenReturn(true);
        when(registeredEntity.getActivationState()).thenReturn(ActivationState.ACTIVATED);
        when(activationEmail.isPending()).thenReturn(false);
        when(activationEmail.isNotPending()).thenReturn(true);
    }


}
