package com.jfcore.log.layout;



import org.apache.log4j.EnhancedPatternLayout;

import com.jfcore.log.parser.EnhancedPatternParser;


public class ExtendedEnhancedPatternLayout extends EnhancedPatternLayout {
    @Override
    protected org.apache.log4j.helpers.PatternParser createPatternParser(String pattern) {
        return new EnhancedPatternParser(pattern);
    }
}
