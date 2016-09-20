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
    public ODocument store(ODocument iRecord, String fieldName, Object fieldValue, OType fieldType) {

        if (OType.BINARY.equals(fieldType)) {
            /*
             * Binary data optimization: http://orientdb.com/docs/2.2/Binary-Data.html
             */
            byte[] bytes = (byte[]) fieldValue;
            return iRecord.field(fieldName, new ORecordBytes(bytes));
        }

        return super.store(iRecord, fieldName, fieldValue, fieldType);
    }

    @Override
    public Object load(ODocument iRecord, String fieldName, OType fieldType) {

        // Deal with binary data

        return super.load(iRecord, fieldName, fieldType);
    }
}
