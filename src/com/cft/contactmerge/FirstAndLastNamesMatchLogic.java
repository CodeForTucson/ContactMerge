package com.cft.contactmerge;

public abstract class FirstAndLastNamesMatchLogic {
    private final static String[] namePrefixes = {"Mr", "Mrs", "Miss", "Dr"};
    private final static String[][] alternateFirstNames = // List of names to match nicknames of the same person
    {
            {"Al", "Allen"},
            {"Al", "Alan"},
            {"Bob", "Robert"}
    };

    public static AnswerType doFirstAndLastNamesMatch(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        return getMatchingResultFromMatchingMethodsList(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName);
    }

    private static AnswerType getMatchingResultFromMatchingMethodsList(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        // Do first and last names have an exact match (case sensitive)? If so, return yes.
        if (isFirstAndLastNamesExactMatchCaseSensitive(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName)){
            return AnswerType.yes;
        }

        // Do first and last names have an exact match (case Insensitive)? If so, return yes.

        // Does a last name contain a prefix, if so, do last names match without the prefixes? If so, return yes.

        // Do first names have an alternate name, but the names still match as the same person? If so, return maybe.

        return AnswerType.no;
    }

    private static boolean isFirstAndLastNamesExactMatchCaseSensitive(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        return (contactOneFirstName.equals(contactTwoFirstName) && contactOneLastName.equals(contactTwoLastName));
    }

    private static AnswerType isFirstAndLastNamesExactMatchCaseInsensitive(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        // fill in matching code here

        return AnswerType.no;
    }
}
