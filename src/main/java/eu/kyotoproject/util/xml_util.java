package eu.kyotoproject.util;

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

public class xml_util {
    static String SEP = System.getProperty("file.separator");
	static boolean DEBUG = false;

////////////////// MarkUp processing functions ////////////////////////////////////////

    static public String getAttributeValue (String tag, String attribute) {
    //<VSM total_vsm_hits="1" vsm_printed_results="1" vsm_best_score="2938.49">
        String value = "";
        int idx_s = tag.indexOf(attribute);
//        System.out.println("attribute:"+attribute);
        int idx_e = 0;
        idx_s = tag.indexOf("\"", idx_s);
        idx_e = tag.indexOf("\"", idx_s+1);
        if ((idx_e>idx_s) && (idx_s>-1)) {
            value = tag.substring(idx_s+1, idx_e).trim();
//            System.out.println("value:"+value);
        }
        return value;
    }

    static public String removeNpTagsFromString (String inputString) {
        String cleanString = "";
        int idx_p = 0;
        int idx_s = inputString.indexOf("<NP ID=");
        if (idx_s>-1) {
            int idx_e = inputString.indexOf(">", idx_s);
            while ((idx_s!=-1) && (idx_e>idx_s)) {
    //             System.out.println("CONTENT:"+content);
                 cleanString += inputString.substring(idx_p, idx_s);
                 idx_p = idx_e+1;
                 idx_s = inputString.indexOf("<NP ID=", idx_p);
                 if (idx_s>-1) {
                     idx_e = inputString.indexOf(">", idx_s);
                 }
            }
            if (idx_p<inputString.length()) {
                cleanString += inputString.substring(idx_p);
            }
            String moreCleanString = "";
            idx_p = 0;
            idx_s = cleanString.indexOf("</NP>");
            if (idx_s>-1) {
                while (idx_s!=-1) {
        //             System.out.println("CONTENT:"+content);
                     moreCleanString += cleanString.substring(idx_p, idx_s);
                     idx_p = idx_s+5;
                     idx_s = cleanString.indexOf("</NP>", idx_p);
                }
                if (idx_p<cleanString.length()) {
                    moreCleanString += cleanString.substring(idx_p);
                }
                cleanString = moreCleanString;
            }
        }
        return cleanString.trim();
    }

    static public String removeTagsFromString (String inputString, String tag) {
        String cleanString = "";
        int idx_p = 0;
        int idx_s = inputString.indexOf(tag);
        while (idx_s!=-1) {
             cleanString += inputString.substring(idx_p, idx_s);
             idx_p = tag.length();
             idx_s = inputString.indexOf(tag, idx_p);
        }
        if (idx_p<inputString.length()) {
            cleanString += inputString.substring(idx_p);
        }
        return cleanString.trim();
    }

    static public String valueWithoutCDATA(String content) {
//        System.out.println("Concent of field before removing CDATA:"+content);
        String cleanData = "";
        int idx_s = content.indexOf("<![CDATA[");
        if (idx_s == -1) {
           idx_s = content.indexOf("<![cdata[");
        }
        int idx_e = content.indexOf("]]>");
        if ((idx_s!=-1) && (idx_e>idx_s)) {
//             System.out.println("CONTENT:"+content);
             cleanData += content.substring(idx_s+9, idx_e);
        }
        else {
            cleanData = content;
        }
//        System.out.println("cleanData:"+cleanData);
        return cleanData;
    }

