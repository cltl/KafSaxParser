package eu.kyotoproject.kaf;

import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 12/13/12
 * Time: 9:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class KafProperty {

    /*
    <properties>
<property type=ÒroomÓ pid=Óp1Ó>
	<references>
	<span> <target id=Ót49Ó /> </span>
	</references>
</property>
</properties>
     */

    private String type;
    private String id;
    //private ArrayList<String> spans;
    private ArrayList<ArrayList<CorefTarget>> setsOfSpans;
    private ArrayList<String> tokenStringArray;


    public KafProperty() {
        this.id = "";
        //this.spans = new ArrayList<String>();
        this.setsOfSpans = new ArrayList<ArrayList<CorefTarget>>();
        this.type = "";
        this.tokenStringArray = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

/*
    public ArrayList<String> getSpans() {
        return spans;
    }

    public void setSpans(ArrayList<String> spans) {
        this.spans = spans;
    }
*/

    public ArrayList<String> getTokenStringArray() {
        return tokenStringArray;
    }

    public void setTokenStringArray(ArrayList<String> tokenStringArray) {
        this.tokenStringArray = tokenStringArray;
    }

    public void setTokenStrings (KafSaxParser parser) {
        for (int i = 0; i < setsOfSpans.size(); i++) {
            ArrayList<CorefTarget> corefTargets = setsOfSpans.get(i);
            ArrayList<String> corefTokens = AddTokensAsCommentsToSpans.convertCorefTargetsToTokenSpan(parser, corefTargets);
            String tokenString = AddTokensAsCommentsToSpans.getTokenString(parser, corefTokens);
            tokenStringArray.add(tokenString);
        }
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "KafProperty{" +
                ", pid='" + id + '\'' +
                ", type='" + type + '\''+
               // ", spans=" + spans +
                '}';
    }

    public Element toXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("property");
        if (type != null)
            root.setAttribute("type", type);
        if (id != null)
            root.setAttribute("pid", id);
/*
        if (spans.size()>0) {
            Element referencesElement = xmldoc.createElement("references");
            Element spanElement = xmldoc.createElement("span");
            for (int i = 0; i < this.spans.size(); i++)
            {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", spans.get(i));
                spanElement.appendChild(target);
            }
            referencesElement.appendChild(spanElement);
            root.appendChild(referencesElement);
        }
*/
        if (setsOfSpans.size()>0) {
            Element referencesElement = xmldoc.createElement("references");
            for (int i = 0; i < setsOfSpans.size(); i++) {
                ArrayList<CorefTarget> corefTargets = setsOfSpans.get(i);
                Element setOfTargets = xmldoc.createElement(("span"));
                for (int j = 0; j < corefTargets.size(); j++) {
                    CorefTarget corefTarget = corefTargets.get(j);
                    if (tokenStringArray.size()>j) {
                        Comment comment = xmldoc.createComment(tokenStringArray.get(j));
                        setOfTargets.appendChild(comment);
                    }
                    setOfTargets.appendChild(corefTarget.toXML(xmldoc));
                }
                referencesElement.appendChild(setOfTargets);
            }
            root.appendChild(referencesElement);
        }
        return root;
    }
}
