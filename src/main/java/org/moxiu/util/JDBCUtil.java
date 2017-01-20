package org.moxiu.util;

import org.moxiu.bean.MailInfo;
import org.moxiu.bean.ReportEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


/**
 * Created by MX-092 on 2017/1/17.
 */
public class JDBCUtil {

    private static final Logger logger= LoggerFactory.getLogger(JDBCUtil.class);
    public static Connection getCon(){
        logger.info("连接数据库：[user.dir"+System.getProperty("user.dir")+"] [user.home:"+System.getProperty("user.home")+"]");
        Connection con =null;
        try{
            con = DriverManager.getConnection("jdbc:mysql://10.1.0.20:3306/mcmp_support?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                    "admin_mx", "F]D3+XIi50C}qpdD");
        }catch(SQLException e) {
            logger.error(e.toString());
        }
        return con;
    }
    public static Connection getCon(String ip,String port,String db_name,String user,String pwd)
    {
        Connection conn =null;
        logger.info("ip:["+ip+"]");
        try{
            conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":"
                            + port + "/" + db_name
                            + "?useUnicode=true&characterEncoding=utf-8&useSSL=false",
                    user, pwd);
        }catch(SQLException e){
            logger.error(e.toString());
        }
        return conn;
    }
    static{
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e)
        {
            logger.error(e.toString());
        }
    }
    public static int updateReportEmailRand(String sql,String merge,String subject,String rank){
        int i=-1;
        Connection conn=null;
        PreparedStatement ps = null;
        conn = JDBCUtil.getCon();
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1,merge);
            ps.setString(2,subject);
            ps.setString(3,rank);
            i=ps.executeUpdate();

        }catch (SQLException e)
        {
            logger.error(e.toString());
        }
        return i;
    }
    // 更新数据如ReportEmail
    public static int toReportEmail(ReportEmail re){
        Connection conn =null;
        PreparedStatement ps =null;
        int rs = -1;
        String sql = "update mcmp_support.support_mail_copy set"
                +"describe=?"
                +"date=?"
                +"ip=?"
                +"port=?"
                +"db_name=?"
                +"pswd=?"
                +"mailto=?"
                +"copyto=?"
                +"warming=?"
                +"fields=?"
                +"sqlstatement=?"
                + "buttom = ?"
                + "sender = ?"
                + "state = ?"
                + "merge = ?"
                + "regularly = ?"
                + " where id =?";
        conn = JDBCUtil.getCon();
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1, re.getDescribe());
            ps.setDate(2, new java.sql.Date(re.getDate().getTime()));
            ps.setString(3, re.getIp());
            ps.setString(4, re.getPort());
            ps.setString(5, re.getDb_name());
            ps.setString(6, re.getUser());
            ps.setString(7, re.getPswd());
            ps.setString(8, re.getMailto());
            ps.setString(9, re.getCopyto());
            ps.setString(10, re.getWarming());
            ps.setString(11, re.getFields());
            ps.setString(12, re.getSqlstatement());
            ps.setString(13, re.getButtom());
            ps.setString(14, re.getSender());
            ps.setString(15, re.getState());
            ps.setString(16, re.getMerge());
            ps.setString(17, re.getRegularly());
            ps.setInt(18, re.getId());

            rs = ps.executeUpdate();
        }catch(SQLException e){
            logger.error(e.toString());
            logger.info(e.toString());
        }finally {
            close(null,ps,conn);
        }
        return rs;
    }
    // 结果数据入到mailinfo
    public static void toMailInfo(MailInfo minfo){
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "inserto into mcmp_support.support_mail_history(date,subject,context,state,description) values(?,?,?,?,?)";
        conn = JDBCUtil.getCon();
        try{
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1,new java.sql.Timestamp(minfo.getDate().getTime()));
            ps.setString(2, minfo.getSubject());
            ps.setString(3, minfo.getContext());
            ps.setString(4, minfo.getState());
            ps.setString(5, minfo.getDescirbe());
            ps.executeUpdate();
        } catch(SQLException e){
            logger.error(e.toString());
            logger.info(e.toString());
        }finally{
            close(null,ps,conn);
        }
    }
    // 结果数据入mailinfo
    public static int senderState(String sql,String subject,String state){
        Connection conn = null;
        PreparedStatement ps = null;
        int rs = -1;
        conn = JDBCUtil.getCon();
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, subject);
            ps.setString(2, state);
            rs = ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e.toString());
            logger.info(e.toString());
        }finally {
            close(null,ps,conn);
        }
        return rs;
    }
    // 获取所有定时邮件发送信息
    public static List<ReportEmail> getReportEmail(String sql){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ReportEmail> relist = new ArrayList();
        ReportEmail re = null;
        logger.info("查询配置表数据 ["+sql+"]");
        try{
            conn = JDBCUtil.getCon();
            ps=conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                re = new ReportEmail();

                re.setId(rs.getInt("id"));
                re.setDescribe(rs.getString("describe"));
                re.setDate(rs.getDate("date"));
                re.setIp(rs.getString("ip"));
                re.setPort(rs.getString("port"));
                re.setDb_name(rs.getString("db_name"));
                re.setUser(rs.getString("user"));
                re.setPswd(rs.getString("pswd"));
                re.setSubject(rs.getString("subject"));
                re.setMailto(rs.getString("mailto"));
                re.setCopyto(rs.getString("copyto"));
                re.setWarming(rs.getString("warming"));
                re.setFields(rs.getString("fields"));
                re.setSqlstatement(rs.getString("sqlstatement"));
                re.setButtom(rs.getString("buttom"));
                re.setSender(rs.getString("sender"));
                re.setMerge(rs.getString("merge"));
                re.setRegularly(rs.getString("regularly"));
                re.setState(rs.getString("state"));
//					re.setTiming(rs.getString("timing"));
                relist.add(re);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(rs,ps,conn);
        }
        String[] date = { "Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday" };
        for (Iterator<ReportEmail> it = relist.iterator(); it.hasNext();) {
            ReportEmail res = it.next();
            if (res.getRegularly() != null
                    && !date[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1]
                    .equals(res.getRegularly())) {
                it.remove();
            }
        }
        return relist;
    }
    public static List<ReportEmail> getEmail(String sql,String subject) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ReportEmail> relist = new ArrayList<ReportEmail>();
        ReportEmail re = null;
        logger.info("查询配置表数据 [" + sql + "]");
        try {
            conn = JDBCUtil.getCon();
            ps = conn.prepareStatement(sql);
            ps.setString(1, subject);
            rs = ps.executeQuery();
            while (rs.next()) {
                re = new ReportEmail();

                re.setId(rs.getInt("id"));
                re.setDescribe(rs.getString("describe"));
                re.setDate(rs.getDate("date"));
                re.setIp(rs.getString("ip"));
                re.setPort(rs.getString("port"));
                re.setDb_name(rs.getString("db_name"));
                re.setUser(rs.getString("user"));
                re.setPswd(rs.getString("pswd"));
                re.setSubject(rs.getString("subject"));
                re.setMailto(rs.getString("mailto"));
                re.setCopyto(rs.getString("copyto"));
                re.setWarming(rs.getString("warming"));
                re.setFields(rs.getString("fields"));
                re.setSqlstatement(rs.getString("sqlstatement"));
                re.setButtom(rs.getString("buttom"));
                re.setSender(rs.getString("sender"));
                re.setMerge(rs.getString("merge"));
                re.setRegularly(rs.getString("regularly"));
                re.setState(rs.getString("state"));
//					re.setTiming(rs.getString("timing"));
                relist.add(re);
            }
        } catch (SQLException e) {
            logger.error(e.toString());
        } finally {
            close(rs,ps,conn);
        }
        return relist;
    }
    public static void close(ResultSet rs,PreparedStatement ps,Connection conn) {
        try {
            if (null != rs)
                rs.close();
        } catch (SQLException e) {
            logger.info(e.toString());
        } finally {
            rs = null;
        }
        try {
            if (null != ps)
                ps.close();
        } catch (SQLException e) {
            logger.info(e.toString());
        } finally {
            ps = null;
        }
        try {
            if (null != conn)
                conn.close();
        } catch (SQLException e) {
            logger.info(e.toString());
        } finally {
            conn = null;
        }
    }


}