package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafWordForm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by piek on 09/07/15.
 */
public class KafFixECB {


    static public void main (String [] args) {
        String pathToFolder = "/Users/piek/Desktop/NWR/ECB+_LREC2014/ECB+NAF";
        ArrayList<File> files = FileProcessor.makeRecursiveFileArrayList(pathToFolder, ".naf");
        KafSaxParser kafSaxParser = new KafSaxParser();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            kafSaxParser.parseFile(file);
            for (int j = 0; j < kafSaxParser.getKafWordFormList().size(); j++) {
                KafWordForm kafWordForm = kafSaxParser.getKafWordFormList().get(j);
                String wid = "w"+kafWordForm.getWid();
                kafWordForm.setWid(wid);
            }
            try {
                OutputStream fos = new FileOutputStream(file+".fix.xml");
                kafSaxParser.writeNafToStream(fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
