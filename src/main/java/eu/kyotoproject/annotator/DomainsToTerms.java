package eu.kyotoproject.annotator;

import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafSense;
import eu.kyotoproject.kaf.KafTerm;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 4/19/12
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class DomainsToTerms {

    /*

u:d_n-9964-n v:d_v-8526-v w:1.0 s:cdb
u:d_n-9964-n v:administration w:1.0 s:domain
u:d_n-9964-n v:commerce w:1.0 s:domain
u:d_n-9964-n v:economy w:1.0 s:domain

     */


    static public void main (String[] args) {
        try {
            String pathToKafFile = args[0];
            String pathToWnRelFile = args[1];
            HashMap<String, ArrayList<String>> synsetDomainMap = new HashMap<String, ArrayList<String>>();
            FileOutputStream fos = new FileOutputStream(pathToKafFile+".anno.kaf");
            FileInputStream fis = new FileInputStream(pathToWnRelFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (in.ready()&&(inputLine = in.readLine()) != null) {
                    if (inputLine.endsWith("s:domain")) {
                        String [] fields = inputLine.split(" ");
                        if (fields.length==4) {
                            String synsetId = fields[0].substring(2);
                           // System.out.println("synsetId = " + synsetId);
                            String domain = fields[1].substring(2);
                            if (synsetDomainMap.containsKey(synsetId)) {
                                ArrayList<String> domains = synsetDomainMap.get(synsetId);
                                if (!domains.contains(domain)) {
                                    domains.add(domain);
                                }
                                synsetDomainMap.put(synsetId, domains);
                            }
                            else {
                                ArrayList<String> domains = new ArrayList<String>();
                                domains.add(domain);
                                synsetDomainMap.put(synsetId, domains);
                            }
                        }
                        else {
/*
                            for (int i = 0; i < fields.length; i++) {
                                String field = fields[i];
                                System.out.println("field = " + field);
                            }
*/
                            //System.out.println("inputLine = " + inputLine);

                        }
                    }
            }
            System.out.println("synsetDomainMap = " + synsetDomainMap.size());
            KafSaxParser parser = new KafSaxParser();
            parser.parseFile(pathToKafFile);
            for (int i = 0; i < parser.getKafTerms().size(); i++) {
                KafTerm kafTerm = parser.getKafTerms().get(i);
                for (int j = 0; j < kafTerm.getSenseTags().size(); j++) {
                    KafSense kafSense = kafTerm.getSenseTags().get(j);
                    String synsetId = kafSense.getSensecode();
                    if (synsetDomainMap.containsKey(synsetId)) {
                        ArrayList<String> domains = synsetDomainMap.get(synsetId);
                        for (int k = 0; k < domains.size(); k++) {
                            String d = domains.get(k);
                            KafSense domainSense = new KafSense();
                            domainSense.setResource("WordnetDomain");
                            domainSense.setSensecode(d);
                            kafSense.addChildren(domainSense);
                        }
                    }
                }
            }
            parser.writeKafToStream(fos);
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
