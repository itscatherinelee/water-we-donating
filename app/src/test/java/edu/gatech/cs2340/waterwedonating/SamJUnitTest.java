package edu.gatech.cs2340.waterwedonating;


import org.junit.Before ;
import org.junit.Test ;
import static org.junit.Assert.*;



/**
 * I will be running this JUnit on the userInformation :)
 *
 *
 */
public class SamJUnitTest {
    private userInformation testboi1 = new userInformation();
    private userInformation testboi2 = new userInformation();
    private userInformation testboi3 = new userInformation();
    private userInformation testboi4;
    private userInformation testboi5;

    @Before
    public void setUp() {
      testboi1.setType("Admin");
      testboi1.setName("Sam");
      testboi1.setEmailOrUsername("sthomas313@gatech.edu");
      testboi1.setId("sthomas313");

      testboi2.setType("User");
      testboi2.setName("Naim");
      testboi2.setEmailOrUsername("naim69@gatech.edu");
      testboi2.setId("naimboi");

      testboi3.setType("Admin");
      testboi3.setName("Sam");
      testboi3.setEmailOrUsername("sthomas313@gatech.edu");
      testboi3.setId("sthomas313");
    }
    @Test
    public void notNull() {
        assertNotNull(testboi1);
        assertNotNull(testboi2);
        assertNotNull(testboi3);
    }

    @Test
    public void isNull() {
        assertNull(testboi4);
        assertNull(testboi5);
    }

    @Test
    public void isSame() {
        assertSame(testboi1,testboi3);
    }

}