package com.pci.afc;

import com.google.common.collect.Lists;
import com.pci.afc.domain.Authority;
import com.pci.afc.domain.Role;
import com.pci.afc.domain.User;
import com.pci.afc.service.RoleService;
import com.pci.afc.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Created by xwj on
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    public void test(){
        Role role = new Role();
        role.setName("ADMIN");
        role.setDescription("超级管理员");

        Authority authority = new Authority();
        authority.setName("超级权限");
        authority.setCode("0001");
        authority.setDescription("超级权限");

        role.setAuthorities(Lists.newArrayList(authority));

        User user = new User();
        user.setLoginName("admin");
        user.setDisplayName("管理员");
        user.setPassword(DigestUtils.md5Hex("admin"));
        user.setEmail("admin@test.cn");
        user.setDescription("管理员");
        user.setRole(role);

        roleService.getRepository().save(role);
        userService.getRepository().save(user);
    }

    @Test
    public void testTree(){
        SortedSet<String> set = new TreeSet<>();
        set.add("0002");
        set.add("0001");
        set.add("0003");
        set.add("0008");
        set.add("0005");
        set.add("0006");

        String first = set.first();
        System.out.println(first);

        SortedSet<String> strings = set.headSet("0004");
        String last = strings.last();
        System.out.println(last);

    }
}
