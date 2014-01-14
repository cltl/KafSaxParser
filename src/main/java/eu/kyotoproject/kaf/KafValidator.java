package eu.kyotoproject.kaf;

import eu.kyotoproject.util.FileProcessor;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

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

public class KafValidator extends DefaultHandler
{	
	private static SAXParser p;
	private static DefaultHandler h;

	/**
	 * 0 = no details
	 * 1 = just ID/IDREF problems
	 * 2 = all problems
	 */
	private static int reportDetails = 0;
	private static boolean idRefProblems = false;

	public KafValidator() throws SAXException, ParserConfigurationException
	{
		final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema"; 

		//String dtd = "/home/joost/workspace/kyotoproject/doc/user/KAF/kaf.dtd";
		String xsd = "/home/joost/workspace/kyotoproject/doc/user/KAF/kaf.xsd";

		// get a schema
		SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA);
		// compile schema (doesn't work with dtd)
		File schemaLocation = new File(xsd);
		Schema schema = sf.newSchema(schemaLocation);

		// get a parser
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setValidating(true);
		spf.setSchema(schema);

		p = spf.newSAXParser();
		//DefaultHandler h = new MyErrorHandler();
		h = new MyIdHandler();
	}

	public static void reportDetails(int details) 
	{
		reportDetails = details;
	}

	public static boolean checkIdRefProblems(String myFile) throws SAXException, IOException
	{
		idRefProblems = false;

		File file = new File(myFile);

		p.parse(file, h);

		return idRefProblems;
	}

	public static void reportTime(String message, long tDiff)
	{
		// print with more easily understandable units
		
		String units = "ms";

		if (tDiff < 2000)
		{
			units = "ms"; 
		}
		else
		{
			tDiff = tDiff / 1000;
			if (tDiff < 120)
			{
				units = "secs";
			}
			else
			{
				tDiff = tDiff / 60;
				if (tDiff < 120)
				{
					units = "min";
				}
				else
				{
					tDiff = tDiff / 60;
					units = "hours";
				}
			}
		}
		System.out.println(message + " took: " + tDiff + " " + units);
	}
	
	/**
	 * small SaxParser ExceptionHandler: only for Id/IdRef problems
	 * @author joost
	 *
	 */
	private static class MyIdHandler extends DefaultHandler
	{
		public void error(SAXParseException e) throws SAXException 
		{
			String message = e.getMessage();
			if (reportDetails == 2 || message.contains("cvc-id."))
			{
				idRefProblems = true;
				
				if (reportDetails > 0)
				{
					System.out.println("Error: Line number: " + 
							e.getLineNumber() + 
							" Column number: " + 
							e.getColumnNumber());
					System.out.println("   Message: " + message);					
				}
			}
//			else
//			{
//				// ignore all other problems ...
//			}
		}
	}

	private static class MyErrorHandler extends DefaultHandler 
	{
		public void warning(SAXParseException e) throws SAXException 
		{
			System.out.println("Warning: "); 
			printInfo(e);
		}
		public void error(SAXParseException e) throws SAXException 
		{
			System.out.println("Error: "); 
			printInfo(e);
		}
		public void fatalError(SAXParseException e) throws SAXException 
		{
			System.out.println("Fatal error: "); 
			printInfo(e);
		}
		private void printInfo(SAXParseException e) 
		{
			// always null ?
			//System.out.println("   Public ID: "+e.getPublicId());
			// just the filename
			//System.out.println("   System ID: "+e.getSystemId());
			System.out.println("   Line number: " + e.getLineNumber() + " Column number: " + e.getColumnNumber());
			System.out.println("   Message: " + e.getMessage());
		}
	}	// endOfClass MyErrorHandler

	/**
	 * @param argv
	 */
	public static void main(String[] argv) 
	{
		String kafFiles[] = null;
		String kafFile = null;
		
		if (argv.length == 1 ) 
		{
			String param  = argv[0];
			File temp = new File(param);
			if (temp == null)
			{
				// param is neither a file, nor a directory
				System.out.println(param + " is no file or directory");
			}
			else
			{
				if (temp.isDirectory())
				{
					// param is a directory
					kafFiles = FileProcessor.makeRecursiveFileList(param, ".kaf");
					
				}
				else
				{
					// param is a file
					kafFile = param;
				}
			}
		}
		else
		{
			// no parameter, use program default(s)
			
			// one of the smallest
			kafFile = "/home/joost/projects/kyoto/myData/upload/estuaries_english/lp/20343.kaf";

			// reported by feikje
			kafFile = "/home/joost/projects/kyoto/myData/upload/estuaries_english/lp/18341.kaf";
			//kafFile = "/home/joost/projects/kyoto/myData/upload/estuaries_english/lp/8508.kaf";
			//kafFile = "/home/joost/projects/kyoto/myData/upload/estuaries_english/lp/8654.kaf";

			// large with duplicates
			//kafFile = "/home/joost/projects/kyoto/myData/upload/estuaries_english/lp/8654.kaf";
			
			// Aitor Soroa
			kafFile = "/home/joost/projects/kyoto/myData/upload/estuaries_english/lp+mw+wsd+ne/11614.mw.wsd.ne.kaf";
		}
		
		try
		{
			KafValidator kafValidator = new KafValidator();
			kafValidator.reportDetails(0);

			long tStart = System.currentTimeMillis();

			if (kafFile != null)
			{
				System.out.println("parsing " + kafFile);
				boolean problems = kafValidator.checkIdRefProblems(kafFile);
				if (problems) System.out.println(kafFile + " has problems");
					
			}
			if (kafFiles != null)
			{
				int filesWithProblems = 0;
				for (int i = 0; i < kafFiles.length; ++i)
				{
					kafFile = kafFiles[i];
					//System.out.println(kafFile);
					boolean problems = kafValidator.checkIdRefProblems(kafFile);
					if (problems)
					{
						System.out.println(kafFile + " has problems");
						++filesWithProblems;
					}
				}
				System.out.println("Out of " + kafFiles.length + " files, " + filesWithProblems + " have problems" );
			}
			
			long tEnd = System.currentTimeMillis();			
			reportTime("checkIdRefProblems", tEnd - tStart);
		}
		catch (ParserConfigurationException e) 
		{
			System.out.println(e.toString()); 
		} 
		catch (SAXException e) 
		{
			System.out.println(e.toString()); 
		}
		catch (IOException e) 
		{
			System.out.println(e.toString()); 
		}
	}

}	// endOfClass KafValidator
