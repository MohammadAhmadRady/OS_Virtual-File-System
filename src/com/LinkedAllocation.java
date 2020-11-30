package com;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class LinkedAllocation implements  ICommands
{
    ArrayList<Boolean> blocks;
    LinkedList<Integer> linked ;
    int numberOfAllocated;
    Directory root;

    boolean isBlockFree(int bIndex)
    {
        return blocks.get(bIndex);
    }
    LinkedAllocation()
    {
        blocks= new ArrayList<>(100);
        numberOfAllocated = 0;
        linked = new LinkedList<>();
        for(int i=0;i<100;i++)
        {
            blocks.add(true);
        }
        root=new Directory("root");
    }
    @Override
    public Boolean createFile(int size, String[] path, int pLength)
    {
        Directory temp = root;
        String filename=path[pLength-1];
        boolean exists = false;
        for (int i =1 ; i<pLength-1 ; i++)
        {
            exists = false;
            for(int t=0;t<temp.subdirectories.size(); t++)
            {
                if(temp.subdirectories.get(t).Dname.equals(path[i])) ///if the path given exists in the structure.
                {
                    temp = temp.subdirectories.get(t);///change the current root to temp...
                    exists=true;
                }

            }
        }
        if(temp==root)
        {
            for (int i = 0 ; i<temp.files.size() ; i++)
            {
                if(root.files.get(i).fName.equals(filename))
                {
                    System.out.println("file already exists");
                    return false;
                }
            }
            FFile newFile=new FFile();
            if (!checkAllAvail(size,newFile))
            {
                System.out.println("Not avail Blocks");
                return false;
            }
            newFile.fName=filename;
            newFile.size=size;
            root.files.add(newFile);
            int newIndex = root.files.indexOf(newFile);
            System.out.println("file added success");
        }
        if(exists)
        {
            for (int i = 0 ; i<temp.files.size() ; i++)
            {
                if(temp.files.get(i).fName.equals(filename))
                {
                    System.out.println("file already exists");
                    return false;
                }

            }
            FFile newFile=new FFile();
            if (!checkAllAvail(size,newFile))
            {
                System.out.println("Not avail Blocks");
                return false;
            }
            newFile.fName=filename;
            newFile.size=size;
            System.out.println(filename);
            temp.files.add(newFile);
            System.out.println("file added success");
            return true;
        }
        return false;
    }

    private boolean checkAllAvail(int size,FFile file)
    {
        if (numberOfAllocated+size>100)
        {
            return false;
        }
        for(int i=0;i<size;i++)
        {
            int random = (int )(Math.random() * 99 + 0);
            if(isBlockFree(random))
            {
                blocks.set(random,false);
                numberOfAllocated++;
                linked.add(random);
                file.allocatedBlocks.add(random);
            }
            else
            {
                i--;
            }
        }
        linked.add(-1);
        return true;
    }

    @Override
    public Boolean createFolder(String[] path, int pLength) {
        Directory temp = root;
        String dName=path[pLength-1];
        boolean exists = false;
        for (int i =1 ; i<pLength-1 ; i++)
        {
            exists = false;
            for(int t=0;t<temp.subdirectories.size(); t++)
            {
                if(temp.subdirectories.get(t).Dname.equals(path[i]))
                {
                    temp = temp.subdirectories.get(t);
                    exists=true;
                }

            }

        }

        if(temp==root)
        {
            for (int i = 0 ; i<temp.subdirectories.size() ; i++)
            {
                if(root.subdirectories.get(i).Dname.equals(dName))
                {
                    System.out.println("file already exists");
                    return false;
                }

            }

            Directory d = new Directory(dName);
            root.subdirectories.add(d);
            System.out.println("Folder added success");
        }
        if(exists)
        {
            for (int i = 0 ; i<temp.subdirectories.size() ; i++)
            {
                if(temp.subdirectories.get(i).Dname.equals(dName))
                {
                    System.out.println("file already exists");
                    return false;
                }
            }
            Directory d = new Directory(dName);
            root.subdirectories.add(d);
            System.out.println("Folder added success");
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteFile(String[] path, int pLength)
    {
        Directory temp = root;
        String filename = path[pLength-1];
        boolean exists = false;
        for (int i =1 ; i<pLength-1 ; i++)
        {
            exists = false;
            for(int t=0;t<temp.subdirectories.size(); t++)
            {
                if(temp.subdirectories.get(t).Dname.equals(path[i]))
                {
                    temp = temp.subdirectories.get(t);
                    exists=true;
                }
            }
        }
        if(temp==root)
        {
            for (int i = 0 ; i<temp.files.size() ; i++)
            {
                if(root.files.get(i).fName.equals(filename))
                {
                    int size = root.files.get(i).allocatedBlocks.size();
                    numberOfAllocated -= size;
                    ArrayList<Integer> arr = root.files.get(i).allocatedBlocks;
                    for (int j=0;j<size;j++)
                    {
                        blocks.set(arr.get(j),true);
                    }
                    root.files.remove(i);
                    System.out.println("Delete is done");
                    return true;
                }
            }
        }
        if(exists)
        {
            for (int i = 0 ; i<temp.files.size() ; i++)
            {
                if(temp.files.get(i).fName.equals(filename))
                {
                    FFile file = temp.files.get(i);
                    temp.files.remove(i);
                    int size = file.size;
                    numberOfAllocated -= size;
                    ArrayList<Integer> arr = file.allocatedBlocks;
                    for (int j=0;j<size;j++)
                    {
                        blocks.set(arr.get(i),true);
                    }
                    System.out.println("Delete is done");
                    return true;
                }
            }
        }
        System.out.println("Not Found");
        return false;
    }

    @Override
    public Boolean deleteFolder(String[] path, int pLength)
    {
        Directory temp = root;
        Directory delete = null;
        String deleted = path[pLength-1];
        boolean exists = false;
        for (int i =1 ; i<pLength-1 ; i++)
        {
            exists = false;
            for(int t=0;t<temp.subdirectories.size(); t++)
            {
                if(temp.subdirectories.get(t).Dname.equals(path[i]))
                {
                    temp = temp.subdirectories.get(t);
                    exists=true;
                }
            }
        }
        if(temp==root)
        {
            for (int i = 0 ; i<temp.subdirectories.size() ; i++)
            {
                if(root.subdirectories.get(i).Dname.equals(deleted))
                {
                    delete = root.subdirectories.get(i);
                    root.subdirectories.remove(i);
                    deleteThis(delete);
                    return true;
                }
            }

        }
        if(exists)
        {
            for (int i = 0 ; i<temp.subdirectories.size() ; i++)
            {
                if(temp.subdirectories.get(i).Dname.equals(deleted))
                {
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

    private void deleteThis(Directory delete)
    {
        for (int i=0;i<delete.files.size();i++)
        {
            FFile toDelete = delete.files.get(i);
            int size = toDelete.size;
            numberOfAllocated += size;
            ArrayList<Integer> arr = toDelete.allocatedBlocks;
            for(int j=0;j<arr.size();j++)
            {
                blocks.set(arr.get(j),true);
            }
        }
        for (int t=0;t<delete.subdirectories.size();t++)
            deleteThis(delete.subdirectories.get(t));
    }

    @Override
    public void displayDiskStatus()
    {
        System.out.println("Number of allocated blocks: " + numberOfAllocated);
        System.out.println("Number of free blocks: " + (100-numberOfAllocated));
        for(int i=0;i<100;i++)
        {
            if(blocks.get(i))
                System.out.print("("+i+"f)");
            else
                System.out.print("("+i+"a)");
            if(i%10==0 && i!=0)
                System.out.println(" ");
        }
        System.out.println(" ");
    }

    @Override
    public void displayDiskStructure(Directory root, String indent)
    {
        System.out.println(indent + '<' + root.Dname + '>');
        indent += "\t";
        for (int i = 0; i< root.files.size() ; i++)
            System.out.println(indent + root.files.get(i).fName);
        for(int i=0; i<root.subdirectories.size();i++)
            displayDiskStructure(root.subdirectories.get(i), indent);
    }

    private String dDiskStructure (Directory root, String indent) {
        String out = (indent + '<' + root.Dname + '>' + '\n');
        indent += "\t";
        for (int i = 0; i < root.files.size(); i++)
            out += (indent + root.files.get(i).fName + '\n');
        for (int i = 0; i < root.subdirectories.size(); i++)
            out += (dDiskStructure(root.subdirectories.get(i), indent));
        return out;
    }
    public void saveWork (){
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