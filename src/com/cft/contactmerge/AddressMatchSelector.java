package com.cft.contactmerge;

import java.util.ArrayList;

public abstract class AddressMatchSelector {
    /*******************************************************************************************************************
     *********************************************** Street Address Only ***********************************************
     *******************************************************************************************************************/
    public static AnswerType getStreetAddressMatchResultDefaultComparisons(String stAddress1, String stAddress2){
        /* ToDo: Fill in code to return match result for street address...
         *       Normalize stAddress1 and stAddress2 into their own ArrayList for Matching
         *          Hint: use StreetAddressMatchLogic.setNormalizeStreetAddress(stAddress)
         *       Use StreetAddressMatchLogic.getStreetAddressMatchResult(stAddress1, stAddress2)
         *          to get the return of AnswerType.
         */
        return AnswerType.no;
    }

    public static AnswerType getStreetAddressMatchResultUsrSelectedComparisons(
            String stAddress, ArrayList<String> matchingOptionsSelected){
        /* ToDo: If the user has special matching requirements,
         *       Fill in code to return results with the user selected
         *       matching requirements as a condition
         *       note: "ArrayList<String> matchingOptionsSelected" can be changed or
         *             removed (with location hard coded somewhere) or
         *             whatever else is decided in the future
         */
        return AnswerType.no;
    }
    /*******************************************************************************************************************
     ************************************ Street Address, City, State, Zip, Country ************************************
     *******************************************************************************************************************/
    public static AnswerType getStreetAddressCityStateZipCountryMatchResultDefaultComparisons(
            String stAdress, String city, String state, String Zip, String country){
        // ToDo: Fill in code to return a result based on the group of these results found
        return AnswerType.no;
    }

    public static AnswerType getStreetAddressCityStateZipCountryMatchResultUsrSelectedComparisons(
            String stAddress1, String stAddress2, String city1, String city2,
            String state1, String state2, String Zip1, String zip2,
            String country1, String country2, ArrayList<String> matchingOptionsSelected){
        /* ToDo: If the user has special matching requirements,
         *       Fill in code to return results with the user selected
         *       matching requirements as a condition
         *       note: "ArrayList<String> matchingOptionsSelected" can be changed or
         *             removed (with location hard coded somewhere) or
         *             whatever else is decided in the future
         */
        return AnswerType.no;
    }
}
