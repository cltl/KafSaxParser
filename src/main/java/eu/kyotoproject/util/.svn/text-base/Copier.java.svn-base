package eu.kyotoproject.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
public class Copier {

    static final String SEP = System.getProperty("file.separator");


    static public int moveFileToFolder (String sourceFilePath, String targetFolderPath) {
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            System.out.println("Target folder does not exist!:"+targetFolderPath);
            return -1;
        }
        else {
            File nextFile = new File (sourceFilePath);
            if (nextFile.isFile()) {
                String name = nextFile.getName();
                String newFilePath = targetFolderPath+SEP+name;
                File newFile = new File (newFilePath);
                if (!nextFile.renameTo(newFile)) {
                    System.out.println("Failed to rename the file to:"+newFilePath);
                    return -1;
                }
            }
            return 0;
        }
    }

    static public int moveFileToFolder (String sourceFilePath, String targetFolderPath, String DOCTYPE) {
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            System.out.println("Target folder does not exist!:"+targetFolderPath);
            return -1;
        }
        else {
            File nextFile = new File (sourceFilePath);
            if (nextFile.isFile()) {
                String name = nextFile.getName();
                String newFilePath = targetFolderPath+SEP+name;
                int idx_ext = name.lastIndexOf(".");
                if (idx_ext == -1) {
                    newFilePath += "."+DOCTYPE;
                }
                File newFile = new File (newFilePath);
                if (!nextFile.renameTo(newFile)) {
                    System.out.println("Failed to rename the file to:"+newFilePath);
                    return -1;
                }
            }
            return 0;
        }
    }

    static public int moveFileToFolderWithExtendedName (String sourceFilePath, String targetFolderPath, String targetFileName) {
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            System.out.println("Target folder does not exist!:"+targetFolderPath);
            return -1;
        }
        else {
            File nextFile = new File (sourceFilePath);
            if (nextFile.isFile()) {
                String newFilePath = targetFolderPath + SEP + targetFileName;
                File newFile = new File (newFilePath);
                if (!nextFile.renameTo(newFile)) {
                    System.out.println("Failed to rename the file to:"+newFilePath);
                    return -1;
                }
            }
            return 0;
        }
    }

    static public int copyFileToFolder (String sourceFilePath, String targetFolderPath) {
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            System.out.println("Target folder does not exist!:"+targetFolderPath);
            return -1;
        }
        else {
            File nextFile = new File (sourceFilePath);
            if (nextFile.isFile()) {
                String name = nextFile.getName();
                String newFilePath = targetFolderPath+SEP+name;
                try {
                    fileCopier(sourceFilePath, newFilePath);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to copy the file to:"+newFilePath);
                    return -1;
                }
            }
            return 0;
        }
    }

    static public int copyFileToFolderAndRename (String sourceFilePath, String targetFolderPath, String newName) {
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            System.out.println("Target folder does not exist!:"+targetFolderPath);
            return -1;
        }
        else {
            File nextFile = new File (sourceFilePath);
            //System.out.println("sourceFilePath = " + sourceFilePath);
            if (nextFile.isFile()) {
                String newFilePath = targetFolderPath+SEP+newName;
                try {
                    fileCopier(sourceFilePath, newFilePath);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to copy the file to:"+newFilePath);
                    return -1;
                }
            }
            return 0;
        }
    }

    static public int copyFileToFolder (String sourceFilePath, String targetFolderPath, String DOCTYPE) {
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            System.out.println("Target folder does not exist!:"+targetFolderPath);
            return -1;
        }
        else {
            File nextFile = new File (sourceFilePath);
            if (nextFile.isFile()) {
                String name = nextFile.getName();
                String newFilePath = targetFolderPath+SEP+name;
                int idx_ext = name.lastIndexOf(".");
                if (idx_ext == -1) {
                    newFilePath += "."+DOCTYPE;
                }
                try {
                    fileCopier(sourceFilePath, newFilePath);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to copy the file to:"+newFilePath);
                    return -1;
                }
            }
            return 0;
        }
    }

    static public int copyFileToFolderWithExtendedName (String sourceFilePath, String targetFolderPath, String targetFileName) {
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            System.out.println("Target folder does not exist!:"+targetFolderPath);
            return -1;
        }
        else {
            File nextFile = new File (sourceFilePath);
            if (nextFile.isFile()) {
                String newFilePath = targetFolderPath+SEP+targetFileName;
                try {
                    fileCopier(sourceFilePath, newFilePath);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to copy the file to:"+newFilePath);
                    return -1;
                }
            }
            return 0;
        }
    }

   static public void fileCopier (String sourFilePath, String targetFilePath) throws IOException{
/*
       System.out.println("sourFilePath:"+sourFilePath);
       System.out.println("targetFilePath:"+targetFilePath);
*/
       File inputFile = new File(sourFilePath);
       File outputFile = new File(targetFilePath);

       FileInputStream in = new FileInputStream(inputFile);
       FileOutputStream out = new FileOutputStream(outputFile);
       int c;

       while ((c = in.read()) != -1)
          out.write(c);

       in.close();
       out.close();
   }

    static public int copyFiles (String sourceFolderPath, String targetFolderPath) {
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            if (!targetFolder.mkdir()) {
                System.out.println("Failed to make directory:"+targetFolderPath);
                return -1;
            }
        }
 //       System.out.println("Made directory:"+targetFolderPath);
        String [] allFiles = FileProcessor.makeFlatFileList(sourceFolderPath, "");
        for (int i = 0; i<allFiles.length; i++)  {
            File nextFile = new File (allFiles[i]);
            if (nextFile.isFile()) {
                String name = nextFile.getName();
                String sourceFilePath = nextFile.getAbsolutePath();
                String newFilePath = targetFolderPath+SEP+name;
                try {
                    fileCopier(sourceFilePath, newFilePath);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to copy the file to:"+newFilePath);
                    return -1;
                }
            }
        }
        return allFiles.length;
    }

    static public int copyFolders (String sourceFolderPath, String targetFolderPath) {
//        File sourceFolder = new File (sourceFolderPath);
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            if (!targetFolder.mkdir()) {
                System.out.println("Failed to make directory:"+targetFolderPath);
                return -1;
            }
        }
 //       System.out.println("Made directory:"+targetFolderPath);
        String [] allFiles = FileProcessor.makeFlatFileList(sourceFolderPath, "");
        for (int i = 0; i<allFiles.length; i++)  {
            File nextFile = new File (allFiles[i]);
            if (nextFile.isFile()) {
                String name = nextFile.getName();
                String sourceFilePath = nextFile.getAbsolutePath();
                String newFilePath = targetFolderPath+SEP+name;
                try {
                    fileCopier(sourceFilePath, newFilePath);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to copy the file to:"+newFilePath);
                    return -1;
                }
            }
            else if (nextFile.isDirectory()) {
                String name = nextFile.getName();
                String newFolderPath = targetFolderPath+SEP+name;
                if (copyFolders(nextFile.getPath(),newFolderPath) == -1) {
                    return -1;
                }

            }
        }
        return allFiles.length;
    }

    static public int moveFolders (String sourceFolderPath, String targetFolderPath) {
//        File sourceFolder = new File (sourceFolderPath);
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.exists()) {
            if (!targetFolder.mkdir()) {
//                System.out.println("Failed to make directory:"+targetFolderPath);
                return -1;
            }
        }
 //       System.out.println("Made directory:"+targetFolderPath);
        String [] allFiles = FileProcessor.makeFlatFileList(sourceFolderPath, "");
        for (int i = 0; i<allFiles.length; i++)  {
            File nextFile = new File (allFiles[i]);
            if (nextFile.isFile()) {
                String name = nextFile.getName();
                String newFilePath = targetFolderPath+SEP+name;
                File newFile = new File (newFilePath);
                if (!nextFile.renameTo(newFile)) {
//                    System.out.println("Failed to rename the file to:"+newFilePath);
                    return -1;
                }
 //               System.out.println("Rename the file to:"+newFilePath);
            }
            else if (nextFile.isDirectory()) {
                String name = nextFile.getName();
                String newFolderPath = targetFolderPath+SEP+name;
                if (copyFolders(nextFile.getPath(),newFolderPath) == -1) {
                    return -1;
                }

            }
        }
        return 0;
    }

    static public int renameFolders (String sourceFolderPath, String targetFolderPath) {
        File sourceFolder = new File (sourceFolderPath);
        File targetFolder = new File (targetFolderPath);
        if (!targetFolder.mkdir()) {
            System.out.println("Failed to make directory:"+targetFolderPath);
            return -1;
        }
        sourceFolder.renameTo(targetFolder);
        return 0;
    }
}
