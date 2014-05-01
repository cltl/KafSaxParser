package eu.kyotoproject.util;

import eu.kyotoproject.kaf.CorefTarget;

import java.util.ArrayList;

/**
 * Created by piek on 4/23/14.
 */
public class SetsOfSpans {

    static public boolean intersectingSpans (ArrayList<ArrayList<CorefTarget>> spans1, ArrayList<ArrayList<CorefTarget>> spans2) {
        for (int i = 0; i < spans1.size(); i++) {
            ArrayList<CorefTarget> corefTargets = spans1.get(i);
            for (int j = 0; j < corefTargets.size(); j++) {
                CorefTarget corefTarget = corefTargets.get(j);
                for (int k = 0; k < spans2.size(); k++) {
                    ArrayList<CorefTarget> targets = spans2.get(k);
                    for (int l = 0; l < targets.size(); l++) {
                        CorefTarget target = targets.get(l);
                        if (target.getId().equals(corefTarget.getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
