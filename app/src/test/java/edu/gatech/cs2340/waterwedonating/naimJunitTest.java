package edu.gatech.cs2340.waterwedonating;
import org.junit.Before ;
import org.junit.Test ;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Naim's javadoc for testing RegistrationActivity.LoginUser() method
 */
public class naimJunitTest {
    public LoginActivity rA;
    @Before
    public void setUp() {
        rA = new LoginActivity();
    }
    @Test
    public void testTrue() {
        assertTrue(rA.LoginUser("ewhdhwdkjwd","erfjekfmewf"));
    }
    @Test
    public void testFalse() {
        assertFalse(rA.LoginUser("",""));
    }
    @Test
    public void checkEqual() {
        assertEquals(false, rA.LoginUser("",""));
        assertEquals(true, rA.LoginUser("sdffwef","weffwefwef"));
    }
    @Test
    public void notNull() {
        assertNotNull(rA);
    }

 }
