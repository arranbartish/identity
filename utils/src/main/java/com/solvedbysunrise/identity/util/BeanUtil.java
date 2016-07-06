package com.solvedbysunrise.identity.util;

import org.apache.commons.beanutils.BeanUtils;

import static java.lang.String.format;

public final class BeanUtil {

    @SuppressWarnings("unchecked")
    public static <T> T cloneBean(T bean) {

        if(bean == null){
            return null;
        }

        T clone = null;

        try {
            clone = (T) BeanUtils.cloneBean(bean);
            BeanUtils.copyProperties(clone, bean);
        } catch (Exception e) {
            throw new RuntimeException(format("Failed to clone bean [%s]", bean.toString()), e);
        }
        return clone;
    }
}
