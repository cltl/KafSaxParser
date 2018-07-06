package eu.kyotoproject.util;

import eu.kyotoproject.kaf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class NafToCausalRelations {


    static public void main (String[] args) {
        String inputFolder = "/Projects/NewsReader/NWR-desktop/benchmark/ecb/data/ecb_pip.v6/";
        String outputFolder = "/Users/piek/Desktop/nwr-clinks";
        File outFolder = new File(outputFolder);
        if (!outFolder.exists()) outFolder.mkdir();
        KafSaxParser kafSaxParser = new KafSaxParser();
        ArrayList<File> nafFiles = FileProcessor.makeRecursiveFileArrayList(inputFolder, ".xml.newpred.xml");
        for (int i = 0; i < nafFiles.size(); i++) {
            File nafFile = nafFiles.get(i);
            kafSaxParser.parseFile(nafFile);
            if (kafSaxParser.kafClinks.size()>0) {
                String resultFile = outputFolder+"/"+nafFile.getName();
                try {
                    OutputStream fos = new FileOutputStream(resultFile);
                    for (int j = 0; j < kafSaxParser.kafClinks.size(); j++) {
                        KafEventRelation kafEventRelation = kafSaxParser.kafClinks.get(j);
                        KafWordForm causeFrom = getTokenIdFromPredId(kafSaxParser, kafEventRelation.getFrom());
                        KafWordForm causeTo = getTokenIdFromPredId(kafSaxParser, kafEventRelation.getTo());
                        String str = j+"\t"+causeFrom.getWid()+":"+causeFrom.getSent()+":"+causeFrom.getWf()+"\t"+
                                causeTo.getWid()+":"+causeTo.getSent()+":"+causeTo.getWf()+"\tHCPE\n";
                        fos.write(str.getBytes());
                    }
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static KafWordForm getTokenIdFromPredId (KafSaxParser kafSaxParser, String predId) {
        KafWordForm tokenId = null;
        for (int i = 0; i < kafSaxParser.kafEventArrayList.size(); i++) {
            KafEvent kafEvent = kafSaxParser.kafEventArrayList.get(i);
            if (kafEvent.getId().equals(predId)) {
                for (int j = 0; j < kafEvent.getSpanIds().size(); j++) {
                    KafTerm kafTerm = kafSaxParser.getTerm(kafEvent.getSpanIds().get(j));
                    if (kafTerm!=null) {
                        ArrayList<String> tokenIds = kafTerm.getSpans();
                        tokenId = kafSaxParser.getWordForm(tokenIds.get(0)); /// we take the first token ids as we cannot handle multiwords yet
                        break;
                    }
                }
            }
        }
        return tokenId;
    }
}
