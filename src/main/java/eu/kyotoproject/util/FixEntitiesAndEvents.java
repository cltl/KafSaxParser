package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by piek on 20/05/16.
 */
public class FixEntitiesAndEvents {

    static public void main (String[] args) {
        KafSaxParser kafSaxParser = new KafSaxParser();
        String pathToFile = "";
        String extension = "";
        String eventPath = "";
        Vector<String> predicates = null;

        pathToFile = "/Users/piek/Desktop/NWR-INC/dasym/test1/test.naf";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equalsIgnoreCase("--input") && args.length>(i+1)) {
                pathToFile = args[i+1];
            }
            else if (arg.equalsIgnoreCase("--extension") && args.length>(i+1)) {
                extension = args[i+1];
            }
            else if (arg.equalsIgnoreCase("--events") && args.length>(i+1)) {
                eventPath = args[i+1];
                predicates = FixEventCoreferences.readFileToVector(eventPath);
            }
        }
        if (pathToFile.equalsIgnoreCase("stream")) {
            kafSaxParser.parseFile(System.in);
            fix(kafSaxParser,predicates);
            kafSaxParser.writeNafToStream(System.out);
        }
        else {
            File file = new File(pathToFile);
            if (file.isDirectory()) {
                ArrayList<File> files = FileProcessor.makeRecursiveFileArrayList(pathToFile, extension);
                for (int i = 0; i < files.size(); i++) {
                    File nextFile = files.get(i);
                    kafSaxParser.parseFile(nextFile);
                    fix(kafSaxParser, predicates);
                    try {
                        OutputStream fos = new FileOutputStream(nextFile);
                        kafSaxParser.writeNafToStream(fos);
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                kafSaxParser.parseFile(file);
                fix(kafSaxParser, predicates);
                try {
                    OutputStream fos = new FileOutputStream(file);
                    kafSaxParser.writeNafToStream(fos);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void fix (KafSaxParser kafSaxParser, Vector events) {
        FixEntities.fix(kafSaxParser);
        if (events!=null) {
            FixEventCoreferences.fixEventCoreferenceSets(kafSaxParser, events);
        }
    }
}
