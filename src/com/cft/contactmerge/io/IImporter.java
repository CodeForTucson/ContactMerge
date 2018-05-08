package com.cft.contactmerge.io;

import java.util.*;
import com.cft.contactmerge.*;

public interface IImporter {
    public void Load(String filename);
    public Iterator<Contact> iterator();
}
