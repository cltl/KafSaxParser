package eu.kyotoproject.util;

import eu.kyotoproject.kaf.*;

import java.util.ArrayList;

/**
 * Created by piek on 16/05/16.
 */
public class FixDutchSrl {
    static public void main (String[] args) {
        KafSaxParser kafSaxParser = new KafSaxParser();
        //String pathToFile = "/Users/piek/Desktop/NWR-INC/dasym/test1/test2.naf";
        ///kafSaxParser.parseFile(pathToFile);
        kafSaxParser.parseFile(System.in);
        dutchSrlFix(kafSaxParser);
        kafSaxParser.writeNafToStream(System.out);
    }

    /**
     * The predicate has lemma "zijn" and there are roles like "#"
     * We look in the last of these roles for a past participle or adjective to be come the new predicate
     * if there are two of these roles and no A0, then the first "#" becomes the A0
     * @param kafSaxParser
     */
    static void dutchSrlFix(KafSaxParser kafSaxParser) {
        ArrayList<KafEvent> newEvents = new ArrayList<KafEvent>();
        for (int i = 0; i < kafSaxParser.kafEventArrayList.size(); i++) {
            KafEvent kafEvent = kafSaxParser.kafEventArrayList.get(i);
            ArrayList<String> spandIds = kafEvent.getSpanIds();
            for (int j = 0; j < spandIds.size(); j++) {
                String span = spandIds.get(j);
                KafTerm kafTerm = kafSaxParser.getTerm(span);
                if (kafTerm!=null && kafTerm.getLemma().equals("zijn")) {
                    KafParticipant firstEmptyRole = null;
                    KafParticipant secondEmptyRole = null;
                    KafParticipant a0 = null;
                    KafParticipant a1 = null;
                    for (int k = 0; k < kafEvent.getParticipants().size(); k++) {
                        KafParticipant kafParticipant = kafEvent.getParticipants().get(k);
                        if (kafParticipant.getRole().equalsIgnoreCase("arg0")) {
                           a0 = kafParticipant;
                        }
                        else if (kafParticipant.getRole().equalsIgnoreCase("arg1")) {
                           a1 = kafParticipant;
                        }
                        if (kafParticipant.getRole().equalsIgnoreCase("#")) {
                           if (firstEmptyRole==null) {
                               firstEmptyRole = kafParticipant;
                           }
                           else {
                               secondEmptyRole = kafParticipant;
                           }
                        }
                    }
                    if (secondEmptyRole==null) {
                        if (firstEmptyRole!=null) {
                            /*int nEvents = kafSaxParser.kafEventArrayList.size()+newEvents.size()+1;
                            String id = "pr"+nEvents;*/
                            KafEvent newKafEvent = getPredicateFromParticipant(firstEmptyRole, kafSaxParser, kafEvent.getId());
                            if (newKafEvent!=null) {
                                for (int k = 0; k < kafEvent.getParticipants().size(); k++) {
                                    KafParticipant kafParticipant = kafEvent.getParticipants().get(k);
                                    if (!kafParticipant.getRole().equalsIgnoreCase("#")) {
                                       newKafEvent.addParticipant(kafParticipant);
                                    }
                                }
                                newEvents.add(newKafEvent);
                            }
                            else {
                                /// fall back: put back original
                                newEvents.add(kafEvent);
                            }
                        }
                        else {
                            /// fall back: put back original
                            newEvents.add(kafEvent);
                        }
                    }
                    else {
                       /* int nEvents = kafSaxParser.kafEventArrayList.size()+newEvents.size();
                        String id = "pr"+nEvents;*/
                        KafEvent newKafEvent = getPredicateFromParticipant(secondEmptyRole, kafSaxParser, kafEvent.getId());
                        if (newKafEvent!=null) {
                            for (int k = 0; k < kafEvent.getParticipants().size(); k++) {
                                KafParticipant kafParticipant = kafEvent.getParticipants().get(k);
                                if (!kafParticipant.getRole().equalsIgnoreCase("#")) {
                                    newKafEvent.addParticipant(kafParticipant);
                                }
                            }
                            if (a0 == null) {
                                firstEmptyRole.setRole("Arg0");
                                newKafEvent.addParticipant(firstEmptyRole);
                            }
                            newEvents.add(newKafEvent);
                        }
                        else {
                            /// fall back: put back original
                            newEvents.add(kafEvent);
                        }
                    }
                }
                else {
                    newEvents.add(kafEvent);
                }
            }
        }
        kafSaxParser.kafEventArrayList = newEvents;
        /*for (int i = 0; i < newEvents.size(); i++) {
            KafEvent kafEvent = newEvents.get(i);
            kafSaxParser.kafEventArrayList.add(kafEvent);
        }*/
    }

