package com.pci.afc.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pci.afc.config.GlobalConfig;
import com.pci.afc.service.RoleService;
import com.pci.afc.service.UserService;
import com.pci.afc.service.impl.PrivilegeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.mockito.internal.configuration.GlobalConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.print.DocFlavor;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by xwj on 2018-08-12.
 */
@RestController
@RequestMapping
public class IndexController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${info.project.version}")
    private String version;

    @Value("#{${map.report_name}}")
    private Map<String, String> reportNameMap;

    private  ArrayList<String> reportList=Lists.newArrayList();

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping
    public ModelAndView main(HttpServletRequest request) throws Exception {

        //获取登录用户名
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String userId=userDetails.getUsername();
        logger.info("UserDetails里获取到的用户userId："+userId);
        String userName=PrivilegeServiceImpl.getInstance().getUserNameById(userId);
        logger.info("根据userId获取到的用户名："+userName);

        //根据登录用户ID初始化所属报表列表
        reportList= PrivilegeServiceImpl.getInstance().getPrivilegeReportList(userId);
       for (String reportName:reportList){
           logger.info("根据用户所属角色获取到的报表名称："+reportName);
       }

        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("main");

        List<Map<String, String>> statisticsReportList=getBirtReportNameList(request,"STATISTICSREPORT_WORKING_FOLDER");
        modelAndView.addObject("statisticsReportList",statisticsReportList); //添加统计类报表

        List<Map<String, String>> stockMReportList=getBirtReportNameList(request,"STOCKMANAGEMENTREPORT_WORKING_FOLDER");
        modelAndView.addObject("stockMReportList",stockMReportList); //添加库存管理类报表

        List<Map<String, String>> profitReportList=getBirtReportNameList(request,"PROFITREPORT_WORKING_FOLDER");
        modelAndView.addObject("profitReportList",profitReportList); //添加收益类报表

        List<Map<String, String>> deviceClassReportList=getBirtReportNameList(request,"DEVICECLASSIFICATIONREPORT_WORKING_FOLDER");
        modelAndView.addObject("deviceClassReportList",deviceClassReportList); //添报表加设备分类统计报表

        List<Map<String, String>> schedulingReportList=getBirtReportNameList(request,"SCHEDULINGREPORT_WORKING_FOLDER");
        modelAndView.addObject("schedulingReportList",schedulingReportList); //添加调度类报表

        List<Map<String, String>> financeReportList=getBirtReportNameList(request,"FINANCEREPORT_WORKING_FOLDER");
        modelAndView.addObject("financeReportList",financeReportList); //添加财务类报表

        //添加登录用户信息到modelAndView中,供页面显示和报表制表人信息显示
        modelAndView.addObject("userId","990001");//userId  用户编号，用户查询显示制表人
        modelAndView.addObject("username",userName); //用户名，用以显示登录用户
        return modelAndView;
    }

    public List<Map<String, String>> getBirtReportNameList(HttpServletRequest request,String reportFolderNameStr){
        ServletContext context = request.getSession().getServletContext();
        String reportFolder = context.getInitParameter(reportFolderNameStr);
        logger.info("报表路径：{}", reportFolder);

        List<Map<String, String>> rptNameList = Lists.newArrayList();
        if (StringUtils.isNotBlank(reportFolder)) {
            File file = new File(reportFolder);
            String[] rptNames = file.list((dir, name) -> name.endsWith(".rptdesign"));

            if (rptNames != null) {
                Arrays.stream(rptNames)
                        .map(fileName -> fileName.substring(0, fileName.indexOf(".")))
                        .forEach(name -> {
                            //String chineseName = reportNameMap.get(name);
                            if ( name.indexOf("【")!=-1) {
                                String chineseName = name.substring(name.indexOf("【") + 1, name.lastIndexOf("】"));
                                String enName = name.substring(0, name.indexOf("【"));
//                                if (reportList.contains(chineseName)) {  //根据权限报表列表过滤显示当前用户可查看的报表
                                    Map<String, String> rptNameMap = Maps.newHashMap();
                                    rptNameMap.put("key", enName);
                                    rptNameMap.put("value", chineseName == null ? enName : chineseName);
                                    logger.info("报表：{}", enName);
                                    rptNameList.add(rptNameMap);
//                                }
                            }
                        });
            }
        }
        return  rptNameList;
    }


    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request) {
        Map<String, String> result = Maps.newHashMap();
        result.put("version", StringUtils.join("V", version));
        return new ModelAndView("login", result);
    }

    public static boolean isRelativePath(String fileName) {
        return fileName != null && !new File(fileName).isAbsolute();
    }

}