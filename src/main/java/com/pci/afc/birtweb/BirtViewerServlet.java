package com.pci.afc.birtweb;

import org.eclipse.birt.report.servlet.ViewerServlet;

import javax.servlet.annotation.WebServlet;

/**
 * Created by xwj on 2018-08-12.
 */
@WebServlet(name = "ViewerServlet", urlPatterns = {"/frameset", "/run"})
public class BirtViewerServlet extends ViewerServlet {

    private static final long serialVersionUID = 1L;
}
