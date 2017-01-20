package org.moxiu.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.moxiu.bean.ErrorInfo;
import org.moxiu.bean.Info;
import org.moxiu.sender.Monitor;
import org.moxiu.service.ControllerService;
import org.moxiu.service.impl.ControllerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by MX-092 on 2017/1/19.
 */
@RestController
@EnableAutoConfiguration
@SpringBootApplication

public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private static ControllerService controllerService = new ControllerServiceImpl();

    public static void main(String[] args){
        logger.info("实时邮件发送程序启动");
        SpringApplication.run(Controller.class,args);
        Monitor.monitor();
    }
    /**
     * 根据主题名称发送邮件，手动发送
     */
    @RequestMapping(value="/resender",method= RequestMethod.POST)
    @ResponseBody
    public Object reSender(@RequestParam(value = "subject", defaultValue = "") String subject,@RequestParam(value = "state", defaultValue = "") String state){
        String result = "";
        try {
            if(null!=subject  && 0<subject.length()){
                Info info = new Info();
                if(controllerService.reSender(subject,state)){
                    info.setDescribe("重新发送成功，请注意查收！");
                    result = mapper.writeValueAsString(info);
                }else{
                    info.setCode("-200");
                    info.setState("-1");
                    info.setDescribe("重新发送失败，请及时联系管理员！");
                    result = mapper.writeValueAsString(info);
                }

            }else{
                ErrorInfo ei = new ErrorInfo("邮件主题不能为空！");
                result = mapper.writeValueAsString(ei);
            }
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        }
        return result;
    }


    /**
     * 关闭/打开邮件发送
     * @param 　subject主题名称
     * @param state	0为打开 非0为关闭
     * @return
     */
    @RequestMapping(value = "/closeSender", method = RequestMethod.POST)
    @ResponseBody
    public Object closeSender(@RequestParam(value = "subject", defaultValue = "") String subject,@RequestParam(value = "state", defaultValue = "0") String state){
        String result = "";
        try {
            if(null!=subject  && 0<subject.length()){
                result = mapper.writeValueAsString(controllerService.closeSender(subject,state));
            }else{
                ErrorInfo ei = new ErrorInfo("邮件主题不能为空！");
                result = mapper.writeValueAsString(ei);
            }
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        }
        return result;
    }
    /**
     * 修改邮件发送			邮件主题不能修改   只能删除重建
     * @param 　subject主题名称
     * @return
     */
    @RequestMapping(value = "/updateSender", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSender(@RequestParam(value = "json", defaultValue = "") String json,@RequestParam(value = "rank", defaultValue = "") String rank){
        String result = "";
        try {
            if(null!=json  && 0<json.length()){
                result = mapper.writeValueAsString(controllerService.updateSender(json, rank));
            }else{
                ErrorInfo ei = new ErrorInfo("邮件主题不能为空！");
                result = mapper.writeValueAsString(ei);
            }
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        }
        return result;

    }
    /**
     * 获取邮件
     * @param 　subject主题名称
     * @return
     */
    @RequestMapping(value = "/getSender", method = RequestMethod.POST)
    @ResponseBody
    public Object getSender(@RequestParam(value = "subject", defaultValue = "") String subject){
        String result = "";
        try {
            if(null!=subject  && 0<subject.length()){
                result = mapper.writeValueAsString(controllerService.getSender(subject));
            }else{
                ErrorInfo ei = new ErrorInfo("邮件主题不能为空！");
                result = mapper.writeValueAsString(ei);
            }
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.info(e.toString());
        }
        return result;
    }










    /**
     * 配置测试邮件信息
     * @param 　json配置信息
     * @return
     */
    @RequestMapping(value = "/testConfig", method = RequestMethod.POST)
    @ResponseBody
    public Object testConfig(@RequestParam(value = "json", defaultValue = "") String json){

        return "测试发送";
    }

    /**
     * 根据主题名称发送邮件，测试发送邮件
     * @param 　subject主题名称
     * @return
     */
    @RequestMapping(value = "/testsender", method = RequestMethod.POST)
    @ResponseBody
    public Object testSender(@RequestParam(value = "subject", defaultValue = "") String subject){

        return "测试发送";
    }

    /**
     * 根据主题名称，把测试邮件加入定时发送邮件部分
     * @param 　subject主题名称
     * @return
     */
    @RequestMapping(value = "/tosender", method = RequestMethod.POST)
    @ResponseBody
    public Object toSender(@RequestParam(value = "subject", defaultValue = "") String subject){

        return "加入定时发送";
    }

}
