package com.pci.afc.service.impl;

import com.google.common.collect.Lists;
import com.pci.afc.criteria.RoleCriteria;
import com.pci.afc.domain.Role;
import com.pci.afc.repository.RoleRepository;
import com.pci.afc.repository.impl.JpaRepositoryImpl;
import com.pci.afc.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
@Transactional
public class RoleServiceImpl extends JpaRepositoryImpl<RoleRepository> implements RoleService {

    private static final long serialVersionUID = 1L;

    @Override
    public Page<Role> query(RoleCriteria criteria) {
        Specification<Role> specification = (root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();

            if (StringUtils.isNotBlank(criteria.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + criteria.getName() + "%"));
            }
            if (StringUtils.isNotBlank(criteria.getDescription())) {
                predicates.add(cb.like(root.get("description"), "%" + criteria.getDescription() + "%"));
            }

            if (!predicates.isEmpty()) {
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
            return cb.conjunction();
        };

        PageRequest pageRequest = new PageRequest(criteria.getPageIndex() - 1, criteria.getPageSize());

        return getRepository().findAll(specification, pageRequest);
    }
}