package com.cft.contactmerge;

import com.cft.contactmerge.contact.*;

import java.util.Arrays;
import java.util.ArrayList;

public class Contact implements IContact {
    private Name name = new Name();

    // TODO: Should support multiple addresses, phones, and emails for each contact
    private Address address = new Address();
    private Phone phone = new Phone();
    private Email email = new Email();

    /*******************************************************************************************************************
     ************************************************* Get/Set Methods *************************************************
     *******************************************************************************************************************/
    public void setName(Name name) {
        this.name = name;
    }

    public Name getName(){
        return this.name;
    }

    /* ToDo: set/get FirstName and LastName methods should be removed once they are no longer used,
     *       Since these methods are already a part of the Name object
     */
    public void setFirstName(String firstName){
        this.name.setFirstName(firstName);
    }

    public String getFirstName(){
        return this.name.getFirstName();
    }

    public void setLastName(String lastName){
        this.name.setLastName(lastName);
    }

    public String getLastName(){
        return this.name.getLastName();
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() { return this.address; }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
    public Phone getPhone() { return this.phone; }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Email getEmail() { return this.email; }

    /*******************************************************************************************************************
     *************************************************** Sub-Methods ***************************************************
     *******************************************************************************************************************/
    private ContactMatchResult calculateMatchResult(AnswerType nameMatch,
                                                    ContactMatchTally tally,
                                                    AnswerType lastNameMatch) {
        // If everything matches
        if (nameMatch == AnswerType.yes && tally.getYesCount() > 0 &&
                (tally.getYesCount() + tally.getBothNullCount()) == tally.getTotalSubmitted()) {
            return new ContactMatchResult(ContactMatchType.Identical);
        }

        // If name and at least one other key contact property matches but a key contact property doesn't match
        if (nameMatch == AnswerType.yes && tally.getYesCount() > 0 && tally.getNoCount() > 0) {
            return new ContactMatchResult(ContactMatchType.MatchButModifying);
        }
        // If name and at least one other key contact property matches
        if (nameMatch == AnswerType.yes && tally.getYesCount() > 0) {
            return new ContactMatchResult(ContactMatchType.Match);
        }

        // If only name matches or name is a maybe match
        if (nameMatch != AnswerType.no) {
            return new ContactMatchResult(ContactMatchType.PotentialMatch);
        }

        // If name doesn't match but one of the other key fields does match
        if (tally.getYesCount() > 0) {
            return new ContactMatchResult(ContactMatchType.Related);
        }

        // If name doesn't match but one of the other key fields is a maybe match
        if (tally.getMaybeCount() > 0  || lastNameMatch != AnswerType.no) {
            return new ContactMatchResult(ContactMatchType.PotentiallyRelated);
        }

        // Default
        return new ContactMatchResult(ContactMatchType.NoMatch);
    }

    public ContactMatchResult compareTo(IContact compareContact)
    {
        AnswerType nameMatch = AnswerType.no;
        AnswerType lastNameMatch = AnswerType.no;

        // TODO: Won't need to test if name is null once name is required in Contact
        if (this.name != null && compareContact.getName() != null)
        {
            nameMatch = this.name.isMatch(compareContact.getName());

            if (this.name.getValue() != null) {

                // TODO: Need to discuss with James. Is this the right way to compare last names? If we change his
                // code to return IContactProperty instead of String, we could just call
                // this.name.getValue().getLastName().isMatch(compareContact.getName().getValue().getLastName())
                ArrayList<String> existingLastName = new ArrayList<String>();
                existingLastName.add(this.name.getValue().getLastName());

                ArrayList<String> toMergeLastName = new ArrayList<String>();
                toMergeLastName.add(compareContact.getName().getValue().getLastName());

                lastNameMatch = NameMatchLogic.isNameMatch(existingLastName, toMergeLastName);
            }
        }

        // We are going to determine if any no's or maybe's are found in any part
        // TODO: Performance note. In theory, we may be able to make a determination before running all comparisons.
        ContactMatchTally tally = new ContactMatchTally();

        tally.addComparison(this.address, compareContact.getAddress());
        tally.addComparison(this.phone, compareContact.getPhone());
        tally.addComparison(this.email, compareContact.getEmail());

        return calculateMatchResult(nameMatch, tally, lastNameMatch);
    }
}
