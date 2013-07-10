package eu.kyotoproject.conversion;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafSense;
import eu.kyotoproject.kaf.KafTerm;
import eu.kyotoproject.kaf.KafWordForm;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 4/23/12
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class kafTerm2LineFormat {



    static public void main (String [] args) {
        try {
            String pathToKafFile = args[0];
            KafSaxParser kafSaxParser = new KafSaxParser();
            kafSaxParser.parseFile(pathToKafFile);
            FileOutputStream fos = new FileOutputStream(pathToKafFile+".txt");
            String str = "";
            for (int i = 0; i < kafSaxParser.getKafWordFormList().size(); i++) {
                KafWordForm kafWordForm =  kafSaxParser.getKafWordFormList().get(i);
                str = kafWordForm.getWf()+"/";
                if (kafSaxParser.WordFormToTerm.containsKey(kafWordForm.getWid())) {
                    String kafTermId = kafSaxParser.WordFormToTerm.get(kafWordForm.getWid());
                    KafTerm kafTerm = kafSaxParser.getTerm(kafTermId);
                    str += kafTerm.getLemma()+"/"+kafTerm.getPos()+"/"+kafTerm.getPolarity()+"/";
                    for (int j = 0; j < kafTerm.getSenseTags().size(); j++) {
                        KafSense kafSense = kafTerm.getSenseTags().get(j);
                        if (kafSense.getResource().equalsIgnoreCase("WordnetDomain")) {
                            str += ":"+kafSense.getSensecode();
                        }
                        else {
                            if (j>0) {
                                str += "#";
                            }
                            str += kafSense.getSensecode()+":"+kafSense.getConfidence();
                        }

                    }
                    str += "\n";
                    fos.write(str.getBytes());
                }
            }
            fos.write(str.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
