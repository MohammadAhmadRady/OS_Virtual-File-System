package com;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    public static String takeInput() throws Exception  // Take input from file
    {
        File file = new File("data.txt");
        BufferedReader READ = new BufferedReader(new FileReader(file));
        int q = READ.read();
        String data = new String("");
        while (q != -1) {
            data += (char) q;
            q = READ.read();
        }
        return data;
    }

    public static void WritInput(String data) throws Exception//5
    {
        File obj1 = new File("data.txt");
        BufferedWriter w = new BufferedWriter(new FileWriter(obj1));
        w.write(data);
        w.close();
    }

    public static void main(String[] args) throws Exception {

        ContiguousAllocation c = new ContiguousAllocation();
        String Comm = "";
        String in = takeInput();
        String[] alldata = in.split("#");
        for (int i = 0; i < alldata.length; i++) {

            String mm[] = alldata[i].split(" ");
            int length = mm.length;
            if (mm.length == 1) {
                if (mm[0].equals("DisplayDiskStatus")) {
                    c.displayDiskStatus();
                } else if (mm[0].equals("DisplayDiskStructure")) {
                    c.displayDiskStructure(c.root, "");
                } else if (mm[0].equals("exit")) {
                    c.saveWork();
                    WritInput(Comm);
                    break;
                } else {
                    System.out.println("Invalid command");
                }
            } else {
                String function = mm[0];
                String path[] = mm[1].split("/");
                if (function.equals("CreateFile")) {
                    int size = Integer.valueOf(mm[length - 1]);

                    if (c.createFile(size, path, path.length)) {
                        Comm += in + '#';
                    }
                } else if ((mm.length == 2) && (function.equals("CreateFolder"))) {
                    if (c.createFolder(path, path.length)) {
                        Comm += in + '#';
                    }
                } else if (mm.length == 2 && function.equals("DeleteFile")) {
                    if (c.deleteFile(path, path.length)) {
                        Comm += in + '#';
                    }
                } else if (mm.length == 2 && function.equals("DeleteFolder")) {
                    if (c.deleteFolder(path, path.length)) {
                        Comm += in + '#';
                    }
                } else {
                    System.out.println("Invalid command");
                }
            }
        }
        ArrayList<String> COMM = new ArrayList<>();
        while (true) {
            System.out.println("enter an allocation scheme Number: ");
            System.out.println("1)contiguous\n" +
                    "2)linked\n" +
                    "3)indexed");
            Scanner SC = new Scanner(System.in);
            int input = SC.nextInt();
            if (input == 1) {
                while (true) {
                    Scanner iput = new Scanner(System.in);
                    System.out.println("Enter your command: ");
                    String commands[] = iput.nextLine().split(" ");
                    int length = commands.length;
                    if (commands.length == 1) {
                        if (commands[0].equals("DisplayDiskStatus")) {
                            c.displayDiskStatus();
                        } else if (commands[0].equals("DisplayDiskStructure")) {
                            c.displayDiskStructure(c.root, "");
                        } else if (commands[0].equals("exit")) {
                            c.saveWork();
                            WritInput(Comm);
                            break;
                        } else {
                            System.out.println("Invalid command");
                        }
                    } else {
                        String function = commands[0];
                        String path[] = commands[1].split("/");
                        if (function.equals("CreateFile")) {
                            int size = Integer.valueOf(commands[length - 1]);

                            if (c.createFile(size, path, path.length)) {
                                Comm += in + '#';
                            }
                        } else if ((commands.length == 2) && (function.equals("CreateFolder"))) {
                            if (c.createFolder(path, path.length)) {
                                Comm += in + '#';
                            }
                        } else if (commands.length == 2 && function.equals("DeleteFile")) {
                            if (c.deleteFile(path, path.length)) {
                                Comm += in + '#';
                            }
                        } else if (commands.length == 2 && function.equals("DeleteFolder")) {
                            if (c.deleteFolder(path, path.length)) {
                                Comm += in + '#';
                            }
                        } else {
                            System.out.println("Invalid command");
                        }
                    }
                }
            } else if (input == 2) {

                LinkedAllocation l = new LinkedAllocation();
                while (true) {
                    Scanner iput = new Scanner(System.in);
                    System.out.println("Enter your command: ");
                    String commands[] = iput.nextLine().split(" ");
                    int length = commands.length;
                    if (commands.length == 1) {
                        if (commands[0].equals("DisplayDiskStatus")) {
                            l.displayDiskStatus();
                        } else if (commands[0].equals("DisplayDiskStructure")) {
                            l.displayDiskStructure(l.root, "");
                        } else if (commands[0].equals("exit")) {
                            l.saveWork();
                            WritInput(Comm);
                            break;
                        } else {
                            System.out.println("Invalid command");
                        }
                    } else {
                        String function = commands[0];
                        String path[] = commands[1].split("/");
                        if (function.equals("CreateFile")) {
                            int size = Integer.valueOf(commands[length - 1]);

                            if (l.createFile(size, path, path.length)) {
                                Comm += in + '#';
                            }
                        } else if ((commands.length == 2) && (function.equals("CreateFolder"))) {
                            if (l.createFolder(path, path.length)) {
                                Comm += in + '#';
                            }
                        } else if (commands.length == 2 && function.equals("DeleteFile")) {
                            if (l.deleteFile(path, path.length)) {
                                Comm += in + '#';
                            }
                        } else if (commands.length == 2 && function.equals("DeleteFolder")) {
                            if (l.deleteFolder(path, path.length)) {
                                Comm += in + '#';
                            }
                        } else {
                            System.out.println("Invalid command");
                        }
                    }
                }
            } else if (input == 3) {
                //IndexedAllocation i = new IndexedAllocation();
                while (true) {
                    //indexed allocation commands go here
                    break;
                }
            } else {
                break;
            }
        }
    }
}