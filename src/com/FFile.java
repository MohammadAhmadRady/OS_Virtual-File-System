package com;

import java.util.ArrayList;

public class FFile
{
    public String fName;
    public ArrayList<Integer> allocatedBlocks;
    public  int size;
    FFile()
    {
        allocatedBlocks = new ArrayList<>();
    }
    FFile(String name, int sz){
        fName = name;
        size = sz;
        allocatedBlocks = new ArrayList<>(sz);
        for (int i = 0; i < sz; i++)
            allocatedBlocks.add(0);
    }
}