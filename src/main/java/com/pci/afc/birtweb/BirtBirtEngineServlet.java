package com.pci.afc.birtweb;

import org.eclipse.birt.report.servlet.BirtEngineServlet;

import javax.servlet.annotation.WebServlet;

/**
 * Created by xwj on 2018-08-12.
 */
@WebServlet(
    name = "EngineServlet",
    urlPatterns = {"/preview", "/download", "/parameter", "/document", "/output", "/extract"}
)
public class BirtBirtEngineServlet extends BirtEngineServlet {

    private static final long serialVersionUID = 1L;
}
