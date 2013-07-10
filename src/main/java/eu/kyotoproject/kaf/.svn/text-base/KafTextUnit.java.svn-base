package eu.kyotoproject.kaf;

import org.w3c.dom.Comment;
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
public class KafTextUnit {

    private String leftContext;
    private String rightContext;
    private String type;
    private int unitid;
    private String firstWord;
    private String lastWord;
/*
    String firstWordSpan;
    String lastWordSpan;
*/
    private ArrayList<String> spans;

    public KafTextUnit() {
        leftContext = "";
        rightContext= "";
        type = "";
        unitid = -1;
        this.firstWord = "";
        this.lastWord = "";
/*
        this.firstWordSpan = "";
        this.lastWordSpan = "";
*/
        spans = new ArrayList<String>();
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

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }

    public String getLastWord() {
        return lastWord;
    }

    public void setLastWord(String lastWord) {
        this.lastWord = lastWord;
    }

    public String getLeftContext() {
        return leftContext;
    }

    public void setLeftContext(String leftContext) {
        this.leftContext = leftContext;
    }

    public String getRightContext() {
        return rightContext;
    }

    public void setRightContext(String rightContext) {
        this.rightContext = rightContext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

/*
    public String getFirstWordSpan() {
        return firstWordSpan;
    }

    public void setFirstWordSpan(String firstWordSpan) {
        this.firstWordSpan = firstWordSpan;
    }

    public String getLastWordSpan() {
        return lastWordSpan;
    }

    public void setLastWordSpan(String lastWordSpan) {
        this.lastWordSpan = lastWordSpan;
    }
*/

    public String toString() {
        String str = "<!--"+leftContext+" <html_page> "+rightContext+"-->\n";
        str += "<!-- first word ="+getFirstWord()+" & "+" last word ="+lastWord+" -->\n";
        str += "<tunit type=\""+type+"\" unitid=\""+ getUnitid()+"\">\n";
        str += "\t<span>\n";
        str += "\t\t<target id=\""+spans.get(0)+"\"/>\n";
        str += "\t\t<target id=\""+spans.get(spans.size()-1)+"\"/>\n";
/*
        for (int i = 0; i < spans.size(); i++) {
            str += "\t\t<target id=\""+spans.get(i)+"\"/>\n";
        }
*/
        str += "\t</span>\n";
        str += "</tunit>\n\n";
        return str;
    }
    
    public Element toXML(Document xmldoc)
    {
    	Comment comment1 = xmldoc.createComment("<!--"+leftContext+" <html_page> "+rightContext+"-->\n");
    	Comment comment2 = xmldoc.createComment("<!-- first word ="+getFirstWord()+" & "+" last word ="+lastWord+" -->\n");
    	
  	  	Element root = xmldoc.createElement("tunit");
  	  	root.setAttribute("type", type);
  	  	root.setAttribute("unitid", Integer.toString(getUnitid()));
  	  	root.appendChild(comment1);
  	  	root.appendChild(comment2);
  	  	
  	  	Element span = xmldoc.createElement("span");
  	  	Element target1 = xmldoc.createElement("target");
  	  	target1.setAttribute("id", spans.get(0));
  	  	span.appendChild(target1);
  	  	Element target2 = xmldoc.createElement("target");
	  	target2.setAttribute("id", spans.get(spans.size()-1));
	  	span.appendChild(target2);
	  	
	  	root.appendChild(span);
  	  	return root;
    }
}
