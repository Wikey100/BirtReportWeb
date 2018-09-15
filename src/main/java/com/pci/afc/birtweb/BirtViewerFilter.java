package com.pci.afc.birtweb;

import org.eclipse.birt.report.filter.ViewerFilter;

import javax.servlet.annotation.WebFilter;

/**
 * Created by xwj on 2018-08-12.
 */
@WebFilter(
    filterName = "ViewerFilter",
    servletNames = {"ViewerServlet", "EngineServlet"}
)
public class BirtViewerFilter extends ViewerFilter {

}
