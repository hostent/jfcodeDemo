package com.jfcore.log.parser;



import org.apache.log4j.pattern.BridgePatternConverter;
import org.apache.log4j.pattern.PatternParser;

import com.jfcore.log.converter.EnhancedBridgePatternConverter;

/**
 * 重写的增强Parser
 * Created by zhangzhenbin on 17-5-25.
 */
public class EnhancedPatternParser extends org.apache.log4j.helpers.PatternParser {
    public EnhancedPatternParser(final String pattern) {
        super(pattern);
    }
    /**
     * Create new pattern converter.
     * @return pattern converter.
     */
    public org.apache.log4j.helpers.PatternConverter parse() {
        return new EnhancedBridgePatternConverter(pattern);
    }
}
