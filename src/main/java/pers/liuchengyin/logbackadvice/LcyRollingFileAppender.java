package pers.liuchengyin.logbackadvice;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;

/**
 * @ClassName RollingFileAppenderDS
 * @Description
 * @Author 柳成荫
 * @Date 2021/1/9
 */
public class LcyRollingFileAppender extends RollingFileAppender {

    @Override
    protected void subAppend(Object event) {
        DesensitizationAppender appender = new DesensitizationAppender();
        appender.operation((LoggingEvent)event);
        super.subAppend(event);
    }
}
