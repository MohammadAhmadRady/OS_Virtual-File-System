package com;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class ContiguousAllocation implements ICommands {
    ArrayList<Boolean> blocks;
    Directory root;
    PriorityQueue<ArrayList<Integer>> freeSpace;

    ContiguousAllocation() {
        freeSpace = new PriorityQueue<>(100, new MyComparator());
        blocks = new ArrayList<Boolean>(100);
        ArrayList<Integer> temp = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            blocks.add(false);
            temp.add(i);
        }
        freeSpace.add(temp);
        root = new Directory("root");
    }

    @Override
    public Boolean createFile(int size, String[] path, int pLength) {
        if ((!path[0].equals("root")) || pLength < 2) {
            System.out.println("Invalid Path!");
            return false;
        }
        if (freeSpace.peek().size() >= size) {
            Directory temp = root;
            boolean exists = false;
            if (pLength > 2) {
                for (int i = 1; i < pLength - 1; i++) {
                    exists = false;
                    for (int t = 0; t < temp.subdirectories.size(); t++) {
                        if (temp.subdirectories.get(t).Dname.equals(path[i])) {
                            temp = temp.subdirectories.get(t);
                            exists = true;
                        }
                    }
                }
            } else {
                exists = true;
            }
            if (exists) {
                FFile f = new FFile(path[pLength - 1], size);
                ArrayList<Integer> tempSpace = freeSpace.poll();
                for (int i = 0; i < size; i++) {
                    blocks.set(tempSpace.get(i), true);
                    f.allocatedBlocks.set(i, tempSpace.get(i));
                }
                temp.files.add(f);
                if (tempSpace.size() > size) {
                    ArrayList<Integer> free = new ArrayList<>(tempSpace.size() - size);
                    for (int i = size; i < tempSpace.size(); i++)
                        free.add(tempSpace.get(i));
                    freeSpace.add(free);
                }
                return true;
            } else {
                System.out.println("Path NOT exists!");
                return false;
            }
        } else {
            System.out.println("NO space Available!");
            return false;
        }
    }

    @Override
    public Boolean createFolder(String[] path, int pLength) {
        if ((!path[0].equals("root")) || pLength < 2) {
            System.out.println("Invalid Path!");
            return false;
        }
        Directory temp = root;
        boolean exists = false;
        if (pLength > 2) {
            for (int i = 1; i < pLength - 1; i++) {
                exists = false;
                for (int t = 0; t < temp.subdirectories.size(); t++) {
                    if (temp.subdirectories.get(t).Dname.equals(path[i])) {
                        temp = temp.subdirectories.get(t);
                        exists = true;
                    }

                }

            }
        } else {
            exists = true;
        }
        if (exists) {
            Boolean repeated = false;
            for (int i = 0; i < temp.subdirectories.size(); i++) {
                if (temp.subdirectories.get(i).Dname.equals(path[pLength - 1]))
                    repeated = true;
            }
            if (!repeated) {
                Directory obj = new Directory(path[pLength - 1]);
                temp.subdirectories.add(obj);
                return true;
            } else {
                System.out.println("Directory already exists!");
                return false;
            }
        } else {
            System.out.println("Path NOT exists!");
            return false;
        }
    }

    @Override
    public Boolean deleteFile(String[] path, int pLength) {
        if ((!path[0].equals("root")) || pLength < 2) {
            System.out.println("Invalid Path!");
            return false;
        }
        Directory temp = root;
        boolean exists = false;
        if (pLength > 2) {
            for (int i = 1; i < pLength - 1; i++) {
                exists = false;
                for (int t = 0; t < temp.subdirectories.size(); t++) {
                    if (temp.subdirectories.get(t).Dname.equals(path[i])) {
                        temp = temp.subdirectories.get(t);
                        exists = true;
                    }
                }
            }
        } else {
            exists = true;
        }
        if (exists) {
            for (int i = 0; i < temp.files.size(); i++) {
                if (temp.files.get(i).fName.equals(path[pLength - 1])) {
                    int size = temp.files.get(i).allocatedBlocks.size();
                    for (int j = temp.files.get(i).allocatedBlocks.get(0); j < size; j++) {
                        blocks.set(j, false);
                    }
                    freeSpace.add(temp.files.get(i).allocatedBlocks);
                    temp.files.remove(i);
                    System.out.println("Delete is done");
                    return true;
                }
            }
        } else {
            System.out.println("Path NOT exists!");
            return false;
        }
        return false;
    }

    @Override
    public Boolean deleteFolder(String[] path, int pLength) {
        Directory temp = root;
        Directory delete = null;
        String deleted = path[pLength - 1];
        boolean exists = false;
        for (int i = 1; i < pLength - 1; i++) {
            exists = false;
            for (int t = 0; t < temp.subdirectories.size(); t++) {
                if (temp.subdirectories.get(t).Dname.equals(path[i])) {
                    temp = temp.subdirectories.get(t);
                    exists = true;
                }
            }
        }
        if (temp == root) {
            for (int i = 0; i < temp.subdirectories.size(); i++) {
                if (root.subdirectories.get(i).Dname.equals(deleted)) {
                    delete = root.subdirectories.get(i);
                    root.subdirectories.remove(i);
                    deleteThis(delete);
                    return true;
                }
            }

        }
        if (exists) {
            for (int i = 0; i < temp.subdirectories.size(); i++) {
                if (temp.subdirectories.get(i).Dname.equals(deleted)) {
                    delete = temp.subdirectories.get(i);
                    temp.subdirectories.remove(i);
                    deleteThis(delete);
                    return true;
                }
            }
        }
        System.out.println("Not Found");
        return false;
    }

    private void deleteThis(Directory delete) {
        for (int i = 0; i < delete.files.size(); i++) {
            for (int j = 0; j < delete.files.size(); j++) {
                int size = delete.files.get(i).allocatedBlocks.size();
                for (int d = delete.files.get(i).allocatedBlocks.get(0); d < size; d++)
                    blocks.set(d, false);
                freeSpace.add(delete.files.get(i).allocatedBlocks);
                delete.files.remove(i);
            }
        }
        for (int t = 0; t < delete.subdirectories.size(); t++)
            deleteThis(delete.subdirectories.get(t));
    }

    @Override
    public void displayDiskStatus() {
        int numberOfAllocated = 0;
        for (int i = 0; i < blocks.size(); i++)
            if (blocks.get(i))
                numberOfAllocated++;
        System.out.println("Number of allocated blocks: " + numberOfAllocated);
        System.out.println("Number of free blocks: " + (100 - numberOfAllocated));
        for (int i = 0; i < 100; i++) {
            if (!blocks.get(i))
                System.out.print("(" + i + "f) ");
            else
                System.out.print("(" + i + "a) ");
            if (i % 10 == 0 && i != 0)
                System.out.println(" ");
        }
        System.out.println(" ");
    }

    @Override
    public void displayDiskStructure(Directory root, String indent) {
        System.out.println(indent + '<' + root.Dname + '>');
        indent += "\t";
        for (int i = 0; i < root.files.size(); i++)
            System.out.println(indent + root.files.get(i).fName);
        for (int i = 0; i < root.subdirectories.size(); i++)
            displayDiskStructure(root.subdirectories.get(i), indent);
    }

    private String dDiskStructure(Directory root, String indent) {
        String out = (indent + '<' + root.Dname + '>' + '\n');
        indent += "\t";
        for (int i = 0; i < root.files.size(); i++)
            out += (indent + root.files.get(i).fName + '\n');
        for (int i = 0; i < root.subdirectories.size(); i++)
            out += (dDiskStructure(root.subdirectories.get(i), indent));
        return out;
    }

    public void saveWork() {
        String temp = dDiskStructure(root, "");
        try {
            WriteToFile(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void WriteToFile(String data) throws Exception//5
    {
        File obj1 = new File("DiskStructure.txt");
        BufferedWriter w = new BufferedWriter(new FileWriter(obj1));
        w.write(data);
        w.close();
    }

    public static String takeInput() throws Exception  // Take input from file
    {
        File file = new File("DiskStructure.txt");
        BufferedReader READ = new BufferedReader(new FileReader(file));
        int q = READ.read();
        String data = new String("");
        while (q != -1) {
            data += (char) q;
            q = READ.read();
        }
        return data;
    }

}