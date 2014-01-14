package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 17-dec-2008
 * Time: 14:38:37
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
public class TermComponent {

    /*
    <term head="t2.12.1" lemma="cites-soort" pos="N.noun" tid="t2.12" type="open">
<span><target id="w2.12"/></span>
<component id="t2.12.0" lemma="CITES"/>
<component id="t2.12.1" lemma="soort"/>
</term>
     */
        ArrayList<KafSense> senseTags;
        private String id;
        private String lemma;
        private String pos;
        private KafTermSentiment kafTermSentiment;


    public TermComponent() {
        this.senseTags = new ArrayList<KafSense>();
        this.id = "";
        this.lemma = "";
        this.pos = "";
        this.kafTermSentiment = new KafTermSentiment();
    }


    public ArrayList<KafSense> getSenseTags() {
        return senseTags;
    }

    public void setSenseTags(ArrayList<KafSense> senseTags) {
        this.senseTags = senseTags;
    }

    public void addSenseTags(KafSense senseTag) {
        this.senseTags.add(senseTag);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setPos(String pos) {
        this.pos = pos;
    }

    public KafTermSentiment getKafTermSentiment() {
        return kafTermSentiment;
    }

    public void setKafTermSentiment(KafTermSentiment kafTermSentiment) {
        this.kafTermSentiment = kafTermSentiment;
    }

    public String toString () {
        String str = "<component";
        str += " id=\""+id+"\"";
        str += " lemma=\""+lemma+"\"";
        if (pos.length()>0) {
            str += " pos=\""+pos+"\"";
        }
        if(kafTermSentiment.hasValue()) {
            str += ">\n";
            str += kafTermSentiment.toString();
            if (senseTags.size()==0) {
                str += "</component>\n";
            }
        }
        if (senseTags.size()>0) {
            if (!kafTermSentiment.hasValue()) str += ">\n";
            str += "\t<externalReferences>\n";
            for (int i = 0; i < senseTags.size(); i++) {
                KafSense kafSense = (KafSense) senseTags.get(i);
                str +="\t"+kafSense.toString();
            }
            str += "</externalReferences>\n";
            str += "</component>\n";
        }
        else {
            str += "/>\n";
        }
        return str;
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("component");
 	  root.setAttribute("id", id);
  	  root.setAttribute("lemma", lemma);
  	  root.setAttribute("pos", pos);
      if (kafTermSentiment.hasValue()) {
            root.appendChild(kafTermSentiment.toXML(xmldoc));
      }
      if (senseTags.size()>0)
      {
        Element refs = xmldoc.createElement("externalReferences");
        for (KafSense sense : senseTags)
            refs.appendChild(sense.toXML(xmldoc));
        root.appendChild(refs);
      }
  	  return root;
    }

}