    static public String removeCDATA(String content) {
  //      System.out.println("Content of field before removing CDATA:"+content);
        String cleanData = "";
        if (content.startsWith("<![CDATA[") &&
            content.endsWith("]]>")) {
            cleanData = content.substring(9, content.length()-3);
        }
        else {
            int idx_s = content.indexOf("<![CDATA[");
            int idx_e = 0;
            if (idx_s==-1) {
                cleanData = content;
            }
            else {
                if ((idx_s>idx_e)) {
                /// FIRST COPY ALL BEFORE "<![CDATA["
                cleanData = content.substring(idx_e, idx_s);
                }
                idx_e = content.indexOf("]]>", idx_s);
                while ((idx_s > -1) && (idx_e>idx_s)) {
     //                System.out.println("CONTENT:"+content);
                     cleanData += content.substring(idx_s+9, idx_e);
                     idx_s = content.indexOf("<![CDATA[", idx_e);
                     if (idx_s>-1) {
                        idx_e = content.indexOf("]]>", idx_s);
                     }
                }
                if (idx_e>-1) {
                    //// COPY THE REST FROM idx_e, ALSO WHEN THERE IS NO CDATA TAG;
                    cleanData += content.substring(idx_e+3).trim();
                }
            }
        }
        return cleanData;
    }


    static public  String addCDATAstring (String value) {
        String CDATAvalue = value;
        if (!value.startsWith("<![CDATA[")) {
            CDATAvalue = "<![CDATA["+value+"]]>";
        }
        return CDATAvalue.trim();
    }

    static public String getValue (String text) {
        String value = "";
        int idx_s = text.indexOf(">");
        int idx_e = text.indexOf("</");
        if (idx_e>idx_s) {
            value = text.substring(idx_s+1, idx_e);
        }
        return cleanValue(value);
    }

    static public String cleanValue (String value) {
        String cleanValue = value;
        if (value.startsWith("\"")) {
            cleanValue = value.substring(1);
        }
        if (value.endsWith("\"")) {
            cleanValue = cleanValue.substring(0, cleanValue.length()-1);
        }
        if (cleanValue.startsWith("<![CDATA[") &&
            cleanValue.endsWith("]]>")) {
            cleanValue = cleanValue.substring(9, cleanValue.length()-3);
        }
        return cleanValue.trim();
    }

    static public String getValueFirstTag (String text, String tag) {
        String id = "";
        int idx_s = text.toLowerCase().indexOf("<"+tag.toLowerCase()+">");
        int idx_e = text.toLowerCase().indexOf("</"+tag.toLowerCase()+">");
        if ((idx_s>-1) && (idx_e>idx_s)) {
            id = text.substring(idx_s+tag.length()+2, idx_e).trim();
        }
        return id;
    }

/////////////////////////////////////////////////////////////////////////////////////////
      /// Auxiliary function that tries to find the position just before the start tag
	static public int getUpToFieldStart(String inputTXT, String tag) {
         /// First look for the tag as it is.....
         int idx_sys = inputTXT.indexOf("<"+tag+">");
         if (idx_sys >0)
         {  return idx_sys-1;}
         else if (idx_sys == 0) { return 0;}
         else {
             tag = tag.toLowerCase();
             idx_sys = inputTXT.indexOf("<"+tag+">");
             if (idx_sys >0)
             {  return idx_sys-1;}
             else if (idx_sys == 0) { return 0;}
             else
             {  tag = tag.toUpperCase();
                idx_sys = inputTXT.indexOf("<"+tag+">");
                if (idx_sys >0)
                {  return idx_sys-1;}
                else if (idx_sys == 0) { return 0;}
                else {
                     tag = tag.toLowerCase();
                     tag = StringOperations.changeInitialCase(tag);
                     idx_sys = inputTXT.indexOf("<"+tag+">");
                     if (idx_sys >0)
                     {  return idx_sys-1;}
                     else if (idx_sys == 0) { return 0;}
                     else {
                         tag = tag.toLowerCase();
                         String lowerCaseText = inputTXT.toLowerCase();
                         idx_sys = lowerCaseText.indexOf("<"+tag+">");
                         if (idx_sys >0)
                         {  return idx_sys-1;}
                         else if (idx_sys == 0) { return 0;}
                         else
                         { return -1;}
                     }
                }
             }
         }
	}

