package com.cft.contactmerge;

import com.cft.contactmerge.contact.Address;
import com.cft.contactmerge.contact.Email;
import com.cft.contactmerge.contact.Name;
import com.cft.contactmerge.contact.Phone;

public interface IContact {
    public void setName(Name name);
    public Name getName();

    public void setAddress(Address address);
    public Address getAddress();

    public void setPhone(Phone phone);
    public Phone getPhone();

    public void setEmail(Email email);
    public Email getEmail();

    public void setPropertyValue(String property, String value);
    public String getPropertyValue(String property);
    public boolean containsProperty(String property);

    public ContactMatchResult compareTo(IContact compareContact);
}
