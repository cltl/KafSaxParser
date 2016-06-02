package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafFactValue;
import eu.kyotoproject.kaf.KafFactuality;
import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafTerm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by piek on 02/06/16.
 */
public class FixFactualityEnglish {

    static public void main (String[] args) {
        KafSaxParser kafSaxParser = new KafSaxParser();
        String pathToFile = "";
        String extension = ".naf";
        pathToFile = "/Users/piek/Desktop/01082e4c-a2f8-11e4-ac1c-00144feab7de.relink.dominantentities.naf";
        pathToFile = "/Users/piek/Desktop/NWR-INC/financialtimes/brexit4-SAMPLE";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equalsIgnoreCase("--input") && args.length>(i+1)) {
                pathToFile = args[i+1];
            }
            else if (arg.equalsIgnoreCase("--extension") && args.length>(i+1)) {
                extension = args[i+1];
            }
        }
        if (pathToFile.equalsIgnoreCase("stream")) {
            kafSaxParser.parseFile(System.in);
            fix(kafSaxParser);
            kafSaxParser.writeNafToStream(System.out);
        }
        else {
            File file = new File(pathToFile);
            if (file.isDirectory()) {
                ArrayList<File> files = FileProcessor.makeRecursiveFileArrayList(pathToFile, extension);
                for (int i = 0; i < files.size(); i++) {
                    File nextFile = files.get(i);
                    kafSaxParser.parseFile(nextFile);
                    fix(kafSaxParser);
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
                fix(kafSaxParser);
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


    /*

        <term id="t713" lemma="can'" morphofeat="MD" pos="O" type="close">
      <span>
        <!--can'-->
        <target id="w713"/>
      </span>
    </term>
    <term id="t714" lemma="t" morphofeat="VB" pos="V" type="open">
      <span>
        <!--t-->
        <target id="w714"/>
      </span>
    </term>
    <term id="t715" lemma="understand" morphofeat="VB" pos="V" type="open">
        <factuality id="f144">
      <span>
        <target id="t715"/>
      </span>
      <factVal resource="nwr:attributionTense" value="UNDERSPECIFIED"/>
      <factVal resource="factbank" value="Uu" source="vua-perspectives_factuality-1.1.models/timbl.factuality.model.v1.no.NONE.wgt"/>
      <factVal resource="nwr:attributionCertainty" value="UNDERSPECIFIED" source="vua-perspectives_factuality-1.1.models/timbl.factuality.model.v1.no.NONE.wgt"/>
      <factVal resource="nwr:attributionPolarity" value="UNDERSPECIFIED" source="vua-perspectives_factuality-1.1.models/timbl.factuality.model.v1.no.NONE.wgt"/>
    </factuality>
     */
    static public void fix (KafSaxParser kafSaxParser) {
        for (int i = 0; i < kafSaxParser.kafFactualityLayer.size(); i++) {
                KafFactuality kafFactuality = kafSaxParser.kafFactualityLayer.get(i);
                KafTerm kafTerm = null;
                KafTerm kafTermp1 = null;
                KafTerm kafTermp2 = null;
                KafTerm kafTermp3 = null;
                for (int k = 0; k < kafSaxParser.kafTermList.size(); k++) {
                    kafTerm = kafSaxParser.kafTermList.get(k);
                    if (kafFactuality.getSpans().contains(kafTerm.getTid())) {
                        if (k>0) kafTermp1 = kafSaxParser.kafTermList.get(k - 1);
                        if (k>1) kafTermp2 = kafSaxParser.kafTermList.get(k - 2);
                        if (k>2) kafTermp3 = kafSaxParser.kafTermList.get(k - 3);
                        break;
                    }
                }
                if (kafTerm!=null) {
                    for (int j = 0; j < kafFactuality.getFactValueArrayList().size(); j++) {
                        KafFactValue kafFactValue = kafFactuality.getFactValueArrayList().get(j);
                        if (!kafFactValue.getValue().equalsIgnoreCase("NEG") &&
                                kafFactValue.getResource().equalsIgnoreCase("nwr:attributionPolarity")) {

                            if (kafTermp1 != null && kafTermp1.getLemma().equals("not")) {
                                kafFactValue.setValue("NEG");
                              //  System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                            }
                            if (kafTermp2 != null && kafTermp2.getLemma().equals("not")) {
                                kafFactValue.setValue("NEG");
                              //  System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                            }
                            if (kafTermp3 != null && kafTermp3.getLemma().equals("not")) {
                                kafFactValue.setValue("NEG");
                            //    System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                            }
                            if (kafTermp1 != null && kafTermp2 != null && kafTermp1.getLemma().equals("t")) {
                                if (kafTermp2.getLemma().equals("do") ||
                                        kafTermp2.getLemma().equals("don") ||
                                        kafTermp2.getLemma().equals("don'") ||
                                        kafTermp2.getLemma().equals("have") ||
                                        kafTermp2.getLemma().equals("haven") ||
                                        kafTermp2.getLemma().equals("haven'") ||
                                        kafTermp2.getLemma().equals("be") ||
                                        kafTermp2.getLemma().equals("aren") ||
                                        kafTermp2.getLemma().equals("aren'") ||
                                        kafTermp2.getLemma().equals("will") ||
                                        kafTermp2.getLemma().equals("won") ||
                                        kafTermp2.getLemma().equals("won'") ||
                                        kafTermp2.getLemma().equals("can") ||
                                        kafTermp2.getLemma().equals("can'") ||
                                        kafTermp2.getLemma().equals("cann") ||
                                        kafTermp2.getLemma().equals("cann'") ||
                                        kafTermp2.getLemma().equals("may") ||
                                        kafTermp2.getLemma().equals("mayn") ||
                                        kafTermp2.getLemma().equals("mayn'") ||
                                        kafTermp2.getLemma().equals("mightn") ||
                                        kafTermp2.getLemma().equals("mightn'") ||
                                        kafTermp2.getLemma().equals("shall") ||
                                        kafTermp2.getLemma().equals("shalln") ||
                                        kafTermp2.getLemma().equals("shalln'") ||
                                        kafTermp2.getLemma().equals("should") ||
                                        kafTermp2.getLemma().equals("shouldn") ||
                                        kafTermp2.getLemma().equals("shouldn'") ||
                                        kafTermp2.getLemma().equals("must") ||
                                        kafTermp2.getLemma().equals("mustn") ||
                                        kafTermp2.getLemma().equals("mustn'") ||
                                        kafTermp2.getMorphofeat().equalsIgnoreCase("MD")) {
                                    kafFactValue.setValue("NEG");
                                //    System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                                }
                            }
                            if (kafTermp1 != null && kafTermp1.getLemma().equals("be") && kafTermp2 != null && kafTermp2.getLemma().equals("t")) {
                                kafFactValue.setValue("NEG");
                             //   System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                            }
                        }
                        else if (!kafFactValue.getValue().equalsIgnoreCase("FUTURE") &&
                                kafFactValue.getResource().equalsIgnoreCase("nwr:attributionTense")) {
                            if (kafTermp1 != null && kafTermp1.getMorphofeat().equalsIgnoreCase("MD")) {
                                kafFactValue.setValue("FUTURE");
                              //    System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                            }
                            if (kafTermp2 != null && kafTermp2.getMorphofeat().equalsIgnoreCase("MD")) {
                                kafFactValue.setValue("FUTURE");
                              //     System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                            }
                            if (kafTermp1 != null) {
                                if (kafTermp1.getLemma().equals("will") ||
                                        kafTermp1.getLemma().equals("can") ||
                                        kafTermp1.getLemma().equals("may") ||
                                        kafTermp1.getLemma().equals("shall") ||
                                        kafTermp1.getLemma().equals("should") ||
                                        kafTermp1.getLemma().equals("would") ||
                                        kafTermp1.getLemma().equals("must")) {
                                    kafFactValue.setValue("FUTURE");
                                //       System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                                }
                            }
                            if (kafTermp2 != null) {
                                if (kafTermp2.getLemma().equals("will") ||
                                        kafTermp2.getLemma().equals("can") ||
                                        kafTermp2.getLemma().equals("may") ||
                                        kafTermp2.getLemma().equals("should") ||
                                        kafTermp2.getLemma().equals("shall") ||
                                        kafTermp2.getLemma().equals("would") ||
                                        kafTermp2.getLemma().equals("must")) {
                                    kafFactValue.setValue("FUTURE");
                                  //     System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                                }
                            }
                        }
                        else if (!kafFactValue.getValue().equalsIgnoreCase("CERTAIN") &&
                                kafFactValue.getResource().equalsIgnoreCase("nwr:attributionCertainty")) {

                            if (kafTermp1!=null && kafTermp1.getLemma().equalsIgnoreCase("might") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("may") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("can") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("could") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("perhaps") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("possibly") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("could") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("uncertain") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("uncertainly") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("unsure") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("unlikely") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("maybe")
                                    ) {
                                    kafFactValue.setValue("UNCERTAIN");
                                    //    System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                            }
                            else if (kafTermp2!=null && kafTermp2.getLemma().equalsIgnoreCase("might") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("may") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("can") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("could") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("perhaps") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("possibly") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("could") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("uncertain") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("uncertainly") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("possibly") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("potentially") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("unsure") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("unlikely") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("maybe")
                                    ) {
                                    kafFactValue.setValue("UNCERTAIN");
                                    //    System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                            }
                        }
                    }
                    /*boolean NEGATION = false;
                    for (int j = 0; j < kafFactuality.getFactValueArrayList().size(); j++) {
                        KafFactValue kafFactValue = kafFactuality.getFactValueArrayList().get(j);
                        if (kafFactValue.getValue().equalsIgnoreCase("NEG") &&
                                kafFactValue.getResource().equalsIgnoreCase("nwr:attributionPolarity")) {
                            NEGATION = true;
                            break;
                        }
                    }
                    for (int j = 0; j < kafFactuality.getFactValueArrayList().size(); j++) {
                        KafFactValue kafFactValue = kafFactuality.getFactValueArrayList().get(j);
                        if (!kafFactValue.getValue().equalsIgnoreCase("CERTAIN") &&
                                kafFactValue.getResource().equalsIgnoreCase("nwr:attributionCertainty")) {

                            if (kafTermp1.getLemma().equalsIgnoreCase("might") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("may") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("can") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("could") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("perhaps") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("possibly") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("could") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("unsure") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("unlikely") ||
                                    kafTermp1.getLemma().equalsIgnoreCase("maybe")
                                    ) {
                                if (!NEGATION) {
                                    kafFactValue.setValue("UNCERTAIN");
                                    //    System.out.println(kafTerm.getLemma() + " kafFactValue = " + kafFactValue.toString());
                                }
                            }
                        }
                    }*/
                }
        }
    }

    /*
    were motivated
    would/could
    are motivated
    have been motivated
    will be motivated
    can be motivated
    shall be motivated
    to motivate
    going to be motivated
     */
}
