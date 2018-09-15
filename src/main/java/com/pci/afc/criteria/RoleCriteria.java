package com.pci.afc.criteria;

/**
 * Created by xwj on 2018-08-12.
 */
public class RoleCriteria extends Criteria {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
