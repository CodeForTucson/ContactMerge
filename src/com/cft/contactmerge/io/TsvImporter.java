package com.cft.contactmerge.io;

import com.cft.contactmerge.Contact;
import com.cft.contactmerge.contact.*;

import java.io.*;
import java.util.*;

public class TsvImporter implements IImporter, Iterable<Contact> {

    private List<String> data = null;
    private Map<String, Integer> columnMap;

    public void Load(String filename) throws FileNotFoundException, IOException {
        // TODO: This method may be the same in all Importer classes. Create parent class?
        if (filename == null || filename.trim() == "")
        {
            throw new IllegalArgumentException("filename is required");
        }

        File inputFile = new File(filename);

        if (!inputFile.exists() || inputFile.isDirectory())
        {
            throw new FileNotFoundException(String.format("Unable to find %s", filename));
        }

        try (InputStream inputStream = new FileInputStream(inputFile)) {
            Load(inputStream);
        }
    }

    private Map<String, Integer> getColumnMap(String headerLine) {
        Map<String, Integer> columnMap = new Hashtable<String, Integer>();

        List<String> columns = SplitStringUtilities.splitTsvString(headerLine);

        int i = 0;
        for (String column: columns) {
            columnMap.put(column, i++);
        }

        return columnMap;
    }

    public void Load(InputStream inputStream) {
        if (inputStream == null)
        {
            throw new IllegalArgumentException("inputStream is required");
        }

        try {
            if (!(inputStream.available() > 0))
                throw new UnknownFormatConversionException("inputStream not available");
        }
        catch (IOException e)
        {
            throw new UnknownFormatConversionException("inputStream not available");
        }

        // Load header line and data
        try (Scanner scanner = new Scanner(inputStream)) {
            if (!scanner.hasNextLine()) {
                throw new UnknownFormatConversionException("inputStream not available");
            }

            this.columnMap = getColumnMap(scanner.nextLine());

            data = new ArrayList<String>();

            while(scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        }
    }

    public Iterator<Contact> iterator() {
        if (data == null)
        {
            throw new IllegalStateException("Must load file before using the iterator");
        }


        Iterator<Contact> it = new Iterator<Contact>() {
            int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < data.size();
            }

            @Override
            public Contact next() {
                Contact contact = new Contact();

                List<String> parts = SplitStringUtilities.splitTsvString(data.get(currentIndex++));

                String lastName = parts.get(columnMap.get("Last"));
                String firstName = parts.get(columnMap.get("First"));

                if (lastName == null || firstName == null || lastName.isEmpty() || firstName.isEmpty()) {
                    throw new IllegalStateException();
                }
                contact.setName(new Name(new LastName(lastName), new FirstName(firstName)));

                String streetAddress = parts.get(columnMap.get("Address"));
                String city = parts.get(columnMap.get("City"));
                String state = parts.get(columnMap.get("State"));
                String zip = parts.get(columnMap.get("Zip"));

                if (streetAddress != null && !streetAddress.isEmpty() &&
                        city != null && !city.isEmpty() &&
                        state != null && !state.isEmpty() &&
                        zip != null && !zip.isEmpty()) {
                    contact.setAddress(new Address(new StreetAddress(streetAddress),
                            null,
                            new GeneralProperty(city),
                            new State(state),
                            new Zip(zip)));
                }

                String phone = parts.get(columnMap.get("Phone #"));

                if (phone != null && !phone.isEmpty()) {
                    try {
                        contact.setPhone(new Phone(phone));
                    }
                    catch (IllegalArgumentException e) {
                        // ignore invalid phone numbers
                        // TODO: We should have a way to log/warn of these
                    }
                }

                String email = parts.get(columnMap.get("E-mail"));

                if (email != null && !email.isEmpty()) {
                    contact.setEmail(new Email(email));
                }

                // TODO: When column maps are supported, should pull in identified properties
                String propertyValue = parts.get(columnMap.get("Bidder #"));

                if (propertyValue != null) {
                    contact.setPropertyValue("ContactId", propertyValue);
                }

                return contact;
            }
        };

        return it;
    }
}
