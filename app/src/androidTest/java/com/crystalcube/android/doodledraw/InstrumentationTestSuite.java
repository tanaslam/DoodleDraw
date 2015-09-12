package com.crystalcube.android.doodledraw;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.TestSuite;

/**
 * <p>
 * Main test suite to run instrumentation tests
 * </p>
 *
 * @author tanny
 *         Created: 11/09/15.
 */
public class InstrumentationTestSuite {

    public static TestSuite suite() {
        return new TestSuiteBuilder(InstrumentationTestSuite.class)
                .includeAllPackagesUnderHere().build();
    }
}
