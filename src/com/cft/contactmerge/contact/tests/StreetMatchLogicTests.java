package com.cft.contactmerge.contact.tests;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.cft.contactmerge.contact.StreetMatchLogic.*;

public class StreetMatchLogicTests {
    /*******************************************************************************************************************
     **************************************** getStreetAddressMatchResult Method ***************************************
     *******************************************************************************************************************/

    /*******************************************************************************************************************
     ***************************************** setNormalizeStreetAddress Method ****************************************
     *******************************************************************************************************************/
    @Test
    void getNumOfCharsInName_equals_AlphaNumericOnly(){
        ArrayList<String> stAddress = setNormalizeStreetAddress("  123 Main strEet ");
        ArrayList<String> expectedStAddress = new ArrayList<>(Arrays.asList("123", "main", "street"));

        assertEquals(expectedStAddress, stAddress);
    }
    // ToDo: Add more tests for this method
}
