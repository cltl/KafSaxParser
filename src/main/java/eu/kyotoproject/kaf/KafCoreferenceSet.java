package eu.kyotoproject.kaf;

import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 11/2/12
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafCoreferenceSet {

    /*
    <coreferences>
<coref id="co1">
<!-- London -->
<span >
<target id="t12" head="yes"/>
</span>
<!-- the capital city of England -->
<span>
<target id="t1"/>
<target id="t2"/>
<target id="t3" head ="yes"/> <!-- city is the head -->
<target id="t4"/>
<target id="t5"/>
</span>
<!-- it -->
<span>
<target id="t20" head="yes"/>
</span>
</coref>
</coreferences>
     */

    private String coid;
    private String type;
    private double score;
    private String lowestCommonSubsumer;
    private ArrayList<ArrayList<CorefTarget>> setsOfSpans;
    private ArrayList<String> tokenStringArray;
    private ArrayList<KafSense> externalReferences;



    public KafCoreferenceSet() {
        this.coid = "";
        this.type = "";
        this.score = 0;
        this.lowestCommonSubsumer = "";
        this.setsOfSpans = new ArrayList<ArrayList<CorefTarget>>();
        this.tokenStringArray = new ArrayList<String>();
        this.externalReferences = new ArrayList<KafSense>();

    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getLowestCommonSubsumer() {
        return lowestCommonSubsumer;
    }

    public void setLowestCommonSubsumer(String lowestCommonSubsumer) {
        this.lowestCommonSubsumer = lowestCommonSubsumer;
    }

    public ArrayList<String> getTokenStringArray() {
        return tokenStringArray;
    }

    public void setTokenStringArray(ArrayList<String> tokenStringArray) {
        this.tokenStringArray = tokenStringArray;
    }

    public void addTokenStringArray(String tokenString) {
        this.tokenStringArray.add(tokenString);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }

    public ArrayList<ArrayList<CorefTarget>> getSetsOfSpans() {
        return setsOfSpans;
    }

    public void setSetsOfSpans(ArrayList<ArrayList<CorefTarget>> setsOfSpans) {
        this.setsOfSpans = setsOfSpans;
    }

    public void addSetsOfSpans(ArrayList<CorefTarget> setOfSpans) {
        this.setsOfSpans.add(setOfSpans);
    }

    public ArrayList<KafSense> getExternalReferences() {
        return externalReferences;
    }

    public ArrayList<KafSense> getLcsFromExternalReferences() {
        ArrayList<KafSense> lcsReferences = new ArrayList<KafSense>();
        for (int i = 0; i < externalReferences.size(); i++) {
            KafSense kafSense = externalReferences.get(i);
            if (kafSense.getSource().equalsIgnoreCase("lowest-common-subsumer")) {
                lcsReferences.add(kafSense);
            }
        }
        return lcsReferences;
    }

    public ArrayList<KafSense> getExternalReferencesWithoutLCS() {
        ArrayList<KafSense> lcsReferences = new ArrayList<KafSense>();
        for (int i = 0; i < externalReferences.size(); i++) {
            KafSense kafSense = externalReferences.get(i);
            if (!kafSense.getSource().equalsIgnoreCase("lowest-common-subsumer")) {
                lcsReferences.add(kafSense);
            }
        }
        return lcsReferences;
    }

    public void setExternalReferences(ArrayList<KafSense> externalReferences) {
        this.externalReferences = externalReferences;
    }

    public void addExternalReferences(KafSense externalReference) {
        this.externalReferences.add(externalReference);
    }

    public boolean hasSpan (ArrayList<CorefTarget> spans) {
        for (int i = 0; i < setsOfSpans.size(); i++) {
            ArrayList<CorefTarget> corefTargets = setsOfSpans.get(i);
            int overlap = CorefTarget.overlapSetOfSpans(corefTargets, spans);
            if (overlap==corefTargets.size() && (overlap==spans.size())) {
                /// exact match
                return true;
            }
        }
        return false;
    }

    public boolean hasIntersection (ArrayList<CorefTarget> spans) {
        for (int i = 0; i < setsOfSpans.size(); i++) {
            ArrayList<CorefTarget> corefTargets = setsOfSpans.get(i);
            int overlap = CorefTarget.overlapSetOfSpans(corefTargets, spans);
            if (overlap>0) {
                /// exact match
                return true;
            }
        }
        return false;
    }


    public void setTokenStrings (KafSaxParser parser) {
        for (int i = 0; i < setsOfSpans.size(); i++) {
            ArrayList<CorefTarget> corefTargets = setsOfSpans.get(i);
            ArrayList<String> corefTokens = AddTokensAsCommentsToSpans.convertCorefTargetsToTokenSpan(parser, corefTargets);
            String tokenString = AddTokensAsCommentsToSpans.getTokenString(parser, corefTokens);
            tokenStringArray.add(tokenString);
        }
    }

    @Override
    public String toString() {
        return "KafCoreferenceSet{" +
                "coid='" + coid + '\'' +
                "type='" + type + '\'' +
                ", setsOfSpans=" + setsOfSpans +
                '}';
    }

    public Element toXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("coref");
        if (coid != null)
            root.setAttribute("coid", coid);
        if (type != null)
            root.setAttribute("type", type);
        if (setsOfSpans.size()>0) {
            for (int i = 0; i < setsOfSpans.size(); i++) {
                ArrayList<CorefTarget> corefTargets = setsOfSpans.get(i);
                Element setOfTargets = xmldoc.createElement(("span"));
                for (int j = 0; j < corefTargets.size(); j++) {
                    CorefTarget corefTarget = corefTargets.get(j);
                    setOfTargets.appendChild(corefTarget.toXML(xmldoc));
                }
                root.appendChild(setOfTargets);
            }
        }

        if (externalReferences.size()>0) {
            Element externalRefs = xmldoc.createElement("externalReferences");
            for (int i = 0; i < externalReferences.size(); i++) {
                KafSense kafSense = externalReferences.get(i);
                externalRefs.appendChild(kafSense.toXML(xmldoc));
            }
            root.appendChild(externalRefs);
        }
        return root;
    }
    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("coref");
        if (!coid.isEmpty())
            root.setAttribute("id", coid);
        if ((type != null) && (!type.isEmpty())) {
            root.setAttribute("type", type);
        }

        if (setsOfSpans.size()>0) {
            for (int i = 0; i < setsOfSpans.size(); i++) {
                ArrayList<CorefTarget> corefTargets = setsOfSpans.get(i);
                Element setOfTargets = xmldoc.createElement(("span"));
                if (tokenStringArray.size()>0) {
                    Comment comment = xmldoc.createComment(tokenStringArray.get(i));
                    setOfTargets.appendChild(comment);
                }
                for (int j = 0; j < corefTargets.size(); j++) {
                    CorefTarget corefTarget = corefTargets.get(j);
                    setOfTargets.appendChild(corefTarget.toXML(xmldoc));
                }
                root.appendChild(setOfTargets);
            }
        }

        if (externalReferences.size()>0) {
            Element externalRefs = xmldoc.createElement("externalReferences");
            for (int i = 0; i < externalReferences.size(); i++) {
                KafSense kafSense = externalReferences.get(i);
                externalRefs.appendChild(kafSense.toXML(xmldoc));
            }
            root.appendChild(externalRefs);
        }
        return root;
    }




}
