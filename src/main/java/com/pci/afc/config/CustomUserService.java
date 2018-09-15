package com.pci.afc.config;

import com.pci.afc.dao.PrivilegeReportDao;
import com.pci.afc.domain.ReportUserModel;
import com.pci.afc.service.impl.PrivilegeServiceImpl;
import org.apache.catalina.startup.RealmRuleSet;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by xwj on 2018-08-12.   用以实现基于数据库实现用户验证
 */
@Service
public class CustomUserService implements UserDetailsService {
    protected final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    //PrivilegeServiceImpl userDao;

    //ReportUserModel reportUser;


    public CustomUserService(){
       // userDao=new PrivilegeServiceImpl();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
//            reportUser=new PrivilegeServiceImpl().getUserInfoByUserId(username);
//                if (reportUser != null) {
//                    List<Permission> permissions = permissionDao.findByAdminUserId(user.getId());
//                    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//                    for (Permission permission : permissions) {
//                        if (permission != null && permission.getName() != null) {
//
//                            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
//                            //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
//                            grantedAuthorities.add(grantedAuthority);
//                        }
//                    }
//                }
//                    return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
