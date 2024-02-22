package com.jfcore.log.converter;

import org.apache.log4j.pattern.LoggingEventPatternConverter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 获取系统用户
 */
public class SystemUserConverter extends LoggingEventPatternConverter {
    private static final SystemUserConverter INSTANCE =
            new SystemUserConverter();
    private static final String USERNAME =
            System.getProperty("user.name");
    public static SystemUserConverter newInstance(
            final String[] options){
        return INSTANCE;
    }
    protected SystemUserConverter() {
        super("current system user name", "systemUser");
    }

    @Override
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        toAppendTo.append(USERNAME);
        
        
       
    }
}
