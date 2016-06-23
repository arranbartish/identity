package com.solvedbysunrise.identity.service.identity.account;

import com.google.common.collect.Lists;
import com.receiptdrop.identity.dao.ApiKeyDao;
import com.receiptdrop.identity.domain.entity.ApiKey;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Date;

import static com.google.common.collect.Sets.newHashSet;
import static com.receiptdrop.identity.account.RandomStringApiKeyService.API_KEY_PREFIX;
import static com.receiptdrop.unit.matcher.CollectionMatchers.containsAllExact;
import static com.receiptdrop.unit.matcher.CollectionMatchers.hasSize;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RandomStringApiKeyServiceTest {

    private static final Long ACCOUNT_ID = 1L;
    private static final String API_KEY = "API_KEY";

    @Mock
    private ApiKeyDao apiKeyDao;

    @InjectMocks
    private RandomStringApiKeyService apiKeyService;

    @Mock
    private ApiKey futureExpireKey;

    @Mock
    private ApiKey nonExpiredKey;

    @Captor
    private ArgumentCaptor<Date> expiryDateCaptor;

    @Test
    public void addNewAPIKeyToAccount_Will_Return_A_Different_String_When_Called_Twice() throws Exception {
        String firstApiKey = apiKeyService.addNewAPIKeyToAccount(ACCOUNT_ID);
        String secondApiKey = apiKeyService.addNewAPIKeyToAccount(ACCOUNT_ID);
        assertThat(firstApiKey, is(not(secondApiKey)));
    }

    @Test
    public void addNewAPIKeyToAccount_Will_Return_A_Key_Starting_With_A_Prefix_When_Called() throws Exception {
        String apiKey = apiKeyService.addNewAPIKeyToAccount(ACCOUNT_ID);
        assertThat(StringUtils.startsWith(apiKey, API_KEY_PREFIX), is(true));
    }

    @Test
    public void addNewAPIKeyToAccount_Will_Return_A_Key_Greater_Than_32_Chars_When_Called() throws Exception {
        String apiKey = apiKeyService.addNewAPIKeyToAccount(ACCOUNT_ID);
        assertThat(apiKey.length()>32, is(true));
    }

    @Test
    public void getAllValidAPIKeys_Will_Return_All_Valid_Keys_When_Called() throws Exception {
        Collection<String> keys = newHashSet(API_KEY);

        when(apiKeyDao.findNonExpiredApiKeysByAccountId(ACCOUNT_ID)).thenReturn(keys);
        Collection<String> allValidAPIKeys = apiKeyService.getAllValidAPIKeys(ACCOUNT_ID);
        assertThat(allValidAPIKeys, hasSize(1));
        assertThat(allValidAPIKeys, containsAllExact(keys));
    }

    @Test
    public void addNewAPIKeyToAccountAndExpireAllNonExpiredKeysNow_Will_Return_A_New_Key_And_Expire_Old_Keys_When_Called() throws Exception {
        Collection<ApiKey> keys = Lists.newArrayList(futureExpireKey, nonExpiredKey);

        when(apiKeyDao.findNonExpiredApiKeyInstancesByAccountId(ACCOUNT_ID)).thenReturn(keys);
        when(futureExpireKey.isExpired()).thenReturn(true);
        when(futureExpireKey.getExpiryDate()).thenReturn(DateTime.now().plusDays(1).toDate());
        when(nonExpiredKey.isExpired()).thenReturn(false);
        String apiKey = apiKeyService.addNewAPIKeyToAccountAndExpireAllNonExpiredKeysNow(ACCOUNT_ID);
        DateTime timeTwoMinutesAgo = DateTime.now().minusMinutes(2);

        Mockito.verify(futureExpireKey).setExpiryDate(expiryDateCaptor.capture());
        Date firstExpiryDate = expiryDateCaptor.getValue();
        Mockito.verify(nonExpiredKey).setExpiryDate(expiryDateCaptor.capture());
        Date secondExpiryDate = expiryDateCaptor.getValue();

        assertThat(apiKey, is(notNullValue()));
        assertThat(firstExpiryDate, is(secondExpiryDate));
        assertThat(timeTwoMinutesAgo.isBefore(new DateTime(firstExpiryDate)), is(true));
        assertThat(timeTwoMinutesAgo.isBefore(new DateTime(secondExpiryDate)), is(true));

    }
}
