package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 5-jun-2009
 * Time: 13:08:30
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
public class KafReference {

    private String docId;
    private String pageId;
 //   private KafTerm term;
    
    private String cid, tid;		//added 29-09-2009, to support kafReferences in locations and dates

    public KafReference(String docId, String pageId, String tid)	//KafTerm term)
    {
    	this.docId = docId;
    	this.pageId = pageId;
    	this.tid = tid;
    }
    
    public KafReference() {
        this.docId = "";
        this.pageId = "";
        this.tid = "";
       // this.term = new KafTerm();
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

  /*  public KafTerm getTerm() {
        return term;
    }*/
    public String getTerm()
    {
    	return tid;
    }

    public void setTerm(String tid)
    {
    	this.tid = tid;
    //    this.term = term;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    
    public String toKaf()
    {
    	StringBuffer sb = new StringBuffer();
    	sb.append("\t\t\t\t<kafReference ");
    	if (docId != null)
    		sb.append("docId=\"" + docId + "\"");
    	if (pageId != null)
    		sb.append(" pageId=\"" + pageId + "\"");
		sb.append(">\n\t\t\t\t\t<span id=\"");
		sb.append(tid);
	//	if (cid != null)
	//		sb.append("\" cid=\"" + cid);
		sb.append("\" />\n\t\t\t\t</kafReference>\n");
		return sb.toString();
    }
    
    public String toString (String tab) {
        String str = tab+"<kafReference ";
        str += " docId=\""+docId+"\"";
        str += " pageId=\""+pageId+"\">\n";
        str += "\t" + tid;
   //     str += "\t"+term.toString(tab);
        str += tab+"</kafReference>\n";
        return str;
    }
    
    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("kafReference");
  	  if (docId != null)
  		  root.setAttribute("docId", docId);
  	  if (pageId != null)
  		  root.setAttribute("pageId", pageId);
  	  
  	  Element span = xmldoc.createElement("span");
  	  span.setAttribute("id", tid);
  //	  if (cid != null)
  //		  span.setAttribute("cid", cid);
 	  root.appendChild(span);
  	  return root;
    }

    public Element toSpan(Document xmldoc)
    {


      Element span = xmldoc.createElement("span");
  	  span.setAttribute("id", tid);
  	  return span;
    }
}
