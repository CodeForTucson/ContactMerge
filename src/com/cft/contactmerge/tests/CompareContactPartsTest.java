package com.cft.contactmerge.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.*;

class CompareContactPartsTest {

    /*************************************
     * Compare First And Last Name Tests *
     *************************************/

    @Test
    void doNamesMatch_No_DifferentLastName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("John", "Doe", "John", "Adams"));
    }

    @Test
    void doNamesMatch_No_DifferentFirstName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("John", "Doe", "Jane", "Doe"));
    }

    @Test
    void doNamesMatch_Yes_CaseDoesNotMatter() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("john", "DOE", "JOHN", "Doe"));
    }

    @Test
    void doNamesMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" John", "Doe", "John ", " Doe  "));
    }

    @Test
    void doNamesMatch_Yes_IgnoreSpacesAndCaseInsensitive() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" JoHn", "DOE", "JOHN ", " Doe  "));
    }

    @Test
    void doNamesMatch_Yes_IgnorePunctuation() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("Jane", "Doe-Adams", "Jane", "Doe Adams"));
    }

    @Test
    void doNamesMatch_Maybe_HyphenatedNames() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("Jane", "Doe-Adams", "Jane", "Doe"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("Jane", "Adams", "Jane", "Doe-Adams"));
    }

    @Test
    void doNamesMatch_Maybe_NamesContainMultiHyphens() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("Areb", "Sa-la-Democh", "Areb", "Democh"));
    }

    @Test
    void doNamesMatch_Maybe_FirstLastSwap() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("John", "Doe", "Doe", "John"));
    }

    @Test
    void doNamesMatch_Maybe_FirstNameInitials() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("John", "Doe", "J", "Doe"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("J.", "Doe", "John", "Doe"));
    }

    @Test
    void doNamesMatch_no_FirstNameInitialsDifferentLastName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("John", "Adams", "J", "Doe"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("J.", "Doe", "John", "Adams"));
    }

    // TODO: Add other name tests
    // 1. Middle initials, Prefix, and Suffix
    // 2. Common forms like: Robert, Robbie, Bob
    // 3. Compare household name to individual name
    // 4. Handle mispellings(??)

    /*************************
     * Compare Address Tests *
     *************************/

    @Test
    void doAddressesMatch_Yes_IgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 main Street", "123 Main street"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfStreet() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Street", "123 Main St"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfAvenue() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Avenue", "123 Main Ave"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfDrive() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Dr", "123 Main Drive"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfLane() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Lane", "123 Main Ln"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfTrail() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Trail", "123 Main trl."));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfCircle() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Circle", "123 Main Cir"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfBoulevard() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Blvd", "123 Main Boulevard"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfRoad() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Road", "123 Main Rd"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfPOBox() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("P.O. Box 1234", "PO Box 1234"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("P O Box 1234", "PO Box 1234"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("PO Box 1234", "Box 1234"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfNorth() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 North Main St", "123 N Main St"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfSouth() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 S Main St", "123 South Main St"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfEast() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 East Main St", "123 E Main St"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfWest() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 West Main St", "123 W Main St"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfApartment() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Apt 40", "123 Main St, Apartment 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Apt 40", "123 Main St, 40"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfSuite() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Suite 40", "123 Main St, Ste 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Suite 40", "123 Main St, 40"));
    }

    @Test
    void doAddressesMatch_Yes_FormsOfUnit() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Unit 40", "123 Main St, 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, #40", "123 Main St, 40"));
    }

    @Test
    void doAddressesMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("  123 Main St", "123  Main St  "));
    }

    @Test
    void doAddressesMatch_Maybe_MissingApartment() {
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressesMatch("123 Main St, Apt 40", "123 Main St"));
    }

    @Test
    void doAddressesMatch_Maybe_MissingRoadType() {
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressesMatch("123 Main", "123 Main St"));
    }

    @Test
    void doAddressesMatch_No_DifferentAddress() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressesMatch("123 Main St", "1234 Main St"));
    }

    @Test
    void doAddressesMatch_No_DifferentApartment() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressesMatch("123 Main St, Unit 10", "123 Main St, Unit 11"));
    }

    @Test
    void doCitiesMatch_Yes_IgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch("Tucson", "TUCSON"));
    }

    @Test
    void doCitiesMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch("Tucson  ", "Tucson"));
    }

    @Test
    void doCitiesMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doCitiesMatch("Tucson", "Phoenix"));
    }

    @Test
    void doStatesMatch_Yes_IgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("AZ", "az"));
    }

    @Test
    void doStatesMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("AZ", " AZ    "));
    }

    @Test
    void doStatesMatch_Yes_Abbreviations() {
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("AZ", "Arizona"));
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("California", "CA"));
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("New York", "NY"));
    }

    @Test
    void doStatesMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doStatesMatch("New York", "Arizona"));
    }

    @Test
    void doPhoneNumbersMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doPhoneNumbersMatch("520 123 4567", "5201234567"));
    }

    @Test
    void doPhoneNumbersMatch_Yes_IgnorePunctuation() {
        assertEquals(AnswerType.yes, CompareContactParts.doPhoneNumbersMatch("(520) 123-4567", "5201234567"));
        assertEquals(AnswerType.yes, CompareContactParts.doPhoneNumbersMatch("(520) 123-4567", "520-123-4567"));
    }

    @Test
    void doPhoneNumbersMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doPhoneNumbersMatch("(520) 123-4567", "(520) 123-4667"));
    }

    @Test
    void doEmailsMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doEmailsMatch("jdoe@yahoo.com", " jdoe@yahoo.com "));
    }

    @Test
    void doEmailsMatch_Yes_IgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doEmailsMatch("jdoe@yahoo.com", "JDoe@Yahoo.com"));
    }

    @Test
    void doEmailsMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doEmailsMatch("jdoe@yahoo.com", "jdoe@gmail.com"));
    }
}