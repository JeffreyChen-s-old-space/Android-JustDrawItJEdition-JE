/*
 * Copyright (c) 2018. JE-Chen
 * Edit by JE 2020/1/1
 */

package com.je_chen.justdrawitjedition;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class FileIO {

    protected  String Path="null";
    protected  String File_name="null";
    protected  File SD_Path=null;
    protected Context File_Context;
    protected int READ_BLOCK_SIZE=1000;

    protected  FileIO(Context context){
        this.File_Context=context;
    }

    protected void Save_File(String Save_String,String File_Name) throws IOException{
        FileOutputStream out = File_Context.openFileOutput(File_Name, MODE_PRIVATE);
        OutputStreamWriter sw = new OutputStreamWriter(out);
        sw.write(Save_String);
        //開始輸出
        sw.flush();
        //關閉輸出
        sw.close();
    }

    protected void Save_File(String Save_String) throws IOException{
        FileOutputStream out = File_Context.openFileOutput(this.File_name, MODE_PRIVATE);
        OutputStreamWriter sw = new OutputStreamWriter(out);
        sw.write(Save_String);
        //開始輸出
        sw.flush();
        //關閉輸出
        sw.close();
    }

    protected String Read_File(String File_Name) throws IOException{
        FileInputStream in = File_Context.openFileInput(File_Name);
        InputStreamReader sr = new InputStreamReader(in);
        char[] buff = new char[READ_BLOCK_SIZE];
        String Read_String = "";
        int count;
        while ((count = sr.read(buff)) > 0) {
            String s = String.copyValueOf(buff, 0, count);
            Read_String += s;
            buff = new char[READ_BLOCK_SIZE];
        }
        sr.close();
        return Read_String;
    }

    //設置檔案路徑
    protected void Set_Path(String Path){

        this.Path=Path;

    }
    //設置檔案檔名
    protected void SetFile_Name(String File_name){

        this.File_name=File_name;

    }

    //設置檔案路徑跟檔名
    protected void SetPath_Filename(String Path,String File_name){

        this.Path=Path;
        this.File_name=File_name;

    }

    protected void Set_Outside_Path(File SD_Path){

        this.SD_Path=SD_Path;

    }

    //搜尋檔案是否存在
    protected boolean Search_File_inside(String Path,String File_name){
        this.Path=Path;
        this.File_name=File_name;

        boolean file_exist = false;
        File exist = new File(Path);
        if(exist.getName().equals(File_name)) {
            if (exist.exists()) {
                file_exist = true;
            }else {
                file_exist = false;
            }
        }
        return file_exist;
    }

    //搜尋檔案是否存在
    protected boolean Search_File_outside(File SD_Path,String File_name){
        this.SD_Path=SD_Path;
        this.File_name=File_name;
        boolean file_exist = false;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if(sdCardExist) {
            String search_path = (SD_Path.getPath()+File_name);
            File search_file= new File(search_path);
            if(search_file.exists()){
                file_exist=true;
            }else {
                file_exist=false;
            }
        }
        return file_exist;
    }

    //搜尋檔案是否存在
    protected boolean Search_Path(String Path,String File_name,File SD_Path){
        this.Path=Path;
        this.File_name=File_name;
        boolean file_exist = false;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if(sdCardExist){
            String search_path = (SD_Path.getPath()+File_name);
            File search_out_file= new File(search_path);
            File search_in_file = new File(Path+File_name);
            if(search_out_file.exists()||search_in_file.exists()){
                file_exist=true;
            }else {
                file_exist=false;
            }
        }

        return file_exist;
    }


    //是否安裝此檔案
    protected boolean Have_This_File(Context context,String name) {
        PackageManager manager = context.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase(name))
                return true;
        }
        return false;
    }


}


