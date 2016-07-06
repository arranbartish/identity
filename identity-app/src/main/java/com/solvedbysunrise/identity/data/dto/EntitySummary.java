package com.solvedbysunrise.identity.data.dto;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Locale;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class EntitySummary {

    private final String primaryContactEmail;
    private final Long entityId;
    private final Locale locale;

    private EntitySummary(String primaryContactEmail, Long entityId, Locale locale) {
        this.primaryContactEmail = primaryContactEmail;
        this.entityId = entityId;
        this.locale = locale;
    }

    public static EntitySummary createEntitySummary(final String primaryContactEmail, final Long entityId, final Locale locale) {
        checkArgument(isNotBlank(primaryContactEmail), format("Primary email [%s] can not be blank", primaryContactEmail));
        checkArgument(entityId > 0, format("invalid account id [%s]", entityId));
        return new EntitySummary(primaryContactEmail, entityId, checkNotNull(locale, "locale not provided"));
    }

    public Locale getLocale() {
        return locale;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getPrimaryContactEmail() {
        return primaryContactEmail;
    }
}
