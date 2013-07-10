package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafTerm;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/16/11
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class DummyKafModifier {


    static public void main (String[] args) {
        String kafFile = args[0];
        KafSaxParser parser = new KafSaxParser();
        parser.parseFile(kafFile);
        for (int i = 0; i < parser.kafTermList.size(); i++) {
            KafTerm kafTerm = parser.kafTermList.get(i);
               //// DO SOMETHING TO MODIFY A TERM
        }
        try {
            FileOutputStream fos = new FileOutputStream(kafFile+".my.kaf");
            parser.writeKafToFile(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


}
