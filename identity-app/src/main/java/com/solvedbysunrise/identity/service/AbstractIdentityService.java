package com.solvedbysunrise.identity.service;

import com.solvedbysunrise.identity.service.exception.CloneFailure;

import static com.solvedbysunrise.identity.util.BeanUtil.cloneBean;
import static java.lang.String.format;

public abstract class AbstractIdentityService {

	
	protected static <T> T cloneBeanInternal(T bean) {
		T clone = null;
		try {
			clone = cloneBean(bean);
		} catch (Exception e) {
			throw new CloneFailure(format("Failed to clone bean[%s]", bean), e);
		}
		return clone;
		
	}
}
