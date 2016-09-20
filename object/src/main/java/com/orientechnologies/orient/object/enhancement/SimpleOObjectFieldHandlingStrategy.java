/*****************************************************************************
 * Copyright (C) Compart AG, 2016 - Compart confidential
 *
 *****************************************************************************/

package com.orientechnologies.orient.object.enhancement;

import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * @author dta@compart.com
 */
public class SimpleOObjectFieldHandlingStrategy implements OObjectFieldHandlingStrategy {

    @Override
    public void save(ODocument iRecord, String fieldName, Object fieldValue, OType fieldType) {
        iRecord.field(fieldName, fieldValue, fieldType);
    }

    @Override
    public Object load(ODocument iRecord, String fieldName, OType fieldType) {
        return null;
    }
}
