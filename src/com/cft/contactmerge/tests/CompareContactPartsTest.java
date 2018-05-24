package com.cft.contactmerge.tests;

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
    private String addressFailedMsg(String addressOne, String addressTwo){
        return "<" + addressOne + "> -vs- <" + addressOne + ">";
    }

    @Test
    void doAddressesMatch_Yes_IgnoreCase() {
        Contact contactOne = new Contact();
        Contact contactTwo = new Contact();

        contactOne.getAddress().setStreetAddress("123 main Street");
        contactOne.getAddress().setCity("Tucson");
        contactOne.getAddress().setState("Arizona");
        contactOne.getAddress().setCountry("USA");

        contactTwo.getAddress().setStreetAddress("123 Main street");
        contactTwo.getAddress().setCity("TUCSON");
        contactTwo.getAddress().setState("ARIZONA");
        contactTwo.getAddress().setCountry("usa");

        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch(contactOne, contactTwo.getAddress()),
                addressFailedMsg(contactOne.getAddress().toString(), contactTwo.getAddress().toString()));
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
    private String apartmentFailedMsg(String apartmentOne, String apartmentTwo){
        return "<" + apartmentOne + "> -vs- <" + apartmentTwo + ">";
    }

    @Test
    void doApartmentAddressesMatch_Yes_FormsOfApartment() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setApartment("Apt 40");
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "Apartment 40"),
                apartmentFailedMsg("Apt 40","Apartment 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "Ap 40"),
                apartmentFailedMsg("Apt 40","Ap 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "40"),
                apartmentFailedMsg("Apt 40","40"));
    }

    @Test
    void doApartmentAddressesMatch_Yes_FormsOfSuite() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setApartment("Suite 40");
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "Ste 40"),
                apartmentFailedMsg("Suite 40","Ste 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "40"),
                apartmentFailedMsg("Suite 40","40"));
    }

    @Test
    void doApartmentAddressesMatch_Yes_FormsOfUnit() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setApartment("Unit 40");
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "40"),
                apartmentFailedMsg("Unit 40","40"));

        contactOne.getAddress().setApartment("#40");
        assertEquals(AnswerType.yes, CompareContactParts.doApartmentAddressesMatch(contactOne, "40"),
                apartmentFailedMsg("#40","40"));
    }

    @Test
    void doApartmentAddressesMatch_No_MissingApartment() {
        Contact contactOne = new Contact();

        assertEquals(AnswerType.no, CompareContactParts.doApartmentAddressesMatch(contactOne, "Apt 40"),
                apartmentFailedMsg("null","Apt 40"));

        contactOne.getAddress().setApartment("Apt 40");
        assertEquals(AnswerType.no, CompareContactParts.doApartmentAddressesMatch(contactOne, ""),
                apartmentFailedMsg("Apt 40","\"\""));
    }

    @Test
    void doApartmentAddressesMatch_No_DifferentApartment() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setApartment("Unit 10");
        assertEquals(AnswerType.no, CompareContactParts.doApartmentAddressesMatch(contactOne, "Unit 11"),
                apartmentFailedMsg("Unit 10","Unit 11"));

        contactOne.getAddress().setApartment("J10");
        assertEquals(AnswerType.no, CompareContactParts.doApartmentAddressesMatch(contactOne, "L10"),
                apartmentFailedMsg("J10","L10"));
    }

    /*******************************************************************************************************************
     ************************************************ Compare City Tests ***********************************************
     *******************************************************************************************************************/
    private String cityFailedMsg(String cityOne, String cityTwo){
        return "<" + cityOne + "> -vs- <" + cityTwo + ">";
    }

    @Test
    void doCitiesMatch_Yes_IgnoreCase() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("Tucson");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "TUCSON"));
    }

    @Test
    void doCitiesMatch_Yes_IgnoreSpaces() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("Tucson  ");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "Tucson"));
    }

    @Test
    void doCitiesMatch_Yes_FormsOfNorth() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("North Las Vegas");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "N Las Vegas"),
                cityFailedMsg("North Las Vegas","N Las Vegas"));
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "N. Las Vegas"),
                cityFailedMsg("North Las Vegas","N. Las Vegas"));
    }

    @Test
    void doCitiesMatch_Yes_FormsOfSouth(){
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("South Jordan");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "S Jordan"),
                cityFailedMsg("South Jordan","S Jordan"));
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "S. Jordan"),
                cityFailedMsg("South Jordan","S. Jordan"));
    }

    @Test
    void doCitiesMatch_Yes_FormsOfEast(){
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("E Hartford");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "East Hartford"),
                cityFailedMsg("E Hartford","East Hartford"));

        contactOne.getAddress().setCity("E. Hartford");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "East Hartford"),
                cityFailedMsg("E. Hartford","East Hartford"));
    }

    @Test
    void doCitiesMatch_Yes_FormsOfWest(){
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("W Haven");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "West Haven"),
                cityFailedMsg("W Haven","West Haven"));

        contactOne.getAddress().setCity("W. Haven");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "West Haven"),
                cityFailedMsg("W. Haven","West Haven"));
    }

    @Test
    void doCitiesMatch_Yes_FormsOfNorthEast() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("NE Washington");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "North-East Washington"),
                cityFailedMsg("NE Washington","North-East Washington"));
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "Northeast Washington"),
                cityFailedMsg("NE Washington","Northeast Washington"));
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "NE. Washington"),
                cityFailedMsg("NE Washington","NE. Washington"));
    }

    @Test
    void doCitiesMatch_Yes_FormsOfNorthWest() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("NW Washington");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "North-West Washington"),
                cityFailedMsg("NW Washington","North-West Washington"));
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "Northwest Washington"),
                cityFailedMsg("NW Washington","Northwest Washington"));
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "NW. Washington"),
                cityFailedMsg("NW Washington","NW. Washington"));
    }

    @Test
    void doCitiesMatch_Yes_FormsOfSouthWest() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("South-West Washington");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "SW Washington"),
                cityFailedMsg("South-West Washington","SW Washington"));

        contactOne.getAddress().setCity("Southwest Washington");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "SW Washington"),
                cityFailedMsg("Southwest Washington","SW Washington"));

        contactOne.getAddress().setCity("SW. Washington");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "SW Washington"),
                cityFailedMsg("SW. Washington","SW Washington"));
    }

    @Test
    void doCitiesMatch_Yes_FormsOfSouthEast() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("South-East Washington");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "SE Washington"),
                cityFailedMsg("South-East Washington","SE Washington"));

        contactOne.getAddress().setCity("Southeast Washington");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "SE Washington"),
                cityFailedMsg("Southeast Washington","SE Washington"));

        contactOne.getAddress().setCity("SE. Washington");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "SE Washington"),
                cityFailedMsg("SE. Washington","SE Washington"));
    }

    @Test
    void doCitiesMatch_Maybe_MissingDirection() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("North Las Vegas");
        assertEquals(AnswerType.maybe, CompareContactParts.doCitiesMatch(contactOne, "Las Vegas"));
    }

    @Test
    void doCitiesMatch_Yes_NumbersInName() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("North 24 Parganas district");
        assertEquals(AnswerType.yes, CompareContactParts.doCitiesMatch(contactOne, "North 24 Parganas district"));
    }

    @Test
    void doCitiesMatch_No() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCity("Tucson");
        assertEquals(AnswerType.no, CompareContactParts.doCitiesMatch(contactOne, "Phoenix"));
    }

    /*******************************************************************************************************************
     *********************************************** Compare State Tests ***********************************************
     *******************************************************************************************************************/
    private String stateFailedMsg(String stateOne, String stateTwo){
        return "<" + stateOne + "> -vs- <" + stateTwo + ">";
    }

    @Test
    void doStatesMatch_Yes_IgnoreCase() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setState("AZ");
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch(contactOne, "az"));
    }

    @Test
    void doStatesMatch_Yes_IgnoreSpaces() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setState("AZ");
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch(contactOne, " AZ    "));
    }

    @Test
    void doStatesMatch_Yes_Abbreviations() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setState("AZ");
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch(contactOne, "Arizona"),
                stateFailedMsg("AZ","Arizona"));

        contactOne.getAddress().setState("California");
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch(contactOne, "CA"),
                stateFailedMsg("California","CA"));

        contactOne.getAddress().setState("New York");
        assertEquals(AnswerType.yes, CompareContactParts.doStatesMatch(contactOne, "NY"),
                stateFailedMsg("New York","NY"));
    }

    @Test
    void doStatesMatch_No() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setState("New York");
        assertEquals(AnswerType.no, CompareContactParts.doStatesMatch(contactOne, "Arizona"));
    }

    /*******************************************************************************************************************
     ********************************************** Compare Country Tests **********************************************
     *******************************************************************************************************************/
    private String countryFailedMsg(String countryOne, String countryTwo){
        return "<" + countryOne + "> -vs- <" + countryTwo + ">";
    }

    @Test
    void doCountriesMatch_Yes_IgnoreCase() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setCountry("United States of America");
        assertEquals(AnswerType.no, CompareContactParts.doCountriesMatch(contactOne, "united states of america"));
    }

    /*******************************************************************************************************************
     ************************************************ Compare Zip Tests ************************************************
     *******************************************************************************************************************/
    private String zipFailedMsg(String zipOne, String zipTwo){
        return "<" + zipOne + "> -vs- <" + zipTwo + ">";
    }

    @Test
    void doZipsMatch_Yes() {
        Contact contactOne = new Contact();

        contactOne.getAddress().setZip("92592");
        assertEquals(AnswerType.no, CompareContactParts.doZipsMatch(contactOne, "92592"));
    }

    /*******************************************************************************************************************
     ******************************************** Compare Phone Number Tests *******************************************
     *******************************************************************************************************************/
    private String phoneNumberFailedMsg(String phoneNumberOne, String phoneNumberTwo){
        return "<" + phoneNumberOne + "> -vs- <" + phoneNumberTwo + ">";
    }

    @Test
    void doPhoneNumbersMatch_Yes_IgnoreSpaces() {
        Contact contactOne = new Contact();

        contactOne.getPhone().setPhoneNumber("520 123 4567");
        assertEquals(AnswerType.yes, CompareContactParts.doPhoneNumbersMatch(contactOne, "5201234567"));
    }

    @Test
    void doPhoneNumbersMatch_Yes_IgnorePunctuation() {
        Contact contactOne = new Contact();

        contactOne.getPhone().setPhoneNumber("(520) 123-4567");
        assertEquals(AnswerType.yes, CompareContactParts.doPhoneNumbersMatch(contactOne, "5201234567"),
                phoneNumberFailedMsg("(520) 123-4567","5201234567"));
        assertEquals(AnswerType.yes, CompareContactParts.doPhoneNumbersMatch(contactOne, "520-123-4567"),
                phoneNumberFailedMsg("(520) 123-4567","520-123-4567"));
    }

    @Test
    void doPhoneNumbersMatch_No() {
        Contact contactOne = new Contact();

        contactOne.getPhone().setPhoneNumber("(520) 123-4567");
        assertEquals(AnswerType.no, CompareContactParts.doPhoneNumbersMatch(contactOne, "(520) 123-4667"));
    }

    /*******************************************************************************************************************
     *********************************************** Compare Email Tests ***********************************************
     *******************************************************************************************************************/
    @Test
    void doEmailsMatch_Yes_IgnoreSpaces() {
        Contact contactOne = new Contact();

        contactOne.getEmail().setEmailAddress("jdoe@yahoo.com");
        assertEquals(AnswerType.yes, CompareContactParts.doEmailsMatch(contactOne, " jdoe@yahoo.com "));
    }

    @Test
    void doEmailsMatch_Yes_IgnoreCase() {
        Contact contactOne = new Contact();

        contactOne.getEmail().setEmailAddress("jdoe@yahoo.com");
        assertEquals(AnswerType.yes, CompareContactParts.doEmailsMatch(contactOne, "JDoe@Yahoo.com"));
    }

    @Test
    void doEmailsMatch_No() {
        Contact contactOne = new Contact();

        contactOne.getEmail().setEmailAddress("jdoe@yahoo.com");
        assertEquals(AnswerType.no, CompareContactParts.doEmailsMatch(contactOne, "jdoe@gmail.com"));
    }
}