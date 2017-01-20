package org.moxiu.service.impl;


import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.moxiu.bean.ReportEmail;
import org.moxiu.sender.Monitor;
import org.moxiu.service.ControllerService;
import org.moxiu.util.JDBCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ControllerServiceImpl implements ControllerService {
	private static final Logger logger = LoggerFactory.getLogger(ControllerServiceImpl.class);
	private static ObjectMapper mapper = new ObjectMapper();
	private static String sender = "select * from mcmp_support.support_mail_gyg where subject =?";
	@Override
	public boolean reSender(String subject,String state) {
		// TODO Auto-generated method stub
		logger.info("reSender:"+subject);
		try {
			List<ReportEmail> relist1 = JDBCUtil
					.getEmail(sender,subject);
			if(null!= relist1 && 0<relist1.size()){
				//相同邮件合并
				Map<String,List<ReportEmail>> map = Monitor.mergerReportMail(relist1);
				if(null!=relist1&&0<relist1.size()){
					//目前都是为0
					if(state.equals(relist1.get(0).getState())){
						Monitor.sendMail(map);
					}
				}
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
			return false;
		}
	}
	@Override
	public List<ReportEmail> getSender(String subject) {
		// TODO Auto-generated method stub
		try {
			List<ReportEmail> relist1 = JDBCUtil.getEmail(sender,subject);
			return relist1;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
			return null;
		}
		
	}

	@Override
	public boolean updateSender(String json,String rank) {
		// TODO Auto-generated method stub
		try {
//			根据id修改邮件内容
//			如果rank不为空，修改rank的时候需要注意 eg: rank为修改前的3 现在修改为2   那么对应之前的rank为2的就要修改成3
			if(null!=json && 0<json.length()){
				JsonNode  content = mapper.readTree(json);
				ReportEmail re = new ReportEmail(content);
				if(null!=rank && 0<rank.length()){//如果rank不为空，修改rank的时候需要注意 eg: rank为修改前的3 现在修改为2   那么对应之前的rank为2的就要修改成3
					String updateRank = "update mcmp_support.support_mail_copy set merge = ? where subject =? and merge =?";
					JDBCUtil.updateReportEmailRand(updateRank, re.getMerge(), re.getSubject(), rank);
				}
				if(JDBCUtil.toReportEmail(re)>0){
					return true;
				}
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
			return false;
		}
		return false;
	}

	
	@Override
	public boolean closeSender(String subject,String state) {
//		TODO 1 查看数据库中邮件状态
//		TODO 2 如果处于停发状态，再次停发不用更改数据库
		String sql = "update mcmp_support.support_mail_copy set state = ? where subject =?";
		List<ReportEmail> relist1 = JDBCUtil.getReportEmail(sender);
		int i = -1;
		try {
			if(null!=relist1 && 0<relist1.size()){
				for(ReportEmail re:relist1){
					if("0".equals(state)){//准备打开
						if(null!=re.getState() && re.getState().startsWith("-")){//判断是否处于关闭转台
							//update state = state.substring(1,state.length())
							i = JDBCUtil.senderState(sql,subject,re.getState().substring(1,re.getState().length()));
							break;
						}
					}else{//准备关闭
						if(null!=re.getState() && (!re.getState().startsWith("-"))){
							//update state = "-"+state
							i = JDBCUtil.senderState(sql,subject,"-"+state);
							break;
						}
					}
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.toString());
			return false;
		}
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean testConfig(String json) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean testSender(String subject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toSender(String subject) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
