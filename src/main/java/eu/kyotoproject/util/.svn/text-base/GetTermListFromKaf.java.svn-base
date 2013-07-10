package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafTerm;
import eu.kyotoproject.kaf.KafTermSaxParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 17-dec-2008
 * Time: 14:38:37
 * To change this template use File | Settings | File Templates.
 * This file is part of KafSaxParser.

    KafSaxParser is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    KafSaxParser is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with KafSaxParser.  If not, see <http://www.gnu.org/licenses/>.
 */
public class GetTermListFromKaf {

    static public class StatObj {
        int freq;
        int doc;
        String lemma;
        String synsetId;

        public StatObj() {
            this.freq = 0;
            this.doc = 0;
            this.lemma = "";
            this.synsetId = "";
        }

        public int getFreq() {
            return freq;
        }

        public void setFreq(int freq) {
            this.freq = freq;
        }

        public void addFreq(int freq) {
            this.freq += freq;
        }

        public int getDoc() {
            return doc;
        }

        public void setDoc(int doc) {
            this.doc = doc;
        }

        public void addDoc(int doc) {
            this.doc += doc;
        }

        public String getLemma() {
            return lemma;
        }

        public void setLemma(String lemma) {
            this.lemma = lemma;
        }

        public String getSynsetId() {
            return synsetId;
        }

        public void setSynsetId(String synsetId) {
            this.synsetId = synsetId;
        }
    }

    static HashMap<String, StatObj> dataMap;
    static HashMap<String, StatObj> docMap;


    static public void main (String [] args) {
       try{ KafTermSaxParser parser = new KafTermSaxParser();
            dataMap = new HashMap<String, StatObj>();
            String databasePath = args[0];
            String pos = args[1];
            String outputId = args[2];
            String extension = args[3];
            String outFile = databasePath+"/"+args[1]+"_kafterm_"+outputId+".out";
            FileOutputStream fos = new FileOutputStream(outFile);
            String [] docFolders = FileProcessor.makeFlatDirList(databasePath);
            for (int i = 0; i < docFolders.length; i++) {
                String docFolder = docFolders[i];
                docMap = new HashMap<String, StatObj>();
                String [] kafFiles = FileProcessor.makeFlatFileList(docFolder, extension);
                for (int j = 0; j < kafFiles.length; j++) {
                    String kafFile = kafFiles[j];
                    if (!(new File(kafFile).getName().startsWith("out"))) {
                    //System.out.println("kafFile = " + kafFile);
                        parser = new KafTermSaxParser();
                        parser.parseFilePos(kafFile, pos);
                        ArrayList<KafTerm> terms = parser.kafTermList;
                        for (int k = 0; k < terms.size(); k++) {
                            KafTerm kafTerm = (KafTerm) terms.get(k);
                            if (docMap.containsKey(kafTerm.getLemma())) {
                                StatObj obj = (StatObj) docMap.get(kafTerm.getLemma());
                                obj.addFreq(1);
                                docMap.put(kafTerm.getLemma(), obj);
                            }
                            else {
                                StatObj obj = new StatObj();
                                obj.addFreq(1);
                                obj.addDoc(1);
                                obj.setLemma(kafTerm.getLemma());
                                obj.setSynsetId(kafTerm.getFirstSense());
                                docMap.put(kafTerm.getLemma(), obj);
                            }
                        }

                        for (String key : docMap.keySet()) {
                            if (dataMap.containsKey(key)) {
                                StatObj obj = (StatObj) dataMap.get(key);
                                StatObj docObj = (StatObj) docMap.get(key);
                                obj.addDoc(1);
                                obj.addFreq(docObj.getFreq());
                                dataMap.put(key, obj);
                            }
                            else {
                                StatObj obj = (StatObj) docMap.get(key);
                                dataMap.put(key, obj);
                            }
                        }
                        System.out.println("dataMap = " + dataMap.size());
                    }
                }
            }

            for (String key : dataMap.keySet()) {
                StatObj obj = (StatObj) dataMap.get(key);
                String str = obj.getLemma()+"\t"+obj.getSynsetId()+"\t"+obj.getFreq()+"\t"+obj.getDoc()+"\n";
                fos.write((str).getBytes());
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
