package com.jfcore.log.converter;


import org.apache.log4j.pattern.LoggingEventPatternConverter;
import org.apache.log4j.spi.LoggingEvent;
import com.jfcore.tools.PropertiesHelp;

/**
 * 获取配置文件中的APPID
 */
public class AppIdConverter extends LoggingEventPatternConverter {
    private static final AppIdConverter INSTANCE =
            new AppIdConverter();

    private AppIdConverter() {
        super("APP ID", "appId");
    }
    public static AppIdConverter newInstance(
            final String[] options) {
        return INSTANCE;
    }
    	

    @Override
    public void format(LoggingEvent event, StringBuffer toAppendTo) {
//        try {
//            Slf4jKafkaConfig contextSlf4jKafkaConfig = Slf4jKafkaContext.getContextSlf4jKafkaConfig();
//            toAppendTo.append(contextSlf4jKafkaConfig.getAppId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    	try
    	{
    		 toAppendTo.append(PropertiesHelp.getApplicationConf("spring.application.name"));
    	}
    	catch (Exception e) {
    		 e.printStackTrace();
		}
    	
    	 
    }
}
