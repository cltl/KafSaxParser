package eu.kyotoproject.util;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

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
public class FileProcessor {
    static public String selectedFile = "";
    static String inString = "";
    static String err = "";
    static String SEP = System.getProperty("file.separator");
    static String UTF_ENCODING = "UTF-8";
    static final public byte[] utf8_bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };


  
    public static void writeUtf8ToFile(File file,
        boolean append, String data) throws IOException {
       final byte[] utf8_bom = { (byte) 0xEF, (byte) 0xBB,
           (byte) 0xBF };

       boolean exists = file.isFile();
       OutputStream out = new FileOutputStream(file, append);
       try {
         if (!exists) {
           // then this is a new file
           // write the BOM marker
           out.write(utf8_bom);
         }

         Writer writer = new OutputStreamWriter(out, "UTF-8");
         try {
           writer.write(data);
         } finally {
           writer.close();
         }
       } finally {
         out.close();
       }
    }


    public static void storeResult(FileOutputStream fos, String serverMsg) {
        if (fos!=null) {
            try {
                fos.write(serverMsg.getBytes(UTF_ENCODING));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("FileOutpurStream is null! Cannot store"+serverMsg);
        }
    }


    public static void storeResult(FileOutputStream fos, String serverMsg, String ENCODING) {
        if (fos!=null) {
            try {
                fos.write(serverMsg.getBytes(ENCODING));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("FileOutpurStream is null! Cannot store"+serverMsg);
        }
    }

    public static void storeResult(OutputStreamWriter fos, String serverMsg) {
        if (fos!=null) {
            try {
                fos.write(serverMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("FileOutpurStream is null! Cannot store"+serverMsg);
        }
    }

    static public String ReadFileToStringBatch(String fileName, String anENCODING) {
        String text = "";
        try {
            File f = new File(fileName);
            if (f.exists()) {
                DataInputStream in = new DataInputStream(new FileInputStream(f));
                byte[] buffer = new byte[(int) f.length()];
                in.readFully(buffer);
                in.close();
                text = new String(buffer, 0, buffer.length, anENCODING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    static public String ReadFileToString(String fileName, String anENCODING) {
        String text = "";
        try {
            File f = new File(fileName);
            if (f.exists()) {
                DataInputStream in = new DataInputStream(new FileInputStream(f));
                byte[] buffer = new byte[(int) f.length()];
                in.readFully(buffer);
                in.close();
                text = new String(buffer, 0, buffer.length, anENCODING);
                if (text.getBytes(anENCODING).length != buffer.length) {
                    //AlertDialog dia = new AlertDialog(new JFrame(), "Could not read complete text!", "Please, check the character encoding of the input file.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    static public byte[]  ReadFileToBytes(String fileName) {
        byte[] buffer = null;
        try {
            File f = new File(fileName);
            if (f.exists()) {
                DataInputStream in = new DataInputStream(new FileInputStream(f));
                buffer = new byte[(int) f.length()];
                in.readFully(buffer);
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    static public String ReadFileToString(String fileName) {
        String text = "";
        try {
            File f = new File(fileName);
            if (f.exists()) {
                DataInputStream in = new DataInputStream(new FileInputStream(f));
                byte[] buffer = new byte[(int) f.length()];
                in.readFully(buffer);
                in.close();
                text = new String(buffer, 0, buffer.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

 
    static public DefaultListModel ReadFileToList(String fileName, String anENCODING) {
        DefaultListModel lineList = new DefaultListModel();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis, anENCODING);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    //System.out.println(inputLine);
                    if (inputLine.trim().length()>0) {
                        lineList.addElement(inputLine);
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineList;
    }

    static public ArrayList<String> ReadFileToArrayList(String fileName) {
        ArrayList<String> lineList = new ArrayList<String>();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                   // System.out.println(inputLine);
                    if (inputLine.trim().length()>0) {
                        lineList.add(inputLine.trim());
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineList;
    }

    static public ArrayList<String> ReadFileToArrayListIgnoreCase(String fileName) {
        ArrayList<String> lineList = new ArrayList<String>();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                   // System.out.println(inputLine);
                    if (inputLine.trim().length()>0) {
                        lineList.add(inputLine.toLowerCase().trim());
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineList;
    }

    static public String [] ReadFileToStringArray(String fileName) {
        ArrayList<String> lineList = new ArrayList<String>();
        String [] results = new String[0];
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                   // System.out.println(inputLine);
                    if (inputLine.trim().length()>0) {
                        lineList.add(inputLine.trim());
                    }
                }
                results = (String[]) lineList.toArray(results);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    static public ArrayList<String> ReadFileToLowerCaseArrayList(String fileName) {
        ArrayList<String> lineList = new ArrayList<String>();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                   // System.out.println(inputLine);
                    if (inputLine.trim().length()>0) {
                        lineList.add(inputLine.toLowerCase().trim());
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineList;
    }

    static public ArrayList<String> ReadFileToArrayList(String fileName, String anENCODING) {
        ArrayList<String> lineList = new ArrayList<String>();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis, anENCODING);
                BufferedReader in = new BufferedReader(isr);
                String inputLine = "";
                long cnt = 0;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    //System.out.println(inputLine);
                    if (inputLine.trim().length()>0) {
                        lineList.add(inputLine);
                        cnt++;
                    }
                }
//                System.out.println("last inputLine = " + inputLine);
                System.out.println("read cnt = " + cnt+" lines....");
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineList;
    }

    static public ArrayList<String> ReadFileLowerCaseToArrayList(String fileName, String anENCODING) {
        ArrayList<String> lineList = new ArrayList<String>();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis, anENCODING);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    if (inputLine.trim().length()>0) {
                        lineList.add(inputLine.toLowerCase());
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineList;
    }

    static public Vector<String> ReadFileToVector(String fileName, String anENCODING) {
        Vector<String> lineVector = new Vector<String>();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis, anENCODING);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    if (inputLine.trim().length()>0) {
                        lineVector.addElement(inputLine.trim());
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineVector;
    }

    static public HashMap<String,String> ReadFileToStringHashMap(String separator, String fileName) {
        HashMap<String,String> lineHashMap = new HashMap<String,String>();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    if (inputLine.trim().length()>0) {
                        int idx_s = inputLine.indexOf(separator);
                        if (idx_s>-1) {
                            String key = inputLine.substring(0, idx_s).trim();
                            String value = inputLine.substring(idx_s+1).trim();
                            lineHashMap.put(key, value);
                        }
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineHashMap;
    }

    static public HashMap<String,String> ReadFileToReversedStringHashMap(String separator, String fileName) {
        HashMap<String,String> lineHashMap = new HashMap<String,String>();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    //System.out.println(inputLine);
                    if (inputLine.trim().length()>0) {
                        int idx_s = inputLine.indexOf(separator);
                        if (idx_s>-1) {
                            String key = inputLine.substring(0, idx_s).trim();
                            String value = inputLine.substring(idx_s+1).trim();
                            lineHashMap.put(value, key);
                        }
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineHashMap;
    }

    static public HashMap<String,String> ReadFileToStringHashMap(String separator, String fileName, String anENCODING) {
        HashMap<String,String> lineHashMap = new HashMap<String,String>();
        if (new File (fileName).exists() ) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                InputStreamReader isr = new InputStreamReader(fis, anENCODING);
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while (in.ready()&&(inputLine = in.readLine()) != null) {
                    if (inputLine.trim().length()>0) {
                        int idx_s = inputLine.indexOf(separator);
                        if (idx_s>-1) {
                            String key = inputLine.substring(0, idx_s).trim();
                            String value = inputLine.substring(idx_s+1).trim();
                            lineHashMap.put(key, value);
                        }
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lineHashMap;
    }
    // Save current files: handle not yet having a filename; report to statusBar
    static public boolean SaveFile(String outFileName, String content) {
        try {
            // Open a file of the current name.
            File outFile = new File(outFileName);
            // Create an output writer that writes to that file.
            // FileWriter handles international character encoding conventions
            FileWriter outWriter = new FileWriter(outFile);
            outWriter.write(content);
            outWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Save current files: handle not yet having a filename; report to statusBar
    static public boolean SaveFile(String outFileName, String content, String ENCODING) {
        try {
            // Open a file of the current name.
            File outFile = new File(outFileName);
            // Create an output writer that writes to that file.
            // FileWriter handles international character encoding conventions
            FileOutputStream outWriter = new FileOutputStream(outFile);
            outWriter.write(content.getBytes(ENCODING));
            outWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    static public String selectOutputFile(JFrame parent, String inputPath, final String extension) {
        File inputFile = new File(inputPath);
        String outputPath = "";
        JFileChooser fc = new JFileChooser(inputFile.getParent());
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        fc.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return (f.getName().toLowerCase().endsWith(extension) ||
                        f.isDirectory());
            }

            public String getDescription() {
                return (extension + " File");
            }
        });
        int returnVal = fc.showSaveDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            outputPath = file.getAbsolutePath();
        }
        fc.setVisible(false);
        return outputPath;
    }

    static public String getFirstFileWithExtension(String inputPath, String extension) {
        File[] theFileList = null;
        File lF = new File(inputPath);
        if ((lF.canRead()) && lF.isDirectory()) {
            theFileList = lF.listFiles();
            for (int i = 0; i < theFileList.length; i++) {
                String newFilePath = theFileList[i].getAbsolutePath();
                if (theFileList[i].isFile()) {
                    if (newFilePath.toLowerCase().endsWith(extension.toLowerCase())) {
                        return newFilePath;
                    }
                }
            }
        }
        return "";
    }

    static public String[] makeFlatFileList(String inputPath) {
        String[] acceptedFileList = new String[0];
        File[] theFileList = null;
        File lF = new File(inputPath);
        if ((lF.canRead()) && lF.isDirectory()) {
            theFileList = lF.listFiles();
            for (int i = 0; i < theFileList.length; i++) {
                String newFilePath = theFileList[i].getAbsolutePath();
                if (theFileList[i].isFile()) {
                        acceptedFileList = addToFileLists(acceptedFileList, newFilePath);
                }
            }
        }
        return acceptedFileList;
    }

    static public ArrayList<File> makeFlatFileArrayList(String inputPath) {
        ArrayList<File> acceptedFileList = new  ArrayList<File>();
        File[] theFileList = null;
        File lF = new File(inputPath);
        if ((lF.canRead()) && lF.isDirectory()) {
            theFileList = lF.listFiles();
            for (int i = 0; i < theFileList.length; i++) {
         //       String newFilePath = theFileList[i].getAbsolutePath();
                if (theFileList[i].isFile()) {
                        acceptedFileList.add(theFileList[i]);
                }
            }
        }
        return acceptedFileList;
    }

    static public String[] makeFlatFileList(String inputPath, String filter) {
        String[] acceptedFileList = new String[0];
        File[] theFileList = null;
        File lF = new File(inputPath);
        if ((lF.canRead()) && lF.isDirectory()) {
            theFileList = lF.listFiles();
            for (int i = 0; i < theFileList.length; i++) {
                String newFilePath = theFileList[i].getAbsolutePath();
                if (theFileList[i].isFile()) {
                    if (acceptFile(newFilePath, filter)) {
                        acceptedFileList = addToFileLists(acceptedFileList, newFilePath);
                    }
                }
            }
        }
        return acceptedFileList;
    }

    static public File getFirstFileFromFolder(String inputPath, String filter) {
        File[] theFileList = null;
        File lF = new File(inputPath);
        if ((lF.canRead()) && lF.isDirectory()) {
            theFileList = lF.listFiles();
            for (int i = 0; i < theFileList.length; i++) {
                String newFilePath = theFileList[i].getAbsolutePath();
                if (theFileList[i].isFile()) {
                    if (acceptFile(newFilePath, filter)) {
                        return theFileList[i];
                    }
                }
            }
        }
        return null;
    }

    static public String[] makeRecursiveFileList(String inputPath, String theFilter) {
        long nSelectedFolders = 1;
        String[] acceptedFileList = new String[0];
        String[] nestedFileList = null;
        File[] theFileList = null;
        File lF = new File(inputPath);
        if ((lF.canRead()) && lF.isDirectory()) {
            theFileList = lF.listFiles();
            for (int i = 0; i < theFileList.length; i++) {
                //        System.out.println(theFileList[i].getPath());
                if (theFileList[i].isDirectory()) {
                    nSelectedFolders++;
                }
            }
            for (int i = 0; i < theFileList.length; i++) {
                String newFilePath = theFileList[i].getAbsolutePath();
                if (theFileList[i].isDirectory()) {
                    String nextFileList [] = makeRecursiveFileList(newFilePath, theFilter);
                    nestedFileList = mergeFileLists(nestedFileList, nextFileList);
                    nextFileList = null;
                } else {
                    if (acceptFile(newFilePath, theFilter)) {
                        acceptedFileList = addToFileLists(acceptedFileList, newFilePath);
                    }
                }
            }
            acceptedFileList = mergeFileLists(acceptedFileList, nestedFileList);
            nestedFileList = null;
        } else {
            System.out.println("Cannot access file:" + inputPath + "#");
            if (!lF.exists()) {
                System.out.println("File does not exist!");
            }
        }
        return acceptedFileList;
    }

    static public String[] makeRecursiveFileList(String inputPath, String [] theFilters) {
        long nSelectedFolders = 1;
        String[] acceptedFileList = new String[0];
        String[] nestedFileList = null;
        File[] theFileList = null;
        File lF = new File(inputPath);
        if ((lF.canRead()) && lF.isDirectory()) {
            theFileList = lF.listFiles();
            for (int i = 0; i < theFileList.length; i++) {
                //        System.out.println(theFileList[i].getPath());
                if (theFileList[i].isDirectory()) {
                    nSelectedFolders++;
                }
            }
            for (int i = 0; i < theFileList.length; i++) {
                String newFilePath = theFileList[i].getAbsolutePath();
                if (theFileList[i].isDirectory()) {
                    String nextFileList [] = makeRecursiveFileList(newFilePath, theFilters);
                    nestedFileList = mergeFileLists(nestedFileList, nextFileList);
                    nextFileList = null;
                } else {
                    if (acceptFile(newFilePath, theFilters)) {
                        acceptedFileList = addToFileLists(acceptedFileList, newFilePath);
                    }
                }
            }
            acceptedFileList = mergeFileLists(acceptedFileList, nestedFileList);
            nestedFileList = null;
        } else {
            System.out.println("Cannot access file:" + inputPath + "#");
            if (!lF.exists()) {
                System.out.println("File does not exist!");
            }
        }
        return acceptedFileList;
    }

    static public String[] makeRecursiveFolderList(String inputPath) {
        String[] acceptedFileList = new String[0];
        String[] nestedFileList = null;
        File[] theFileList = null;
        File lF = new File(inputPath);
        if ((lF.canRead()) && lF.isDirectory()) {
            theFileList = lF.listFiles();
            for (int i = 0; i < theFileList.length; i++) {
                String newFilePath = theFileList[i].getAbsolutePath();
                if (theFileList[i].isDirectory()) {

                    acceptedFileList = addToFileLists(acceptedFileList, newFilePath);
                    String nextFileList [] = makeRecursiveFolderList(newFilePath);
                    nestedFileList = mergeFileLists(nestedFileList, nextFileList);
                    nextFileList = null;
                }
                else {
                }
            }
            acceptedFileList = mergeFileLists(acceptedFileList, nestedFileList);
            nestedFileList = null;
        } else {
            System.out.println("Cannot access file:" + inputPath + "#");
            if (!lF.exists()) {
                System.out.println("File does not exist!");
            }
        }
        return acceptedFileList;
    }

    static boolean acceptFile(String fileName, String filter) {
        if (fileName.toLowerCase().endsWith(filter.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    static boolean acceptFile(String fileName, String [] filters) {
        for (int i = 0; i < filters.length; i++) {
            String filter = filters[i];
            if (fileName.toLowerCase().endsWith(filter.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    static String[] mergeFileLists(String[] list1, String[] list2) {
        int size = 0;
        String[] mergedList = null;
        if ((list1 != null) && (list2 != null)) {
            size = list1.length + list2.length;
            mergedList = new String[size];
            int k = 0;
            for (int i = 0; i < list1.length; i++) {
                mergedList[k] = list1[i];
                k++;
            }
            for (int i = 0; i < list2.length; i++) {
                mergedList[k] = list2[i];
                k++;
            }
        } else if ((list1 == null) && (list2 != null)) {
            return list2;
        } else if ((list1 != null) && (list2 == null)) {
            return list1;
        }
        return mergedList;
    }

    static String[] addToFileLists(String[] list1, String element) {
        int size = list1.length + 1;
        String[] mergedList = new String[size];
        int k = 0;
        for (int i = 0; i < list1.length; i++) {
            mergedList[k] = list1[i];
            k++;
        }
        mergedList[k] = element;
        return mergedList;
    }

    static public String[] makeFlatDirList(String inputPath) {
        String[] acceptedFileList = new String[0];
        File[] theFileList = null;
        File lF = new File(inputPath);
        if (lF.canRead()) {
            theFileList = lF.listFiles();
            for (int i = 0; i < theFileList.length; i++) {
                String newFilePath = theFileList[i].getAbsolutePath();
                if (theFileList[i].isDirectory()) {
                    acceptedFileList = addToFileLists(acceptedFileList, newFilePath);
                }
            }
        }
        return acceptedFileList;
    }

    static public int deleteAllSubDirs(String dir) throws Exception {
      if (!dir.endsWith(SEP)) {
          String[] DirList = null;             // Array with files to be processed
          File lF = new File(dir);
          if (!lF.exists())
          {  return 1;
          }
          if (lF.canRead())
          {  DirList = lF.list();
          }
          if (DirList!=null)
          {	  for (int i=0; i< DirList.length; i++)
                { String name = DirList[i];
                    try {
                        deleteAllSubDirs(dir+SEP+name);
                    } catch (Exception e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
          }
          try {
              lF.delete();
          } catch (Exception e) {
             // e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
          }
          if (lF.exists())
          {  return -1;}
          else
          { return 0;}
      }
      else {
          return -1;
      }
    }

    static public void deleteAllFilesFromDir(String dir) {
        String [] DirList = makeFlatFileList(dir, "log");
        if (DirList.length>0) {
            for (int i=0; i< DirList.length; i++) {
                  String path = DirList[i];
                  File aFile = new File (path);
                    if (!aFile.delete()) {
                        System.out.println("Cannot delete file....");
                    }
            }
        }
        else {
            System.out.println("Nothing to delete from: "+dir);
        }
    }
    
    
    
} /// end of FileProcessor
