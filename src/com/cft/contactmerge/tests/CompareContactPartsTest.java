package com.cft.contactmerge.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.*;

class CompareContactPartsTest {

    @Test
    void doNamesMatch_No_DifferentLastName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("Jonh", "Doe", "John", "Adams"));
    }

    @Test
    void doNamesMatch_NoDifferentFirstName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("Jonh", "Doe", "Jane", "Doe"));
    }

    @Test
    void doNamesMatch_YesCaseDoesNotMatter() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("john", "DOE", "JOHN", "Doe"));
    }

    @Test
    void doNamesMatch_YesIgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" John", "Doe", "John ", " Doe  "));
    }

    @Test
    void doNamesMatch_YesIgnorePunctuation() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("Jane", "Doe-Adams", "Jane", "Doe Adams"));
    }

    @Test
    void doNamesMatch_YesHyphenatedNames() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("Jane", "Doe-Adams", "Jane", "Doe"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("Jane", "Adams", "Jane", "Doe-Adams"));
    }

    @Test
    void doNamesMatch_MaybeFirstLastSwap() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("John", "Doe", "Doe", "John"));
    }

    @Test
    void doNamesMatch_MaybeFirstNameInitials() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("John", "Doe", "J", "Doe"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("J.", "Doe", "John", "Doe"));
    }

    // TODO: Add other name tests
    // 1. Middle initials, Prefix, and Suffix
    // 2. Common forms like: Robert, Robbie, Bob
    // 3. Compare household name to individual name
    // 4. Handle mispellings(??)

    @Test
    void doAddressesMatch_YesIgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 main Street", "123 Main street"));
    }

    @Test
    void doAddressesMatch_YesFormsOfStreet() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Street", "123 Main St"));
    }

    @Test
    void doAddressesMatch_YesFormsOfAvenue() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Avenue", "123 Main Ave"));
    }

    @Test
    void doAddressesMatch_YesFormsOfDrive() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Dr", "123 Main Drive"));
    }

    @Test
    void doAddressesMatch_YesFormsOfLane() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Lane", "123 Main Ln"));
    }

    @Test
    void doAddressesMatch_YesFormsOfTrail() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Trail", "123 Main trl."));
    }

    @Test
    void doAddressesMatch_YesFormsOfCircle() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Circle", "123 Main Cir"));
    }

    @Test
    void doAddressesMatch_YesFormsOfBoulevard() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Blvd", "123 Main Boulevard"));
    }

    @Test
    void doAddressesMatch_YesFormsOfRoad() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main Road", "123 Main Rd"));
    }

    @Test
    void doAddressesMatch_YesFormsOfPOBox() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("P.O. Box 1234", "PO Box 1234"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("P O Box 1234", "PO Box 1234"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("PO Box 1234", "Box 1234"));
    }

    @Test
    void doAddressesMatch_YesFormsOfNorth() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 North Main St", "123 N Main St"));
    }

    @Test
    void doAddressesMatch_YesFormsOfSouth() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 S Main St", "123 South Main St"));
    }

    @Test
    void doAddressesMatch_YesFormsOfEast() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 East Main St", "123 E Main St"));
    }

    @Test
    void doAddressesMatch_YesFormsOfWest() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 West Main St", "123 W Main St"));
    }

    @Test
    void doAddressesMatch_YesFormsOfApartment() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Apt 40", "123 Main St, Apartment 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Apt 40", "123 Main St, 40"));
    }

    @Test
    void doAddressesMatch_YesFormsOfSuite() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Suite 40", "123 Main St, Ste 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Suite 40", "123 Main St, 40"));
    }

    @Test
    void doAddressesMatch_YesFormsOfUnit() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, Unit 40", "123 Main St, 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("123 Main St, #40", "123 Main St, 40"));
    }

    @Test
    void doAddressesMatch_YesIgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch("  123 Main St", "123  Main St  "));
    }

    @Test
    void doAddressesMatch_MabyeMissingApartment() {
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressesMatch("123 Main St, Apt 40", "123 Main St"));
    }

    @Test
    void doAddressesMatch_MabyeMissingRoadType() {
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressesMatch("123 Main", "123 Main St"));
    }

    @Test
    void doAddressesMatch_NoDifferentAddress() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressesMatch("123 Main St", "1234 Main St"));
    }

    @Test
    void doAddressesMatch_NoDifferentApartment() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressesMatch("123 Main St, Unit 10", "123 Main St, Unit 11"));
    }

    @Test
    void doCitiesMatch_YesIgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch("Tucson", "TUCSON"));
    }

    @Test
    void doCitiesMatch_YesIgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch("Tucson  ", "Tucson"));
    }

    @Test
    void doCitiesMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doCitiesMatch("Tucson", "Phoenix"));
    }

    @Test
    void doStatesMatch_YesIgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("AZ", "az"));
    }

    @Test
    void doStatesMatch_YesIgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("AZ", " AZ    "));
    }

    @Test
    void doStatesMatch_YesAbbreviations() {
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("AZ", "Arizona"));
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("California", "CA"));
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch("New York", "NY"));
    }

    @Test
    void doStatesMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doStatesMatch("New York", "Arizona"));
    }

    @Test
    void doPhoneNumbersMatch_YesIgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doPhoneNumbersMatch("520 123 4567", "5201234567"));
    }

    @Test
    void doPhoneNumbersMatch_YesIgnorePunctuation() {
        assertEquals(AnswerType.yes, CompareContactParts.doPhoneNumbersMatch("(520) 123-4567", "5201234567"));
        assertEquals(AnswerType.yes, CompareContactParts.doPhoneNumbersMatch("(520) 123-4567", "520-123-4567"));
    }

    @Test
    void doPhoneNumbersMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doPhoneNumbersMatch("(520) 123-4567", "(520) 123-4667"));
    }

    @Test
    void doEmailsMatch_YesIgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doEmailsMatch("jdoe@yahoo.com", " jdoe@yahoo.com "));
    }

    @Test
    void doEmailsMatch_YesIgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doEmailsMatch("jdoe@yahoo.com", "JDoe@Yahoo.com"));
    }

    @Test
    void doEmailsMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doEmailsMatch("jdoe@yahoo.com", "jdoe@gmail.com"));
    }
}