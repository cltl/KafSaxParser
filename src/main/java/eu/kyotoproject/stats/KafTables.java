package eu.kyotoproject.stats;

import eu.kyotoproject.kaf.*;
import eu.kyotoproject.util.AddTokensAsCommentsToSpans;
import eu.kyotoproject.util.FileProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by piek on 3/10/14.
 */
public class KafTables {


    static void writePlaces (String fileName, KafSaxParser kafSaxParser, FileOutputStream fileOutputStream) throws IOException {
        String str = "";
        for (int i = 0; i < kafSaxParser.kafPlaceArrayList.size(); i++) {
            GeoPlaceObject geoPlaceObject = kafSaxParser.kafPlaceArrayList.get(i);
            ArrayList<String> setOfSPans = geoPlaceObject.getSpans();
            String sentenceId = "";
            String termIds = "";
            /// timex spans are tokens
            for (int j = 0; j < setOfSPans.size(); j++) {
                String kafReference = setOfSPans.get(j);
                if (sentenceId.isEmpty()) {
                    sentenceId= kafSaxParser.getSentenceId(kafReference);
                }
                if (!termIds.isEmpty()) {
                    termIds+=",";
                }
                termIds+=kafReference;
            }
            str = fileName+"\t"+ sentenceId+"\t"+termIds+"\t"+geoPlaceObject.getPlaceInfo().getName()+"\n";
            fileOutputStream.write(str.getBytes());
        }
    }


    static void writeTimex (String fileName, KafSaxParser kafSaxParser, FileOutputStream fileOutputStream) throws IOException {
        String str = "";
        for (int i = 0; i < kafSaxParser.kafTimexLayer.size(); i++) {
            KafTimex timex = kafSaxParser.kafTimexLayer.get(i);
            timex.setTokenString(AddTokensAsCommentsToSpans.getTokenString(kafSaxParser, timex.getSpans()));

            ArrayList<String> setOfSPans = timex.getSpans();
            String sentenceId = "";
            String tokenIds = "";
            for (int j = 0; j < setOfSPans.size(); j++) {
                String kafReference = setOfSPans.get(j);
                if (sentenceId.isEmpty()) {
                    sentenceId= kafSaxParser.getSentenceIdForToken(kafReference);
                }
                if (!tokenIds.isEmpty()) {
                    tokenIds+=",";
                }
                tokenIds+=kafReference;
            }
            str = fileName+"\t"+ sentenceId+"\t"+tokenIds+"\t"+timex.getTokenString()+"\n";
            fileOutputStream.write(str.getBytes());
        }
    }


    static void writeDates (String fileName, KafSaxParser kafSaxParser, FileOutputStream fileOutputStream) throws IOException {
        String str = "";
        for (int i = 0; i < kafSaxParser.kafDateArrayList.size(); i++) {
            ISODate isoDate = kafSaxParser.kafDateArrayList.get(i);
            ArrayList<String> setOfSPans = isoDate.getSpans();
            String sentenceId = "";
            String termIds = "";
            for (int j = 0; j < setOfSPans.size(); j++) {
                String kafReference = setOfSPans.get(j);
                if (sentenceId.isEmpty()) {
                    sentenceId= kafSaxParser.getSentenceId(kafReference);
                }
                if (!termIds.isEmpty()) {
                    termIds+=",";
                }
                termIds+=kafReference;
            }
            str = fileName+"\t"+ sentenceId+"\t"+termIds+"\t"+isoDate.getDateInfo().getDateISO()+"\n";
            fileOutputStream.write(str.getBytes());
        }
    }

