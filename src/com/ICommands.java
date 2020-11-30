package com;

public interface ICommands
{
    Boolean createFile(int size , String path[] , int pLength);
    Boolean createFolder( String path[] , int pLength);
    Boolean deleteFile( String path[] , int pLength);
    Boolean deleteFolder( String path[] , int pLength);
    void displayDiskStatus();
    void displayDiskStructure(Directory root, String indent);
}