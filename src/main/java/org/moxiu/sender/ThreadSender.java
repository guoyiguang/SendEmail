package org.moxiu.sender;

import org.moxiu.bean.MailInfo;
import org.moxiu.bean.ReportEmail;
import org.moxiu.util.ContextFormat;
import org.moxiu.util.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by MX-092 on 2017/1/19.
 */
public class ThreadSender implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ThreadSender.class);
    private List<ReportEmail> list = new ArrayList<ReportEmail>();
    private String key;

    public ThreadSender(String key, List<ReportEmail> relist) {
        this.key = key;
        this.list = relist;
    }

    @Override
    public void run() {
        logger.info("ThreadSender.start[普通邮件发送启动]");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String[] head = key.split("\\[&\\]");//标题
        String context = "", subject = head[0], Mailto = head[1], Sender = head[2], warm = head[3], buttom = "";
        String[] r = null;
        try {
            if (null != list) {
                r = new String[list.size()]; //对需要合并的邮件进行排序处理 Merge
                for (ReportEmail re : list) {
                    String[] fields = re.getFields().split(",");
                    if (null != re.getButtom() && 0 < re.getButtom().length()) {
                        buttom = buttom + "<br/>" + re.getButtom();
                    }
                    conn = JDBCUtil.getCon(re.getIp(), re.getPort(),
                            re.getDb_name(), re.getUser(), re.getPswd());
                    ps = conn.prepareStatement(re.getSqlstatement());
                    rs = ps.executeQuery();

                    Map<String, String> detail = new LinkedHashMap<String, String>();
                    int index = 0;
                    while (rs.next()) {
                        String str = "";
                        for (int i = 0; i < fields.length; i++) {
                            str += "" + fields[i] + "[:]" + rs.getString(i + 1)
                            +"[;]";
                        }
                        detail.put(String.valueOf(index), str); //日期[:]2016-02-27[;]
                        index++;
                    }
                    if (detail.size() > 0) {
                        r[(null != re.getMerge()) ? (Integer.valueOf(re.getMerge()) - 1) : 0] = ContextFormat.contextFormat(detail, re.getFields());
                    } else {
                        //报警邮件
                        SendMail.Warming("邮件发送系统报警_" + re.getSubject(), "邮件数据异常，请查看数据库异常，并及时处理", warm, "mx-www");

                    }
                }
            }
            // 邮件整合完成，开始发送
            for (String con : r) {
                if (10 >= con.length()) { //邮件中有空内容的返回报警
                    context = null;
                    break;
                }
                context += con;
            }
            if (null != context && context.length() > 10 && "null" != context) {//邮件不为空时发送
                if (null != buttom && 0 < buttom.length()) {
                    context = context + buttom;
                } else {
                    context = context + "<a style='margin-top: 15px;font-family: Arial;font-size:95%;border-spacing:0px; display:block; color:#000;' href='http://mcmp.moxiu.net'>Data Source:mcmp.moxiu.net</a>";
                }
                SendMail.callShell(subject, context, Mailto, Sender);
                MailInfo minfo = new MailInfo(new Date(), list.get(0).getDescribe(), list.get(0).getMailto(), "0", list.get(0).getSubject(), context);
                JDBCUtil.toMailInfo(minfo);
            } else {
                // 报警邮件
                SendMail.Warming("邮件发送系统报警_" + subject, "邮件数据异常，请查看数据库异常。", warm, "mx-xxx");
                MailInfo minfo = new MailInfo(new Date(), list.get(0).getDescribe(), list.get(0).getMailto(), "-1", list.get(0).getSubject());
                JDBCUtil.toMailInfo(minfo);

            }

        } catch (SQLException e) {
            logger.info(e.toString());
            logger.error(e.toString());
            MailInfo minfo = new MailInfo(new Date(), list.get(0).getDescribe(), list.get(0).getMailto(), "-1", list.get(0).getSubject());
            JDBCUtil.toMailInfo(minfo);
        }
    }
}
