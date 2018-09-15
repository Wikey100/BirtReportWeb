package com.pci.afc.service.impl;

import com.google.common.collect.Lists;
import com.pci.afc.criteria.UserCriteria;
import com.pci.afc.domain.User;
import com.pci.afc.repository.UserRepository;
import com.pci.afc.repository.impl.JpaRepositoryImpl;
import com.pci.afc.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl extends JpaRepositoryImpl<UserRepository> implements UserService {

    private static final long serialVersionUID = 1L;

    @Override
    public Page<User> query(UserCriteria criteria) {
        Specification<User> specification = (root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();

            predicates.add(cb.notEqual(root.get("loginName"), "admin"));//不显示超级管理员
            if (StringUtils.isNotBlank(criteria.getLoginName())) {
                predicates.add(cb.equal(root.get("loginName"), criteria.getLoginName()));
            }
            if (StringUtils.isNotBlank(criteria.getDisplayName())) {
                predicates.add(cb.like(root.get("displayName"), "%" + criteria.getDisplayName() + "%"));
            }
            if (StringUtils.isNotBlank(criteria.getEmail())) {
                predicates.add(cb.like(root.get("email"), "%" + criteria.getEmail() + "%"));
            }

            if (!predicates.isEmpty()) {
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
            return cb.conjunction();
        };

        PageRequest pageRequest = new PageRequest(criteria.getPageIndex() - 1, criteria.getPageSize());

        return getRepository().findAll(specification, pageRequest);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getRepository().findByLoginName(username);

        if (user == null) {
            throw new UsernameNotFoundException("找不到指定的用户信息!!!");
        }
        return user;
    }
}