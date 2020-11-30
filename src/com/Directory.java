package com;

import java.util.ArrayList;

public class Directory
{
    public String Dname;
    public ArrayList<FFile> files;
    public ArrayList<Directory> subdirectories;
    public Directory(String name)
    {
        Dname = name;
        files = new ArrayList<>();
        subdirectories = new ArrayList<>();
    }
}