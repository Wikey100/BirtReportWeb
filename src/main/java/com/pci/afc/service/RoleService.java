package com.pci.afc.service;

import com.pci.afc.criteria.RoleCriteria;
import com.pci.afc.domain.Role;
import com.pci.afc.repository.JpaRepository;
import com.pci.afc.repository.RoleRepository;
import org.springframework.data.domain.Page;

public interface RoleService extends JpaRepository<RoleRepository> {

    Page<Role> query(RoleCriteria criteria);
}