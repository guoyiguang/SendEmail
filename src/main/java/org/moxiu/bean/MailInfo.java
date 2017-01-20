package org.moxiu.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MX-092 on 2017/1/17.
 */
public class MailInfo implements Serializable {

    private static final long serialVersionUID=1L;
    private Integer id; //数组库字段id
    private Date date; //发送时间
    private String subject; //邮件主题
    private String context; //邮件内容
    private String mailto;  //邮件收件人
    private String descirbe; //邮件负责人
    private String state; //邮件发送状态
    // 无参构造函数
    public MailInfo()
    { }
    // 有参构造函数==》无context
    public MailInfo(Date date,String descirbe,String mailto,String state,String subject)
    {
        super();
        this.date=date;
        this.subject=subject;
        this.mailto=mailto;
        this.descirbe=descirbe;
        this.state=state;
    }
    //有参构造函数==》有context
    public MailInfo(Date date,String descirbe,String mailto,String state,String subject,String context){
        super();
        this.date=date;
        this.subject=subject;
        this.mailto=mailto;
        this.descirbe=descirbe;
        this.state=state;
        this.context=context;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setMailto(String mailto) {
        this.mailto = mailto;
    }

    public void setDescirbe(String descirbe) {
        this.descirbe = descirbe;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMailto() {

        return mailto;
    }

    public Integer getId() {

        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getContext() {
        return context;
    }

    public String getDescirbe() {
        return descirbe;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString(){
        return "MailInfo [id=" + id + ", date=" + date + ", subject=" + subject
                + ", context=" + context + ", mailto=" + mailto + ", descirbe="
                + descirbe + ", state=" + state + "]";
    }


}
