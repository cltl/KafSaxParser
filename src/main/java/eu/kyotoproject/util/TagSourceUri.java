package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by piek on 1/23/15.
 */
public class TagSourceUri {


    static public void main (String [] args) {
        String pathToFolder = args[0];
        String tag = args[1];
        String filter = args[2];
        String fileTag = args[3];
        /*pathToFolder = "/Users/piek/Desktop/NWR/Cross-lingual/corpus_NAF_output_141214-lemma-en-coref";
        tag = ".en";
        filter = ".naf";
        fileTag = ".en.coref";*/
        KafSaxParser kafSaxParser = new KafSaxParser();
        ArrayList<File> files = FileProcessor.makeRecursiveFileArrayList(pathToFolder, filter);
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            kafSaxParser.parseFile(file);
            String url = kafSaxParser.getKafMetaData().getUrl()+tag;
            kafSaxParser.getKafMetaData().setUrl(url);
            try {
                String filePath = file.getAbsolutePath()+fileTag;
               /* int idx = filePath.lastIndexOf(".naf.");
                if (idx>-1) {
                    filePath = filePath.substring(0, idx+4)+fileTag;
                }*/
                OutputStream fos = new FileOutputStream(filePath);
                kafSaxParser.writeNafToStream(fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
