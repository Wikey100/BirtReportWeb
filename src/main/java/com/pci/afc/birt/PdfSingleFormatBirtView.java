package com.pci.afc.birt;


import com.sun.scenario.animation.shared.FiniteClipEnvelope;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by xwj on 2018-08-12.
 */
public class PdfSingleFormatBirtView extends AbstractSingleFormatBirtView {

    public PdfSingleFormatBirtView() {
        setContentType("application/pdf");
    }
    protected final Logger log= LoggerFactory.getLogger(getClass());

    @Override
    protected RenderOption renderReport(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, BirtViewResourcePathCallback resourcePathCallback, Map<String, Object> appContextValuesMap, String reportName, String format, IRenderOption options) throws Throwable {
    	String oName = reportName;
        log.info("导出PDF报表名称:"+oName);

    	if( oName.toLowerCase().endsWith(".rptdesign")){
    		oName = oName.replaceAll("(?i).rptdesign", "");
    	}
    	response.setHeader ("Content-Disposition","attachment; filename="+oName +".pdf");

        PDFRenderOption pdfOptions = new PDFRenderOption(options);
        pdfOptions.setOutputFormat("pdf");
        pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
        pdfOptions.setOutputStream(response.getOutputStream());
        return pdfOptions;
    }
}
