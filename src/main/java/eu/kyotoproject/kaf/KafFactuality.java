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

  NEW FORMAT July 2015

      <factuality id="f40">
      <span>
        <target id="t248"/>
      </span>
      <factVal resource="nwr:attributionTense" value="NON_FUTURE"/>
      <factVal resource="factbank" value="CT+"/>
      <factVal resource="nwr:attributionCertainty" value="CERTAIN"/>
      <factVal resource="nwr:attributionPolarity" value="POS"/>
    </factuality>
    <factuality id="f41">
      <span>
        <target id="t183"/>
      </span>
      <factVal resource="nwr:attributionTense" value="UNDERSPECIFIED"/>
      <factVal resource="factbank" value="NONE"/>
      <factVal resource="nwr:attributionCertainty" value="CERTAIN"/>
      <factVal resource="nwr:attributionPolarity" value="POS"/>
    </factuality>
     */
   // static public final String defaultAttribution = "CERTAIN,NON_FUTURE,POS";
    static public final String defaultAttribution = "CERTAIN_NON_FUTURE_POS";
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


    /**
     * Values are converted to a fixed string with fields for each of the following 4 sources
     *** example output
     <factValue confidence="0.83" resource="FactBank" value="CT+"/>
     <factValue confidence="0.11" resource="nwr:AttributionCertainty" value="PROBABLE"/>
     <factValue confidence="0.91" resource="nwr:AttributionTime" value="FUTURE"/>
     <factValue confidence="0.67" resource="nwr:AttributionPolarity" value="POS"/>

     *** actual output
     <factuality id="f40">
     <span>
     <target id="t248"/>
     </span>
     <factVal resource="nwr:attributionTense" value="NON_FUTURE"/>
     <factVal resource="factbank" value="CT+"/>
     <factVal resource="nwr:attributionCertainty" value="CERTAIN"/>
     <factVal resource="nwr:attributionPolarity" value="POS"/>
     </factuality>
     <factuality id="f41">
     <span>
     <target id="t183"/>
     </span>
     <factVal resource="nwr:attributionTense" value="UNDERSPECIFIED"/>
     <factVal resource="factbank" value="NONE"/>
     <factVal resource="nwr:attributionCertainty" value="CERTAIN"/>
     <factVal resource="nwr:attributionPolarity" value="POS"/>
     </factuality>

     WE ignore FactBank values
     http://www.newsreader-project.eu/ontologies/value#attr=CERTAIN,FUTURE,POS

     * @return
     */
    public String getPrediction () {
        String prediction = "";
        String certainty = "u";
        String time = "u";
        String polarity = "u";
      //  System.out.println("factValueArrayList.size() = " + factValueArrayList.size());
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (kafFactValue.getResource().toLowerCase().endsWith("attributioncertainty")) {
                certainty = kafFactValue.getValue();
                break; // assume there is one value only
            }
        }
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (kafFactValue.getResource().toLowerCase().endsWith("attributiontense")) {
                time = kafFactValue.getValue();
                break; // assume there is one value only
            }
        }
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (kafFactValue.getResource().toLowerCase().endsWith("attributionpolarity")) {
                polarity = kafFactValue.getValue();
                break; // assume there is one value only
            }
        }
        prediction = certainty+"_"+time+"_"+polarity;
      //  prediction = certainty+","+time+","+polarity;
        prediction = prediction.replaceAll("UNDERSPECIFIED","u");

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
