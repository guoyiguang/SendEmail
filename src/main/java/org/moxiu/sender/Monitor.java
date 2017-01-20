package org.moxiu.sender;

import com.sun.jmx.snmp.tasks.ThreadService;
import org.moxiu.bean.MailInfo;
import org.moxiu.bean.ReportEmail;
import org.moxiu.util.JDBCUtil;
import org.moxiu.util.ReadProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by MX-092 on 2017/1/19.
 */
public class Monitor {
        private static final Logger logger = LoggerFactory.getLogger(Monitor.class);
        private static final String sql1 = "select * from support_mail_gyg where state='1'";
        private static final String sql4 = "select * from support_mail_copy_gyg where state='4'";
        //自动监控查找是否有需要发送的邮件

    public static void monitor(){
        Timer timer = new Timer();
        // 立刻进入循环，并且从当下初始化时间计算到现在。
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if((new Date().getTime()-60000)>getDate().getTime()){ //过了定时发送的时间，不能发送
                    logger.info("非定时发送的时间，不能发送");
                }else{ //到了发动的时间，可以发送
                    logger.info("邮件定时发送程序启动");
                    //第一类需要发送邮件
                    try{
                        sendMail(mergerReportMail(JDBCUtil.getReportEmail(sql1)));
                    }catch(Exception e){
                        logger.info(sql1+"出现异常");
                    }
                }
            }
        },getDate(),100*60*60*24); //每天执行一次
    }
    public static Date getDate(){
        Properties properties = null;
        try{
            properties = ReadProperties.getConf(System.getProperty("user.dir")+ File.separator+"conf"+File.separator+"conf.properties");

        }catch (Exception e){
            logger.info("配置文件异常");
            logger.info(e.toString());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(properties.get("HOUR_OF_DAY").toString()));
        calendar.set(Calendar.SECOND,Integer.valueOf(properties.get("SECOND").toString()));
        calendar.set(Calendar.MILLISECOND,Integer.valueOf(properties.get("MILLISECOND").toString()));
        logger.info("启动时间HOUR_OF_DAY:"+Integer.valueOf(properties.get("HOUR_OF_DAY").toString()));
        logger.info("启动时间MINUTE:"+Integer.valueOf(properties.get("MINUTE").toString()));
        logger.info("启动时间SECOND:"+Integer.valueOf(properties.get("SECOND").toString()));
        logger.info("启动时间MILLISECOND:"+Integer.valueOf(properties.get("MILLISECOND").toString()));
        Date time = calendar.getTime(); //得到执行任务的时间，此处为今天的 09:30
        return time;
    }
    public static void main(String[] args){

        try {
            List<ReportEmail> relist1 = JDBCUtil.getReportEmail(sql4);
            // 相同邮件合并
            Map<String, List<ReportEmail>> map = mergerReportMail(relist1);
            for (String key : map.keySet()) {
                try {
                    new ThreadSender(key, map.get(key)).run();
                } catch (Exception e) {
                    logger.info("[" + key + "]:发送失败");
                    logger.error(e.toString());
                    MailInfo minfo = new MailInfo(new Date(), map.get(key).get(0).getDescribe(), map.get(key).get(0).getMailto(), "-1", map.get(key).get(0).getSubject());
                    JDBCUtil.toMailInfo(minfo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.toString());
            logger.info(sql1+"出现异常");
        }
    }
    //发送邮件
    public static void sendMail(Map<String,List<ReportEmail>>map){
        for(String key:map.keySet()){
            // 相同需要合并的邮件
            try{
                new ThreadSender(key,map.get(key)).run();
                Thread.sleep(5000);
            }catch (Exception e){
                logger.info("["+key+"]：发送失败");
                logger.error(e.toString());
                MailInfo minfo = new MailInfo(new Date(),map.get(key).get(0).getDescribe(),map.get(key).get(0).getMailto(),"-1",map.get(key).get(0).getSubject());
                JDBCUtil.toMailInfo(minfo);
            }
        }
    }
    // 邮件整合
    public static Map<String,List<ReportEmail>> mergerReportMail(List<ReportEmail> relist){
        // 把相同收件人，抄送人，主题的邮件进行整合
        Map<String,List<ReportEmail>> map = new HashMap<String,List<ReportEmail>>();
        List<ReportEmail> li = null;
        for(ReportEmail re:relist){
            if(null!=map.get(re.getSubject()+"[&]"+re.getMailto()+"[&]"+re.getSender()+"[&]"+re.getWarming())){
                li = map.get(re.getSubject()+"[&]"+re.getMailto()+"[&]"+re.getSender()+"[&]"+re.getWarming());
                li.add(re);
                map.put(re.getSubject()+"[&]"+re.getMailto()+"[&]"+re.getSender()+"[&]"+re.getWarming(), li);
            }else{
                li = new ArrayList<ReportEmail>();
                li.add(re);
                map.put(re.getSubject()+"[&]"+re.getMailto()+"[&]"+re.getSender()+"[&]"+re.getWarming(), li);
            }
        }
        return map;
    }
}
