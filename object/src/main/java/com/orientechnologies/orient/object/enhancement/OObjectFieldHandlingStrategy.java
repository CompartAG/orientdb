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
public interface OObjectFieldHandlingStrategy {

    /**
     * Stores an object in a document
     * 
     * @param iRecord
     * @param fieldName
     * @param fieldValue
     * @param suggestedFieldType ignored if the type is set in the schema
     */
    ODocument store(ODocument iRecord, String fieldName, Object fieldValue, OType suggestedFieldType);

    /**
     * Retrieves a field from a document
     * 
     * @param iRecord
     * @param fieldName
     * @param suggestedFieldType ignored if the type is set in the schema
     * @return field value
     */
    Object load(ODocument iRecord, String fieldName, OType suggestedFieldType);

}