package com.pci.afc.service.impl;

import com.google.common.collect.Lists;
import com.pci.afc.dao.PrivilegeReportDao;
import com.pci.afc.domain.ReportUserModel;
import com.pci.afc.domain.User;
import oracle.jdbc.OracleConnection;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.jni.Global;
import org.aspectj.weaver.ast.Var;
import org.eclipse.birt.chart.model.data.DataSet;
import org.eclipse.birt.report.engine.api.impl.ReportRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.jsp.tagext.TryCatchFinally;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by xwj on 2018-08-12.
 */
public class PrivilegeServiceImpl {
    public  PrivilegeServiceImpl(){

    }
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private  static  PrivilegeServiceImpl instance;

    public static PrivilegeServiceImpl getInstance() {
        if (instance==null){
            instance=new PrivilegeServiceImpl();
        }
        return instance;
    }

    //根据用户编号获取对应权限所属报表列表数据
    public ArrayList<String> getPrivilegeReportList(String userId) throws Exception {
        ArrayList<String> reportList= Lists.newArrayList();
        try {
            ResultSet rs=new PrivilegeReportDao().query(userId);
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String reportName=rs.getObject(i).toString();
                    log.info("对应的报表名称:"+reportName);
                    reportList.add(reportName);
                }
            }
            log.info("报表数量为:"+reportList.size());
        }
        catch (Exception ex)
        {
            //throw  new Exception("根据用户编号获取对应权限所属报表列表出现未知异常:"+ex.getMessage());
        }
        return  reportList;
    }

    //获取所有用户列表
    public  ArrayList<User> getAllUser() throws Exception {
        ArrayList<User> userList= Lists.newArrayList();
        try {
            ResultSet rs=new PrivilegeReportDao().queryAllUser();
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    User userInfo=new User();
                    userInfo.setLoginName(rs.getString(1));
                    userInfo.setPassword(rs.getString(2));
                    log.info("从数据据库中获取到用户名:"+rs.getString(1).toString());
                    log.info("从数据据库中获取到密码:"+rs.getString(2).toString());
                    userList.add(userInfo);
                }
            }
            log.info("用户数量为:"+userList.size());
        }
        catch (Exception ex)
        {
            //throw  new Exception("获取所有用户信息列表出现未知异常:"+ex.getMessage());
        }
        return  userList;
    }

    //根据用户编号获取用户名
    public String getUserNameById(String userId)throws SQLException, ClassNotFoundException, UnsupportedEncodingException{
        String userName="";
        try {
            log.info("根据用户编号获取用户名"+userId);
            ResultSet rs=new PrivilegeReportDao().getUserNameByUserId(userId);
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                userName=rs.getString(1);
            }
        }
        catch (Exception ex){
            log.error("根据用户编号获取用户名出现未知异常:"+ex.getMessage());
        }
        return userName;
    }

    //根据用户编号获取用户所有信息
    public ReportUserModel getUserInfoByUserId(String userId) throws Exception {
        ReportUserModel userModel=new ReportUserModel();
        try {
            ResultSet rs=new PrivilegeReportDao().getUserInfoByUserId(userId);
            ResultSetMetaData md=rs.getMetaData(); //获得结果集结构信息，元数据
            int columnCount=md.getColumnCount();
            while (rs.next()){
                userModel.setUserID(rs.getString(1));
                userModel.setUserName(rs.getString(2));
                userModel.setPassWord(rs.getString(3));
                userModel.setCardId(rs.getString(4));
                log.info("获取用户信息");
            }
        }
        catch (Exception ex){
            log.error(String.format("获取用户{0}信息出现未知异常:{1}",userId,ex.getMessage()));
            //throw  new Exception(ex.getMessage());
        }
        return  userModel;
    }

    //根据用户编号获取权限列表数据
    public  ArrayList<String> getPrivilegeListByUserId(String userId) throws Exception {
        ArrayList<String> privilegeList= Lists.newArrayList();
        try {
            ResultSet rs=new PrivilegeReportDao().getPrivilegeListByUserId(userId);
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String reportName=rs.getObject(i).toString();
                    log.info("对应的权限名称:"+reportName);
                    privilegeList.add(reportName);
                }
            }
            log.info("权限列表数量为:"+privilegeList.size());
        }
        catch (Exception ex){
            //  new Exception("根据用户编号获取权限列表数据出现未知异常:"+ex.getMessage());
        }
        return privilegeList;
    }
}
