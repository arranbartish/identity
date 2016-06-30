package com.solvedbysunrise.identity.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class BeanUtilTest {

    private static final String VALUE = "VALUE";
    private static final String ANOTHER_VALUE = "ANOTHER_VALUE";

    private SomeBean beanToClone;
    private SomeBean someOtherBean;

    @Before
    public void setUp() throws Exception {
        beanToClone = new SomeBean(VALUE);
        someOtherBean = new SomeBean(ANOTHER_VALUE);
    }

    @Test
    public void cloneBean_Will_Clone_A_Bean() throws Exception {
        SomeBean clonedBean = BeanUtil.cloneBean(beanToClone);

        assertThat(clonedBean, is(beanToClone));
        assertThat(clonedBean == beanToClone, is(false));
        assertThat(clonedBean, is(not(someOtherBean)));

    }


    @Test
    public void cloneBean_Will_Return_Null_When_Given_Null() throws Exception {
        SomeBean clonedBean = BeanUtil.cloneBean(null);

        assertThat(clonedBean, is(nullValue()));
    }

    @Test(expected = RuntimeException.class)
    public void cloneBean_Will_Throw_Exception_Given_An_Illegal_Bean() throws Exception {
        BeanUtil.cloneBean(new FailureBean());
    }

    private class FailureBean {
    }

}