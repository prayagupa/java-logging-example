package org.apache.logging.log4j.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.impl.*;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.util.DummyNanoClock;
import org.apache.logging.log4j.core.util.NanoClock;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.message.TimestampMessage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Created by prayagupd
 * on 12/19/16.
 */

public class DnLogEvent extends org.apache.logging.log4j.core.impl.Log4jLogEvent {

    private static final long serialVersionUID = -8393305700508709448L;
    private static volatile NanoClock nanoClock = new DummyNanoClock();
    private final String loggerFqcn;
    private final Marker marker;
    private final Level level;
    private final String loggerName;
    private Message message;
    private final long timeMillis;
    private final transient Throwable thrown;
    private ThrowableProxy thrownProxy;
    private final Map<String, String> contextMap;
    private final ThreadContext.ContextStack contextStack;
    private long threadId;
    private String threadName;
    private int threadPriority;
    private StackTraceElement source;
    private boolean includeLocation;
    private boolean endOfBatch;
    private final transient long nanoTime;

    private String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private DnLogEvent(String loggerName,
                       Marker marker,
                       String loggerFQCN,
                       Level level,
                       Message message,
                       Throwable thrown,
                       ThrowableProxy thrownProxy,
                       Map<String, String> contextMap,
                       ThreadContext.ContextStack contextStack,
                       long threadId,
                       String threadName,
                       int threadPriority,
                       StackTraceElement stackTraceElement,
                       long timestampMillis,
                       long nanoTime) {
        this.endOfBatch = false;
        this.loggerName = loggerName;
        this.marker = marker;
        this.loggerFqcn = loggerFQCN;
        this.level = level == null ? Level.OFF : level;
        this.message = message;
        this.thrown = thrown;
        this.thrownProxy = thrownProxy;
        this.contextMap = contextMap == null ? ThreadContext.EMPTY_MAP : contextMap;
        this.contextStack = (ThreadContext.ContextStack) (contextStack == null ? ThreadContext.EMPTY_STACK : contextStack);
        this.timeMillis = message instanceof TimestampMessage ? ((TimestampMessage) message).getTimestamp() : timestampMillis;
        this.threadId = threadId;
        this.threadName = threadName;
        this.threadPriority = threadPriority;
        this.source = stackTraceElement;
        if (message != null && message instanceof LoggerNameAwareMessage) {
            ((LoggerNameAwareMessage) message).setLoggerName(loggerName);
        }

        this.nanoTime = nanoTime;
    }