      /// Auxiliary function that tries to find the position just up to the end tag
	static public int getUpToFieldEnd(String inputTXT, String tag, int pos) {
          /// First look for the tag as it is.....
          int idx_sys = inputTXT.indexOf("</"+tag+">", pos);
          if (idx_sys >0)
          {  return idx_sys-1;}
          else if (idx_sys == 0) { return 0;}
          else {
             tag = tag.toLowerCase();
             idx_sys = inputTXT.indexOf("</"+tag+">", pos);
             if (idx_sys >0)
             {  return idx_sys-1;}
             else if (idx_sys == 0) { return 0;}
             else
             {  tag = tag.toUpperCase();
                idx_sys = inputTXT.indexOf("</"+tag+">", pos);
                if (idx_sys >0)
                {  return idx_sys-1;}
                else if (idx_sys == 0) { return 0;}
                else
                {   tag = tag.toLowerCase();
                    tag = StringOperations.changeInitialCase(tag);
                    idx_sys = inputTXT.indexOf("</"+tag+">", pos);
                    if (idx_sys >0)
                    {  return idx_sys-1;}
                    else if (idx_sys == 0) { return 0;}
                    else {
                        tag = tag.toLowerCase();
                        String lowerCaseText = inputTXT.toLowerCase();
                        idx_sys = lowerCaseText.indexOf("</"+tag+">", pos);
                        if (idx_sys >0)
                        {  return idx_sys-1;}
                        else if (idx_sys == 0) { return 0;}
                        else
                        { return -1;}
                    }
                }
             }
          }
	}

      /// Auxiliary function that tries to find the position just after the end tag
	static public int getAfterFieldEnd(String inputTXT, String tag, int pos) {
          /// First look for the tag as it is.....
          int idx_sys = inputTXT.indexOf("</"+tag+">", pos);
          if (idx_sys >=0)
          { return idx_sys+tag.length()+3;}
          else {
             tag = tag.toLowerCase();
             idx_sys = inputTXT.indexOf("</"+tag+">", pos);
             if (idx_sys >=0)
             { return idx_sys+tag.length()+3;}
             else
             {  tag = tag.toUpperCase();
                idx_sys = inputTXT.indexOf("</"+tag+">", pos);
                if (idx_sys >=0)
                { return idx_sys+tag.length()+3;}
                else
                {   tag = tag.toLowerCase();
                    tag = StringOperations.changeInitialCase(tag);
                    idx_sys = inputTXT.indexOf("</"+tag+">", pos);
                    if (idx_sys >=0)
                       { return idx_sys+tag.length()+3;}
                    else {
                        tag = tag.toLowerCase();
                        String lowerCaseText = inputTXT.toLowerCase();
                        idx_sys = lowerCaseText.indexOf("</"+tag+">", pos);
                        if (idx_sys >=0)
                        { return idx_sys+tag.length()+3;}
                        else
                        { return -1;}
                    }
                }
             }
          }
	}

      /// Auxiliary function that tries to find the position just after the last end tag
	static public int getAfterLastFieldEnd(String inputTXT, String tag, int pos) {
        /// Searching for the last index of this time....
        /// First look for the precise tag, than play around with cases....
         int idx_sys = inputTXT.lastIndexOf("</"+tag+">", pos);
         if (idx_sys >=0)
         { return idx_sys+tag.length()+3;}
         else {
             tag = tag.toLowerCase();
             idx_sys = inputTXT.lastIndexOf("</"+tag+">", pos);
             if (idx_sys >=0)
             { return idx_sys+tag.length()+3;}
             else
             {  tag = tag.toUpperCase();
                idx_sys = inputTXT.lastIndexOf("</"+tag+">", pos);
                if (idx_sys >=0)
                { return idx_sys+tag.length()+3;}
                else
                {    tag = tag.toLowerCase();
                     tag = StringOperations.changeInitialCase(tag);
                     idx_sys = inputTXT.indexOf("</"+tag+">", pos);
                     if (idx_sys >=0)
                       { return idx_sys+tag.length()+3;}
                     else {
                         tag = tag.toLowerCase();
                         String lowerCaseText = inputTXT.toLowerCase();
                         idx_sys = lowerCaseText.indexOf("</"+tag+">", pos);
                         if (idx_sys >=0)
                         { return idx_sys+tag.length()+3;}
                         else
                         { return -1;}
                     }
                }
             }
         }
	}

