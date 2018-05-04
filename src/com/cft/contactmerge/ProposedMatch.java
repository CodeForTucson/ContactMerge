package com.cft.contactmerge;
import javax.naming.OperationNotSupportedException;
import java.util.*;

public class ProposedMatch {
    private Contact contactToMerge;
    private List<Contact> possibleTargetContacts;

    public ProposedMatch(Contact contactToMerge, List<Contact> possibleTargetContacts)
    {
        this.contactToMerge = contactToMerge;
        this.possibleTargetContacts = possibleTargetContacts;
    }
    public Contact getContactToMerge()
    {
        return null;
    }

    public List<Contact> getPossibleTargetContacts()
    {
        return null;
    }

    public int getProposedTargetIndex()
    {
        return -1;
    }

    public void setProposedTargetIndex(int proposedTargetIndex)
    {
    }
}
