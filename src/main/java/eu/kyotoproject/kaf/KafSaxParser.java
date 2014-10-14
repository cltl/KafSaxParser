package eu.kyotoproject.kaf;


import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Piek Vossen
 * Date: 23-sep-2008
 * Time: 17:54:07
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
public class KafSaxParser extends DefaultHandler {
    static final String NAFVERSION = "v3";
    final byte[] utf8_bom = { (byte) 0xEF, (byte) 0xBB,
        (byte) 0xBF }; /// For Chinese KAF
    final String utf8_bomString = new String(utf8_bom);
    //static String encoding = "UTF-8";
    private String value = "";
    private String previousvalue = "";
    private KafMetaData kafMetaData;
    public ArrayList<KafEvent> kafEventArrayList;
    public ArrayList<KafEntity> kafEntityArrayList;
    public ArrayList<KafProperty> kafPropertyArrayList;
    public ArrayList<KafOpinion> kafOpinionArrayList;
    public ArrayList<KafCoreferenceSet> kafCorefenceArrayList;
    public ArrayList<GeoCountryObject> kafCountryArrayList;
    public ArrayList<GeoPlaceObject> kafPlaceArrayList;
    public ArrayList<ISODate> kafDateArrayList;
    private ArrayList<KafReference> kafReferenceArrayList;
    public ArrayList<KafConstituencyTree> kafConstituencyTrees;
    public ArrayList<KafFactuality> kafFactualityLayer;
    public ArrayList<KafTimex> kafTimexLayer;
    public boolean AUGMENTID;
    private KafReference kafReference;
    private boolean kafreference;
    private boolean coreference;
    private boolean property;
    private boolean entity;
    private ISODate date;
    private DateInfo dateInfo;
    private GeoCountryObject country;
    private GeoInfoCountry countryInfo;
    private GeoPlaceObject place;
    private GeoInfoPlace placeInfo;
    private String locId;
    private KafSense sense;
    private boolean countryObject;
    private boolean placeObject;
    private boolean externalreference;
    private int externalRefLevel = 0;
    public String rawText;
    /**
     * ArrayList with KafEventISI: special events represented in the ISI corpus
     */
    public ArrayList<KafEventISI> kafEventISIList;

    /**
     * ArrayList with the KafWordForm elements in the order as presented in the KAF file
     */
    public ArrayList<KafWordForm> kafWordFormList;

    /**
     * ArrayList with the KafTerm elements in the order as presented in the KAF file
     */
    public ArrayList<KafTerm> kafTermList;

    /**
     * ArrayList with the KafChunk elements in the order as presented in the KAF file
     */
    public ArrayList<KafChunk> kafChunkList;

    /**
     * ArrayList with the KafDep elements in the order as presented in the KAF file
     */
    public ArrayList<KafDep> kafDepList;

    /**
     * ArrayList with KafEventRelations expressing Clinks
     */
    public ArrayList<KafEventRelation> kafClinks;

    /**
     * ArrayList with KafEventRelations expressing TLinks
     */
    public ArrayList<KafEventRelation> kafTlinks;

    /**
     * ArrayList with the KafTextUnit elements in the order as presented in the KAF file
     */
    public ArrayList<KafTextUnit> kafDiscourseList;

    /**
     * HashMap with IDs of KafWordForm pointing to the ID of the KafTerm in which they occur as part of the span
     */
    public HashMap<String,String> WordFormToTerm;

    /**
     * HashMap with IDs of the KafTerm pointing to the ID of the KafChunk in which they occur as part of the span
     */
    public HashMap<String,ArrayList<String>> TermToChunk;

    /**
     * HashMap with IDs of the KafToken pointing to the ID of the Timex in which they occur as part of the span
     */
    public HashMap<String,ArrayList<String>> TokenToTimex;

    /**
     * HashMap with IDs of the KafTerm pointing to the ID of the KafDep in which they occur as part of the span
     */
    public HashMap<String,ArrayList<KafDep>> TermToDeps;

    /**
     * HashMap with IDs of the KafTerm pointing to the ID of the KafOpinion in which they occur as part of the span
     */
    public HashMap<String,ArrayList<String>> TermToOpinions;

    /**
     * HashMap with IDs of the KafTerm pointing to the ID of the KafEntity in which they occur as part of the span     */
    public HashMap<String,ArrayList<String>> TermToEntities;

    /**
     * HashMap with IDs of the KafTerm pointing to the ID of the KafProperty in which they occur as part of the span     */
    public HashMap<String,ArrayList<String>> TermToProperties;

    /**
     * HashMap with IDs of the KafTerm pointing to the ID of the KafCoreferenceSet in which they occur as part of the span     */
    public HashMap<String,ArrayList<String>> TermToCoreferences;

    /**
     * HashMap with IDs of the sentence pointing to an ArrayList IDs of the KafWordForm elements that make up a sentence
     */
    public HashMap<String,ArrayList<String>> SentenceToWord;


    /**
     * HashMap with IDs of the sentence pointing to an ArrayList IDs of the KafTerm elements that make up a sentence
     */
    public HashMap<String,ArrayList<String>> SentenceToTerm;


    /**
     * HashMap with IDs of the KafTerm pointing to an ArrayList IDs of the KafWordForm elements that make up a KafTerm span
     */
    public HashMap<String,ArrayList<String>> TermToWord;	//keys are tid's, values are lists of wid's


//   public HashMap<String,String> IdToWord;
    
    //// HashMaps to find wordforms, terms and chunks by their IDs
    /**
     * HashMap with KafWordForm id as key and the KafWordForm as the value
     */
    public HashMap<String,KafWordForm> wordFormMap;

    /**
     * HashMap with KafTerm id as key and the KafTerm as the value
     */
    public HashMap<String,KafTerm> termMap;

    /**
     * HashMap with KafChunk id as key and the KafChunk as the value
     */
    public HashMap<String,KafChunk> chunkMap;


//    private String lastTermId;
//    private String lastTermLemma;
    private String layer;
    private ArrayList<String> spans;
    private ArrayList<CorefTarget> predicateSpans;
    private ArrayList<CorefTarget> roleSpans;
    private ArrayList<CorefTarget> corefSpans;
    private ArrayList<KafParticipant> participantArrayList;
    private KafTerm kafTerm;
    private KafChunk kafChunk;
    private KafEntity kafEntity;
    private KafProperty kafProperty;
    private KafCoreferenceSet kafCoreferenceSet;
    private KafConstituencyTree kafConstituencyTree;
    private KafConstituencyTerminal kafConstituencyTerminal;
    private KafWordForm kafWordForm;
    private TermComponent termComponent;
    private KafDep kafDep;
    private KafEventRelation kafEventRelation;
    private KafOpinion kafOpinion;
    private KafTermSentiment kafTermSentiment;
    private KafEvent kafEvent;
    private KafParticipant kafParticipant;
    private KafEventISI kafEventISI;
    private KafTextUnit kaftextUnit;
    private KafTimex kafTimex;
    private ArrayList<KafSense> senseTags;
    private ArrayList<KafSense> predicateSenseTags;
    private ArrayList<KafSense> roleSenseTags;
    private ArrayList<KafSense> senseTagsComponents;
    private boolean COMPONENT = false;
    private boolean PREDICATE = false;
    private boolean ROLE = false;

    /*
     * This KafSaxParser can be used to check some internal KAF consistencies.
     * At this moment we check:
     * - unique termIds
     */
    private boolean checkKafConsistency		= false;
    private boolean reportedDuplicateTerms	= false;	// to prevent too many details

    public KafSaxParser() {
        init();
    }
    
/*
    public boolean parseFile(InputSource source, String encoding)
    {
    	try 
    	{
            init();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            SAXParser parser = factory.newSAXParser();
            if (encoding != null)
            	source.setEncoding(encoding);
            parser.parse(source, this);
            buildSentenceTermIndex();
            return true;
        } 
    	catch (FactoryConfigurationError factoryConfigurationError) 
    	{
            factoryConfigurationError.printStackTrace();
        } 
    	catch (ParserConfigurationException e) 
    	{
            e.printStackTrace();
        } 
    	catch (SAXException e)
        {
            //System.out.println("last value = " + previousvalue);
            e.printStackTrace();
        } 
    	catch (IOException e) 
    	{
           // e.printStackTrace();
        }
    	return false;
    }
*/

    public boolean parseFile(InputSource source)
    {
    	try
    	{
            init();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(false);
            SAXParser parser = factory.newSAXParser();
            parser.parse(source, this);
            buildSentenceTermIndex();
            return true;
        }
    	catch (FactoryConfigurationError factoryConfigurationError)
    	{
            factoryConfigurationError.printStackTrace();
        }
    	catch (ParserConfigurationException e)
    	{
            e.printStackTrace();
        }
    	catch (SAXException e)
        {
            //System.out.println("last value = " + previousvalue);
            e.printStackTrace();
        }
    	catch (IOException e)
    	{
           // e.printStackTrace();
        }
    	return false;
    }
    
    public boolean parseFile(File file)
    {
        try
        {
            // System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
            FileReader reader = new FileReader(file);
            InputSource inp = new InputSource(reader);
            boolean result = parseFile(inp);
            reader.close();
            return result;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }    }
    
/*
    public boolean parseFile(File file, String encoding)
    {
    	try
    	{
           // System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
            FileReader reader = new FileReader(file);
    		InputSource inp = new InputSource(reader);
    		boolean result = parseFile(inp, encoding);
    		reader.close();
    		return result;
    	}
    	catch (IOException e)
    	{
    		e.printStackTrace();
    		return false;
    	}
    }
*/

    public boolean parseFile(InputStream stream)
    {
    	InputSource source = new InputSource(stream);
    	boolean result = parseFile(source);
    	try 
    	{
			stream.close();
		} 
    	catch (IOException e) 
		{}
    	return result;
    }

/*
    public boolean parseFile(InputStream stream, String encoding)
    {
    	InputSource source = new InputSource(stream);
        source.setEncoding(encoding);
    	return parseFile(source, encoding);
    }

    public boolean parseFile(String filePath, String encoding) {
        return parseFile(new File(filePath), encoding);
    }//--c
*/

    public boolean parseFile(String filePath) {
    	return parseFile(new File(filePath));
    }//--c
    
    public boolean parseFileWithConsistencyChecks(String filePath)
    {
    	checkKafConsistency    = true;
    	reportedDuplicateTerms = false;
    	
    	return parseFile(new File(filePath));
    }

    public boolean parseStringContent(String content)
    {
       	InputSource source = new InputSource(new ByteArrayInputStream(content.getBytes()));
       	return parseFile(source);
    }//--c

    public boolean parseByteContent(byte [] content) 
    {
    	InputSource source = new InputSource(new ByteArrayInputStream(content));
    	return parseFile(source);
    }//--c

    public void init() {
     //    lastTermId = "";
     //    lastTermLemma = "";
         rawText = "";
         kafEventArrayList = new ArrayList<KafEvent>();
         kafEventISIList = new ArrayList<KafEventISI>();
         kafMetaData = new KafMetaData();
         kafDiscourseList = new ArrayList<KafTextUnit>();
         kafWordFormList = new ArrayList<KafWordForm>();
         kafTermList = new ArrayList<KafTerm>();
         kafChunkList = new ArrayList<KafChunk>();
         kafDepList = new ArrayList<KafDep>();
         kafClinks = new ArrayList<KafEventRelation>();
         kafTlinks = new ArrayList<KafEventRelation>();
         senseTags = new ArrayList<KafSense>();
         predicateSenseTags = new ArrayList<KafSense>();
         roleSenseTags = new ArrayList<KafSense>();
         senseTagsComponents = new ArrayList<KafSense>();
         kafCountryArrayList = new ArrayList<GeoCountryObject>();
         kafPlaceArrayList = new ArrayList<GeoPlaceObject>();
         kafDateArrayList = new ArrayList<ISODate>();
         kafReferenceArrayList = new ArrayList<KafReference>();
         kafOpinionArrayList = new ArrayList<KafOpinion>();
         kafEntityArrayList = new ArrayList<KafEntity>();
         kafPropertyArrayList = new ArrayList<KafProperty>();
         kafCorefenceArrayList = new ArrayList<KafCoreferenceSet>();
         participantArrayList = new ArrayList<KafParticipant>();
         kafConstituencyTrees = new ArrayList<KafConstituencyTree>();
         kafConstituencyTree = new KafConstituencyTree();
         kafConstituencyTerminal = new KafConstituencyTerminal();
         kafFactualityLayer = new ArrayList<KafFactuality>();
         kafTimexLayer = new ArrayList<KafTimex>();
         AUGMENTID = false;
         kafreference = false;
         coreference = false;
         entity = false;
         property = false;
         kafParticipant = new KafParticipant();
         kafReference = new KafReference();
         kafEventRelation = new KafEventRelation();
         date = new ISODate();
         country = new GeoCountryObject();
         place = new GeoPlaceObject();
         dateInfo = new DateInfo();
         locId = "";
         countryObject = false;
         placeObject = false;
         placeInfo = new GeoInfoPlace();
         countryInfo = new GeoInfoCountry();
         externalreference = false;
         externalRefLevel = 0;
         spans = new ArrayList<String>();
         predicateSpans = new ArrayList<CorefTarget>();
         roleSpans = new ArrayList<CorefTarget>();
         kafChunk = new KafChunk();
         kafDep = new KafDep();
         kafTerm = new KafTerm();
         sense = new KafSense();
         kafWordForm = new KafWordForm();
         termComponent = new TermComponent();
         kafEvent = new KafEvent();
         kafEventISI = new KafEventISI();
         kaftextUnit = new KafTextUnit();
         WordFormToTerm = new HashMap<String,String>();
         SentenceToWord = new HashMap<String,ArrayList<String>>();
         SentenceToTerm = new HashMap<String,ArrayList<String>>();
         TokenToTimex = new HashMap<String,ArrayList<String>>();
         TermToChunk = new HashMap<String,ArrayList<String>>();
         TermToDeps = new HashMap<String,ArrayList<KafDep>>();
         TermToWord = new HashMap<String,ArrayList<String>>();
         TermToOpinions = new HashMap<String, ArrayList<String>>();
         TermToEntities = new HashMap<String, ArrayList<String>>();
         TermToProperties = new HashMap<String, ArrayList<String>>();

         wordFormMap = new HashMap<String,KafWordForm>();
         termMap = new HashMap<String,KafTerm>();
         chunkMap = new HashMap<String,KafChunk>();
         COMPONENT = false;
         PREDICATE = false;
         ROLE = false;
    }