    static KafEvent getPredicateFromParticipant (KafParticipant kafParticipant, KafSaxParser kafSaxParser, String id) {
        KafEvent newKafEvent = null;
        String predicateId = "";
        ArrayList<String> termIds = kafParticipant.getSpanIds();
        for (int i = 0; i < termIds.size(); i++) {
            String termId = termIds.get(i);
            KafTerm kafTerm = kafSaxParser.getTerm(termId);
            if (kafTerm!=null && (kafTerm.getPos().equalsIgnoreCase("verb") || kafTerm.getPos().equalsIgnoreCase("adj"))) {
                predicateId = termId;
            }
        }
        if (!predicateId.isEmpty()) {
            newKafEvent = new KafEvent();
            ArrayList<CorefTarget> corefs = new ArrayList<CorefTarget>();
            CorefTarget corefTarget = new CorefTarget();
            corefTarget.setId(predicateId);
            corefs.add(corefTarget);
            newKafEvent.setId(id);
            newKafEvent.setSpans(corefs);
        }
        return newKafEvent;
    }

    /**
     *
     *     <predicate id="pr17">
     <!--zijn-->
     <externalReferences>
     <externalRef confidence="0.023007702" reference="eng-30-02702508-v" resource="ODWN"/>
     </externalReferences>
     <span>
     <target id="t_69"/>
     </span>
     <role id="r25" semRole="#">
     <!--Volgens GfK lijkt het sentiment hiermee te zijn verbeterd-->
     <span>
     <target id="t_62"/>
     <target id="t_63"/>
     <target id="t_64"/>
     <target id="t_65"/>
     <target id="t_66"/>
     <target id="t_67"/>
     <target id="t_68"/>
     <target id="t_69"/>
     <target head="yes" id="t_70"/>
     </span>
     </role>
     <role id="r36" semRole="Arg1">
     <!--het sentiment-->
     <span>
     <target id="t_65"/>
     <target head="yes" id="t_66"/>
     </span>
     </role>
     </predicate>
     *
     * <predicate id="pr10">
     <!--was-->
     <externalReferences>
     <externalRef confidence="0.021667985" reference="eng-30-02702508-v" resource="ODWN"/>
     </externalReferences>
     <span>
     <target id="t_268"/>
     </span>
     <role id="r10" semRole="ArgM-TMP">
     <!--tot nog toe-->
     <span>
     <target head="yes" id="t_269"/>
     <target id="t_270"/>
     <target id="t_271"/>
     </span>
     </role>
     <role id="r14" semRole="#">
     <!--Afgelopen maandag-->
     <span>
     <target id="t_266"/>
     <target head="yes" id="t_267"/>
     </span>
     </role>
     <role id="r24" semRole="#">
     <!--de drukste dag van het jaar-->
     <span>
     <target id="t_272"/>
     <target id="t_273"/>
     <target head="yes" id="t_274"/>
     <target id="t_275"/>
     <target id="t_276"/>
     <target id="t_277"/>
     </span>
     </role>
     </predicate>
     */
}
