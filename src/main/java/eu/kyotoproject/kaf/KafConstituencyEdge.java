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
    private String id;
    private String head;

    public KafConstituencyEdge() {
        this.from = "";
        this.to = "";
        this.id = "";
        this.head = "";
    }

    public KafConstituencyEdge(String from, String to) {
        this.from = from;
        this.to = to;
        this.id = "";
        this.head = "";
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    /// toNaf

    public Element toNafXML(Document xmldoc)
    {

        Element element = xmldoc.createElement("edge");
        if (!this.from.isEmpty())
            element.setAttribute("from", this.getFrom());
        if (!this.to.isEmpty())
            element.setAttribute("to", this.getTo());
        if (!this.id.isEmpty())
            element.setAttribute("id", this.getId());
        if (!this.head.isEmpty())
            element.setAttribute("head", this.getHead());
        return element;
    }

}