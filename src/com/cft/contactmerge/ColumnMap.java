/*
 * Identifies where a property (LastName, Address, etc) should be stored/retrieved
 * in/from a file.
 */
package com.cft.contactmerge;

public class ColumnMap {
    private String contactProperty;

    // TODO: This won't work for xml files and databases. Need to figure out a way
    // that will allow us to represent all column types.
    private int columnInFile;

    public ColumnMap(String contactProperty, int columnInFile)
    {
        this.contactProperty = contactProperty;
        this.columnInFile = columnInFile;
    }

    public String getContactProperty()
    {
        return this.contactProperty;
    }

    public int getColumnInFile()
    {
        return this.columnInFile;
    }
}
