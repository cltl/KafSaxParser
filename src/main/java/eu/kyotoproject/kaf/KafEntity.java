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
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafEntity {
    /*
    <entities>
  <!-- John -->
  <entity type="person">
    <span>
      <target id="t1"/>
    </span>
  </entity>
  <!-- Monday  -->
  <entity type="date">
    <span>
      <target id="t6"/>
    </span>
  </entity>
  <!-- New York -->
  <entity type="location">
    <span>
      <target id="t8"/>
    </span>
    <externalReferences>
      <externalRef resource="Wikipedia"
                   reference="New_York"
                   confidence="0.85"/>
      <externalRef resource="Wikipedia"
                   reference="New_York,Lincolnshire"
                   confidence="0.15"/>
    </externalReferences>
  </entity>
</entities>


<entities>
  <entity type=ÕpersonÕ eid=Óe9Ó>
    <references>
      <reference>
          <target id=Ót1Ó/> // ex. First Name
          <target id=Ót2Ó/> // ex. Last Name
      </reference>
      <reference>
        <target id=Ót34Ó/>
        <target id=Ót35Ó/>
      </reference>
    </references>
    <externalReferences>
      <externalReference confidence=Ó0.7Ó reference=Ó13982343Ó resource=ÓJRCNamesÓ/>
      <externalReference confidence=Ó0.3Ó reference=Ó834354Ó resource=ÓJRCNamesÓ/>
    </externalReferences>
  </entity>
  <entity type=ÓpersonÓ eid=Óe11Ó>
    <references>
      <reference>
        <target id=Ót2Ó/> // ex. Name
      </reference>
    </references>
    <externalReferences>
      <externalReference confidence=Ó0.9Ó reference=Ó13982343Ó resource=ÓJRCNamesÓ/>
      <externalReference confidence=Ó0.1Ó reference=Ó834354Ó resource=ÓJRCNamesÓ/>
    </externalReferences>
  </entity>
</entities>

<entities>

  <entity type=ÓpersonÓ eid=Óe1Ó>
    <references>
      <span>
        <target id=Ót1Ó/>
        <target id=Ót2Ó/>
      </span>
      <span>
        ... TARGET(S) ...
      </span>
    </references>
    <externalReferences>
      <externalReference confidence=Ó0.7Ó reference=Ó13982343Ó resource=ÓJRCNamesÓ/>
      <externalReference confidence=Ó0.3Ó reference=Ó834354Ó resource=ÓJRCNamesÓ/>
    </externalReferences>
  </entity>
     */

    private String id;
    private String type;
    private String subtype;
    private String moneyISO;
    private String dateISO;
    private ArrayList<ArrayList<CorefTarget>> setsOfSpans;
    private ArrayList<String> tokenStringArray;

//    private ArrayList<String> spans;
    private ArrayList<KafSense> externalReferences;

    public KafEntity() {
        this.externalReferences = new ArrayList<KafSense>();
      //  this.spans = new ArrayList<String>();
        this.setsOfSpans = new ArrayList<ArrayList<CorefTarget>>();
        this.tokenStringArray = new ArrayList<String>();
        this.type = "";
        this.id = "";
        this.subtype = "";
        this.moneyISO = "";
        this.dateISO = "";
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

    public ArrayList<KafSense> getExternalReferences() {
        return externalReferences;
    }

    public void setExternalReferences(ArrayList<KafSense> externalReferences) {
        this.externalReferences = externalReferences;
    }

    public void addExternalReferences(KafSense externalReference) {
        this.externalReferences.add(externalReference);
    }

/*
    public ArrayList<String> getSpans() {
        return spans;
    }

    public void setSpans(ArrayList<String> spans) {
        this.spans = spans;
    }

    public void addSpan(String span) {
        if (!this.spans.contains(span)) {
            this.spans.add(span);
        }
    }
*/

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDateISO() {
        return dateISO;
    }

    public void setDateISO(String dateISO) {
        this.dateISO = dateISO;
    }

    public String getMoneyISO() {
        return moneyISO;
    }

    public void setMoneyISO(String moneyISO) {
        this.moneyISO = moneyISO;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
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
        return "KafEntity{" +
                "externalReferences=" + externalReferences +
                ", eid='" + id + '\'' +
                ", type='" + type + '\'' +
                ", subtype='" + subtype + '\'' +
                ", moneyISO='" + moneyISO + '\'' +
                ", dateISO='" + dateISO + '\'' +
              //  ", spans=" + spans +
                '}';
    }

    public Element toXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("entity");
        if (type != null)
            root.setAttribute("type", type);
        if (id != null)
            root.setAttribute("eid", id);
        if ((subtype != null) && (!subtype.isEmpty()))
            root.setAttribute("subtype", subtype);
        if ((moneyISO != null)  && (!moneyISO.isEmpty()))
            root.setAttribute("moneyISO", moneyISO);
        if ((dateISO != null)  && (!dateISO.isEmpty()))
            root.setAttribute("dateISO", dateISO);
/*        if (spans.size()>0) {
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
        }*/
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
