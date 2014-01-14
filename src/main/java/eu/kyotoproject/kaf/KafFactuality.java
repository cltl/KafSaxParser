package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by kyoto on 12/4/13.
 */
public class KafFactuality {

    /**
     * <factualitylayer>
     *     <factvalue id="w4" prediction="CT+" confidence="0.5904876913248487"/>
     * <factvalue id="w681" prediction="Uu" confidence="0.5324089742552198"/>
     */

    private String id;
    private String prediction;
    private Double confidence;

    public KafFactuality() {
        this.id = "";
        this.prediction = "";
        this.confidence = 0.0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Element toNafXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("factvalue");
        if (!id.isEmpty())
            root.setAttribute("id", id);
        if (!prediction.isEmpty())
            root.setAttribute("prediction", prediction);
        if (confidence>0) {
            root.setAttribute("confidence", confidence.toString());
        }
        return root;
    }

}
