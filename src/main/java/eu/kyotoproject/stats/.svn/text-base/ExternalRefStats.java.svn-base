package eu.kyotoproject.stats;

import eu.kyotoproject.kaf.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 2/23/12
 * Time: 9:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExternalRefStats {

  /*
  <externalRef confidence="0.0767154" refType="" reference="eng-30-11425088-n" resource="wneng_domain" status="">
                    <externalRef confidence="1.0" reference="eng-30-11419404-n"  reftype="baseConcept" resource="wn30g"/>
                    <externalRef confidence="1.0" reference="Kyoto#physical_phenomenon-eng-3.0-11419404-n" reftype="sc_subClassOf" resource="ontology">
                        <externalRef reftype="SubClassOf" reference="Kyoto#physical_phenomenon-eng-3.0-11419404-n" status="implied"/>
                        <externalRef reftype="SubClassOf" reference="ExtendedDnS.owl#physical-phenomenon"/>

   */


    static void getAttributesFromNestedReferences (HashMap<String, Integer> externalRefMap, KafSense kafSense) {
        //   System.out.println("kafSense.getRefType() = " + kafSense.getRefType());
        String key = "reftype\t"+kafSense.getRefType();
        if (externalRefMap.containsKey(key)) {
            Integer cnt = externalRefMap.get(key);
            cnt++;
            externalRefMap.put(key, cnt);
        }
        else {
            externalRefMap.put(key, 1);
        }
        key = "reference\t"+kafSense.getSensecode();
        if (externalRefMap.containsKey(key)) {
            Integer cnt = externalRefMap.get(key);
            cnt++;
            externalRefMap.put(key, cnt);
        }
        else {
            externalRefMap.put(key, 1);
        }
        key = "resource\t"+kafSense.getResource();
        if (externalRefMap.containsKey(key)) {
            Integer cnt = externalRefMap.get(key);
            cnt++;
            externalRefMap.put(key, cnt);
        }
        else {
            externalRefMap.put(key, 1);
        }
        key = "status\t"+kafSense.getStatus();
        if (externalRefMap.containsKey(key)) {
            Integer cnt = externalRefMap.get(key);
            cnt++;
            externalRefMap.put(key, cnt);
        }
        else {
            externalRefMap.put(key, 1);
        }
        for (int i = 0; i < kafSense.getChildren().size(); i++) {
            KafSense sense = kafSense.getChildren().get(i);
            getAttributesFromNestedReferences(externalRefMap, sense);
        }
    }
    static void getAttributesFromReferences (HashMap<String, Integer> externalRefMap, KafSense kafSense) {
        //   System.out.println("kafSense.getRefType() = " + kafSense.getRefType());
        String key = "reftype\t"+kafSense.getRefType();
        if (externalRefMap.containsKey(key)) {
            Integer cnt = externalRefMap.get(key);
            cnt++;
            externalRefMap.put(key, cnt);
        }
        else {
            externalRefMap.put(key, 1);
        }
        key = "reference\t"+kafSense.getSensecode();
        if (externalRefMap.containsKey(key)) {
            Integer cnt = externalRefMap.get(key);
            cnt++;
            externalRefMap.put(key, cnt);
        }
        else {
            externalRefMap.put(key, 1);
        }
        key = "resource\t"+kafSense.getResource();
        if (externalRefMap.containsKey(key)) {
            Integer cnt = externalRefMap.get(key);
            cnt++;
            externalRefMap.put(key, cnt);
        }
        else {
            externalRefMap.put(key, 1);
        }
        key = "status\t"+kafSense.getStatus();
        if (externalRefMap.containsKey(key)) {
            Integer cnt = externalRefMap.get(key);
            cnt++;
            externalRefMap.put(key, cnt);
        }
        else {
            externalRefMap.put(key, 1);
        }
    }


    
    static public String getOntologyStatistics (KafSaxParser parser, String pos) {
        int nSenseTokens = 0;
        int nOntologyTokens = 0;
        int nOntologyImpliedTokens = 0;
        int nBaseConceptTokens = 0;
        int nTerms = 0;

        String str = "KAF Statistics\n";
        str += "Language\t"+parser.getLanguage()+"\n";
        str +=  parser.getKafMetaData().toTableString();
        str += "\n";

        HashMap<String, Integer> ontologyMap = new HashMap<String, Integer>();
        HashMap<String, Integer> ontologyImpliedMap = new HashMap<String, Integer>();
        HashMap<String, Integer> senseMap = new HashMap<String, Integer>();
        HashMap<String, Integer> baseConceptMap = new HashMap<String, Integer>();
        for (int i = 0; i < parser.kafTermList.size(); i++) {
            KafTerm kafTerm = parser.kafTermList.get(i);
            if (!pos.isEmpty()) {
                if (kafTerm.getPos().startsWith(pos)) {
                    nTerms++;
                }
                else {
                    continue;
                }
            }
            else {
                nTerms++;
            }
            for (int j = 0; j < kafTerm.getSenseTags().size(); j++) {
                KafSense kafSense = kafTerm.getSenseTags().get(j);
                getAttributesFromReferences(senseMap, kafSense);
                nSenseTokens++;
                for (int k = 0; k < kafSense.getChildren().size(); k++) {
                    KafSense senseChild = kafSense.getChildren().get(k);
                    if (senseChild.getRefType().equalsIgnoreCase("baseConcept")) {
                       getAttributesFromReferences(baseConceptMap, senseChild);
                        nBaseConceptTokens++;
                    }
                    else {
                        if (senseChild.getStatus().equalsIgnoreCase("implied")) {
                            nOntologyImpliedTokens++;
                            getAttributesFromReferences(ontologyImpliedMap, senseChild);
                        }
                        else {
                            getAttributesFromReferences(ontologyMap, senseChild);
                            nOntologyTokens++;
                        }
/*
                        for (int l = 0; l < senseChild.getChildren().size(); l++) {
                            KafSense impliedChild = senseChild.getChildren().get(l);
                            nOntologyImpliedTokens++;
                            getAttributesFromNestedReferences(ontologyImpliedMap, impliedChild);
                        }
*/
                    }
                }
            }
        }

        str += "Nr. of Terms\t"+nTerms+"\n";
        str += "Sense tokens:\t"+nSenseTokens+"\n";
        str += "Average polysemy:\t"+(nSenseTokens/nTerms)+"\n";
        str += "Sense types:\t"+senseMap.size()+"\n";
        str += "Base concept tokens:\t"+nBaseConceptTokens+"\n";
        str += "Base concept types:\t"+baseConceptMap.size()+"\n";
        str += "Ontology tokens\t"+nOntologyTokens+"\n";
        str += "Ontology types\t"+ontologyMap.size()+"\n";
        str += "Implied Ontology tokens:\t"+nOntologyImpliedTokens+"\n";
        str += "Implied Ontologye types:\t"+ontologyImpliedMap.size()+"\n";
        str += "\n";
        str += "Sense map\n";
        Set keySet = senseMap.keySet();
        Iterator keys = keySet.iterator();
        TreeSet sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }

        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = senseMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";
        }

        str += "\n";
        str += "Base Concept map\n";
        keySet = baseConceptMap.keySet();
        keys = keySet.iterator();
        sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }

        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = baseConceptMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";
        }

        str += "\n";
        str += "Ontology map\n";
        keySet = ontologyMap.keySet();
        keys = keySet.iterator();
        sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }

        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = ontologyMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";
        }

        str += "\n";
        str += "Implied Ontology map\n";
        keySet = ontologyImpliedMap.keySet();
        keys = keySet.iterator();
        sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }

        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = ontologyImpliedMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";
        }

        return str;
    }

    static public void main (String [] args) {
        try {
            String kafFilePath = args[0];
            String kafStatFilePath = kafFilePath+"."+"xls";
            String pos = "";
            if (args.length==2) {
                pos = args[1];
                kafStatFilePath = kafFilePath+"."+pos+".xls";
            }
            FileOutputStream fos = new FileOutputStream(kafStatFilePath);
            KafSaxParser parser = new KafSaxParser();
            parser.parseFile(kafFilePath);
            String str = getOntologyStatistics(parser, pos);
            fos.write(str.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
