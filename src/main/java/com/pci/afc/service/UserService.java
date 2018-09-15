package com.pci.afc.service;

import com.pci.afc.criteria.UserCriteria;
import com.pci.afc.domain.User;
import com.pci.afc.repository.JpaRepository;
import com.pci.afc.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends JpaRepository<UserRepository>, UserDetailsService {

    Page<User> query(UserCriteria criteria);
}