    static void writeEntities (String fileName, KafSaxParser kafSaxParser, FileOutputStream fileOutputStream) throws IOException {
        String str = "";
        for (int i = 0; i < kafSaxParser.kafEntityArrayList.size(); i++) {
            KafEntity kafEntity = kafSaxParser.kafEntityArrayList.get(i);
            kafEntity.setTokenStrings(kafSaxParser);
            ArrayList<ArrayList<CorefTarget>> setOfSPans = kafEntity.getSetsOfSpans();
            String sentenceId = "";
            String termIds = "";
            String tokenString = kafEntity.getTokenStringArray().get(0);
            for (int j = 0; j < setOfSPans.size(); j++) {
                ArrayList<CorefTarget> corefTargets = setOfSPans.get(j);
                if (sentenceId.isEmpty()) {
                    sentenceId= kafSaxParser.getSentenceId(corefTargets.get(0).getId());
                }
                for (int k = 0; k < corefTargets.size(); k++) {
                    CorefTarget corefTarget = corefTargets.get(k);
                    if (!termIds.isEmpty()) {
                        termIds+=",";
                    }
                    termIds+=corefTarget.getId();
                }
            }
            str = fileName+"\t"+ sentenceId+"\t"+termIds+"\t"+kafEntity.toTableStringValues()+"\t"+tokenString+"\n";
            fileOutputStream.write(str.getBytes());
        }
    }

    static void writeOpinions (String fileName, KafSaxParser kafSaxParser, FileOutputStream fileOutputStream) throws IOException {
        String str = "";
        for (int i = 0; i < kafSaxParser.kafOpinionArrayList.size(); i++) {
            KafOpinion kaf   = kafSaxParser.kafOpinionArrayList.get(i);
            kaf.setTokenStrings(kafSaxParser);
            str = kaf.toTableValueString();
            String sentenceIdOpinion = "";
            String sentenceIdHolder = "";
            String sentenceIdTarget = "";
            String termIdsOpinion = "";
            String termIdsHolder = "";
            String termIdsTarget = "";
            for (int j = 0; j < kaf.getSpansOpinionExpression().size(); j++) {
                String span = kaf.getSpansOpinionExpression().get(j);
                if (sentenceIdOpinion.isEmpty()) {
                    sentenceIdOpinion= kafSaxParser.getSentenceId(span);
                }
                if (!termIdsOpinion.isEmpty()) {
                    termIdsOpinion+=",";
                }
                termIdsOpinion+=span;
            }
            for (int j = 0; j < kaf.getSpansOpinionHolder().size(); j++) {
                String span = kaf.getSpansOpinionHolder().get(j);
                if (sentenceIdHolder.isEmpty()) {
                    sentenceIdHolder= kafSaxParser.getSentenceId(span);
                }
                if (!termIdsHolder.isEmpty()) {
                    termIdsHolder+=",";
                }
                termIdsHolder+=span;
            }
            for (int j = 0; j < kaf.getSpansOpinionTarget().size(); j++) {
                String span = kaf.getSpansOpinionTarget().get(j);
                if (sentenceIdTarget.isEmpty()) {
                    sentenceIdTarget= kafSaxParser.getSentenceId(span);
                }
                if (!termIdsTarget.isEmpty()) {
                    termIdsTarget+=",";
                }
                termIdsTarget+=span;
            }
            str = fileName+"\t"+ sentenceIdOpinion+"\t"+termIdsOpinion+"\t"+termIdsHolder+"\t"+termIdsTarget+"\t"+kaf.toTableValueString()+"\n";
            fileOutputStream.write(str.getBytes());
        }
    }


    static void writeSrl (String fileName, KafSaxParser kafSaxParser, FileOutputStream fileOutputStream) throws IOException {
        String str = "";
        for (int i = 0; i < kafSaxParser.kafEventArrayList.size(); i++) {
            KafEvent event   = kafSaxParser.kafEventArrayList.get(i);
            event.setTokenString(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(kafSaxParser, event.getSpanIds()));
            String sentenceIdEvent= "";
            String eventTermIds = "";
            String eventType = "";
            for (int j = 0; j < event.getExternalReferences().size(); j++) {
                KafSense kafSense = event.getExternalReferences().get(j);
                if (!eventType.isEmpty()) {
                    eventType += ",";
                }
                eventType += kafSense.getSensecode();
            }
            for (int j = 0; j < event.getSpanIds().size(); j++) {
                String span = event.getSpanIds().get(j);
                if (sentenceIdEvent.isEmpty()) {
                    sentenceIdEvent= kafSaxParser.getSentenceId(span);
                }
                if (!eventTermIds.isEmpty()) {
                    eventTermIds+=",";
                }
                eventTermIds+=span;
            }
            for (int j = 0; j < event.getParticipants().size(); j++) {
                KafParticipant kafParticipant = event.getParticipants().get(j);
                kafParticipant.setTokenString(AddTokensAsCommentsToSpans.getTokenStringFromTermIds(kafSaxParser, kafParticipant.getSpanIds()));
                String sentenceIdParticipant = "";
                String participantTermIds = "";
                for (int k = 0; k < kafParticipant.getSpanIds().size(); k++) {
                    String span = kafParticipant.getSpanIds().get(k);
                    if (sentenceIdParticipant.isEmpty()) {
                        sentenceIdParticipant= kafSaxParser.getSentenceId(span);
                    }
                    if (!participantTermIds.isEmpty()) {
                        participantTermIds+=",";
                    }
                    participantTermIds+=span;
                }
                str = fileName+"\t"+ sentenceIdEvent+"\t"+eventTermIds+"\t"+participantTermIds+"\t"+event.getId()+"\t"+eventType+"\t"+event.getTokenString()+"\t"+kafParticipant.getRole()+"\t"+kafParticipant.getId()+"\t"+kafParticipant.getTokenString()+"\n";
                fileOutputStream.write(str.getBytes());
            }
        }
    }

