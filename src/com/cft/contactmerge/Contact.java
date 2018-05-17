package com.cft.contactmerge;

import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.Email;
import com.cft.contactmerge.contact.Name;
import com.cft.contactmerge.contact.Phone;

public class Contact implements IContact {
    private Name name = new Name();

    // TODO: Should support multiple addresses, phones, and emails for each contact
    private Address address = new Address();
    private Phone phone = new Phone();
    private Email email = new Email();

    public void setName(Name name) {
        this.name = name;
    }

    public Name getName(){
        return this.name;
    }

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

    public ContactMatchResult CompareTo(IContact compareContact)
    {
        // TODO: Need to add logic
        return new ContactMatchResult(ContactMatchType.NoMatch);
    }
}
