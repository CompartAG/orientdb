package com.orientechnologies.orient.object.enhancement;

import java.io.IOException;
import java.util.Random;

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
        byte[] huntUglyPicture = randomBytes(1024);
        hunt.setImageData(huntUglyPicture);

        // exercise
        Driver savedHunt = this.databaseTx.save(hunt);
        Driver loadedHunt = this.databaseTx.load(new ORecordId(savedHunt.getId()));

        // verify
        Assert.assertNotNull(savedHunt);
        Assert.assertNotNull(loadedHunt);
        Assert.assertArrayEquals(huntUglyPicture, hunt.getImageData());
        Assert.assertArrayEquals(huntUglyPicture, savedHunt.getImageData());
        Assert.assertArrayEquals(huntUglyPicture, loadedHunt.getImageData());
    }

    @Test
    public void testSaveAndLoad_BinaryFieldsSingleRecordMapping() throws IOException {

        // setup
        OGlobalConfiguration.OBJECT_BINARY_MAPPING.setValue(OObjectFieldHandler.SINGLE_ORECORD_BYTES);

        Driver lauda = new Driver();
        lauda.setName("Niki Lauda");
        byte[] laudaRealisticPicture = randomBytes(4096);
        lauda.setImageData(laudaRealisticPicture);

        // exercise
        Driver savedLauda = this.databaseTx.save(lauda);
        Driver loadedLauda = this.databaseTx.load(new ORecordId(savedLauda.getId()));

        // verify
        Assert.assertNotNull(savedLauda);
        Assert.assertNotNull(loadedLauda);
        Assert.assertArrayEquals(laudaRealisticPicture, lauda.getImageData());
        Assert.assertArrayEquals(laudaRealisticPicture, savedLauda.getImageData());
        Assert.assertArrayEquals(laudaRealisticPicture, loadedLauda.getImageData());
    }

    @Test
    public void testSaveAndLoad_BinaryFieldsSplitRecordMapping() throws IOException {

        // setup
        OGlobalConfiguration.OBJECT_BINARY_MAPPING.setValue(OObjectFieldHandler.SPLIT_ORECORD_BYTES);

        Driver prost = new Driver();
        prost.setName("Alain Prost");
        byte[] prostUglyPicture = randomBytes(4096);
        prost.setImageData(prostUglyPicture);

        // exercise
        Driver savedProst = this.databaseTx.save(prost);
        Driver loadedProst = this.databaseTx.load(new ORecordId(savedProst.getId()));

        // verify
        Assert.assertNotNull(savedProst);
        Assert.assertNotNull(loadedProst);
        Assert.assertArrayEquals(prostUglyPicture, prost.getImageData());
        Assert.assertArrayEquals(prostUglyPicture, savedProst.getImageData());
        Assert.assertArrayEquals(prostUglyPicture, loadedProst.getImageData());
    }

    private byte[] randomBytes(int size) {

        byte[] b = new byte[size];
        new Random().nextBytes(b);

        return b;
    }
}
