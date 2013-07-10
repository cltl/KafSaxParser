package eu.kyotoproject.stats;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafSense;
import eu.kyotoproject.kaf.KafTerm;
import eu.kyotoproject.kaf.TermComponent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: kyoto
 * Date: 3/11/11
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class DomainStats {


    static public void main (String[] args) {
        String kafFile = args[0];
        KafSaxParser parser = new KafSaxParser();
        parser.parseFile(kafFile);
        int nMWs = 0;
        int nCOMPs = 0;
        int nWN = 0;
        int nONT = 0;
        int nCWN = 0;
        int nCONT = 0;
        HashMap<String, Integer> scMappingsTerms = new HashMap<String, Integer> ();
        HashMap<String, Integer> scMappingsComponents = new HashMap<String, Integer> ();

        for (int i = 0; i < parser.kafTermList.size(); i++) {
            KafTerm kafTerm = parser.kafTermList.get(i);
            if (kafTerm.getComponents().size()>0) {
                ///this is a multiword
                nMWs++;
                for (int j = 0; j < kafTerm.getSenseTags().size(); j++) {
                    KafSense kafSense = kafTerm.getSenseTags().get(j);
                 //   System.out.println("kafSense.getResource() = " + kafSense.getResource());
                 //   System.out.println("kafSense.getSensecode() = " + kafSense.getSensecode());
                    if (kafSense.getResource().equals("wneng_domain")) {
                       nWN++;
                    }
                    else if (kafSense.getResource().equals("wn30g")) {
                       nWN++;
                    }
                    else {
                       nONT++;
                    }
                    String refType = kafSense.getRefType();
                    if (refType.startsWith("sc_")) {
                        if (scMappingsTerms.containsKey(refType)) {
                            Integer cnt = scMappingsTerms.get(refType);
                            cnt++;
                            scMappingsTerms.put(refType,cnt);
                        }
                        else {
                            scMappingsTerms.put(refType, 1);
                        }
                    }
                }
                for (int j = 0; j < kafTerm.getComponents().size(); j++) {
                    TermComponent termComponent = kafTerm.getComponents().get(j);
                    for (int k = 0; k < termComponent.getSenseTags().size(); k++) {
                        KafSense kafSense = termComponent.getSenseTags().get(k);
                      //  System.out.println("kafSense.getResource() = " + kafSense.getResource());
                      //  System.out.println("kafSense.getSensecode() = " + kafSense.getSensecode());
                        if (kafSense.getResource().equals("wneng_domain")) {
                           nCWN++;
                        }
                        else if (kafSense.getResource().equals("wn30g")) {
                           nCWN++;
                        }
                        else {
                           nCONT++;
                        }
                        String refType = kafSense.getRefType();
                        if (refType.startsWith("sc_")) {
                            if (scMappingsComponents.containsKey(refType)) {
                                Integer cnt = scMappingsComponents.get(refType);
                                cnt++;
                                scMappingsComponents.put(refType,cnt);
                            }
                            else {
                                scMappingsComponents.put(refType, 1);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("kafFile = " + kafFile);
        System.out.println("parser.kafTermList.size() = " + parser.kafTermList.size());
        System.out.println("nMWs = " + nMWs);
        System.out.println("nWN = " + nWN);
        System.out.println("nONT = " + nONT);
        System.out.println("nCOMPs = " + nCOMPs);
        System.out.println("nCWN = " + nCWN);
        System.out.println("nCONT = " + nCONT);
        System.out.println("scMappingsTerms = " + scMappingsTerms.size());
        Set keySet = scMappingsTerms.keySet();
        Iterator keys = keySet.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = scMappingsTerms.get(key);
            System.out.println(key+"="+cnt.toString());
        }
        System.out.println("scMappingsComponents = " + scMappingsComponents.size());
        keySet = scMappingsComponents.keySet();
        keys = keySet.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            Integer cnt = scMappingsComponents.get(key);
            System.out.println(key+"="+cnt.toString());
        }

    }
}
