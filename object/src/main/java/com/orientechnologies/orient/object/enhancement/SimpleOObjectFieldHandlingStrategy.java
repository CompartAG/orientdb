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
    public ODocument store(ODocument iRecord, String fieldName, Object fieldValue, OType suggestedFieldType) {

        return iRecord.field(fieldName, fieldValue, deriveFieldType(iRecord, fieldName, suggestedFieldType));
    }

    @Override
    public Object load(ODocument iRecord, String fieldName, OType suggestedFieldType) {

        OType fieldType = deriveFieldType(iRecord, fieldName, suggestedFieldType);

        /*
         * For backward compatibility reasons this approach is kept (though it's kind of odd): asking a field value
         * using a suggested type which does not match the actual type of the field implies doing a cast and updating
         * the type of the field in the document.
         */
        if (iRecord.fieldType(fieldName) != fieldType) {
            iRecord.field(fieldName, iRecord.field(fieldName), fieldType);
        }

        return iRecord.field(fieldName);
    }

    /**
     * Derives the type of a field in a document.
     * 
     * @param iRecord
     * @param fieldName
     * @param requestedFieldType
     * @return derived field type
     */
    protected OType deriveFieldType(ODocument iRecord, String fieldName, OType requestedFieldType) {

        // Schema defined types can not be ignored
        if (iRecord.getSchemaClass().existsProperty(fieldName)) {
            return iRecord.getSchemaClass().getProperty(fieldName).getType();
        }

        // New type
        if (requestedFieldType != null) {
            return requestedFieldType;
        }

        // Existing type (not fixed by the schema)
        return iRecord.fieldType(fieldName);
    }
}
