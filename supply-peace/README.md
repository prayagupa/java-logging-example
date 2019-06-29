
Demo for sync/async log4j2
--

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

TODO
----

set `-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector`

run the class `SupplyChainLogger`

`cat sync_rolled.log` would result `%d %p %c{1.} [%t] %m%n`

```
| %d        | %p           | %c    | %t                    | %n
------------|--------------|-------|-----------------------|---------------------------------
|2016-09-17 | 19:47:18,131 | DEBUG | suppliesLogger [main] | I'm Hunter Thomson and I'm alive.
```