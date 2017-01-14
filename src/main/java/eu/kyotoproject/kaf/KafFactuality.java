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


    static public final String defaultAttribution = "CERTAIN_NONFUTURE_POS";
    static public final String[] defaultAttributionArray = {"CERTAIN", "NONFUTURE", "POS"};
    static public ArrayList<String> castToDefault () {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < defaultAttributionArray.length; i++) {
            String s = defaultAttributionArray[i];
            arrayList.add(s);
        }
        return arrayList;
    }

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
     * @Deprecated
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
        String certainty = "";
        String time = "";
        String polarity = "";
      //  System.out.println("factValueArrayList.size() = " + factValueArrayList.size());
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (kafFactValue.getResource().toLowerCase().endsWith("attributioncertainty")) {
                certainty = kafFactValue.getValue();
                break; // assume there is one value only
            }
            else if (kafFactValue.getResource().equalsIgnoreCase("factbank")) {
                if (kafFactValue.getValue().startsWith("CT")) {
                    certainty = "CERTAIN";
                    break; // assume there is one value only
                } else if (kafFactValue.getValue().startsWith("PR")) {
                    certainty = "PROBABLE";
                    break; // assume there is one value only
                } else if (kafFactValue.getValue().startsWith("PS")) {
                    certainty = "POSSIBLE";
                    break; // assume there is one value only
                }
            }
        }
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (kafFactValue.getResource().toLowerCase().endsWith("attributiontense")) {
                time = kafFactValue.getValue();
                if (time.equalsIgnoreCase("NON_FUTURE")) time = "NONFUTURE";
                break; // assume there is one value only
            }
        }
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (kafFactValue.getResource().toLowerCase().endsWith("attributionpolarity")) {
                polarity = kafFactValue.getValue();
                break; // assume there is one value only
            }
            else if (kafFactValue.getResource().equalsIgnoreCase("factbank")) {
                if (kafFactValue.getValue().endsWith("-")) {
                    polarity = "NEG";
                    break; // assume there is one value only
                }
                else if (kafFactValue.getValue().endsWith("+")) {
                    polarity = "POS";
                    break; // assume there is one value only
                }
            }
        }
        if (certainty.equalsIgnoreCase("uu") ||
                certainty.equalsIgnoreCase("UNDERSPECIFIED")) certainty ="";
        if (time.equalsIgnoreCase("uu") ||
                time.equalsIgnoreCase("UNDERSPECIFIED")) time ="";
        if (polarity.equalsIgnoreCase("uu") ||
                polarity.equalsIgnoreCase("UNDERSPECIFIED")) polarity="";

        prediction = certainty+"_"+time+"_"+polarity;
        return prediction;
    }

    public ArrayList<String> getPredictionArrayList () {
        ArrayList<String> prediction = new ArrayList<String>();
        String certainty = "";
        String time = "";
        String polarity = "";
      //  System.out.println("factValueArrayList.size() = " + factValueArrayList.size());
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (kafFactValue.getResource().toLowerCase().endsWith("attributioncertainty")) {
                certainty = kafFactValue.getValue();
                break; // assume there is one value only
            }
            else if (kafFactValue.getResource().equalsIgnoreCase("factbank")) {
                if (kafFactValue.getValue().startsWith("CT")) {
                    certainty = "CERTAIN";
                    break; // assume there is one value only
                } else if (kafFactValue.getValue().startsWith("PR")) {
                    certainty = "PROBABLE";
                    break; // assume there is one value only
                } else if (kafFactValue.getValue().startsWith("PS")) {
                    certainty = "POSSIBLE";
                    break; // assume there is one value only
                }
            }
        }
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (kafFactValue.getResource().toLowerCase().endsWith("attributiontense")) {
                time = kafFactValue.getValue();
                if (time.equalsIgnoreCase("NON_FUTURE")) time = "NONFUTURE";
                break; // assume there is one value only
            }
        }
        for (int i = 0; i < factValueArrayList.size(); i++) {
            KafFactValue kafFactValue = factValueArrayList.get(i);
            if (kafFactValue.getResource().toLowerCase().endsWith("attributionpolarity")) {
                polarity = kafFactValue.getValue();
                break; // assume there is one value only
            }
            else if (kafFactValue.getResource().equalsIgnoreCase("factbank")) {
                if (kafFactValue.getValue().endsWith("-")) {
                    polarity = "NEG";
                    break; // assume there is one value only
                }
                else if (kafFactValue.getValue().endsWith("+")) {
                    polarity = "POS";
                    break; // assume there is one value only
                }
            }
        }
        if (!certainty.isEmpty() &&
                !certainty.equalsIgnoreCase("uu") &&
        !certainty.equalsIgnoreCase("UNDERSPECIFIED")) prediction.add(certainty);
        if (!time.isEmpty()&&
                !time.equalsIgnoreCase("uu") &&
                !time.equalsIgnoreCase("UNDERSPECIFIED")) prediction.add(time);
        if (!polarity.isEmpty()&&
                !polarity.equalsIgnoreCase("uu") &&
                !polarity.equalsIgnoreCase("UNDERSPECIFIED")) prediction.add(polarity);

        return prediction;
    }

    /**
     * 118189       <factVal resource="factbank" value="CT+"/>
     4354       <factVal resource="factbank" value="CT-"/>
     1791       <factVal resource="factbank" value="PR+"/>
     51       <factVal resource="factbank" value="PR-"/>
     1932       <factVal resource="factbank" value="PS+"/>
     37       <factVal resource="factbank" value="PS-"/>
     23409       <factVal resource="factbank" value="Uu"/>
     122543       <factVal resource="nwr:attributionCertainty" value="CERTAIN"/>
     941       <factVal resource="nwr:attributionCertainty" value="POSSIBLE"/>
     1838       <factVal resource="nwr:attributionCertainty" value="PROBABLE"/>
     1551       <factVal resource="nwr:attributionCertainty" value="UNCERTAIN"/>
     22890       <factVal resource="nwr:attributionCertainty" value="UNDERSPECIFIED"/>
     5831       <factVal resource="nwr:attributionPolarity" value="NEG"/>
     120977       <factVal resource="nwr:attributionPolarity" value="POS"/>
     22955       <factVal resource="nwr:attributionPolarity" value="UNDERSPECIFIED"/>
     11947       <factVal resource="nwr:attributionTense" value="FUTURE"/>
     62878       <factVal resource="nwr:attributionTense" value="NON_FUTURE"/>
     74938       <factVal resource="nwr:attributionTense" value="UNDERSPECIFIED"/>
     */
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
