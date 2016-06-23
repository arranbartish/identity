package com.solvedbysunrise.identity.data.dto;

import org.apache.commons.lang3.BooleanUtils;

import java.util.HashMap;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.collections.CollectionUtils.addIgnoreNull;
import static org.apache.commons.collections.MapUtils.getBoolean;
import static org.apache.commons.collections.MapUtils.getString;

public class EmailProperties extends HashMap<String, String> {

    public final static String SUBJECT = "SUBJECT";
    public final static String FROM_ADDRESS_PATTERN = "FROM_ADDRESS_PATTERN";
    public final static String FROM_ADDRESS_USER = "FROM_ADDRESS_USER";
    public final static String FROM_ADDRESS_DOMAIN = "FROM_ADDRESS_DOMAIN";

    public final static String CAMPAIGN = "CAMPAIGN";
    public final static String IS_TRACKING_ENABLED = "IS_TRACKING_ENABLED";
    public final static String IS_CLICK_TRACKING_ENABLED = "IS_CLICK_TRACKING_ENABLED";
    public final static String IS_OPEN_TRACKING_ENABLED = "IS_OPEN_TRACKING_ENABLED";

    public final static String TAG_ONE = "TAG1";
    public final static String TAG_TWO = "TAG2";
    public final static String TAG_THREE = "TAG3";

    public final static String YES = "yes";
    public final static String NO = "no";

    public String getSubject() {
        return getString(this, SUBJECT);
    }

    public String getFromAddressPattern() {
        return getString(this, FROM_ADDRESS_PATTERN);
    }

    public String getFromAddressUser() {
        return getString(this, FROM_ADDRESS_USER);
    }

    public String getFromAddressDomain() {
        return getString(this, FROM_ADDRESS_DOMAIN);
    }

    public String getCampaign() {
        return getString(this, CAMPAIGN, "registration");
    }

    public String isTrackingEnabled() {
        return BooleanUtils.toString(getBoolean(this, IS_TRACKING_ENABLED, Boolean.TRUE), YES, NO);
    }

    public String isClickTrackingEnabled() {
        return BooleanUtils.toString(getBoolean(this, IS_CLICK_TRACKING_ENABLED, Boolean.TRUE), YES, NO);
    }

    public String isOpenTrackingEnabled() {
        return BooleanUtils.toString(getBoolean(this, IS_OPEN_TRACKING_ENABLED, Boolean.TRUE), YES, NO);
    }

    public String getTagOne() {
        return getString(this, TAG_ONE);
    }

    public String getTagTwo() {
        return getString(this, TAG_TWO);
    }

    public String getTagThree() {
        return getString(this, TAG_THREE);
    }

    public Set<String> getTags() {
        Set<String> tags = newHashSet();
        addIgnoreNull(tags, getTagOne());
        addIgnoreNull(tags, getTagTwo());
        addIgnoreNull(tags, getTagThree());
        return tags;
    }
}
