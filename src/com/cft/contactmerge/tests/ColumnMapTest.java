package com.cft.contactmerge.tests;

import org.junit.jupiter.api.Test;
import com.cft.contactmerge.*;
import static org.junit.jupiter.api.Assertions.*;

class ColumnMapTest {

    @Test
    void ColumnMap()
    {
        ColumnMap columnMap = new ColumnMap("FirstName", 2);
        assertNotNull(columnMap);
    }

    @Test
    void getContactProperty()
    {
        ColumnMap columnMap = new ColumnMap("FirstName", 2);
        assertEquals("FirstName", columnMap.getContactProperty());
    }

    @Test
    void getColumnInFile()
    {
        ColumnMap columnMap = new ColumnMap("FirstName", 2);
        assertEquals(2, columnMap.getColumnInFile());
    }
}