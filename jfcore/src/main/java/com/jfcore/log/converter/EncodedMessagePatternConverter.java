package com.jfcore.log.converter;

import org.apache.log4j.pattern.LoggingEventPatternConverter;
import org.apache.log4j.pattern.MessagePatternConverter;
import org.apache.log4j.spi.LoggingEvent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 去掉消息中的单双引号
 */
public class EncodedMessagePatternConverter extends LoggingEventPatternConverter {
    /**
     * Singleton.
     */
    private static final EncodedMessagePatternConverter INSTANCE =
            new EncodedMessagePatternConverter();

    /**
     * Private constructor.
     */
    private EncodedMessagePatternConverter() {
        super("encode Message", "encodedMessage");
    }

    /**
     * Obtains an instance of pattern converter.
     * @param options options, may be null.
     * @return instance of pattern converter.
     */
    public static EncodedMessagePatternConverter newInstance(
            final String[] options) {
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    public void format(final LoggingEvent event, final StringBuffer toAppendTo) {
        toAppendTo.append(event.getRenderedMessage().replace("\"","\\\"").replace("'","\\'"));
        
       
    }
}
