package org.moxiu.bean;

import org.codehaus.jackson.JsonNode;
import org.moxiu.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MX-092 on 2017/1/17.
 */
public class ReportEmail implements Serializable {
    private static final long serivalVersionUID = 1L;
    private int id;
    private String describe; //邮件负责人
    private Date date; //添加邮件日期
    private String ip; //数据库连接信息 ==ip地址
    private String port; //数据库连接信息 ==端口
    private String db_name; //数据库连接信息 ==数据库名
    private String user; //数据库连接信息 ==用户名
    private String pswd; //数据库连接信息 ==密码
    private String subject;  //邮件主题
    private String mailto;  //收件人
    private String copyto; //抄送人
    private String warming; // 报警收件人

    private String fields; //对应表格字段名称
    private String sqlstatement; // 对应表格数据生成的sql语句
    private String buttom; //邮件底部内容

    private String timing; //定时发送
    private String sender; //发件人
    private String state; //状态 1 -1为取消发送
    private String merge; //合并
    private String regularly;  //规律


    public ReportEmail() {
        // TODO Auto-generated constructor stub
    }

    public ReportEmail(JsonNode node) {
        if(null!=node){
            JsonNode dateNode = node.get("date");
            if(dateNode != null){
                this.date = DateUtil.stringToDate(dateNode.getValueAsText(), "yyyy-MM-dd HH:mm:ss");
            }
            JsonNode regularlyNode = node.get("regularly");
            if(regularlyNode != null){
                this.regularly = regularlyNode.getValueAsText();
            }
            JsonNode mergeNode = node.get("merge");
            if(mergeNode != null){
                this.merge = mergeNode.getValueAsText();
            }
            JsonNode describeNode = node.get("describe");
            if(describeNode != null){
                this.describe = describeNode.getValueAsText();
            }
            JsonNode ipNode = node.get("ip");
            if(ipNode != null){
                this.ip = ipNode.getValueAsText();
            }
            JsonNode portNode = node.get("port");
            if(portNode != null){
                this.port = portNode.getValueAsText();
            }
            JsonNode db_nameNode = node.get("db_name");
            if(db_nameNode != null){
                this.db_name = db_nameNode.getValueAsText();
            }
            JsonNode userNode = node.get("user");
            if(userNode != null){
                this.user = userNode.getValueAsText();
            }
            JsonNode pswdNode = node.get("pswd");
            if(pswdNode != null){
                this.pswd = pswdNode.getValueAsText();
            }
            JsonNode subjectNode = node.get("subject");
            if(subjectNode != null){
                this.subject = subjectNode.getValueAsText();
            }
            JsonNode mailtoNode = node.get("mailto");
            if(mailtoNode != null){
                this.mailto = mailtoNode.getValueAsText();
            }
            JsonNode copytoNode = node.get("copyto");
            if(copytoNode != null){
                this.copyto = copytoNode.getValueAsText();
            }
            JsonNode warmingNode = node.get("warming");
            if(warmingNode != null){
                this.warming = warmingNode.getValueAsText();
            }
            JsonNode fieldsNode = node.get("fields");
            if(fieldsNode != null){
                this.fields = fieldsNode.getValueAsText();
            }
            JsonNode sqlstatementNode = node.get("sqlstatement");
            if(sqlstatementNode != null){
                this.sqlstatement = sqlstatementNode.getValueAsText();
            }
            JsonNode buttomNode = node.get("buttom");
            if(buttomNode != null){
                this.buttom = buttomNode.getValueAsText();
            }
            JsonNode senderNode = node.get("sender");
            if(senderNode != null){
                this.sender = senderNode.getValueAsText();
            }
            JsonNode stateNode = node.get("state");
            if(stateNode != null){
                this.state = stateNode.getValueAsText();
            }
            JsonNode idNode = node.get("id");
            if(idNode != null){
                this.id = idNode.getValueAsInt();
            }
        }

    }


    public String getWarming() {
        return warming;
    }

    public void setWarming(String warming) {
        this.warming = warming;
    }

    public String getButtom() {
        return buttom;
    }

    public void setButtom(String buttom) {
        this.buttom = buttom;
    }



    public void setRegularly(String regularly) {
        this.regularly = regularly;
    }

    public String getRegularly() {

        return regularly;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDb_name() {
        return this.db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPswd() {
        return this.pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {

        if (null != subject && 0 < subject.length() && subject.contains("[")
                && subject.contains("]")) {
            String first = subject.substring(0, subject.indexOf("["));
            String end = subject.substring(subject.lastIndexOf("]") + 1);
            String date = "";
            String str = subject.substring(subject.indexOf("[") + 1,
                    subject.lastIndexOf("]"));
            String[] strs = str.split("/");
            for (String s : strs) {
                date += DateUtil.getDate2String(s) + "/";
            }
            date = date.substring(0, date.length() - 1);
            // mobile业务监控([-1/-2])报表之主业务请求情况对比
            subject = first + date + end;
        }
        this.subject = subject;
    }

    public String getMailto() {
        return this.mailto;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }

    public String getCopyto() {
        return this.copyto;
    }

    public void setCopyto(String copyto) {
        this.copyto = copyto;
    }

    public String getFields() {
        return this.fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getSqlstatement() {
        return this.sqlstatement;
    }

    public void setSqlstatement(String sqlstatement) {
        this.sqlstatement = sqlstatement;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getState() {
        return this.state;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMerge() {
        return merge;
    }

    public void setMerge(String merge) {
        this.merge = merge;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "ReportEmail [id=" + id + ", describe=" + describe + ", date="
                + date + ", ip=" + ip + ", port=" + port + ", db_name="
                + db_name + ", user=" + user + ", pswd=" + pswd + ", subject="
                + subject + ", mailto=" + mailto + ", copyto=" + copyto
                + ", warming=" + warming + ", fields=" + fields
                + ", sqlstatement=" + sqlstatement + ", buttom=" + buttom
                + ", timing=" + timing + ", sender=" + sender + ", state="
                + state + ", merge=" + merge + ", regularly=" + regularly + "]";
    }

}