    public static DnLogEvent fromLog4jEvent(LogEvent logEvent) {
        System.out.println("logevent " + logEvent);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        final String date = format.format(new Date(logEvent.getTimeMillis()));

        Objects.requireNonNull(logEvent, "Event cannot be null");
        DnLogEvent result = new DnLogEvent(logEvent.getLoggerName(),
                logEvent.getMarker(),
                logEvent.getLoggerFqcn(),
                logEvent.getLevel(),
                logEvent.getMessage(),
                logEvent.getThrown(),
                logEvent.getThrownProxy(),
                logEvent.getContextMap(),
                logEvent.getContextStack(),
                logEvent.getThreadId(),
                logEvent.getThreadName(),
                logEvent.getThreadPriority(),
                logEvent.getSource(),
                logEvent.getTimeMillis(),
                logEvent.getNanoTime());
        result.setTimestamp(date);
        System.out.println(result);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String n = this.loggerName.isEmpty() ? "root" : this.loggerName;
        sb.append("Logger=").append(n);
        sb.append(" Level=").append(this.level.name());
        sb.append(" Message=").append(this.message.getFormattedMessage());
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            DnLogEvent that = (DnLogEvent) o;
            if (this.endOfBatch != that.endOfBatch) {
                return false;
            } else if (this.includeLocation != that.includeLocation) {
                return false;
            } else if (this.timeMillis != that.timeMillis) {
                return false;
            } else if (this.nanoTime != that.nanoTime) {
                return false;
            } else {
                if (this.loggerFqcn != null) {
                    if (!this.loggerFqcn.equals(that.loggerFqcn)) {
                        return false;
                    }
                } else if (that.loggerFqcn != null) {
                    return false;
                }

                label136:
                {
                    if (this.level != null) {
                        if (this.level.equals(that.level)) {
                            break label136;
                        }
                    } else if (that.level == null) {
                        break label136;
                    }

                    return false;
                }

                label129:
                {
                    if (this.source != null) {
                        if (this.source.equals(that.source)) {
                            break label129;
                        }
                    } else if (that.source == null) {
                        break label129;
                    }

                    return false;
                }

                if (this.marker != null) {
                    if (!this.marker.equals(that.marker)) {
                        return false;
                    }
                } else if (that.marker != null) {
                    return false;
                }

                if (this.contextMap != null) {
                    if (!this.contextMap.equals(that.contextMap)) {
                        return false;
                    }
                } else if (that.contextMap != null) {
                    return false;
                }

                if (!this.message.equals(that.message)) {
                    return false;
                } else if (!this.loggerName.equals(that.loggerName)) {
                    return false;
                } else {
                    label105:
                    {
                        if (this.contextStack != null) {
                            if (this.contextStack.equals(that.contextStack)) {
                                break label105;
                            }
                        } else if (that.contextStack == null) {
                            break label105;
                        }

                        return false;
                    }

                    if (this.threadId != that.threadId) {
                        return false;
                    } else {
                        if (this.threadName != null) {
                            if (!this.threadName.equals(that.threadName)) {
                                return false;
                            }
                        } else if (that.threadName != null) {
                            return false;
                        }

                        if (this.threadPriority != that.threadPriority) {
                            return false;
                        } else {
                            label89:
                            {
                                if (this.thrown != null) {
                                    if (this.thrown.equals(that.thrown)) {
                                        break label89;
                                    }
                                } else if (that.thrown == null) {
                                    break label89;
                                }

                                return false;
                            }

                            if (this.thrownProxy != null) {
                                if (!this.thrownProxy.equals(that.thrownProxy)) {
                                    return false;
                                }
                            } else if (that.thrownProxy != null) {
                                return false;
                            }

                            return true;
                        }
                    }
                }
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.loggerFqcn != null ? this.loggerFqcn.hashCode() : 0;
        result = 31 * result + (this.marker != null ? this.marker.hashCode() : 0);
        result = 31 * result + (this.level != null ? this.level.hashCode() : 0);
        result = 31 * result + this.loggerName.hashCode();
        result = 31 * result + this.message.hashCode();
        result = 31 * result + (int) (this.timeMillis ^ this.timeMillis >>> 32);
        result = 31 * result + (int) (this.nanoTime ^ this.nanoTime >>> 32);
        result = 31 * result + (this.thrown != null ? this.thrown.hashCode() : 0);
        result = 31 * result + (this.thrownProxy != null ? this.thrownProxy.hashCode() : 0);
        result = 31 * result + (this.contextMap != null ? this.contextMap.hashCode() : 0);
        result = 31 * result + (this.contextStack != null ? this.contextStack.hashCode() : 0);
        result = 31 * result + (int) (this.threadId ^ this.threadId >>> 32);
        result = 31 * result + (this.threadName != null ? this.threadName.hashCode() : 0);
        result = 31 * result + (this.threadPriority ^ this.threadPriority >>> 32);
        result = 31 * result + (this.source != null ? this.source.hashCode() : 0);
        result = 31 * result + (this.includeLocation ? 1 : 0);
        result = 31 * result + (this.endOfBatch ? 1 : 0);
        return result;
    }

    public static Serializable serialize(LogEvent event, boolean includeLocation) {
        if (event instanceof DnLogEvent) {
            event.getThrownProxy();
            return new LogEventProxy((DnLogEvent) event, includeLocation);
        } else {
            return new LogEventProxy(event, includeLocation);
        }
    }

    public static Log4jLogEvent deserialize(Serializable event) {
        Objects.requireNonNull(event, "Event cannot be null");
        if (event instanceof LogEventProxy) {
            LogEventProxy proxy = (LogEventProxy) event;
            DnLogEvent result = new DnLogEvent(proxy.loggerName, proxy.marker, proxy.loggerFQCN, proxy.level,
                    proxy.message, proxy.thrown, proxy.thrownProxy, proxy.contextMap, proxy.contextStack,
                    proxy.threadId, proxy.threadName, proxy.threadPriority, proxy.source, proxy.timeMillis,
                    proxy.nanoTime);
            result.setEndOfBatch(proxy.isEndOfBatch);
            result.setIncludeLocation(proxy.isLocationRequired);
            return result;
        } else {
            throw new IllegalArgumentException("Event is not a serialized LogEvent: " + event.toString());
        }
    }

    static class LogEventProxy implements Serializable {
        private static final long serialVersionUID = -8634075037355293699L;
        private final String loggerFQCN;
        private final Marker marker;
        private final Level level;
        private final String loggerName;
        private final Message message;
        private final long timeMillis;
        private final transient Throwable thrown;
        private final ThrowableProxy thrownProxy;
        private final Map<String, String> contextMap;
        private final ThreadContext.ContextStack contextStack;
        private final long threadId;
        private final String threadName;
        private final int threadPriority;
        private final StackTraceElement source;
        private final boolean isLocationRequired;
        private final boolean isEndOfBatch;
        private transient final long nanoTime;
        private final String timestamp;

        public LogEventProxy(DnLogEvent event, boolean includeLocation) {
            this.loggerFQCN = event.loggerFqcn;
            this.marker = event.marker;
            this.level = event.level;
            this.loggerName = event.loggerName;
            this.message = event.message instanceof ReusableMessage ? this.memento((ReusableMessage) event.message) : event.message;
            this.timeMillis = event.timeMillis;
            this.thrown = event.thrown;
            this.thrownProxy = event.thrownProxy;
            this.contextMap = event.contextMap;
            this.contextStack = event.contextStack;
            this.source = includeLocation ? event.getSource() : null;
            this.threadId = event.getThreadId();
            this.threadName = event.getThreadName();
            this.threadPriority = event.getThreadPriority();
            this.isLocationRequired = includeLocation;
            this.isEndOfBatch = event.endOfBatch;
            this.nanoTime = event.nanoTime;
            this.timestamp = event.timestamp;
        }

        public LogEventProxy(LogEvent event, boolean includeLocation) {
            this.loggerFQCN = event.getLoggerFqcn();
            this.marker = event.getMarker();
            this.level = event.getLevel();
            this.loggerName = event.getLoggerName();
            Message msg = event.getMessage();
            this.message = msg instanceof ReusableMessage ? this.memento((ReusableMessage) msg) : msg;
            this.timeMillis = event.getTimeMillis();
            this.thrown = event.getThrown();
            this.thrownProxy = event.getThrownProxy();
            this.contextMap = event.getContextMap();
            this.contextStack = event.getContextStack();
            this.source = includeLocation ? event.getSource() : null;
            this.threadId = event.getThreadId();
            this.threadName = event.getThreadName();
            this.threadPriority = event.getThreadPriority();
            this.isLocationRequired = includeLocation;
            this.isEndOfBatch = event.isEndOfBatch();
            this.nanoTime = event.getNanoTime();

            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            final String date = myFormat.format(new Date(event.getTimeMillis()));

            this.timestamp = date;
        }

        private Message memento(ReusableMessage message) {
            return message.memento();
        }

        protected Object readResolve() {
            Log4jLogEvent result = new DnLogEvent(this.loggerName, this.marker, this.loggerFQCN, this.level, this.message, this.thrown, this.thrownProxy, this.contextMap, this.contextStack, this.threadId, this.threadName, this.threadPriority, this.source, this.timeMillis, this.nanoTime);
            result.setEndOfBatch(this.isEndOfBatch);
            result.setIncludeLocation(this.isLocationRequired);
            return result;
        }
    }
}
