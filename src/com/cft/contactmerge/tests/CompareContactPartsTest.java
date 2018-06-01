package com.cft.contactmerge.tests;

import com.cft.contactmerge.contact.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.*;

class CompareContactPartsTest {
    /*******************************************************************************************************************
     ************************************************ Compare Name Tests ***********************************************
     *******************************************************************************************************************/
    /* preconditions...
     * All test cases must include a check for trailing white spaces and different upper/lower case letters.
     * All prefixes will not be included in any matching, since prefix is just a title that can change randomly while the person remains the same.
     */
    private String firstAndLastNameFailedMsg(String fNameOne,String  lNameOne,String  fNameTwo,String  lNameTwo){
        return "(compareFirstNames: \"" + fNameOne + "\" vs \"" + fNameTwo + "\") and (compareLastNames: \"" + lNameOne + "\" vs \"" + lNameTwo + "\")";
    }
    //--------------------------------------------------------------------- Basic Tests --------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes() {
        Name firstAndLastNameOne = new Name(" DaVe", " dave ", "AUSHERMAN ", "Dr.", "2nd");
        Name firstAndLastNameTwo = new Name("DAVE ", "  Dave ", " Ausherman  ", "dr", "II");

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_Yes_FirstAndLastNameOnly() {
        Name firstAndLastNameOne = new Name(" JoHn", null, "DOE ", null, null);
        Name firstAndLastNameTwo = new Name("JOHN ", null, " Doe  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_Yes_MissingPrefix() {
        Name firstAndLastNameOne = new Name(" JoHn", null, "DOE ", "Dr.", null);
        Name firstAndLastNameTwo = new Name("JOHN ", null, " Doe  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_Yes_DifferentPrefix() {
        Name firstAndLastNameOne = new Name(" JoHn", null, "DOE ", "Dr.", null);
        Name firstAndLastNameTwo = new Name("JOHN ", null, " Doe  ", "Mr", null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_DifferentLastName() {
        Name firstAndLastNameOne = new Name(" JoHn", null, "DOE ", null, null);
        Name firstAndLastNameTwo = new Name("JOHN ", null, " aDams  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_DifferentMiddleName() {
        Name firstAndLastNameOne = new Name(" JoHn", " allen ", "DOE ", null, null);
        Name firstAndLastNameTwo = new Name("JOHN ", "  Ray ", " Doe  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_DifferentFirstName() {
        Name firstAndLastNameOne = new Name(" JoHn", null, "DOE ", null, null);
        Name firstAndLastNameTwo = new Name("jAne ", null, " Doe  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_DifferentSuffixName() {
        Name firstAndLastNameOne = new Name(" DaVe", " dave ", "AUSHERMAN ", "Dr.", "2nd");
        Name firstAndLastNameTwo = new Name("DAVE ", "  Dave ", " Ausherman  ", "dr", "3rd");

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    //--------------------------------------------------------------------- Punctuation --------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_IgnoreMultiPunctuationInFirstAndLastName() {
        Name firstAndLastNameOne = new Name("aN-De-denIse ", null, " Sa-LA-Democh ", null, null);
        Name firstAndLastNameTwo = new Name(" An dE Denise ", null, "sa la democh  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_Yes_IgnoreMultiPunctuationInFirstAndLastNameDifferentOrder() {
        Name firstAndLastNameOne = new Name("aN-De-denIse ", null, " Sa-LA-Democh ", null, null);
        Name firstAndLastNameTwo = new Name(" An Denise dE", null, "sa democh la  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    //--------------------------------------------------------------------- Hyphenated First and Last Names ---------------------------------------------------------------------
    // If the name is less than 4 characters and is contained inside the match name, return maybe.
    // If the name is 4 or more characters and is contained inside the match name, return yes.

    @Test
    void doNamesMatch_YesMaybeNo_FirstAndLastNamesContainMultiHyphens() {
        Name firstAndLastNameOne = new Name(" Josephine-mc-vitty", null, "Sa-la-democh ", null, null);
        Name firstAndLastNameTwo = new Name("vittY ", null, " democH", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Sa-la-democh ", "vittY ", " democH"));

        firstAndLastNameOne.setFullName(" joSephine-mc-vitty ", null, " sa-La-democh ", null, null);
        firstAndLastNameTwo.setFullName("  mC  ", null, "  lA  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" joSephine-mc-vitty ", " sa-La-democh ", "  mC  ", "  lA  "));

        firstAndLastNameOne.setFullName(" JosePh  ", null, "  leE", null, null);
        firstAndLastNameTwo.setFullName(" JosEphine-Mc-Vitty", null, " sa-la-Cathleen ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" JosePh  ", "  leE", " JosEphine-Mc-Vitty", " sa-la-Cathleen "));
    }

    //----------------------------------------------------------------------- Last and First Names Swapped ----------------------------------------------------------------------
    @Test
    void doNamesMatch_Maybe_FirstLastSwap() {
        Name firstAndLastNameOne = new Name(" joHn  ", null, "  Doe ", null, null);
        Name firstAndLastNameTwo = new Name(" doe", null, "JOhn ", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    //--------------------------------------------------------------------------- First Name Initials ---------------------------------------------------------------------------
    // Rule: program will not allow last name initials
    // Only matches with a maybe if it is first name
    @Test
    void doNamesMatch_Maybe_FirstNameInitials() {
        Name firstAndLastNameOne = new Name(" John  ", null, "  Doe ", null, null);
        Name firstAndLastNameTwo = new Name("j ", null, " dOe", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" John  ", "  Doe ", "j ", " dOe"));

        firstAndLastNameOne.setFullName("J. ", null, " Doe", null, null);
        firstAndLastNameTwo.setFullName("  joHn ", null, " doe  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("J. ", " Doe", "  joHn ", " doe  "));
    }

    //------------------------------------------------------------------------------- Other Tests -------------------------------------------------------------------------------
    @Test
    void doNamesMatch_YesMaybe_Apostrophe() {
        Name firstAndLastNameOne = new Name("  adriaNno ", null, "d'onOfio ", null, null);
        Name firstAndLastNameTwo = new Name(" adriaNno", null, " d onofiO  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  adriaNno ", "d'onOfio ", " adriaNno", " d onofiO  "));

        firstAndLastNameOne.setFullName(" mc  ", null, " d oNofio", null, null);
        firstAndLastNameTwo.setFullName("Josephine-mc-vitty ", null, "  d'onoFio ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" mc vitty  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "));
    }

    @Test
    void doNamesMatch_No_Apostrophe_NameCombined() { // Note: In the future, we may let this return type be decided by the user as yes or maybe.
        Name firstAndLastNameOne = new Name(" adriaNno  ", null, " doNofio", null, null);
        Name firstAndLastNameTwo = new Name("adriaNno ", null, "  d'onoFio ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    /*******************************************************************************************************************
     ********************************************** Compare Address Tests **********************************************
     *******************************************************************************************************************/
    private String addressFailedMsg(String addressOne, String addressTwo){
        return "<" + addressOne + "> -vs- <" + addressTwo + ">";
    }

    @Test
    void doAddressesMatch_Yes_IgnoreCase() {
        Address addressOne = new Address();
        addressOne.setFullAddress("123 main Street", null, "Tucson", "Arizona", "USA", null);
        Address addressTwo = new Address("123 Main street", null, "TUCSON", "ARIZONA", "usa", null);

        assertEquals(AnswerType.yes, CompareContactParts.doAddressesMatch(addressOne, addressTwo),
                addressFailedMsg(addressOne.toString(), addressTwo.toString()));
    }

    @Test
    void doAddressesMatch_Maybe_PartsMatch_HasMissingParts() {
        Address addressOne = new Address();
        Address addressTwo = new Address();

        addressOne.setStreet("123 main Street");
        addressOne.setCity("Tucson");
        addressOne.setState("Arizona");
        addressOne.setZip("85713");

        addressTwo.setStreet("123 Main street");
        addressTwo.setState("ARIZONA");

        assertEquals(AnswerType.maybe, CompareContactParts.doAddressesMatch(addressOne, addressTwo),
                addressFailedMsg(addressOne.toString(), addressTwo.toString()));
    }

    @Test
    void doAddressesMatch_No() {
        Address addressOne = new Address();
        Address addressTwo = new Address();

        addressOne.setStreet("123 Valencia street");
        addressOne.setCity("Tucson");
        addressOne.setState("Arizona");
        addressOne.setCountry("USA");

        addressTwo.setStreet("123 Main street");
        addressTwo.setCity("TUCSON");
        addressTwo.setState("ARIZONA");
        addressTwo.setCountry("usa");

        assertEquals(AnswerType.no, CompareContactParts.doAddressesMatch(addressOne, addressTwo),
                addressFailedMsg(addressOne.toString(), addressTwo.toString()));
    }

    /*******************************************************************************************************************
     **************************************** Compare Address Street Parts Tests ***************************************
     *******************************************************************************************************************/
    private String streetFailedMsg(String streetOne, String streetTwo){
        return "<firstApartment = " + streetOne + "> -vs- <secondApartment = " + streetTwo + ">";
    }

    @Test
    void doAddressStreetPartsMatch_Yes_IgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 main Street", "123 Main street"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfStreet() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 Main Street", "123 Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfAvenue() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 Main Avenue", "123 Main Ave"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfDrive() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 Main Dr", "123 Main Drive"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfLane() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 Main Lane", "123 Main Ln"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfTrail() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 Main Trail", "123 Main trl."));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfCircle() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 Main Circle", "123 Main Cir"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfBoulevard() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 Main Blvd", "123 Main Boulevard"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfRoad() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 Main Road", "123 Main Rd"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfPOBox() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("P.O. Box 1234", "PO Box 1234"),
                streetFailedMsg("P.O. Box 1234","PO Box 1234"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("P O Box 1234", "PO Box 1234"),
                streetFailedMsg("P O Box 1234","PO Box 1234"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("PO Box 1234", "Box 1234"),
                streetFailedMsg("PO Box 1234","Box 1234"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfNorth() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 North Main St", "123 N Main St"),
                streetFailedMsg("123 North Main St", "123 N Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 North Main St", "123 N. Main St"),
                streetFailedMsg("123 North Main St", "123 N. Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfNorthEast() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 NE Main St", "123 North-East Main St"),
                streetFailedMsg("123 NE Main St","123 North-East Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 NE Main St", "123 Northeast Main St"),
                streetFailedMsg("123 NE Main St","123 Northeast Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 NE Main St", "123 NE. Main St"),
                streetFailedMsg("123 NE Main St","123 NE. Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfNorthWest() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 NW Main St", "123 North-West Main St"),
                streetFailedMsg("123 NW Main St","123 North-West Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 NW Main St", "123 Northwest Main St"),
                streetFailedMsg("123 NW Main St","123 Northwest Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 NW Main St", "123 NW. Main St"),
                streetFailedMsg("123 NW Main St","123 NW. Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfSouth() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 S Main St", "123 South Main St"),
                streetFailedMsg("123 S Main St","123 South Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 S. Main St", "123 South Main St"),
                streetFailedMsg("123 S. Main St","123 South Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfSouthEast() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 SE Main St", "123 South-East Main St"),
                streetFailedMsg("123 SE Main St","123 South-East Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 SE Main St", "123 Southeast Main St"),
                streetFailedMsg("123 SE Main St","123 Southeast Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 SE Main St", "123 SE. Main St"),
                streetFailedMsg("123 SE Main St","123 SE. Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfSouthWest() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 SW Main St", "123 South-West Main St"),
                streetFailedMsg("123 SW Main St","123 South-West Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 SW Main St", "123 Southwest Main St"),
                streetFailedMsg("123 SW Main St","123 Southwest Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 SW Main St", "123 SW. Main St"),
                streetFailedMsg("123 SW Main St","123 SW. Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfEast() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 East Main St", "123 E Main St"),
                streetFailedMsg("123 East Main St","123 E Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 East Main St", "123 E. Main St"),
                streetFailedMsg("123 East Main St","123 E. Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_FormsOfWest() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 West Main St", "123 W Main St"),
                streetFailedMsg("123 West Main St","123 W Main St"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("123 West Main St", "123 W. Main St"),
                streetFailedMsg("123 West Main St","123 W. Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStreetPartsMatch("  123 Main St", "123  Main St  "));
    }

    @Test
    void doAddressStreetPartsMatch_Maybe_MissingRoadType() {
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressStreetPartsMatch("123 Main", "123 Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Maybe_MissingDirection() {
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressStreetPartsMatch("123 N Main St", "123 Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_Maybe_MissingDirectionAndRoadType() {
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressStreetPartsMatch("123 N Main", "123 Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_No_DifferentStreetAddress() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressStreetPartsMatch("123 Main St", "1234 Main St"));
    }

    @Test
    void doAddressStreetPartsMatch_No_MissingStreetAddress() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressStreetPartsMatch(null, "1234 Main St"),
                streetFailedMsg(null,"1234 Main St"));
        assertEquals(AnswerType.no, CompareContactParts.doAddressStreetPartsMatch("1234 Main St", ""),
                streetFailedMsg("1234 Main St",""));
    }

    /*******************************************************************************************************************
     ************************************** Compare Address Apartment Parts Tests **************************************
     *******************************************************************************************************************/
    private String apartmentFailedMsg(String apartmentOne, String apartmentTwo){
        return "<firstApartment = " + apartmentOne + "> -vs- <secondApartment = " + apartmentTwo + ">";
    }

    @Test
    void doAddressApartmentPartsMatch_Yes_FormsOfApartment() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressApartmentPartsMatch("Apt 40", "Apartment 40"),
                apartmentFailedMsg("Apt 40","Apartment 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressApartmentPartsMatch("Apt 40", "Ap 40"),
                apartmentFailedMsg("Apt 40","Ap 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressApartmentPartsMatch("Apt 40", "40"),
                apartmentFailedMsg("Apt 40","40"));
    }

    @Test
    void doAddressApartmentPartsMatch_Yes_FormsOfSuite() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressApartmentPartsMatch("Suite 40", "Ste 40"),
                apartmentFailedMsg("Suite 40","Ste 40"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressApartmentPartsMatch("Suite 40", "40"),
                apartmentFailedMsg("Suite 40","40"));
    }

    @Test
    void doAddressApartmentPartsMatch_Yes_FormsOfUnit() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressApartmentPartsMatch("Unit 40", "40"),
                apartmentFailedMsg("Unit 40","40"));

        assertEquals(AnswerType.yes, CompareContactParts.doAddressApartmentPartsMatch("#40", "40"),
                apartmentFailedMsg("#40","40"));
    }

    @Test
    void doAddressApartmentPartsMatch_No_MissingApartment() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressApartmentPartsMatch(null, "Apt 40"),
                apartmentFailedMsg("null","Apt 40"));

        assertEquals(AnswerType.no, CompareContactParts.doAddressApartmentPartsMatch("Apt 40", ""),
                apartmentFailedMsg("Apt 40","\"\""));
    }

    @Test
    void doAddressApartmentPartsMatch_No_DifferentApartment() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressApartmentPartsMatch("Unit 10", "Unit 11"),
                apartmentFailedMsg("Unit 10","Unit 11"));

        assertEquals(AnswerType.no, CompareContactParts.doAddressApartmentPartsMatch("J10", "L10"),
                apartmentFailedMsg("J10","L10"));
    }

    /*******************************************************************************************************************
     ***************************************** Compare Address City Parts Tests ****************************************
     *******************************************************************************************************************/
    private String cityFailedMsg(String cityOne, String cityTwo){
        return "<firstCity = " + cityOne + "> -vs- <secondCity = " + cityTwo + ">";
    }

    @Test
    void doAddressCityPartsMatch_Yes_IgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("Tucson", "TUCSON"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("Tucson  ", "Tucson"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_FormsOfNorth() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("North Las Vegas", "N Las Vegas"),
                cityFailedMsg("North Las Vegas","N Las Vegas"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("North Las Vegas", "N. Las Vegas"),
                cityFailedMsg("North Las Vegas","N. Las Vegas"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_FormsOfSouth(){
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("South Jordan", "S Jordan"),
                cityFailedMsg("South Jordan","S Jordan"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("South Jordan", "S. Jordan"),
                cityFailedMsg("South Jordan","S. Jordan"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_FormsOfEast(){
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("E Hartford", "East Hartford"),
                cityFailedMsg("E Hartford","East Hartford"));

        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("E. Hartford", "East Hartford"),
                cityFailedMsg("E. Hartford","East Hartford"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_FormsOfWest(){
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("W Haven", "West Haven"),
                cityFailedMsg("W Haven","West Haven"));

        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("W. Haven", "West Haven"),
                cityFailedMsg("W. Haven","West Haven"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_FormsOfNorthEast() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("NE Washington", "North-East Washington"),
                cityFailedMsg("NE Washington","North-East Washington"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("NE Washington", "Northeast Washington"),
                cityFailedMsg("NE Washington","Northeast Washington"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("NE Washington", "NE. Washington"),
                cityFailedMsg("NE Washington","NE. Washington"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_FormsOfNorthWest() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("NW Washington", "North-West Washington"),
                cityFailedMsg("NW Washington","North-West Washington"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("NW Washington", "Northwest Washington"),
                cityFailedMsg("NW Washington","Northwest Washington"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("NW Washington", "NW. Washington"),
                cityFailedMsg("NW Washington","NW. Washington"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_FormsOfSouthWest() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("South-West Washington", "SW Washington"),
                cityFailedMsg("South-West Washington","SW Washington"));

        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("Southwest Washington", "SW Washington"),
                cityFailedMsg("Southwest Washington","SW Washington"));

        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("SW. Washington", "SW Washington"),
                cityFailedMsg("SW. Washington","SW Washington"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_FormsOfSouthEast() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("South-East Washington", "SE Washington"),
                cityFailedMsg("South-East Washington","SE Washington"));

        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("Southeast Washington", "SE Washington"),
                cityFailedMsg("Southeast Washington","SE Washington"));

        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("SE. Washington", "SE Washington"),
                cityFailedMsg("SE. Washington","SE Washington"));
    }

    @Test
    void doAddressCityPartsMatch_Maybe_MissingDirection() {
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressCityPartsMatch("North Las Vegas", "Las Vegas"));
    }

    @Test
    void doAddressCityPartsMatch_Yes_NumbersInName() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCityPartsMatch("North 24 Parganas district", "North 24 Parganas district"));
    }

    @Test
    void doAddressCityPartsMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressCityPartsMatch("Tucson", "Phoenix"));
    }

    /*******************************************************************************************************************
     **************************************** Compare Address State Parts Tests ****************************************
     *******************************************************************************************************************/
    private String stateFailedMsg(String stateOne, String stateTwo){
        return "<firstState = " + stateOne + "> -vs- <secondState = " + stateTwo + ">";
    }

    @Test
    void doAddressStatePartsMatch_Yes_IgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStatePartsMatch("AZ", "az"));
    }

    @Test
    void doAddressStatePartsMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStatePartsMatch("az", " AZ    "));
    }

    @Test
    void doAddressStatePartsMatch_Yes_Abbreviations() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStatePartsMatch("AZ", "Arizona"),
                stateFailedMsg("AZ","Arizona"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStatePartsMatch("California", "CA"),
                stateFailedMsg("California","CA"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressStatePartsMatch("New York", "NY"),
                stateFailedMsg("New York","NY"));
    }

    @Test
    void doAddressStatePartsMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressStatePartsMatch("New York", "Arizona"),
                stateFailedMsg("New York","Arizona"));
        assertEquals(AnswerType.no, CompareContactParts.doAddressStatePartsMatch("Virginia", "West Virginia"),
                stateFailedMsg("Virginia","West Virginia"));
    }

    /*******************************************************************************************************************
     *************************************** Compare Address Country Parts Tests ***************************************
     *******************************************************************************************************************/
    private String countryFailedMsg(String countryOne, String countryTwo){
        return "<firstCountry = " + countryOne + "> -vs- <secondCountry = " + countryTwo + ">";
    }

    @Test
    void doAddressCountryPartsMatch_Yes_IgnoreCase() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCountryPartsMatch("United States of America", "united states of america"));
    }

    @Test
    void doAddressCountryPartsMatch_Yes_IgnoreSpaces() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCountryPartsMatch("   United States   of  America ", "united states of america"));
    }

    @Test
    void doAddressCountryPartsMatch_Yes_Abbreviations() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCountryPartsMatch("United States of America", "united states"),
                countryFailedMsg("United States of America", "united states"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCountryPartsMatch("us", "United States of America"),
                countryFailedMsg("us", "United States of America"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressCountryPartsMatch("United States of America", "usa"),
                countryFailedMsg("United States of America", "usa"));
    }

    @Test
    void doAddressCountryPartsMatch_no() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressCountryPartsMatch("United States", "United Kingdom"));
    }

    /*******************************************************************************************************************
     ***************************************** Compare Address Zip Parts Tests *****************************************
     *******************************************************************************************************************/
    private String zipFailedMsg(String zipOne, String zipTwo){
        return "<firstZip = " + zipOne + "> -vs- <secondZip = " + zipTwo + ">";
    }

    @Test
    void doAddressZipPartsMatch_Yes() {
        assertEquals(AnswerType.yes, CompareContactParts.doAddressZipPartsMatch("85713-1000", "85713-1000"), zipFailedMsg("85713-1000", "85713-1000"));
        assertEquals(AnswerType.yes, CompareContactParts.doAddressZipPartsMatch("85713", "85713"), zipFailedMsg("85713", "85713"));
    }

    @Test
    void doAddressZipPartsMatch_Maybe() {
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressZipPartsMatch("85713", "85713-1000"), zipFailedMsg("85713", "85713-1000"));
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressZipPartsMatch("85713", "85713-1004"), zipFailedMsg("85713", "85713-1004"));
        assertEquals(AnswerType.maybe, CompareContactParts.doAddressZipPartsMatch("85713", "85713-1008"), zipFailedMsg("85713", "85713-1008"));
    }

    @Test
    void doAddressZipPartsMatch_No() {
        assertEquals(AnswerType.no, CompareContactParts.doAddressZipPartsMatch("85713-1000", "85713-1001"), zipFailedMsg("85713-1000", "85713-1001"));
        assertEquals(AnswerType.no, CompareContactParts.doAddressZipPartsMatch("85713-1000", "85714-1000"), zipFailedMsg("85713-1000", "85714-1000"));
    }

    /*******************************************************************************************************************
     *********************************************** Compare Phone Tests ***********************************************
     *******************************************************************************************************************/
    private String phoneFailedMsg(String phoneNumberOne, String phoneNumberTwo){
        return "<" + phoneNumberOne + "> -vs- <" + phoneNumberTwo + ">";
    }

    @Test
    void doPhonesMatch_Yes_IgnoreSpaces() {
        Phone phone1 = new Phone("520 123 4567");
        Phone phone2 = new Phone("5201234567");

        assertEquals(AnswerType.yes, CompareContactParts.doPhonesMatch(phone1, phone2));
    }

    @Test
    void doPhonesMatch_Yes_IgnorePunctuation() {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();

        phone1.setFullNumber("(619) 123-4567");
        phone2.setFullNumber("6191234567");
        assertEquals(AnswerType.yes, CompareContactParts.doPhonesMatch(phone1, phone2),
                phoneFailedMsg("(619) 123-4567","6191234567"));

        phone2.setFullNumber("619-123-4567");
        assertEquals(AnswerType.yes, CompareContactParts.doPhonesMatch(phone1, phone2),
                phoneFailedMsg("(619) 123-4567","619-123-4567"));
    }

    @Test
    void doPhonesMatch_Maybe_MissingCountryCallingCode() {
        Phone phone1 = new Phone("1 (520) 123-4567");
        Phone phone2 = new Phone("5201234567");

        assertEquals(AnswerType.maybe, CompareContactParts.doPhonesMatch(phone1, phone2));
    }

    @Test
    void doPhonesMatch_Maybe_MissingAreaCode() {
        Phone phone1 = new Phone("(520) 123-4567");
        Phone phone2 = new Phone("1234567");

        assertEquals(AnswerType.maybe, CompareContactParts.doPhonesMatch(phone1, phone2),
                phoneFailedMsg("(520) 123-4567","1234567"));

        phone2.setFullNumber("123-4567");
        assertEquals(AnswerType.maybe, CompareContactParts.doPhonesMatch(phone1, phone2),
                phoneFailedMsg("(520) 123-4567","123-4567"));
    }

    @Test
    void doPhonesMatch_No() {
        Phone phone1 = new Phone("(520) 123-4567");
        Phone phone2 = new Phone("(520) 123-8567");

        assertEquals(AnswerType.no, CompareContactParts.doPhonesMatch(phone1, phone2));
    }

    @Test
    void doPhonesMatch_No_DifferentAreaCode() {
        Phone phone1 = new Phone("(520) 123-4567");
        Phone phone2 = new Phone("(619) 123-4567");

        assertEquals(AnswerType.no, CompareContactParts.doPhonesMatch(phone1, phone2));
    }

    @Test
    void doPhonesMatch_No_Missing() {
        Phone phone1 = new Phone("(520) 123-4567");
        Phone phone2 = new Phone();

        assertEquals(AnswerType.no, CompareContactParts.doPhonesMatch(phone1, phone2));
    }

    /*******************************************************************************************************************
     *********************************************** Compare Email Tests ***********************************************
     *******************************************************************************************************************/
    @Test
    void doEmailsMatch_Yes_IgnoreSpaces() {
        Email firstEmail = new Email(" jdoe@yahoo.com ");
        Email secondEmail = new Email("jdoe@yahoo.com");

        assertEquals(AnswerType.yes, CompareContactParts.doEmailsMatch(firstEmail, secondEmail));
    }

    @Test
    void isEmailAddressMatch_Maybe_IgnoreCase() {
        Email firstEmail = new Email("jdoe@yahoo.com");
        Email secondEmail = new Email("JDoe@Yahoo.com");

        assertEquals(AnswerType.maybe, CompareContactParts.doEmailsMatch(firstEmail, secondEmail));
    }

    @Test
    void doEmailsMatch_No() {
        Email firstEmail = new Email("jdoe@yahoo.com");
        Email secondEmail = new Email("jdoe@gmail.com");

        assertEquals(AnswerType.no, CompareContactParts.doEmailsMatch(firstEmail, secondEmail));
    }

    @Test
    void doEmailsMatch_No_Missing() {
        Email firstEmail = new Email();
        Email secondEmail = new Email("jdoe@yahoo.com");

        assertEquals(AnswerType.no, CompareContactParts.doEmailsMatch(firstEmail, secondEmail));
    }
}