      /// Old Auxiliary function that tries to find the position just up to the begin tag
	/// Too simplistic view on tags...
	static public String getUptoXmlField(String inputTXT, String tag) {
		 String content = "";
		 int idx_sys = inputTXT.indexOf("<"+tag+">");
		 idx_sys +=tag.length()+2; // Go to point beyond title tag...
		 if (idx_sys >0)
		 {
			 content = inputTXT.substring(0, idx_sys);
    //		   System.out.println("Tag: "+tag+" Content is:"+content);
		 }
		 return content;
	}

      /// Old Auxiliary function that tries to find the position just up to the begin tag
	/// Too simplistic view on tags...
	static public String getFromXmlField(String inputTXT, String tag) {
		 String content = "";
		 int idx_sys = inputTXT.indexOf("<"+tag+">");
		 idx_sys +=tag.length()+2; // Go to point beyond title tag...
		 content = inputTXT.substring(idx_sys);
    //		   System.out.println("Tag: "+tag+" Content is:"+content);
		 return content;
	}

	/////////////////////////////////////////////////////////////////////////////////
	/// Auxiliary function to get the content for a TAG froim a given position
	///
	/// We assume that the content starts at nPosition, next we locate the end tag
	/// (easier and more robust than finding the begin tag.
	/// We put the content into content and return it.
	/// This function does not yet take care of nested tags, e.g.
	/// <tag> <tag> </tag> </tag>
	///
	static public String getTagContentFromPosition_old (String inputTXT, String tag, int nPosition) {
		 String content = "";
		 String tagEnd = "</"+tag.toLowerCase()+">";
		 int idx_tagEnd = inputTXT.indexOf(tagEnd, nPosition);
//		 System.out.println("nPosition:"+nPosition);
//		 System.out.println("idx_tagEnd:"+idx_tagEnd);
		 if (idx_tagEnd == -1)
		 { tagEnd = "</"+tag.toUpperCase()+">";
		   idx_tagEnd = inputTXT.indexOf(tagEnd, nPosition);
		 }
		 if (idx_tagEnd>nPosition)
		 {   content = inputTXT.substring(nPosition, idx_tagEnd); }
//    	   System.out.println("Content is:" + content);
		 return content;
	}

	// New version uses more sophisticated getTagContentFromPosition that can handle mixed cases...
  	static public String getTagContentFromPosition (String org_inputTXT, String tag, int nPosition) {
		 String content = "";
	     if (nPosition>org_inputTXT.length()) { return content;}
	     String inputTXT = org_inputTXT.substring(nPosition);
		 content = getTagContentFromPosition(inputTXT, tag);
		 return content;
	}

	/// Does not work for mixed cases......
	/// Simple function that does not expect nesting....
	static public String getTagContentFromPosition_old (String inputTXT, String tag) {
		 String content = "";
		 String tagStart = "<"+tag.toLowerCase()+">";
		 int idx_tagStart = inputTXT.indexOf(tagStart);
//		 System.out.println(tagStart);
		 if (idx_tagStart == -1)
		 { tagStart = "<"+tag.toUpperCase()+">";
		   idx_tagStart = inputTXT.indexOf(tagStart);
//		   System.out.println(tagStart);
		 }
		 if (idx_tagStart == -1)
		 {  tagStart = "<"+tag.toLowerCase()+" ";
		    idx_tagStart = inputTXT.indexOf(tagStart);
//		    System.out.println(tagStart);
		 }
		 if (idx_tagStart == -1)
		 { tagStart = "<"+tag.toUpperCase()+" ";
		   idx_tagStart = inputTXT.indexOf(tagStart);
//		   System.out.println(tagStart);
		 }
		 if (idx_tagStart == -1)
		 {  tagStart = "<"+tag.toLowerCase()+"\t";
		    idx_tagStart = inputTXT.indexOf(tagStart);
//		    System.out.println(tagStart);
		 }
		 if (idx_tagStart == -1)
		 { tagStart = "<"+tag.toUpperCase()+"\t";
		   idx_tagStart = inputTXT.indexOf(tagStart);
//		   System.out.println(tagStart);
		 }
		 if (idx_tagStart != -1)
		 { int idx_contentStart = inputTXT.indexOf(">", idx_tagStart);
		   if (idx_contentStart!= -1) {
		     String tagEnd = "</"+tag.toLowerCase()+">";
		     int idx_tagEnd = inputTXT.indexOf(tagEnd, idx_contentStart);
		     if (idx_tagEnd == -1)
		     { tagEnd = "</"+tag.toUpperCase()+">";
			 idx_tagEnd = inputTXT.indexOf(tagEnd, idx_contentStart);
		     }
		     if (idx_tagEnd!=-1)
		     {   content = inputTXT.substring(idx_contentStart+1, idx_tagEnd); }
		   }
		 }
	 //      System.out.println("Content is:" + content);
		 return content;
	}

