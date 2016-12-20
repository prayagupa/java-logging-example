package logger.savemyjob;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by prayagupd
 * on 12/19/16.
 */

public class DnLayout implements Layout<String> {

    @Override
    public byte[] getFooter() {
        return new byte[0];
    }

    @Override
    public byte[] getHeader() {
        return new byte[0];
    }

    @Override
    public byte[] toByteArray(LogEvent event) {
        return new byte[0];
    }

    @Override
    public String toSerializable(LogEvent event) {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public Map<String, String> getContentFormat() {
        return null;
    }

    @Override
    public void encode(LogEvent source, ByteBufferDestination destination) {

    }
}
