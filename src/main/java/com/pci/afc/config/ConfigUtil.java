package com.pci.afc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
/**
 * Created by xwj on 2018-08-12.
 */
public class ConfigUtil {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    //获取配置文件信息
    public String getConfig(String key) {
        Properties p= new Properties();
        FileInputStream fis = null;
        String path= ConfigUtil.class.getClassLoader().getResource("application.properties").getPath();  //IDE调试环境时使用
        //String path=this.getClass().getResource("/").getPath().split("/lib")[0].toString().split("le:")[1]+"/config/application.properties"; //发布版本时使用
        logger.info("配置信息："+path);
        try {
            fis = new FileInputStream(path);
            p.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return p.getProperty(key);
    }
}
