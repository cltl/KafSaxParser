package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 23-sep-2008
 * Time: 18:01:46
 * To change this template use File | Settings | File Templates.
 *
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
public class KafChunk {
    /*
    <chunk cid="cid53895.3" head="tid53895.4" phrase="S"><span>
<target tid="tid53895.11"/>
<target tid="tid53895.13"/>
<target tid="tid53895.14"/>
<target tid="tid53895.4"/>
<target tid="tid53895.15"/>
<target tid="tid53895.6"/>
<target tid="tid53895.7"/>
<target tid="tid53895.8"/>
<target tid="tid53895.17"/>
<target tid="tid53895.19"/>
<target tid="tid53895.21"/>
<target tid="tid53895.22"/></span></chunk>
     */

    public String toString() {
        String str = "<chunk cid=\""+cid+"\" head=\""+head+"\" phrase=\""+phrase+"\">\n";
        str += "<span>\n";
        for (int i = 0; i < this.spans.size(); i++) {
            str += "\t<target tid=\""+spans.get(i)+"\"/>\n";
        }
        str += "</span>\n";
        str += "</chunk>\n";
        return str;
    }

    String cid;
    String head;
    String phrase;
    ArrayList<String> spans;

    public KafChunk() {
        this.cid = "";
        this.head = "";
        this.phrase = "";
        this.spans = new ArrayList<String>();
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("chunk");
 	  root.setAttribute("cid", cid);
  	  root.setAttribute("head", head);
  	  root.setAttribute("phrase", phrase);
  	  
  	  Element span = xmldoc.createElement("span");
  	  for (int i = 0; i < this.spans.size(); i++) 
  	  {
  		  Element target = xmldoc.createElement("target");
  		  target.setAttribute("tid", spans.get(i));
  		  span.appendChild(target);
  	  }
  	  root.appendChild(span);
  	  return root;
    }
    
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
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

}
