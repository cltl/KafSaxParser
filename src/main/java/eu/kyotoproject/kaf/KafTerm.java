package eu.kyotoproject.kaf;

import eu.kyotoproject.util.XmlCharacterConversion;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 23-sep-2008
 * Time: 17:57:18
 * To change this template use File | Settings | File Templates.
 * This file is part of KafSaxParser.

    KafSaxParser is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    KafSaxParser is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with KafSaxParser.  If not, see <http://www.gnu.org/licenses/>.
 */
public class KafTerm {

    /*
<term tid="tid53895.2" lemma="en" pos="none" type="none"><spans>
<target wid="wid53895.2"/></spans></term>
<term tid="tid53895.4" lemma="ben" pos="V" type="open"><spans>
<target wid="wid53895.4"/></spans>
<externalReferences>
<externalRef resource="wn30g" reference="eng-30-00003553-n" confidence="0.18468"/>
<externalRef resource="wn30g" reference="eng-30-13583724-n" confidence="0.176632"/>
<externalRef resource="wn30g" reference="eng-30-08189659-n" confidence="0.165047"/>
<externalRef resource="wn30g" reference="eng-30-09465459-n" confidence="0.162495"/>
<externalRef resource="wn30g" reference="eng-30-05869857-n" confidence="0.157206"/>
<externalRef resource="wn30g" reference="eng-30-13810323-n" confidence="0.153941"/>
</externalReferences>

</term>


<term head="t2.12.1" lemma="cites-soort" pos="N.noun" tid="t2.12" type="open">
<span><target id="w2.12"/></span>
<component id="t2.12.0" lemma="CITES"/>
<component id="t2.12.1" lemma="soort"/>
</term>
     */

    /*
    <externalReferences> <externalRef resource="WN-ENG" ref="c_1009" conf="0.38">
<sentiment resource="VUA_polarityLexicon_synset" polarity="positive" strength="average" subjectivity="subjective" sentiment_semanrtic_type="behaviour/traitEvaluation" sentiment_feature="" />
</externalRef> <externalRef resource="WN-ENG" ref="c_1008" conf="0.31">
<sentiment resource="VUA_polarityLexicon_synset" polarity="positive" strength="average" subjectivity="objective"
sentiment_semanrtic_type="temperature" sentiment_product_feature=""/> </externalRef>

<term tid="t2.10" lemma="nice" pos="G.adj" type="open"> <sentiment resource="VUA_polarityLexicon_word" polarity="positive" strength="average" subjectivity="subjective"
</term>
sentiment_semanrtic_type="behaviour/trait" sentiment_product_feature="" />
     */

    String tid;
    String lemma;
    String dep;
    String pos;
    String type;
    String polarity;
    String morphofeat;
    String netype;
    ArrayList<String> spans;
    String tokenString;
    ArrayList<TermComponent> components;
    int nFreq;
    String head;
    String parent;
    String modifier;
    ArrayList<KafSense> senseTags;
    KafTermSentiment kafTermSentiment;

    public KafTerm() {
        this.tid = "";
        this.dep = "";
        this.lemma = "";
        this.morphofeat = "";
        this.pos = "";
        this.type = "";
        this.head ="";
        this.parent ="";
        this.modifier = "";
        this.polarity = "";
        netype ="";
        nFreq = 0;
        this.kafTermSentiment = new KafTermSentiment();
        this.spans = new ArrayList<String>();
        this.tokenString = "";
        this.senseTags = new ArrayList<KafSense>();
        this.components = new ArrayList<TermComponent>();
    }
    
    public KafTerm(String tid, String lemma, String pos, String type)
    {
    	this();
    	if (tid != null)
    		this.tid = tid;
    	if (lemma != null)
    		this.lemma = lemma;
    	if (pos != null)
    		this.pos = pos;
    	if (type != null)
    		this.type = type;
    }

