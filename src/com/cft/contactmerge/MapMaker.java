/*
 * MapMaker is responsible for creating the map file that tells ContactImporter
 * and MergeFileExporter how to map properties (FirstName, Address, etc) to
 * the columns in the file.
 */
package com.cft.contactmerge;

import java.util.*;

public class MapMaker {
    public void addColumnMap(String propertyName, int columnInFile)
    {
        // TODO: Implementation needed
    }

    public void removeColumnMap(String propertyName)
    {
        // TODO: Implementation needed
    }

    public void addStaticMap(String propertyName, String value)
    {
        // TODO: Implementation needed
    }

    public Collection<ColumnMap> getColumnMaps()
    {
        // TODO: Implementation needed
        return null;
    }
}
