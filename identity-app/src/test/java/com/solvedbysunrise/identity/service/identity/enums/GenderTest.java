package com.solvedbysunrise.identity.service.identity.enums;

import org.junit.Test;

import static com.receiptdrop.identity.enums.Gender.*;
import static org.junit.Assert.assertThat;

public class GenderTest {

    @Test
    public void fromValue_Will_Return_Values_When_Provided_Valid_Options() throws Exception {

        Gender male = fromValue("M");
        assertThat(male, is(MALE));
        Gender female = fromValue("F");
        assertThat(female, is(FEMALE));
    }

    @Test
    public void fromValue_Will_Return_Male_When_Null() throws Exception {

        Gender male = fromValue(null);
        assertThat(male, is(MALE));
    }

    @Test
    public void fromValue_Will_Return_Male_When_Blank() throws Exception {

        Gender male = fromValue("  ");
        assertThat(male, is(MALE));
    }

    @Test
    public void fromValue_Will_Return_Male_When_Unknown() throws Exception {

        Gender male = fromValue("It's life jim but not as we know it");
        assertThat(male, is(MALE));
    }
}
