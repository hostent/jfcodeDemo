package com.jfcore.log.appender;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

import com.jfcore.kafka.MqProducerHelp;

public class KafkaAppender extends WriterAppender {
	
	
	final static String topic="KafkaLog";
	
 
	@Override
	protected boolean checkEntryConditions() {
		
		//System.out.println("kafkaAppender 准备就绪");
		

		//判断是否准备就绪,一个日志都调用一次。
		return true;
	}
	
 
	@Override
	protected void subAppend(LoggingEvent event) {
		 if (event.getLoggerName().contains("org.apache.kafka")) {
	            System.err.println("contain org.apache.kafka,break subAppend");
	            return;
	        }
	        String msg = this.layout.format(event);
	        
	        //System.out.println(msg);
	        
	        MqProducerHelp.send(topic, msg);
	        
	       
	}
	
 
}
