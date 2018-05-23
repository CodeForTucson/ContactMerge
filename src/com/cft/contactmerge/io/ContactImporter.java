/*
 * ContactImporter is responsible for calling the right importer class
 * (xml, tsv, csv, etc), extract contact information from the specified file
 * and return the extract list of contacts when requested.
 */
package com.cft.contactmerge.io;

import com.cft.contactmerge.*;
import java.util.Collection;

public class ContactImporter {
    public ContactImporter(SupportedFileType fileType, String fileName, Collection<ColumnMap> columnMap )
    {
        // TODO: Implementation needed
    }

    public Collection<Contact> getContacts()
    {
        // TODO: Implementation needed
        return null;
    }
}
