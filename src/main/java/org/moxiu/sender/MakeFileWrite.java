package org.moxiu.sender;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.spec.ECField;

/**
 * Created by MX-092 on 2017/1/19.
 */
public class MakeFileWrite {

    /**
     * 给定一个路径，创建目录
     * @param path
     * @return
     */
    public static boolean createDirectory(String path)
    {
        boolean flag=false;
        File file = new File(path);
        if((!file.exists())&&(!file.isDirectory())){
            file.mkdir();
        }
        return flag;
    }
    /**
     * 给定一个路径删除目录
      */
    public static boolean deleteDirectory(String path){
        File file = new File(path);
        boolean flag = false;
        if((file.exists())&&file.isDirectory())
        {
            File[] files = file.listFiles();
            for(File f:files){
                if(f.isFile()){
                    f.delete();
                }
            }
            file.delete();
            flag=true;
        }
        return flag;
    }
    /**
     * 给定一个文件名，文件内容，编码格式，写入内容
     */
    public static boolean writeFile(String filename,String context,String code){

        boolean flag = false;
        try {
            File file = new File(filename);
            if (createFile(file))  //调用写好的类，如果文件不存在则先创建文件
                flag = writeTxtFile(context, file, code); //调用定义好的写文件类，并制定编码格式
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 给定一个文件名，判断如果文件不存在则创建文件
     */
    public static boolean createFile(File fileName) throws Exception{
        boolean flag = false;
        try{
            if(!fileName.exists()){
                fileName.createNewFile();
                flag=true;
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 给定内容，文件名，编码格式 将内容写入文件
     */
    public static boolean writeTxtFile(String content,File fileName,String code){
        RandomAccessFile mm = null;

        boolean flag = false;
        FileOutputStream o = null;
        try{
            o = new FileOutputStream(fileName);
            if((null == code) || (""==code.trim())){
                code="utf-8";
            }
            o.write(content.getBytes(code));
            o.close();
            flag=true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            if(mm!=null){
                try{
                    mm.close();
                }catch(IOException e)
                {
                    e.printStackTrace();
                }
                finally{
                    mm=null;
                }
            }
        }
        return flag;
    }
}
