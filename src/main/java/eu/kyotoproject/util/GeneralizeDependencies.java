package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafDep;
import eu.kyotoproject.kaf.KafSaxParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by piek on 3/4/14.
 */
public class GeneralizeDependencies {

    static public void main (String [] args) {
            //String kafFilePath = args[0];
            //String extension = args[1];
            String kafFilePath = "/Users/piek/Desktop/Thomese/Thomese_book_opener_nwr_dep";
            //String kafFilePath = "/Users/piek/Desktop/Thomese/Thomese_book_opener_nwr/page_20.tok.pos.pol.ner.const.dep.opi.kaf.wordnet.kaf.event.kaf";
            String extension = "kaf";
            File file = new File(kafFilePath);
            if (file.isDirectory()) {
                String [] files= FileProcessor.makeRecursiveFileList(kafFilePath, extension);
                for (int i = 0; i < files.length; i++) {
                    String filePath = files[i];
                    generalizeDependencies(filePath);
                }
            }
            else {
                generalizeDependencies(kafFilePath);
            }
    }


    /*
    ##### CONJUNCTIONS
#    <!--hd/su(die,controleren)-->
#    <dep from="t32" rfunc="hd/su" to="t36"/>
#    <!--hd/obj1(en,controleren)-->
#    <dep from="t34" rfunc="hd/obj1" to="t36"/>
#    <!--crd/cnj(paspoorten,en)-->
#    <dep from="t33" rfunc="crd/cnj" to="t34"/>
#    <!--crd/cnj(permits,en)-->
#    <dep from="t35" rfunc="crd/cnj" to="t34"/>
     */
    static void generalizeDependencies (String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName+".dep");
            KafSaxParser kafSaxParser = new KafSaxParser();
            kafSaxParser.parseFile(fileName);
            ArrayList<KafDep> newDepArrayList = new ArrayList<KafDep>();
            for (int i = 0; i < kafSaxParser.kafDepList.size(); i++) {
                KafDep kafDep = kafSaxParser.kafDepList.get(i);
                if (kafDep.getRfunc().startsWith("crd")) {
                    String cnjTo = kafDep.getTo();
                    String conjFrom = kafDep.getFrom();
                    if (kafSaxParser.TermToDeps.containsKey(cnjTo)) {
                        ArrayList<KafDep> deps = kafSaxParser.TermToDeps.get(cnjTo);
                        for (int j = 0; j < deps.size(); j++) {
                            KafDep dep = deps.get(j);
                            if ((!dep.getRfunc().startsWith("crd")) &&
                                    (!dep.getRfunc().startsWith("--")))    {
                                if (dep.getFrom().equalsIgnoreCase(cnjTo)) {
                                    KafDep newDep = new KafDep();
                                    newDep.setFrom(conjFrom);
                                    newDep.setTo(dep.getTo());
                                    newDep.setRfunc(dep.getRfunc());
                                    newDepArrayList.add(newDep);
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < newDepArrayList.size(); i++) {
                KafDep kafDep = newDepArrayList.get(i);
               // System.out.println("kafDep.toString() = " + kafDep.toString());
               // System.out.println("kafDep.toString() = " + kafDep.toString());
                kafSaxParser.kafDepList.add(kafDep);
            }
            kafSaxParser.writeNafToStream(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
