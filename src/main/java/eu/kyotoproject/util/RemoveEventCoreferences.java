package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafCoreferenceSet;
import eu.kyotoproject.kaf.KafSaxParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by piek on 03/06/15.
 */
public class RemoveEventCoreferences {

    static public void main (String[] args) {
        // String pathToFile = args[0];
        String pathToFile = "/Users/piek/Desktop/NWR/NWR-DATA/cars-2/test/47KP-XF40-00D1-T37Y.xml";
        KafSaxParser kafSaxParser = new KafSaxParser();
        kafSaxParser.parseFile(pathToFile);
        removeEventCoreferenceSets(kafSaxParser);
        try {
            OutputStream fos = new FileOutputStream(pathToFile+".fix");
            kafSaxParser.writeNafToStream(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void removeEventCoreferenceSets (KafSaxParser kafSaxParser) {
        ArrayList<KafCoreferenceSet> fixedSets = new ArrayList<KafCoreferenceSet>();
        for (int i = 0; i < kafSaxParser.kafCorefenceArrayList.size(); i++) {
            KafCoreferenceSet kafCoreferenceSet = kafSaxParser.kafCorefenceArrayList.get(i);
            if (!kafCoreferenceSet.getType().toLowerCase().startsWith("event")) {
                fixedSets.add(kafCoreferenceSet);
            }
        }
        kafSaxParser.kafCorefenceArrayList = fixedSets;
    }
}
