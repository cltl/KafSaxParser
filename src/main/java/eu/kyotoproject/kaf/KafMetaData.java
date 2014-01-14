package eu.kyotoproject.kaf;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 5-okt-2009
 * Time: 8:19:54
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
public class KafMetaData {

    private String version;
    private String title;
    private String author;
    private String filename;
    private String filetype;
    private String docId;
    private String publicId;
    private String url;
    private int nPages;
    private String project;
    private String collectionId;
    private String language;
    private String creationtime;
    private String dateString;
    private long filesize;
    private String pagefolderpath;
    private String metakey;
    
    //capturing deviant kafheaders
    private Map<String,List<LP>> linguisticProcessors = new HashMap<String,List<LP>>();
//    private String layer;
//    private List<LP> lps = new ArrayList<LP>();

    static final String WEBSITE = "website";
    static final String PDF = "pdf";
    static final String WORD = "word";
    static final String HTML = "html";
    
  public KafMetaData() {
      this.version = "";
      this.title = "";
      this.author = "";
      this.filename = "";
      this.filetype = "";
      this.docId = "";
      this.publicId = "";
      this.url = "";
      this.project = "";
      this.collectionId = "";
      this.language = "";
      this.nPages = 0;
      this.filesize = 0;
      this.metakey = "";
      creationtime = "";
      dateString = "";
      pagefolderpath = "";
  }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getMetakey() {
        return metakey;
    }

    public void setMetakey(String metakey) {
        this.metakey = metakey;
    }

    public String getPagefolderpath() {
        return pagefolderpath;
    }

    public void setPagefolderpath(String pagefolderpath) {
        this.pagefolderpath = pagefolderpath;
    }

    public String getTitle() {
      return title;
  }

  public void setTitle(String title) {
      this.title = title;
  }

  public String getAuthor() {
      return author;
  }

  public void setAuthor(String author) {
      this.author = author;
  }

  public String getFilename() {
      return filename;
  }

  public void setFilename(String filename) {
      this.filename = filename;
  }

  public String getFiletype() {
      return filetype;
  }

  public void setFiletype(String filetype) {
      this.filetype = filetype;
  }

  public String getPublicId() {
      return publicId;
  }

  public void setPublicId(String publicId) {
      this.publicId = publicId;
  }

  public String getUrl() {
      return url;
  }

  public void setUrl(String url) {
      this.url = url;
  }

  public int getNPages() {
      return nPages;
  }

  public void setNPages(int nPages) {
      this.nPages = nPages;
  }

  public String getProject() {
      return project;
  }

  public void setProject(String project) {
      this.project = project;
  }

  public String getCollectionId() {
      return collectionId;
  }

  public void setCollectionId(String collectionId) {
      this.collectionId = collectionId;
  }

  public String getLanguage() {
      return language;
  }

  public void setLanguage(String language) {
      this.language = language;
  }

  public String getDateString() {
      return dateString;
  }

  public void setDateString(String dateString) {
      this.dateString = dateString;
  }

    public String getCreationtime() {
        return creationtime;
    }

    public void setCreationtime(String creationtime) {
        this.creationtime = creationtime;
    }

    public long getFilesize() {
      return filesize;
  }

  public void setFilesize(long filesize) {
      this.filesize = filesize;
  }


    public String toTableString () {
      String str = "";
      str += "meta:title\t"+title+"\n";
      str += "meta:author\t"+author+"\n";
      str += "meta:filename\t"+filename+"\n";
      str += "meta:filesize\t"+filesize+"\n";
      str += "meta:filetype\t"+filetype+"\n";
      str += "meta:nPages\t"+nPages+"\n";
      str += "meta:metakey\t"+metakey+"\n";
      str += "meta:project\t"+project+"\n";
      str += "meta:collectionId\t"+collectionId+"\n";
      str += "meta:docId\t"+docId+"\n";
      str += "meta:publicId\t"+publicId+"\n";
      str += "meta:url\t"+url+"\n";
      str += "meta:dateString\t"+dateString+"\n";
      return str;
  }

