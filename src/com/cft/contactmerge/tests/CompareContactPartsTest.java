package com.cft.contactmerge.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.*;

class CompareContactPartsTest {
    /****************************************************************************************************************************************************************************
     ********************************************************************* Compare First And Last Name Tests ********************************************************************
     ****************************************************************************************************************************************************************************/
    /* preconditions...
     * All test cases must include a check for trailing white spaces and different upper/lower case letters.
     */
    private String firstAndLastNameFailedMsg(String fNameOne,String  lNameOne,String  fNameTwo,String  lNameTwo){
        return "(compareFirstNames: \"" + fNameOne + "\" vs \"" + fNameTwo + "\") and (compareLastNames: \"" + lNameOne + "\" vs \"" + lNameTwo + "\")";
    }
    //--------------------------------------------------------------------- Basic Tests --------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" JoHn", "DOE ", "JOHN ", " Doe  "));
    }

    @Test
    void doNamesMatch_No_DifferentLastName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" JoHn", "DOE ", "JOHN ", " aDams  "));
    }

    @Test
    void doNamesMatch_No_DifferentFirstName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" JoHn", "DOE ", "jAne ", " Doe  "));
    }

    //--------------------------------------------------------------------- Punctuation --------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_IgnoreMultiPunctuationInFirstAndLastName() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("aN-De-denIse ", " Sa-LA-Democh ", " An dE Denise ", "sa la democh  "));
    }

    //--------------------------------------------------------------------- Hyphenated First and Last Names ---------------------------------------------------------------------
    // If the name is less than 4 characters and is contained inside the match name, return maybe.
    // If the name is 4 or more characters and is contained inside the match name, return yes.

    @Test
    void doNamesMatch_YesMaybeNo_FirstAndLastNamesContainMultiHyphens() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" Josephine-mc-vitty", "Sa-la-democh ", "vittY ", " democH"), firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Sa-la-democh ", "vittY ", " democH"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" joSephine-mc-vitty ", " sa-La-democh ", "  mC  ", "  lA  "), firstAndLastNameFailedMsg(" joSephine-mc-vitty ", " sa-La-democh ", "  mC  ", "  lA  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" JosePh  ", "  leE", " JosEphine-Mc-Vitty", " sa-la-Cathleen "), firstAndLastNameFailedMsg(" JosePh  ", "  leE", " JosEphine-Mc-Vitty", " sa-la-Cathleen "));
    }

    //----------------------------------------------------------------------- Last and First Names Swapped ----------------------------------------------------------------------
    @Test
    void doNamesMatch_Maybe_FirstLastSwap() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" joHn  ", "  Doe ", " doe", "JOhn "));
    }

    //--------------------------------------------------------------------------- First Name Initials ---------------------------------------------------------------------------
    // Rule: program will not allow last name initials
    // Only matches with a maybe if it is first name
    @Test
    void doNamesMatch_Maybe_FirstNameInitials() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" John  ", "  Doe ", "j ", " dOe"), firstAndLastNameFailedMsg(" John  ", "  Doe ", "j ", " dOe"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("J. ", " Doe", "  joHn ", " doe  "), firstAndLastNameFailedMsg("J. ", " Doe", "  joHn ", " doe  "));
    }

    //------------------------------------------------------------------------------- Other Tests -------------------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_Apostrophe() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  adriaNno ", "d'onOfio ", " adriaNno", " d onofiO  "), firstAndLastNameFailedMsg("  adriaNno ", "d'onOfio ", " adriaNno", " d onofiO  "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" adriaNno  ", " doNofio", "adriaNno ", "  d'onoFio "), firstAndLastNameFailedMsg(" adriaNno  ", " doNofio", "adriaNno ", "  d'onoFio "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" mc  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "), firstAndLastNameFailedMsg(" mc vitty  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "));
    }

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