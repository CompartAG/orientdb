package com.orientechnologies.orient.object.enhancement;

import java.io.IOException;

import javax.persistence.Basic;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * @author diegomtassis <a href="mailto:dta@compart.com">Diego Martin Tassis</a>
 */
public class OObjectBinaryDataStorageTest {

    private OObjectDatabaseTx databaseTx;

    @Before
    public void setUp() throws Exception {
        databaseTx = new OObjectDatabaseTx("memory:" + this.getClass().getSimpleName());
        databaseTx.create();

        databaseTx.getEntityManager().registerEntityClass(ExactEntity.class);
    }

    @After
    public void tearDown() {
        databaseTx.drop();
    }

    @Test
    public void testSaveAndLoad_BinaryFieldsSimpleRecordMapping() throws IOException {

        // setup
        OGlobalConfiguration.OBJECT_BINARY_MAPPING.setValue(OObjectFieldHandler.SIMPLE);

        Driver hunt = new Driver();
        hunt.name = "James Hunt";
        hunt.nationality = "british";
        hunt.randomData = "qrtonvpeoitunbpweoirutvnüpoeqirüw".getBytes();

        // exercise
        Driver savedHunt = this.databaseTx.save(hunt);
        Driver loadedHunt = this.databaseTx.load(savedHunt.id);

        // verify
        Assert.assertNotNull(savedHunt);
        Assert.assertNotNull(loadedHunt);
        Assert.assertArrayEquals(savedHunt.randomData, hunt.randomData);
        Assert.assertArrayEquals(loadedHunt.randomData, hunt.randomData);
    }

    @Test
    public void testSaveAndLoad_BinaryFieldsSingleRecordMapping() throws IOException {

        // setup
        OGlobalConfiguration.OBJECT_BINARY_MAPPING.setValue(OObjectFieldHandler.SINGLE_ORECORD_BYTES);

        Driver lauda = new Driver();
        lauda.name = "Niki Lauda";
        lauda.nationality = "austrian";
        lauda.randomData = "qelicuhewiryvqbewiournqcowuiepoxehqhew94387213bc47456cb0987".getBytes();

        // exercise
        Driver savedLauda = this.databaseTx.save(lauda);
        Driver loadedLauda = this.databaseTx.load(savedLauda.id);

        // verify
        Assert.assertNotNull(savedLauda);
        Assert.assertNotNull(loadedLauda);
        Assert.assertArrayEquals(savedLauda.randomData, lauda.randomData);
        Assert.assertArrayEquals(loadedLauda.randomData, lauda.randomData);
    }

    @Test
    public void testSaveAndLoad_BinaryFieldsSplitRecordMapping() throws IOException {

        // setup
        OGlobalConfiguration.OBJECT_BINARY_MAPPING.setValue(OObjectFieldHandler.SPLIT_ORECORD_BYTES);

        Driver prost = new Driver();
        prost.name = "Alain Prost";
        prost.nationality = "french";
        prost.randomData = "19348750193847b0v983by4tiwuervbc34crc234098cynxfwef".getBytes();

        // exercise
        Driver savedProst = this.databaseTx.save(prost);
        Driver loadedProst = this.databaseTx.load(savedProst.id);

        // verify
        Assert.assertNotNull(savedProst);
        Assert.assertNotNull(loadedProst);
        Assert.assertArrayEquals(savedProst.randomData, prost.randomData);
        Assert.assertArrayEquals(loadedProst.randomData, prost.randomData);
    }

    public static class Driver {

        @Id
        public String id;

        @Basic
        public String name;

        @Basic
        public String nationality;

        @Basic
        public String team;

        @Basic
        @Lob
        public byte[] randomData;
    }
}
