/*****************************************************************************
 * Copyright (C) Compart AG, 2016 - Compart confidential
 *
 *****************************************************************************/

package com.orientechnologies.orient.object.enhancement;

/**
 * Wraps a {@link OObjectFieldHandlingStrategy} to be accessed statically.
 * 
 * @author dta
 */
public class OObjectFieldHandler {

    private static final OObjectFieldHandlingStrategy STRATEGY;
    static {
        STRATEGY = new SmartOObjectFieldHandlingStrategy();
    }

    public static final OObjectFieldHandlingStrategy getStrategy() {
        return STRATEGY;
    }
}
