package com.cft.contactmerge.tests.ExtraTests;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.*;
import com.cft.contactmerge.contact.Name;

public class FirstAndLastNameMatchTests {
    /****************************************************************************************************************************************************************************
     ********************************************************************* Compare First And Last Name Tests ********************************************************************
     ****************************************************************************************************************************************************************************/
    /* preconditions...
     * All test cases must include a check for trailing white spaces and different upper/lower case letters.
     */
    private String firstAndLastNameFailedMsg(String fNameOne,String  lNameOne,String  fNameTwo,String  lNameTwo){
        return "(compareFirstNames: \"" + fNameOne + "\" vs \"" + fNameTwo + "\") and (compareLastNames: \"" + lNameOne + "\" vs \"" + lNameTwo + "\")";
    }
    //---------------------------------------------------------------------------- Basic Names Tests ----------------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes() {
        Name firstAndLastNameOne = new Name(" JoHn", null, "DOE ", null, null);
        Name firstAndLastNameTwo = new Name("JOHN ", null, " Doe  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_DifferentLastName() {
        Name firstAndLastNameOne = new Name(" JoHn", null, "DOE ", null, null);
        Name firstAndLastNameTwo = new Name("JOHN ", null, " aDams  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_DifferentFirstName() {
        Name firstAndLastNameOne = new Name(" JoHn", null, "DOE ", null, null);
        Name firstAndLastNameTwo = new Name("jAne ", null, " Doe  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    //------------------------------------------------------------------------- Punctuation First Names -------------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_IgnorePunctuationInFirstName() {
        Name firstAndLastNameOne = new Name("aN-denIse ", null, "Doe ", null, null);
        Name firstAndLastNameTwo = new Name(" An Denise ", null, "   doe ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_Yes_IgnoreMultiPunctuationInFirstName() {
        Name firstAndLastNameOne = new Name("aN-De-denIse ", null, "dOe ", null, null);
        Name firstAndLastNameTwo = new Name(" An dE Denise ", null, "   doe ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInFirstName_DifferentLastNames() {
        Name firstAndLastNameOne = new Name(" aN-De-denIse", null, " dOe", null, null);
        Name firstAndLastNameTwo = new Name("   An dE Denise  ", null, " adams  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    /* What if a name is backwards, do we want this testcase?
    @Test
    void doNamesMatch_Yes_IgnorePunctuationInFirstName() {
        assertEquals(AnswerType.????, CompareContactParts.doNamesMatch("aN-denIse ", "Doe ", " Denise An ", "   doe "));
    }
     */

    //-------------------------------------------------------------------------- Punctuation Last Names -------------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_IgnorePunctuationInLastName() {
        Name firstAndLastNameOne = new Name("Jane ", null, "Doe-adams ", null, null);
        Name firstAndLastNameTwo = new Name("Jane", null, "  Doe Adams", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_Yes_IgnoreMultiPunctuationInLastName() {
        Name firstAndLastNameOne = new Name(" arEb", null, " Sa-LA-Democh", null, null);
        Name firstAndLastNameTwo = new Name("Areb", null, "sa la democh  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInLastName_DifferentFirstNames() {
        Name firstAndLastNameOne = new Name(" haRi", null, " Sa-LA-Democh", null, null);
        Name firstAndLastNameTwo = new Name("Areb", null, "sa la democh  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    //--------------------------------------------------------------------- Punctuation First And Last Names --------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_IgnorePunctuationInFirstAndLastName() {
        Name firstAndLastNameOne = new Name("aN-denIse ", null, "Doe-adams ", null, null);
        Name firstAndLastNameTwo = new Name(" An Denise ", null, "   Doe Adams", null, null);


    }

    @Test
    void doNamesMatch_Yes_IgnoreMultiPunctuationInFirstAndLastName() {
        Name firstAndLastNameOne = new Name("aN-De-denIse ", null, " Sa-LA-Democh ", null, null);
        Name firstAndLastNameTwo = new Name(" An dE Denise ", null, "sa la democh  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInFirstAndLastName_DifferentFirstNames() {
        Name firstAndLastNameOne = new Name(" aN-Day-denIse", null, " Sa-LA-Democh", null, null);
        Name firstAndLastNameTwo = new Name("   An dE Denise  ", null, "sa la democh  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInFirstAndLastName_DifferentLastNames() {
        Name firstAndLastNameOne = new Name(" aN-De-denIse", null, " Sa-LA-Democh", null, null);
        Name firstAndLastNameTwo = new Name("   An dE Denise  ", null, "sa le democh  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInFirstAndLastName_DifferentFirstAndLastNames() {
        Name firstAndLastNameOne = new Name(" aN-Day-denIse", null, " Sa-LA-Democh", null, null);
        Name firstAndLastNameTwo = new Name("   An dE Denise  ", null, "sa le democh  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    //-------------------------------------------------------------------------- Hyphenated First Names -------------------------------------------------------------------------
    // If the name is less than 4 characters and is contained inside the match name, return maybe.
    // If the name is 4 or more characters and is contained inside the match name, return yes.
    @Test
    void doNamesMatch_YesMaybe_HyphenatedFirstNames() {
        Name firstAndLastNameOne = new Name(" An-Denise  ", null, " Doe", null, null);
        Name firstAndLastNameTwo = new Name("  aN", null, "Doe ", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" An-Denise  ", " Doe", "  aN", "Doe "));

        firstAndLastNameOne.setFullName("An-Denise", null, " Adams", null, null);
        firstAndLastNameTwo.setFullName("deNise  ", null, "Adams", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("An-Denise", " Adams", "deNise  ", "Adams"));

        firstAndLastNameOne.setFullName("an ", null, "Doe ", null, null);
        firstAndLastNameTwo.setFullName("An-Denise ", null, "Doe   ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("an ", "Doe ", "An-Denise ", "Doe   "));

        firstAndLastNameOne.setFullName(" dEniSe", null, " Adams", null, null);
        firstAndLastNameTwo.setFullName(" An-Denise", null, "Adams", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" dEniSe", " Adams", " An-Denise", "Adams"));
    }

    @Test
    void doNamesMatch_YesMaybeNo_FirstNamesContainMultiHyphens() {
        Name firstAndLastNameOne = new Name(" Josephine-mc-vitty", null, "Democh ", null, null);
        Name firstAndLastNameTwo = new Name("vittY", null, "dEmoch", null, null);

        // if all hyphen names are less than 4, assign maybe return, else return yes
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Democh ", "vittY", "dEmoch"));

        firstAndLastNameOne.setFullName("jOsephine-mc-vitty ", null, " Democh", null, null);
        firstAndLastNameTwo.setFullName("mc vitTy", null, "deMoch", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("jOsephine-mc-vitty ", " Democh", "mc vitTy", "deMoch"));

        firstAndLastNameOne.setFullName("  joSephine-mc-vitty", null, "Democh  ", null, null);
        firstAndLastNameTwo.setFullName("mC", null, "demOch", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  joSephine-mc-vitty", "Democh  ", "mC", "demOch"));

        firstAndLastNameOne.setFullName("josEphine-mc-vitty  ", null, "  Democh", null, null);
        firstAndLastNameTwo.setFullName("josephine Mc", null, "demoCh", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("josEphine-mc-vitty  ", "  Democh", "josephine Mc", "demoCh"));

        firstAndLastNameOne.setFullName(" josePhine-mc-vitty ", null, " Democh ", null, null);
        firstAndLastNameTwo.setFullName("josephinE", null, "democH", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" josePhine-mc-vitty ", " Democh ", "josephinE", "democH"));

        firstAndLastNameOne.setFullName("  josepHine-mc-vitty  ", null, "  DEmoch  ", null, null);
        firstAndLastNameTwo.setFullName("josephiNe vitty", null, "Democh", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josepHine-mc-vitty  ", "  DEmoch  ", "josephiNe vitty", "Democh"));

        firstAndLastNameOne.setFullName("Vitty", null, "DEmoch", null, null);
        firstAndLastNameTwo.setFullName(" josephine-Mc-vitty", null, " dEmoch", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("Vitty", "DEmoch", " josephine-Mc-vitty", " dEmoch"));

        firstAndLastNameOne.setFullName("Mc vitty", null, "DEmoch", null, null);
        firstAndLastNameTwo.setFullName("josephinE-mc-vitty ", null, "deMoch ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("Mc vitty", "DEmoch", "josephinE-mc-vitty ", "deMoch "));

        firstAndLastNameOne.setFullName("mC", null, "DEmoch", null, null);
        firstAndLastNameTwo.setFullName("  josephiNe-mc-vitty  ", null, "  demOch", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("mC", "DEmoch", "  josephiNe-mc-vitty  ", "  demOch"));

        firstAndLastNameOne.setFullName("josephine MC", null, "DEmoch", null, null);
        firstAndLastNameTwo.setFullName(" josephIne-mc-vitty ", null, "demoCh  ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("josephine MC", "DEmoch", " josephIne-mc-vitty ", "demoCh  "));

        firstAndLastNameOne.setFullName("josephinE", null, "DEmoch", null, null);
        firstAndLastNameTwo.setFullName("  josepHine-mc-vitty  ", null, " democH ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("josephinE", "DEmoch", "  josepHine-mc-vitty  ", " democH "));

        firstAndLastNameOne.setFullName(" josephine ViTty", null, " DEMOCH  ", null, null);
        firstAndLastNameTwo.setFullName(" josePhine-mc-vitty ", null, " democh ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" josephine ViTty", " DEMOCH  ", " josePhine-mc-vitty ", " democh "));

        firstAndLastNameOne.setFullName("JosePh ", null, "DemOch ", null, null);
        firstAndLastNameTwo.setFullName(" JosEphine-Mc-Vitty  ", null, " DeMoch  ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("JosePh ", "DemOch ", " JosEphine-Mc-Vitty  ", " DeMoch  "));

        firstAndLastNameOne.setFullName(" JOsephine-Mc-Vitty", null, " DemoCh", null, null);
        firstAndLastNameTwo.setFullName("  joSeph ", null, "  DemocH ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" JOsephine-Mc-Vitty", " DemoCh", "  joSeph ", "  DemocH "));
    }

    @Test
    void doNamesMatch_No_FirstNamesContainMultiHyphens_DifferentLastName() {
        Name firstAndLastNameOne = new Name(" Josephine-mc-vitty", null, "Bedi ", null, null);
        Name firstAndLastNameTwo = new Name("vittY", null, "dEmoch", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Bedi ", "vittY", "dEmoch"));

        firstAndLastNameOne.setFullName("jOsephine-mc-vitty ", null, " bEdi", null, null);
        firstAndLastNameTwo.setFullName("mc vitTy", null, "deMoch", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("jOsephine-mc-vitty ", " bEdi", "mc vitTy", "deMoch"));

        firstAndLastNameOne.setFullName("  joSephine-mc-vitty", null, "beDi  ", null, null);
        firstAndLastNameTwo.setFullName("mC", null, "demOch", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  joSephine-mc-vitty", "beDi  ", "mC", "demOch"));

        firstAndLastNameOne.setFullName("josEphine-mc-vitty  ", null, "  bedI", null, null);
        firstAndLastNameTwo.setFullName("josephine Mc", null, "demoCh", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("josEphine-mc-vitty  ", "  bedI", "josephine Mc", "demoCh"));

        firstAndLastNameOne.setFullName(" josePhine-mc-vitty ", null, "  BEdi  ", null, null);
        firstAndLastNameTwo.setFullName("josephinE", null, "democH", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" josePhine-mc-vitty ", "  BEdi  ", "josephinE", "democH"));

        firstAndLastNameOne.setFullName("  josepHine-mc-vitty  ", null, " BeDi ", null, null);
        firstAndLastNameTwo.setFullName("josephiNe vitty", null, "Democh", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josepHine-mc-vitty  ", " BeDi ", "josephiNe vitty", "Democh"));

        firstAndLastNameOne.setFullName("Vitty", null, "BedI", null, null);
        firstAndLastNameTwo.setFullName(" josephine-Mc-vitty", null, "dEmoch ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("Vitty", "BedI", " josephine-Mc-vitty", "dEmoch "));

        firstAndLastNameOne.setFullName("Mc vitty", null, "BEDi", null, null);
        firstAndLastNameTwo.setFullName("josephinE-mc-vitty ", null, " deMoch", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("Mc vitty", "BEDi", "josephinE-mc-vitty ", " deMoch"));

        firstAndLastNameOne.setFullName("mC", null, "BEdI", null, null);
        firstAndLastNameTwo.setFullName(" josephiNe-mc-vitty ", null, "demOch  ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("mC", "BEdI", " josephiNe-mc-vitty ", "demOch  "));

        firstAndLastNameOne.setFullName("josephine MC", null, "BEDI", null, null);
        firstAndLastNameTwo.setFullName("  josephIne-mc-vitty  ", null, "  demoCh", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("josephine MC", "BEDI", "  josephIne-mc-vitty  ", "  demoCh"));

        firstAndLastNameOne.setFullName("josephinE ", null, "BeDI", null, null);
        firstAndLastNameTwo.setFullName(" josepHine-mc-vitty  ", null, "  democH  ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("josephinE ", "BeDI", " josepHine-mc-vitty  ", "  democH  "));

        firstAndLastNameOne.setFullName("  josephine ViTty ", null, "bedi", null, null);
        firstAndLastNameTwo.setFullName(" josePhine-mc-vitty", null, " democh ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josephine ViTty ", "bedi", " josePhine-mc-vitty", " democh "));

        firstAndLastNameOne.setFullName(" JosePh  ", null, "  Demo ", null, null);
        firstAndLastNameTwo.setFullName("JosEphine-Mc-Vitty ", null, " Democh", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" JosePh  ", "  Demo ", "JosEphine-Mc-Vitty ", " Democh"));

        firstAndLastNameOne.setFullName("  JOsephine-Mc-Vitty ", null, " Demo  ", null, null);
        firstAndLastNameTwo.setFullName(" joSeph", null, "Democh ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  JOsephine-Mc-Vitty ", " Demo  ", " joSeph", "Democh "));
    }

    //-------------------------------------------------------------------------- Hyphenated Last Names --------------------------------------------------------------------------
    // If the name is less than 4 characters and is contained inside the match name, return maybe.
    // If the name is 4 or more characters and is contained inside the match name, return yes.
    @Test
    void doNamesMatch_YesMaybe_HyphenatedLastNames() {
        Name firstAndLastNameOne = new Name("jAne", null, "dOe-adaMs", null, null);
        Name firstAndLastNameTwo = new Name("janE", null, "DOE", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("jAne", "dOe-adaMs", "janE", "DOE"));

        firstAndLastNameOne.setFullName("jaNe", null, "doE-aDams", null, null);
        firstAndLastNameTwo.setFullName("jAne", null, "Adams", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("jaNe", "doE-aDams", "jAne", "Adams"));

        firstAndLastNameOne.setFullName("janE", null, "doe", null, null);
        firstAndLastNameTwo.setFullName("jaNe", null, "DOE-adams", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("janE", "doe", "jaNe", "DOE-adams"));

        firstAndLastNameOne.setFullName("JANE", null, "ADAMS", null, null);
        firstAndLastNameTwo.setFullName("jane", null, "DoE-adAmS", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("JANE", "ADAMS", "jane", "DoE-adAmS"));
    }

    @Test
    void doNamesMatch_YesMaybeNo_LastNamesContainMultiHyphens() {
        Name firstAndLastNameOne = new Name(" Areb", null, "Sa-la-democh ", null, null);
        Name firstAndLastNameTwo = new Name("aREB ", null, " democH", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" Areb", "Sa-la-democh ", "aREB ", " democH"));

        firstAndLastNameOne.setFullName("aReb ", null, " sA-La-democh", null, null);
        firstAndLastNameTwo.setFullName(" ArEB", null, "la demoCh ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("aReb ", " sA-La-democh", " ArEB", "la demoCh "));

        firstAndLastNameOne.setFullName(" arEb ", null, " sa-La-democh ", null, null);
        firstAndLastNameTwo.setFullName("  AReB  ", null, "  lA  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" arEb ", " sa-La-democh ", "  AReB  ", "  lA  "));

        firstAndLastNameOne.setFullName(" areB  ", null, "  sa-lA-democh ", null, null);
        firstAndLastNameTwo.setFullName("  AREb ", null, " sa La  ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" areB  ", "  sa-lA-democh ", "  AREb ", " sa La  "));

        firstAndLastNameOne.setFullName("  AReb ", null, " sa-la-Democh  ", null, null);
        firstAndLastNameTwo.setFullName(" aREB  ", null, "  sA ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  AReb ", " sa-la-Democh  ", " aREB  ", "  sA "));

        firstAndLastNameOne.setFullName("  ArEb  ", null, "  sa-la-dEmoch  ", null, null);
        firstAndLastNameTwo.setFullName(" aReB ", null, " Sa democh ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  ArEb  ", "  sa-la-dEmoch  ", " aReB ", " Sa democh "));

        firstAndLastNameOne.setFullName("AreB ", null, " Democh", null, null);
        firstAndLastNameTwo.setFullName(" aREb", null, "sa-la-democH ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("AreB ", " Democh", " aREb", "sa-la-democH "));

        firstAndLastNameOne.setFullName(" AReB", null, "lA democh ", null, null);
        firstAndLastNameTwo.setFullName("arEb ", null, " sa-la-demoCh", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" AReB", "lA democh ", "arEb ", " sa-la-demoCh"));

        firstAndLastNameOne.setFullName("  AREb  ", null, "  La  ", null, null);
        firstAndLastNameTwo.setFullName(" areB ", null, " sa-la-demOch ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  AREb  ", "  La  ", " areB ", " sa-la-demOch "));

        firstAndLastNameOne.setFullName("  ArEB ", null, " Sa la  ", null, null);
        firstAndLastNameTwo.setFullName(" aReb  ", null, "  sa-la-deMoch ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  ArEB ", " Sa la  ", " aReb  ", "  sa-la-deMoch "));

        firstAndLastNameOne.setFullName(" AREB  ", null, "  sA ", null, null);
        firstAndLastNameTwo.setFullName("  areb ", null, " sa-lA-democh  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" AREB  ", "  sA ", "  areb ", " sa-lA-democh  "));

        firstAndLastNameOne.setFullName(" areb ", null, " sa dEmoch ", null, null);
        firstAndLastNameTwo.setFullName(" AREB ", null, "  Sa-la-democh  ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" areb ", " sa dEmoch ", " AREB ", "  Sa-la-democh  "));

        firstAndLastNameOne.setFullName(" arEB  ", null, "  sa deM", null, null);
        firstAndLastNameTwo.setFullName(" AReb", null, " sa-la-Democh ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" arEB  ", "  sa deM", " AReb", " sa-la-Democh "));

        firstAndLastNameOne.setFullName("  aREB ", null, "sa-la-demOch  ", null, null);
        firstAndLastNameTwo.setFullName("Areb ", null, " sa dEm ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  aREB ", "sa-la-demOch  ", "Areb ", " sa dEm "));
    }

    @Test
    void doNamesMatch_No_LastNamesContainMultiHyphens_DifferentFirstName() {
        Name firstAndLastNameOne = new Name(" Satya", null, "Sa-la-democh ", null, null);
        Name firstAndLastNameTwo = new Name("aREB ", null, " democH", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" Satya", "Sa-la-democh ", "aREB ", " democH"));

        firstAndLastNameOne.setFullName("sAtya ", null, " sA-La-democh", null, null);
        firstAndLastNameTwo.setFullName(" ArEB", null, "la demoCh ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("sAtya ", " sA-La-democh", " ArEB", "la demoCh "));

        firstAndLastNameOne.setFullName(" saTya ", null, " sa-La-democh ", null, null);
        firstAndLastNameTwo.setFullName("  AReB  ", null, "  lA  ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" saTya ", " sa-La-democh ", "  AReB  ", "  lA  "));

        firstAndLastNameOne.setFullName(" satYa  ", null, "  sa-lA-democh ", null, null);
        firstAndLastNameTwo.setFullName("  AREb ", null, " sa La  ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" satYa  ", "  sa-lA-democh ", "  AREb ", " sa La  "));

        firstAndLastNameOne.setFullName("  satyA ", null, " sa-la-Democh  ", null, null);
        firstAndLastNameTwo.setFullName(" aREB  ", null, "  sA ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  satyA ", " sa-la-Democh  ", " aREB  ", "  sA "));

        firstAndLastNameOne.setFullName("  SAtya  ", null, "  sa-la-dEmoch  ", null, null);
        firstAndLastNameTwo.setFullName(" aReB ", null, " Sa democh ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  SAtya  ", "  sa-la-dEmoch  ", " aReB ", " Sa democh "));

        firstAndLastNameOne.setFullName("SaTya ", null, " Democh", null, null);
        firstAndLastNameTwo.setFullName(" aREb", null, "sa-la-democH ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("SaTya ", " Democh", " aREb", "sa-la-democH "));

        firstAndLastNameOne.setFullName(" SatYa", null, "lA democh ", null, null);
        firstAndLastNameTwo.setFullName("arEb ", null, " sa-la-demoCh", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" SatYa", "lA democh ", "arEb ", " sa-la-demoCh"));

        firstAndLastNameOne.setFullName("  SatyA  ", null, "  La  ", null, null);
        firstAndLastNameTwo.setFullName(" areB ", null, " sa-la-demOch ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  SatyA  ", "  La  ", " areB ", " sa-la-demOch "));

        firstAndLastNameOne.setFullName("  sATya ", null, " Sa la  ", null, null);
        firstAndLastNameTwo.setFullName(" aReb  ", null, "  sa-la-deMoch ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  sATya ", " Sa la  ", " aReb  ", "  sa-la-deMoch "));

        firstAndLastNameOne.setFullName(" sAtYa  ", null, "  sA ", null, null);
        firstAndLastNameTwo.setFullName("  areb ", null, " sa-lA-democh  ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" sAtYa  ", "  sA ", "  areb ", " sa-lA-democh  "));

        firstAndLastNameOne.setFullName(" sAtyA ", null, " sa dEmoch ", null, null);
        firstAndLastNameTwo.setFullName(" AREB ", null, "  Sa-la-democh  ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" saTYa  ", "  sa deM", " AREB ", "  Sa-la-democh  "));

        firstAndLastNameOne.setFullName(" saTYa  ", null, "  sa deM", null, null);
        firstAndLastNameTwo.setFullName(" AReb", null, " sa-la-Democh ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" saTYa  ", "  sa deM", " AReb", " sa-la-Democh "));

        firstAndLastNameOne.setFullName("  saTyA ", null, "sa-la-demOch  ", null, null);
        firstAndLastNameTwo.setFullName("Areb ", null, " sa dEm ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  saTyA ", "sa-la-demOch  ", "Areb ", " sa dEm "));
    }

    //--------------------------------------------------------------------- Hyphenated First and Last Names ---------------------------------------------------------------------
    // If the name is less than 4 characters and is contained inside the match name, return maybe.
    // If the name is 4 or more characters and is contained inside the match name, return yes.

    @Test
    void doNamesMatch_YesMaybe_HyphenatedFirstAndLastNames() {
        Name firstAndLastNameOne = new Name(" An-Denise  ", null, " Doe", null, null);
        Name firstAndLastNameTwo = new Name("  aN", null, "Doe ", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" An-Denise  ", " Doe", "  aN", "Doe "));

        firstAndLastNameOne.setFullName("An-Denise", null, " Adams", null, null);
        firstAndLastNameTwo.setFullName("deNise  ", null, "Adams", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("An-Denise", " Adams", "deNise  ", "Adams"));

        firstAndLastNameOne.setFullName("an ", null, "Doe ", null, null);
        firstAndLastNameTwo.setFullName("An-Denise ", null, "Doe   ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("an ", "Doe ", "An-Denise ", "Doe   "));

        firstAndLastNameOne.setFullName(" dEniSe", null, " Adams", null, null);
        firstAndLastNameTwo.setFullName(" An-Denise", null, "Adams", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" dEniSe", " Adams", " An-Denise", "Adams"));
    }

    @Test
    void doNamesMatch_YesMaybeNo_FirstAndLastNamesContainMultiHyphens() {
        Name firstAndLastNameOne = new Name(" Josephine-mc-vitty", null, "Sa-la-democh ", null, null);
        Name firstAndLastNameTwo = new Name("vittY ", null, " democH", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Sa-la-democh ", "vittY ", " democH"));

        firstAndLastNameOne.setFullName("jOsephine-mc-vitty ", null, " sA-La-democh", null, null);
        firstAndLastNameTwo.setFullName(" mc vitTy", null, "la demoCh ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("jOsephine-mc-vitty ", " sA-La-democh", " mc vitTy", "la demoCh "));

        firstAndLastNameOne.setFullName(" joSephine-mc-vitty ", null, " sa-La-democh ", null, null);
        firstAndLastNameTwo.setFullName("  mC  ", null, "  lA  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" joSephine-mc-vitty ", " sa-La-democh ", "  mC  ", "  lA  "));

        firstAndLastNameOne.setFullName(" josEphine-mc-vitty  ", null, "  sa-lA-democh ", null, null);
        firstAndLastNameTwo.setFullName("  josephine Mc ", null, " sa La  ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" josEphine-mc-vitty  ", "  sa-lA-democh ", "  josephine Mc ", " sa La  "));

        firstAndLastNameOne.setFullName("  josePhine-mc-vitty ", null, " sa-la-Democh  ", null, null);
        firstAndLastNameTwo.setFullName(" josephinE  ", null, "  sA ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josePhine-mc-vitty ", " sa-la-Democh  ", " josephinE  ", "  sA "));

        firstAndLastNameOne.setFullName("  josepHine-mc-vitty  ", null, "  sa-la-dEmoch  ", null, null);
        firstAndLastNameTwo.setFullName(" josephiNe vitty ", null, " Sa democh ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josepHine-mc-vitty  ", "  sa-la-dEmoch  ", " josephiNe vitty ", " Sa democh "));

        firstAndLastNameOne.setFullName("Vitty ", null, " Democh", null, null);
        firstAndLastNameTwo.setFullName(" josephine-Mc-vitty", null, "sa-la-democH ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("Vitty ", " Democh", " josephine-Mc-vitty", "sa-la-democH "));

        firstAndLastNameOne.setFullName(" Mc vitty", null, "LA democh ", null, null);
        firstAndLastNameTwo.setFullName("josephinE-mc-vitty ", null, " sa-la-demoCh", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" Mc vitty", "LA democh ", "josephinE-mc-vitty ", " sa-la-demoCh"));

        firstAndLastNameOne.setFullName("  mC  ", null, "  La  ", null, null);
        firstAndLastNameTwo.setFullName(" josephiNe-mc-vitty ", null, " sa-la-demOch ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  mC  ", "  La  ", " josephiNe-mc-vitty ", " sa-la-demOch "));

        firstAndLastNameOne.setFullName("  josephine MC ", null, " Sa la  ", null, null);
        firstAndLastNameTwo.setFullName(" josephIne-mc-vitty  ", null, "  sa-la-deMoch ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josephine MC ", " Sa la  ", " josephIne-mc-vitty  ", "  sa-la-deMoch "));

        firstAndLastNameOne.setFullName(" josephinE  ", null, "  sA ", null, null);
        firstAndLastNameTwo.setFullName("  josepHine-mc-vitty ", null, " sa-LA-democh  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" josephinE  ", "  sA ", "  josepHine-mc-vitty ", " sa-LA-democh  "));

        firstAndLastNameOne.setFullName(" josephine viTty ", null, " sa dEmoch ", null, null);
        firstAndLastNameTwo.setFullName(" josePhine-mc-vitty ", null, "  Sa-la-democh  ", null, null);
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" josephine viTty ", " sa dEmoch ", " josePhine-mc-vitty ", "  Sa-la-democh  "));

        firstAndLastNameOne.setFullName(" JosePh  ", null, "  leE", null, null);
        firstAndLastNameTwo.setFullName(" JosEphine-Mc-Vitty", null, " sa-la-Cathleen ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" JosePh  ", "  leE", " JosEphine-Mc-Vitty", " sa-la-Cathleen "));

        firstAndLastNameOne.setFullName("  JOsephine-Mc-Vitty ", null, "sa-la-cathLeen  ", null, null);
        firstAndLastNameTwo.setFullName(" la lEe ", null, "joSeph ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  JOsephine-Mc-Vitty ", "sa-la-cathLeen  ", "joSeph ", " la lEe "));
    }

    //----------------------------------------------------------------------- Last and First Names Swapped ----------------------------------------------------------------------
    @Test
    void doNamesMatch_Maybe_FirstLastSwap() {
        Name firstAndLastNameOne = new Name(" joHn  ", null, "  Doe ", null, null);
        Name firstAndLastNameTwo = new Name(" doe", null, "JOhn ", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_MaybeNo_FirstLastSwap_Punctuation() {
        Name firstAndLastNameOne = new Name(" aN-de-denIse  ", null, "  Sa-LA-Democh ", null, null);
        Name firstAndLastNameTwo = new Name(" sa la democh", null, "An dE Denise ", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" aN-de-denIse  ", "  Sa-LA-Democh ", " sa la democh", "An dE Denise "));

        firstAndLastNameOne.setFullName(" aN-Day-denIse  ", null, "  Sa-LA-Democh ", null, null);
        firstAndLastNameTwo.setFullName(" sa le democh", null, "An dE Denise ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" aN-Day-denIse  ", "  Sa-LA-Democh ", " sa le democh", "An dE Denise "));
    }

    @Test
    void doNamesMatch_Maybe_FirstLastSwap_Hyphenated() {
        Name firstAndLastNameOne = new Name(" Josephine-mc-vitty", null, "Sa-la-democh ", null, null);
        Name firstAndLastNameTwo = new Name("democH ", null, " vittY", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Sa-la-democh ", "democH ", " vittY"));

        firstAndLastNameOne.setFullName("jOsephine-mc-vitty ", null, " sA-La-democh", null, null);
        firstAndLastNameTwo.setFullName(" la demoCh", null, "mc vitTy ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("jOsephine-mc-vitty ", " sA-La-democh", " la demoCh", "mc vitTy "));

        firstAndLastNameOne.setFullName(" joSephine-mc-vitty ", null, " sa-La-democh ", null, null);
        firstAndLastNameTwo.setFullName("  lA  ", null, "  mC  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" joSephine-mc-vitty ", " sa-La-democh ", "  lA  ", "  mC  "));

        firstAndLastNameOne.setFullName(" josEphine-mc-vitty  ", null, "  sa-lA-democh ", null, null);
        firstAndLastNameTwo.setFullName("  sa La ", null, " josephine Mc  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" josEphine-mc-vitty  ", "  sa-lA-democh ", "  sa La ", " josephine Mc  "));

        firstAndLastNameOne.setFullName("  josePhine-mc-vitty ", null, " sa-la-Democh  ", null, null);
        firstAndLastNameTwo.setFullName(" sA  ", null, "  josephinE ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josePhine-mc-vitty ", " sa-la-Democh  ", " sA  ", "  josephinE "));

        firstAndLastNameOne.setFullName("  josepHine-mc-vitty  ", null, "  sa-la-dEmoch  ", null, null);
        firstAndLastNameTwo.setFullName(" Sa democh ", null, " josephiNe vitty ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josepHine-mc-vitty  ", "  sa-la-dEmoch  ", " Sa democh ", " josephiNe vitty "));

        firstAndLastNameOne.setFullName("Vitty ", null, " Democh", null, null);
        firstAndLastNameTwo.setFullName(" sa-la-democH", null, "josephine-Mc-vitty ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("Vitty ", " Democh", " sa-la-democH", "josephine-Mc-vitty "));

        firstAndLastNameOne.setFullName(" Mc vitty", null, "LA democh ", null, null);
        firstAndLastNameTwo.setFullName("sa-la-demoCh ", null, " josephinE-mc-vitty", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" Mc vitty", "LA democh ", "sa-la-demoCh ", " josephinE-mc-vitty"));

        firstAndLastNameOne.setFullName("  mC  ", null, "  La  ", null, null);
        firstAndLastNameTwo.setFullName(" sa-la-demOch ", null, " josephiNe-mc-vitty ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  mC  ", "  La  ", " sa-la-demOch ", " josephiNe-mc-vitty "));

        firstAndLastNameOne.setFullName("  josephine MC ", null, " Sa la  ", null, null);
        firstAndLastNameTwo.setFullName(" sa-la-deMoch  ", null, "  josephIne-mc-vitty ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josephine MC ", " Sa la  ", " sa-la-deMoch  ", "  josephIne-mc-vitty "));

        firstAndLastNameOne.setFullName(" josephinE  ", null, "  sA ", null, null);
        firstAndLastNameTwo.setFullName("  sa-LA-democh ", null, " josepHine-mc-vitty  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" josephinE  ", "  sA ", "  sa-LA-democh ", " josepHine-mc-vitty  "));

        firstAndLastNameOne.setFullName(" josephine viTty ", null, " sa dEmoch ", null, null);
        firstAndLastNameTwo.setFullName(" Sa-la-democh ", null, "  josePhine-mc-vitty  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" josephine viTty ", " sa dEmoch ", " Sa-la-democh ", "  josePhine-mc-vitty  "));
    }

    //--------------------------------------------------------------------------- First Name Initials ---------------------------------------------------------------------------
    // Rule: program will not allow last name initials
    // Only matches with a maybe if it is first name
    @Test
    void doNamesMatch_Maybe_FirstNameInitials() {
        Name firstAndLastNameOne = new Name(" John  ", null, "  Doe ", null, null);
        Name firstAndLastNameTwo = new Name("j ", null, " dOe", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" John  ", "  Doe ", "j ", " dOe"));

        firstAndLastNameOne.setFullName("J. ", null, " Doe", null, null);
        firstAndLastNameTwo.setFullName("  joHn ", null, " doe  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("J. ", " Doe", "  joHn ", " doe  "));
    }

    @Test
    void doNamesMatch_Maybe_FirstNameInitials_Punctuation() {
        Name firstAndLastNameOne = new Name(" JosepHine-mc-vitty  ", null, "  Doe ", null, null);
        Name firstAndLastNameTwo = new Name("j ", null, " dOe", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" JosepHine-mc-vitty  ", "  Doe ", "j ", " dOe"));

        firstAndLastNameOne.setFullName("J. ", null, " Doe", null, null);
        firstAndLastNameTwo.setFullName("  josepHine Mc Vitty ", null, " doe  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("J. ", " Doe", "  josepHine Mc Vitty ", " doe  "));

        firstAndLastNameOne.setFullName(" JosepHine-mc-vitty  ", null, "  sa-la-dEmoch ", null, null);
        firstAndLastNameTwo.setFullName("j ", null, " sa la Democh", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" JosepHine-mc-vitty  ", "  sa-la-dEmoch ", "j ", " sa la Democh"));

        firstAndLastNameOne.setFullName("J. ", null, " sa La democh", null, null);
        firstAndLastNameTwo.setFullName("  josepHine Mc Vitty ", null, " Sa-la-democh  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("J. ", " sa La democh", "  josepHine Mc Vitty ", " Sa-la-democh  "));
    }

    @Test
    void doNamesMatch_Maybe_FirstNameInitials_Hyphenated() {
        Name firstAndLastNameOne = new Name(" JosepHine-mc-vitty  ", null, "  Doe ", null, null);
        Name firstAndLastNameTwo = new Name("j ", null, " dOe", null, null);

        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" JosepHine-mc-vitty  ", "  Doe ", "j ", " dOe"));

        firstAndLastNameOne.setFullName("J. ", null, " Doe", null, null);
        firstAndLastNameTwo.setFullName("  josepHine Vitty ", null, " doe  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("J. ", " Doe", "  josepHine Vitty ", " doe  "));

        firstAndLastNameOne.setFullName(" JosepHine-mc-vitty  ", null, "  sa-la-dEmoch ", null, null);
        firstAndLastNameTwo.setFullName("j ", null, " Sa democh", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" JosepHine-mc-vitty  ", "  sa-la-dEmoch ", "j ", " Sa democh"));

        firstAndLastNameOne.setFullName("J. ", null, " Sa democh", null, null);
        firstAndLastNameTwo.setFullName("  josepHine Vitty ", null, " sa-la-dEmoch  ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("J. ", " Sa democh", "  josepHine Vitty ", " sa-la-dEmoch  "));
    }

    @Test
    void doNamesMatch_No_FirstNameInitialsDifferentLastName() {
        Name firstAndLastNameOne = new Name("John", null, "Adams", null, null);
        Name firstAndLastNameTwo = new Name("J", null, "Doe", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("John", "Adams", "J", "Doe"));

        firstAndLastNameOne.setFullName("J.", null, "Doe", null, null);
        firstAndLastNameTwo.setFullName("John", null, "Adams", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("J.", "Doe", "John", "Adams"));
    }

    //------------------------------------------------------------------------------- Other Tests -------------------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_Apostrophe() {
        Name firstAndLastNameOne = new Name("  adriaNno ", null, "d'onOfio ", null, null);
        Name firstAndLastNameTwo = new Name(" adriaNno", null, " d onofiO  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_Yes_Apostrophe_Punctuation() {
        Name firstAndLastNameOne = new Name("  josepHine-mc-vitty ", null, "d onofiO ", null, null);
        Name firstAndLastNameTwo = new Name(" josephiNe mc vItty", null, " d'onOfio  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }

    @Test
    void doNamesMatch_YesMaybe_Apostrophe_Hyphenated() {
        Name firstAndLastNameOne = new Name("  josepHine-mc-vitty ", null, "d'onOfio ", null, null);
        Name firstAndLastNameTwo = new Name(" josephiNe vItty", null, " d onofiO  ", null, null);

        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josepHine-mc-vitty ", "d'onOfio ", " josephiNe vItty", " d onofiO  "));

        firstAndLastNameOne.setFullName(" mc  ", null, " d oNofio", null, null);
        firstAndLastNameTwo.setFullName("Josephine-mc-vitty ", null, "  d'onoFio ", null, null);
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" mc vitty  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "));
    }

    @Test
    void doNamesMatch_No_Apostrophe_DifferentFirstName() {
        Name firstAndLastNameOne = new Name("  josepHine-mc-vitty ", null, "d'onOfio ", null, null);
        Name firstAndLastNameTwo = new Name(" josepH", null, " d onofiO  ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg("  josepHine-mc-vitty ", "d'onOfio ", " josepH", " d onofiO  "));

        firstAndLastNameOne.setFullName(" joSeph  ", null, " doNofio", null, null);
        firstAndLastNameTwo.setFullName("Josephine-mc-vitty ", null, "  d'onoFio ", null, null);
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo),
                firstAndLastNameFailedMsg(" joSeph  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "));
    }

    @Test
    void doNamesMatch_No_Apostrophe_NameCombined() { // Note: In the future, we may let this return type be decided by the user as yes or maybe.
        Name firstAndLastNameOne = new Name(" adriaNno  ", null, " doNofio", null, null);
        Name firstAndLastNameTwo = new Name("adriaNno ", null, "  d'onoFio ", null, null);

        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(firstAndLastNameOne, firstAndLastNameTwo));
    }
}