    //// Does work for mixed cases....
	/// Simple function that does not expect nesting....
	static public String getTagContentFromPosition (String inputTXT, String tag) {
        if (inputTXT.indexOf("SOURCE_DOC_URL")>-1) {
            DEBUG = false;
        }
       //String inputTXT = removeCDATA (text);
	   if (DEBUG) System.out.println("util.getTagContentFromPosition");
       if (DEBUG) System.out.println("inputTXT = " + inputTXT);
	     String content = "";
         String inputLowCase = inputTXT.toLowerCase();
         String tagStart = "<"+tag.toLowerCase();
         String tagEnd = "</"+tag.toLowerCase()+">";

         int idx_tagStart = inputLowCase.indexOf(tagStart);
         if (idx_tagStart == -1) {
//            System.out.println("No start"); return content;
         }
        else {
             int idx_tagEnd = inputLowCase.indexOf(tagEnd, idx_tagStart);
             if (idx_tagEnd <= idx_tagStart) {
    //            System.out.println("No end"); return content;

             }
             else {
                 // Check if the next character marks the end of the tag name...
                 // Either ">", space or tab.
                 int idxNext = idx_tagStart+tag.length()+1;
                 String tagStartNext = inputTXT.substring(idxNext,idxNext+1);
                 if (DEBUG) System.out.println("tagStartNext:"+tagStartNext);
                 if ((tagStartNext.equals(">")) ||
                     (tagStartNext.equals(" ")) ||
                     (tagStartNext.equals("\t")))
                 { // now we have a proper tag...
                   // we look for the next ">" after which the content starts
                     int idx_contentStart = inputTXT.indexOf(">", idx_tagStart);
                     if (idx_contentStart > 0) {
                        // we copy the content from the original because the case was ruined...
                        content = inputTXT.substring(idx_contentStart+1, idx_tagEnd);
                     }
                     else {
                         System.out.println("idx_contentStart"+idx_contentStart);
                     }
                }
            }
         }
        if (DEBUG) System.out.println("Content is:" + content);
        if (content.length()>0) {
            content = removeCDATA (content);
        }
	    if (DEBUG) System.out.println("Final Content is:" + content);
        DEBUG = false;
        return content;
	}

    static public String getTagAttributeValue (String inputTXT, String tag, String attribute) {
         String content = "";
         String inputLowCase = inputTXT.toLowerCase();
         String tagStart = "<"+tag.toLowerCase()+" ";
         int idx_tagStart = inputLowCase.indexOf(tagStart);
         if (idx_tagStart == -1) {
             tagStart = "<"+tag.toLowerCase()+"\t";
             idx_tagStart = inputLowCase.indexOf(tagStart);
             if (idx_tagStart == -1) {
               System.out.println("No start"); return content;
             }
         }
         String tagAttribute = attribute.toLowerCase();
         int idx_tagAttr = inputLowCase.indexOf(tagAttribute, idx_tagStart);
         if (idx_tagAttr == -1) {
            System.out.println("No attribute"); return content;
         }
         int idx_tagAttrValue = inputLowCase.indexOf("\"", idx_tagAttr);
         if (idx_tagAttrValue == -1) {
           System.out.println("No value start"); return content;
         }
         int idx_tagAttrValueEnd = inputLowCase.indexOf("\"", idx_tagAttr);
         if (idx_tagAttrValueEnd == -1) {
           System.out.println("No value end"); return content;
         }
         content = inputTXT.substring(idx_tagAttrValue+1,idx_tagAttrValueEnd);
         return content;
    }

