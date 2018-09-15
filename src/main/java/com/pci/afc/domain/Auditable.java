package com.pci.afc.domain;

import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.io.*;
import java.util.*;

/**
 * Created by xwj on 2018-08-12.
 */
@MappedSuperclass
public class Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    @Column(name = "version")
    private long version;
    @Column(name = "created_by", length = 64)
    private String createUser;
    @Column(name = "created_time")
    private Date createTime;
    @Column(name = "lastupdated_by", length = 64)
    private String lastUpdateUser;
    @Column(name = "lastupdated_time")
    private Date lastUpdateTime;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @PrePersist
    public void prePersist() {
        Date currentTime = new Date();
        this.setCreateTime(currentTime);
        this.setCreateUser(getAuthenticationName());
        this.setLastUpdateTime(currentTime);
        this.setLastUpdateUser(getAuthenticationName());
    }

    @PreUpdate
    public void preUpdate() {
        this.setLastUpdateTime(new Date());
        this.setLastUpdateUser(getAuthenticationName());
    }

    private String getAuthenticationName() {
        String username;
        try {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {
            username = "unknown";
        }
        return username;
    }
}