package com.cft.contactmerge.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import com.cft.contactmerge.*;

public interface IImporter {
    public void Load(String filename) throws FileNotFoundException, IOException;
    public Iterator<Contact> iterator();
}