	  static public String getNextTagField (String inputTXT, int nFrom) {
		 String content = "";
		 int idx_tagStart = nFrom;
		 int idx_tagEnd = inputTXT.indexOf(">", idx_tagStart);
		 if (idx_tagEnd > idx_tagStart){
			 content = inputTXT.substring(idx_tagStart, idx_tagEnd+1);
		 }
    //	   System.out.println("Extracted the next tagField:"+content);
		 return content;
	}

	static public String getTagFromTagField (String TagField) {
	// Field tag is either terminated by a space or by a >
		 String tag = "";
		 int idx_tagStart = 1;
		 int idx_tagEnd = TagField.indexOf(" ", idx_tagStart);
		 if (idx_tagEnd == -1){
		    idx_tagEnd = TagField.indexOf(">", idx_tagStart);
		 }
		 if (idx_tagEnd > idx_tagStart){
			 tag = TagField.substring(idx_tagStart, idx_tagEnd);
		 }
    //	   System.out.println("Extracted the tag:"+tag);
		 return tag;
	}

	// Purpose: look for the first tag position from nTagPosition
	// Skips end tags: </tag>, there could be multiple sequential end tags
	// Return -1 if no tag found
	static public int getNextTagPosition(String inputTXT, int nTagPosition) {
		 int pos = 0;
    //	   System.out.println("Org inputTXT:"+inputTXT);
		 pos = inputTXT.indexOf("<", nTagPosition);
    //	   System.out.println("Tag pos is:"+pos);
		 while ((pos < inputTXT.length()) && (pos !=-1))
		 {   if ((inputTXT.charAt(pos+1)=='/'))
		     {   // in that case we found an endTag: </tag>, go to the next
    //	           System.out.println("End Tag pos is:"+pos);
			   pos = inputTXT.indexOf("<", pos+2);
		     }
		     else // we found the position....
		     { //  System.out.println("Begin Tag pos is:"+pos);
			   return pos;
		     }
		 }
		 if (pos == inputTXT.length())
		 { // no tag found return zero
		   pos = -1;
		 }
		 return pos;
	}

	static int chooseFirst (int pos1, int pos2, int pos3, int pos4, int pos5) {
    //    System.out.println(pos1+":"+pos2+":"+pos3+":"+pos4+":"+pos5);
		 int pos = pos1;
         if (((pos==-1) || (pos > pos2)) && (pos2!=-1))
		 {  pos = pos2; }
		 if (((pos==-1) || (pos > pos3)) && (pos3!=-1))
		 {  pos = pos3; }
		 if (((pos==-1) || (pos > pos4)) && (pos4!=-1))
		 {  pos = pos4; }
         if (((pos==-1) || (pos > pos5)) && (pos5!=-1))
         {  pos = pos5; }
		 return pos;
	}

    static int chooseFirst (int pos1, int pos2, int pos3, int pos4) {
    //    System.out.println(pos1+":"+pos2+":"+pos3+":"+pos4+":"+pos5);
         int pos = pos1;
         if (((pos==-1) || (pos > pos2)) && (pos2!=-1))
         {  pos = pos2; }
         if (((pos==-1) || (pos > pos3)) && (pos3!=-1))
         {  pos = pos3; }
         if (((pos==-1) || (pos > pos4)) && (pos4!=-1))
         {  pos = pos4; }
         return pos;
    }