    static public void main (String [] args) {
        try {
            //String kafFilePath = args[0];
            //String extension = args[1];
            //String kafFilePath = "/Users/piek/Desktop/NWR-DATA/techcrunch/1_3000";
             String kafFilePath = "/Users/piek/Desktop/Thomese/thomese_plus_dbpedia";
             //String extension = "kaf.coref.coref";
             //String kafFilePath = "";
             //String extension = "";
            String extension = ".naf";
            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg.equalsIgnoreCase("--input") && args.length>(i+1)) {
                    kafFilePath = args[i+1];
                }
                else if (arg.equalsIgnoreCase("--extension") && args.length>(i+1)) {
                    extension = args[i+1];
                }

            }
            File file = new File(kafFilePath);
            String [] files = null;
            if (file.isDirectory()) {
                files= FileProcessor.makeRecursiveFileList(kafFilePath, extension);
            }
            else {
                files= new String[1];
                files[0] = kafFilePath;
            }
            if (files!=null) {
                System.out.println("files.length = " + files.length);
                FileOutputStream entities = new FileOutputStream(kafFilePath+"/"+"entity-list.xls");
                FileOutputStream opinions = new FileOutputStream(kafFilePath+"/"+"opinion-list.xls");
                FileOutputStream events = new FileOutputStream(kafFilePath+"/"+"event-list.xls");
                //FileOutputStream dates = new FileOutputStream(kafFilePath+"/"+"date-list.xls");
                FileOutputStream timex = new FileOutputStream(kafFilePath+"/"+"time-list.xls");
                //FileOutputStream locations = new FileOutputStream(kafFilePath+"/"+"location-list.xls");
                String header = "File\tsentence\tterms\tentity-id\ttype\turi\texpression\n";
                entities.write(header.getBytes());
                header = "File\tsentence\tterms opinion\tterms holder\tterms target\t"+ KafOpinion.toTableHeaderString()+"\n";
                opinions.write(header.getBytes());
                header = "File\tsentence\tterms event\tterms participant\tevent id\tevent type\tevent expression\trole\tparticipant id\tparticipant expression\n";
                events.write(header.getBytes());
                header =  "File\tsentence\ttokens\ttime expression\n";
                timex.write(header.getBytes());
               // header =  "File\tsentence\tterms\tdate\n";
               // dates.write(header.getBytes());
               // header =  "File\tsentence\tterms\tplace\n";
               // locations.write(header.getBytes());
                KafSaxParser parser = new KafSaxParser();
                for (int i = 0; i < files.length; i++) {
                    String filePath = files[i];
                    // System.out.println("filePath = " + filePath);
                    String fileName = new File (filePath).getName();
                    parser.parseFile(filePath);
                    writeEntities(fileName, parser, entities);
                    writeOpinions(fileName, parser, opinions);
                    writeSrl(fileName, parser, events);
                    writeTimex(fileName, parser, timex);
                   // writeDates(fileName, parser, dates);
                   // writePlaces(fileName, parser, locations);
                }
                entities.close();
                opinions.close();
                events.close();
                timex.close();
               // dates.close();
               // locations.close();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
