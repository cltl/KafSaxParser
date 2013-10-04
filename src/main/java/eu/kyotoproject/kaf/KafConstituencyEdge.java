package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 10/4/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafConstituencyEdge {

    private String from;
    private String to;

    public KafConstituencyEdge() {
        this.from = "";
        this.to = "";
    }

    public KafConstituencyEdge(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    /// toNaf

    public Element toNafXML(Document xmldoc)
    {

        Element element = xmldoc.createElement("edge");
        if (this.from != null)
            element.setAttribute("from", this.getFrom());
        if (this.to != null)
            element.setAttribute("to", this.getTo());

        return element;
    }

}