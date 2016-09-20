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
     * @param fieldType
     */
    void save(ODocument iRecord, String fieldName, Object fieldValue, OType fieldType);

    /**
     * Retrieve a field from a document
     * 
     * @param iRecord
     * @param fieldName
     * @param fieldType
     * @return field value
     */
    Object load(ODocument iRecord, String fieldName, OType fieldType);

}