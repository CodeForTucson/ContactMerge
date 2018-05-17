package com.cft.contactmerge;

import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.Email;
import com.cft.contactmerge.contact.Name;
import com.cft.contactmerge.contact.Phone;

public interface IContact {
    public void setName(Name name);
    public Name getName();

    public void setFirstName(String firstName);
    public String getFirstName();

    public void setLastName(String lastName);
    public String getLastName();

    public void setAddress(Address address);
    public Address getAddress();

    public void setPhone(Phone phone);
    public Phone getPhone();

    public void setEmail(Email email);
    public Email getEmail();

    public ContactMatchResult CompareTo(IContact compareContact);
}
