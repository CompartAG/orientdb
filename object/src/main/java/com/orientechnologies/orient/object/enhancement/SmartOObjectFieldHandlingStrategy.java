/*****************************************************************************
 * Copyright (C) Compart AG, 2016 - Compart confidential
 *
 *****************************************************************************/

package com.orientechnologies.orient.object.enhancement;

import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.record.impl.ORecordBytes;

/**
 * {@link OObjectFieldHandlingStrategy} that deals with fields (depending on their type) in a smarter way than a
 * {@link SimpleOObjectFieldHandlingStrategy}.
 * 
 * @author dta@compart.com
 */
public class SmartOObjectFieldHandlingStrategy extends SimpleOObjectFieldHandlingStrategy {

    @Override
    public ODocument store(ODocument iRecord, String fieldName, Object fieldValue, OType suggestedFieldType) {

        OType fieldType = deriveFieldType(iRecord, fieldName, suggestedFieldType);

        if (fieldType == null) {
            return super.store(iRecord, fieldName, fieldValue, suggestedFieldType);
        }

        switch (fieldType) {
        case BINARY:
            // Binary data optimization: http://orientdb.com/docs/2.2/Binary-Data.html
            byte[] bytes = fieldValue != null ? (byte[]) fieldValue : null;
            ORecordBytes recordBytes;
            if ((recordBytes = iRecord.field(fieldName)) == null) {
                // No data yet
                recordBytes = new ORecordBytes();
                iRecord.field(fieldName, recordBytes);
            } else {
                // There's already a document storing some binary data
                recordBytes.clear();
            }

            if (bytes != null) {
                recordBytes.fromStream(bytes);
            }

            return iRecord;

        default:
            return super.store(iRecord, fieldName, fieldValue, suggestedFieldType);
        }
    }

    @Override
    public Object load(ODocument iRecord, String fieldName, OType suggestedFieldType) {

        OType fieldType = deriveFieldType(iRecord, fieldName, suggestedFieldType);

        if (OType.BINARY.equals(fieldType)) {
            ORecordBytes oRecordBytes = iRecord.field(fieldName);
            return oRecordBytes != null ? oRecordBytes.toStream() : null;
        }

        return super.load(iRecord, fieldName, suggestedFieldType);
    }
}