    public void buildSentenceTermIndex (){
        Set keySet = SentenceToWord.keySet();
        Iterator keys = keySet.iterator();
        while (keys.hasNext()) {
            String sentenceId = (String) keys.next();
            ArrayList<String> tokenIds = SentenceToWord.get(sentenceId);
            ArrayList<String> termIds = new ArrayList<String>();
            for (int i = 0; i < tokenIds.size(); i++) {
                String tokenId = tokenIds.get(i);
                String termId = WordFormToTerm.get(tokenId);
                if (termId!=null) {
                    termIds.add(termId);
                }
            }
            if (termIds.size()>0) {
                SentenceToTerm.put(sentenceId, termIds);
            }

        }
    }

    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {
        //System.out.println("qName = " + qName);
       previousvalue = value;
       value = "";
       if ((qName.equalsIgnoreCase("KAF")) || (qName.equalsIgnoreCase("NAF"))) {
           //<KAF xml:lang="en" doc="1000">
           kafMetaData = new KafMetaData ();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("xml:lang")) {
                   kafMetaData.setLanguage(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("version")) {
            	   kafMetaData.setDocId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("doc")) {
            	   kafMetaData.setDocId(attributes.getValue(i).trim());
               }
               else {
            	//   System.out.println("216 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
       }
       else if (qName.equalsIgnoreCase("fileDesc")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if (name.equalsIgnoreCase("title")) {
                    kafMetaData.setTitle(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("author")) {
                    kafMetaData.setAuthor(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("creationtime")) {
                    kafMetaData.setCreationtime(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("filename")) {
                    kafMetaData.setFilename(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("filesize")) {
                    try {
                        kafMetaData.setFilesize(Long.parseLong(attributes.getValue(i).trim()));
                    } catch (NumberFormatException e) {
                        //e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                else if (name.equalsIgnoreCase("filetype")) {
                    kafMetaData.setFiletype(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("metakey")) {
                    kafMetaData.setMetakey(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("pages")) {
                    try {
                        kafMetaData.setNPages(Integer.parseInt(attributes.getValue(i).trim()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                else {
             	//   System.out.println("251 ********* FOUND UNKNOWN Attribute " + name + " *****************");
                }
            }
        }
        else if (qName.equalsIgnoreCase("captureDesc")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if (name.equalsIgnoreCase("storagedate")) {
                    kafMetaData.setDateString(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("dateString")) {
                    kafMetaData.setDateString(attributes.getValue(i).trim());
                }
                else {
             	//   System.out.println("262 ********* FOUND UNKNOWN Attribute " + name + " *****************");
                }
            }
        }
        else   if (qName.equalsIgnoreCase("public")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String name = attributes.getQName(i);
                if (name.equalsIgnoreCase("project")) {
                    kafMetaData.setProject(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("collectionid")) {
                    kafMetaData.setCollectionId(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("docid")) {
                    kafMetaData.setDocId(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("dmsid")) {
                    kafMetaData.setPublicId(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("publicid")) {
                    kafMetaData.setPublicId(attributes.getValue(i).trim());
                }
                else if (name.equalsIgnoreCase("uri")) {
                    kafMetaData.setUrl(attributes.getValue(i).trim());
                }
                else {
             	  // System.out.println("282 ********* FOUND UNKNOWN Attribute " + name + " *****************");
                }
            }
        }
        else if (qName.equalsIgnoreCase("linguisticProcessors"))
        {
        	layer = attributes.getValue("layer");
        }
        else if (qName.equalsIgnoreCase("lp"))
        {
           	kafMetaData.addLayer(layer, attributes.getValue("name"), attributes.getValue("version"), attributes.getValue("timestamp"), attributes.getValue("beginTimestamp"), attributes.getValue("endTimestamp"));
        	//kafMetaData.addLP(attributes.getValue("name"), attributes.getValue("version"), attributes.getValue("timestamp"));
        }

       else if (qName.equalsIgnoreCase("timex3")) {
           /**
            * <timeExpressions>
            <timex3 id="tmx1" type="DURATION">
            <span>
            <target id="w9"/>
            <target id="w10"/>
            </span>
            </timex3>
            <timex3 id="tmx2" type="DURATION">
            <span>
            <target id="w46"/>
            <target id="w47"/>
            </span>
            </timex3>
            </timeExpressions>
            */
            kafTimex = new KafTimex();
            for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("id")) {
                   kafTimex.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("type")) {
                   kafTimex.setType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("value")) {
                   kafTimex.setValue(attributes.getValue(i).trim());
               }
               else {
                   //   System.out.println("314 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
            }
       }
       else if (qName.equalsIgnoreCase("chunk")) {
/*
    <chunk cid="cid53895.3" head="tid53895.4" phrase="S"><span>
<target tid="tid53895.11"/>
<target tid="tid53895.13"/>
<target tid="tid53895.22"/></span></chunk>
     */
           spans = new ArrayList<String>();
           kafChunk = new KafChunk();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if ((name.equalsIgnoreCase("cid")) || (name.equalsIgnoreCase("id"))) {
                   kafChunk.setCid(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("head")) {
                   kafChunk.setHead(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("phrase")) {
                   kafChunk.setPhrase(attributes.getValue(i).trim());
               }
               else {
            	//   System.out.println("314 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
       }
       else if (qName.equalsIgnoreCase("tlink")) {
           kafEventRelation = new KafEventRelation();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("from")) {
                   kafEventRelation.setFrom(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("to")) {
                   kafEventRelation.setTo(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("relType")) {
                   kafEventRelation.setRelType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("fromType")) {
                   kafEventRelation.setFromType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("toType")) {
                   kafEventRelation.setToType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafEventRelation.setId(attributes.getValue(i).trim());
               }
               else {
            	//   System.out.println("329 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
           /// dep only has attributes so we can store it now....
           kafTlinks.add(kafEventRelation);
       }
       else if (qName.equalsIgnoreCase("clink")) {
           kafEventRelation = new KafEventRelation();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("from")) {
                   kafEventRelation.setFrom(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("to")) {
                   kafEventRelation.setTo(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("relType")) {
                   kafEventRelation.setRelType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("fromType")) {
                   kafEventRelation.setFromType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("toType")) {
                   kafEventRelation.setToType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafEventRelation.setId(attributes.getValue(i).trim());
               }
               else {
            	//   System.out.println("329 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
           /// dep only has attributes so we can store it now....
           kafClinks.add(kafEventRelation);
       }
       else if (qName.equalsIgnoreCase("dep")) {
           kafDep = new KafDep();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("from")) {
                   kafDep.setFrom(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("to")) {
                   kafDep.setTo(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("rfunc")) {
                   kafDep.setRfunc(attributes.getValue(i).trim());
               }
               else {
            	//   System.out.println("329 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
           /// dep only has attributes so we can store it now....
           kafDepList.add(kafDep);
       }
       else if (qName.equalsIgnoreCase("tunit")) {
           /*
           <tunit type="page" unitId="2">
		<span>
			<target id="w579"/>
			<target id="w1263"/>
		</span>
	</tunit>
            */
           spans = new ArrayList<String>();
           kaftextUnit = new KafTextUnit();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("type")) {
                   kaftextUnit.setType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("unitid")) {
                   kaftextUnit.setUnitid(Integer.parseInt(attributes.getValue(i).trim()));
               }
               else {
            	//   System.out.println("352 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
       }


       else if (qName.equalsIgnoreCase("tree")) {
           kafConstituencyTree = new KafConstituencyTree();

       }
       else if (qName.equalsIgnoreCase("nt")) {
           KafConstituencyNonTerminal kafConstituencyNonTerminal = new KafConstituencyNonTerminal();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("id")) {
                   kafConstituencyNonTerminal.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("label")) {
                   kafConstituencyNonTerminal.setLabel(attributes.getValue(i).trim());
               }
               else {
                //   System.out.println("352 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
           kafConstituencyTree.addNonterminals(kafConstituencyNonTerminal);
       }
       else if (qName.equalsIgnoreCase("t")) {
           kafConstituencyTerminal = new KafConstituencyTerminal();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("id")) {
                   kafConstituencyTerminal.setId(attributes.getValue(i).trim());
               }
               else {
                //   System.out.println("352 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
       }
       else if (qName.equalsIgnoreCase("edge")) {
           KafConstituencyEdge kafConstituencyEdge = new KafConstituencyEdge();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("from")) {
                   kafConstituencyEdge.setFrom(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("to")) {
                   kafConstituencyEdge.setTo(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafConstituencyEdge.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("head")) {
                   kafConstituencyEdge.setHead(attributes.getValue(i).trim());
               }
               else {
                //   System.out.println("352 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
           kafConstituencyTree.addEdges(kafConstituencyEdge);
       }


       /**
        * <factualitylayer>
        *     <factvalue id="w4" prediction="CT+" confidence="0.5904876913248487"/>
        * <factvalue id="w681" prediction="Uu" confidence="0.5324089742552198"/>
        */

       else if (qName.equalsIgnoreCase("factvalue")) {
           KafFactuality kafFactuality = new KafFactuality();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("id")) {
                   kafFactuality.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("prediction")) {
                   kafFactuality.setPrediction(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("confidence")) {
                   try {
                       kafFactuality.setConfidence(Double.parseDouble(attributes.getValue(i).trim()));
                   } catch (NumberFormatException e) {
                       e.printStackTrace();
                   }
               }
               else  {
                //   System.out.println("352 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
           kafFactualityLayer.add(kafFactuality);
       }

       else if (qName.equalsIgnoreCase("opinion")) {
           kafOpinion = new KafOpinion();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("oid")) {
                   kafOpinion.setOpinionId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafOpinion.setOpinionId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("overlap_ents")) {
                   kafOpinion.setOverlap_ents(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("overlap_props")) {
                   kafOpinion.setOverlap_props(attributes.getValue(i).trim());
               }
               else  {
                //   System.out.println("352 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
       }
       else if (qName.equalsIgnoreCase("opinion_holder")) {
           spans = new ArrayList<String>();
           String type = attributes.getValue("type");
           if (type!=null) {
               kafOpinion.setOpinionHolderType(type);
           }
       }
       else if (qName.equalsIgnoreCase("opinion_target")) {
           spans = new ArrayList<String>();
       }
       else if (qName.equalsIgnoreCase("opinion_expression")) {
           spans = new ArrayList<String>();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
              // System.out.println("name = " + name);
               if (name.equalsIgnoreCase("polarity")) {
                   kafOpinion.getOpinionSentiment().setPolarity(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("strength")) {
                   kafOpinion.getOpinionSentiment().setStrength(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("resource")) {
                   kafOpinion.getOpinionSentiment().setResource(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("factual")) {
                   kafOpinion.getOpinionSentiment().setFactual(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("subjectivity")) {
                   kafOpinion.getOpinionSentiment().setSubjectivity(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("sentiment_semantic_type")) {
                   kafOpinion.getOpinionSentiment().setSentiment_semantic_type(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("sentiment_product_feature")) {
                   kafOpinion.getOpinionSentiment().setSentiment_product_feature(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("isi-event")) {
           kafEventISI = new KafEventISI();
           spans = new ArrayList<String>();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("eventid")) {
                   kafEventISI.setEventid(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafEventISI.setEventid(attributes.getValue(i).trim());
               }
               if (name.equalsIgnoreCase("epistemic_status")) {
                   kafEventISI.setEpistemic_status(attributes.getValue(i).trim());
               }
               if (name.equalsIgnoreCase("memberOf")) {
                   kafEventISI.setMemberOf(attributes.getValue(i).trim());
               }
               if (name.equalsIgnoreCase("inReporting")) {
                   kafEventISI.setInReporting(attributes.getValue(i).trim());
               }
               if (name.equalsIgnoreCase("coreference")) {
                   kafEventISI.setCoreference(attributes.getValue(i).trim());
               }
               if (name.equalsIgnoreCase("event_type")) {
                   kafEventISI.setEvent_type(attributes.getValue(i).trim());
               }
               if (name.equalsIgnoreCase("subevent_of")) {
                   kafEventISI.setSubevent_of(attributes.getValue(i).trim());
               }
               if (name.equalsIgnoreCase("start")) {
                   kafEventISI.setStart(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("predicate")) {
           kafEvent = new KafEvent();
           kafEvent.setComponentType("event");
           PREDICATE = true;
           ROLE = false;
           predicateSpans = new ArrayList<CorefTarget>();
           predicateSenseTags = new ArrayList<KafSense>();

           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("id")) {
                   kafEvent.setId(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("role")) {
           kafParticipant = new KafParticipant();
           kafParticipant.setComponentType("participant");
           ROLE = true;
           roleSpans = new ArrayList<CorefTarget>();
           roleSenseTags = new ArrayList<KafSense>();

           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("id")) {
                   kafParticipant.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("semrole")) {
                   kafParticipant.setRole(attributes.getValue(i).trim());
               }
           }

       }
       else if (qName.equalsIgnoreCase("sentiment")) {
               kafTermSentiment = new KafTermSentiment();
           /*
              <sentiment resource="VUA_polarityLexicon_word"
   polarity="positive" strength="average"
   subjectivity="subjective"
   sentiment_semantic_type="behaviour/trait"
   sentiment_product_feature="" />
            */
               for (int i = 0; i < attributes.getLength(); i++) {
                   String name = attributes.getQName(i);
                  // System.out.println("attributes.getValue(i).trim() = " + attributes.getValue(i).trim());
                   if (name.equalsIgnoreCase("resource")) {
                       kafTermSentiment.setResource(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("polarity")) {
                       kafTermSentiment.setPolarity(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("subjectivity")) {
                       kafTermSentiment.setSubjectivity(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("sentiment_modifier")) {
                       kafTermSentiment.setSentiment_modifier(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("factual")) {
                       kafTermSentiment.setFactual(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("sentiment_semantic_type")) {
                       kafTermSentiment.setSentiment_semantic_type(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("semantic_type")) {
                       kafTermSentiment.setSentiment_semantic_type(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("strength")) {
                       kafTermSentiment.setStrength(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("sentiment_product_feature")) {
                       kafTermSentiment.setSentiment_product_feature(attributes.getValue(i).trim());
                   }
               }
              // System.out.println("kafTermSentiment.toString() = " + kafTermSentiment.toString());
               /// NASTY BIT. I NEED TO ADD THE SENTIMENT TO: A TERM, A COMPONENT OR A SENSE

               if (COMPONENT) {
                   if (externalRefLevel<=0) {
                       /// we are at the component lemma level
                       termComponent.setKafTermSentiment(kafTermSentiment);
                       kafTermSentiment = new KafTermSentiment();
                   }
                   else {
                      /// we are at a component sense level
                       if (senseTagsComponents.size()>0) {
                               KafSense k = senseTagsComponents.get(senseTagsComponents.size()-1);
                               if (kafTermSentiment.hasValue()) {
                                   k.setKafTermSentiment(kafTermSentiment);
                                   kafTermSentiment = new KafTermSentiment();
                               }
                       }
                       else {
                           /// something wrong, there should be components
                       }
                   }

               }
               else {
                   if (externalRefLevel<=0) {
                       /// we are at the term lemma level
                       kafTerm.setKafTermSentiment(kafTermSentiment);
                       kafTermSentiment = new KafTermSentiment();

                   }
                   else {
                       /// we are at a term sense level
                   }
                   if (senseTags.size()>0) {
                           KafSense k = senseTags.get(senseTags.size()-1);
                           if (kafTermSentiment.hasValue()) {
                               k.setKafTermSentiment(kafTermSentiment);
                               kafTermSentiment = new KafTermSentiment();
                           }
                   }
                   else {
                       //something wrong
                   }
               }
       }
       else if (qName.equalsIgnoreCase("term")) {
           /*
           <term tid="tid53895.2" lemma="en" pos="none" dep="subj" type="none"><span>
<target id="wid53895.2"/></span></term>
<term tid="tid53895.4" lemma="ben" pos="V" type="open"><span>
<target id="wid53895.4"/></span></term>
            */
           COMPONENT = false;
           kafTermSentiment = new KafTermSentiment();
           senseTags = new ArrayList<KafSense>();
           spans = new ArrayList<String>();
           kafTerm = new KafTerm();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("tid")) {
                   kafTerm.setTid(attributes.getValue(i).trim());
                 /*  if (kafTerm.getTid().length()>0) {
                       lastTermId = kafTerm.getTid();
                   }*/
                   //System.out.println("tid attributes.getValue(i).trim() = " + attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafTerm.setTid(attributes.getValue(i).trim());
                 /*  if (kafTerm.getTid().length()>0) {
                       lastTermId = kafTerm.getTid();
                   }*/
                   //System.out.println("tid attributes.getValue(i).trim() = " + attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("lemma")) {
                   kafTerm.setLemma(attributes.getValue(i).trim());
                /*   if (kafTerm.getLemma().length()>0) {
                       lastTermLemma = kafTerm.getLemma();
                   }*/
                   //System.out.println("lemma attributes.getValue(i).trim() = " + attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("pos")) {
                   kafTerm.setPos(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("head")) {
                   kafTerm.setHead(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("morphofeat")) {
                   kafTerm.setMorphofeat(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("dep")) {
                   kafTerm.setDep(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("type")) {
                   kafTerm.setType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("netype")) {
                   kafTerm.setNetype(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("parent")) {
                   kafTerm.setParent(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("modifier")) {
                   kafTerm.setModifier(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("polarity")) {
                   kafTerm.setPolarity(attributes.getValue(i).trim());
               }
               else {
            	//   System.out.println("395 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
       }
       else if (qName.equalsIgnoreCase("wf")) {
           //<wf wid="wid58832.8">dat</wf>
           kafWordForm = new KafWordForm();
           String wid = "";
           String sentenceId = "";
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("wid")) {
                   wid = attributes.getValue(i).trim();
                   kafWordForm.setWid(wid);
               }
               else if (name.equalsIgnoreCase("id")) {
                   wid = attributes.getValue(i).trim();
                   kafWordForm.setWid(wid);
               }
               else if (name.equalsIgnoreCase("para")) {
                   kafWordForm.setPara(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("page")) {
                   kafWordForm.setPage(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("sent")) {
                   sentenceId = attributes.getValue(i).trim();
                   kafWordForm.setSent(sentenceId);
               }
               else if (name.equalsIgnoreCase("charoffset")) {
                   kafWordForm.setCharOffset(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("charlength")) {
                   kafWordForm.setCharLength(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("offset")) {
                   kafWordForm.setCharOffset(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("length")) {
                   kafWordForm.setCharLength(attributes.getValue(i).trim());
               }
               else  {
            	 //  System.out.println("414 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
               if ((wid.length()>0) && (sentenceId.length()>0)) {
                   if (SentenceToWord.containsKey(sentenceId)) {
                       ArrayList<String> tokenIds = SentenceToWord.get(sentenceId);
                       tokenIds.add(wid);
                       SentenceToWord.put(sentenceId, tokenIds);
                   }
                   else {
                       ArrayList<String> tokenIds = new ArrayList<String>();
                       tokenIds.add(wid);
                       SentenceToWord.put(sentenceId, tokenIds);
                   }
               }

           }
       }
       else if (qName.equalsIgnoreCase("target")) {
           if (ROLE) {
               CorefTarget corefTarget = new CorefTarget();
               for (int i = 0; i < attributes.getLength(); i++) {
                   String name = attributes.getQName(i);
                   if (name.equalsIgnoreCase("id")) {
                       corefTarget.setId(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("head")) {
                       corefTarget.setHead(attributes.getValue(i).trim());
                   }
               }
               roleSpans.add(corefTarget);
           }
           else if (PREDICATE) {
               CorefTarget corefTarget = new CorefTarget();
               for (int i = 0; i < attributes.getLength(); i++) {
                   String name = attributes.getQName(i);
                   if (name.equalsIgnoreCase("id")) {
                       corefTarget.setId(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("head")) {
                       corefTarget.setHead(attributes.getValue(i).trim());
                   }
               }
               predicateSpans.add(corefTarget);
           }
           else if ((coreference) || (entity) || (property)) {
               CorefTarget corefTarget = new CorefTarget();
               for (int i = 0; i < attributes.getLength(); i++) {
                   String name = attributes.getQName(i);
                   if (name.equalsIgnoreCase("id")) {
                       corefTarget.setId(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("head")) {
                       corefTarget.setHead(attributes.getValue(i).trim());
                   }
               }
               corefSpans.add(corefTarget);
           }
           else {//// simple span
                for (int i = 0; i < attributes.getLength(); i++) {
                       spans.add(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("externalReferences")) {
           if (COMPONENT) {
               senseTagsComponents = new ArrayList<KafSense>();
           }
           else if (ROLE) {
               roleSenseTags = new ArrayList<KafSense>();
           }
           else if (PREDICATE) {
               predicateSenseTags = new ArrayList<KafSense>();
           }
           else {
               senseTags = new ArrayList<KafSense>();
           }
           externalRefLevel = 0;
       }
       else if (qName.equalsIgnoreCase("externalRef")) {
            //// new style for sensecodes
            /// Can be nested......
            /*
                <externalRef confidence="0.243178" reference="d_v-2892-v" resource="dwn12Domain2">
                    <externalRef confidence="1.0" reference="d_v-2645-v"  reftype="baseConcept" resource="wn30g"/>
                    <externalRef confidence="1.0" reference="ActionVerbalBase" reftype="sc_subClassOf" resource="ontology"/>
                </externalRef>
             */
         //  System.out.println("externalRefLevel = " + externalRefLevel);
           sense = new KafSense();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("reference")) {
                   sense.setSensecode(attributes.getValue(i).trim());
               //    System.out.println("sense.getSenseCode() = " + sense.getSensecode());
               }
               else if (name.equalsIgnoreCase("confidence")) {
                   double conf = 0;
                   try {
                       conf = Double.parseDouble(attributes.getValue(i).trim());
                   } catch (NumberFormatException e) {
                       e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                   }
                   sense.setConfidence(conf);
               }
               else if (name.equalsIgnoreCase("resource"))
               {
            	   sense.setResource(attributes.getValue(i).trim());
                   //System.out.println("sense.getR = " + sense.getResource());
               }
               else if (name.equalsIgnoreCase("reftype"))  {
                    sense.setRefType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("status"))  {
                    sense.setStatus(attributes.getValue(i).trim());
               }
               else {
            	 //  System.out.println("449 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
           //System.out.println("sense.toString() = " + sense.toString());
           if (COMPONENT) {
               KafSense.addSenseToSenseTags(senseTagsComponents, externalRefLevel, sense);
           }
           else if (PREDICATE && !ROLE) {
               KafSense.addSenseToSenseTags(predicateSenseTags, externalRefLevel, sense);
           }
           else if (ROLE) {
               KafSense.addSenseToSenseTags(roleSenseTags, externalRefLevel, sense);
           }
           else {
               //// in all other cases we are dealing with external reference sense linked to terms
               KafSense.addSenseToSenseTags(senseTags, externalRefLevel, sense);

/* DOES NOT DEAL WITH NESTING
               if (senseTags.size()>0) {
                   if (externalRefLevel>0) {
                       KafSense k = senseTags.get(senseTags.size()-1);
                        k.getChildren().add(sense);
                   }
                   else {
                       senseTags.add(sense);
                   }
               }
               else {
                   senseTags.add(sense);
               }
*/

           }
           externalRefLevel++;
          // System.out.println("ex = " + externalRefLevel);
       }
       else if (qName.equalsIgnoreCase("component")) {
    /*
    <term head="t2.12.1" lemma="cites-soort" pos="N.noun" tid="t2.12" type="open">
          <span><target id="w2.12"/></span>
          <component id="t2.12.0" lemma="CITES"/>
          <component id="t2.12.1" lemma="soort"/>
    </term>

            <component id="t6251" lemma="fish" pos="N">
                <externalReferences>
                    <externalRef confidence="0.473948" reference="dw-eng-30-294-n" resource="wneng_domain"/>
                    <externalRef confidence="0.147602" reference="eng-30-02512053-n" resource="wneng_domain"/>
                    <externalRef confidence="0.127141" reference="eng-30-08688076-n" resource="wneng_domain"/>
                    <externalRef confidence="0.125845" reference="eng-30-09753792-n" resource="wneng_domain"/>
                    <externalRef confidence="0.125464" reference="eng-30-07775375-n" resource="wneng_domain"/>
                </externalReferences>
            </component>

            */
           COMPONENT = true;
           termComponent = new TermComponent();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("id")) {
                   termComponent.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("lemma")) {
                   termComponent.setLemma(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("pos")) {
            	   termComponent.setPos(attributes.getValue(i).trim());
               }
               else {
            	//   System.out.println("521 ********* FOUND UNKNOWN Attribute " + name + " *****************");
               }
           }
       }
       ///// handle kaf references for dates, locations and countries (NEs)
       else if (qName.equalsIgnoreCase("kafReferences")) {
           kafReferenceArrayList = new ArrayList<KafReference>();
       }
       else if (qName.equalsIgnoreCase("kafReference")) {
           kafreference = true;
           kafReference = new KafReference();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("pageId")) {
                   kafReference.setPageId(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("span") && kafreference) {
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("id")) {
                   kafReference.setTerm(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("date")) {
       /*
               <date did="d4">
            <kafReferences>
                <kafReference pageId="0">
                    <span id="t16.9"></span>
                </kafReference>
            </kafReferences>
            <dateInfo dateISO="1948" lemma="1948"></dateInfo>
        </date>
        */
           date = new ISODate();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("did")) {
                   date.setDid(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   date.setDid(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("dateInfo")) {
           dateInfo = new DateInfo();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("dateIso")) {
                   dateInfo.setDateISO(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("lemma")) {
                   dateInfo.setLemma(attributes.getValue(i).trim());
               }
           }
       }
        else if (qName.equalsIgnoreCase("location")) {
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("lid")) {
                   locId = attributes.getValue(i).trim();
               }
               else if (name.equalsIgnoreCase("id")) {
                   locId = attributes.getValue(i).trim();
               }
           }
        }
        else if (qName.equalsIgnoreCase("country")) {
           countryObject = true;
           placeObject = false;
           countryInfo = new GeoInfoCountry();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("lid")) {
                   locId = attributes.getValue(i).trim();
               }
               else if (name.equalsIgnoreCase("id")) {
                   locId = attributes.getValue(i).trim();
               }
               else if (name.equalsIgnoreCase("countryCode")) {
                   countryInfo.setCountryCode(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("countryName")) {
                   countryInfo.setCountryName(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("capital")) {
                   countryInfo.setCapital(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("continent")) {
                   countryInfo.setContinent(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("eat")) {
                   countryInfo.setEast(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("west")) {
                   countryInfo.setWest(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("north")) {
                   countryInfo.setNorth(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("south")) {
                   countryInfo.setSouth(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("population")) {
                   countryInfo.setPopulation(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("fname")) {
                   countryInfo.setFname(attributes.getValue(i).trim());
               }
           }
        }
        else if (qName.equalsIgnoreCase("place")) {
           placeObject = true;
           countryObject = false;
           placeInfo = new GeoInfoPlace();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("lid")) {
                   locId = attributes.getValue(i).trim();
               }
               else if (name.equalsIgnoreCase("id")) {
                   locId = attributes.getValue(i).trim();
               }
               else if (name.equalsIgnoreCase("countryCode")) {
                   placeInfo.setCountryCode(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("countryName")) {
                   placeInfo.setCountryName(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("latitude")) {
                   placeInfo.setLatitude(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("longitude")) {
                   placeInfo.setLongitude(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("population")) {
                   placeInfo.setPopulation(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("fname")) {
                   placeInfo.setFname(attributes.getValue(i).trim());
               }
           }

        }
        else if ((qName.equalsIgnoreCase("geoInfo")) && attributes.getLength()>0) {
           if (placeObject) {
               placeInfo = new GeoInfoPlace();
               for (int i = 0; i < attributes.getLength(); i++) {
                   String name = attributes.getQName(i);
                   if (name.equalsIgnoreCase("countryCode")) {
                       placeInfo.setCountryCode(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("countryName")) {
                       placeInfo.setCountryName(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("latitude")) {
                       placeInfo.setLatitude(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("longitude")) {
                       placeInfo.setLongitude(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("population")) {
                       placeInfo.setPopulation(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("fname")) {
                       placeInfo.setFname(attributes.getValue(i).trim());
                   }
               }
           }
           else if (countryObject) {
               countryInfo = new GeoInfoCountry();
               for (int i = 0; i < attributes.getLength(); i++) {
                   String name = attributes.getQName(i);
                   if (name.equalsIgnoreCase("countryCode")) {
                       countryInfo.setCountryCode(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("countryName")) {
                       countryInfo.setCountryName(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("capital")) {
                       countryInfo.setCapital(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("continent")) {
                       countryInfo.setContinent(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("eat")) {
                       countryInfo.setEast(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("west")) {
                       countryInfo.setWest(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("north")) {
                       countryInfo.setNorth(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("south")) {
                       countryInfo.setSouth(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("population")) {
                       countryInfo.setPopulation(attributes.getValue(i).trim());
                   }
                   else if (name.equalsIgnoreCase("fname")) {
                       countryInfo.setFname(attributes.getValue(i).trim());
                   }
               }
           }
        }
        else if (qName.equalsIgnoreCase("entity")) {
           kafEntity = new KafEntity();
           corefSpans = new ArrayList<CorefTarget>();
           entity = true;
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("eid")) {
                   kafEntity.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafEntity.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("type")) {
                   kafEntity.setType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("subtype")) {
                   kafEntity.setSubtype(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("moneyISO")) {
                   kafEntity.setMoneyISO(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("dateISO")) {
                   kafEntity.setDateISO(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("property")) {
           kafProperty = new KafProperty();
           corefSpans = new ArrayList<CorefTarget>();
           property = true;
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("pid")) {
                   kafProperty.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafProperty.setId(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("type")) {
                   kafProperty.setType(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("coref")) {
           coreference = true;
           kafCoreferenceSet = new KafCoreferenceSet();
           corefSpans = new ArrayList<CorefTarget>();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("coid")) {
                   kafCoreferenceSet.setCoid(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("type")) {
                   kafCoreferenceSet.setType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafCoreferenceSet.setCoid(attributes.getValue(i).trim());
               }
           }
       }
       else if (qName.equalsIgnoreCase("timex3")) {
           spans = new ArrayList<String>();
           for (int i = 0; i < attributes.getLength(); i++) {
               String name = attributes.getQName(i);
               if (name.equalsIgnoreCase("coid")) {
                   kafCoreferenceSet.setCoid(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("type")) {
                   kafCoreferenceSet.setType(attributes.getValue(i).trim());
               }
               else if (name.equalsIgnoreCase("id")) {
                   kafCoreferenceSet.setCoid(attributes.getValue(i).trim());
               }
           }
       }
       else {
          ///
       }
    }//--startElement

    /**
     * @TODO fix predicate and role processing
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
            if (qName.equalsIgnoreCase("chunk")) {
                kafChunk.setSpans(spans);
                for (int i = 0; i < spans.size(); i++) {
                     String span = (String) spans.get(i);
                     if (TermToChunk.containsKey(span)) {
                         ArrayList<String> chunks = TermToChunk.get(span);
                         chunks.add(kafChunk.getCid());
                         TermToChunk.put(span, chunks);
                     }
                     else {
                         ArrayList<String> chunks = new ArrayList<String>();
                         chunks.add(kafChunk.getCid());
                         TermToChunk.put(span, chunks);
                     }
                }
                spans = new ArrayList<String>();
                kafChunkList.add(kafChunk);
            }
            else if (qName.equalsIgnoreCase("timex3")) {
                kafTimex.setSpans(spans);
                for (int i = 0; i < spans.size(); i++) {
                     String span = (String) spans.get(i);
                     if (TokenToTimex.containsKey(span)) {
                         ArrayList<String> chunks = TokenToTimex.get(span);
                         chunks.add(kafTimex.getId());
                         TokenToTimex.put(span, chunks);
                     }
                     else {
                         ArrayList<String> chunks = new ArrayList<String>();
                         chunks.add(kafTimex.getId());
                         TokenToTimex.put(span, chunks);
                     }
                }
                spans = new ArrayList<String>();
                kafTimexLayer.add(kafTimex);
            }
            else if (qName.equalsIgnoreCase("raw")) {
                rawText = value.trim();
            }
            else if (qName.equalsIgnoreCase("opinion")) {
                this.kafOpinionArrayList.add(kafOpinion);
                kafOpinion = new KafOpinion();

            }
            else if (qName.equalsIgnoreCase("opinion_expression")) {
                kafOpinion.setSpansOpinionExpression(spans);
                for (int i = 0; i < kafOpinion.getSpansOpinionExpression().size(); i++) {
                    String span = (String) kafOpinion.getSpansOpinionExpression().get(i);
                    if (TermToOpinions.containsKey(span)) {
                        ArrayList<String> opinions = TermToOpinions.get(span);
                        opinions.add(kafOpinion.getOpinionId());
                        TermToOpinions.put(span, opinions);
                    }
                    else {
                        ArrayList<String> opinions = new ArrayList<String>();
                        opinions.add(kafOpinion.getOpinionId());
                        TermToOpinions.put(span, opinions);
                    }
                }
                for (int i = 0; i < kafOpinion.getSpansOpinionHolder().size(); i++) {
                    String span = (String) kafOpinion.getSpansOpinionHolder().get(i);
                    if (TermToOpinions.containsKey(span)) {
                        ArrayList<String> opinions = TermToOpinions.get(span);
                        opinions.add(kafOpinion.getOpinionId());
                        TermToOpinions.put(span, opinions);
                    }
                    else {
                        ArrayList<String> opinions = new ArrayList<String>();
                        opinions.add(kafOpinion.getOpinionId());
                        TermToOpinions.put(span, opinions);
                    }
                }
                for (int i = 0; i < kafOpinion.getSpansOpinionTarget().size(); i++) {
                    String span = (String) kafOpinion.getSpansOpinionTarget().get(i);
                    if (TermToOpinions.containsKey(span)) {
                        ArrayList<String> opinions = TermToOpinions.get(span);
                        opinions.add(kafOpinion.getOpinionId());
                        TermToOpinions.put(span, opinions);
                    }
                    else {
                        ArrayList<String> opinions = new ArrayList<String>();
                        opinions.add(kafOpinion.getOpinionId());
                        TermToOpinions.put(span, opinions);
                    }
                }
                spans = new ArrayList<String>();
            }
            else if (qName.equalsIgnoreCase("opinion_holder")) {
                /*
                   <opinion_holder type="SW/AC" >
                       <span target id="t1.1"></span>
                   </opinion_holder>
                */
                kafOpinion.setSpansOpinionHolder(spans);
                spans = new ArrayList<String>();
            }
            else if (qName.equalsIgnoreCase("opinion_target")) {
                /*
                   <opinion_target
                       <span> <id>t1.6</id><id>t1.7</id><id>t1.8</id></span>
                   </opinion_target>
                */
                kafOpinion.setSpansOpinionTarget(spans);
                spans = new ArrayList<String>();
            }
            else if (qName.equalsIgnoreCase("dep")) {
                 String span = kafDep.getFrom();
                 if (TermToDeps.containsKey(span)) {
                     ArrayList<KafDep> deps = TermToDeps.get(span);
                     deps.add(kafDep);
                     TermToDeps.put(span, deps);
                 }
                 else {
                     ArrayList<KafDep> deps = new ArrayList<KafDep>();
                     deps.add(kafDep);
                     TermToDeps.put(span, deps);
                 }
                 span = kafDep.getTo();
                 if (TermToDeps.containsKey(span)) {
                     ArrayList<KafDep> deps = TermToDeps.get(span);
                     deps.add(kafDep);
                     TermToDeps.put(span, deps);
                 }
                 else {
                     ArrayList<KafDep> deps = new ArrayList<KafDep>();
                     deps.add(kafDep);
                     TermToDeps.put(span, deps);
                 }
            }
            else if (qName.equalsIgnoreCase("isi-event")) {
                kafEventISI.setSpans(spans);
                spans = new ArrayList<String>();
                kafEventISIList.add(kafEventISI);
            }
            else if (qName.equalsIgnoreCase("t")) {
                kafConstituencyTerminal.setSpans(spans);
                spans = new ArrayList<String>();
                kafConstituencyTree.addTerminals(kafConstituencyTerminal);
            }
            else if (qName.equalsIgnoreCase("tree")) {
                kafConstituencyTrees.add(kafConstituencyTree);
                kafConstituencyTree = new KafConstituencyTree();
            }
            else if (qName.equalsIgnoreCase("term")) {
                kafTerm.setSpans(spans);
                //if (!COMPONENT && !PREDICATE && !ROLE) {
                    /// to be sure that these senses are not for other sublayers
                    if (senseTags.size() > 0) {
                        kafTerm.setSenseTags(senseTags);
                        //// set to empty since they can also apply to components...
                        senseTags = new ArrayList<KafSense>();
                    }
                //}
                for (int i = 0; i < spans.size(); i++) {
                     String span = (String) spans.get(i);
                     WordFormToTerm.put(span, kafTerm.getTid());
                }
                
                if (checkKafConsistency)
                {
                    // check for unique termIds
                    //System.out.println(kafTerm.getTid());
                    if (termMap.containsKey(kafTerm.getTid()))
                    {
                    	//System.out.println("duplicate termId " + kafTerm.getTid());
                    	if (! reportedDuplicateTerms)
                    	{
                    		System.out.println("  ***** duplicate termId " + kafTerm.getTid());
                    		reportedDuplicateTerms = true;
                    	}
                    }
                    else
                    {
                    	termMap.put(kafTerm.getTid(), kafTerm);
                    }
                }

                if (kafTermSentiment.hasValue()) {
                    kafTerm.setKafTermSentiment(kafTermSentiment);
                    kafTermSentiment = new KafTermSentiment();
                }

                kafTermList.add(kafTerm);
                TermToWord.put(kafTerm.getTid(), spans);

                ///// THIS HACK IS NEEDED BECAUSE COMPONENTS DO NOT HAVE A SPAN
                ///// WE USE THE SPAN OF THE TERM FOR EACH COMPONENT AS A HACK!!!!
                for (int i = 0; i < kafTerm.getComponents().size(); i++) {
                    TermComponent component = kafTerm.getComponents().get(i);
                    TermToWord.put(component.getId(), spans);
                }
                spans = new ArrayList<String>();
                senseTags = new ArrayList<KafSense>();
            }
            else if (qName.equalsIgnoreCase("component")) {
                COMPONENT = false;
                if (senseTagsComponents.size()>0) {
                    termComponent.setSenseTags(senseTagsComponents);
                    senseTagsComponents = new ArrayList<KafSense>();
                    //// set to empty since they can also apply to terms...
                }
                kafTerm.addComponents(termComponent);
            }
            else if (qName.equalsIgnoreCase("wf")) {
                kafWordForm.setWf(value.trim());
                kafWordFormList.add(kafWordForm);
                wordFormMap.put(kafWordForm.getWid(), kafWordForm);
//               IdToWord.put(kafWordForm.wid, kafWordForm.wf);
            }
            else if (qName.equalsIgnoreCase("tunit")) {
                kaftextUnit.setSpans(spans);
                spans = new ArrayList<String>();
                kafDiscourseList.add(kaftextUnit);
            }
            else if (qName.equalsIgnoreCase("kafreference"))
            {
                kafreference = false;
                kafReferenceArrayList.add(kafReference);
            }
            else if (qName.equalsIgnoreCase("externalRef")) {
                externalRefLevel--;
            }
            else if (qName.equalsIgnoreCase("date"))
            {   date.setKafReferences(kafReferenceArrayList);
                date.setDateInfo(dateInfo);
                this.kafDateArrayList.add(date);
            }
            else if (qName.equalsIgnoreCase("location"))  // version 1
            {
                if (placeObject) {
                    place = new GeoPlaceObject();
                    place.setpId(locId);
                    place.setKafReferences(kafReferenceArrayList);
                    place.setPlaceInfo(placeInfo);
                    place.setExternalReferences(senseTags);
                    this.kafPlaceArrayList.add(place);
                    senseTags = new ArrayList<KafSense>();
                }
                else if (countryObject) {
                    country = new GeoCountryObject();
                    country.setcId(locId);
                    country.setKafReferences(kafReferenceArrayList);
                    country.setCountryInfo(countryInfo);
                    country.setExternalReferences(senseTags);
                    this.kafCountryArrayList.add(country);
                    senseTags = new ArrayList<KafSense>();
                }
                placeObject = false;
                countryObject = false;
            }
            else if (qName.equalsIgnoreCase("place"))      /// version 2
            {
                if (placeObject) {
                    place = new GeoPlaceObject();
                    place.setpId(locId);
                    place.setKafReferences(kafReferenceArrayList);
                    place.setPlaceInfo(placeInfo);
                    place.setExternalReferences(senseTags);
                    this.kafPlaceArrayList.add(place);
                    senseTags = new ArrayList<KafSense>();
                }
                placeObject = false;
                countryObject = false;
            }
            else if (qName.equalsIgnoreCase("country"))       /// version 2
            {
                if (countryObject) {
                    country = new GeoCountryObject();
                    country.setcId(locId);
                    country.setKafReferences(kafReferenceArrayList);
                    country.setCountryInfo(countryInfo);
                    country.setExternalReferences(senseTags);
                    this.kafCountryArrayList.add(country);
                    senseTags = new ArrayList<KafSense>();
                }
            }
            else if (qName.equalsIgnoreCase("property"))       /// version 2
            {
                    this.kafPropertyArrayList.add(kafProperty);
                    kafProperty = new KafProperty();
                    property = false;
            }

            else if (qName.equalsIgnoreCase("entity"))       /// version 2
            {
                kafEntity.setExternalReferences(senseTags);
                senseTags = new ArrayList<KafSense>();
                this.kafEntityArrayList.add(kafEntity);
                kafEntity = new KafEntity();
                entity = false;
            }
            else if (qName.equalsIgnoreCase("coref"))       /// version 2
            {
                    this.kafCorefenceArrayList.add(kafCoreferenceSet);
                    kafCoreferenceSet = new KafCoreferenceSet();
                    coreference = false;
            }
            else if (qName.equalsIgnoreCase("span")) {
                if (coreference) {
                    kafCoreferenceSet.addSetsOfSpans(corefSpans);
                    for (int i = 0; i < corefSpans.size(); i++) {
                        CorefTarget span = corefSpans.get(i);
                        if (TermToProperties.containsKey(span.getId())) {
                            ArrayList<String> corefIds = TermToProperties.get(span.getId());
                            corefIds.add(kafCoreferenceSet.getCoid());
                            TermToProperties.put(span.getId(), corefIds);
                        }
                        else {
                            ArrayList<String> corefIds = new ArrayList<String>();
                            corefIds.add(kafCoreferenceSet.getCoid());
                            TermToProperties.put(span.getId(), corefIds);
                        }
                    }
                    corefSpans = new ArrayList<CorefTarget>();
                }
                else if (entity) {
                    kafEntity.addSetsOfSpans(corefSpans);
                    for (int i = 0; i < corefSpans.size(); i++) {
                        CorefTarget span = corefSpans.get(i);
                        if (TermToEntities.containsKey(span.getId())) {
                            ArrayList<String> entityIds = TermToEntities.get(span.getId());
                            entityIds.add(kafEntity.getId());
                            TermToEntities.put(span.getId(), entityIds);
                        }
                        else {
                            ArrayList<String> entityIds = new ArrayList<String>();
                            entityIds.add(kafEntity.getId());
                            TermToEntities.put(span.getId(), entityIds);
                        }
                    }
                    corefSpans = new ArrayList<CorefTarget>();
                }
                else if (property) {
                    kafProperty.addSetsOfSpans(corefSpans);
                    for (int i = 0; i < corefSpans.size(); i++) {
                        CorefTarget span = corefSpans.get(i);
                        if (TermToProperties.containsKey(span.getId())) {
                            ArrayList<String> propertyIds = TermToProperties.get(span.getId());
                            propertyIds.add(kafProperty.getId());
                            TermToProperties.put(span.getId(), propertyIds);
                        }
                        else {
                            ArrayList<String> propertyIds = new ArrayList<String>();
                            propertyIds.add(kafProperty.getId());
                            TermToProperties.put(span.getId(), propertyIds);
                        }
                    }
                    corefSpans = new ArrayList<CorefTarget>();
                }
            }
            else if (qName.equalsIgnoreCase("predicate")) {
                kafEvent.setSpans(predicateSpans);
                kafEvent.setExternalReferences(predicateSenseTags);
                kafEvent.setParticipants(participantArrayList);
                kafEventArrayList.add(kafEvent);

                //init
                kafEvent = new KafEvent();
                participantArrayList = new ArrayList<KafParticipant>();
                predicateSpans = new ArrayList<CorefTarget>();
                predicateSenseTags = new ArrayList<KafSense>();
                PREDICATE = false;
            }
            else if (qName.equalsIgnoreCase("role")) {
                kafParticipant.setSpans(roleSpans);
                kafParticipant.setExternalReferences(roleSenseTags);
                participantArrayList.add(kafParticipant);

                //init
                kafParticipant = new KafParticipant();
                roleSpans = new ArrayList<CorefTarget>();
                roleSenseTags = new ArrayList<KafSense>();
                ROLE = false;
               /////
            }
    }


    public void characters(char ch[], int start, int length)
            throws SAXException {
        value += new String(ch, start, length);
    }

    /**	Returns the source language specified in the Kaf-header,
     * or 'en' as default.
     * @return
     */
    public String getLanguage()
    {
    	if (this.kafMetaData.getLanguage() != null)
    		return this.kafMetaData.getLanguage();
    	return "en";
    }
    
    /**	Returns the source language specified in the Kaf-header,
     * or the given language as default.
     * @return
     */
    public String getLanguage(String l)
    {
    	if (this.kafMetaData.getLanguage() != null)
    		return this.kafMetaData.getLanguage();
    	return l;
    }
    
    /**	Returns the Id of the document
     * 
     * @return
     */
    public String getDocId()
    {
    	return this.kafMetaData.getDocId();
    }
    
    /**	Returns the id of the page with the given KafTerm.
     * 
     * @param term
     * @return
     */
    public String getPageId(KafTerm term)
    {
		String wid = term.getFirstSpan();
		if ((wid.length() > 0) && wordFormMap.containsKey(wid))
		{
			KafWordForm word = wordFormMap.get(wid);
			return word.getPage();
		}
		return "-1";
    }
    
    public String getSentenceId(KafTerm term)
    {
		String wid = term.getFirstSpan();
		if ((wid.length() > 0) && wordFormMap.containsKey(wid))
		{
			KafWordForm word = wordFormMap.get(wid);
			return word.getSent();
		}
		return "-1";
    }

    public String getSentenceId(String termId)
    {
        KafTerm kafTerm = this.getTerm(termId);
		String wid = kafTerm.getFirstSpan();
		if ((wid.length() > 0) && wordFormMap.containsKey(wid))
		{
			KafWordForm word = wordFormMap.get(wid);
			return word.getSent();
		}
		return "-1";
    }

    public String getSentenceIdForToken(String token)
    {   if (wordFormMap.containsKey(token))
		{
			KafWordForm word = wordFormMap.get(token);
			return word.getSent();
		}
		return "-1";
    }

    public KafMetaData getKafMetaData() {
        return kafMetaData;
    }

    public List<KafTerm> getKafTerms()
    {
    	return kafTermList;
    }

    public ArrayList<GeoCountryObject> getKafCountryArrayList() {
        return kafCountryArrayList;
    }

    public ArrayList<KafChunk> getKafChunkList() {
        return kafChunkList;
    }

    public ArrayList<KafDep> getKafDepList() {
        return kafDepList;
    }

    public ArrayList<KafTextUnit> getKafDiscourseList() {
        return kafDiscourseList;
    }

    public ArrayList<KafEventISI> getKafEventISIList() {
        return kafEventISIList;
    }

    public ArrayList<KafEvent> getKafEventArrayList() {
        return kafEventArrayList;
    }

    public ArrayList<GeoPlaceObject> getKafPlaceArrayList() {
        return kafPlaceArrayList;
    }

    public ArrayList<KafWordForm> getKafWordFormList() {
        return kafWordFormList;
    }

    public ArrayList<KafReference> getKafReferenceArrayList() {
        return kafReferenceArrayList;
    }

    public ArrayList<KafOpinion> getKafOpinionArrayList() {
        return kafOpinionArrayList;
    }


    /**
     * Gives the sentence identifier for a term identifiewr
     * @param termId
     * @return
     */
    public String termToSentenceId (String termId) {
        String sentenceId = "";
        ArrayList<String> wordIds = this.TermToWord.get(termId);
        for (int i = 0; i < wordIds.size(); i++) {
            String wid = wordIds.get(i);
            KafWordForm kafWordForm = this.getWordForm(wid);
            if (!kafWordForm.getSent().isEmpty()) {
                sentenceId = kafWordForm.getSent();
                break;
            }
        }
        return sentenceId;
    }


    /**
     *
     * @param span
     * @return
     */
     public boolean contentWord (String span) {
        KafTerm kafTerm = getTerm(span);
        if (kafTerm!=null) {
            String pos = kafTerm.getPos();
            if (pos.toLowerCase().startsWith("n")) {
                return true;
            }
            if (pos.toLowerCase().startsWith("v")) {
                return true;
            }
            if (pos.toLowerCase().startsWith("a")) {
                return true;
            }
            if (pos.toLowerCase().startsWith("g")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts how many of the span elements are content words
     * @param spans
     * @return
     */
     public int getNumberContentWords (ArrayList<String> spans) {
        int nContentWords = 0;
        for (int i = 0; i < spans.size(); i++) {
            String span = spans.get(i);
            if (contentWord(span)) {
                nContentWords++;
            }
        }
        return nContentWords;
    }

    /**
     * Gives all GeoCountries in the same sentence is a term
     * @param termId
     * @return
     */
    public ArrayList<GeoCountryObject> getCountriesForTermId (String termId) {
        String sentenceId = termToSentenceId(termId);
       // System.out.println("sentenceId = " + sentenceId);
        ArrayList<GeoCountryObject> countries = new ArrayList<GeoCountryObject>();
        for (int i = 0; i < kafCountryArrayList.size(); i++) {
            GeoCountryObject geoCountryObject = kafCountryArrayList.get(i);
            if (!geoCountryObject.getCountryInfo().getCountryName().isEmpty()) {
                for (int j = 0; j < geoCountryObject.getKafReferences().size(); j++) {
                    KafReference reference = geoCountryObject.getKafReferences().get(j);
                    String geoTermId = reference.getTerm();
                    if (sentenceId.equals(termToSentenceId(geoTermId))) {
                        boolean match = false;
                        for (int k = 0; k < countries.size(); k++) {
                            GeoCountryObject object = countries.get(k);
                            if (object.getcId().equals(geoCountryObject.getcId())) {
                                match = true;
                                break;
                            }
                        }
                        if (!match) {
                       //     System.out.println("adding geoCountryObject = " + geoCountryObject.getcId());
                            countries.add(geoCountryObject);
                        }
                        else {
                       //     System.out.println("duplicate geoCountryObject = " + geoCountryObject.getcId());
                        }
                    //    System.out.println("termToSentenceId(geoTermId) = " + termToSentenceId(geoTermId));

                     //   System.out.println("geoCountryObject = " + geoCountryObject.toEventFormat());
                     //   return countries;
                    }
                }
            }
        }
        return countries;
    }

    /**
     * Gives all GeoPlaces in the same sentence is a term
     * @param termId
     * @return
     */
    public ArrayList<GeoPlaceObject> getPlacesForTermId (String termId) {
        String sentenceId = termToSentenceId(termId);
        ArrayList<GeoPlaceObject> places = new ArrayList<GeoPlaceObject>();
        for (int i = 0; i < kafPlaceArrayList.size(); i++) {
            GeoPlaceObject geoPlaceObject = kafPlaceArrayList.get(i);
            if (!geoPlaceObject.getPlaceInfo().getName().isEmpty()) {
                for (int j = 0; j < geoPlaceObject.getKafReferences().size(); j++) {
                    KafReference reference = geoPlaceObject.getKafReferences().get(j);
                    String geoTermId = reference.getTerm();
                    if (sentenceId.equals(termToSentenceId(geoTermId))) {
                    //    System.out.println("termToSentenceId(geoTermId) = " + termToSentenceId(geoTermId));
                    ///    System.out.println("geoPlaceObject = " + geoPlaceObject.toEventFormat());
                        boolean match = false;
                        for (int k = 0; k < places.size(); k++) {
                            GeoPlaceObject object = places.get(k);
                            if (object.getpId().equals(geoPlaceObject.getpId())) {
                                match = true;
                                break;
                            }
                        }
                        if (!match) {
                            places.add(geoPlaceObject);
                        }

                       // return places;
                    }
                }
            }
        }
        return places;
    }

    /**
     * Gives all IsoDates in the same sentence is a term
     * @param termId
     * @return
     */
    public ArrayList<ISODate> getDatesForTermId (String termId) {
        String sentenceId = termToSentenceId(termId);
        ArrayList<ISODate> dates = new ArrayList<ISODate>();
        for (int i = 0; i < kafDateArrayList.size(); i++) {
            ISODate isoDateObject = kafDateArrayList.get(i);
            for (int j = 0; j < isoDateObject.getKafReferences().size(); j++) {
                KafReference reference = isoDateObject.getKafReferences().get(j);
                String geoTermId = reference.getTerm();
                if (sentenceId.equals(termToSentenceId(geoTermId))) {
                    boolean match = false;
                    for (int k = 0; k < dates.size(); k++) {
                        ISODate object = dates.get(k);
                        if (object.getDid().equals(isoDateObject.getDid())) {
                            match = true;
                            break;
                        }
                    }
                    if (!match) {
                        dates.add(isoDateObject);
                    }
                 //   return dates;
                }
            }
        }
        return dates;
    }

    /**
     * Gives the most frequent country
     * @return
     */
    public GeoCountryObject getMostFrequentGeoCountry () {
        GeoCountryObject geoCountryObject = null;
        int nReferences = 0;
        for (int i = 0; i < kafCountryArrayList.size(); i++) {
            GeoCountryObject object = kafCountryArrayList.get(i);
            if (object.getKafReferences().size()>nReferences) {
                geoCountryObject = object;
            }
        }
        return geoCountryObject;
    }

    /**
     * Gives the most frequent country
     * @return
     */
    public GeoPlaceObject getMostFrequentGeoPlace () {
        GeoPlaceObject geoPlaceObject = null;
        int nReferences = 0;
        for (int i = 0; i < kafPlaceArrayList.size(); i++) {
            GeoPlaceObject object = kafPlaceArrayList.get(i);
            if (object.getKafReferences().size()>nReferences) {
                geoPlaceObject = object;
            }
        }
        return geoPlaceObject;
    }

    /**
     * Gives the most frequent iso date
     * @return
     */
    public ISODate getMostFrequentDate () {
        ISODate dateObject = null;
        int nReferences = 0;
        for (int i = 0; i < kafDateArrayList.size(); i++) {
            ISODate object = kafDateArrayList.get(i);
            if (object.getKafReferences().size()>nReferences) {
                dateObject = object;
            }
        }
        return dateObject;
    }

    /** returns best country
     *
     * @param termId
     * @return
     */
    public ArrayList<GeoCountryObject> getCountries (String termId) {
        ArrayList<GeoCountryObject> geoCountryObjects = new ArrayList<GeoCountryObject>();
        geoCountryObjects = getCountriesForTermId(termId);
        if (geoCountryObjects.size()==0) {
            GeoCountryObject geoCountry = getMostFrequentGeoCountry();
            if ((geoCountry!=null) && !geoCountry.getCountryInfo().getCountryName().isEmpty()) {

                geoCountryObjects.add(geoCountry);
            }
        }
        return geoCountryObjects;
    }

    /** returns best place
     *
     * @param termId
     * @return
     */
    public ArrayList<GeoPlaceObject> getPlaces (String termId) {
        ArrayList<GeoPlaceObject> geoPlaceObjects = new ArrayList<GeoPlaceObject>();
        geoPlaceObjects = getPlacesForTermId(termId);
        if (geoPlaceObjects.size()==0) {
            GeoPlaceObject geoPlace = getMostFrequentGeoPlace();
            if ((geoPlace!=null) && !geoPlace.getPlaceInfo().getName().isEmpty()) {
                geoPlaceObjects.add(geoPlace);
            }
        }
        return geoPlaceObjects;
    }

    /** returns best date
     *
     * @param termId
     * @return
     */
    public ArrayList<ISODate> getDates (String termId) {
        ArrayList<ISODate> isoDates = new ArrayList<ISODate>();
        isoDates = getDatesForTermId(termId);
        if (isoDates.size()==0) {
            ISODate isoDate = getMostFrequentDate();
            if (isoDate!=null) {
                isoDates.add(isoDate);
            }
        }
        return isoDates;
    }


    
    /**	Writes an xml-representation of the kaf-header and discourse units to
     * file.
     * @param fos
     */
    public void writeDiscourseUnitsToFile(FileOutputStream fos)
    {
    	try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();

			Document xmldoc = impl.createDocument(null, "KAF", null);
			Element root = xmldoc.getDocumentElement();
			root.setAttribute("xml:lang", kafMetaData.getLanguage());
			root.appendChild(kafMetaData.toHeaderXML(xmldoc));

			Element tunits = xmldoc.createElement("tunits");
			for (int i = 0; i < this.kafDiscourseList.size(); i++) 
			{
				KafTextUnit kaf  = (KafTextUnit) kafDiscourseList.get(i);
				tunits.appendChild(kaf.toXML(xmldoc));
			}
			root.appendChild(tunits);
			
			// Serialisation through Tranform.
			DOMSource domSource = new DOMSource(xmldoc);
			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", 4);  
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			
			StreamResult streamResult = new StreamResult(new OutputStreamWriter(fos,"UTF-8"));
			serializer.transform(domSource, streamResult); 
			fos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }


    
    /**	Writes all kaf-info about the wordforms in the given KafTextUnit
     * to the given outputstream.
     * @param ktu
     * @param fos
     */
    public void writePageToFile(KafTextUnit ktu, FileOutputStream fos)
    {
    	try
    	{
    		writePageToXML(ktu, new OutputStreamWriter(fos, "UTF-8"));
    		fos.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    /**	Writes all kaf-info about the wordforms in the given KafTextUnit
     * to the given writer.
     * WARNING: this method does not guarantee a UTF-8 encoding; you must
     * see to that yourself when constructing the Writer!
     * @param ktu
     * @param 
     */
    public void writePageToXML(KafTextUnit ktu, Writer writer)
    {
    	try
		{   		
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();


			Document xmldoc = impl.createDocument(null, "KAF", null);
			Element root = xmldoc.getDocumentElement();
			root.setAttribute("xml:lang", kafMetaData.getLanguage());
			root.appendChild(kafMetaData.toHeaderXML(xmldoc));

			ArrayList<String> termIdScope = new ArrayList<String>();
			boolean scope = true;
			
			Element text = xmldoc.createElement("text");
			for (int i = 0; i < this.kafWordFormList.size(); i++) 
			{	//Add only those KafWordForms that are on the correct page
				KafWordForm kaf  = (KafWordForm) kafWordFormList.get(i);
				if (ktu.getSpans().contains(kaf.getWid())) 
					text.appendChild(kaf.toXML(xmldoc));
			}
			root.appendChild(text);

			Element terms = xmldoc.createElement("terms");
			for (int i = 0; i < this.kafTermList.size(); i++) 
			{	//For each term, check if it is within the scope of the KafTextUnit
				KafTerm kaf  = (KafTerm) kafTermList.get(i);
				scope = true;
                for (int j = 0; j < kaf.spans.size(); j++) 
                {
                   String span = (String) kaf.spans.get(j);
                    if (!ktu.getSpans().contains(span)) 
                    {
                        scope = false;
                        break;
                    }
                }	
                if (scope) 
                {	//if it is, add it to the XML
                    termIdScope.add(kaf.getTid());
                    terms.appendChild(kaf.toXML(xmldoc));
                }
			}
			root.appendChild(terms);

			Element chunks = xmldoc.createElement("chunks");
			for (int i = 0; i < this.kafChunkList.size(); i++) 
			{
				KafChunk kaf  = (KafChunk) kafChunkList.get(i);
				scope = true;
                for (int j = 0; j < kaf.spans.size(); j++) 
                {
                   String span = (String) kaf.spans.get(j);
                    if (!termIdScope.contains(span)) 
                    {
                        scope = false;
                        break;
                    }
                }
                if (scope) 
               		chunks.appendChild(kaf.toXML(xmldoc));
			}
			root.appendChild(chunks);

			Element deps = xmldoc.createElement("deps");
			for (int i = 0; i < this.kafDepList.size(); i++) 
			{
				KafDep kaf  = (KafDep) kafDepList.get(i);
				if (termIdScope.contains(kaf.from) && termIdScope.contains(kaf.to))
					deps.appendChild(kaf.toXML(xmldoc));
			}
			root.appendChild(deps);

            if (kafOpinionArrayList.size()>0) {
                Element opinions = xmldoc.createElement("opinions");
                for (int i = 0; i < this.kafOpinionArrayList.size(); i++) {
                    KafOpinion kaf  = (KafOpinion) kafOpinionArrayList.get(i);
                    scope = true;
                    for (int j = 0; j < kaf.getSpansOpinionExpression().size(); j++){
                        String span = (String) kaf.getSpansOpinionExpression().get(j);
                        if (!termIdScope.contains(span)){
                            scope = false;
                            break;
                        }
                    }
                    if (scope)
                        opinions.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(opinions);
            }

            if (kafEntityArrayList.size()>0) {
                Element entities = xmldoc.createElement("entities");
                for (int i = 0; i < this.kafEntityArrayList.size(); i++) {
                    KafEntity kaf  = kafEntityArrayList.get(i);
                    scope = true;
                    for (int j = 0; j < kaf.getSetsOfSpans().size(); j++){
                        ArrayList<CorefTarget> corefTargets = kaf.getSetsOfSpans().get(j);
                        for (int k = 0; k < corefTargets.size(); k++) {
                            CorefTarget corefTarget = corefTargets.get(k);
                            if (!termIdScope.contains(corefTarget.getId())){
                                scope = false;
                                break;
                            }

                        }
                    }
                    if (scope)
                        entities.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(entities);
            }

            if (kafPropertyArrayList.size()>0) {
                Element properties = xmldoc.createElement("properties");
                for (int i = 0; i < this.kafPropertyArrayList.size(); i++) {
                    KafProperty kaf  = kafPropertyArrayList.get(i);
                    scope = true;
                    for (int j = 0; j < kaf.getSetsOfSpans().size(); j++){
                        ArrayList<CorefTarget> corefTargets = kaf.getSetsOfSpans().get(j);
                        for (int k = 0; k < corefTargets.size(); k++) {
                            CorefTarget corefTarget = corefTargets.get(k);
                            if (!termIdScope.contains(corefTarget.getId())){
                                scope = false;
                                break;
                            }

                        }
                    }
                    if (scope)
                        properties.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(properties);
            }

            if (kafCorefenceArrayList.size()>0) {
                Element coreferences = xmldoc.createElement("coreferences");
                for (int i = 0; i < this.kafCorefenceArrayList.size(); i++) {
                    KafCoreferenceSet kaf  = kafCorefenceArrayList.get(i);
                    scope = true;
                    for (int j = 0; j < kaf.getSetsOfSpans().size(); j++){
                        ArrayList<CorefTarget> corefTargets = kaf.getSetsOfSpans().get(j);
                        for (int k = 0; k < corefTargets.size(); k++) {
                            CorefTarget corefTarget = corefTargets.get(k);
                            if (!termIdScope.contains(corefTarget.getId())){
                                scope = false;
                                break;
                            }

                        }
                    }
                    if (scope)
                        coreferences.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(coreferences);
            }


            if (kafEventISIList.size()>0) {
                Element isiEvents  = xmldoc.createElement("isi-events");
                for (int i = 0; i < kafEventISIList.size(); i++) {
                    KafEventISI eventISI = kafEventISIList.get(i);
                    scope = true;
                    for (int j = 0; j < eventISI.spans.size(); j++)
                    {
                        String span = (String) eventISI.spans.get(j);
                        if (!termIdScope.contains(span))
                        {
                            scope = false;
                            break;
                        }
                    }
                    if (scope)
                        isiEvents.appendChild(eventISI.toXML(xmldoc));
                }
                root.appendChild(isiEvents);
            }

            if (kafEventArrayList.size()>0) {
                Element events  = xmldoc.createElement("events");
                for (int i = 0; i < kafEventArrayList.size(); i++) {
                    KafEvent event = kafEventArrayList.get(i);
                    scope = true;
                    for (int j = 0; j < event.getSpans().size(); j++)
                    {
                        CorefTarget span = event.getSpans().get(j);
                        if (!termIdScope.contains(span.getId()))
                        {
                            scope = false;
                            break;
                        }
                    }
                    if (scope)
                        events.appendChild(event.toXML(xmldoc));
                }
                root.appendChild(events);
            }

			Element tunits = xmldoc.createElement("tunits");
			for (int i = 0; i < this.kafDiscourseList.size(); i++) 
			{
				KafTextUnit kaf  = (KafTextUnit) kafDiscourseList.get(i);
				if (!kaf.getType().equalsIgnoreCase(KafDiscourseUnits.sPAGE))
				{
                    scope = true;
                    for (int j = 0; j < kaf.getSpans().size(); j++) 
                    {
                       String span = kaf.getSpans().get(j);
                        if (!ktu.getSpans().contains(span)) 
                        {
                            scope = false;
                            break;
                        }
                    }
                    if (scope) 
                    	tunits.appendChild(kaf.toXML(xmldoc));
                }
			}
			root.appendChild(tunits);
			
			// Serialisation through Tranform.
			DOMSource domSource = new DOMSource(xmldoc);
			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", 4);  
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			//serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
            //serializer.setParameter("format-pretty-print", Boolean.TRUE);
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StreamResult streamResult = new StreamResult(writer);
			serializer.transform(domSource, streamResult); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    public void addLP(String layer, String name, String version)
    {
    	Calendar cal = Calendar.getInstance();
    	String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        String time = new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
    	kafMetaData.addLayer(layer, name, version, date + "T" + time + "Z", null, null);
    }

    public String getXML()
    {
    	return getXML(false);
    }
    
    public String getXML(boolean onlyNE)
    {
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	writeKafToStream(out, onlyNE);
    	return out.toString();
    }
    
    /**	Writes an XML-representation of the kaf-file to the given FileOutputStream.
     *  
     * @param fos FileOutputStream
     * @param onlyNE If true, it only writes the kaf-header and named entities 
     * (dates and locations); if false, all the contents of the kaf-file.
     */
    public void writeKafToFile(FileOutputStream fos, boolean onlyNE)
    {
    	try
    	{
    		writeKafToStream(fos, onlyNE);
    		fos.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }

    /**	Writes an XML-representation of the kaf-file to the given FileOutputStream.
     *
     * @param fos FileOutputStream
     */
    public void writeKafToFile(FileOutputStream fos)
    {
    	try
    	{
    		writeKafToStream(fos);
    		fos.close();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }

    public void writeKafToStream(OutputStream stream, boolean onlyNE)
    {
    	try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();

			Document xmldoc = impl.createDocument(null, "KAF", null);
			xmldoc.setXmlStandalone(false);
			Element root = xmldoc.getDocumentElement();
			root.setAttribute("xml:lang", kafMetaData.getLanguage());
			root.appendChild(kafMetaData.toHeaderXML(xmldoc));

			if (!onlyNE)
			{
				Element text = xmldoc.createElement("text");
				for (int i = 0; i < this.kafWordFormList.size(); i++) {
					KafWordForm kaf  = (KafWordForm) kafWordFormList.get(i);
					text.appendChild(kaf.toXML(xmldoc));
				}
				root.appendChild(text);

				Element terms = xmldoc.createElement("terms");
				for (int i = 0; i < this.kafTermList.size(); i++) {
					KafTerm kaf  = (KafTerm) kafTermList.get(i);
					terms.appendChild(kaf.toXML(xmldoc));
				}
				root.appendChild(terms);

				Element deps = xmldoc.createElement("deps");
				for (int i = 0; i < this.kafDepList.size(); i++) {
					KafDep kaf  = (KafDep) kafDepList.get(i);
                    /// the next checks are needed because some parser create reference to nonexisting elements
                    if ((this.getTerm(kaf.from)!=null) && (this.getTerm(kaf.to)!=null)) {
                        deps.appendChild(kaf.toXML(xmldoc));
                    }
                }
				root.appendChild(deps);

				Element chunks = xmldoc.createElement("chunks");
				for (int i = 0; i < this.kafChunkList.size(); i++) {
					KafChunk kaf  = (KafChunk) kafChunkList.get(i);
                    /// the next checks are needed because some parser create reference to nonexisting elements
                    boolean nullSpan = false;
                    for (int j = 0; j < kaf.getSpans().size(); j++) {
                        String span = kaf.getSpans().get(j);
                        if (this.getTerm(span)==null) {
                            nullSpan = true;
                            break;
                        }
                    }
                    if (this.getTerm(kaf.getHead())!=null) {
                        if (!nullSpan) chunks.appendChild(kaf.toXML(xmldoc));
                    }
                }
				root.appendChild(chunks);

                if (kafOpinionArrayList.size()>0) {
                    Element opinions = xmldoc.createElement("opinions");
                    for (int i = 0; i < this.kafOpinionArrayList.size(); i++){
                        KafOpinion kaf  =  kafOpinionArrayList.get(i);
                        opinions.appendChild(kaf.toXML(xmldoc));
                    }
                    root.appendChild(opinions);
                }

                if (kafEntityArrayList.size()>0) {
                    Element entities = xmldoc.createElement("entities");
                    for (int i = 0; i < this.kafEntityArrayList.size(); i++) {
                        KafEntity kaf  = kafEntityArrayList.get(i);
                        entities.appendChild(kaf.toXML(xmldoc));
                    }
                    root.appendChild(entities);
                }

                if (kafPropertyArrayList.size()>0) {
                    Element properties = xmldoc.createElement("properties");
                    for (int i = 0; i < this.kafPropertyArrayList.size(); i++) {
                        KafProperty kaf  = kafPropertyArrayList.get(i);
                        properties.appendChild(kaf.toXML(xmldoc));
                    }
                    root.appendChild(properties);
                }

                if (kafCorefenceArrayList.size()>0) {
                    Element coreferences = xmldoc.createElement("coreferences");
                    for (int i = 0; i < this.kafCorefenceArrayList.size(); i++) {
                        KafCoreferenceSet kaf  = kafCorefenceArrayList.get(i);
                        coreferences.appendChild(kaf.toXML(xmldoc));
                    }
                    root.appendChild(coreferences);
                }


                if (kafConstituencyTrees.size()>0) {
                    Element constituency = xmldoc.createElement("kafConstituencyTree");
                    for (int i = 0; i < kafConstituencyTrees.size(); i++) {
                        KafConstituencyTree constituencyTree = kafConstituencyTrees.get(i);
                        constituency.appendChild(constituencyTree.toNafXML(xmldoc));
                    }
                    root.appendChild(constituency);
                }

                if (kafEventISIList.size()>0) {
                    Element isiEvents  = xmldoc.createElement("isi-events");
                    for (int i = 0; i < kafEventISIList.size(); i++) {
                        KafEventISI eventISI = kafEventISIList.get(i);
                        isiEvents.appendChild(eventISI.toXML(xmldoc));
                    }
                    root.appendChild(isiEvents);
                }


                if (kafEventArrayList.size()>0) {
                    Element events  = xmldoc.createElement("srls");

                    for (int i = 0; i < kafEventArrayList.size(); i++) {
                        KafEvent event = kafEventArrayList.get(i);
                        event.setTokenString(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(this, event.getSpanIds()));
                        for (int j = 0; j < event.getParticipants().size(); j++) {
                            KafParticipant kafParticipant = event.getParticipants().get(j);
                            kafParticipant.setTokenString(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(this, kafParticipant.getSpanIds()));
                        }
                        events.appendChild(event.toXML(xmldoc));
                    }
                    root.appendChild(events);
                }

                if (kafTimexLayer.size()>0) {
                    Element timexLayer = xmldoc.createElement("timeExpressions");

                    for (int i = 0; i < kafTimexLayer.size(); i++) {
                        KafTimex timex = kafTimexLayer.get(i);
                        timex.setTokenString(AddTokensAsCommentsToSpans.getTokenString(this, timex.getSpans()));
                        timexLayer.appendChild(timex.toNafXML(xmldoc));
                    }

                    root.appendChild(timexLayer);
                }

                if (kafFactualityLayer.size()>0) {
                    Element factualities = xmldoc.createElement("factualitylayer");

                    for (int i = 0; i < kafFactualityLayer.size(); i++) {
                        KafFactuality kafFactuality = kafFactualityLayer.get(i);
                        factualities.appendChild((kafFactuality.toNafXML(xmldoc)));
                    }
                    root.appendChild(factualities);
                }


                Element tunits = xmldoc.createElement("tunits");
				for (int i = 0; i < this.kafDiscourseList.size(); i++) {
					KafTextUnit kaf  = (KafTextUnit) kafDiscourseList.get(i);
					tunits.appendChild(kaf.toXML(xmldoc));
				}
				root.appendChild(tunits);
			}

			
			// Serialisation through Tranform.
			DOMSource domSource = new DOMSource(xmldoc);
			TransformerFactory tf = TransformerFactory.newInstance();
			//tf.setAttribute("indent-number", 4);  
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			//serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			serializer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            //serializer.setParameter("format-pretty-print", Boolean.TRUE);
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

          //  StreamResult streamResult = new StreamResult(new OutputStreamWriter(stream,encoding));
            StreamResult streamResult = new StreamResult(new OutputStreamWriter(stream));
			serializer.transform(domSource, streamResult);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }


    public void writeNafToStream(OutputStream stream)
    {
    	try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();

			Document xmldoc = impl.createDocument(null, "NAF", null);
			xmldoc.setXmlStandalone(false);
			Element root = xmldoc.getDocumentElement();
            root.setAttribute("version", NAFVERSION);
			root.setAttribute("xml:lang", kafMetaData.getLanguage());
			root.appendChild(kafMetaData.toNafHeaderXML(xmldoc));

            if (!rawText.isEmpty())  {
                Element text = xmldoc.createElement("raw");
                text.setTextContent(rawText);
                root.appendChild(text);
            }
            if (this.kafWordFormList.size()>0) {
                Element text = xmldoc.createElement("text");
                for (int i = 0; i < this.kafWordFormList.size(); i++) {
                    KafWordForm kaf  = kafWordFormList.get(i);
                    //System.out.println("kaf.getWf() = " + kaf.getWf());
                    text.appendChild(kaf.toNafXML(xmldoc));
                }
                root.appendChild(text);
            }

            if (this.kafTermList.size()>0) {
                Element terms = xmldoc.createElement("terms");
                for (int i = 0; i < this.kafTermList.size(); i++) {
                    KafTerm kaf  = kafTermList.get(i);
                    kaf.setTokenString(AddTokensAsCommentsToSpans.getTokenString(this, kaf.getSpans()));
                    terms.appendChild(kaf.toNafXML(xmldoc));
                }
                root.appendChild(terms);
            }

            if (this.kafDepList.size()>0) {
                Element deps = xmldoc.createElement("deps");
                for (int i = 0; i < this.kafDepList.size(); i++) {
                    KafDep kaf  = kafDepList.get(i);
                    String commentString = kaf.getTokensString(this);
                    Comment comment = xmldoc.createComment(commentString);
                    deps.appendChild(comment);
                    /// the next checks are needed because some parser create reference to nonexisting elements
                    if ((this.getTerm(kaf.from)!=null) && (this.getTerm(kaf.to)!=null)) {
                        deps.appendChild(kaf.toNafXML(xmldoc));
                    }
                }
                root.appendChild(deps);
            }

            if (this.kafChunkList.size()>0) {
                Element chunks = xmldoc.createElement("chunks");
                for (int i = 0; i < this.kafChunkList.size(); i++) {
                    KafChunk kaf  = kafChunkList.get(i);
                    kaf.setTokenString(this);
                    /// the next checks are needed because some parser create reference to nonexisting elements
                    boolean nullSpan = false;
                    for (int j = 0; j < kaf.getSpans().size(); j++) {
                        String span = kaf.getSpans().get(j);
                        if (this.getTerm(span)==null) {
                            nullSpan = true;
                            break;
                        }
                    }
                    if (this.getTerm(kaf.getHead())!=null) {
                        if (!nullSpan) chunks.appendChild(kaf.toNafXML(xmldoc));
                    }
                }
                root.appendChild(chunks);
            }

            if (kafOpinionArrayList.size()>0) {
                Element opinions = xmldoc.createElement("opinions");
                for (int i = 0; i < this.kafOpinionArrayList.size(); i++){
                    KafOpinion kaf  =  kafOpinionArrayList.get(i);
                    kaf.setTokenStrings(this);
                    opinions.appendChild(kaf.toNafXML(xmldoc));
                }
                root.appendChild(opinions);
            }

            if (kafEntityArrayList.size()>0) {
                Element entities = xmldoc.createElement("entities");
                for (int i = 0; i < this.kafEntityArrayList.size(); i++) {
                    KafEntity kaf  = kafEntityArrayList.get(i);
                    kaf.setTokenStrings(this);
                    entities.appendChild(kaf.toNafXML(xmldoc));
                }
                root.appendChild(entities);
            }

            if (kafPropertyArrayList.size()>0) {
                Element properties = xmldoc.createElement("properties");
                for (int i = 0; i < this.kafPropertyArrayList.size(); i++) {
                    KafProperty kaf  = kafPropertyArrayList.get(i);
                    kaf.setTokenStrings(this);
                    properties.appendChild(kaf.toNafXML(xmldoc));
                }
                root.appendChild(properties);
            }

            if (kafCorefenceArrayList.size()>0) {
                Element coreferences = xmldoc.createElement("coreferences");
                for (int i = 0; i < this.kafCorefenceArrayList.size(); i++) {
                    KafCoreferenceSet kaf  = kafCorefenceArrayList.get(i);
                    kaf.setTokenStrings(this);
                    coreferences.appendChild(kaf.toNafXML(xmldoc));
                }
                root.appendChild(coreferences);
            }

            if (kafConstituencyTrees.size()>0) {
                Element constituency = xmldoc.createElement("constituency");
                for (int i = 0; i < kafConstituencyTrees.size(); i++) {
                    KafConstituencyTree constituencyTree = kafConstituencyTrees.get(i);
                    constituencyTree.addComments(this);
                    constituency.appendChild(constituencyTree.toNafXML(xmldoc));
                }
                root.appendChild(constituency);
            }
            if (kafCountryArrayList.size()>0) {
                Element locations = xmldoc.createElement("locations");
                for (int i = 0; i < kafCountryArrayList.size(); i++) {
                    GeoCountryObject geoCountryObject = kafCountryArrayList.get(i);
                    locations.appendChild(geoCountryObject.toNafXML(xmldoc));
                }
                for (int i = 0; i < kafPlaceArrayList.size(); i++) {
                    GeoPlaceObject geoPlaceObject = kafPlaceArrayList.get(i);
                    locations.appendChild(geoPlaceObject.toNafXML(xmldoc));
                }
                root.appendChild(locations);
            }

            if (kafDateArrayList.size()>0) {
                Element dates = xmldoc.createElement("dates");
                for (int i = 0; i < kafDateArrayList.size(); i++) {
                    ISODate isoDate = kafDateArrayList.get(i);
                    dates.appendChild(isoDate.toNafXML(xmldoc));
                }
                root.appendChild(dates);
            }


            if (kafEventArrayList.size()>0) {
                Element events  = xmldoc.createElement("srl");

                for (int i = 0; i < kafEventArrayList.size(); i++) {
                    KafEvent event = kafEventArrayList.get(i);
                    event.setTokenString(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(this, event.getSpanIds()));
                    for (int j = 0; j < event.getParticipants().size(); j++) {
                        KafParticipant kafParticipant = event.getParticipants().get(j);
                        kafParticipant.setTokenString(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(this, kafParticipant.getSpanIds()));
                    }
                    events.appendChild(event.toNafXML(xmldoc));
                }
                root.appendChild(events);
            }


            if (kafTimexLayer.size()>0) {
                Element timexLayer = xmldoc.createElement("timeExpressions");

                for (int i = 0; i < kafTimexLayer.size(); i++) {
                    KafTimex timex = kafTimexLayer.get(i);
                    timex.setTokenString(AddTokensAsCommentsToSpans.getTokenString(this, timex.getSpans()));
                    timexLayer.appendChild(timex.toNafXML(xmldoc));
                }

                root.appendChild(timexLayer);
            }

            if (kafClinks.size()>0) {
                Element tlinksLayer = xmldoc.createElement("temporalRelations");

                for (int i = 0; i < kafTlinks.size(); i++) {
                    KafEventRelation eventRelation = kafTlinks.get(i);
                    tlinksLayer.appendChild(eventRelation.toNafXML(xmldoc, "tlink"));
                }

                root.appendChild(tlinksLayer);
            }

            if (kafTlinks.size()>0) {
                Element clinksLayer = xmldoc.createElement("causalRelations");

                for (int i = 0; i < kafClinks.size(); i++) {
                    KafEventRelation eventRelation = kafClinks.get(i);
                    clinksLayer.appendChild(eventRelation.toNafXML(xmldoc, "clink"));
                }

                root.appendChild(clinksLayer);
            }

            if (kafFactualityLayer.size()>0) {
                Element factualities = xmldoc.createElement("factualitylayer");

                for (int i = 0; i < kafFactualityLayer.size(); i++) {
                    KafFactuality kafFactuality = kafFactualityLayer.get(i);
                    factualities.appendChild((kafFactuality.toNafXML(xmldoc)));
                }
                root.appendChild(factualities);
            }

/*       @deprecated
            Element tunits = xmldoc.createElement("tunits");
            for (int i = 0; i < this.kafDiscourseList.size(); i++) {
                KafTextUnit kaf  =  kafDiscourseList.get(i);
                tunits.appendChild(kaf.toNafXML(xmldoc));
            }
            root.appendChild(tunits);

*/
			// Serialisation through Tranform.
			DOMSource domSource = new DOMSource(xmldoc);
			TransformerFactory tf = TransformerFactory.newInstance();
			//tf.setAttribute("indent-number", 4);
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			//serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			serializer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            //serializer.setParameter("format-pretty-print", Boolean.TRUE);
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            //StreamResult streamResult = new StreamResult(new OutputStreamWriter(stream, encoding));
            StreamResult streamResult = new StreamResult(new OutputStreamWriter(stream));
			serializer.transform(domSource, streamResult);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }


    public void writeKafToStream(OutputStream stream)
    {
    	try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation impl = builder.getDOMImplementation();

			Document xmldoc = impl.createDocument(null, "KAF", null);
			xmldoc.setXmlStandalone(false);
			Element root = xmldoc.getDocumentElement();
			root.setAttribute("xml:lang", kafMetaData.getLanguage());
			root.appendChild(kafMetaData.toHeaderXML(xmldoc));

            if (this.kafWordFormList.size()>0) {
                Element text = xmldoc.createElement("text");
                for (int i = 0; i < this.kafWordFormList.size(); i++) {
                    KafWordForm kaf  = kafWordFormList.get(i);
                    text.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(text);
            }

            if (this.kafTermList.size()>0) {
                Element terms = xmldoc.createElement("terms");
                for (int i = 0; i < this.kafTermList.size(); i++) {
                    KafTerm kaf  = kafTermList.get(i);
                    kaf.setTokenString(AddTokensAsCommentsToSpans.getTokenString(this, kaf.getSpans()));
                    terms.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(terms);
            }

            if ( this.kafDepList.size()>0) {
                Element deps = xmldoc.createElement("deps");
                for (int i = 0; i < this.kafDepList.size(); i++) {
                    KafDep kaf  = kafDepList.get(i);
                    /// the next checks are needed because some parser create reference to nonexisting elements
                    if ((this.getTerm(kaf.from)!=null) && (this.getTerm(kaf.to)!=null)) {
                        deps.appendChild(kaf.toXML(xmldoc));
                    }
                }
                root.appendChild(deps);
            }

            if (this.kafChunkList.size()>0) {
                Element chunks = xmldoc.createElement("chunks");
                for (int i = 0; i < this.kafChunkList.size(); i++) {
                    KafChunk kaf  = kafChunkList.get(i);
                    /// the next checks are needed because some parser create reference to nonexisting elements
                    boolean nullSpan = false;
                    for (int j = 0; j < kaf.getSpans().size(); j++) {
                        String span = kaf.getSpans().get(j);
                        if (this.getTerm(span)==null) {
                            nullSpan = true;
                            break;
                        }
                    }
                    if (this.getTerm(kaf.getHead())!=null) {
                        if (!nullSpan) chunks.appendChild(kaf.toXML(xmldoc));
                    }
                }
                root.appendChild(chunks);
            }


            if (kafOpinionArrayList.size()>0) {
                Element opinions = xmldoc.createElement("opinions");
                for (int i = 0; i < this.kafOpinionArrayList.size(); i++){
                    KafOpinion kaf  =  kafOpinionArrayList.get(i);
                    kaf.setTokenStrings(this);
                    opinions.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(opinions);
            }

            if (kafEntityArrayList.size()>0) {
                Element entities = xmldoc.createElement("entities");
                for (int i = 0; i < this.kafEntityArrayList.size(); i++) {
                    KafEntity kaf  = kafEntityArrayList.get(i);
                    kaf.setTokenStrings(this);
                    entities.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(entities);
            }

            if (kafPropertyArrayList.size()>0) {
                Element properties = xmldoc.createElement("properties");
                for (int i = 0; i < this.kafPropertyArrayList.size(); i++) {
                    KafProperty kaf  = kafPropertyArrayList.get(i);
                    kaf.setTokenStrings(this);
                    properties.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(properties);
            }

            if (kafCorefenceArrayList.size()>0) {
                Element coreferences = xmldoc.createElement("coreferences");
                for (int i = 0; i < this.kafCorefenceArrayList.size(); i++) {
                    KafCoreferenceSet kaf  = kafCorefenceArrayList.get(i);
                    coreferences.appendChild(kaf.toXML(xmldoc));
                }
                root.appendChild(coreferences);
            }

            if (kafCountryArrayList.size()>0) {
                Element locations = xmldoc.createElement("locations");
                for (int i = 0; i < kafCountryArrayList.size(); i++) {
                    GeoCountryObject geoCountryObject = kafCountryArrayList.get(i);
                    locations.appendChild(geoCountryObject.toXML(xmldoc));
                }
                for (int i = 0; i < kafPlaceArrayList.size(); i++) {
                    GeoPlaceObject geoPlaceObject = kafPlaceArrayList.get(i);
                    locations.appendChild(geoPlaceObject.toXML(xmldoc));
                }
                root.appendChild(locations);
            }

            if (kafDateArrayList.size()>0) {
                Element dates = xmldoc.createElement("dates");
                for (int i = 0; i < kafDateArrayList.size(); i++) {
                    ISODate isoDate = kafDateArrayList.get(i);
                    dates.appendChild(isoDate.toXML(xmldoc));
                }
                root.appendChild(dates);
            }

            if (kafEventISIList.size()>0) {
                Element isiEvents  = xmldoc.createElement("isi-events");
                for (int i = 0; i < kafEventISIList.size(); i++) {
                    KafEventISI eventISI = kafEventISIList.get(i);
                    isiEvents.appendChild(eventISI.toXML(xmldoc));
                }
                root.appendChild(isiEvents);
            }


            if (kafEventArrayList.size()>0) {
                Element events  = xmldoc.createElement("srls");
                for (int i = 0; i < kafEventArrayList.size(); i++) {
                    KafEvent event = kafEventArrayList.get(i);
                    event.setTokenString(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(this, event.getSpanIds()));
                    for (int j = 0; j < event.getParticipants().size(); j++) {
                        KafParticipant kafParticipant = event.getParticipants().get(j);
                        kafParticipant.setTokenString(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(this, kafParticipant.getSpanIds()));
                    }
                    events.appendChild(event.toXML(xmldoc));
                }
                root.appendChild(events);
            }


            if (kafTimexLayer.size()>0) {
                Element timexLayer = xmldoc.createElement("timeExpressions");

                for (int i = 0; i < kafTimexLayer.size(); i++) {
                    KafTimex timex = kafTimexLayer.get(i);
                    timex.setTokenString(AddTokensAsCommentsToSpans.getTokenString(this, timex.getSpans()));
                    timexLayer.appendChild(timex.toNafXML(xmldoc));
                }

                root.appendChild(timexLayer);
            }

            if (kafFactualityLayer.size()>0) {
                Element factualities = xmldoc.createElement("factualitylayer");

                for (int i = 0; i < kafFactualityLayer.size(); i++) {
                    KafFactuality kafFactuality = kafFactualityLayer.get(i);
                    factualities.appendChild((kafFactuality.toNafXML(xmldoc)));
                }
                root.appendChild(factualities);
            }

            Element tunits = xmldoc.createElement("tunits");
            for (int i = 0; i < this.kafDiscourseList.size(); i++) {
                KafTextUnit kaf  = (KafTextUnit) kafDiscourseList.get(i);
                tunits.appendChild(kaf.toXML(xmldoc));
            }
            root.appendChild(tunits);

			// Serialisation through Tranform.
			DOMSource domSource = new DOMSource(xmldoc);
			TransformerFactory tf = TransformerFactory.newInstance();
			//tf.setAttribute("indent-number", 4);
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT,"yes");
			//serializer.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			serializer.setOutputProperty(OutputKeys.STANDALONE, "yes");
           // serializer.setParameter("format-pretty-print", Boolean.TRUE);
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            //StreamResult streamResult = new StreamResult(new OutputStreamWriter(stream,encoding));
            StreamResult streamResult = new StreamResult(new OutputStreamWriter(stream));
			serializer.transform(domSource, streamResult);

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    public String getFullText () {
        String text = "";
        for (int i = 0; i < this.kafWordFormList.size(); i++) {
            KafWordForm wordForm = this.kafWordFormList.get(i);
            text += " "+wordForm.getWf();
        }
        return text.trim();
    }
    public KafTerm getTerm (String tid) {
        for (int i = 0; i < this.kafTermList.size(); i++) {
            KafTerm kt  = (KafTerm) this.kafTermList.get(i);
            if (kt.getTid().equalsIgnoreCase(tid)) {
                return kt;
            }
        }
        return null;
    }
    
    public KafTerm getTermForWordId (String wid) {
        for (int i = 0; i < this.kafTermList.size(); i++) {
            KafTerm kt  = (KafTerm) this.kafTermList.get(i);
            for (int j = 0; j < kt.getSpans().size(); j++) {
                String s = kt.getSpans().get(j);
                if (s.equalsIgnoreCase(wid)) {
                    return kt;
                }
            }
        }
        return null;
    }

    public KafEventISI getEventISI (String eventid) {
        for (int i = 0; i < this.kafEventISIList.size(); i++) {
            KafEventISI kt  = (KafEventISI) this.kafEventISIList.get(i);
            if (kt.getEventid().equalsIgnoreCase(eventid)) {
                return kt;
            }
        }
        return null;
    }

    public KafEvent getEvents (String eventid) {
        for (int i = 0; i < this.kafEventArrayList.size(); i++) {
            KafEvent kt  = (KafEvent) this.kafEventArrayList.get(i);
            if (kt.getId().equalsIgnoreCase(eventid)) {
                return kt;
            }
        }
        return null;
    }

    public ArrayList<KafTerm> getNamedEntities (String type) {
        ArrayList<KafTerm> entities = new ArrayList<KafTerm>(); 
        for (int i = 0; i < this.kafTermList.size(); i++) {
            KafTerm kt  = (KafTerm) this.kafTermList.get(i);
            if (kt.getNetype().equalsIgnoreCase(type)) {
                entities.add(kt);
            }
        }
        return entities;
    }

    public KafOpinion getOpinion (String oid) {
        for (int i = 0; i < kafOpinionArrayList.size(); i++) {
            KafOpinion opinion = kafOpinionArrayList.get(i);
            if (opinion.getOpinionId().equals(oid)) {
                return opinion;
            }
        }
        return null;
    }

    public KafWordForm getWordForm (String wid) {
        for (int i = 0; i < this.kafWordFormList.size(); i++) {
            KafWordForm kw  = (KafWordForm) this.kafWordFormList.get(i);
            if (kw.getWid().equalsIgnoreCase(wid)) {
                return kw;
            }
        }
        return null;
    }

    public int getWordFormRank (String wid) {
        for (int i = 0; i < this.kafWordFormList.size(); i++) {
            KafWordForm kw  = (KafWordForm) this.kafWordFormList.get(i);
            if (kw.getWid().equalsIgnoreCase(wid)) {
                return i;
            }
        }
        return -1;
    }

    public KafChunk getChunks (String cid) {
        for (int i = 0; i < this.kafChunkList.size(); i++) {
            KafChunk kc  = (KafChunk) this.kafChunkList.get(i);
            if (kc.getCid().equalsIgnoreCase(cid)) {
                return kc;
            }
        }
        return null;
    }

    public ArrayList<String> covertTokensSpanToTermSPan (ArrayList<String> tokenSpans) {
        ArrayList<String> termSpans = new ArrayList<String>();
        for (int i = 0; i < tokenSpans.size(); i++) {
            String tokenSpan =  tokenSpans.get(i);
            KafTerm kafTerm = getTermForWordId(tokenSpan);
            if (kafTerm!=null) {
                if (!termSpans.contains(kafTerm.getTid())) {
                    termSpans.add(kafTerm.getTid());
                }
            }

        }
        return termSpans;
    }

    static public void main (String[] args) {
        //String file = "/Projects/NewsReader/collaboration/bulgarian/razni11-01.naf";
        //String file = "/Users/piek/Desktop/NWR/NWR-SRL/wikinews-nl/files/14369_Airbus_offers_funding_to_search_for_black_boxes_from_Air_France_disaster.ukb.kaf";
       // String file = "/Users/piek/Desktop/NWR/NWR-SRL/wikinews-nl/files/14369_Airbus_offers_funding_to_search_for_black_boxes_from_Air_France_disaster.ukb.kaf";
       // String file = "/Tools/ontotagger-v1.0/naf-example/spinoza-voorbeeld-ukb.ont.xml";
        //String file = "/Users/piek/Desktop/test/eventcorref_in.xml";
        String file = "/Users/piek/Desktop/NWR/en_pipeline2.0/v21_out.naf";

        //String file = "/Code/vu/kyotoproject/KafSaxParser/test/eventcoref_in.xml";
        //String file = "/Tools/TextPro/TextPro2.0-forNewsReader/test/gold/Time.NAF.xml";
       // String file = "/Code/vu/newsreader/pos.xml";
       // String file = "test/car.naf";
        KafSaxParser parser = new KafSaxParser();
        parser.parseFile(file);
/*        for (int i = 0; i < parser.kafWordFormList.size(); i++) {
            KafWordForm kafWordForm = parser.kafWordFormList.get(i);
            System.out.println("kafWordForm.getWf() = " + kafWordForm.getWf());
        }*/
        String outfile = file+".out.xml";
        try {
            FileOutputStream fos = new FileOutputStream(outfile);
            //parser.writeKafToFile(fos);
            parser.writeNafToStream(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
