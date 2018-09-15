package com.pci.afc.service.impl;

import com.google.common.collect.Lists;
import com.pci.afc.criteria.AuthorityCriteria;
import com.pci.afc.domain.Authority;
import com.pci.afc.repository.AuthorityRepository;
import com.pci.afc.repository.impl.JpaRepositoryImpl;
import com.pci.afc.service.AuthorityService;
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
public class AuthorityServiceImpl extends JpaRepositoryImpl<AuthorityRepository> implements AuthorityService {

    private static final long serialVersionUID = 1L;

    @Override
    public Page<Authority> query(AuthorityCriteria criteria) {
        Specification<Authority> specification = (root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayList();

            if (StringUtils.isNotBlank(criteria.getCode())) {
                predicates.add(cb.equal(root.get("code"), criteria.getCode()));
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