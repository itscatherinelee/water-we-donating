package edu.gatech.cs2340.waterwedonating;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Junit test for constructor of location reader.
 */
public class EvaJUnitTest {

    private InputStream testingInput = null;
    private InputStream validInput = new ByteArrayInputStream("A,B,C".getBytes());

    @Test
    public void locationReaderTest() {

        try {

            LocationReader locationReader = new LocationReader(testingInput);

        } catch (IllegalArgumentException e) {

            assertEquals(e.getMessage(), "inputStream cannot be null.");
        }


    }

    @Test (expected = IllegalArgumentException.class)
    public void nullInputStream() {

        LocationReader locationReader = new LocationReader(null);

       ArrayList arrayList = (ArrayList) locationReader.read();

       assertTrue(arrayList.size() > 0);

    }

    @Test
    public void validInputStream() {

        LocationReader locationReader = new LocationReader(validInput);

        assertNotNull(locationReader.inputStream);
        assertSame(locationReader.inputStream, validInput);
    }
}