  public String toKafHeaderString () 
  {
	  // JJ changed to StringBuffer 
	  // & escaped in filename as well
	  StringBuffer sb = new StringBuffer();
	  sb.append("<kafHeader>\n");
      sb.append("  <fileDesc title=\""+title+"\" author=\""+author+"\" filename=\""+filename.replaceAll("&", "&amp;")+"\" filesize=\""+filesize+"\" filetype=\""+filetype+"\" pages=\""+nPages+"\" metakey=\""+ metakey +"\"/>\n");
      sb.append("  <public project=\""+project+"\" collectionid=\""+ collectionId +"\" docid=\""+docId+"\" dmsid=\""+publicId+"\" uri=\""+url.replaceAll("&", "&amp;")+"\" />\n");
      sb.append("  <captureDesc dateString=\""+ dateString+"\""+"/>\n");
      sb.append("</kafHeader>\n");
      return sb.toString();
  }


  public String toKafStartElementString() {
        String sb = "<KAF ";
        if (!this.getLanguage().isEmpty()) {
            sb += " xml:lang=\""+this.getLanguage()+"\"";
        }
        if (!this.getDocId().isEmpty()) {
            sb += " doc=\""+this.getDocId()+"\"";
        }
        if (!this.getVersion().isEmpty()) {
            sb += " version=\""+this.getVersion()+"\"";
        }
        sb +=">\n";

        return sb;
  }

  public Element toHeaderXML(Document xmldoc)
  {
	  Element root = xmldoc.createElement("kafHeader");
	  Element fileDesc = xmldoc.createElement("fileDesc");
      if ((creationtime != null) && (creationtime.length() > 0))
          fileDesc.setAttribute("creationtime", creationtime);
      if ((title != null) && (title.length() > 0))
		  fileDesc.setAttribute("title", title);
	  if ((author != null) && (author.length() > 0))
		  fileDesc.setAttribute("author", author);
	  if ((filename != null) && (filename.length() > 0))
		  fileDesc.setAttribute("filename", filename);
	  if (filesize > 0)
		  fileDesc.setAttribute("filesize", Long.toString(filesize));
	  if ((filetype != null) && (filetype.length() > 0))
		  fileDesc.setAttribute("filetype", filetype);
	  if (nPages > 0)
		  fileDesc.setAttribute("pages", Integer.toString(nPages));
	  if ((metakey != null) && (metakey.length() > 0))
		  fileDesc.setAttribute("metakey", metakey);
	  if (fileDesc.hasAttributes())		//only add the element if it had any attributes
		  root.appendChild(fileDesc);
	  
	  Element p = xmldoc.createElement("public");
	  if ((project != null) && (project.length() > 0))
		  p.setAttribute("project", project);
	  if ((collectionId != null) && (collectionId.length() > 0))
		  p.setAttribute("collectionid", collectionId);
	//  if ((docId != null) && (docId.length() > 0))
	//	  p.setAttribute("docid", docId);
	  if ((publicId != null) && (publicId.length() > 0))
		  p.setAttribute("dmsid", publicId);
	  if ((url != null) && (url.length() > 0))
		  p.setAttribute("uri", url);
		//  p.setAttribute("uri", url.replaceAll("&", "&amp;"));
	  if (p.hasAttributes())		//only add the element if it had any attributes
		  root.appendChild(p);
	  
	  Element capture = xmldoc.createElement("captureDesc");
	  if ((dateString != null) && (dateString.length() > 0))
		  capture.setAttribute("dateString", dateString);
	  if (capture.hasAttributes())		//only add the element if it had any attributes
		  root.appendChild(capture);
	  
	  for (String layer : linguisticProcessors.keySet())
	  {
		  Element lingEl = xmldoc.createElement("linguisticProcessors");
		  lingEl.setAttribute("layer", layer);
		  for (LP lp : linguisticProcessors.get(layer))
			  lingEl.appendChild(lp.toXML(xmldoc));
		  root.appendChild(lingEl);
	  }
	  return root;
  }
  
  /*    "<kafHeader>\n"+
  "  <fileDesc title=\"3_3012\" author=\"WWF\" filename=\"KYOTO_3_3012\" \n"+
  "            filetype=\"PDF\" pages=\"19\"/>\n"+
  "  <public publicId=\"3_3012\" uri=\"http://kyoto.org/docs/KYOTO_3_3012.pdf\" />\n"+
  "  <linguisticProcessors layer=\"text\">\n"+
  "    <lp name=\"Freeling\" version=\"2.1\" timestamp=\"2009-06-25T10:05:00Z\"/>\n"+
  "  </linguisticProcessors>\n"+
  "  <linguisticProcessors layer=\"terms\">\n"+
  "    <lp name=\"Freeling\" version=\"2.1\" timestamp=\"2009-06-25T10:10:19Z\"/>\n"+
  "    <lp name=\"ukb\" version=\"0.1.2\" timestamp=\"2009-06-25T16:10:19Z\"/>\n"+
  "  </linguisticProcessors>\n"+
  "  <linguisticProcessors layer=\"namedEntities\">\n"+
  "    <lp name=\"kybot_NE\" version=\"0.1\" timestamp=\"2009-06-26T00:10:19Z\"/>\n"+
  "  </linguisticProcessors>\n"+
  "</kafHeader>"*/

/*
    //// IRION specific
    public String toDocIdxString () {
      ///234#myFileName#PDF#http://mysite/myFileName.pdf#2009-12-01
      String str = publicId+"#"+filename+"#"+filetype+"#"+url+"#"+ dateString+"\n";
      return str;
  }

*/



