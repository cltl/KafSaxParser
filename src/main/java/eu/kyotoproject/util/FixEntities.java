package eu.kyotoproject.util;

import eu.kyotoproject.kaf.KafEntity;
import eu.kyotoproject.kaf.KafSaxParser;
import eu.kyotoproject.kaf.KafTerm;

import java.util.ArrayList;

/**
 * Created by piek on 16/05/16.
 */
public class FixEntities {

    static public void main (String[] args) {
        KafSaxParser kafSaxParser = new KafSaxParser();
        kafSaxParser.parseFile(System.in);
        kafSaxParser.writeNafToStream(System.out);
    }

    static void fix (KafSaxParser kafSaxParser) {
        ArrayList<KafEntity> fixedEntities = new ArrayList<KafEntity>();
        for (int i = 0; i < kafSaxParser.kafEntityArrayList.size(); i++) {
            KafEntity kafEntity = kafSaxParser.kafEntityArrayList.get(i);
            if (validEntityType(kafEntity.getType())) {
                fixedEntities.add(kafEntity);
            }
            else {
                ArrayList<String> spanIds = kafEntity.getTermIds();
                String lastSpanId = spanIds.get(spanIds.size()-1);
                KafTerm kafTerm = kafSaxParser.getTerm(lastSpanId);
                if (kafTerm!=null) {
                    if (kafTerm.getPos().equalsIgnoreCase("name")) {
                      fixedEntities.add(kafEntity);
                    }
                }
            }
        }
        kafSaxParser.kafEntityArrayList = fixedEntities;
    }

    static boolean validEntityType (String type) {
        if (type.equalsIgnoreCase("PER")) {
            return true;
        }
        else if (type.equalsIgnoreCase("PRO")) {
            return true;
        }
        else if (type.equalsIgnoreCase("MISC")) {
            return true;
        }
        else if (type.equalsIgnoreCase("ORG")) {
            return true;
        }
        else if (type.equalsIgnoreCase("LOC")) {
            return true;
        }
        return false;
    }



}
