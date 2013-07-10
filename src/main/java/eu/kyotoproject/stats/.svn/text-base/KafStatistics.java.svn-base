package eu.kyotoproject.stats;

import eu.kyotoproject.kaf.KafChunk;
import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafTerm;
import eu.kyotoproject.kaf.KafWordForm;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 11/16/11
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafStatistics {

    static public int getNumberOfSentences (KafSaxParser parser) {
        int nSentences = 0;
        String s = "";
        for (int i = 0; i < parser.kafWordFormList.size(); i++) {
            KafWordForm kafWordForm = parser.kafWordFormList.get(i);
            if (!s.equals(kafWordForm.getSent())) {
                s = kafWordForm.getSent();
                nSentences++;
            }

        }
        return nSentences;
    }

    static public int getNumberOfParagraphs (KafSaxParser parser) {
        int nParagraphs = 0;
        String s = "";
        for (int i = 0; i < parser.kafWordFormList.size(); i++) {
            KafWordForm kafWordForm = parser.kafWordFormList.get(i);
            if (!s.equals(kafWordForm.getPara())) {
                s = kafWordForm.getPara();
                nParagraphs++;
            }

        }
        return nParagraphs;
    }

    static public int getNumberOfPages (KafSaxParser parser) {
        int nPages = 0;
        String s = "";
        for (int i = 0; i < parser.kafWordFormList.size(); i++) {
            KafWordForm kafWordForm = parser.kafWordFormList.get(i);
            if (!s.equals(kafWordForm.getPage())) {
                s = kafWordForm.getPage();
                nPages++;
            }

        }
        return nPages;
    }

    static public HashMap<String, Integer> getLemmaMap (KafSaxParser parser) {
        HashMap<String, Integer> lemmaMap = new HashMap<String, Integer>();
        for (int i = 0; i < parser.kafTermList.size(); i++) {
            KafTerm kafTerm = parser.kafTermList.get(i);
            if (lemmaMap.containsKey(kafTerm.getLemma())) {
                Integer cnt = lemmaMap.get(kafTerm.getLemma());
                cnt++;
                lemmaMap.put(kafTerm.getLemma(), cnt);
            }
            else {
                lemmaMap.put(kafTerm.getLemma(), 1);
            }
        }
        return lemmaMap;
    }

    static public HashMap<String, Integer> getPosMap (KafSaxParser parser) {
        HashMap<String, Integer> posMap = new HashMap<String, Integer>();
        for (int i = 0; i < parser.kafTermList.size(); i++) {
            KafTerm kafTerm = parser.kafTermList.get(i);
            if (posMap.containsKey(kafTerm.getPos())) {
                Integer cnt = posMap.get(kafTerm.getPos());
                cnt++;
                posMap.put(kafTerm.getPos(), cnt);
            }
            else {
                posMap.put(kafTerm.getPos(), 1);
            }
        }
        return posMap;
    }

    static public HashMap<String, Integer> getMainPosMap (KafSaxParser parser) {
        HashMap<String, Integer> posMap = new HashMap<String, Integer>();
        for (int i = 0; i < parser.kafTermList.size(); i++) {
            KafTerm kafTerm = parser.kafTermList.get(i);
            String pos = kafTerm.getPos().substring(0,1);
            if (posMap.containsKey(pos)) {
                Integer cnt = posMap.get(pos);
                cnt++;
                posMap.put(pos, cnt);
            }
            else {
                posMap.put(pos, 1);
            }
        }
        return posMap;
    }

    static public HashMap<String, Integer> getChunkPhaseMap (KafSaxParser parser) {
        HashMap<String, Integer> phraseMap = new HashMap<String, Integer>();
        for (int i = 0; i < parser.kafChunkList.size(); i++) {
            KafChunk kafChunk = parser.kafChunkList.get(i);
            if (phraseMap.containsKey(kafChunk.getPhrase())) {
                Integer cnt = phraseMap.get(kafChunk.getPhrase());
                cnt++;
                phraseMap.put(kafChunk.getPhrase(), cnt);
            }
            else {
                phraseMap.put(kafChunk.getPhrase(), 1);
            }
        }
        return phraseMap;
    }

    static public String getBasicKafStatistics (String kafFilePath) {
        String str = "KAF Statistics\n";
        str += "File\t"+kafFilePath+"\n";
        KafSaxParser parser = new KafSaxParser();
        parser.parseFile(kafFilePath);
        str += "Language\t"+parser.getLanguage()+"\n";
        str +=  parser.getKafMetaData().toTableString();
        str += "\n";
        str += "Nr. of pages\t"+getNumberOfPages(parser)+"\n";
        str += "Nr. of paragraphs\t"+getNumberOfParagraphs(parser)+"\n";
        str += "Nr. of sentences\t"+getNumberOfSentences(parser)+"\n";
        str += "Nr. of WFs\t"+parser.kafWordFormList.size()+"\n";
        str += "Nr. of Terms\t"+parser.kafTermList.size()+"\n";
        str += "Nr. of Phrases\t"+parser.kafChunkList.size()+"\n";
        str += "Nr. of Dependencies\t"+parser.kafDepList.size()+"\n";
        str += "Nr. of Dates\t"+parser.kafDateArrayList.size()+"\n";
        str += "Nr. of Places\t"+parser.kafPlaceArrayList.size()+"\n";
        str += "Nr. of Discourse units\t"+parser.kafDiscourseList.size()+"\n";
        str += "\n";
        str += "Main  POS table\n";
        HashMap<String, Integer> posMap = getMainPosMap(parser);
        Set keySet = posMap.keySet();
        Iterator keys = keySet.iterator();
        TreeSet sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }
        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = posMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";
        }

        str += "POS table\n";
        posMap = getPosMap(parser);
        keySet = posMap.keySet();
        keys = keySet.iterator();
        sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }
        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = posMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";
        }

        str += "Phrase table\n";
        HashMap<String, Integer> phraseMap = getChunkPhaseMap(parser);
        keySet = phraseMap.keySet();
        keys = keySet.iterator();
        sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }
        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = phraseMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";
        }

        str += "Lemma table\n";
        HashMap<String, Integer> lemmaMap = getLemmaMap(parser);
        str += "\tNr. type\t"+lemmaMap.size()+"\n";
        str += "\tNr. term tokens\t"+parser.kafTermList.size()+"\n";
        str += "\tNr. word form tokens\t"+parser.kafWordFormList.size()+"\n";
        str += "\tType/term token ration\t"+parser.kafTermList.size()/lemmaMap.size()+"\n";
        str += "\tType/word token ration\t"+parser.kafWordFormList.size()/lemmaMap.size()+"\n";
        keySet = lemmaMap.keySet();
        keys = keySet.iterator();
        sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }
        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = lemmaMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";

        }

        return str;
    }

    static public void main (String [] args) {
        try {
            String kafFilePath = args[0];
            String kafStatFilePath = kafFilePath+".xls";
            FileOutputStream fos = new FileOutputStream(kafStatFilePath);
            fos.write(getBasicKafStatistics(kafFilePath).getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
