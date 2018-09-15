package com.pci.afc.dao;

import java.sql.Connection;
import com.pci.afc.config.ConfigUtil;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.sql.DriverManager;

/**
 * Created by xwj on 2018-08-12.
 * 添加jdbc数据库操作处理类，用以实现报表系统报表模板权限控制
 */
public class PrivilegeReportDao {

    private static final String driverClassName=new ConfigUtil().getConfig("spring.datasource.driverClassName");
    private static final String URL=new ConfigUtil().getConfig("spring.datasource.url");
    private static final String NAME=new ConfigUtil().getConfig("spring.datasource.username");
    private static final String PASSWORD=new ConfigUtil().getConfig("spring.datasource.password");
    // 数据库连接对象
    private Connection con = null;
    // 预编译语句对象
    private PreparedStatement psmt = null;
    // 结果集对象
    private ResultSet result = null;

    public  PrivilegeReportDao() throws SQLException, ClassNotFoundException {
        init();
    }

    //初始化数据库连接信息
    private  void  init() throws ClassNotFoundException, SQLException {
        Class.forName(driverClassName);
        con = DriverManager.getConnection(URL, NAME, PASSWORD);// 获取连接
    }

    //根据用户编号，获取对权限所能管理的报表列表
    public ResultSet query(String userId) throws SQLException {
        String sql= String.format("select distinct c.functionname from PRM5021_SPECSTATIONINFO a,prm5021_accountinfo b,PRM5021_SPECFUNCTION c where a.userid=b.userid and c.functionid in (select d.functionid from PRM5021_SPECGROUPRIGHT d where d.groupid=a.groupid) and b.userid=? and c.speciesid='11'");
        psmt = con.prepareStatement(sql.toString()); // 预编译SQL
        psmt.setString(1, userId); // 设置参数
        result = psmt.executeQuery(); // 执行SQL
        return result;
    }

    //查询所有用户信息
    public ResultSet queryAllUser() throws SQLException {
        String sql= String.format("select t.userid,t.passwd from PRM5021_ACCOUNTINFO t");
        psmt = con.prepareStatement(sql.toString()); // 预编译SQL
        result = psmt.executeQuery(); // 执行SQL
        return result;
    }

    //根据登录用户编号获取用户名
    public  ResultSet getUserNameByUserId(String userId) throws SQLException{
        String sql=String.format("select t.username from PRM5021_ACCOUNTINFO t where t.userid=?");
        psmt = con.prepareStatement(sql.toString()); // 预编译SQL
        psmt.setString(1, userId); // 设置参数
        result = psmt.executeQuery(); // 执行SQL
        return result;
    }

    //根据登录用户获取当前用户信息
    public  ResultSet getUserInfoByUserId(String userId) throws SQLException {
        String sql=String.format("select t.userid,t.username,t.passwd,t.cardid from PRM5021_ACCOUNTINFO t where t.userid=? ");
        psmt=con.prepareStatement(sql.toString()); //预编译SQL
        psmt.setString(1,userId);
        result=psmt.executeQuery(); //执行SQL
        return  result;
    }

    //根据用户编号获取权限列表
    public  ResultSet getPrivilegeListByUserId(String userId){
//        String sql=String.format("select ");
        return  null;
    }

    //关闭连接
    public void closeConnection() throws SQLException {
        if (null != result) {
            result.close();
        }
        if (null != psmt) {
            psmt.close();
        }
        if (null != con) {
            con.close();
        }
    }
}
