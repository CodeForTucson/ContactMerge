package com.cft.contactmerge.tests;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.IContact;
import com.cft.contactmerge.ContactMatchResult;
import com.cft.contactmerge.ContactMatchType;
import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.Email;
import com.cft.contactmerge.contact.Name;
import com.cft.contactmerge.contact.Phone;
import com.cft.contactmerge.contact.Zip;
import com.cft.contactmerge.contact.State;
import com.cft.contactmerge.contact.StreetAddress;
import com.cft.contactmerge.contact.FirstName;
import com.cft.contactmerge.contact.LastName;
import com.cft.contactmerge.contact.GeneralProperty;
import org.junit.jupiter.api.Test;
import com.cft.contactmerge.Contact;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContactTest {
    @Test
    void Constructor() {
        Contact newContact = new Contact();

        assertNotNull(newContact);
    }

    @Test
    void setName() {
        Contact newContact = new Contact();
        Name name = new Name("john", "leon", "adams", "dr", "III");
        newContact.setName(name);

        assertEquals("john", newContact.getName().getFirstName());
        assertEquals("leon", newContact.getName().getMiddleName());
        assertEquals("adams", newContact.getName().getLastName());
        assertEquals("dr", newContact.getName().getPrefix());
        assertEquals("III", newContact.getName().getSuffix());
    }

    @Test
    void setAddress() {
        Contact newContact = new Contact();
        Address address = new Address("ghi", "4", "hts", "az", "usa", "85713");
        newContact.setAddress(address);
        assertEquals("ghi", newContact.getAddress().getStreet());
        assertEquals("4", newContact.getAddress().getApartment());
        assertEquals("hts", newContact.getAddress().getCity());
        assertEquals("az", newContact.getAddress().getState());
        assertEquals("usa", newContact.getAddress().getCountry());
        assertEquals("85713", newContact.getAddress().getZip());
    }

    @Test
    void setPhone() {
        Contact newContact = new Contact();
        Phone phone = new Phone("5207734512");
        newContact.setPhone(phone);
        assertEquals("520", newContact.getPhone().getAreaCode());
        assertEquals("7734512", newContact.getPhone().getPhoneNumber());
    }

    @Test
    void setEmail() {
        Contact newContact = new Contact();
        Email email = new Email("jfk@yahoo.com");
        newContact.setEmail(email);
        assertEquals("jfk@yahoo.com", newContact.getEmail().getEmailAddress());
    }

    /* -------------------------------------------------------------------------
     * There are a large number of CompareTo tests which are broken into steps
     * reflecting the rules used to determine if a ContactToMerge matches an
     * ExistingContact.
     * -------------------------------------------------------------------------
     */

    // Helper methods for tests
    private Contact createBaseContact()
    {
        Contact contact = new Contact();
        contact.setName(new Name(new LastName("Doe"), new FirstName("John")));
        contact.setAddress(new Address(new StreetAddress("123 Main St"),
                null,
                new GeneralProperty("Tucson"),
                new State("AZ"),
                new Zip("85750")));
        contact.setPhone(new Phone("(520) 123-4567"));
        contact.setEmail(new Email("jdoe@gmail.com"));

        return contact;
    }

    // Creates a stub so that compareTo() can execute. The Contact object will never actually compare
    // its parts with this object.
    private IContact createContactStub(boolean addressSet, boolean phoneSet, boolean emailSet)
    {
        Name nameMock = mock(Name.class);

        Address addressMock = null;

        if (addressSet) {
            addressMock = mock(Address.class);
//        when(streetAddressMock.getValue()).thenReturn("This is where you would find the StreetAddress value");
        }

        Phone phoneMock = null;

        if (phoneSet) {
            phoneMock = mock(Phone.class);
        }

        Email emailMock = null;

        if (emailSet) {
            emailMock = mock(Email.class);
        }

        IContact contactToCompareWith = mock(Contact.class);
        when(contactToCompareWith.getName()).thenReturn(nameMock);
        when(contactToCompareWith.getAddress()).thenReturn(addressMock);
        when(contactToCompareWith.getPhone()).thenReturn(phoneMock);
        when(contactToCompareWith.getEmail()).thenReturn(emailMock);

        return contactToCompareWith;
    }

    // Executes a specific test, verifies the result, and verifies that the internal name parts
    // are called as expected.
    private void runIsMatchTest(AnswerType answerTypeForNameIsMatch,
                                AnswerType answerTypeForAddressIsMatch,
                                AnswerType answerTypeForPhoneIsMatch,
                                AnswerType answerTypeForEmailIsMatch,
                                ContactMatchType expectedMatchResult,
                                boolean targetAddressSet,
                                boolean targetPhoneSet,
                                boolean targetEmailSet)
    {
        // Set up Address with mock internals
        Name nameMock = mock(Name.class);
        when(nameMock.isMatch(any())).thenReturn(answerTypeForNameIsMatch);

        Address addressMock = null;
        boolean sourceAddressSet = answerTypeForAddressIsMatch != null;

        if (sourceAddressSet) {
            addressMock = mock(Address.class);
            when(addressMock.isMatch(any())).thenReturn(answerTypeForAddressIsMatch);
        }

        Phone phoneMock = null;
        boolean sourcePhoneSet = answerTypeForPhoneIsMatch != null;

        if (sourcePhoneSet) {
            phoneMock = mock(Phone.class);
            when(phoneMock.isMatch(any())).thenReturn(answerTypeForPhoneIsMatch);
        }

        Email emailMock = null;
        boolean sourceEmailSet = answerTypeForEmailIsMatch != null;

        if (sourceEmailSet) {
            emailMock = mock(Email.class);
            when(emailMock.isMatch(any())).thenReturn(answerTypeForEmailIsMatch);
        }

        Contact contactWithMockInternals = new Contact();
        // TODO: Switch to use constructor for initialization once that is set up
        contactWithMockInternals.setName(nameMock);
        contactWithMockInternals.setAddress(addressMock);
        contactWithMockInternals.setPhone(phoneMock);
        contactWithMockInternals.setEmail(emailMock);

        IContact contactToComparewith = createContactStub(targetAddressSet, targetPhoneSet, targetEmailSet);

        // Run test
        ContactMatchResult result = contactWithMockInternals.compareTo(contactToComparewith);
        assertEquals(expectedMatchResult, result.getMatchType());

        // Verify expected internals were called
        verify(nameMock).isMatch(any());

        if (sourceAddressSet && targetAddressSet) {
            verify(addressMock).isMatch(any());
        }

        if (sourcePhoneSet && targetPhoneSet) {
            verify(phoneMock).isMatch(any());
        }

        if (sourceEmailSet && targetEmailSet) {
            verify(emailMock).isMatch(any());
        }
    }

    // This version does not set the target property if the corresponding source property is unset.
    private void runIsMatchTest(AnswerType answerTypeForNameIsMatch,
                                AnswerType answerTypeForAddressIsMatch,
                                AnswerType answerTypeForPhoneIsMatch,
                                AnswerType answerTypeForEmailIsMatch,
                                ContactMatchType expectedMatchResult) {
        runIsMatchTest(answerTypeForNameIsMatch,
                answerTypeForAddressIsMatch,
                answerTypeForPhoneIsMatch,
                answerTypeForEmailIsMatch,
                expectedMatchResult,
                answerTypeForAddressIsMatch != null,
                answerTypeForPhoneIsMatch != null,
                answerTypeForEmailIsMatch != null);
    }

    // Step 1 - Return ContactMatchType.NoMatch if nothing is specified in the
    // ContactToMerge or the ExistingContact
    // TODO: The following tests won't be valid when Name is required
    @Test
    void compareTo_NoMatch_NothingSpecifiedInContactToMerge()
    {
        Contact c1 = new Contact();
        Contact c2 = createBaseContact();

        assertEquals(ContactMatchType.NoMatch, c1.compareTo(c2).getMatchType());
    }

    @Test
    void compareTo_NoMatch_NothingSpecifiedInExistingContact()
    {
        Contact c1 = createBaseContact();
        Contact c2 = new Contact();

        assertEquals(ContactMatchType.NoMatch, c1.compareTo(c2).getMatchType());
    }

    // Step 2 - Return ContactMatchType.Identical if all the parts that are specified in
    // either Contact match
    @Test
    void compareTo_Identical_AllParts()
    {
        runIsMatchTest(AnswerType.yes, AnswerType.yes, AnswerType.yes, AnswerType.yes, ContactMatchType.Identical);
    }

    @Test
    void compareTo_Identical_AddressMissing()
    {
        runIsMatchTest(AnswerType.yes, null, AnswerType.yes, AnswerType.yes, ContactMatchType.Identical);
    }

    @Test
    void compareTo_Identical_PhoneMissing()
    {
        runIsMatchTest(AnswerType.yes, AnswerType.yes, null, AnswerType.yes, ContactMatchType.Identical);
    }

    @Test
    void compareTo_Identical_EmailMissing()
    {
        runIsMatchTest(AnswerType.yes, AnswerType.yes, AnswerType.yes, null, ContactMatchType.Identical);
    }

    // Step 3 - Return ContactMatchType.NoMatch if all of the parts that are specified
    // in ContactToMerge do not match the ExistingContact
    @Test
    void compareTo_NoMatch_AllParts() {
        runIsMatchTest(AnswerType.no, AnswerType.no, AnswerType.no, AnswerType.no, ContactMatchType.NoMatch);
    }

    @Test
    void compareTo_NoMatch_AddressMissing()
    {
        runIsMatchTest(AnswerType.no, null, AnswerType.no, AnswerType.no, ContactMatchType.NoMatch);
    }

    @Test
    void compareTo_NoMatch_PhoneMissing()
    {
        runIsMatchTest(AnswerType.no, AnswerType.no, null, AnswerType.no, ContactMatchType.NoMatch);
    }

    @Test
    void compareTo_NoMatch_EmailMissing()
    {
        runIsMatchTest(AnswerType.no, AnswerType.no, AnswerType.no, null, ContactMatchType.NoMatch);
    }

    // Step 4 - Return ContactMatchType.Match if the Name and at least one of the other
    // parts (Address, Phones, or Email) match and if all the parts in the ContactToMerge
    // that don't match are empty or null

    @Test
    void compareTo_Match_NameAndAddress()
    {
        runIsMatchTest(AnswerType.yes, AnswerType.yes, null,
                null, ContactMatchType.Match,
                true, true, true);
    }

    @Test
    void compareTo_Match_NameAndEmail()
    {
        runIsMatchTest(AnswerType.yes, null, null,
                AnswerType.yes, ContactMatchType.Match, true, true, true);
    }

    @Test
    void compareTo_Match_NameAndPhone()
    {
        runIsMatchTest(AnswerType.yes, null, AnswerType.yes,
                null, ContactMatchType.Match,
                true, true, true);
    }

    @Test
    void compareTo_PotentialMatch_OnlyNameSpecified()
    {
        runIsMatchTest(AnswerType.yes, null, null,
                null, ContactMatchType.PotentialMatch,
                true, true, true);
    }

    // Step 5 - Return ContactMatchType.MatchButModifying if the Name and at least one
    // of the other parts (Address, Phones, or Email) match but there is at least one
    // part that doesn't match

    @Test
    void compareTo_MatchButModifying_NameAndAddress()
    {
        runIsMatchTest(AnswerType.yes, AnswerType.yes, AnswerType.no, AnswerType.no, ContactMatchType.MatchButModifying);
    }

    @Test
    void compareTo_MatchButModifying_NameAndEmail()
    {
        runIsMatchTest(AnswerType.yes, AnswerType.no, AnswerType.no, AnswerType.yes, ContactMatchType.MatchButModifying);
    }

    @Test
    void compareTo_MatchButModifying_NameAndPhone()
    {
        runIsMatchTest(AnswerType.yes, AnswerType.no, AnswerType.yes, AnswerType.no, ContactMatchType.MatchButModifying);
    }

    // Step 6 - Return ContactMatchType.PotentialMatch if the Names might match
    // or the Name does match but nothing else matches
    @Test
    void compareTo_PotentialMatch_OnlyNameMatches()
    {
        runIsMatchTest(AnswerType.yes, AnswerType.no, AnswerType.no, AnswerType.no, ContactMatchType.PotentialMatch);
    }

    @Test
    void compareTo_PotentialMatch_NameMightMatch()
    {
        runIsMatchTest(AnswerType.maybe, AnswerType.no, AnswerType.no, AnswerType.no, ContactMatchType.PotentialMatch);
    }

    // Step 7 - Return ContactMatchType.Related if at least 1 of Address, Phone, or
    // Email match
    @Test
    void compareTo_Related_AddressMatch()
    {
        runIsMatchTest(AnswerType.no, AnswerType.yes, AnswerType.no, AnswerType.no, ContactMatchType.Related);
    }

    @Test
    void compareTo_Related_PhoneMatch()
    {
        runIsMatchTest(AnswerType.no, AnswerType.no, AnswerType.yes, AnswerType.no, ContactMatchType.Related);
    }

    @Test
    void compareTo_Related_EmailMatch()
    {
        runIsMatchTest(AnswerType.no, AnswerType.no, AnswerType.yes, AnswerType.no, ContactMatchType.Related);
    }

    // Step 8 - Return ContactMatchType.PotentiallyRelated if at least one of
    // Address, Phone, or Email is "might match"
    @Test
    void compareTo_PotentiallyRelated_AddressMightMatch()
    {
        runIsMatchTest(AnswerType.no, AnswerType.maybe, AnswerType.no, AnswerType.no,
                ContactMatchType.PotentiallyRelated);
    }

    @Test
    void compareTo_PotentiallyRelated_PhoneMightMatch()
    {
        runIsMatchTest(AnswerType.no, AnswerType.no, AnswerType.maybe, AnswerType.no,
                ContactMatchType.PotentiallyRelated);
    }

    @Test
    void compareTo_PotentiallyRelated_EmailMightMatch()
    {
        runIsMatchTest(AnswerType.no, AnswerType.no, AnswerType.no, AnswerType.maybe,
                ContactMatchType.PotentiallyRelated);
    }

    /* -------------------------------------------------------------------------
     * Special cases
     * -------------------------------------------------------------------------
     */

    @Test
    void compareTo_PotentiallyRelated_SameLastName() {
        Contact c1 = createBaseContact();
        Contact c2 = new Contact();
        c2.setName(new Name(new LastName("Doe"), new FirstName("Adam")));
        c2.setAddress(new Address(new StreetAddress("92 Broadway"),
                null,
                new GeneralProperty("Tucson"),
                new State("AZ"),
                new Zip("85750")));
        c2.setPhone(new Phone("(520) 987-6543"));
        c2.setEmail(new Email("asmith@homail.com"));

        assertEquals(ContactMatchType.PotentiallyRelated, c1.compareTo(c2).getMatchType());
    }

    @Test
    void compareTo_MatchButModifying_RealContacts() {
        Contact c1 = createBaseContact();

        // Same name and phone number
        Contact c2 = new Contact();
        c2.setName(c1.getName());
        c2.setAddress(new Address(new StreetAddress("92 Broadway"),
                null,
                new GeneralProperty("Tucson"),
                new State("AZ"),
                new Zip("85750")));
        c2.setPhone(c1.getPhone());
        c2.setEmail(new Email("asmith@homail.com"));

        assertEquals(ContactMatchType.MatchButModifying, c1.compareTo(c2).getMatchType());
    }

    @Test
    void compareTo_Related_RealContacts() {
        Contact c1 = createBaseContact();
        Contact c2 = new Contact();
        c2.setName(new Name(new LastName("Smith"), new FirstName("Adam")));
        c2.setAddress(new Address(new StreetAddress("92 Broadway"),
                null,
                new GeneralProperty("Tucson"),
                new State("AZ"),
                new Zip("85750")));
        c2.setPhone(c1.getPhone());
        c2.setEmail(new Email("asmith@homail.com"));

        assertEquals(ContactMatchType.Related, c1.compareTo(c2).getMatchType());
    }

    @Test
    void compareTo_Potentially_RealContacts() {
        Contact c1 = createBaseContact();

        // Address is same but without the street type
        Contact c2 = new Contact();
        c2.setName(new Name(new LastName("Smth"), new FirstName("Adam")));
        c2.setAddress(new Address(new StreetAddress("123 Main"),
                null,
                new GeneralProperty("Tucson"),
                new State("AZ"),
                new Zip("85750")));
        c2.setPhone(new Phone("(520) 987-6543"));
        c2.setEmail(new Email("asmith@homail.com"));

        assertEquals(ContactMatchType.PotentiallyRelated, c1.compareTo(c2).getMatchType());
    }

    // Test to try real contacts and help determine why we are not getting expected
    // result
    @Test
    void compareTo_TestRealContacts() {
        Contact c1 = new Contact();
        c1.setName(new Name(new LastName("Scotinsky"), new FirstName("Sonya")));
        c1.setAddress(new Address(new StreetAddress("2810 E 4th St"),
                null,
                new GeneralProperty("Tucson"),
                new State("AZ"),
                new Zip("85716")));
        c1.setPhone(new Phone("(520) 909-7177"));
        c1.setEmail(new Email("sonya@forsarchitecture.com"));

        Contact c2 = new Contact();
        c2.setName(new Name(new LastName("Fuentevilla"), new FirstName("Siena")));
        c2.setAddress(new Address(new StreetAddress("2810 E 4th St"),
                null,
                new GeneralProperty("Tucson"),
                new State("AZ"),
                new Zip("85716-4422")));
        c2.setPhone(new Phone("(520) 870-7454"));
        c2.setEmail(new Email("18sfuentevilla@salpointe.org"));

        // TODO: Need to discuss... Mbilodeaus code had these as Related because
        // they share the same address where James code had it as Potentially
        // related. Which one do we want to go with?
        assertEquals(ContactMatchType.PotentiallyRelated, c1.compareTo(c2).getMatchType());
    }

    @Test
    void setPropertyValue_NullProperty() {
        Contact c = createBaseContact();

        assertThrows(IllegalArgumentException.class, () -> c.setPropertyValue(null, "Value1"));
    }

    @Test
    void setPropertyValue_EmptyProperty() {
        Contact c = createBaseContact();

        assertThrows(IllegalArgumentException.class, () -> c.setPropertyValue("", "Value1"));
    }

    @Test
    void setPropertyValue_NullValue() {
        Contact c = createBaseContact();

        assertThrows(IllegalArgumentException.class, () -> c.setPropertyValue("PropertyA", null));
    }

    @Test
    void setPropertyValue_EmptyValue() {
        Contact c = createBaseContact();

        c.setPropertyValue("PropertyA", "");

        assertEquals("", c.getPropertyValue("PropertyA"));
    }

    @Test
    void setPropertyValue() {
        Contact c = createBaseContact();

        c.setPropertyValue("PropertyA", "Value1");

        assertEquals("Value1", c.getPropertyValue("PropertyA"));
    }

    @Test
    void setPropertyValue_Reset() {
        Contact c = createBaseContact();

        c.setPropertyValue("PropertyA", "Value1");

        // We do not allow you to change the value of properties
        assertThrows(UnsupportedOperationException.class, () -> c.setPropertyValue("PropertyA", "Value2"));
    }

    @Test
    void getPropertyValue_NullProperty() {
        Contact c = createBaseContact();

        c.setPropertyValue("PropertyA", "Value1");

        assertThrows(IllegalArgumentException.class, () -> c.getPropertyValue(null));
    }

    @Test
    void getPropertyValue_EmptyProperty() {
        Contact c = createBaseContact();

        c.setPropertyValue("PropertyA", "Value1");

        assertThrows(IllegalArgumentException.class, () -> c.getPropertyValue(""));
    }

    @Test
    void containsProperty_NullProperty() {
        Contact c = createBaseContact();
        assertThrows(IllegalArgumentException.class, () -> c.containsProperty(null));
    }

    @Test
    void containsProperty_EmptyProperty() {
        Contact c = createBaseContact();
        assertThrows(IllegalArgumentException.class, () -> c.containsProperty(""));
    }

    @Test
    void containsProperty_true() {
        Contact c = createBaseContact();

        c.setPropertyValue("PropertyA", "Value1");

        assertTrue(c.containsProperty("PropertyA"));
    }

    @Test
    void containsProperty_false() {
        Contact c = createBaseContact();

        c.setPropertyValue("PropertyA", "Value1");

        assertFalse(c.containsProperty("Value1"));
    }
}