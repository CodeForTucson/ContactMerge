/*
 * Stores proposed matches for a single contact being merged into a list of
 * existing contacts. If one proposed match is more likely than others, it
 * will be identified by ProposedTargetIndex.
 */
package com.cft.contactmerge;
import javax.naming.OperationNotSupportedException;
import java.util.*;
import java.util.stream.Collectors;

public class ProposedMatch {
    private IContact contactToMerge;
    private List<IContact> possibleTargetContacts;
    private int proposedTargetIndex;

    public ProposedMatch(IContact contactToMerge, List<IContact> possibleTargetContacts) {
        if (contactToMerge == null) {
            throw new IllegalArgumentException("contactToMerge can not be null");
        }

        if (possibleTargetContacts == null) {
            throw new IllegalArgumentException("possibleTargetContacts can not be null");
        }

        this.contactToMerge = contactToMerge;
        this.possibleTargetContacts = possibleTargetContacts;
        this.proposedTargetIndex = -1;
    }

    public IContact getContactToMerge()
    {
        return this.contactToMerge;
    }

    public List<IContact> getPossibleTargetContacts() {
        return this.possibleTargetContacts;
    }

    public int getProposedTargetIndex()
    {
        return this.proposedTargetIndex;
    }

    private void verifyIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("proposedTargetIndex must be greater than zero");
        }

        if (index >= possibleTargetContacts.size()) {
            throw new IndexOutOfBoundsException("proposedTargetIndex must be less than size of the possibleTargetContacts list");
        }
    }
    public void setProposedTargetIndex(int proposedTargetIndex) {
        verifyIndex(proposedTargetIndex);
        this.proposedTargetIndex = proposedTargetIndex;
    }

    public void removeTargetContact(int index) {
        verifyIndex(index);

        this.possibleTargetContacts.remove(index);
        this.proposedTargetIndex = -1;
    }
}
