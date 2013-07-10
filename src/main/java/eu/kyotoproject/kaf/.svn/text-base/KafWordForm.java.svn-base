package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
public class KafWordForm {

    /*
<wf wid="wid53895.11">Met</wf>
<wf wid="wid53895.13">groot</wf>
<wf wid="wid53895.14">materieel</wf>
<wf wid="wid53895.4">zijn</wf>
<wf wid="wid53895.15">onlangs</wf>
<wf wid="wid53895.6">8</wf>
<wf wid="wid53895.7">grote</wf>
<wf wid="wid53895.8">leilindes</wf>
<wf wid="wid53895.17">aangevoerd</wf>
 <wf wid="w5101" sent="331" para="1">policies</wf>


 <text>
  <wf wid="w1" charOffset="0" charLenght="4" sent="1" para="1">John</wf>
  <wf wid="w2" charOffset="5" charLenght="6" sent="1" para="1">taught</wf>
  <wf wid="w3" charOffset="12" charLenght="11" sent="1" para="1">mathematics</wf>
  <wf wid="w4" charOffset="24" charLenght="2" sent="1" para="1">20</wf>
  <wf wid="w5" charOffset="27" charLenght="7" sent="1" para="1">minutes</wf>
  <wf wid="w6" charOffset="35" charLenght="5" sent="1" para="1">every</wf>
  <wf wid="w7" charOffset="41" charLenght="6" sent="1" para="1">Monday</wf>
  <wf wid="w8" charOffset="48" charLenght="2" sent="1" para="1">in</wf>
  <wf wid="w9" charOffset="51" charLenght="3" sent="1" para="1">New</wf>
  <wf wid="w10" charOffset="55" charLenght="3" sent="1" para="1">York</wf>
  <wf wid="w11" charOffset="59" charLenght="1" sent="1" para="1">.</wf>
</text>

     */
    String wid;
    String wf;
    String para;
    String sent;
    String page;
    String charOffset;
    String charLength;

    public String toString() {
        String str = "<wf wid=\""+wid+"\" sent=\""+sent+"\" para=\""+para+"\"";
        if (charOffset.length()>0) {
            str += " charOffset=\""+charOffset+"\"";
        }
        if (charLength.length()>0) {
            str += " charLength=\""+charLength+"\"";
        }
        if (page.length()>0) {
          str += " page=\""+page+"\"";
        }
        str += "><![CDATA["+wf+"]]></wf>\n";
        return str;
    }
    
    public String toSimpleString() {
        String str = "<wf wid=\""+wid+"\" sent=\""+sent+"\" para=\""+para+"\"";
        if (charOffset.length()>0) {
            str += " charOffset=\""+charOffset+"\"";
        }
        if (charLength.length()>0) {
            str += " charLength=\""+charLength+"\"";
        }
        if (page.length()>0) {
          str += " page=\""+page+"\"";
        }
        str += ">"+wf+"</wf>";
        return str;
    }

    public KafWordForm() {
        this.wid = "";
        this.wf = "";
        this.para = "";
        this.sent="";
        this.page="";
        this.charOffset = "";
        this.charLength = "";
    }

    public Element toXML(Document xmldoc)
    {
  	  Element root = xmldoc.createElement("wf");
 	  root.setAttribute("wid", wid);
  	  root.setAttribute("sent", sent);
  	  if (para.length() > 0)
  		  root.setAttribute("para", para);
  	  if (page.length() > 0)
  		  root.setAttribute("page", page);
  	  if (charOffset.length() > 0)
  		  root.setAttribute("offset", charOffset);

  	  if (charLength.length() > 0)
  		  root.setAttribute("length", charLength);

  	  Node text = xmldoc.createTextNode(wf);
  	  root.appendChild(text);
  	  return root;
    }
    
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWf() {
        return wf;
    }

    public void setWf(String wf) {
        this.wf = wf;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getCharOffset() {
        return charOffset;
    }

    public void setCharOffset(String charoffset) {
        this.charOffset = charoffset;
    }

    public String getCharLength() {
        return charLength;
    }

    public void setCharLength(String charLength) {
        this.charLength = charLength;
    }
}
