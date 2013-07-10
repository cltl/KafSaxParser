package eu.kyotoproject.util;

import eu.kyotoproject.kaf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/13/11
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class NamedEntitiesToTermAttribute {

    static public void main (String [] args) {
        File lF = new File(args[0]);
        File[] theFileList = null;
        if ((lF.canRead()) && lF.isDirectory()) {
                theFileList = lF.listFiles();
        }
        else {
                theFileList = new File[1];
                theFileList[0]=lF;
        }
        if (theFileList!=null) {
            KafSaxParser parser = new KafSaxParser();
            for (int f = 0; f < theFileList.length; f++) {
                File kaf = theFileList[f];
                parser.parseFile(kaf);
                for (int i = 0; i < parser.kafCountryArrayList.size(); i++) {
                    GeoCountryObject geoCountryObject = parser.kafCountryArrayList.get(i);
                    for (int j = 0; j < geoCountryObject.getKafReferences().size(); j++) {
                        KafReference kafReference = geoCountryObject.getKafReferences().get(j);
                        KafTerm term = parser.getTerm(kafReference.getTerm());
                        term.setNetype("country");
                    }
                }
                for (int i = 0; i < parser.kafPlaceArrayList.size(); i++) {
                    GeoPlaceObject geoPlaceObject = parser.kafPlaceArrayList.get(i);
                    for (int j = 0; j < geoPlaceObject.getKafReferences().size(); j++) {
                        KafReference kafReference = geoPlaceObject.getKafReferences().get(j);
                        KafTerm term = parser.getTerm(kafReference.getTerm());
                        term.setNetype("place");
                    }
                }
                for (int i = 0; i < parser.kafDateArrayList.size(); i++) {
                    ISODate isoDate = parser.kafDateArrayList.get(i);
                    for (int j = 0; j < isoDate.getKafReferences().size(); j++) {
                        KafReference kafReference = isoDate.getKafReferences().get(j);
                        KafTerm term = parser.getTerm(kafReference.getTerm());
                        term.setNetype("date");
                    }
                }
                try {
                    String filePath = kaf.getAbsolutePath().substring(0, kaf.getAbsolutePath().indexOf("."));
                    FileOutputStream fos = new FileOutputStream(filePath+".ne-term.kaf");
                    parser.writeKafToFile(fos);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }


}