    public String toString() {
        String str = "<term tid=\""+tid+"\" lemma=\""+XmlCharacterConversion.replaceXmlChar(lemma)+"\" pos=\""+pos+"\" type=\""+type+"\"";
        if (head.length()>0) {
            str += " head=\""+head+"\"";
        }
        if (morphofeat.length()>0) {
            str += " morphofeat=\""+morphofeat+"\"";
        }
        if (netype.length()>0) {
            str += " netype=\""+netype+"\"";
        }
        if (parent.length()>0) {
            str += " parent=\""+parent+"\"";
        }
        if (modifier.length()>0) {
            str += " modifier=\""+modifier+"\"";
        }
        if (this.polarity.length()>0) {
            str += " polarity=\""+this.polarity+"\"";
        }
        if (this.dep.length()>0) {
            str += " dep=\""+this.dep+"\"";
        }
        str += ">\n";
        if (kafTermSentiment.hasValue()) {
            str += kafTermSentiment.toString();
        }
        str += "<span>\n";
        for (int i = 0; i < this.spans.size(); i++) {
            str += "\t<target id=\""+spans.get(i)+"\"/>\n";
        }
        str += "</span>\n";
        //    <senseAlt><sense sensecode="ENG-30-07504841-n" confidence="0.185633"/><sense sensecode="ENG-30-07524529-n" confidence="0.213195"/><sense sensecode="ENG-30-05832264-n" confidence="0.213224"/><sense sensecode="ENG-30-08061042-n" confidence="0.191701"/><sense sensecode="ENG-30-05670710-n" confidence="0.196247"/></senseAlt></term>
        if (senseTags.size()>0) {
        str += "<externalReferences>\n";
            for (int i = 0; i < senseTags.size(); i++) {
                KafSense kafSense = (KafSense) senseTags.get(i);
                str += kafSense.toString();
            }
            str += "</externalReferences>\n";
        }
        str += "</term>\n";
        if (components.size()>0) {
            for (int i = 0; i < components.size(); i++) {
                TermComponent termComponent = components.get(i);
                str += termComponent.toString();
            }
        }
        return str;
    }

    public String toString(String tab) {
        String str = tab+"<term tid=\""+tid+"\" lemma=\""+ XmlCharacterConversion.replaceXmlChar(lemma)+"\" pos=\""+pos+"\" type=\""+type+"\"";
        if (netype.length()>0) {
            str += " netype=\""+netype+"\"";
        }
        if (morphofeat.length()>0) {
            str += " morphofeat=\""+morphofeat+"\"";
        }
        if (parent.length()>0) {
            str += " parent=\""+parent+"\"";
        }
        if (modifier.length()>0) {
            str += " modifier=\""+modifier+"\"";
        }
        if (this.polarity.length()>0) {
            str += " polarity=\""+this.polarity+"\"";
        }
        if (this.dep.length()>0) {
            str += " dep=\""+this.dep+"\"";
        }
        str += ">\n";
        if (kafTermSentiment.hasValue()) {
            str += tab+ kafTermSentiment.toString();
        }

        if (tokenString.length()>0) {
            str += "<!-- "+tokenString+" -->\n";
        }
        str += tab+"\t<span>\n";
        for (int i = 0; i < this.spans.size(); i++) {
            str += tab+"\t\t<target id=\""+spans.get(i)+"\"/>\n";
        }
        str += tab+"\t</span>\n";
        //    <senseAlt><sense sensecode="ENG-30-07504841-n" confidence="0.185633"/><sense sensecode="ENG-30-07524529-n" confidence="0.213195"/><sense sensecode="ENG-30-05832264-n" confidence="0.213224"/><sense sensecode="ENG-30-08061042-n" confidence="0.191701"/><sense sensecode="ENG-30-05670710-n" confidence="0.196247"/></senseAlt></term>
        if (senseTags.size()>0) {
        str += tab+"<externalReferences>\n";
            for (int i = 0; i < senseTags.size(); i++) {
                KafSense kafSense = (KafSense) senseTags.get(i);
                str += tab+"\t"+kafSense.toString();
            }
            str += tab+"</externalReferences>\n";
        }
        if (components.size()>0) {
            for (int i = 0; i < components.size(); i++) {
                TermComponent termComponent = components.get(i);
                str += termComponent.toString();
            }
        }
        str += tab+"</term>\n";
        return str;
    }

    public Element toXML(Document xmldoc)
    {
    	Element root = xmldoc.createElement("term");
    	root.setAttribute("tid", tid);
    	root.setAttribute("lemma", XmlCharacterConversion.replaceXmlChar(lemma));
    	root.setAttribute("pos", pos);
    	root.setAttribute("type", type);

        if (head.length()>0) {
            root.setAttribute("head", head);
        }
        if (morphofeat.length()>0) {
            root.setAttribute("morphofeat", morphofeat);
        }
    	if (netype.length()>0)
    		root.setAttribute("netype", netype);
    	if (parent.length()>0) 
    		root.setAttribute("parent", parent);
    	if (modifier.length()>0)
    		root.setAttribute("modifier", modifier);
        if (this.polarity.length()>0)
            root.setAttribute("polarity", polarity);
        if (this.dep.length()>0)
            root.setAttribute("dep", dep);

        if (kafTermSentiment.hasValue()) {
            Element sentiment = kafTermSentiment.toXML(xmldoc);
            root.appendChild(sentiment);
        }

        Element span = xmldoc.createElement("span");
        if (tokenString.length()>0) {
            Comment comment = xmldoc.createComment(tokenString);
            span.appendChild(comment);
        }
    	for (int i = 0; i < this.spans.size(); i++)
    	{
    		Element target = xmldoc.createElement("target");
    		target.setAttribute("id", spans.get(i));
    		span.appendChild(target);
    	}
    	root.appendChild(span);
    	
    	if (senseTags.size()>0) 
    	{
    		Element refs = xmldoc.createElement("externalReferences");
            for (KafSense sense : senseTags)
            	refs.appendChild(sense.toXML(xmldoc));
            root.appendChild(refs);
    	}
        if (components.size()>0) {
             for (TermComponent comp : components)
                 root.appendChild(comp.toXML(xmldoc));
        }

    	return root;
    }

