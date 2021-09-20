import com.sun.nio.sctp.MessageInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
//~~~~~~~~~~~~~~~~~~~~~~~~WELCOME~~~~~~~~~~~~~~~~~~~~~~~~
        String fileName = "backup_raport_output.txt";
        String filePath = System.getProperty("user.dir") + File.separator + fileName;
        ArrayList<FileInfo> fileInfoArray = new ArrayList<>();
        ArrayList<String> arrayOfCommands = new ArrayList<>();
        boolean work = true;

        System.out.println("Hi, I'm the BackUpReader_v2 ");
        System.out.println("Write -help to get more information about commands");
        System.out.println("What You want me to do?");

        while (work) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String command = reader.readLine();
            String[] commands = command.split(" ");
            arrayOfCommands.add(command);
            if (command.equals("-help")) {
                System.out.println(

                        """
                                "show"                                will show all existing backups
                                "save"                                will save raport about all showed backups as backup_raport_output.txt file
                                "last raport"                         will open last saved raport in Notepad++ or Note
                                "find (name_of_backup)"               will show backups by name you put
                                "find+ (name_of_backup)"              will search all backups that contain name you put in their name
                                "clear"                               will clear terminal
                                "end"                                 will shut down this program
                                "Note"                                will open last raport in Note
                                "Notepad"                             will open last raport in Notepad++
                                "command or comar"                    will show history of user commands
                                """);
            }

            if (command.equals("end")) {
                work = false;
            }
            if (command.contains("show")) {
                if (fileInfoArray.isEmpty()) {
                    new LOGIC(fileInfoArray, true);
                /*    DatabaseConection conection = new DatabaseConection();
                    conection.connect();*/
                    System.out.println("\nSave this as new raport?\twrite \"save\"");
                } else {
                    for (FileInfo fileInfo : fileInfoArray) {
                        System.out.println(fileInfo);
                    }
                }
            }
//===========================SAVA_TXT_DATABASE==========================
            if (command.contains("save")) {
                LOGIC.saveRaport(fileInfoArray, fileName);
                DatabaseConection conection = new DatabaseConection();
                conection.connect();
                conection.deleteAll();
                for (FileInfo str : fileInfoArray)
                    try {
                        conection.insertToDataBase(str);
                        System.out.println("save proccess finished");
                    }catch (NullPointerException ex){
                        System.out.println(ex.getMessage());
                    }
                System.out.println("All finished");
            }
            if (command.equals("last raport")) {
                File file = new File(filePath);
                if (!file.exists()) {
                    System.out.println("Raport file doesn't exist");
                } else {
                    File notepadFile = new File("C:\\Program Files (x86)\\Notepad++\\notepad++.exe ");
                    if (notepadFile.exists()) {
                        LOGIC.openFileNotepad(fileName);
                    } else {
                        LOGIC.openFileNote(fileName);
                    }
                }
            }
            //----------------FIND+---------------------
            if (Arrays.asList(commands).contains("find+")) {
                try {
                    String search = "";
                    for (int i = 0; i < commands.length; i++) {
                        if (commands[i].equals("find+")) {
                            search = commands[i + 1];
                        }
                    }
                    if (search.isEmpty()) {
                        System.out.println("nothing to search");
                    } else {
                        ArrayList<FileInfo> searchArray = new ArrayList<>();
                        if (!fileInfoArray.isEmpty()) {
                            for (FileInfo str : fileInfoArray) {
                                if (str.name.contains(search)) {
                                    searchArray.add(str);
                                }
                            }
                        } else {
                            new LOGIC(fileInfoArray, false);
                            for (FileInfo str : fileInfoArray) {
                                if (str.name.contains(search)) {
                                    searchArray.add(str);
                                }
                            }
                        }
                        if (searchArray.isEmpty()) {
                            System.out.println("Nothing");
                        } else {
                            for (FileInfo str : searchArray) {
                                System.out.println(str);
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("nothing to search ");
                }
                //---------------------find--------------------------------------
            } else if (Arrays.asList(commands).contains("find")) {
                try {
                    String search = "";
                    for (int i = 0; i < commands.length; i++) {
                        if (commands[i].equals("find")) {
                            search = commands[i + 1];
                        }
                    }
                    if (search.isEmpty()) {
                        System.out.println("nothing to search");
                    }
                    ArrayList<FileInfo> searchArray = new ArrayList<>();
                    if (!fileInfoArray.isEmpty()) {
                        for (FileInfo str : fileInfoArray) {
                            if (str.name.equals(search)) {
                                searchArray.add(str);
                            }
                        }
                    } else {
                        new LOGIC(fileInfoArray, false);
                        for (FileInfo str : fileInfoArray) {
                            if (str.name.equals(search)) {
                                searchArray.add(str);
                            }
                        }
                    }
                    if (searchArray.isEmpty()) {
                        System.out.println("Nothing");
                    } else {
                        for (FileInfo str : searchArray) {
                            System.out.println(str);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("nothing to search");
                }
            }
            if (command.equals("Note") || (command.equals("note"))) {
                LOGIC.openFileNote(fileName);
            }
            if (command.equals("Notepad") || command.equals("notepad") || (command.equals("Notepad++")) ||
                    (command.equals("notepad++")) || command.equals("open")) {
                LOGIC.openFileNotepad(fileName);
            }
            if (command.equals("command")) {
                System.out.println("command: " + command);
            }
            if (command.contains("comar")) {
                System.out.println("history of commands: ");
                for (String str : arrayOfCommands) {
                    System.out.println(str);
                }
            }


        }


        // Fileblob.DivideAndName(listFileName,listAcronis, listTrueImage);

        //Fileblob.hostnameFinder("FTVIS08-F5B3BC73-E244-414D-94F8-D5E87EA25E67-83843D62-FCFF-4D98-B658-EFDBE383607FA-0002.tibx");


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


    }
}

