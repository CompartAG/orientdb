/*****************************************************************************
 * Copyright (C) Compart AG, 2016 - Compart confidential
 *
 *****************************************************************************/

package com.orientechnologies.orient.object.enhancement;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;

/**
 * Wraps a {@link OObjectFieldHandlingStrategy} to be accessed statically.<br/>
 * The strategy to be used is set via {@link OGlobalConfiguration#OBJECT_BINARY_MAPPING} and the possible values are
 * {@link #SIMPLE},{@link #SINGLE_ORECORD_BYTES} or {@link #SPLIT_ORECORD_BYTES}.
 * 
 * @author dta
 */
public class OObjectFieldHandler {

    public static final int SIMPLE = 0;
    public static final int SINGLE_ORECORD_BYTES = 1;
    public static final int SPLIT_ORECORD_BYTES = 2;

    private static final OObjectFieldHandlingStrategy STRATEGY;
    static {
        switch (OGlobalConfiguration.OBJECT_BINARY_MAPPING.getValueAsInteger()) {
        case SINGLE_ORECORD_BYTES:
            STRATEGY = new SmartOObjectFieldHandlingStrategy();
            break;
        case 2:
        case 0:
        default:
            STRATEGY = new SimpleOObjectFieldHandlingStrategy();
            break;
        }
    }

    public static final OObjectFieldHandlingStrategy getStrategy() {
        return STRATEGY;
    }
}
