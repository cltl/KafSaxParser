package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by piek on 03/07/15.
 */
public class KafFactValue {
    /*
          <factVal value="CT+" resource="FactBank"
               confidence="0.83"/>

     */

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
        Element root = xmldoc.createElement("factValue");
        if (!value.isEmpty()) root.setAttribute("value", value);
        if (!resource.isEmpty()) root.setAttribute("resource", resource);
        if (confidence > -1) root.setAttribute("confidence", Double.toString(confidence));
        return root;
    }
}