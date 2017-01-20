package org.moxiu.sender;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by MX-092 on 2017/1/19.
 */
public class SendMail {
    private static final Logger logger= LoggerFactory.getLogger(SendMail.class);
    public static void callShell(String subject,String context,String mailto,String cc,String sender){

        context = "Subject:"+subject+"\n"+"To:"+mailto+"\n"+"CC:"+cc+"\n"+context;
        MakeFileWrite.deleteDirectory(System.getProperty("user.dir")+ File.separator+"tmp");
        MakeFileWrite.createDirectory(System.getProperty("user.dir")+File.separator+"tmp");
        MakeFileWrite.createDirectory(System.getProperty("user.dir")+File.separator+"tmp");
        String uuid = UUID.randomUUID().toString();
        MakeFileWrite.writeFile(System.getProperty("user.dir")+File.separator+"tmp"+File.separator+uuid,context,"utf-8");
        logger.info("邮件发送内容:["+context+"]");
        try{
            String[] str={
                    "bash","-c","(/bin/echo\"Content-Type:text/html;charset=utf-8\";cat ./tmp/"+uuid+")|/usr/sbin/sendmail"+mailto};
            Runtime.getRuntime().exec(str);
            }catch(IOException e){
            logger.info(e.toString());
            e.printStackTrace();
        }
    }
    public static void callShell(String subject,String context,String mailto,String sender){
        context="From:"+sender+"\n"+"Subject:"+subject+"\n"+"To:"+mailto+"\n"+context;
        MakeFileWrite.createDirectory(System.getProperty("user.dir")+File.separator+"tmp");
        String uuid=UUID.randomUUID().toString();
        MakeFileWrite.writeFile(System.getProperty("user.dir")+File.separator+"tmp"+File.separator+uuid,context,"utf-8");
        logger.info(subject+" "+mailto+"邮件已发送");
        try {
            String[] str = {
                    "bash",
                    "-c",
                    "(/bin/echo \"Content-Type: text/html;charset=utf-8\"; cat ./tmp/"
                            + uuid + ")| /usr/sbin/sendmail " + mailto };
            Runtime.getRuntime().exec(str);
            }catch(IOException e){
            logger.info(e.toString());
            e.printStackTrace();
        }
    }
    public static void Warming(String subject,String context,String mailto,String sender){
        context = "From:"+sender+"\n"+"Subject:"+subject+"\n"+"To:"+mailto+"\n"+context+"\n"+"邮件内容为空";
        MakeFileWrite.createDirectory(System.getProperty("user.dir")+File.separator+"tmp");
        String uuid = UUID.randomUUID().toString();
        MakeFileWrite.writeFile(System.getProperty("user.dir")+File.separator+"tmp"+File.separator+uuid,context,"utf-8");
        logger.info("邮件发送内容：["+context+"]");
        try{
            String[] str={
                    "bash","-c","(/bin/echo \"Content-Type:text/html;charset=utf-8\";cat ./tmp/" +uuid+")|/usr/sbin/sendmail"+mailto};
            Runtime.getRuntime().exec(str);
            }catch(IOException e){
            logger.error(e.toString());
            e.printStackTrace();
            }
    }
}
