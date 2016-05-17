package eu.kyotoproject.util;

import eu.kyotoproject.kaf.CorefTarget;
import eu.kyotoproject.kaf.KafCoreferenceSet;
import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafTerm;

import java.io.*;
import java.util.*;

/**
 * Created by piek on 11/10/14.
 */
public class FixEventCoreferences {

    static public void main (String[] args) {
       // String pathToFile = args[0];
        String pathToPredicateFile = "";
        String pathToFile = "";
        Vector<String> predicates = readFileToVector(pathToPredicateFile);
        KafSaxParser kafSaxParser = new KafSaxParser();
        kafSaxParser.parseFile(System.in);
        fixEventCoreferenceSets(kafSaxParser, predicates);
        kafSaxParser.writeNafToStream(System.out);

/*        try {
            OutputStream fos = new FileOutputStream(pathToFile+".fix");
            kafSaxParser.writeNafToStream(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }




    static boolean breakNeeded (KafCoreferenceSet kafCoreferenceSet, KafSaxParser kafSaxParser, Vector<String> predicates) {
        for (int j = 0; j < kafCoreferenceSet.getSetsOfSpans().size(); j++) {
            ArrayList<CorefTarget> corefTargets = kafCoreferenceSet.getSetsOfSpans().get(j);
            String lemma = "";
            for (int k = 0; k < corefTargets.size(); k++) {
                CorefTarget corefTarget = corefTargets.get(k);
                KafTerm kafTerm = kafSaxParser.getTerm(corefTarget.getId());
                if (kafTerm!=null) {
                    lemma += kafTerm.getLemma()+" ";
                }
            }
            lemma = lemma.trim();
            if (predicates.contains(lemma)) {
                return true;
            }
        }
        return false;
    }

    static void fixEventCoreferenceSets (KafSaxParser kafSaxParser, Vector<String> predicates) {
        ArrayList<KafCoreferenceSet> fixedSets = new ArrayList<KafCoreferenceSet>();
        for (int i = 0; i < kafSaxParser.kafCorefenceArrayList.size(); i++) {
            KafCoreferenceSet kafCoreferenceSet = kafSaxParser.kafCorefenceArrayList.get(i);
            if (kafCoreferenceSet.getType().toLowerCase().startsWith("event")) {
                if (breakNeeded(kafCoreferenceSet, kafSaxParser, predicates)) {
                    HashMap<String, KafCoreferenceSet> corefMap = new HashMap<String, KafCoreferenceSet>();
                    int nSubSets = 0;
                    for (int j = 0; j < kafCoreferenceSet.getSetsOfSpans().size(); j++) {
                        ArrayList<CorefTarget> corefTargets = kafCoreferenceSet.getSetsOfSpans().get(j);
                        String lemma = "";
                        for (int k = 0; k < corefTargets.size(); k++) {
                            CorefTarget corefTarget = corefTargets.get(k);
                            KafTerm kafTerm = kafSaxParser.getTerm(corefTarget.getId());
                            if (kafTerm!=null) {
                                lemma += kafTerm.getLemma()+" ";
                            }
                        }
                        lemma = lemma.trim();
                        if (corefMap.containsKey(lemma)) {
                            KafCoreferenceSet kafCoreferenceSetNew = corefMap.get(lemma);
                            kafCoreferenceSetNew.addSetsOfSpans(corefTargets);
                            corefMap.put(lemma, kafCoreferenceSetNew);
                        }
                        else {
                            nSubSets++;
                            KafCoreferenceSet kafCoreferenceSetNew = new KafCoreferenceSet();
                            String corefId = kafCoreferenceSet.getCoid()+"_"+nSubSets;
                            kafCoreferenceSetNew.setCoid(corefId);
                            kafCoreferenceSetNew.setType(kafCoreferenceSet.getType());
                            kafCoreferenceSetNew.addSetsOfSpans(corefTargets);
                            corefMap.put(lemma, kafCoreferenceSetNew);
                        }
                    }
                    Set keySet = corefMap.keySet();
                    Iterator<String> keys = keySet.iterator();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        KafCoreferenceSet kafCoreferenceSetNew = corefMap.get(key);
                        fixedSets.add(kafCoreferenceSetNew);
                    }
                }
                else {
                    fixedSets.add(kafCoreferenceSet);
                }
            }
            else {
                fixedSets.add(kafCoreferenceSet);
            }
        }
        kafSaxParser.kafCorefenceArrayList = fixedSets;
    }


    static Vector<String> readFileToVector(String fileName) {
        Vector<String> lineList = new Vector<String>();
        if (new File(fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    if (inputLine.trim().length()>0) {
                        lineList.add(inputLine.trim());
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineList;
    }

}
