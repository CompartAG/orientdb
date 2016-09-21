/*****************************************************************************
 * Copyright (C) Compart AG, 2016 - Compart confidential
 *
 *****************************************************************************/

package com.orientechnologies.orient.object.enhancement;

import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Strategy handling how to store and retrieve data in documents.
 * 
 * @author dta@compart.com
 */
public interface OObjectFieldOTypeHandlingStrategy {

    /**
     * Stores an object in a document
     * 
     * @param iRecord
     * @param fieldName
     * @param fieldValue
     */
    ODocument store(ODocument iRecord, String fieldName, Object fieldValue);

    /**
     * Retrieves a field from a document
     * 
     * @param iRecord
     * @param fieldName
     * @param suggestedFieldType ignored if the type is set in the schema
     * @return field value
     */
    Object load(ODocument iRecord, String fieldName);

    /**
     * @return {@link OType} handled by the strategy.
     */
    OType getOType();
}