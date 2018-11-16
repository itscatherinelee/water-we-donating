package edu.gatech.cs2340.waterwedonating;
import org.junit.Before ;
import org.junit.Test ;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Naim's javadoc for testing RegistrationActivity.register() method
 */
public class naimJunitTest {
    private RegistrationActivity rA;
    ArrayList<String> lists;
    @Before
    public void setUp() {
        rA = new RegistrationActivity();
    }
    @Test
    public void testTrue() {
        assertTrue(rA.register("sdjfsjfhsd","dsjchjdshcjsd"));
    }
    @Test
    public void testFalse() {
        assertFalse("The parameters are null", rA.register("",""));
    }

 }
