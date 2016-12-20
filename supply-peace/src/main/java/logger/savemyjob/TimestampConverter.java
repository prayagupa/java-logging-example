package logger.savemyjob;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.ThreadIdPatternConverter;

import java.util.Date;

/**
 * Created by prayagupd
 * on 12/19/16.
 */

@Plugin(name = "TimestampConverter", category = "Converter")
@ConverterKeys({"timestamp", "timestamp"})
public class TimestampConverter extends LogEventPatternConverter {

    protected TimestampConverter(String key, String style){
        super(key, style);
    }

    public static TimestampConverter newInstance(String[] options){
        return new TimestampConverter("timestamp", Thread.currentThread().getName());
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {
        Date date1 = getTimestamp(event);

        toAppendTo.append(date1);
    }

    private Date getTimestamp(LogEvent event) {
        long date = event.getTimeMillis();
        return new Date(date);
    }
}
