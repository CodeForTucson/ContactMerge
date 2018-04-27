package com.cft.contactmerge;

public class CompareContactParts {
    public static AnswerType doNamesMatch(String firstName1, String lastName1, String firstName2, String lastName2)
    {
        return FirstAndLastNamesMatchLogic.doFirstAndLastNamesMatchUsingAllComparisonChecks(firstName1, lastName1, firstName2, lastName2);
    }

    public static AnswerType doAddressesMatch(String address1, String address2)
    {
        return null;
    }

    public static AnswerType doCitiesMatch(String city1, String city2)
    {
        return null;
    }

    public static AnswerType doStatesMatch(String state1, String state2)
    {
        return null;
    }

    public static AnswerType doPhoneNumbersMatch(String phoneNumber1, String phoneNumber2)
    {
        return null;
    }

    public static AnswerType doEmailsMatch(String email1, String email2)
    {
        return null;
    }
}
