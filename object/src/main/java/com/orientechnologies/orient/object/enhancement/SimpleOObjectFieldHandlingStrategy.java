/*****************************************************************************
 * Copyright (C) Compart AG, 2016 - Compart confidential
 *
 *****************************************************************************/

package com.orientechnologies.orient.object.enhancement;

import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * {@link OObjectFieldHandlingStrategy} that delegates to the default {@link ODocument#field(String)} implementation.
 * 
 * @author dta@compart.com
 */
public class SimpleOObjectFieldHandlingStrategy implements OObjectFieldHandlingStrategy {

    @Override
    public ODocument store(ODocument iRecord, String fieldName, Object fieldValue, OType fieldType) {

        if (iRecord.getSchemaClass().existsProperty(fieldName)) {
            return iRecord.field(fieldName, fieldValue);
        }
        
        return iRecord.field(fieldName, fieldValue, fieldType);
    }

    @Override
    public Object load(ODocument iRecord, String fieldName, OType fieldType) {

        if (iRecord.fieldType(fieldName) != fieldType) {
            iRecord.field(fieldName, iRecord.field(fieldName), fieldType);
        }

        return iRecord.field(fieldName);
    }
}
