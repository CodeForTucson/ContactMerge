package com.cft.contactmerge;

import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.Email;
import com.cft.contactmerge.contact.Phone;

public class CompareContactParts {
    public static AnswerType doNamesMatch(String firstName1, String lastName1, String firstName2, String lastName2)
    {
        return NameMatchSelector.getFirstLastNamesMatchResultDefaultComparisons(firstName1, lastName1, firstName2, lastName2);
    }

    public static AnswerType doAddressesMatch(Contact contact, Address otherAddress)
    {
        return contact.getAddress().isAddressMatch(otherAddress);
    }

    public static AnswerType doStreetAddressesMatch(Contact contact, String otherStreetAddress)
    {
        return contact.getAddress().isStreetAddressMatch(otherStreetAddress);
    }

    public static AnswerType doApartmentAddressesMatch(Contact contact, String otherApartmentAddress)
    {
        return contact.getAddress().isApartmentMatch(otherApartmentAddress);
    }

    public static AnswerType doCitiesMatch(Contact contact, String otherCity)
    {
        return contact.getAddress().isCityMatch(otherCity);
    }

    public static AnswerType doStatesMatch(Contact contact, String otherState)
    {
        return contact.getAddress().isStateMatch(otherState);
    }

    public static AnswerType doCountriesMatch(Contact contact, String otherCountry)
    {
        return contact.getAddress().isCountryMatch(otherCountry);
    }

    public static AnswerType doZipsMatch(Contact contact, String otherZip)
    {
        return contact.getAddress().isZipMatch(otherZip);
    }

    public static AnswerType doPhonesMatch(Phone firstPhone, Phone secondPhone)
    {
        return firstPhone.isMatch(secondPhone);
    }

    public static AnswerType doEmailsMatch(Email firstEmail, Email secondEmail)
    {
        return firstEmail.isMatch(secondEmail);
    }
}
