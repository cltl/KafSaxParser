package eu.kyotoproject.kaf;

import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kyoto on 12/4/13.
 */
public class KafFactuality  implements Serializable {

    /**
     * OLD FORMAT
     * <factualitylayer>
     *     <factvalue id="w4" prediction="CT+" confidence="0.5904876913248487"/>
     * <factvalue id="w681" prediction="Uu" confidence="0.5324089742552198"/>
     */

    /*
    NEW FORMAT, June 2015

    <factualities>
	<factuality id="f1">
      <span>
        <target id="t3"/>
        <target id="t4"/>
        <target id="t5"/>
        <target id="t6"/>
        <target id="t7"/>
      </span>
      <factVal value="CT+" resource="FactBank"
               confidence="0.83"/>
      <factVal value="CERTAIN" resource="nwr:AttributionCertainty"
               confidence="0.79"/>
      <factVal value="PROBABLE" resource="nwr:AttributionCertainty"
               confidence="0.11"/>
      <factVal value="NONFUTURE" resource="nwr:AttributionTime"
               confidence="0.91"/>
      <factVal value="POS" resource="nwr:AttributionPolarity"
               confidence="0.67"/>
	</factuality>
  </factualities>
     */

    private String id;
    //private String prediction;
    //private Double confidence;
    private ArrayList<KafFactValue> factValueArrayList;
    private ArrayList<String> spans;
    private String tokenString;

    public KafFactuality() {
        this.id = "";
      //  this.prediction = "";
      //  this.confidence = 0.0;
        this.spans = new ArrayList<String>();
        this.factValueArrayList = new ArrayList<KafFactValue>();
        this.tokenString = "";
    }

    public String getId() {
        return id;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString (KafSaxParser kafSaxParser) {
        this.tokenString = AddTokensAsCommentsToSpans.getTokenStringFromTermIds(kafSaxParser,
                this.getSpans());
    }

    public void setId(String id) {
        this.id = id;
    }

/*
    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }
*/

    public String getPrediction () {
        String prediction = "";
      //  System.out.println("factValueArrayList.size() = " + factValueArrayList.size());
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (!prediction.isEmpty()) {
                prediction +=";";
            }
            prediction += kafFactValue.getValue();
        }
     //   System.out.println("prediction = " + prediction);

        return prediction;
    }

    public ArrayList<KafFactValue> getFactValueArrayList() {
        return factValueArrayList;
    }

    public void setFactValueArrayList(ArrayList<KafFactValue> factValueArrayList) {
        this.factValueArrayList = factValueArrayList;
    }

    public void addFactValue(KafFactValue factValue) {
        this.factValueArrayList.add(factValue);
    }

    public ArrayList<String> getSpans() {
        return spans;
    }

    public void setSpans(ArrayList<String> spans) {
        this.spans = spans;
    }

    public void addSpan(String span) {
        if (!spans.contains(span)) this.spans.add(span);
    }

    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("factuality");
        if (!id.isEmpty())
            root.setAttribute("id", id);
/*
        if (!prediction.isEmpty())
            root.setAttribute("prediction", prediction);
        if (confidence>0) {
            root.setAttribute("confidence", confidence.toString());
        }
*/
        if (spans.size()>0) {
            Element span = xmldoc.createElement("span");
            if (!tokenString.isEmpty()) {
                Comment comment = xmldoc.createComment(tokenString.trim());
                span.appendChild(comment);
            }
            for (int i = 0; i < spans.size(); i++) {
                Element target = xmldoc.createElement("target");
                target.setAttribute("id", this.spans.get(i));
                span.appendChild(target);
            }
            root.appendChild(span);
        }
        if (factValueArrayList.size()>0) {
            for (int i = 0; i < factValueArrayList.size(); i++) {
                Element factValue = this.factValueArrayList.get(i).toNafXML(xmldoc);
                root.appendChild(factValue);
            }
        }
        return root;
    }

}
