package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 10/4/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafConstituencyTerminal {

    private String id;
    private ArrayList<String> spans;

    public KafConstituencyTerminal() {
        this.id = "";
        this.spans = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getSpans() {
        return spans;
    }

    public void setSpans(ArrayList<String> spans) {
        this.spans = spans;
    }

    public void addSpans(String span) {
        this.spans.add(span);
    }

    /// toNAF

    public Element toNafXML(Document xmldoc)
    {

        Element element = xmldoc.createElement("t");
        if (this.id != null)
            element.setAttribute("id", this.getId());

        Element spanElement = xmldoc.createElement("span");
        for (int i = 0; i < this.getSpans().size(); i++)
        {
            Element target = xmldoc.createElement("target");
            target.setAttribute("id", this.getSpans().get(i));
            spanElement.appendChild(target);
        }
        element.appendChild(spanElement);

        return element;
    }

}
