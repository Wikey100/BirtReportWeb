package com.pci.afc.birt;

import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by xwj on 2018-08-12.
 */
public class ExcelSingleFormatBirtView extends AbstractSingleFormatBirtView {

    public ExcelSingleFormatBirtView() {
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    protected final Logger log= LoggerFactory.getLogger(getClass());
    @Override
    protected RenderOption renderReport(Map<String, Object> modelData, HttpServletRequest request, HttpServletResponse response, BirtViewResourcePathCallback resourcePathCallback, Map<String, Object> appContextValuesMap, String reportName, String format, IRenderOption options) throws Throwable {
        EXCELRenderOption rOptions = new EXCELRenderOption(options);
        rOptions.setOutputFormat("xlsx");
        rOptions.setOutputStream(response.getOutputStream());

        String oName = reportName;
        log.info("导出Excel报表名称:"+oName);

        if (oName.toLowerCase().endsWith(".rptdesign")) {
            oName = oName.replaceAll("(?i).rptdesign", "");
        }
        String att = oName + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + att + "\"");

        return rOptions;
    }
}
