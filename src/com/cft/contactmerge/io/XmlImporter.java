package com.cft.contactmerge.io;

import com.cft.contactmerge.Contact;
import java.util.*;

public class XmlImporter implements IImporter, Iterable<Contact> {

    public void Load(String filename)
    {
        // TODO: Add code
    }

    public Iterator<Contact> iterator()
    {
        Iterator<Contact> it = new Iterator<Contact>() {

            @Override
            public boolean hasNext() {

                // TODO: Add code and return appropriate result
                return false;
            }

            @Override
            public Contact next() {
                Contact contact = new Contact();

                // TODO: Fill in code

                return contact;
            }
        };

        return it;
    }

}
