
Core config, 
------------

```
$ sysctl -n hw.ncpu
8
```

Demo for sync log4j2

```
{
  "configuration": {
    "name": "logggg",
    "appenders": {
      "RollingFile": {
        "name": "rollingStone",
        "fileName": "sync_rolled.log",
        "filePattern": "%d{MM-dd-yy-HH-mm-ss}-%i.log.gz",
        "JSONLayout": {
          "complete": true,
          "compact": false,
          "eventEol": true
        },
        "SizeBasedTriggeringPolicy": {
          "size": "10 MB"
        },
        "DefaultRolloverStrategy": {
          "max": "10"
        }
      }
    },
    "loggers": {
      "root": {
        "level": "debug",
        "appender-ref": {
          "ref": "rollingStone"
        }
      }
    }
  }
}
```

output
------

```
{"timeMillis":1482343311313,"thread":"Thread-12","level":"INFO","message":"ConcurrentRequest12;5","endOfBatch":false,"loggerFqcn":"org.apache.logging.log4j.spi.AbstractLogger","threadId":24,"threadPriority":5}

Millis":1482343311311,"thread":"Thread-16","level":"INFO","message":"ConcurrentRequest16;4","endOfBatch":false,"loggerFqcn":"org.apache.logging.log4j.spi.AbstractLogger","threadId":28,"threadPriority":5}
{"time{"timeMillis":1482343311313,"thread":"Thread-22","level":"INFO","message":"ConcurrentRequest22;5","endOfBatch":false,"loggerFqcn":"org.apache.logging.log4j.spi.AbstractLogger","threadId":34,"threadPriority":5}
{"timeMillis":1482343311313,"thread":"Thread-11","level":"INFO","message":"ConcurrentRequest11;6","endOfBatch":false,"loggerFqcn":"org.apache.logging.log4j.spi.AbstractLogger","threadId":23,"threadPriority":5}
{"timeMillis":1482343311313,"thread":"Thread-14","level":"INFO","message":"ConcurrentRequest14;11","endOfBatch":false,"loggerFqcn":"org.apache.logging.log4j.spi.AbstractLogger","threadId":26,"threadPriority":5}

```

with 32 concurrent requests writing 200 writes each, there should have been 6400 logs but happens to be only around 5880, which is totally 
unacceptable. Mate, this is in prod for a big company which claims they have great technology.

With Async logging, 

```
`-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector`
```

```
<?xml version="1.0" encoding="UTF-8"?>

<!-- Don't forget to set system property
-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
     to make all loggers asynchronous. -->

<Configuration status="INFO">
  <Appenders>
    <!-- Async Loggers will auto-flush in batches, so switch off immediateFlush. -->
    <RollingFile name="rollingStone" fileName="async.log" immediateFlush="false" append="true"
                 filePattern="logs/async-%d{MM-dd-yyyy}-%i.log.gz">
      <PatternLayout>
        <Pattern>%d %p %c{1.} [%t] %m %ex%n</Pattern>
      </PatternLayout>
      <Policies>
        <SizeBasedTriggeringPolicy size="2 KB"/>
      </Policies>
    </RollingFile>
  </Appenders>
  <Loggers>
    <Root level="debug" includeLocation="false">
      <AppenderRef ref="rollingStone"/>
    </Root>
  </Loggers>
</Configuration>

```

all 6400 writes are there in a file.


run the class `Joker` changing log4j2.json file pointer to `_threadsafe.log`
