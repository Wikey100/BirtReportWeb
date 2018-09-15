package com.pci.afc.criteria;

/**
 * Created by xwj on 2018-08-12.
 */
public class UserCriteria extends Criteria {

    private String loginName;
    private String displayName;
    private String email;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