	// Purpose: look for the first tag position from nTagPosition
	// Skips end tags: </tag>, there could be multiple sequential end tags
	// Return zero if no tag found
	static public int getNextTagPosition(String inputTXT, String aTag, int nTagPosition) {
      //   String inputLowCase = inputTXT.toLowerCase();
		 int pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0, pos = 0;
		 // we first check the tag as it is...
//         System.out.println("inputTXT"+inputTXT);
//         System.out.println("aTag:#"+aTag+"#, search from pos:"+nTagPosition);
		 pos1 = inputTXT.indexOf("<"+aTag+">", nTagPosition);
		 pos2 = inputTXT.indexOf("<"+aTag+" ", nTagPosition);
		 pos3 = inputTXT.indexOf("<"+aTag+"\t", nTagPosition);
		 pos4 = inputTXT.indexOf("<"+aTag+"<", nTagPosition);
        // pos5 = inputTXT.indexOf("<"+aTag, nTagPosition);
        // pos5 matches tags which are substrings of longer tags!!!!!
//        pos = chooseFirst(pos1, pos2, pos3, pos4, pos5);
        pos = chooseFirst(pos1, pos2, pos3, pos4);
//        System.out.println(inputTXT.indexOf(aTag));
//        System.out.println(inputTXT.indexOf("<"+aTag));
//        System.out.println(inputTXT.indexOf("<"+aTag+" "));
//         System.out.println("pos org case:"+pos);
		 if (pos == -1) {
		    String inputLowCase = inputTXT.toLowerCase();
		    String tagLowCase = aTag.toLowerCase();
		    pos1 = inputLowCase.indexOf("<"+tagLowCase+">", nTagPosition);
		    pos2 = inputLowCase.indexOf("<"+tagLowCase+" ", nTagPosition);
		    pos3 = inputLowCase.indexOf("<"+tagLowCase+"\t", nTagPosition);
		    pos4 = inputLowCase.indexOf("<"+tagLowCase+"<", nTagPosition);
           // pos5 = inputLowCase.indexOf("<"+tagLowCase, nTagPosition);
             // pos5 matches tags which are substrings of longer tags!!!!!
//             pos = chooseFirst(pos1, pos2, pos3, pos4, pos5);
             pos = chooseFirst(pos1, pos2, pos3, pos4);
//            System.out.println("pos small case:"+pos);
		 }
         else {
//             System.out.println("Found it");
         }
		 return pos;
	}

	// Purpose: look for the first tag position from nTagPosition
	// Skips end tags: </tag>, there could be multiple sequential end tags
	// Return zero if no tag found
	static public int getNextTagPosition_old(String inputTXT, String aTag, int nTagPosition) {
		 int pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0, pos = 0;
		 String Tag = "";
		 // we first check the tag as it is...
		 pos1 = inputTXT.indexOf("<"+aTag+">", nTagPosition);
		 // check lower case version
		 if (pos1 == -1)
		 { Tag = aTag.toLowerCase();
		   pos1 = inputTXT.indexOf("<"+Tag+">", nTagPosition);
		 }
		 // check upper case version...
		 if (pos1 == -1)
		 { Tag = aTag.toUpperCase();
		   pos1 = inputTXT.indexOf("<"+Tag+">", nTagPosition);
		 }
		 pos2 = inputTXT.indexOf("<"+aTag+" ", nTagPosition);
		 // check lower case version
		 if (pos2 == -1)
		 { Tag = aTag.toLowerCase();
		   pos2 = inputTXT.indexOf("<"+Tag+" ", nTagPosition);
		 }
		 // check upper case version...
		 if (pos2 == -1)
		 { Tag = aTag.toUpperCase();
		   pos2 = inputTXT.indexOf("<"+Tag+" ", nTagPosition);
		 }

		 pos3 = inputTXT.indexOf("<"+aTag+"\t", nTagPosition);
		 // check lower case version
		 if (pos3 == -1)
		 { Tag = aTag.toLowerCase();
		   pos3 = inputTXT.indexOf("<"+Tag+"\t", nTagPosition);
		 }
		 // check upper case version...
		 if (pos3 == -1)
		 { Tag = aTag.toUpperCase();
		   pos3 = inputTXT.indexOf("<"+Tag+"\t", nTagPosition);
		 }

		 pos4 = inputTXT.indexOf("<"+aTag+"<", nTagPosition);
		 // check lower case version
		 if (pos4 == -1)
		 { Tag = aTag.toLowerCase();
		   pos4 = inputTXT.indexOf("<"+Tag+"<", nTagPosition);
		 }
		 // check upper case version...
		 if (pos4 == -1)
		 { Tag = aTag.toUpperCase();
		   pos4 = inputTXT.indexOf("<"+Tag+"<", nTagPosition);
		 }

//    	   System.out.println(pos1+";"+pos2+";"+pos3+";"+pos4);
		 pos = chooseFirst(pos1, pos2, pos3, pos4, -1);
//   	   System.out.println("Tag "+Tag+" pos is:"+pos);
		 return pos;
	}

