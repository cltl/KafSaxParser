package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.Serializable;

/**
 * Created by piek on 03/07/15.
 */
public class KafFactValue implements Serializable {
    /*
          <factVal value="CT+" resource="FactBank"
               confidence="0.83"/>

                    <factVal resource="nwr:attributionTense" value="NON_FUTURE"/>
     <factVal resource="factbank" value="CT+"/>
     <factVal resource="nwr:attributionCertainty" value="CERTAIN"/>
     <factVal resource="nwr:attributionPolarity" value="POS"/>

      //// OLD VERSION factValue instead of factVal
     */
    static public final String FUTURE = "FUTURE";
    static public final String NON_FUTURE = "NONFUTURE";
    static public final String RECENT = "RECENT";
    static public final String PAST = "PAST";
    static public final String CERTAIN = "CERTAIN";
    static public final String UNCERTAIN = "UNCERTAIN";
    static public final String POS = "POS";
    static public final String NEG = "NEG";
    static public final String PROBABLE = "PROBABLE";
    static public final String UNPROBABLE = "UNPROBABLE";
    static public final String resourceFactbank = "factbank";
    static public final String resourceAttributionTense = "nwr:attributionTense";
    static public final String resourceAttributionCertainty = "nwr:attributionCertainty";
    static public final String resourceAttributionPolarity = "nwr:attributionPolarity";

    private String value;
    private String resource;
    private double confidence;

    public KafFactValue() {
        this.confidence = -1;
        this.resource = "";
        this.value = "";
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Element toNafXML(Document xmldoc) {
        Element root = xmldoc.createElement("factVal");
        if (!value.isEmpty()) root.setAttribute("value", value);
        if (!resource.isEmpty()) root.setAttribute("resource", resource);
        if (confidence > -1) root.setAttribute("confidence", Double.toString(confidence));
        return root;
    }

    @Override
    public String toString() {
        return "KafFactValue{" +
                "value=" + value +
                ", resource='" + resource + '\'' +
                ", confidence='" + confidence + '\'' +
                '}';
    }
}
