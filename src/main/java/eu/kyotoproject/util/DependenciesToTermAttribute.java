package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafDep;
import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafTerm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/13/11
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class DependenciesToTermAttribute {

    static public void main (String [] args) {
        String kaf = args[0];
        File kafFile = new File(kaf);
        if (kafFile.isDirectory()) {
            ArrayList<File> files = FileProcessor.makeFlatFileArrayList(kaf);
            for (int f = 0; f < files.size(); f++) {
                File file = files.get(f);
                KafSaxParser parser = new KafSaxParser();
                parser.parseFile(file.getAbsolutePath());
                for (int i = 0; i < parser.kafDepList.size(); i++) {
                    KafDep kafDep = parser.kafDepList.get(i);
                    String tidF = kafDep.getFrom();
                    String tidT = kafDep.getTo();
                    String function = kafDep.getRfunc().toLowerCase();
                    if ((function.startsWith("obj")) ||
                        (function.startsWith("dobj")) ||
                            (function.startsWith("pobj"))||
                         (function.startsWith("nsub"))||
                         (function.startsWith("sub"))
                        ) {
                        KafTerm term = parser.getTerm(tidF);
                        if (term!=null) {
                            term.setModifier(tidT);
                            term.setDep("from."+function);
                        }
                        term = parser.getTerm(tidT);
                        if (term!=null) {
                            term.setModifier(tidF);
                            term.setDep("to."+function);
                        }
                    }

                }
                try {
                    FileOutputStream fos = new FileOutputStream(file.getAbsolutePath()+".dep.kaf");
                    parser.writeKafToFile(fos);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        else {
            KafSaxParser parser = new KafSaxParser();
            parser.parseFile(kafFile.getAbsolutePath());
            for (int i = 0; i < parser.kafDepList.size(); i++) {
                KafDep kafDep = parser.kafDepList.get(i);
                String tidF = kafDep.getFrom();
                String tidT = kafDep.getTo();
                String function = kafDep.getRfunc().toLowerCase();
                if ((function.startsWith("obj")) ||
                        (function.startsWith("dobj")) ||
                        (function.startsWith("pobj"))||
                        (function.startsWith("nsub"))||
                        (function.startsWith("sub"))
                        ) {
                    KafTerm term = parser.getTerm(tidF);
                    if (term!=null) {
                        term.setModifier(tidT);
                        term.setDep("from."+function);
                    }
                    term = parser.getTerm(tidT);
                    if (term!=null) {
                        term.setDep("to."+function);
                        term.setModifier(tidF);
                        //term.setModifier(tidF);
                    }
                }

            }
            parser.writeKafToStream(System.out);
        }
    }


}
