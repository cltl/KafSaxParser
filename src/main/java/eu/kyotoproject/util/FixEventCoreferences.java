package eu.kyotoproject.util;

import eu.kyotoproject.kaf.CorefTarget;
import eu.kyotoproject.kaf.KafCoreferenceSet;
import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafTerm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by piek on 11/10/14.
 */
public class FixEventCoreferences {


    static public void main (String[] args) {
       // String pathToFile = args[0];
        String pathToFile = "/Users/piek/Desktop/NWR/NWR-DATA/cars-2/test/47KP-XF40-00D1-T37Y.xml";
        KafSaxParser kafSaxParser = new KafSaxParser();
        kafSaxParser.parseFile(pathToFile);
        fixEventCoreferenceSets(kafSaxParser);
        try {
            OutputStream fos = new FileOutputStream(pathToFile+".fix");
            kafSaxParser.writeNafToStream(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void fixEventCoreferenceSets (KafSaxParser kafSaxParser) {
        ArrayList<KafCoreferenceSet> fixedSets = new ArrayList<KafCoreferenceSet>();
        for (int i = 0; i < kafSaxParser.kafCorefenceArrayList.size(); i++) {
            KafCoreferenceSet kafCoreferenceSet = kafSaxParser.kafCorefenceArrayList.get(i);
            if (kafCoreferenceSet.getType().toLowerCase().startsWith("event")) {
                if (kafCoreferenceSet.getExternalReferences().size()>4) {
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
}
