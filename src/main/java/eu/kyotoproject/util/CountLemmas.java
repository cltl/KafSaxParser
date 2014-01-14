package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafTerm;
import eu.kyotoproject.kaf.KafWordForm;
import eu.kyotoproject.kaf.TermComponent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/22/11
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CountLemmas {



    static public ArrayList<File> makeFlatFileList(String inputPath, String filter) {
            ArrayList<File> acceptedFileList = new  ArrayList<File>();
            File[] theFileList = null;
            File lF = new File(inputPath);
            if ((lF.canRead()) && lF.isDirectory()) {
                theFileList = lF.listFiles();
                for (int i = 0; i < theFileList.length; i++) {
                    String newFilePath = theFileList[i].getAbsolutePath();
                    if (theFileList[i].isFile()) {
                        if (newFilePath.endsWith(filter)) {
                            acceptedFileList.add(theFileList[i]);
                        }
                    }
                }
            }
            return acceptedFileList;
    }

    static public void main (String [] args) {
        String filePath = args[0];
        KafSaxParser parser = new KafSaxParser();
        HashMap<String, Integer> wordList = new HashMap<String, Integer>();
        ArrayList<File> files = makeFlatFileList(filePath, ".kaf");
        for (int j = 0; j < files.size(); j++) {
            File file = files.get(j);
            System.out.println("file.getName() = " + file.getName());
            parser.parseFile(file);
            for (int i = 0; i < parser.kafWordFormList.size(); i++) {
                KafWordForm kafWordForm = parser.kafWordFormList.get(i);
                //// doe iets
            }
            for (int i = 0; i < parser.kafTermList.size(); i++) {
                KafTerm kafTerm = parser.kafTermList.get(i);
                if (kafTerm.getComponents().size()==0) {
                    if (wordList.containsKey(kafTerm.getLemma())) {
                        Integer cnt = wordList.get(kafTerm.getLemma());
                        cnt++;
                        wordList.put(kafTerm.getLemma(), cnt);
                    }
                    else {
                        wordList.put(kafTerm.getLemma(), 1);
                    }

                }
                else {
                    for (int k = 0; k < kafTerm.getComponents().size(); k++) {
                        TermComponent termComponent = kafTerm.getComponents().get(k);
                        if (wordList.containsKey(termComponent.getLemma())) {
                            Integer cnt = wordList.get(termComponent.getLemma());
                            cnt++;
                            wordList.put(termComponent.getLemma(), cnt);
                        }
                        else {
                            wordList.put(termComponent.getLemma(), 1);
                        }
                    }
                }

            }
        }
    }
}
