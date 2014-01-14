package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafSense;
import eu.kyotoproject.kaf.KafTerm;
import eu.kyotoproject.kaf.TermComponent;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 17-dec-2008
 * Time: 14:38:37
 * To change this template use File | Settings | File Templates.
 * This file is part of KafSaxParser.

    KafSaxParser is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    KafSaxParser is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with KafSaxParser.  If not, see <http://www.gnu.org/licenses/>.
 */
public class CheckOntologyForTerms {


    public static void main (String[] args) {
        try {
            String str = "";
            int cntWN = 0;
            int cntOnt = 0;
            String kafFile = args[0];
            String logFile1 = kafFile+".1.log";
            String logFile2 = kafFile+".2.log";
            FileOutputStream fos1 = new FileOutputStream(logFile1);
            FileOutputStream fos2 = new FileOutputStream(logFile2);
            KafSaxParser parser = new KafSaxParser();
            parser.parseFile(kafFile);
            for (int i = 0; i < parser.kafTermList.size(); i++) {
                KafTerm kafTerm = parser.kafTermList.get(i);
                boolean wnMap = false;
                boolean ontMap = false;
                for (int j = 0; j < kafTerm.getSenseTags().size(); j++) {
                    KafSense kafSense = kafTerm.getSenseTags().get(j);
                    if (kafSense.getResource().equals("wneng_domain")) {
                       wnMap = true;
                    }
                    else if (kafSense.getResource().equals("wn30g")) {
                       wnMap = true;
                    }
                    else {
                       ontMap = true;
                    }
                }
                for (int j = 0; j < kafTerm.getComponents().size(); j++) {
                    TermComponent termComponent = kafTerm.getComponents().get(j);
                    for (int k = 0; k < termComponent.getSenseTags().size(); k++) {
                        KafSense kafSense = termComponent.getSenseTags().get(k);
                        if (kafSense.getResource().equals("wneng_domain")) {
                           wnMap = true;
                        }
                        else if (kafSense.getResource().equals("wn30g")) {
                           wnMap = true;
                        }
                        else {
                           ontMap = true;
                        }
                    }
                }
                if (!ontMap) {
                    if ((kafTerm.getLemma().length()>3)
                            && (!kafTerm.getLemma().equalsIgnoreCase("bay"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("new"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("old"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("one"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("ago"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("air"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("air"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("far"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("low"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("may"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("top"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("air"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("air"))
                            && (!kafTerm.getLemma().equalsIgnoreCase("air"))
                            && kafTerm.getType().equals("open")
                            && kafTerm.getLemma().toLowerCase().equals(kafTerm.getLemma())
                            ) {
                        if (wnMap) {
                            str = kafTerm.getLemma()+";"+kafTerm.getPos()+"\n";
                            fos1.write(str.getBytes());
                            cntWN++;
                        }
                        else {
                            str = kafTerm.getLemma()+";"+kafTerm.getPos()+"\n";
                            fos2.write(str.getBytes());
                            cntOnt++;
                        }
                    }
                    else {
                     //   System.out.println("kafTerm = " + kafTerm.getLemma());
                    }
                }
            }
            System.out.println("cntWN = " + cntWN);
            str = "Nr of terms with WN mappings but no Ontology mappings ="+cntWN+"\n";
            fos1.write(str.getBytes());
            fos1.close();
            System.out.println("cntOnt = " + cntOnt);
            str = "Nr of terms without even WN mappings ="+cntWN+"\n";
            fos2.write(str.getBytes());
            fos2.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
