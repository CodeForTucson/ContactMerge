package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;
import com.cft.contactmerge.ProposedMatch;
import com.cft.contactmerge.IContact;
import com.cft.contactmerge.ContactMatchType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ContactMatchingHelpers {
    public static List<IContact> sortPossibleMatches(IContact contactToMerge, List<IContact> possibleMatches) {
        assert(contactToMerge != null);
        assert(possibleMatches != null);

        List<IContact> sortedMatches =
                possibleMatches.stream().
                        filter(x -> contactToMerge.compareTo(x).getMatchType() == ContactMatchType.Identical).
                        collect(Collectors.toList());

        sortedMatches.addAll(possibleMatches.stream().
                filter(x -> contactToMerge.compareTo(x).getMatchType() == ContactMatchType.Match).
                collect(Collectors.toList()));

        sortedMatches.addAll(possibleMatches.stream().
                filter(x -> contactToMerge.compareTo(x).getMatchType() == ContactMatchType.MatchButModifying).
                collect(Collectors.toList()));

        sortedMatches.addAll(possibleMatches.stream().
                filter(x -> contactToMerge.compareTo(x).getMatchType() == ContactMatchType.PotentialMatch).
                collect(Collectors.toList()));

        sortedMatches.addAll(possibleMatches.stream().
                filter(x -> contactToMerge.compareTo(x).getMatchType() == ContactMatchType.Related).
                collect(Collectors.toList()));

        sortedMatches.addAll(possibleMatches.stream().
                filter(x -> contactToMerge.compareTo(x).getMatchType() == ContactMatchType.PotentiallyRelated).
                collect(Collectors.toList()));

        return sortedMatches;
    }

    public static boolean hasContactInfo(IContact contact) {
        assert(contact != null);

        return contact.getAddress() != null || contact.getPhone() != null || contact.getEmail() != null;
    }

    public static MatchAction getSuggestedAction(ProposedMatch match) {
        assert(match != null);

        List<IContact> sortedMatches = sortPossibleMatches(match.getContactToMerge(), match.getPossibleTargetContacts());

        if (sortedMatches.size() == 0) {
            if (hasContactInfo(match.getContactToMerge())) {
                return MatchAction.Add;
            }
            else {
                return MatchAction.Skip;
            }
        }

        IContact contact = match.getContactToMerge();
        long matchCount = sortedMatches.stream().
                filter(x -> contact.compareTo(x).isMatchFound() != AnswerType.no).count();

        if (matchCount == 0) {
            return MatchAction.Review;
        }

        if (matchCount == 1) {
            return MatchAction.Update;
        }

        ContactMatchType closestMatchType = contact.compareTo(sortedMatches.get(0)).getMatchType();
        long closestMatchCount = sortedMatches.stream().
                filter(x -> contact.compareTo(x).getMatchType() == closestMatchType).count();

        if (closestMatchCount == 1) {
            return MatchAction.Update;
        }

        // TODO: Theory that it might be useful to check if match is against different types
        // (org, individual, household) it is best to update the individual. Need to test
        // and if it looks useful, add code.

        return MatchAction.Review;
    }

}
