package com.jfcore.log.converter;

import org.apache.log4j.pattern.LoggingEventPatternConverter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * ProcessIdConverter
 */
public class ProcessIdConverter extends LoggingEventPatternConverter {
    private static final ProcessIdConverter INSTANCE =
            new ProcessIdConverter();

    private static final String PROCESS_ID =
            new ApplicationPid().toString();

    protected ProcessIdConverter() {
        super("application process pid", "processId");
    }

    public static ProcessIdConverter newInstance(
            final String[] options){
        return INSTANCE;
    }
    
    @Override
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
        toAppendTo.append(PROCESS_ID);
        
       
    }
}
