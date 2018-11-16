package edu.gatech.cs2340.waterwedonating;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**testing
 * public Boolean save(donationData donation) {
 *
 * }
 * in donationActivity.java
 */

public class CatherineJUnitTest {
    donationData donation0 = new donationData();
    donationData donation1 = new donationData();
    donationData donation2 = new donationData();
    donationData donation3 = new donationData();
    donationData donation4;
    donationData donation5;


    @Before
    public void setUp() {
        //donation0 with empty values
        donation0.setTimestamp(null);
        donation0.setLocation(null);
        donation0.setShortDescription(null);
        donation0.setFullDescription(null);
        donation0.setValue(null);
        donation0.setCategory(null);

        //donation1 with full constructor based on donationActivity
        donation1.setTimestamp("1:00:00");
        donation1.setLocation("Atlanta");
        donation1.setShortDescription("old shirts");
        donation1.setFullDescription("Old but nice shirts from gucci");
        donation1.setValue("$70");
        donation1.setCategory("Clothing");

        //donation2 with second constructor based on donationActivity
        donation2.setTimestamp("15:30:00");
        donation2.setCategory("Clothing");

        //donation3;
        donation3.setTimestamp("1:00:00");
        donation3.setLocation("Atlanta");
        donation3.setShortDescription(null);
        donation3.setFullDescription(null);
        donation3.setValue("$8");
        donation3.setCategory(null);
    }

    @Test
    public void notNull() {
        assertNotNull(donation0);
        assertNotNull(donation1);
        assertNotNull(donation3);
    }

    @Test
    public void isNull() {
        assertNull(donation4);
        assertNull(donation5);
    }




}

