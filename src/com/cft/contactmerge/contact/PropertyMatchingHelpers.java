package com.cft.contactmerge.contact;

import com.cft.contactmerge.AnswerType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

public class PropertyMatchingHelpers {
    private static final int MIN_PART_LENGTH_TO_MAKE_YES_MATCH = 3;

    private static Collection<String> splitPropertyString(String propertyValue, String splitRegEx) {
        assert(propertyValue != null);
        assert(splitRegEx != null);

        return Arrays.asList(propertyValue.split(splitRegEx)).stream()
                .filter(x -> !x.isEmpty()).map(x -> x.toLowerCase()).collect(Collectors.toList());
    }

    public static Collection<String> splitPropertyStringOnNonAlpha(String propertyValue) {
        return splitPropertyString(propertyValue, "[^a-zA-Z]");
    }

    public static Collection<String> splitPropertyStringOnNonAlphaNumeric(String propertyValue) {
        return splitPropertyString(propertyValue, "[^a-zA-Z0-9]");
    }

    public static AnswerType doPropertyPartsMatchOrderDoesNotMatter(Collection<String> sourceCollection, Collection<String> targetCollection) {
        assert(sourceCollection != null);
        assert(targetCollection != null);

        int maxFoundLength = 0;

        for(String part: sourceCollection) {
            if (targetCollection.contains(part)) {
                maxFoundLength = Math.max(maxFoundLength, part.length());
            }
        }

        return maxFoundLength == 0 ? AnswerType.no :
                (maxFoundLength > MIN_PART_LENGTH_TO_MAKE_YES_MATCH ? AnswerType.yes : AnswerType.maybe);
    }

    public static AnswerType doPropertyPartsMatchOrderDoesMatter(Collection<String> sourceCollection, Collection<String> targetCollection) {
        assert(sourceCollection != null);
        assert(targetCollection != null);

        if (sourceCollection.size() != targetCollection.size() || sourceCollection.size() == 0) {
            return AnswerType.no;
        }

        Iterator<String> sourceIterator = sourceCollection.iterator();
        Iterator<String> targetIterator = targetCollection.iterator();

        while(sourceIterator.hasNext()) {
            if (PropertyMatchingHelpers.doNamePartsMatch(sourceIterator.next(), targetIterator.next()) != AnswerType.yes) {
                return AnswerType.no;
            }
        }

        return AnswerType.yes;
    }

    public static AnswerType doPropertyPartsMatch(String sourceString, String targetString) {
        assert(sourceString != null);
        assert(targetString!= null);

        return sourceString.equalsIgnoreCase(targetString) ? AnswerType.yes : AnswerType.no;
    }

    public static boolean doesInitialMatchWithName(char initial, String name) {
        assert(name != null);

        return name.length() > 0 && name.toLowerCase().charAt(0) == Character.toLowerCase(initial);
    }

    public static AnswerType doNamePartsMatch(String namePartSource, String namePartTarget) {
        if (PropertyMatchingHelpers.doPropertyPartsMatch(namePartSource, namePartTarget) == AnswerType.yes)
        {
            return AnswerType.yes;
        }

        Collection<String> sourceParts = PropertyMatchingHelpers.splitPropertyStringOnNonAlpha(namePartSource);
        Collection<String> targetParts = PropertyMatchingHelpers.splitPropertyStringOnNonAlpha(namePartTarget);

        return PropertyMatchingHelpers.doPropertyPartsMatchOrderDoesNotMatter(sourceParts, targetParts);
    }

    public static boolean containsAlpha(String string) {
        return string.matches(".*[a-zA-Z].*");
    }
}
