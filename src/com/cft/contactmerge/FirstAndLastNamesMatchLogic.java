package com.cft.contactmerge;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class FirstAndLastNamesMatchLogic {
    private final static String[] namePrefixes = {"Mr", "Mrs", "Miss", "Dr"};
    private final static String[][] alternateFirstNames = // List of names to match nicknames of the same person
    {
            {"Al", "Allen"},
            {"Al", "Alan"},
            {"Bob", "Robert"}
    };
    private final static String[] namePunctuations = {".", ",", "'", "-"};

    /***********************************************
     * Method(s) used to call specific comparisons *
     ***********************************************/

    public static AnswerType doFirstAndLastNamesMatchUsingAllComparisonChecks(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        return getMatchingResultFromMatchingMethodsList(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName);
    }

    /*************************************
     * Use all logical comparison method *
     *************************************/

    private static AnswerType getMatchingResultFromMatchingMethodsList(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        // Do first and last names have an exact match (case sensitive)? If so, return yes.
        if (isMatchMatchCaseSensitive(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName)){
            return AnswerType.yes;
        }

        // Do first and last names have an exact match (case Insensitive)? If so, return yes.
        if (isMatchCaseInsensitive(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName)){
            return AnswerType.yes;
        }

        // Does last name and first name contain white spaces at the start and/or end of the value(s)? if so,
        // compare without the whitespaces at the start and/or end of the value(s)...
        // return yes if the names match without the white spaces at start/end of the name value(s).
        if (isMatchWithWhiteSpaces(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName)){
            return AnswerType.yes;
        }

        // Does last name and first name contain white spaces at the start and/or end of the value(s), and uppercase not matching?
        // if so, compare without the whitespaces at the start and/or end of the value(s), and match if the names match even with different casing...
        // return yes if the names match without the white spaces at start/end of the name value(s).
        if (isMatchWithWhiteSpaces_CaseInsensitive(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName)){
            return AnswerType.yes;
        }

        if (isMatchWithPunctuations(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName)){
            return AnswerType.yes;
        }

        if (isMatchWithHyphenatedNames(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName)){
            return AnswerType.maybe;
        }

        if (isFirstNameAndLastNameSwitched(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName)){
            return AnswerType.maybe;
        }

        if (isInitialMatchNameWithoutPrefix(contactOneFirstName, contactOneLastName, contactTwoFirstName, contactTwoLastName)){
            return AnswerType.maybe;
        }

        // Does a last name contain a prefix, if so, do last names match without the prefixes? If so, return yes.

        // Do first names have an alternate name, but the names still match as the same person? If so, return maybe.

        return AnswerType.no;
    }

    /************************
     * Compare Name Methods *
     ************************/

    private static boolean isMatchMatchCaseSensitive(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        return (contactOneFirstName.equals(contactTwoFirstName) && contactOneLastName.equals(contactTwoLastName));
    }

    private static boolean isMatchCaseInsensitive(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        return (contactOneFirstName.equalsIgnoreCase(contactTwoFirstName) && contactOneLastName.equalsIgnoreCase(contactTwoLastName));
    }

    private static boolean isMatchWithWhiteSpaces(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        return (contactOneFirstName.trim().equals(contactTwoFirstName.trim()) && contactOneLastName.trim().equals(contactTwoLastName.trim()));
    }

    private static boolean isMatchWithWhiteSpaces_CaseInsensitive(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        return (contactOneFirstName.trim().equalsIgnoreCase(contactTwoFirstName.trim()) && contactOneLastName.trim().equalsIgnoreCase(contactTwoLastName.trim()));
    }

    private static boolean isMatchWithPunctuations(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        return (replacePunctuationsInNameWithWhiteSpace(contactOneFirstName).equals(replacePunctuationsInNameWithWhiteSpace(contactTwoFirstName)) &&
                replacePunctuationsInNameWithWhiteSpace(contactOneLastName).equals(replacePunctuationsInNameWithWhiteSpace(contactTwoLastName)));
    }

    private static boolean isMatchWithHyphenatedNames(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        // Also checks names if they have more than one hyphen in them
        // Splits the name(s) into arrays, and runs a compare check.
        // If any match, returns true (for every compare check).
        // If both first and last name compare checks return true, method returns true.

        ArrayList<String> contactOneLastNamesWithoutHyphens = seperateHypenNames(contactOneFirstName);
        ArrayList<String> contactOneFirstNamesWithoutHyphens = seperateHypenNames(contactOneLastName);
        ArrayList<String> contactTwoFirstNamesWithoutHyphens = seperateHypenNames(contactTwoFirstName);
        ArrayList<String> contactTwoLastNamesWithoutHyphens = seperateHypenNames(contactTwoLastName);
        boolean firstNamesMatch = isNameInGroupNamesMatch(contactOneLastNamesWithoutHyphens, contactTwoFirstNamesWithoutHyphens);
        boolean lastNamesMatch = isNameInGroupNamesMatch(contactOneFirstNamesWithoutHyphens, contactTwoLastNamesWithoutHyphens);

        return (firstNamesMatch & lastNamesMatch);
    }

    private static boolean isFirstNameAndLastNameSwitched(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        return (contactOneFirstName.equals(contactTwoLastName) && contactOneLastName.equals(contactTwoFirstName));
    }

    private static boolean isInitialMatchNameWithoutPrefix(String contactOneFirstName, String contactOneLastName, String contactTwoFirstName, String contactTwoLastName){
        String contactOneFirstNameWithoutPunctuations = removePunctuationsInName(contactOneFirstName);
        String contactOneLastNameWithoutPunctuations = removePunctuationsInName(contactOneLastName);
        String contactTwoFirstNameWithoutPunctuations = removePunctuationsInName(contactTwoFirstName);
        String contactTwoLastNameWithoutPunctuations = removePunctuationsInName(contactTwoLastName);
        boolean firstNameIsInitialOf2ndFirstName = false;
        boolean lastNameIsInitialOf2ndLastName = false;
        boolean firstNamesMatchFound = false;
        boolean lastNamesMatchFound = false;

        // First name is an initial
        if (isNameInitial(contactOneFirstNameWithoutPunctuations)){
            // Initial matches the first name
            if (contactTwoFirstNameWithoutPunctuations.startsWith(contactOneFirstNameWithoutPunctuations)){
                firstNameIsInitialOf2ndFirstName = true;
            }
        } else if (isNameInitial(contactTwoFirstNameWithoutPunctuations)){
            if (contactOneFirstNameWithoutPunctuations.startsWith(contactTwoFirstNameWithoutPunctuations)){
                firstNameIsInitialOf2ndFirstName = true;
            }
        }

        // Last name is an initial
        if (isNameInitial(contactOneLastNameWithoutPunctuations)){
            // Initial matches the last name
            if (contactTwoLastNameWithoutPunctuations.startsWith(contactOneLastNameWithoutPunctuations)){
                lastNameIsInitialOf2ndLastName = true;
            }
        } else if (isNameInitial(contactTwoLastNameWithoutPunctuations)){
            if (contactOneLastNameWithoutPunctuations.startsWith(contactTwoLastNameWithoutPunctuations)){
                lastNameIsInitialOf2ndLastName = true;
            }
        }

        // If names were not an initial, just check if they match.
        if (!firstNameIsInitialOf2ndFirstName){
            if (contactOneFirstNameWithoutPunctuations.equals(contactTwoFirstNameWithoutPunctuations)){
                firstNamesMatchFound = true;
            }
        }

        // If names were not an initial, just check if they match.
        if (!lastNameIsInitialOf2ndLastName){
            if (contactOneLastNameWithoutPunctuations.equals(contactTwoLastNameWithoutPunctuations)){
                lastNamesMatchFound = true;
            }
        }

        // Return matching logic
        if ((firstNameIsInitialOf2ndFirstName && lastNameIsInitialOf2ndLastName) ||
                (firstNameIsInitialOf2ndFirstName && lastNamesMatchFound) ||
                (firstNamesMatchFound && lastNameIsInitialOf2ndLastName) ||
                (firstNamesMatchFound && lastNamesMatchFound)){
            return true;
        }
        return false;
    }

    /*******************
     * Support Methods *
     *******************/

    private static boolean isNameInGroupNamesMatch(ArrayList<String> namesOne, ArrayList<String> namesTwo){
        for (String name: namesOne){
            if (namesTwo.contains(name)){
                return true;
            }
        }
        return false;
    }

    private static boolean isNameInitial(String name){
        return name.length() == 1;
    }

    private static ArrayList<String> seperateHypenNames(String name){
        return new ArrayList<>(Arrays.asList(name.split("-",-1)));
    }

    private static String replacePunctuationsInNameWithWhiteSpace(String name){
        String nameWithoutPunctuations = name;

        for (String punctuation: namePunctuations){
            nameWithoutPunctuations = nameWithoutPunctuations.replace(punctuation, " ");
        }
        return nameWithoutPunctuations;
    }

    private static String removePunctuationsInName(String name){
        String nameWithoutPunctuations = name;

        for (String punctuation: namePunctuations){
            nameWithoutPunctuations = nameWithoutPunctuations.replace(punctuation, "");
        }
        return nameWithoutPunctuations;
    }
}
