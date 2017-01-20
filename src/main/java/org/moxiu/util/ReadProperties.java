package org.moxiu.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by MX-092 on 2017/1/18.
 */
public class ReadProperties {
    public static Properties getConf(String filePath){
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(filePath);
            properties.load(in);
        }catch(FileNotFoundException f)
        {
            f.getMessage();
        }catch(IOException i)
        {
            i.getMessage();
        }finally{
        if(in!=null){
            try{
                in.close();
            }catch(IOException e){
                e.getMessage();
            }finally {
                in = null;
            }
        }
        }
        return properties;
    }
   /* public static void main(String args[])
    {
            Properties d =getConf("test.properties");
            System.out.print(d.get("guoyiguang"));
    }*/
}
