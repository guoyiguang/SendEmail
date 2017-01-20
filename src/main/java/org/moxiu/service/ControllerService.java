package org.moxiu.service;



import org.moxiu.bean.ReportEmail;

import java.util.List;

public interface ControllerService {
	//重新发送邮件
	boolean reSender(String subject, String state);
	//修改邮件发送----注意rank 原来的排序位置
	boolean updateSender(String json, String rank);
	//打开/关闭邮件发送---------通过在state字段前面添加-来表示是否发送
	boolean closeSender(String subject, String state);
	//根据主题查询该邮件下的所有信息
	List<ReportEmail> getSender(String subject);
	
	
	//配置测试邮件
	boolean testConfig(String json);
	//测试邮件发送
	boolean testSender(String subject);
	//把测试邮件配置移动到正式发送环境
	boolean toSender(String subject);

}
