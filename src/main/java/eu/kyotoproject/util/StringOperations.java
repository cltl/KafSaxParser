package eu.kyotoproject.util;

import java.util.ArrayList;
import java.util.List;

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
public class StringOperations 
{
    static public int latestWordMatch (List<String> b1, List<String> b2) {
        int lastMatch = 0;
        for (int i = 0; i < b1.size(); i++) {
            String s = (String) b1.get(i);
            for (int j = b2.size()-1; j < 0; j--) {
                String s1 = (String) b2.get(j);
                if (s1.equals(s))
                	lastMatch = i;
            }
        }
        return lastMatch;
    }

    static public boolean checkStringNumber(String number, boolean integer) {
      if (number.trim().length()==0)
          return false;
      
      try
      {
    	  if (integer)
    		  new Integer(number);
    	  else
    		  new Double(number);
    	  return true;
      }
      catch(NumberFormatException e)
      {
    	  return false;
      }
	}

    static public int firstDigit(String number, int pos) 
    {
    	for (int i = pos; i<number.length();i++)
    		if (Character.isDigit(number.charAt(i)))
    			return i;
    	return -1;
    }

    static public int firstNonNumber(String number, int pos) 
    {
     	for (int i = pos; i<number.length();i++)
     	{
     		char c = number.charAt(i);
    		if (Character.isDigit(c) || (c == '.') || (c == ','))
    			return i;
     	}
        return number.length();
     }

    static public ArrayList<String> stringToLowCaseArrayList(String theString) 
    {
        ArrayList<String> theList = new ArrayList<String>();
        String nextElement = "";
        int idx_s = 0;
        int idx_e = theString.indexOf(" ");
        while (idx_e>-1) {
            nextElement = cleanWord(theString.substring(idx_s, idx_e).trim());
            if (nextElement.length()>0) {
                theList.add(nextElement.toLowerCase());
            }
            idx_s = idx_e+1;
            idx_e = theString.indexOf(" ", idx_s);
        }
        if (idx_s<theString.length()) {
            nextElement = cleanWord(theString.substring(idx_s).trim());
            if (nextElement.length()>0) 
                theList.add(nextElement.toLowerCase());
        }
        return theList;
    }

    static public String changeInitialCase(String text) {
	/*	 String initial = text;
		 if (initial.length()>0) {
		   initial = text.substring(0,1).toUpperCase();
		   initial += text.substring(1);
		 }
		 return initial.trim();*/
    	StringBuffer result = new StringBuffer(text.trim());
    	result.setCharAt(0, Character.toUpperCase(text.charAt(0)));
    	return result.toString();
	}

    public static boolean hasIniCase(String word) 
    {
    	if (word.length() == 0)
    		return false;
    	return Character.isUpperCase(word.charAt(0));
    }

    public static boolean isPunctuation(char c)
    {
    	int type = Character.getType(c);
    	if ((type == Character.END_PUNCTUATION) || (type == Character.START_PUNCTUATION) || 
    			(type == Character.FINAL_QUOTE_PUNCTUATION) || (type == Character.INITIAL_QUOTE_PUNCTUATION) ||
    			(type == Character.OTHER_PUNCTUATION))
    		return true;
    	return false;
    }
    
    static public String cleanWord (String text) {
        String cleanText = text.trim();
        while ((cleanText.length() > 0) && isPunctuation(cleanText.charAt(0)))
        	cleanText = cleanText.substring(1);
        while ((cleanText.length() > 0) && isPunctuation(cleanText.charAt(cleanText.length() - 1)))
        	cleanText = cleanText.substring(0, cleanText.length() - 2);

        return cleanText;
    }

    static public String removeSurroundingRubbish (String text) {
        String cleanText = text.trim();
        cleanText = cleanText.replaceAll("\r", " ");
        cleanText = cleanText.replaceAll("\n", " ");
        cleanText = cleanText.replaceAll("\t", " ");
        cleanText = cleanText.replaceAll("  ", " ");
        return cleanText;
    }
} 

