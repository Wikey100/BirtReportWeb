package com.pci.afc.birt;

import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by xwj on 2018-08-12.
 */
public class HtmlSingleFormatBirtView extends AbstractSingleFormatBirtView {

    public HtmlSingleFormatBirtView() {
        setContentType("text/html");
    }

    protected final Logger log= LoggerFactory.getLogger(getClass());

    @Override
    protected RenderOption renderReport(Map<String, Object> modelData, HttpServletRequest request, HttpServletResponse response, BirtViewResourcePathCallback resourcePathCallback, Map<String, Object> appContextValuesMap, String reportName, String format, IRenderOption options) throws Throwable {
        ServletContext sc = request.getServletContext();
        HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
        htmlOptions.setOutputFormat("html");
        htmlOptions.setOutputStream(response.getOutputStream());
        htmlOptions.setImageHandler(new HTMLServerImageHandler());
        htmlOptions.setBaseImageURL(birtViewResourcePathCallback.baseImageUrl(sc, request, reportName));
        htmlOptions.setImageDirectory(birtViewResourcePathCallback.imageDirectory(sc, request, reportName));
        htmlOptions.setBaseURL(birtViewResourcePathCallback.baseUrl(sc, request, reportName));
        log.info("导出html报表名称:"+reportName);
        return htmlOptions;
    }
}
