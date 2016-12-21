
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

With Async logging, 

```

```

TODO
----

set `-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector`

run the class `Joker`
