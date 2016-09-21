/*****************************************************************************
 * Copyright (C) Compart AG, 2016 - Compart confidential
 *
 *****************************************************************************/

package com.orientechnologies.orient.object.enhancement;

import java.util.HashMap;
import java.util.Map;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.metadata.schema.OType;

/**
 * Wraps a {@link OObjectFieldHandlingStrategy} to be accessed statically.<br/>
 * The strategy to be used is set via {@link OGlobalConfiguration#OBJECT_BINARY_MAPPING} and the possible values are
 * {@link #SIMPLE},{@link #SINGLE_ORECORD_BYTES} or {@link #SPLIT_ORECORD_BYTES}.
 * 
 * @author dta@compart.com
 */
public class OObjectFieldHandler {

    public static final int SIMPLE = 0;
    public static final int SINGLE_ORECORD_BYTES = 1;
    public static final int SPLIT_ORECORD_BYTES = 2;

    private static final OObjectFieldHandlingStrategy STRATEGY;
    static {
        Map<OType, OObjectFieldOTypeHandlingStrategy> typeHandlingStrategies = new HashMap<OType, OObjectFieldOTypeHandlingStrategy>();

        switch (OGlobalConfiguration.OBJECT_BINARY_MAPPING.getValueAsInteger()) {
        case SINGLE_ORECORD_BYTES:
            typeHandlingStrategies.put(OType.BINARY, new OObjectSingleRecordBytesOTypeHandlingStrategy());
            break;

        case SPLIT_ORECORD_BYTES:
            break;

        case SIMPLE:
        default:
            break;
        }

        STRATEGY = new SmartOObjectFieldHandlingStrategy(typeHandlingStrategies);
    }

    public static final OObjectFieldHandlingStrategy getStrategy() {
        return STRATEGY;
    }
}
