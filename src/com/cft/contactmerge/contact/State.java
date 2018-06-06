package com.cft.contactmerge.contact;

import java.util.HashMap;

public class State extends GeneralProperty {
    private static HashMap<String, String> nameToAbbreviationMap = null;

    public State(String state) {
        super(state);

        if (nameToAbbreviationMap == null) {
            nameToAbbreviationMap = new HashMap<String, String>(80);
            nameToAbbreviationMap.put("alabama", "AL");
            nameToAbbreviationMap.put("alaska", "AK");
            nameToAbbreviationMap.put("arizona", "AZ");
            nameToAbbreviationMap.put("arkansas", "AR");
            nameToAbbreviationMap.put("california", "CA");
            nameToAbbreviationMap.put("colorado", "CO");
            nameToAbbreviationMap.put("connecticut", "CT");
            nameToAbbreviationMap.put("delaware", "DE");
            nameToAbbreviationMap.put("florida", "FL");
            nameToAbbreviationMap.put("georgia", "GA");
            nameToAbbreviationMap.put("hawaii", "HI");
            nameToAbbreviationMap.put("idaho", "ID");
            nameToAbbreviationMap.put("illinois", "IL");
            nameToAbbreviationMap.put("indiana", "IN");
            nameToAbbreviationMap.put("iowa", "IA");
            nameToAbbreviationMap.put("kansas", "KS");
            nameToAbbreviationMap.put("kentucky", "KY");
            nameToAbbreviationMap.put("louisiana", "LA");
            nameToAbbreviationMap.put("maine", "ME");
            nameToAbbreviationMap.put("maryland", "MD");
            nameToAbbreviationMap.put("massachusetts", "MA");
            nameToAbbreviationMap.put("michigan", "MI");
            nameToAbbreviationMap.put("minnesota", "MN");
            nameToAbbreviationMap.put("mississippi", "MS");
            nameToAbbreviationMap.put("missouri", "MO");
            nameToAbbreviationMap.put("montana", "MT");
            nameToAbbreviationMap.put("nebraska", "NE");
            nameToAbbreviationMap.put("nevada", "NV");
            nameToAbbreviationMap.put("new hampshire", "NH");
            nameToAbbreviationMap.put("new jersey", "NJ");
            nameToAbbreviationMap.put("new mexico", "NM");
            nameToAbbreviationMap.put("new york", "NY");
            nameToAbbreviationMap.put("north carolina", "NC");
            nameToAbbreviationMap.put("north dakota", "ND");
            nameToAbbreviationMap.put("ohio", "OH");
            nameToAbbreviationMap.put("oklahoma", "OK");
            nameToAbbreviationMap.put("oregon", "OR");
            nameToAbbreviationMap.put("pennsylvania", "PA");
            nameToAbbreviationMap.put("rhode island", "RI");
            nameToAbbreviationMap.put("south carolina", "SC");
            nameToAbbreviationMap.put("south dakota", "SD");
            nameToAbbreviationMap.put("tennessee", "TN");
            nameToAbbreviationMap.put("texas", "TX");
            nameToAbbreviationMap.put("utah", "UT");
            nameToAbbreviationMap.put("vermont", "VT");
            nameToAbbreviationMap.put("virginia", "VA");
            nameToAbbreviationMap.put("washington", "WA");
            nameToAbbreviationMap.put("west virginia", "WV");
            nameToAbbreviationMap.put("wisconsin", "WI");
            nameToAbbreviationMap.put("wyoming", "WY");

            // US Commonwealth and Territories
            nameToAbbreviationMap.put("american samoa", "AS");
            nameToAbbreviationMap.put("district of columbia", "DC");
            nameToAbbreviationMap.put("federated states of micronesia", "FM");
            nameToAbbreviationMap.put("micronesia", "FM");
            nameToAbbreviationMap.put("guam", "GU");
            nameToAbbreviationMap.put("marshall islands", "MH");
            nameToAbbreviationMap.put("northern mariana islands", "MP");
            nameToAbbreviationMap.put("palau", "PW");
            nameToAbbreviationMap.put("puerto rico", "PR");
            nameToAbbreviationMap.put("virgin islands", "VI");

            // Canadian Provinces
            nameToAbbreviationMap.put("alberta", "AB");
            nameToAbbreviationMap.put("british columbia", "BC");
            nameToAbbreviationMap.put("manitoba", "MB");
            nameToAbbreviationMap.put("new brunswick", "NB");
            nameToAbbreviationMap.put("newfoundland and labrador", "NL");
            nameToAbbreviationMap.put("newfoundland", "NL");
            nameToAbbreviationMap.put("northwest territories", "NT");
            nameToAbbreviationMap.put("nova scotia", "NS");
            nameToAbbreviationMap.put("nunavut", "NU");
            nameToAbbreviationMap.put("ontario", "ON");
            nameToAbbreviationMap.put("prince edward island", "PE");
            nameToAbbreviationMap.put("pei", "PE");
            nameToAbbreviationMap.put("quebec", "QC");
            nameToAbbreviationMap.put("saskatchewan", "SK");
            nameToAbbreviationMap.put("yukon", "YT");
        }

        String lowerCaseValue = this.value.toLowerCase();

        if (nameToAbbreviationMap.containsKey(lowerCaseValue)) {
            this.value = nameToAbbreviationMap.get(lowerCaseValue);
        }
    }
}