    public static String addNewLinesToFields1 (String value) {
        String newValue ="";
        int idx_s =0;
        int idx_ts = value.indexOf("<");
        int idx_tc = -1;
        int idx_te = -1;
        if (idx_ts>-1) {
            idx_te = value.indexOf("</",idx_ts);
            idx_tc = value.indexOf("<![CDATA[",idx_ts);
            if ((idx_tc>-1) && (idx_tc<idx_te)) {
                idx_ts = value.indexOf("]]>",idx_tc);
                if (idx_ts > -1) {
                    idx_te = value.indexOf("</",idx_ts);
                }
                else {
                    idx_te = -1;
                }
            }
        }
//        System.out.println("before loop");
        while (idx_te!=-1) {
            idx_te = value.indexOf(">",idx_te);
            newValue += value.substring(idx_s,idx_te+1);
            newValue += "\n";
//            System.out.println(newValue);
            idx_s = idx_te+1;
            if (idx_s<value.length()) {
                idx_ts = value.indexOf("<",idx_te);
                if (idx_ts>-1) {
                    idx_te = value.indexOf("</",idx_ts);
                    idx_tc = value.indexOf("<![CDATA[",idx_ts);
                    if ((idx_tc>-1) && (idx_tc<idx_te)) {
                        idx_ts = value.indexOf("]]>",idx_tc);
                        if (idx_ts > -1) {
                            idx_te = value.indexOf("</",idx_ts);
                        }
                        else {
                            idx_te = -1;
                        }
                    }
                }
                else {
                    idx_te =-1;
                }
            }
            else {
      //          break;
            }
        }
        if (idx_s<value.length()) {
            newValue +=  value.substring(idx_s);
            newValue += "\n";
        }
        return newValue;
    }

    public static String addNewLinesToFields2 (String value) {
        String newValue ="";
        int idx_s =0;
        int idx_ts = value.indexOf("</");
        int idx_te = -1;
        if (idx_ts>-1) {
            idx_te = value.indexOf(">",idx_ts);
        }

        while (idx_te!=-1) {
            newValue += value.substring(idx_s,idx_te+1);
            newValue += "\n";
  //          System.out.println(newValue);
            idx_s = idx_te+1;
            if (idx_s<value.length()) {
                idx_ts = value.indexOf("</",idx_s);
                if (idx_ts>-1) {
                    idx_te = value.indexOf(">",idx_ts);
                }
                else {
                    idx_te =-1;
                }
            }
            else {
                break;
            }
        }
        if (idx_s<value.length()) {
            newValue +=  value.substring(idx_s);
            newValue += "\n";
        }
        return newValue;
    }

    public static String addNewLinesToFields3 (String value) {
        String newValue ="";
        int idx_te = value.indexOf("><");
        int idx_ts = 0;
        while (idx_te!=-1) {
            newValue += value.substring(idx_ts,idx_te+1);
            newValue += "\n";
            idx_ts = idx_te+1;
            idx_te = value.indexOf("><", idx_ts);
//            System.out.println(newValue);
        }
        if (idx_ts<value.length()) {
            newValue +=  value.substring(idx_ts);
            newValue += "\n";
        }
        return newValue;
    }
}  /// End of xml_util

