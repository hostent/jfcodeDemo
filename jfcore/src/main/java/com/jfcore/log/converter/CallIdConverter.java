package com.jfcore.log.converter;

import org.apache.log4j.pattern.LoggingEventPatternConverter;
import org.apache.log4j.spi.LoggingEvent;

import com.jfcore.frame.CallerContext;

public class CallIdConverter extends LoggingEventPatternConverter{

	 

    private static final CallIdConverter INSTANCE =
            new CallIdConverter();
 
    public static CallIdConverter newInstance(
            final String[] options){
        return INSTANCE;
    }
    protected CallIdConverter() {
        super("current system call id", "callId");
    }
	@Override
	public void format(LoggingEvent event, StringBuffer toAppendTo) {

		toAppendTo.append(CallerContext.getCallerID());
 
		
	}

}
