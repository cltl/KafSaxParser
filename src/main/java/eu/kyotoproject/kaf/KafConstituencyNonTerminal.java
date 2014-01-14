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
public class KafConstituencyNonTerminal {

    private String id;
    private String label;

    public KafConstituencyNonTerminal(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public KafConstituencyNonTerminal() {
        this.id = "";
        this.label = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    public Element toNafXML(Document xmldoc)
    {

        Element element = xmldoc.createElement("nt");
        if (!this.id.isEmpty())
            element.setAttribute("id", this.getId());
        if (!this.label.isEmpty())
            element.setAttribute("label", this.getLabel());

        return element;
    }
    //toNaf
}
