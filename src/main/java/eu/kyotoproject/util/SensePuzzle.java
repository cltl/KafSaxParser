package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafSense;
import eu.kyotoproject.kaf.KafTerm;

/**
 * Created by piek on 22/09/15.
 */
public class SensePuzzle {


    static public void main (String[] args) {
      //  String pathToNafFile = "/Users/piek/Desktop/ignobel-short.xml";
        String pathToNafFile = "/Users/piek/Desktop/ignobel-short3.xml";
        String resource = "wn30g.bin64";
        KafSaxParser kafSaxParser = new KafSaxParser();
        kafSaxParser.parseFile(pathToNafFile);
        int nWords = kafSaxParser.kafWordFormList.size();
        int nSenses = 0;
        long nSenseCombinations = 0;
        for (int i = 0; i < kafSaxParser.kafTermList.size(); i++) {
            KafTerm kafTerm = kafSaxParser.kafTermList.get(i);
            int termSenses = 0;
            for (int j = 0; j < kafTerm.getSenseTags().size(); j++) {
                KafSense kafSense = kafTerm.getSenseTags().get(j);
                if (kafSense.getResource().equals(resource)) {
                    termSenses++;
                }
            }
            nSenses += termSenses;
            if (termSenses > 1)  {
                if (nSenseCombinations == 0) {
                    nSenseCombinations = termSenses;
                } else {
                    nSenseCombinations = nSenseCombinations * termSenses;
                }
             }
        }
        System.out.println("nWords = " + nWords);
        System.out.println("nSenses = " + nSenses);
        System.out.println("nSenseCombinations = " + nSenseCombinations);

    }
}
