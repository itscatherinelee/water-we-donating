package edu.gatech.cs2340.waterwedonating;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;


/**testing
 * public List read() {
 *
 * }
 * in LocationReader.java
 */

public class SeonghunJUnitTest {

    private InputStream inputStream = new ByteArrayInputStream( "1,2,3,4,5,6".getBytes());
    private String[] expected;


    /**
     *
     * Method: read()
     *
     */
    @Test
    public void testRead(){

        LocationReader locationReader = new LocationReader(inputStream);
        ArrayList dataList = (ArrayList) locationReader.read();
        String[] dataArray = (String[]) dataList.get(0);

        assertNotNull(dataList);
        assertEquals( 1,dataList.size());
        assertEquals(6, dataArray.length);

        expected = new String[6];

        for (int i = 0; i < 6; i++) {
            expected[i] = Integer.toString(i + 1);
        }

        assertEquals(expected, dataArray);
    }


}