package com.pci.afc.service;

import com.pci.afc.criteria.AuthorityCriteria;
import com.pci.afc.domain.Authority;
import com.pci.afc.repository.AuthorityRepository;
import com.pci.afc.repository.JpaRepository;
import org.springframework.data.domain.Page;

public interface AuthorityService extends JpaRepository<AuthorityRepository> {

    Page<Authority> query(AuthorityCriteria criteria);
}