package com.solvedbysunrise.identity.service.security;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class JenkinsHashTest {

    private final JenkinsHash jenkinsHash = new JenkinsHash ();

    private final TestData testCase;

    public JenkinsHashTest(final TestData testCase) {
        this.testCase = testCase;
    }

    @Parameters
    public static Collection<TestData[]> getTestCases() {
        TestData[][] testCases = new TestData [][] {
      /*0*/     {new TestData ("hello", 0L, 3070638494L)},
      /*1*/     {new TestData ("wow", 0L, 627410295L)},
      /*2*/     {new TestData (new byte[] { 0 }, 0L, 1843378377L)},
      /*3*/     {new TestData (new byte[] { (byte)255, (byte)128, 64, 1 }, 0L, 3359486273L)},
      /*4*/     {new TestData ("this is 11c", 0L, 3384459500L)},
      /*5*/     {new TestData ("this is 12ch", 0L, 313177311L)},
      /*6*/     {new TestData ("this is >12ch", 0L, 2321813933L)},
      /*7*/     {new TestData ("this is much large than 12 characters", 0L, 2771373033L)},

      /*8*/     {new TestData ("hello", 3070638494L, 1535955511L)},
      /*9*/     {new TestData ("wow", 627410295L, 320141986L)},
     /*10*/     {new TestData (new byte[] { 0 }, 1843378377L, 341630388L)},
     /*11*/     {new TestData (new byte[] { (byte)255, (byte)128, 64, 1 }, 3359486273L, 2916354366L)},
     /*12*/     {new TestData ("this is 11c", 3384459500L, 1497460513L)},
     /*13*/     {new TestData ("this is 12ch", 313177311L, 1671722359L)},
     /*14*/     {new TestData ("this is >12ch", 2321813933L, 4197822112L)},
     /*15*/   //  {new TestData ("an international character like é which should work", 0L, 1497837269L)},
     /*16*/     {new TestData ("this is much large than 12 characters", 2771373033L, 1338302094L)}
        };

        return asList(testCases);
    }

    @Test
    public void hash_Will_Return_Correct_Long_When_Given_Value(){
        final Long result = (testCase.initial == 0)?
                jenkinsHash.hash(testCase.bytes):
                jenkinsHash.hash(testCase.bytes, testCase.initial);

        String failureMessage =  format("Buffer '%s' (len %s) with initial %s expected %s but got %s",
                testCase.string,
                testCase.bytes.length,
                testCase.initial,
                testCase.expected,
                result);
        Assert.assertThat(failureMessage, result, is(testCase.expected));
    }

    private static class TestData {
        private final String string;
        private final byte[] bytes;
        private final long initial;
        private final long expected;

        public TestData (final byte[] bytes, final long initial, final long expected) {
            StringBuilder stringBuilder = new StringBuilder("byte[] { ");
            for (byte one : bytes) {
                stringBuilder.append(one + ", ");
            }
            stringBuilder.append("}");
            this.string = stringBuilder.toString();
            this.bytes = bytes;
            this.initial = initial;
            this.expected = expected;
        }
        public TestData (final String str, final long initial, final long expected) {
            this.string = str;
            this.bytes = new byte[str.length()];
            for (int charCount = 0; charCount < str.length(); charCount++) {
                this.bytes[charCount] = (byte)str.codePointAt(charCount);
            }
            this.initial = initial;
            this.expected = expected;
        }
    }
}
