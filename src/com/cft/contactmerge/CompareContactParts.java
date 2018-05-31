package com.cft.contactmerge;

import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.Email;
import com.cft.contactmerge.contact.NameMatchSelector;
import com.cft.contactmerge.contact.Phone;

public class CompareContactParts {
    public static AnswerType doNamesMatch(String firstName1, String lastName1, String firstName2, String lastName2)
    {
        return NameMatchSelector.getFirstLastNamesMatchResultDefaultComparisons(firstName1, lastName1, firstName2, lastName2);
    }

    public static AnswerType doAddressesMatch(Address addressOne, Address addressTwo)
    {
        return addressOne.isMatch(addressTwo);
    }

    public static AnswerType doAddressStreetPartsMatch(String firstStreet, String secondStreet)
    {
        return Address.isStreetMatch(firstStreet, secondStreet);
    }

    public static AnswerType doAddressApartmentPartsMatch(String firstApartment, String secondApartment)
    {
        return Address.isApartmentMatch(firstApartment, secondApartment);
    }

    public static AnswerType doAddressCityPartsMatch(String firstCity, String secondCity)
    {
        return Address.isCityMatch(firstCity, secondCity);
    }

    public static AnswerType doAddressStatePartsMatch(String firstState, String secondState)
    {
        return Address.isStateMatch(firstState, secondState);
    }

    public static AnswerType doAddressCountryPartsMatch(String firstCountry, String secondCountry)
    {
        return Address.isCountryMatch(firstCountry, secondCountry);
    }

    public static AnswerType doAddressZipPartsMatch(String firstZip, String secondZip)
    {
        return Address.isZipMatch(firstZip, secondZip);
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
