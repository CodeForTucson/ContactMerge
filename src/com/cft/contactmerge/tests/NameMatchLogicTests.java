package com.cft.contactmerge.tests;

import com.cft.contactmerge.AnswerType;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.cft.contactmerge.NameMatchLogic.*;


public class NameMatchLogicTests {
    /***********************************************************************************
     ********************* getYesOrMaybeHyphenatedNameMatch Method *********************
     ***********************************************************************************/
    @Test
    void getYesOrMaybeHyphenatedNameMatch_Yes_NamesMoreThanSetCharLimit(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Ann", "Marie"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Marie"));

        assertEquals(AnswerType.yes, getYesOrMaybeHyphenatedNameMatch(nameOne, nameTwo));
    }

    @Test
    void getYesOrMaybeHyphenatedNameMatch_Maybe_NameEqualToSetCharLimit(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Ann", "Marie"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Ann"));

        assertEquals(AnswerType.maybe, getYesOrMaybeHyphenatedNameMatch(nameOne, nameTwo));
    }

    @Test
    void getYesOrMaybeHyphenatedNameMatch_Maybe_NameLessThanSetCharLimit(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Mary", "Jo"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Jo"));

        assertEquals(AnswerType.maybe, getYesOrMaybeHyphenatedNameMatch(nameOne, nameTwo));
    }

    /***********************************************************************************
     ********************* getFirstLastSwapNameMatchResult Method **********************
     ***********************************************************************************/
    @Test
    void isNamesSwappedMatched_Maybe(){
        ArrayList<String> firstNameOne = new ArrayList<>(Arrays.asList("john"));
        ArrayList<String> lastNameOne = new ArrayList<>(Arrays.asList("doe"));
        ArrayList<String> firstNameTwo = new ArrayList<>(Arrays.asList("doe"));
        ArrayList<String> lastNameTwo = new ArrayList<>(Arrays.asList("john"));

        assertEquals(AnswerType.maybe, getFirstLastSwapNameMatchResult(firstNameOne, lastNameOne, firstNameTwo, lastNameTwo));
    }

    @Test
    void isNamesSwappedDifferentFirstName_No(){
        ArrayList<String> firstNameOne = new ArrayList<>(Arrays.asList("ray"));
        ArrayList<String> lastNameOne = new ArrayList<>(Arrays.asList("doe"));
        ArrayList<String> firstNameTwo = new ArrayList<>(Arrays.asList("doe"));
        ArrayList<String> lastNameTwo = new ArrayList<>(Arrays.asList("john"));

        assertEquals(AnswerType.no, getFirstLastSwapNameMatchResult(firstNameOne, lastNameOne, firstNameTwo, lastNameTwo));
    }

    @Test
    void isNamesSwappedDifferentLastName_No(){
        ArrayList<String> firstNameOne = new ArrayList<>(Arrays.asList("john"));
        ArrayList<String> lastNameOne = new ArrayList<>(Arrays.asList("adams"));
        ArrayList<String> firstNameTwo = new ArrayList<>(Arrays.asList("doe"));
        ArrayList<String> lastNameTwo = new ArrayList<>(Arrays.asList("john"));

        assertEquals(AnswerType.no, getFirstLastSwapNameMatchResult(firstNameOne, lastNameOne, firstNameTwo, lastNameTwo));
    }

    @Test
    void isNamesSwappedPunctuatedNames_Maybe(){
        ArrayList<String> firstNameOne = new ArrayList<>(Arrays.asList("an", "de", "denise"));
        ArrayList<String> lastNameOne = new ArrayList<>(Arrays.asList("as", "la", "democh"));
        ArrayList<String> firstNameTwo = new ArrayList<>(Arrays.asList("as", "la", "democh"));
        ArrayList<String> lastNameTwo = new ArrayList<>(Arrays.asList("an", "de", "denise"));

        assertEquals(AnswerType.maybe, getFirstLastSwapNameMatchResult(firstNameOne, lastNameOne, firstNameTwo, lastNameTwo));
    }

    @Test
    void isNamesSwappedHyphenatedNames_Maybe(){
        ArrayList<String> firstNameOne = new ArrayList<>(Arrays.asList("denise"));
        ArrayList<String> lastNameOne = new ArrayList<>(Arrays.asList("democh"));
        ArrayList<String> firstNameTwo = new ArrayList<>(Arrays.asList("as", "la", "democh"));
        ArrayList<String> lastNameTwo = new ArrayList<>(Arrays.asList("an", "de", "denise"));

        assertEquals(AnswerType.maybe, getFirstLastSwapNameMatchResult(firstNameOne, lastNameOne, firstNameTwo, lastNameTwo));
    }

    @Test
    void isNamesSwappedInitialNames_No(){
        ArrayList<String> firstNameOne = new ArrayList<>(Arrays.asList("j"));
        ArrayList<String> lastNameOne = new ArrayList<>(Arrays.asList("doe"));
        ArrayList<String> firstNameTwo = new ArrayList<>(Arrays.asList("doe"));
        ArrayList<String> lastNameTwo = new ArrayList<>(Arrays.asList("john"));

        assertEquals(AnswerType.no, getFirstLastSwapNameMatchResult(firstNameOne, lastNameOne, firstNameTwo, lastNameTwo));
    }

    /***********************************************************************************
     ***************************** isNamePartsMatch Method *****************************
     ***********************************************************************************/
    @Test
    void isNamePartsMatch_true_SameNames(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Mary", "Jo"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Mary", "Jo"));

        assertTrue(isNamePartsMatch(nameOne, nameTwo));
    }

    @Test
    void isNamePartsMatch_true_PartialNameFirstNameLarger(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Mary", "Jo"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Mary"));

        assertTrue(isNamePartsMatch(nameOne, nameTwo));
    }

    @Test
    void isNamePartsMatch_true_PartialNameSecondNameLarger(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Jo"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Mary", "Jo"));

        assertTrue(isNamePartsMatch(nameOne, nameTwo));
    }

    @Test
    void isNamePartsMatch_false_PartialMatch(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Mary", "Jo"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Mar", "Jo"));

        assertFalse(isNamePartsMatch(nameOne, nameTwo));
    }

    /***********************************************************************************
     ************************* isNameMatchedWithInitial Method *************************
     ***********************************************************************************/
    @Test
    void isNameMatchedWithInitial_true_FirstNameIsInitialMatch(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("M"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Mary"));

        assertTrue(isNameMatchedWithInitial(nameOne, nameTwo));
    }

    @Test
    void isNameMatchedWithInitial_true_SecondNameIsInitialMatch(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Mary"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("M"));

        assertTrue(isNameMatchedWithInitial(nameOne, nameTwo));
    }

    @Test
    void isNameMatchedWithInitial_false_FirstNameIsInitialMismatch(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("A"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Mary"));

        assertFalse(isNameMatchedWithInitial(nameOne, nameTwo));
    }

    @Test
    void isNameMatchedWithInitial_false_SecondNameIsInitialMismatch(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Mary"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("A"));

        assertFalse(isNameMatchedWithInitial(nameOne, nameTwo));
    }

    @Test
    void isNameMatchedWithInitial_false_NamesNotInitial(){
        ArrayList<String> nameOne = new ArrayList<>(Arrays.asList("Mary"));
        ArrayList<String> nameTwo = new ArrayList<>(Arrays.asList("Mary"));

        assertFalse(isNameMatchedWithInitial(nameOne, nameTwo));
    }

    /***********************************************************************************
     ***************************** setNormalizeName Method *****************************
     ***********************************************************************************/
    @Test
    void setNormalizeName_equals_NoAlphaCharactersRemovedUpperAndLower_TrailingWhiteSpaces(){
        ArrayList<String> nameParts = setNormalizeName(" aBcDeFgHiJkLmNoPqRsTuVwXyZ   ");
        ArrayList<String> expectedNameParts = new ArrayList<>(Arrays.asList("abcdefghijklmnopqrstuvwxyz"));

        assertEquals(expectedNameParts, nameParts);
    }

    @Test
    void setNormalizeName_equals_WhiteSpacesInBetween(){
        ArrayList<String> nameParts = setNormalizeName(" aB cDe  FgHi JkL   mNoPqRs TuVwX   yZ   ");
        ArrayList<String> expectedNameParts = new ArrayList<>(Arrays.asList("ab", "cde", "fghi", "jkl", "mnopqrs", "tuvwx", "yz"));

        assertEquals(expectedNameParts, nameParts);
    }

    @Test
    void setNormalizeName_equals_CharactersNumbersWhiteSpacesInBetween(){
        ArrayList<String> nameParts = setNormalizeName(" a1B c,D.e  F/g;H'i J[k]L   m\\N-o=P`q|R_s T+u{V}w:X   y\"Z   ");
        ArrayList<String> expectedNameParts = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));

        assertEquals(expectedNameParts, nameParts);
    }

    /***********************************************************************************
     **************************** getNumOfCharsInName Method ***************************
     ***********************************************************************************/
    @Test
    void getNumOfCharsInName_equals_OnePart(){
        ArrayList<String> nameParts = new ArrayList<>(Arrays.asList("abcdefghijklmnopqrstuvwxyz"));
        int numOfCharactersInName = getNumOfCharsInName(nameParts);

        assertEquals(26, numOfCharactersInName);
    }

    @Test
    void getNumOfCharsInName_equals_MultipleParts(){
        ArrayList<String> nameParts = new ArrayList<>(Arrays.asList("ab", "cde", "fghi", "jkl", "mnopqrs", "tuvwx", "yz"));
        int numOfCharactersInName = getNumOfCharsInName(nameParts);

        assertEquals(26, numOfCharactersInName);
    }

    @Test
    void getNumOfCharsInName_equals_EachLetter(){
        ArrayList<String> nameParts = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
        int numOfCharactersInName = getNumOfCharsInName(nameParts);

        assertEquals(26, numOfCharactersInName);
    }

    @Test
    void getNumOfCharsInName_equals_OneCharacter(){
        ArrayList<String> nameParts = new ArrayList<>(Arrays.asList("a"));
        int numOfCharactersInName = getNumOfCharsInName(nameParts);

        assertEquals(1, numOfCharactersInName);
    }

    @Test
    void getNumOfCharsInName_equals_EmptyString(){
        ArrayList<String> nameParts = new ArrayList<>(Arrays.asList(""));
        int numOfCharactersInName = getNumOfCharsInName(nameParts);

        assertEquals(0, numOfCharactersInName);
    }

    @Test
    void getNumOfCharsInName_equals_EmptyArrayList(){
        ArrayList<String> nameParts = new ArrayList<>();
        int numOfCharactersInName = getNumOfCharsInName(nameParts);

        assertEquals(0, numOfCharactersInName);
    }
}