    public Element toNafHeaderXML(Document xmldoc)
    {
        Element root = xmldoc.createElement("nafHeader");
        Element fileDesc = xmldoc.createElement("fileDesc");
        if ((creationtime != null) && (creationtime.length() > 0))
            fileDesc.setAttribute("creationtime", creationtime);
        if ((title != null) && (title.length() > 0))
            fileDesc.setAttribute("title", title);
        if ((author != null) && (author.length() > 0))
            fileDesc.setAttribute("author", author);
        if ((filename != null) && (filename.length() > 0))
            fileDesc.setAttribute("filename", filename);
        if (filesize > 0)
            fileDesc.setAttribute("filesize", Long.toString(filesize));
        if ((filetype != null) && (filetype.length() > 0))
            fileDesc.setAttribute("filetype", filetype);
        if (nPages > 0)
            fileDesc.setAttribute("pages", Integer.toString(nPages));
        if ((metakey != null) && (metakey.length() > 0))
            fileDesc.setAttribute("metakey", metakey);
        if (fileDesc.hasAttributes())		//only add the element if it had any attributes
            root.appendChild(fileDesc);

        Element p = xmldoc.createElement("public");
        if ((project != null) && (project.length() > 0))
            p.setAttribute("project", project);
        if ((collectionId != null) && (collectionId.length() > 0))
            p.setAttribute("collectionid", collectionId);
        //  if ((docId != null) && (docId.length() > 0))
        //	  p.setAttribute("docid", docId);
        if ((publicId != null) && (publicId.length() > 0))
            p.setAttribute("publicId", publicId);
        if ((url != null) && (url.length() > 0))
            p.setAttribute("uri", url);
        //  p.setAttribute("uri", url.replaceAll("&", "&amp;"));
        if (p.hasAttributes())		//only add the element if it had any attributes
            root.appendChild(p);

        Element capture = xmldoc.createElement("captureDesc");
        if ((dateString != null) && (dateString.length() > 0))
            capture.setAttribute("dateString", dateString);
        if (capture.hasAttributes())		//only add the element if it had any attributes
            root.appendChild(capture);

        for (String layer : linguisticProcessors.keySet())
        {
            Element lingEl = xmldoc.createElement("linguisticProcessors");
            lingEl.setAttribute("layer", layer);
            for (LP lp : linguisticProcessors.get(layer))
                lingEl.appendChild(lp.toXML(xmldoc));
            root.appendChild(lingEl);
        }
        return root;
    }

    public String toString () {
          String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n";
          if (this.getLanguage().length()>0) {
              str += "<KAF xml:lang=\""+this.getLanguage()+"\" version=\""+version+"\">\n";
          }
          else {
              str += "<KAF>\n";
          }
          str += this.toKafHeaderString();
          str += "</KAF>\n";
          return str;
  }
    
    public void addLayer(String layer, String name, String version, String timestamp)
    {
    	LP lp = new LP(name,version, timestamp);
    	if (linguisticProcessors.containsKey(layer))
    		linguisticProcessors.get(layer).add(lp);
    	else
    	{
    		List<LP> list = new ArrayList<LP>();
    		list.add(lp);
    		linguisticProcessors.put(layer, list);
    	}
    }

    private class LP
    {
    	String name, version, timestamp;
    	
    	public LP(String name, String version, String timestamp)
    	{
    		this.name = name;
    		this.version = version;
    		this.timestamp = timestamp;
    	}
    	
    	public Element toXML(Document xmldoc)
    	{
    		Element lp = xmldoc.createElement("lp");
    		if (name != null)
    			lp.setAttribute("name", name);
    		if (version != null)
    			lp.setAttribute("version", version);
    		if (timestamp != null)
    			lp.setAttribute("timestamp", timestamp);
    		return lp;
    	}
    }
}
