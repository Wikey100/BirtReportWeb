package com.pci.afc.config;

import com.pci.afc.domain.User;
import com.pci.afc.service.UserService;
import com.pci.afc.service.impl.PrivilegeServiceImpl;
import com.sun.corba.se.spi.orbutil.threadpool.NoSuchThreadPoolException;
import net.minidev.json.writer.ArraysMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.poi.poifs.nio.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xwj on 2018-08-12.
 */
@Configuration
@EnableGlobalMethodSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/plugin/**").permitAll()
            .antMatchers("/webcontent/birt/**").permitAll()
            .antMatchers("/**").authenticated();
        http.headers().frameOptions().disable();
        http.formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login?error");
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout");
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        //基于内存验证用户
        ArrayList<User> userList= PrivilegeServiceImpl.getInstance().getAllUser();
        for(User userInfo:userList){
            auth.inMemoryAuthentication().withUser(userInfo.getUsername()).password("123456").roles("ADMIN");
            //auth.inMemoryAuthentication().withUser(userInfo.getUsername()).password(userInfo.getPassword()).roles("ADMIN");
        }
        auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return DigestUtils.md5Hex(rawPassword.toString());
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(encode(rawPassword));
            }
        });

        //基于数据库验证 TODO:未实现
        //auth.userDetailsService(new CustomUserService()); //user Detail Service验证

    }
}