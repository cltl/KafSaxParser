package eu.kyotoproject.stats;

import eu.kyotoproject.kaf.*;
import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import eu.kyotoproject.util.FileProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 11/16/11
 * Time: 3:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafStatistics {
    public static class Statistics {
        private int nFile = 0;
        private ArrayList<String> sourceNames;
        private ArrayList<Integer> nLemmasArrayList;
        private ArrayList<Integer> nEntitiesArrayList;
        private ArrayList<Integer> nEventsArrayList;
        private ArrayList<Integer> nSentencesArrayList;
        private ArrayList<Integer> nParagraphsArrayList;
        private ArrayList<Integer> nPagesArrayList;
        private ArrayList<Integer> nWordFormsArrayList;
        private ArrayList<Integer> nWordTermsArrayList;
        private ArrayList<Integer> nChunksArrayList;
        private ArrayList<Integer> nDependenciesArrayList;
        private ArrayList<Integer> nDatesArrayList;
        private ArrayList<Integer> nPlacesArrayList;
        private ArrayList<Integer> nCoreferencesArrayList;
        private HashMap<String, ArrayList<Integer>> entityMap;
        private HashMap<String, ArrayList<Integer>> eventMap;
        private HashMap<String, ArrayList<Integer>> lemmaMap;
        private HashMap<String, ArrayList<Integer>> posMap = new HashMap<String, ArrayList<Integer>>();
        private HashMap<String, ArrayList<Integer>> mainPosMap = new HashMap<String, ArrayList<Integer>>();
        private HashMap<String, ArrayList<Integer>> phraseMap = new HashMap<String, ArrayList<Integer>>();

        public Statistics(int nFiles) {
            this.nFile = nFiles;
            this.sourceNames = new ArrayList<String>();
            this.nLemmasArrayList = initArrayList(nFiles);
            this.nSentencesArrayList = initArrayList(nFiles);
            this.nParagraphsArrayList = initArrayList(nFiles);
            this.nPagesArrayList = initArrayList(nFiles);
            this.nWordFormsArrayList = initArrayList(nFiles);
            this.nWordTermsArrayList = initArrayList(nFiles);
            this.nChunksArrayList = initArrayList(nFile);
            this.nDependenciesArrayList = initArrayList(nFile);
            this.nDatesArrayList = initArrayList(nFile);
            this.nPlacesArrayList = initArrayList(nFile);
            this.nCoreferencesArrayList = initArrayList(nFile);
            this.nEntitiesArrayList = initArrayList(nFile);
            this.nEventsArrayList = initArrayList(nFile);
            this.entityMap = new HashMap<String, ArrayList<Integer>>();
            this.eventMap = new HashMap<String, ArrayList<Integer>>();
            this.lemmaMap = new HashMap<String, ArrayList<Integer>>();
            this.posMap = new HashMap<String, ArrayList<Integer>>();
            this.mainPosMap = new HashMap<String, ArrayList<Integer>>();
            this.phraseMap = new HashMap<String, ArrayList<Integer>>();
        }

        public ArrayList<String> getSourceNames() {
            return sourceNames;
        }

        public void setSourceNames(ArrayList<String> sourceNames) {
            this.sourceNames = sourceNames;
        }

        public void addSourceNames(String sourceName, int n) {
            this.sourceNames.add(n, sourceName);
        }


        public int getnFile() {
            return nFile;
        }

        public void setnFile(int nFile) {
            this.nFile = nFile;
        }

        public ArrayList<Integer> getnLemmasArrayList() {
            return nLemmasArrayList;
        }

        public void setnLemmasArrayList(ArrayList<Integer> nLemmasArrayList) {
            this.nLemmasArrayList = nLemmasArrayList;
        }

        public void addnEntitiesArrayList(Integer nEntityArrayList, int n) {
            this.nEntitiesArrayList.add(n,nEntityArrayList);
        }

        public void addnEventsArrayList(Integer nEventsArrayList, int n) {
            this.nEventsArrayList.add(n,nEventsArrayList);
        }

        public void addnLemmasArrayList(Integer nLemmasArrayList, int n) {
            this.nLemmasArrayList.add(n,nLemmasArrayList);
        }

        public ArrayList<Integer> getnSentencesArrayList() {
            return nSentencesArrayList;
        }

        public void setnSentencesArrayList(ArrayList<Integer> nSentencesArrayList) {
            this.nSentencesArrayList = nSentencesArrayList;
        }

        public void addnSentencesArrayList(Integer nSentencesArrayList, int n) {
            this.nSentencesArrayList.add(n, nSentencesArrayList);
        }

        public ArrayList<Integer> getnParagraphsArrayList() {
            return nParagraphsArrayList;
        }

        public void setnParagraphsArrayList(ArrayList<Integer> nParagraphsArrayList) {
            this.nParagraphsArrayList = nParagraphsArrayList;
        }

        public void addnParagraphsArrayList(Integer nParagraphsArrayList, int n) {
            this.nParagraphsArrayList.add(n, nParagraphsArrayList);
        }

        public ArrayList<Integer> getnPagesArrayList() {
            return nPagesArrayList;
        }

        public void setnPagesArrayList(ArrayList<Integer> nPagesArrayList) {
            this.nPagesArrayList = nPagesArrayList;
        }

        public void addnPagesArrayList(Integer nPagesArrayList, int n) {
            this.nPagesArrayList.add(n, nPagesArrayList);
        }

        public ArrayList<Integer> getnWordFormsArrayList() {
            return nWordFormsArrayList;
        }

        public void setnWordFormsArrayList(ArrayList<Integer> nWordFormsArrayList) {
            this.nWordFormsArrayList = nWordFormsArrayList;
        }

        public void addnWordFormsArrayList(Integer nWordFormsArrayList, int n) {
            this.nWordFormsArrayList.add(n, nWordFormsArrayList);
        }

        public ArrayList<Integer> getnWordTermsArrayList() {
            return nWordTermsArrayList;
        }

        public void setnWordTermsArrayList(ArrayList<Integer> nWordTermsArrayList) {
            this.nWordTermsArrayList = nWordTermsArrayList;
        }

        public void addnWordTermsArrayList(Integer nWordTermsArrayList, int n) {
            this.nWordTermsArrayList.add(n, nWordTermsArrayList);
        }

        public ArrayList<Integer> getnChunksArrayList() {
            return nChunksArrayList;
        }

        public void setnChunksArrayList(ArrayList<Integer> nChunksArrayList) {
            this.nChunksArrayList = nChunksArrayList;
        }

        public void addnChunksArrayList(Integer nChunksArrayList, int n) {
            this.nChunksArrayList.add(n, nChunksArrayList);
        }

        public ArrayList<Integer> getnDependenciesArrayList() {
            return nDependenciesArrayList;
        }

        public void setnDependenciesArrayList(ArrayList<Integer> nDependenciesArrayList) {
            this.nDependenciesArrayList = nDependenciesArrayList;
        }

        public void addnDependenciesArrayList(Integer nDependenciesArrayList, int n) {
            this.nDependenciesArrayList.add(n, nDependenciesArrayList);
        }

        public ArrayList<Integer> getnDatesArrayList() {
            return nDatesArrayList;
        }

        public void setnDatesArrayList(ArrayList<Integer> nDatesArrayList) {
            this.nDatesArrayList = nDatesArrayList;
        }

        public void addnDatesArrayList(Integer nDatesArrayList, int n) {
            this.nDatesArrayList.add(n, nDatesArrayList);
        }

        public ArrayList<Integer> getnPlacesArrayList() {
            return nPlacesArrayList;
        }

        public void setnPlacesArrayList(ArrayList<Integer> nPlacesArrayList) {
            this.nPlacesArrayList = nPlacesArrayList;
        }

        public void addnPlacesArrayList(Integer nPlacesArrayList, int n) {
            this.nPlacesArrayList.add(n, nPlacesArrayList);
        }

        public ArrayList<Integer> getnCoreferencesArrayList() {
            return nCoreferencesArrayList;
        }

        public void setnCoreferencesArrayList(ArrayList<Integer> nCoreferencesArrayList) {
            this.nCoreferencesArrayList = nCoreferencesArrayList;
        }

        public void addnCoreferencesArrayList(Integer nCoreferencesArrayList, int n) {
            this.nCoreferencesArrayList.add(n, nCoreferencesArrayList);
        }

        public ArrayList<Integer> getnEntitiesArrayList() {
            return nEntitiesArrayList;
        }

        public void setnEntitiesArrayList(ArrayList<Integer> nEntitiesArrayList) {
            this.nEntitiesArrayList = nEntitiesArrayList;
        }


        public HashMap<String, ArrayList<Integer>> getLemmaMap() {
            return lemmaMap;
        }

        public void setLemmaMap(HashMap<String, ArrayList<Integer>> lemmaMap) {
            this.lemmaMap = lemmaMap;
        }

        ArrayList<Integer> initArrayList (int nFile) {
            ArrayList<Integer> values = new ArrayList<Integer>();
            for (int i = 0; i < nFile; i++) {
                values.add(0);
            }
            return values;
        }

        public void addLemmaMap(String str, Integer cnt, int n) {
            if (this.lemmaMap.containsKey(str)) {
                ArrayList<Integer> values = this.lemmaMap.get(str);
                values.add(n, cnt);
                this.lemmaMap.put(str, values);
            }
            else {
                ArrayList<Integer> values = initArrayList(nFile);
                values.add(n, cnt);
                this.lemmaMap.put(str, values);
            }
        }


        public void setEntityMap(HashMap<String, ArrayList<Integer>> entityMap) {
            this.entityMap = entityMap;
        }


        public void addEntityMap(String str, Integer cnt, int n) {
            if (this.entityMap.containsKey(str)) {
                ArrayList<Integer> values = this.entityMap.get(str);
                values.add(n, cnt);
                this.entityMap.put(str, values);
            }
            else {
                ArrayList<Integer> values = initArrayList(nFile);
                values.add(n, cnt);
                this.entityMap.put(str, values);
            }
        }

        public HashMap<String, ArrayList<Integer>> getEntityMap() {
            return entityMap;
        }

        public void setEventMap(HashMap<String, ArrayList<Integer>> eventMap) {
            this.eventMap = eventMap;
        }


        public void addEventMap(String str, Integer cnt, int n) {
            if (this.eventMap.containsKey(str)) {
                ArrayList<Integer> values = this.eventMap.get(str);
                values.add(n, cnt);
                this.eventMap.put(str, values);
            }
            else {
                ArrayList<Integer> values = initArrayList(nFile);
                values.add(n, cnt);
                this.eventMap.put(str, values);
            }
        }

        public HashMap<String, ArrayList<Integer>> getEventMap() {
            return eventMap;
        }

        public HashMap<String, ArrayList<Integer>> getPosMap() {
            return posMap;
        }

        public void setPosMap(HashMap<String, ArrayList<Integer>> posMap) {
            this.posMap = posMap;
        }


        public void addPosMap(String str, Integer cnt, int n) {
            if (this.posMap.containsKey(str)) {
                ArrayList<Integer> values = this.posMap.get(str);
                values.add(n, cnt);
                this.posMap.put(str, values);
            }
            else {
                ArrayList<Integer> values = initArrayList(nFile);
                values.add(n, cnt);
                this.posMap.put(str, values);
            }
        }

        public HashMap<String, ArrayList<Integer>> getMainPosMap() {
            return mainPosMap;
        }

        public void setMainPosMap(HashMap<String, ArrayList<Integer>> mainPosMap) {
            this.mainPosMap = mainPosMap;
        }

        public void addMainPosMap(String str, Integer cnt, int n) {
            if (this.mainPosMap.containsKey(str)) {
                ArrayList<Integer> values = this.mainPosMap.get(str);
                values.add(n, cnt);
                this.mainPosMap.put(str, values);
            }
            else {
                ArrayList<Integer> values = initArrayList(nFile);
                values.add(n, cnt);
                this.mainPosMap.put(str, values);
            }
        }

        public HashMap<String, ArrayList<Integer>> getPhraseMap() {
            return phraseMap;
        }

        public void setPhraseMap(HashMap<String, ArrayList<Integer>> phraseMap) {
            this.phraseMap = phraseMap;
        }

        public void addPhraseMap(String str, Integer cnt, int n) {
            if (this.phraseMap.containsKey(str)) {
                ArrayList<Integer> values = this.phraseMap.get(str);
                values.add(n, cnt);
                this.phraseMap.put(str, values);
            }
            else {
                ArrayList<Integer> values = initArrayList(nFile);
                values.add(n,cnt);
                this.phraseMap.put(str, values);
            }
        }

        public void getOutputString (FileOutputStream fos, String folderPath) throws IOException {
            String str = "KAF Statistics\n";
            str += "File\t"+folderPath+"\n";
            str += "\n";
            String fileHeaderString = "\tTotal";
            for (int i = 0; i < sourceNames.size(); i++) {
                String s = sourceNames.get(i);
                fileHeaderString += "\t"+s;
            }

            str += fileHeaderString+"\n";

            str += "Nr. of pages";
            Integer total = 0;
            for (int i = 0; i < this.nPagesArrayList.size(); i++) {
                Integer integer = this.nPagesArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < this.nPagesArrayList.size(); i++) {
                Integer integer = this.nPagesArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";

            total = 0;
            str += "Nr. of paragraphs";
            for (int i = 0; i < this.nParagraphsArrayList.size(); i++) {
                Integer integer = this.nParagraphsArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < this.nParagraphsArrayList.size(); i++) {
                Integer integer = this.nParagraphsArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";

            total = 0;
            str += "Nr. of sentences";
            for (int i = 0; i < this.nSentencesArrayList.size(); i++) {
                Integer integer = this.nSentencesArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < this.nSentencesArrayList.size(); i++) {
                Integer integer = this.nSentencesArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";

            total = 0;
            str += "Nr. types";
            for (int i = 0; i < nLemmasArrayList.size(); i++) {
                Integer integer = nLemmasArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < nLemmasArrayList.size(); i++) {
                Integer integer = nLemmasArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";

            total = 0;
            str += "Nr. term tokens";
            for (int i = 0; i < nWordTermsArrayList.size(); i++) {
                Integer integer = nWordTermsArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < nWordTermsArrayList.size(); i++) {
                Integer integer = nWordTermsArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";

            total = 0;
            str += "Nr. word form tokens";
            for (int i = 0; i < nWordFormsArrayList.size(); i++) {
                Integer integer = nWordFormsArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < nWordFormsArrayList.size(); i++) {
                Integer integer = nWordFormsArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";

            str += "Type/term token ratio\t";
            for (int i = 0; i < nWordTermsArrayList.size(); i++) {
                Integer integer = nWordTermsArrayList.get(i);
                Integer types = nLemmasArrayList.get(i);
                Double fraction = new Double((double)types/(double)integer);
                DecimalFormat df = new DecimalFormat("#.##");
                str += "\t"+df.format(fraction);
            }
            str += "\n";

            str += "Type/word token ratio\t";
            for (int i = 0; i < nWordFormsArrayList.size(); i++) {
                Integer integer = nWordFormsArrayList.get(i);
                Integer types = nLemmasArrayList.get(i);
                Double fraction = new Double((double)types/(double)integer);
                DecimalFormat df = new DecimalFormat("#.##");
                str += "\t"+df.format(fraction);
            }
            str += "\n";

            total = 0;
            str += "Nr. of Phrases";
            for (int i = 0; i < this.nChunksArrayList.size(); i++) {
                Integer integer = this.nChunksArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < this.nChunksArrayList.size(); i++) {
                Integer integer = this.nChunksArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";

            total = 0;
            str += "Nr. of Dependencies";
            for (int i = 0; i < this.nDependenciesArrayList.size(); i++) {
                Integer integer = this.nDependenciesArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < this.nDependenciesArrayList.size(); i++) {
                Integer integer = this.nDependenciesArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";

            total = 0;
            str += "Nr. of Entities";
            for (int i = 0; i < this.nEntitiesArrayList.size(); i++) {
                Integer integer = this.nEntitiesArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < this.nEntitiesArrayList.size(); i++) {
                Integer integer = this.nEntitiesArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";

/*
            str += "Nr. of Properties";
            for (int i = 0; i < this.nProperties.size(); i++) {
                Integer integer = this.nProperties.get(i);
                str += "\t"+integer;
            }
            str += "\n";
*/

            str += "\n";
            fos.write(str.getBytes());

            str = "POS table"+fileHeaderString+"\n";
            Set keySet = this.mainPosMap.keySet();
            Iterator keys = keySet.iterator();
            TreeSet sorter = new TreeSet();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                sorter.add(key);
            }
            keys = sorter.iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                str += key;
                total = 0;
                ArrayList<Integer> cnt = mainPosMap.get(key);
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    total += integer;
                }
                str += "\t"+total;
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    str += "\t"+integer.toString();
                }
                str += "\n";
            }
            fos.write(str.getBytes());

            str = "\n";
            str += "Morphofeatures table"+fileHeaderString+"\n";
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
                str += key;
                total = 0;
                ArrayList<Integer> cnt = posMap.get(key);
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    total += integer;
                }
                str += "\t"+total;
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    str += "\t"+integer.toString();
                }
                str += "\n";
            }
            fos.write(str.getBytes());

            str = "\n";
            str += "Phrase table"+fileHeaderString+"\n";
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
                str += key;
                total = 0;
                ArrayList<Integer> cnt = phraseMap.get(key);
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    total += integer;
                }
                str += "\t"+total;
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    str += "\t"+integer.toString();
                }
                str += "\n";
            }
            fos.write(str.getBytes());

        }

        public void getLemmaString (FileOutputStream fos, String folderPath) throws IOException {
            String str = "KAF Statistics\n";
            str += "File\t"+folderPath+"\n";
            str += "\n";
            String fileHeaderString = "\tTotal freq\tNr. doc";
            for (int i = 0; i < sourceNames.size(); i++) {
                String s = sourceNames.get(i);
                fileHeaderString += "\t"+s;
            }

            str += fileHeaderString+"\n";


            int total = 0;
            int nDoc = 0;
            str += "Nr. of Lemmas\t"+lemmaMap.size()+"\n";

            fos.write(str.getBytes());
            Set keySet = lemmaMap.keySet();
            Iterator keys = keySet.iterator();
            TreeSet sorter = new TreeSet();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                sorter.add(key);
            }
            keys = sorter.iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                str = key;
                total = 0;
                ArrayList<Integer> cnt = lemmaMap.get(key);
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    total += integer;
                    if (integer>0) {
                        nDoc++;
                    }
                }
                str += "\t"+total+"\t"+nDoc;
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    str += "\t"+integer.toString();
                }
                str += "\n";
                fos.write(str.getBytes());
            }
            fos.write(str.getBytes());
        }

       public void getEntityString (FileOutputStream fos, String folderPath) throws IOException {
            String str = "KAF Statistics\n";
            str += "File\t"+folderPath+"\n";
            str += "\n";
            String fileHeaderString = "\tTotal";
            for (int i = 0; i < sourceNames.size(); i++) {
                String s = sourceNames.get(i);
                fileHeaderString += "\t"+s;
            }

            str += fileHeaderString+"\n";


            int total = 0;
            str += "Nr. of Entities";
            for (int i = 0; i < this.nEntitiesArrayList.size(); i++) {
                Integer integer = this.nEntitiesArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < this.nEntitiesArrayList.size(); i++) {
                Integer integer = this.nEntitiesArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";



            str = "\n";
            str += "Entity table"+fileHeaderString+"\n";
            fos.write(str.getBytes());
            Set keySet = entityMap.keySet();
            Iterator keys = keySet.iterator();
            TreeSet sorter = new TreeSet();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                sorter.add(key);
            }
            keys = sorter.iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                str = key;
                total = 0;
                ArrayList<Integer> cnt = entityMap.get(key);
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    total += integer;
                }
                str += "\t"+total;
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    str += "\t"+integer.toString();
                }
                str += "\n";
                fos.write(str.getBytes());
            }
            fos.write(str.getBytes());
        }

        public void getEventString (FileOutputStream fos, String folderPath) throws IOException {
            String str = "KAF Statistics\n";
            str += "File\t"+folderPath+"\n";
            str += "\n";
            String fileHeaderString = "\tTotal";
            for (int i = 0; i < sourceNames.size(); i++) {
                String s = sourceNames.get(i);
                fileHeaderString += "\t"+s;
            }

            str += fileHeaderString+"\n";


            int total = 0;
            str += "Nr. of Events";
            for (int i = 0; i < this.nEventsArrayList.size(); i++) {
                Integer integer = this.nEventsArrayList.get(i);
                total += integer;
            }
            str += "\t"+total;
            for (int i = 0; i < this.nEventsArrayList.size(); i++) {
                Integer integer = this.nEventsArrayList.get(i);
                str += "\t"+integer;
            }
            str += "\n";



            str = "\n";
            str += "Entity table"+fileHeaderString+"\n";
            fos.write(str.getBytes());
            Set keySet = eventMap.keySet();
            Iterator keys = keySet.iterator();
            TreeSet sorter = new TreeSet();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                sorter.add(key);
            }
            keys = sorter.iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                str = key;
                total = 0;
                ArrayList<Integer> cnt = eventMap.get(key);
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    total += integer;
                }
                str += "\t"+total;
                for (int i = 0; i < cnt.size(); i++) {
                    Integer integer = cnt.get(i);
                    str += "\t"+integer.toString();
                }
                str += "\n";
                fos.write(str.getBytes());
            }
            fos.write(str.getBytes());
        }
    }


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

    static public HashMap<String, Integer> getEntityMap (KafSaxParser parser) {
        HashMap<String, Integer> entityMap = new HashMap<String, Integer>();
        for (int i = 0; i < parser.kafEntityArrayList.size(); i++) {
            KafEntity kafEntity = parser.kafEntityArrayList.get(i);
            kafEntity.setTokenStrings(parser);
            String phrase = kafEntity.getTokenStringArray().get(0);
            if (entityMap.containsKey(phrase)) {
                Integer cnt = entityMap.get(phrase);
                cnt++;
                entityMap.put(phrase, cnt);
            }
            else {
                entityMap.put(phrase, 1);
            }
        }
        return entityMap;
    }


    static public HashMap<String, Integer> getEventMap (KafSaxParser parser) {
        HashMap<String, Integer> eventMap = new HashMap<String, Integer>();
        for (int i = 0; i < parser.kafEventArrayList.size(); i++) {
            KafEvent kafEvent = parser.kafEventArrayList.get(i);
            String phrase = AddTokensAsCommentsToSpans.getTokenStringFromTermIds(parser, kafEvent.getSpanIds());
            if (eventMap.containsKey(phrase)) {
                Integer cnt = eventMap.get(phrase);
                cnt++;
                eventMap.put(phrase, cnt);
            }
            else {
                eventMap.put(phrase, 1);
            }
        }
        return eventMap;
    }

    static public HashMap<String, Integer> getPosMap (KafSaxParser parser) {
        HashMap<String, Integer> posMap = new HashMap<String, Integer>();
        for (int i = 0; i < parser.kafTermList.size(); i++) {
            KafTerm kafTerm = parser.kafTermList.get(i);
            if (posMap.containsKey(kafTerm.getMorphofeat())) {
                Integer cnt = posMap.get(kafTerm.getMorphofeat());
                cnt++;
                posMap.put(kafTerm.getMorphofeat(), cnt);
            }
            else {
                posMap.put(kafTerm.getMorphofeat(), 1);
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

    static public HashMap<String, Integer> getChunkPhraseMap(KafSaxParser parser) {
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

    static void updateStatistics (Statistics stats, String kafFilePath, KafSaxParser parser, int n) {
        String sourceName = new File (kafFilePath).getName();
        stats.addSourceNames(sourceName, n);
        stats.addnPagesArrayList(getNumberOfPages(parser), n);
        stats.addnParagraphsArrayList(getNumberOfParagraphs(parser), n);
        stats.addnSentencesArrayList(getNumberOfSentences(parser), n);
        stats.addnChunksArrayList(parser.kafChunkList.size(), n);
        stats.addnDependenciesArrayList(parser.kafDepList.size(), n);
        stats.addnEntitiesArrayList(parser.kafEntityArrayList.size(), n);
        stats.addnEventsArrayList(parser.kafEventArrayList.size(), n);
        stats.addnWordFormsArrayList(parser.kafWordFormList.size(),n);
        stats.addnWordTermsArrayList(parser.kafTermList.size(),n);
        stats.addnDatesArrayList(parser.kafDateArrayList.size(),n);
        stats.addnPlacesArrayList(parser.kafPlaceArrayList.size(),n);

        HashMap<String, Integer> posMap = getMainPosMap(parser);
        Set keySet = posMap.keySet();
        Iterator keys = keySet.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = posMap.get(key);
            stats.addMainPosMap(key, cnt, n);
        }

        posMap = getPosMap(parser);
        keySet = posMap.keySet();
        keys = keySet.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = posMap.get(key);
            stats.addPosMap(key, cnt, n);
        }

        HashMap<String, Integer> phraseMap = getChunkPhraseMap(parser);
        keySet = phraseMap.keySet();
        keys = keySet.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = phraseMap.get(key);
            stats.addPhraseMap(key, cnt, n);
        }

        HashMap<String, Integer> entityMap = getEntityMap(parser);
        keySet = entityMap.keySet();
        keys = keySet.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = entityMap.get(key);
            stats.addEntityMap(key, cnt, n);
        }

        stats.addnEntitiesArrayList(entityMap.size(), n);

        HashMap<String, Integer> eventMap = getEventMap(parser);
        keySet = eventMap.keySet();
        keys = keySet.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = eventMap.get(key);
            stats.addEventMap(key, cnt, n);
        }

        stats.addnEventsArrayList(eventMap.size(), n);

        HashMap<String, Integer> lemmaMap = getLemmaMap(parser);
        keySet = lemmaMap.keySet();
        keys = keySet.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = lemmaMap.get(key);
            stats.addLemmaMap(key, cnt, n);
        }

        stats.addnLemmasArrayList(lemmaMap.size(), n);

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
        str += "POS table\n";
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

        str += "Morphofeature table\n";
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
        HashMap<String, Integer> phraseMap = getChunkPhraseMap(parser);
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

        str += "Entity table\n";
        HashMap<String, Integer> entityMap = getEntityMap(parser);
        str += "\tNr. type\t"+entityMap.size()+"\n";
        keySet = entityMap.keySet();
        keys = keySet.iterator();
        sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }
        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = entityMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";

        }

        str += "Event table\n";
        HashMap<String, Integer> eventMap = getEventMap(parser);
        str += "\tNr. type\t"+eventMap.size()+"\n";
        keySet = eventMap.keySet();
        keys = keySet.iterator();
        sorter = new TreeSet();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            sorter.add(key);
        }
        keys = sorter.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = eventMap.get(key);
            str += "\t"+key+"\t"+cnt.toString()+"\n";

        }


        str += "Lemma table\n";
        HashMap<String, Integer> lemmaMap = getLemmaMap(parser);
        str += "\tNr. type\t"+lemmaMap.size()+"\n";
        str += "\tNr. term tokens\t"+parser.kafTermList.size()+"\n";
        str += "\tNr. word form tokens\t"+parser.kafWordFormList.size()+"\n";
        str += "\tType/term token ratio\t"+parser.kafTermList.size()/lemmaMap.size()+"\n";
        str += "\tType/word token ratio\t"+parser.kafWordFormList.size()/lemmaMap.size()+"\n";
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
           // String kafFilePath = "/Users/piek/Desktop/NWR-DATA/techcrunch/1_3000";
           // String kafFilePath = "/Users/piek/Desktop/Thomese/Thomese_book_opener_nwr_srl";
           // String kafFilePath = "/Users/piek/Desktop/Thomese/grillroom";
            String kafFilePath = "/Users/piek/Desktop/CLTL-onderwijs/TMC/week-5-new/slides/data";

             String extension = ".kaf";
           // String extension = ".naf";
           // String kafFilePath = "";
            //String extension = "";
            for (int i = 0; i < args.length; i++) {
                 String arg = args[i];
                 if (arg.equalsIgnoreCase("--input") && args.length>(i+1)) {
                     kafFilePath = args[i+1];
                 }
                else if (arg.equalsIgnoreCase("--extension") && args.length>(i+1)) {
                     extension = args[i+1];
                 }

            }
            File file = new File(kafFilePath);
            if (file.isDirectory()) {
                String [] files= FileProcessor.makeRecursiveFileList(kafFilePath, extension);
                System.out.println("files.length = " + files.length);
                Statistics stats = new Statistics(files.length);

                KafSaxParser parser = new KafSaxParser();
                for (int i = 0; i < files.length; i++) {
                    String filePath = files[i];
                   // System.out.println("filePath = " + filePath);
                    //String fileName = new File (filePath).getName();
                    parser.parseFile(filePath);
                    updateStatistics(stats, filePath, parser, i);
                }
                /// printout overview data for all files
                String kafStatFilePath = kafFilePath+"/stats.xls";
                FileOutputStream fos = new FileOutputStream(kafStatFilePath);
                stats.getOutputString(fos, kafFilePath);
                fos.close();
                String lemmaMapFile = kafFilePath+"/lemma.stats.xls";
                fos = new FileOutputStream(lemmaMapFile);
                stats.getLemmaString(fos, lemmaMapFile);
                fos.close();
                String entityMapFile = kafFilePath+"/entity.stats.xls";
                fos = new FileOutputStream(entityMapFile);
                stats.getEntityString(fos, entityMapFile);
                fos.close();
                String eventMapFile = kafFilePath+"/event.stats.xls";
                fos = new FileOutputStream(eventMapFile);
                stats.getEventString(fos, eventMapFile);
                fos.close();
            }
            else {
                String kafStatFilePath = kafFilePath+".xls";
                FileOutputStream fos = new FileOutputStream(kafStatFilePath);
                fos.write(getBasicKafStatistics(kafFilePath).getBytes());
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
