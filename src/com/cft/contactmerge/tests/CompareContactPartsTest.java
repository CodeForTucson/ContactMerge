package com.cft.contactmerge.tests;

import com.cft.contactmerge.contact.Address;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.*;

class CompareContactPartsTest {
    /*******************************************************************************************************************
     **************************************** Compare First And Last Name Tests ****************************************
     *******************************************************************************************************************/
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
    //--------------------------------------------------------------------- Basic Tests --------------------------------------------------------------------
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

    //--------------------------------------------------------------------- Punctuation --------------------------------------------------------------------
    @Test
    void doNamesMatch_Maybe_IgnoreMultiPunctuationInFirstAndLastNameDifferentOrder() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("aN-De-denIse ", " Sa-LA-Democh ", " An Denise dE", "sa democh la  "));
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
    void doNamesMatch_YesMaybe_Apostrophe() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  adriaNno ", "d'onOfio ", " adriaNno", " d onofiO  "), firstAndLastNameFailedMsg("  adriaNno ", "d'onOfio ", " adriaNno", " d onofiO  "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" mc  ", " d oNofio", "Josephine-mc-vitty ", "  d'onoFio "), firstAndLastNameFailedMsg(" mc vitty  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "));
    }

    @Test
    void doNamesMatch_No_Apostrophe_NameCombined() { // Note: In the future, we may let this return type be decided by the user as yes or maybe.
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" adriaNno  ", " doNofio", "adriaNno ", "  d'onoFio "), firstAndLastNameFailedMsg(" adriaNno  ", " doNofio", "adriaNno ", "  d'onoFio "));
    }

    /*******************************************************************************************************************
     ******************************************* Compare Street Address Tests ******************************************
     *******************************************************************************************************************/
    private String streetAddressFailedMsg(String streetAddressOne, String streetAddressTwo){
        return "<" + streetAddressOne + "> -vs- <" + streetAddressTwo + ">";
    }
    @Test
    void doStreetAddressesMatch_Yes_IgnoreCase() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 main Street");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main street"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfStreet() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main Street");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main St"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfAvenue() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main Avenue");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main Ave"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfDrive() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main Dr");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main Drive"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfLane() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main Lane");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main Ln"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfTrail() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main Trail");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main trl."));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfCircle() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main Circle");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main Cir"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfBoulevard() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main Blvd");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main Boulevard"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfRoad() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main Road");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main Rd"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfPOBox() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("P.O. Box 1234");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "PO Box 1234"),
                streetAddressFailedMsg("P.O. Box 1234","PO Box 1234"));

        contactOne.getAddress().setStreetAddress("P O Box 1234");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "PO Box 1234"),
                streetAddressFailedMsg("P O Box 1234","PO Box 1234"));

        contactOne.getAddress().setStreetAddress("PO Box 1234");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "Box 1234"),
                streetAddressFailedMsg("PO Box 1234","Box 1234"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfNorth() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 North Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 N Main St"),
                streetAddressFailedMsg("123 North Main St", "123 N Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 N. Main St"),
                streetAddressFailedMsg("123 North Main St", "123 N. Main St"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfNorthEast() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 NE Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 North-East Main St"),
                streetAddressFailedMsg("123 NE Main St","123 North-East Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Northeast Main St"),
                streetAddressFailedMsg("123 NE Main St","123 Northeast Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 NE. Main St"),
                streetAddressFailedMsg("123 NE Main St","123 NE. Main St"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfNorthWest() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 NW Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 North-West Main St"),
                streetAddressFailedMsg("123 NW Main St","123 North-West Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Northwest Main St"),
                streetAddressFailedMsg("123 NW Main St","123 Northwest Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 NW. Main St"),
                streetAddressFailedMsg("123 NW Main St","123 NW. Main St"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfSouth() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 S Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 South Main St"),
                streetAddressFailedMsg("123 S Main St","123 South Main St"));

        contactOne.getAddress().setStreetAddress("123 S. Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 South Main St"),
                streetAddressFailedMsg("123 S. Main St","123 South Main St"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfSouthEast() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 SE Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 South-East Main St"),
                streetAddressFailedMsg("123 SE Main St","123 South-East Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Southeast Main St"),
                streetAddressFailedMsg("123 SE Main St","123 Southeast Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 SE. Main St"),
                streetAddressFailedMsg("123 SE Main St","123 SE. Main St"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfSouthWest() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 SW Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 South-West Main St"),
                streetAddressFailedMsg("123 SW Main St","123 South-West Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Southwest Main St"),
                streetAddressFailedMsg("123 SW Main St","123 Southwest Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 SW. Main St"),
                streetAddressFailedMsg("123 SW Main St","123 SW. Main St"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfEast() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 East Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 E Main St"),
                streetAddressFailedMsg("123 East Main St","123 E Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 E. Main St"),
                streetAddressFailedMsg("123 East Main St","123 E. Main St"));
    }

    @Test
    void doStreetAddressesMatch_Yes_FormsOfWest() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 West Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 W Main St"),
                streetAddressFailedMsg("123 West Main St","123 W Main St"));

        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123 W. Main St"),
                streetAddressFailedMsg("123 West Main St","123 W. Main St"));
    }

    @Test
    void doStreetAddressesMatch_Yes_IgnoreSpaces() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("  123 Main St");
        assertEquals(AnswerType.yes, CompareContactParts.doStreetAddressesMatch(contactOne, "123  Main St  "));
    }

    @Test
    void doStreetAddressesMatch_Maybe_MissingRoadType() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main");
        assertEquals(AnswerType.maybe, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main St"));
    }

    @Test
    void doStreetAddressesMatch_Maybe_MissingDirection() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 N Main St");
        assertEquals(AnswerType.maybe, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main St"));
    }

    @Test
    void doStreetAddressesMatch_Maybe_MissingDirectionAndRoadType() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 N Main");
        assertEquals(AnswerType.maybe, CompareContactParts.doStreetAddressesMatch(contactOne, "123 Main St"));
    }

    @Test
    void doStreetAddressesMatch_No_DifferentStreetAddress() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setStreetAddress("123 Main St");
        assertEquals(AnswerType.no, CompareContactParts.doStreetAddressesMatch(contactOne, "1234 Main St"));
    }

    @Test
    void doStreetAddressesMatch_No_MissingStreetAddress() {
        Contact contactOne = new Contact();

        assertEquals(AnswerType.no, CompareContactParts.doStreetAddressesMatch(contactOne, "1234 Main St"));
    }

    /*******************************************************************************************************************
     ***************************************** Compare Apartment Address Tests *****************************************
     *******************************************************************************************************************/
    @Test
    void doApartmentAddressesMatch_Yes_FormsOfApartment() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setApartment("Apt 40");
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "Apartment 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "40"));
    }

    @Test
    void doApartmentAddressesMatch_Yes_FormsOfSuite() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setApartment("Suite 40");
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "Ste 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "40"));
    }

    @Test
    void doApartmentAddressesMatch_Yes_FormsOfUnit() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setApartment("Unit 40");
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "40"));

        contactOne.getAddress().setApartment("#40");
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "40"));
    }

    @Test
    void doApartmentAddressesMatch_Maybe_MissingApartment() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setApartment("Apt 40");
        assertEquals(AnswerType.maybe, CompareContactParts.doApartmentAddressesMatch(contactOne, ""));
    }

    @Test
    void doApartmentAddressesMatch_No_DifferentApartment() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setApartment("Unit 10");
        assertEquals(AnswerType.no, CompareContactParts.doApartmentAddressesMatch(contactOne, "Unit 11"));
    }

    /*******************************************************************************************************************
     ************************************************ Compare City Tests ***********************************************
     *******************************************************************************************************************/
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

    /*******************************************************************************************************************
     *********************************************** Compare State Tests ***********************************************
     *******************************************************************************************************************/
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

    /*******************************************************************************************************************
     ******************************************** Compare Phone Number Tests *******************************************
     *******************************************************************************************************************/
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

    /*******************************************************************************************************************
     *********************************************** Compare Email Tests ***********************************************
     *******************************************************************************************************************/
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