package com.orientechnologies.orient.object.enhancement;

import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.record.impl.ORecordBytes;

public class OObjectSingleRecordBytesOTypeHandlingStrategy implements OObjectFieldOTypeHandlingStrategy {

    @Override
    public ODocument store(ODocument iRecord, String fieldName, Object fieldValue) {

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
    }

    @Override
    public Object load(ODocument iRecord, String fieldName) {
        ORecordBytes oRecordBytes = iRecord.field(fieldName);
        return oRecordBytes != null ? oRecordBytes.toStream() : null;
    }

    @Override
    public OType getOType() {
        return OType.BINARY;
    }
}
