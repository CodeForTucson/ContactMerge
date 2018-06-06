package com.cft.contactmerge.contact.tests;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.contact.Zip;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZipTest {

    @Test
    void isMatch_No() {
        Zip zip = new Zip("85750");

        assertEquals(AnswerType.no, zip.isMatch(new Zip("85751")) );
    }

    @Test
    void isMatch_No_NotZip() {
        Zip zip = new Zip("T5T 1S9");

        // Making sure the short zip test isn't used (first 5 chars match
        // but that only leads to match for US Zips).
        assertEquals(AnswerType.no, zip.isMatch(new Zip("T5T 1S8")) );
    }

    @Test
    void isMatch_Yes() {
        Zip zip = new Zip("85750");

        assertEquals(AnswerType.yes, zip.isMatch(new Zip("85750")) );
    }

    @Test
    void isMatch_Yes_MainPart() {
        Zip zip = new Zip("85750-1234");

        assertEquals(AnswerType.yes, zip.isMatch(new Zip("85750")) );
    }

    @Test
    void isMatch_Yes_MainPartReverse() {
        Zip zip = new Zip("85750");

        assertEquals(AnswerType.yes, zip.isMatch(new Zip("85750-1234")) );
    }

    @Test
    void isMatch_No_ShortZip() {
        Zip zip = new Zip("8575");

        assertEquals(AnswerType.no, zip.isMatch(new Zip("8574")) );
    }
}