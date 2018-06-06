package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.contact.StreetAddress;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreetAddressTest {

    @Test
    void Constructor()
    {
        StreetAddress property = new StreetAddress("123 Main St");
        assertEquals("123 Main St", property.toString());
    }

    @Test
    void isMatch_Yes_Equal() {
        StreetAddress source = new StreetAddress("123 Main St");
        StreetAddress target = new StreetAddress("123 Main St");

        assertEquals(AnswerType.yes, source.isMatch(target));
    }

    @Test
    void isMatch_No() {
        StreetAddress source = new StreetAddress("123 Main St");
        StreetAddress target = new StreetAddress("132 Main St");

        assertEquals(AnswerType.no, source.isMatch(target));
    }

    /* -------------------------------------------------------------------------------------
     * Tests to verify that address that only differ in inclusion of street type are
     * a maybe match.
     * -------------------------------------------------------------------------------------
     */

    private void testMissingStreetType(String streetType) {
        StreetAddress source = new StreetAddress("123 Main");
        StreetAddress target = new StreetAddress("123 Main " + streetType);

        assertEquals(AnswerType.maybe, source.isMatch(target));
    }

    @Test
    void isMatch_Maybe_MissingStreet() {
        testMissingStreetType("St");
    }

    @Test
    void isMatch_Maybe_MissingAvenue() {
        testMissingStreetType("ave");
    }

    @Test
    void isMatch_Maybe_MissingDrive() {
        testMissingStreetType("DR");
    }

    @Test
    void isMatch_Maybe_MissingLane() {
        testMissingStreetType("Ln");
    }

    @Test
    void isMatch_Maybe_MissingTrail() {
        testMissingStreetType("Trl");
    }

    @Test
    void isMatch_Maybe_MissingCircle() {
        testMissingStreetType("cir");
    }

    @Test
    void isMatch_Maybe_MissingBoulevard() {
        testMissingStreetType("blvd");
    }

    @Test
    void isMatch_Maybe_MissingRoad() {
        testMissingStreetType("rd");
    }

    /* -------------------------------------------------------------------------------------
     * Tests to verify that address that only differ in inclusion of direction are
     * a maybe match.
     * -------------------------------------------------------------------------------------
     */

    private void testMissingDirection(String direction) {
        StreetAddress source = new StreetAddress("123 Main");
        StreetAddress target = new StreetAddress("123 " + direction + " Main St");

        assertEquals(AnswerType.maybe, source.isMatch(target));
    }

    @Test
    void isMatch_Maybe_MissingDirectionW() {
        testMissingDirection("W");
    }

    @Test
    void isMatch_Maybe_MissingDirectionE() {
        testMissingDirection("E");
    }

    @Test
    void isMatch_Maybe_MissingDirectionN() {
        testMissingDirection("N");
    }

    @Test
    void isMatch_Maybe_MissingDirectionNW() {
        testMissingDirection("NW");
    }

    @Test
    void isMatch_Maybe_MissingDirectionNE() {
        testMissingDirection("NE");
    }

    @Test
    void isMatch_Maybe_MissingDirectionS() {
        testMissingDirection("S");
    }

    @Test
    void isMatch_Maybe_MissingDirectionSE() {
        testMissingDirection("SE");
    }

    @Test
    void isMatch_Maybe_MissingDirectionSW() {
        testMissingDirection("SW");
    }
}