package com.orientechnologies.orient.object.enhancement;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.orientechnologies.orient.core.config.OGlobalConfiguration;
import com.orientechnologies.orient.core.id.ORecordId;
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

        databaseTx.getEntityManager().registerEntityClass(Driver.class);
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
        hunt.setName("James Hunt");
        hunt.setImageData("qrtonvpeoitunbpweoirutvnüpoeqirüw".getBytes());

        // exercise
        Driver savedHunt = this.databaseTx.save(hunt);
        Driver loadedHunt = this.databaseTx.load(new ORecordId(savedHunt.getId()));

        // verify
        Assert.assertNotNull(savedHunt);
        Assert.assertNotNull(loadedHunt);
        Assert.assertArrayEquals(savedHunt.getImageData(), hunt.getImageData());
        Assert.assertArrayEquals(loadedHunt.getImageData(), hunt.getImageData());
    }

    @Test
    public void testSaveAndLoad_BinaryFieldsSingleRecordMapping() throws IOException {

        // setup
        OGlobalConfiguration.OBJECT_BINARY_MAPPING.setValue(OObjectFieldHandler.SINGLE_ORECORD_BYTES);

        Driver lauda = new Driver();
        lauda.setName("Niki Lauda");
        lauda.setImageData("qelicuhewiryvqbewiournqcowuiepoxehqhew94387213bc47456cb0987".getBytes());

        // exercise
        Driver savedLauda = this.databaseTx.save(lauda);
        Driver loadedLauda = this.databaseTx.load(new ORecordId(savedLauda.getId()));

        // verify
        Assert.assertNotNull(savedLauda);
        Assert.assertNotNull(loadedLauda);
        Assert.assertArrayEquals(savedLauda.getImageData(), lauda.getImageData());
        Assert.assertArrayEquals(loadedLauda.getImageData(), lauda.getImageData());
    }

    @Test
    public void testSaveAndLoad_BinaryFieldsSplitRecordMapping() throws IOException {

        // setup
        OGlobalConfiguration.OBJECT_BINARY_MAPPING.setValue(OObjectFieldHandler.SPLIT_ORECORD_BYTES);

        Driver prost = new Driver();
        prost.setName("Alain Prost");
        prost.setImageData("19348750193847b0v983by4tiwuervbc34crc234098cynxfwef".getBytes());

        // exercise
        Driver savedProst = this.databaseTx.save(prost);
        Driver loadedProst = this.databaseTx.load(new ORecordId(savedProst.getId()));

        // verify
        Assert.assertNotNull(savedProst);
        Assert.assertNotNull(loadedProst);
        Assert.assertArrayEquals(savedProst.getImageData(), prost.getImageData());
        Assert.assertArrayEquals(loadedProst.getImageData(), prost.getImageData());
    }
}
