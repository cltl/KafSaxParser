package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: kyoto
 * Date: 10/4/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class KafConstituencyTree {

    private ArrayList<KafConstituencyTerminal> terminals;
    private ArrayList<KafConstituencyNonTerminal> nonterminals;
    private ArrayList<KafConstituencyEdge> edges;

    public KafConstituencyTree() {
        this.edges = new ArrayList<KafConstituencyEdge>();
        this.nonterminals = new ArrayList<KafConstituencyNonTerminal>();
        this.terminals = new ArrayList<KafConstituencyTerminal>();
    }

    public ArrayList<KafConstituencyEdge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<KafConstituencyEdge> edges) {
        this.edges = edges;
    }

    public void addEdges(KafConstituencyEdge edge) {
        this.edges.add(edge);
    }

    public ArrayList<KafConstituencyNonTerminal> getNonterminals() {
        return nonterminals;
    }

    public void setNonterminals(ArrayList<KafConstituencyNonTerminal> nonterminals) {
        this.nonterminals = nonterminals;
    }

    public void addNonterminals(KafConstituencyNonTerminal nonterminal) {
        this.nonterminals.add(nonterminal);
    }

    public ArrayList<KafConstituencyTerminal> getTerminals() {
        return terminals;
    }

    public void setTerminals(ArrayList<KafConstituencyTerminal> terminals) {
        this.terminals = terminals;
    }

    public void addTerminals(KafConstituencyTerminal terminal) {
        this.terminals.add(terminal);
    }

    //toNaf


    public Element toNafXML(Document xmldoc)
    {
        Element element = xmldoc.createElement("tree");

        for (int i = 0; i < terminals.size(); i++) {
            KafConstituencyTerminal kafConstituencyTerminal = terminals.get(i);
            element.appendChild(kafConstituencyTerminal.toNafXML(xmldoc));
        }

        for (int i = 0; i < nonterminals.size(); i++) {
            KafConstituencyNonTerminal kafConstituencyNonTerminal = nonterminals.get(i);
            element.appendChild(kafConstituencyNonTerminal.toNafXML(xmldoc));
        }

        for (int i = 0; i < edges.size(); i++) {
            KafConstituencyEdge kafConstituencyEdge = edges.get(i);
            element.appendChild(kafConstituencyEdge.toNafXML(xmldoc));
        }
        return element;
    }

}
