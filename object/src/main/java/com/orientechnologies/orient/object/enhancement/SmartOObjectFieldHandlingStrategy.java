/*****************************************************************************
 * Copyright (C) Compart AG, 2016 - Compart confidential
 *
 *****************************************************************************/

package com.orientechnologies.orient.object.enhancement;

import java.util.HashMap;
import java.util.Map;

import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * {@link OObjectFieldHandlingStrategy} that deals with fields (depending on their type) in a smarter way than a
 * {@link SimpleOObjectFieldHandlingStrategy}.
 * 
 * @author dta@compart.com
 */
public class SmartOObjectFieldHandlingStrategy extends SimpleOObjectFieldHandlingStrategy {

    private final Map<OType, OObjectFieldOTypeHandlingStrategy> customTypeHandlers = new HashMap<OType, OObjectFieldOTypeHandlingStrategy>();

    /**
     * Constructor
     * 
     * @param typeHandlers
     */
    public SmartOObjectFieldHandlingStrategy(Map<OType, OObjectFieldOTypeHandlingStrategy> typeHandlers) {
        this.customTypeHandlers.putAll(typeHandlers);

        // Validate the strategy mappings
        OObjectFieldOTypeHandlingStrategy currentStrategy;
        for (OType oType : this.customTypeHandlers.keySet()) {
            currentStrategy = this.customTypeHandlers.get(oType);
            if (!oType.equals(currentStrategy.getOType())) {
                throw new IllegalArgumentException(
                        "Strategy " + currentStrategy.getClass() + " can not handle fields with type: " + oType);
            }
        }
    }

    @Override
    public ODocument store(ODocument iRecord, String fieldName, Object fieldValue, OType suggestedFieldType) {

        OType fieldType = deriveFieldType(iRecord, fieldName, suggestedFieldType);

        if (fieldType == null) {
            return super.store(iRecord, fieldName, fieldValue, suggestedFieldType);
        }

        if (this.customTypeHandlers.containsKey(fieldType)) {
            return this.customTypeHandlers.get(fieldType).store(iRecord, fieldName, fieldValue);
        }

        return super.store(iRecord, fieldName, fieldValue, suggestedFieldType);
    }

    @Override
    public Object load(ODocument iRecord, String fieldName, OType suggestedFieldType) {

        OType fieldType = deriveFieldType(iRecord, fieldName, suggestedFieldType);

        if (this.customTypeHandlers.containsKey(fieldType)) {
            return this.customTypeHandlers.get(fieldType).load(iRecord, fieldName);
        }

        return super.load(iRecord, fieldName, suggestedFieldType);
    }
}