    public Element toNafXML(Document xmldoc)
    {
    	Element root = xmldoc.createElement("term");
    	if (!tid.isEmpty()) root.setAttribute("id", tid);
    	if (!lemma.isEmpty()) root.setAttribute("lemma", XmlCharacterConversion.replaceXmlChar(lemma));
    	if (!pos.isEmpty()) root.setAttribute("pos", pos);
    	if (!type.isEmpty()) root.setAttribute("type", type);

        if (head.length()>0) {
            root.setAttribute("head", head);
        }
        if (morphofeat.length()>0) {
            root.setAttribute("morphofeat", morphofeat);
        }
    	if (netype.length()>0)
    		root.setAttribute("netype", netype);
    	if (parent.length()>0)
    		root.setAttribute("parent", parent);
    	if (modifier.length()>0)
    		root.setAttribute("modifier", modifier);
        if (this.polarity.length()>0)
            root.setAttribute("polarity", polarity);
        if (this.dep.length()>0)
            root.setAttribute("dep", dep);

        if (kafTermSentiment.hasValue()) {
            Element sentiment = kafTermSentiment.toXML(xmldoc);
            root.appendChild(sentiment);
        }

        Element span = xmldoc.createElement("span");
        if (tokenString.length()>0) {
            Comment comment = xmldoc.createComment(tokenString);
            span.appendChild(comment);
        }
    	for (int i = 0; i < this.spans.size(); i++)
    	{
    		Element target = xmldoc.createElement("target");
    		target.setAttribute("id", spans.get(i));
    		span.appendChild(target);
    	}
    	root.appendChild(span);

    	if (senseTags.size()>0)
    	{
    		Element refs = xmldoc.createElement("externalReferences");
            for (KafSense sense : senseTags)
            	refs.appendChild(sense.toXML(xmldoc));
            root.appendChild(refs);
    	}
        if (components.size()>0) {
             for (TermComponent comp : components)
                 root.appendChild(comp.toXML(xmldoc));
        }

    	return root;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public String getMorphofeat() {
        return morphofeat;
    }

    public void setMorphofeat(String morphofeat) {
        this.morphofeat = morphofeat;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public void addModifier(String modifier) {
        this.modifier += modifier+";";
    }

    public int getNFreq() {
        return nFreq;
    }

    public void setNFreq(int nFreq) {
        this.nFreq = nFreq;
    }

    public String getNetype() {
        return netype;
    }

    public void setNetype(String netype) {
        this.netype = netype;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getPos() {
        return pos;
    }

    public String getPosIni() {
        return pos.substring(0,1);
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getSpans() {
        return spans;
    }

    public String getFirstSpan() {
        if (spans.size()>0) {
           return (String) spans.get(0);
        }
        return "";
    }

    public void setSpans(ArrayList<String> spans) {
        this.spans = spans;
    }

    public void addSpans(String span) {
        this.spans.add(span);
    }

    public ArrayList<KafSense> getSenseTags() {
        return senseTags;
    }

    public void addSenseTag(KafSense sense)
    {
    	senseTags.add(sense);
    }
    
    public void setSenseTags(ArrayList<KafSense> senseTags) {
        this.senseTags = senseTags;
    }

    public String getFirstSense() {
        if (senseTags.size()>0) {
           return ((KafSense) senseTags.get(0)).getSensecode();
        }
        return "";
    }

    public ArrayList<TermComponent> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<TermComponent> components) {
        this.components = components;
    }

    public void addComponents(TermComponent component) {
        this.components.add(component);
    }

    public int getnFreq() {
        return nFreq;
    }

    public void setnFreq(int nFreq) {
        this.nFreq = nFreq;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public KafTermSentiment getKafTermSentiment() {
        return kafTermSentiment;
    }

    public void setKafTermSentiment(KafTermSentiment kafTermSentiment) {
        this.kafTermSentiment = kafTermSentiment;
    }

    public String toHtmlTableRow (int pages) {
        String str = "";
        str = "<tr>\n";
        str += "<td>"+this.getLemma()+"</td>";
//        str += "<td>"+this.netype+"</td>";
        str += "<td>"+pages+"</td>";
        str += "</tr>\n";
        return str;
    }

}
