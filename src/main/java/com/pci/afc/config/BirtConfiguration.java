package com.pci.afc.config;

import com.pci.afc.birt.AbstractSingleFormatBirtView;
import com.pci.afc.birt.BirtEngineFactory;
import com.pci.afc.birt.ExcelSingleFormatBirtView;
import com.pci.afc.birt.HtmlSingleFormatBirtView;
import com.pci.afc.birt.PdfSingleFormatBirtView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by xwj on 2018-08-12.
 */
@Configuration
public class BirtConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public HtmlSingleFormatBirtView htmlSingleFormatBirtView() throws Throwable {
        HtmlSingleFormatBirtView abstractSingleFormatBirtView = new HtmlSingleFormatBirtView();
        setAttribute(abstractSingleFormatBirtView);
        return abstractSingleFormatBirtView;
    }

    @Bean
    public ExcelSingleFormatBirtView excelSingleFormatBirtView() throws Throwable {
        ExcelSingleFormatBirtView abstractSingleFormatBirtView = new ExcelSingleFormatBirtView();
        setAttribute(abstractSingleFormatBirtView);
        return abstractSingleFormatBirtView;
    }

    @Bean
    public PdfSingleFormatBirtView ticketAddByDayView() throws Throwable {
        PdfSingleFormatBirtView abstractSingleFormatBirtView = new PdfSingleFormatBirtView();
        setAttribute(abstractSingleFormatBirtView);
        return abstractSingleFormatBirtView;
    }

    private void setAttribute(AbstractSingleFormatBirtView abstractSingleFormatBirtView) {
        abstractSingleFormatBirtView.setDataSource(dataSource);
        abstractSingleFormatBirtView.setBirtEngine(new BirtEngineFactory().getObject());
        abstractSingleFormatBirtView.setReportsDirectory("reports");
    }
}
