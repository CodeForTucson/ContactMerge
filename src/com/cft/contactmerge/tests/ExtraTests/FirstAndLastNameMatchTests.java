package com.cft.contactmerge.tests.ExtraTests;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cft.contactmerge.*;

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
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" JoHn", "DOE ", "JOHN ", " Doe  "));
    }

    @Test
    void doNamesMatch_No_DifferentLastName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" JoHn", "DOE ", "JOHN ", " aDams  "));
    }

    @Test
    void doNamesMatch_No_DifferentFirstName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" JoHn", "DOE ", "jAne ", " Doe  "));
    }

    //------------------------------------------------------------------------- Punctuation First Names -------------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_IgnorePunctuationInFirstName() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("aN-denIse ", "Doe ", " An Denise ", "   doe "));
    }

    @Test
    void doNamesMatch_Yes_IgnoreMultiPunctuationInFirstName() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("aN-De-denIse ", "dOe ", " An dE Denise ", "   doe "));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInFirstName_DifferentLastNames() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" aN-De-denIse", " dOe", "   An dE Denise  ", " adams  "));
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
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("Jane ", "Doe-adams ", "Jane", "  Doe Adams"));
    }

    @Test
    void doNamesMatch_Yes_IgnoreMultiPunctuationInLastName() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" arEb", " Sa-LA-Democh", "Areb", "sa la democh  "));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInLastName_DifferentFirstNames() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" haRi", " Sa-LA-Democh", "Areb", "sa la democh  "));
    }

    //--------------------------------------------------------------------- Punctuation First And Last Names --------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_IgnorePunctuationInFirstAndLastName() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("aN-denIse ", "Doe-adams ", " An Denise ", "   Doe Adams"));
    }

    @Test
    void doNamesMatch_Yes_IgnoreMultiPunctuationInFirstAndLastName() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("aN-De-denIse ", " Sa-LA-Democh ", " An dE Denise ", "sa la democh  "));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInFirstAndLastName_DifferentFirstNames() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" aN-Day-denIse", " Sa-LA-Democh", "   An dE Denise  ", "sa la democh  "));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInFirstAndLastName_DifferentLastNames() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" aN-De-denIse", " Sa-LA-Democh", "   An dE Denise  ", "sa le democh  "));
    }

    @Test
    void doNamesMatch_No_IgnoreMultiPunctuationInFirstAndLastName_DifferentFirstAndLastNames() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" aN-Day-denIse", " Sa-LA-Democh", "   An dE Denise  ", "sa le democh  "));
    }

    //-------------------------------------------------------------------------- Hyphenated First Names -------------------------------------------------------------------------
    // If the name is less than 4 characters and is contained inside the match name, return maybe.
    // If the name is 4 or more characters and is contained inside the match name, return yes.
    @Test
    void doNamesMatch_YesMaybe_HyphenatedFirstNames() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" An-Denise  ", " Doe", "  aN", "Doe "), firstAndLastNameFailedMsg(" An-Denise  ", " Doe", "  aN", "Doe "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("An-Denise", " Adams", "deNise  ", "Adams"), firstAndLastNameFailedMsg("An-Denise", " Adams", "deNise  ", "Adams"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("an ", "Doe ", "An-Denise ", "Doe   "), firstAndLastNameFailedMsg("an ", "Doe ", "An-Denise ", "Doe   "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" dEniSe", " Adams", " An-Denise", "Adams"), firstAndLastNameFailedMsg(" dEniSe", " Adams", " An-Denise", "Adams"));
    }

    @Test
    void doNamesMatch_YesMaybeNo_FirstNamesContainMultiHyphens() {
        // if all hyphen names are less than 4, assign maybe return, else return yes
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" Josephine-mc-vitty", "Democh ", "vittY", "dEmoch"), firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Democh ", "vittY", "dEmoch"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("jOsephine-mc-vitty ", " Democh", "mc vitTy", "deMoch"), firstAndLastNameFailedMsg("jOsephine-mc-vitty ", " Democh", "mc vitTy", "deMoch"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("  joSephine-mc-vitty", "Democh  ", "mC", "demOch"), firstAndLastNameFailedMsg("  joSephine-mc-vitty", "Democh  ", "mC", "demOch"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("josEphine-mc-vitty  ", "  Democh", "josephine Mc", "demoCh"), firstAndLastNameFailedMsg("josEphine-mc-vitty  ", "  Democh", "josephine Mc", "demoCh"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" josePhine-mc-vitty ", " Democh ", "josephinE", "democH"), firstAndLastNameFailedMsg(" josePhine-mc-vitty ", " Democh ", "josephinE", "democH"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  josepHine-mc-vitty  ", "  DEmoch  ", "josephiNe vitty", "Democh"), firstAndLastNameFailedMsg("  josepHine-mc-vitty  ", "  DEmoch  ", "josephiNe vitty", "Democh"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("Vitty", "DEmoch", " josephine-Mc-vitty", " dEmoch"), firstAndLastNameFailedMsg("Vitty", "DEmoch", " josephine-Mc-vitty", " dEmoch"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("Mc vitty", "DEmoch", "josephinE-mc-vitty ", "deMoch "), firstAndLastNameFailedMsg("Mc vitty", "DEmoch", "josephinE-mc-vitty ", "deMoch "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("mC", "DEmoch", "  josephiNe-mc-vitty  ", "  demOch"), firstAndLastNameFailedMsg("mC", "DEmoch", "  josephiNe-mc-vitty  ", "  demOch"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("josephine MC", "DEmoch", " josephIne-mc-vitty ", "demoCh  "), firstAndLastNameFailedMsg("josephine MC", "DEmoch", " josephIne-mc-vitty ", "demoCh  "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("josephinE", "DEmoch", "  josepHine-mc-vitty  ", " democH "), firstAndLastNameFailedMsg("josephinE", "DEmoch", "  josepHine-mc-vitty  ", " democH "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" josephine ViTty", " DEMOCH  ", " josePhine-mc-vitty ", " democh "), firstAndLastNameFailedMsg(" josephine ViTty", " DEMOCH  ", " josePhine-mc-vitty ", " democh "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("JosePh ", "DemOch ", " JosEphine-Mc-Vitty  ", " DeMoch  "), firstAndLastNameFailedMsg("JosePh ", "DemOch ", " JosEphine-Mc-Vitty  ", " DeMoch  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" JOsephine-Mc-Vitty", " DemoCh", "  joSeph ", "  DemocH "), firstAndLastNameFailedMsg(" JOsephine-Mc-Vitty", " DemoCh", "  joSeph ", "  DemocH "));
    }

    @Test
    void doNamesMatch_No_FirstNamesContainMultiHyphens_DifferentLastName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" Josephine-mc-vitty", "Bedi ", "vittY", "dEmoch"), firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Bedi ", "vittY", "dEmoch"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("jOsephine-mc-vitty ", " bEdi", "mc vitTy", "deMoch"), firstAndLastNameFailedMsg("jOsephine-mc-vitty ", " bEdi", "mc vitTy", "deMoch"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  joSephine-mc-vitty", "beDi  ", "mC", "demOch"), firstAndLastNameFailedMsg("  joSephine-mc-vitty", "beDi  ", "mC", "demOch"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("josEphine-mc-vitty  ", "  bedI", "josephine Mc", "demoCh"), firstAndLastNameFailedMsg("josEphine-mc-vitty  ", "  bedI", "josephine Mc", "demoCh"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" josePhine-mc-vitty ", "  BEdi  ", "josephinE", "democH"), firstAndLastNameFailedMsg(" josePhine-mc-vitty ", "  BEdi  ", "josephinE", "democH"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  josepHine-mc-vitty  ", " BeDi ", "josephiNe vitty", "Democh"), firstAndLastNameFailedMsg("  josepHine-mc-vitty  ", " BeDi ", "josephiNe vitty", "Democh"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("Vitty", "BedI", " josephine-Mc-vitty", "dEmoch "), firstAndLastNameFailedMsg("Vitty", "BedI", " josephine-Mc-vitty", "dEmoch "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("Mc vitty", "BEDi", "josephinE-mc-vitty ", " deMoch"), firstAndLastNameFailedMsg("Mc vitty", "BEDi", "josephinE-mc-vitty ", " deMoch"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("mC", "BEdI", " josephiNe-mc-vitty ", "demOch  "), firstAndLastNameFailedMsg("mC", "BEdI", " josephiNe-mc-vitty ", "demOch  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("josephine MC", "BEDI", "  josephIne-mc-vitty  ", "  demoCh"), firstAndLastNameFailedMsg("josephine MC", "BEDI", "  josephIne-mc-vitty  ", "  demoCh"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("josephinE ", "BeDI", " josepHine-mc-vitty  ", "  democH  "), firstAndLastNameFailedMsg("josephinE ", "BeDI", " josepHine-mc-vitty  ", "  democH  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  josephine ViTty ", "bedi", " josePhine-mc-vitty", " democh "), firstAndLastNameFailedMsg("  josephine ViTty ", "bedi", " josePhine-mc-vitty", " democh "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" JosePh  ", "  Demo ", "JosEphine-Mc-Vitty ", " Democh"), firstAndLastNameFailedMsg(" JosePh  ", "  Demo ", "JosEphine-Mc-Vitty ", " Democh"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  JOsephine-Mc-Vitty ", " Demo  ", " joSeph", "Democh "), firstAndLastNameFailedMsg("  JOsephine-Mc-Vitty ", " Demo  ", " joSeph", "Democh "));
    }

    //-------------------------------------------------------------------------- Hyphenated Last Names --------------------------------------------------------------------------
    // If the name is less than 4 characters and is contained inside the match name, return maybe.
    // If the name is 4 or more characters and is contained inside the match name, return yes.
    @Test
    void doNamesMatch_YesMaybe_HyphenatedLastNames() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("jAne", "dOe-adaMs", "janE", "DOE"), firstAndLastNameFailedMsg("jAne", "dOe-adaMs", "janE", "DOE"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("jaNe", "doE-aDams", "jAne", "Adams"), firstAndLastNameFailedMsg("jaNe", "doE-aDams", "jAne", "Adams"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("janE", "doe", "jaNe", "DOE-adams"), firstAndLastNameFailedMsg("janE", "doe", "jaNe", "DOE-adams"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("JANE", "ADAMS", "jane", "DoE-adAmS"), firstAndLastNameFailedMsg("JANE", "ADAMS", "jane", "DoE-adAmS"));
    }

    @Test
    void doNamesMatch_YesMaybeNo_LastNamesContainMultiHyphens() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" Areb", "Sa-la-democh ", "aREB ", " democH"), firstAndLastNameFailedMsg(" Areb", "Sa-la-democh ", "aREB ", " democH"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("aReb ", " sA-La-democh", " ArEB", "la demoCh "), firstAndLastNameFailedMsg("aReb ", " sA-La-democh", " ArEB", "la demoCh "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" arEb ", " sa-La-democh ", "  AReB  ", "  lA  "), firstAndLastNameFailedMsg(" arEb ", " sa-La-democh ", "  AReB  ", "  lA  "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" areB  ", "  sa-lA-democh ", "  AREb ", " sa La  "), firstAndLastNameFailedMsg(" areB  ", "  sa-lA-democh ", "  AREb ", " sa La  "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("  AReb ", " sa-la-Democh  ", " aREB  ", "  sA "), firstAndLastNameFailedMsg("  AReb ", " sa-la-Democh  ", " aREB  ", "  sA "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  ArEb  ", "  sa-la-dEmoch  ", " aReB ", " Sa democh "), firstAndLastNameFailedMsg("  ArEb  ", "  sa-la-dEmoch  ", " aReB ", " Sa democh "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("AreB ", " Democh", " aREb", "sa-la-democH "), firstAndLastNameFailedMsg("AreB ", " Democh", " aREb", "sa-la-democH "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" AReB", "lA democh ", "arEb ", " sa-la-demoCh"), firstAndLastNameFailedMsg(" AReB", "lA democh ", "arEb ", " sa-la-demoCh"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("  AREb  ", "  La  ", " areB ", " sa-la-demOch "), firstAndLastNameFailedMsg("  AREb  ", "  La  ", " areB ", " sa-la-demOch "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  ArEB ", " Sa la  ", " aReb  ", "  sa-la-deMoch "), firstAndLastNameFailedMsg("  ArEB ", " Sa la  ", " aReb  ", "  sa-la-deMoch "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" AREB  ", "  sA ", "  areb ", " sa-lA-democh  "), firstAndLastNameFailedMsg(" AREB  ", "  sA ", "  areb ", " sa-lA-democh  "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" areb ", " sa dEmoch ", " AREB ", "  Sa-la-democh  "), firstAndLastNameFailedMsg(" areb ", " sa dEmoch ", " AREB ", "  Sa-la-democh  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" arEB  ", "  sa deM", " AReb", " sa-la-Democh "), firstAndLastNameFailedMsg(" arEB  ", "  sa deM", " AReb", " sa-la-Democh "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  aREB ", "sa-la-demOch  ", "Areb ", " sa dEm "), firstAndLastNameFailedMsg("  aREB ", "sa-la-demOch  ", "Areb ", " sa dEm "));
    }

    @Test
    void doNamesMatch_No_LastNamesContainMultiHyphens_DifferentFirstName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" Satya", "Sa-la-democh ", "aREB ", " democH"), firstAndLastNameFailedMsg(" Satya", "Sa-la-democh ", "aREB ", " democH"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("sAtya ", " sA-La-democh", " ArEB", "la demoCh "), firstAndLastNameFailedMsg("sAtya ", " sA-La-democh", " ArEB", "la demoCh "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" saTya ", " sa-La-democh ", "  AReB  ", "  lA  "), firstAndLastNameFailedMsg(" saTya ", " sa-La-democh ", "  AReB  ", "  lA  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" satYa  ", "  sa-lA-democh ", "  AREb ", " sa La  "), firstAndLastNameFailedMsg(" satYa  ", "  sa-lA-democh ", "  AREb ", " sa La  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  satyA ", " sa-la-Democh  ", " aREB  ", "  sA "), firstAndLastNameFailedMsg("  satyA ", " sa-la-Democh  ", " aREB  ", "  sA "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  SAtya  ", "  sa-la-dEmoch  ", " aReB ", " Sa democh "), firstAndLastNameFailedMsg("  SAtya  ", "  sa-la-dEmoch  ", " aReB ", " Sa democh "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("SaTya ", " Democh", " aREb", "sa-la-democH "), firstAndLastNameFailedMsg("SaTya ", " Democh", " aREb", "sa-la-democH "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" SatYa", "lA democh ", "arEb ", " sa-la-demoCh"), firstAndLastNameFailedMsg(" SatYa", "lA democh ", "arEb ", " sa-la-demoCh"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  SatyA  ", "  La  ", " areB ", " sa-la-demOch "), firstAndLastNameFailedMsg("  SatyA  ", "  La  ", " areB ", " sa-la-demOch "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  sATya ", " Sa la  ", " aReb  ", "  sa-la-deMoch "), firstAndLastNameFailedMsg("  sATya ", " Sa la  ", " aReb  ", "  sa-la-deMoch "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" sAtYa  ", "  sA ", "  areb ", " sa-lA-democh  "), firstAndLastNameFailedMsg(" sAtYa  ", "  sA ", "  areb ", " sa-lA-democh  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" sAtyA ", " sa dEmoch ", " AREB ", "  Sa-la-democh  "), firstAndLastNameFailedMsg(" saTYa  ", "  sa deM", " AREB ", "  Sa-la-democh  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" saTYa  ", "  sa deM", " AReb", " sa-la-Democh "), firstAndLastNameFailedMsg(" saTYa  ", "  sa deM", " AReb", " sa-la-Democh "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  saTyA ", "sa-la-demOch  ", "Areb ", " sa dEm "), firstAndLastNameFailedMsg("  saTyA ", "sa-la-demOch  ", "Areb ", " sa dEm "));
    }

    //--------------------------------------------------------------------- Hyphenated First and Last Names ---------------------------------------------------------------------
    // If the name is less than 4 characters and is contained inside the match name, return maybe.
    // If the name is 4 or more characters and is contained inside the match name, return yes.

    @Test
    void doNamesMatch_YesMaybe_HyphenatedFirstAndLastNames() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" An-Denise  ", " Doe", "  aN", "Doe "), firstAndLastNameFailedMsg(" An-Denise  ", " Doe", "  aN", "Doe "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("An-Denise", " Adams", "deNise  ", "Adams"), firstAndLastNameFailedMsg("An-Denise", " Adams", "deNise  ", "Adams"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("an ", "Doe ", "An-Denise ", "Doe   "), firstAndLastNameFailedMsg("an ", "Doe ", "An-Denise ", "Doe   "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" dEniSe", " Adams", " An-Denise", "Adams"), firstAndLastNameFailedMsg(" dEniSe", " Adams", " An-Denise", "Adams"));
    }

    @Test
    void doNamesMatch_YesMaybeNo_FirstAndLastNamesContainMultiHyphens() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" Josephine-mc-vitty", "Sa-la-democh ", "vittY ", " democH"), firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Sa-la-democh ", "vittY ", " democH"));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("jOsephine-mc-vitty ", " sA-La-democh", " mc vitTy", "la demoCh "), firstAndLastNameFailedMsg("jOsephine-mc-vitty ", " sA-La-democh", " mc vitTy", "la demoCh "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" joSephine-mc-vitty ", " sa-La-democh ", "  mC  ", "  lA  "), firstAndLastNameFailedMsg(" joSephine-mc-vitty ", " sa-La-democh ", "  mC  ", "  lA  "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" josEphine-mc-vitty  ", "  sa-lA-democh ", "  josephine Mc ", " sa La  "), firstAndLastNameFailedMsg(" josEphine-mc-vitty  ", "  sa-lA-democh ", "  josephine Mc ", " sa La  "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("  josePhine-mc-vitty ", " sa-la-Democh  ", " josephinE  ", "  sA "), firstAndLastNameFailedMsg("  josePhine-mc-vitty ", " sa-la-Democh  ", " josephinE  ", "  sA "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  josepHine-mc-vitty  ", "  sa-la-dEmoch  ", " josephiNe vitty ", " Sa democh "), firstAndLastNameFailedMsg("  josepHine-mc-vitty  ", "  sa-la-dEmoch  ", " josephiNe vitty ", " Sa democh "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("Vitty ", " Democh", " josephine-Mc-vitty", "sa-la-democH "), firstAndLastNameFailedMsg("Vitty ", " Democh", " josephine-Mc-vitty", "sa-la-democH "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" Mc vitty", "LA democh ", "josephinE-mc-vitty ", " sa-la-demoCh"), firstAndLastNameFailedMsg(" Mc vitty", "LA democh ", "josephinE-mc-vitty ", " sa-la-demoCh"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("  mC  ", "  La  ", " josephiNe-mc-vitty ", " sa-la-demOch "), firstAndLastNameFailedMsg("  mC  ", "  La  ", " josephiNe-mc-vitty ", " sa-la-demOch "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  josephine MC ", " Sa la  ", " josephIne-mc-vitty  ", "  sa-la-deMoch "), firstAndLastNameFailedMsg("  josephine MC ", " Sa la  ", " josephIne-mc-vitty  ", "  sa-la-deMoch "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" josephinE  ", "  sA ", "  josepHine-mc-vitty ", " sa-LA-democh  "), firstAndLastNameFailedMsg(" josephinE  ", "  sA ", "  josepHine-mc-vitty ", " sa-LA-democh  "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" josephine viTty ", " sa dEmoch ", " josePhine-mc-vitty ", "  Sa-la-democh  "), firstAndLastNameFailedMsg(" josephine viTty ", " sa dEmoch ", " josePhine-mc-vitty ", "  Sa-la-democh  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" JosePh  ", "  leE", " JosEphine-Mc-Vitty", " sa-la-Cathleen "), firstAndLastNameFailedMsg(" JosePh  ", "  leE", " JosEphine-Mc-Vitty", " sa-la-Cathleen "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  JOsephine-Mc-Vitty ", "sa-la-cathLeen  ", "joSeph ", " la lEe "), firstAndLastNameFailedMsg("  JOsephine-Mc-Vitty ", "sa-la-cathLeen  ", "joSeph ", " la lEe "));
    }

    //----------------------------------------------------------------------- Last and First Names Swapped ----------------------------------------------------------------------
    @Test
    void doNamesMatch_Maybe_FirstLastSwap() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" joHn  ", "  Doe ", " doe", "JOhn "));
    }

    @Test
    void doNamesMatch_MaybeNo_FirstLastSwap_Punctuation() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" aN-de-denIse  ", "  Sa-LA-Democh ", " sa la democh", "An dE Denise "), firstAndLastNameFailedMsg(" aN-de-denIse  ", "  Sa-LA-Democh ", " sa la democh", "An dE Denise "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" aN-Day-denIse  ", "  Sa-LA-Democh ", " sa le democh", "An dE Denise "), firstAndLastNameFailedMsg(" aN-Day-denIse  ", "  Sa-LA-Democh ", " sa le democh", "An dE Denise "));
    }

    @Test
    void doNamesMatch_Maybe_FirstLastSwap_Hyphenated() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" Josephine-mc-vitty", "Sa-la-democh ", "democH ", " vittY"), firstAndLastNameFailedMsg(" Josephine-mc-vitty", "Sa-la-democh ", "democH ", " vittY"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("jOsephine-mc-vitty ", " sA-La-democh", " la demoCh", "mc vitTy "), firstAndLastNameFailedMsg("jOsephine-mc-vitty ", " sA-La-democh", " la demoCh", "mc vitTy "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" joSephine-mc-vitty ", " sa-La-democh ", "  lA  ", "  mC  "), firstAndLastNameFailedMsg(" joSephine-mc-vitty ", " sa-La-democh ", "  lA  ", "  mC  "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" josEphine-mc-vitty  ", "  sa-lA-democh ", "  sa La ", " josephine Mc  "), firstAndLastNameFailedMsg(" josEphine-mc-vitty  ", "  sa-lA-democh ", "  sa La ", " josephine Mc  "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("  josePhine-mc-vitty ", " sa-la-Democh  ", " sA  ", "  josephinE "), firstAndLastNameFailedMsg("  josePhine-mc-vitty ", " sa-la-Democh  ", " sA  ", "  josephinE "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("  josepHine-mc-vitty  ", "  sa-la-dEmoch  ", " Sa democh ", " josephiNe vitty "), firstAndLastNameFailedMsg("  josepHine-mc-vitty  ", "  sa-la-dEmoch  ", " Sa democh ", " josephiNe vitty "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("Vitty ", " Democh", " sa-la-democH", "josephine-Mc-vitty "), firstAndLastNameFailedMsg("Vitty ", " Democh", " sa-la-democH", "josephine-Mc-vitty "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" Mc vitty", "LA democh ", "sa-la-demoCh ", " josephinE-mc-vitty"), firstAndLastNameFailedMsg(" Mc vitty", "LA democh ", "sa-la-demoCh ", " josephinE-mc-vitty"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("  mC  ", "  La  ", " sa-la-demOch ", " josephiNe-mc-vitty "), firstAndLastNameFailedMsg("  mC  ", "  La  ", " sa-la-demOch ", " josephiNe-mc-vitty "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("  josephine MC ", " Sa la  ", " sa-la-deMoch  ", "  josephIne-mc-vitty "), firstAndLastNameFailedMsg("  josephine MC ", " Sa la  ", " sa-la-deMoch  ", "  josephIne-mc-vitty "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" josephinE  ", "  sA ", "  sa-LA-democh ", " josepHine-mc-vitty  "), firstAndLastNameFailedMsg(" josephinE  ", "  sA ", "  sa-LA-democh ", " josepHine-mc-vitty  "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" josephine viTty ", " sa dEmoch ", " Sa-la-democh ", "  josePhine-mc-vitty  "), firstAndLastNameFailedMsg(" josephine viTty ", " sa dEmoch ", " Sa-la-democh ", "  josePhine-mc-vitty  "));
    }

    //--------------------------------------------------------------------------- First Name Initials ---------------------------------------------------------------------------
    // Rule: program will not allow last name initials
    // Only matches with a maybe if it is first name
    @Test
    void doNamesMatch_Maybe_FirstNameInitials() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" John  ", "  Doe ", "j ", " dOe"), firstAndLastNameFailedMsg(" John  ", "  Doe ", "j ", " dOe"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("J. ", " Doe", "  joHn ", " doe  "), firstAndLastNameFailedMsg("J. ", " Doe", "  joHn ", " doe  "));
    }

    @Test
    void doNamesMatch_Maybe_FirstNameInitials_Punctuation() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" JosepHine-mc-vitty  ", "  Doe ", "j ", " dOe"), firstAndLastNameFailedMsg(" JosepHine-mc-vitty  ", "  Doe ", "j ", " dOe"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("J. ", " Doe", "  josepHine Mc Vitty ", " doe  "), firstAndLastNameFailedMsg("J. ", " Doe", "  josepHine Mc Vitty ", " doe  "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" JosepHine-mc-vitty  ", "  sa-la-dEmoch ", "j ", " sa la Democh"), firstAndLastNameFailedMsg(" JosepHine-mc-vitty  ", "  sa-la-dEmoch ", "j ", " sa la Democh"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("J. ", " sa La democh", "  josepHine Mc Vitty ", " Sa-la-democh  "), firstAndLastNameFailedMsg("J. ", " sa La democh", "  josepHine Mc Vitty ", " Sa-la-democh  "));
    }

    @Test
    void doNamesMatch_Maybe_FirstNameInitials_Hyphenated() {
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" JosepHine-mc-vitty  ", "  Doe ", "j ", " dOe"), firstAndLastNameFailedMsg(" JosepHine-mc-vitty  ", "  Doe ", "j ", " dOe"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("J. ", " Doe", "  josepHine Vitty ", " doe  "), firstAndLastNameFailedMsg("J. ", " Doe", "  josepHine Vitty ", " doe  "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" JosepHine-mc-vitty  ", "  sa-la-dEmoch ", "j ", " Sa democh"), firstAndLastNameFailedMsg(" JosepHine-mc-vitty  ", "  sa-la-dEmoch ", "j ", " Sa democh"));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch("J. ", " Sa democh", "  josepHine Vitty ", " sa-la-dEmoch  "), firstAndLastNameFailedMsg("J. ", " Sa democh", "  josepHine Vitty ", " sa-la-dEmoch  "));
    }

    @Test
    void doNamesMatch_No_FirstNameInitialsDifferentLastName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("John", "Adams", "J", "Doe"), firstAndLastNameFailedMsg("John", "Adams", "J", "Doe"));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("J.", "Doe", "John", "Adams"), firstAndLastNameFailedMsg("J.", "Doe", "John", "Adams"));
    }

    //------------------------------------------------------------------------------- Other Tests -------------------------------------------------------------------------------
    @Test
    void doNamesMatch_Yes_Apostrophe() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  adriaNno ", "d'onOfio ", " adriaNno", " d onofiO  "), firstAndLastNameFailedMsg("  adriaNno ", "d'onOfio ", " adriaNno", " d onofiO  "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" adriaNno  ", " doNofio", "adriaNno ", "  d'onoFio "), firstAndLastNameFailedMsg(" adriaNno  ", " doNofio", "adriaNno ", "  d'onoFio "));
    }

    @Test
    void doNamesMatch_Yes_Apostrophe_Punctuation() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  josepHine-mc-vitty ", "d'onOfio ", " josephiNe mc vItty", " d onofiO  "), firstAndLastNameFailedMsg("  josepHine-mc-vitty ", "d'onOfio ", " josephiNe mc vItty", " d onofiO  "));
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch(" josepHine mc vitty  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "), firstAndLastNameFailedMsg(" josepHine mc vitty  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "));
    }

    @Test
    void doNamesMatch_YesMaybe_Apostrophe_Hyphenated() {
        assertEquals(AnswerType.yes, CompareContactParts.doNamesMatch("  josepHine-mc-vitty ", "d'onOfio ", " josephiNe vItty", " d onofiO  "), firstAndLastNameFailedMsg("  josepHine-mc-vitty ", "d'onOfio ", " josephiNe vItty", " d onofiO  "));
        assertEquals(AnswerType.maybe, CompareContactParts.doNamesMatch(" mc  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "), firstAndLastNameFailedMsg(" mc vitty  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "));
    }

    @Test
    void doNamesMatch_No_Apostrophe_DifferentFirstName() {
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch("  josepHine-mc-vitty ", "d'onOfio ", " josepH", " d onofiO  "), firstAndLastNameFailedMsg("  josepHine-mc-vitty ", "d'onOfio ", " josepH", " d onofiO  "));
        assertEquals(AnswerType.no, CompareContactParts.doNamesMatch(" joSeph  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "), firstAndLastNameFailedMsg(" joSeph  ", " doNofio", "Josephine-mc-vitty ", "  d'onoFio "));
    }
}
