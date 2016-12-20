
Demo for sync/async log4j2

```
{
  "configuration": {
    "name": "logggg",
    "packages" : "logger.savemyjob",
    "appenders": {
      "RollingFile": {
        "name": "rollingStone",
        "fileName": "async_rolled.log",
        "filePattern": "async_rolled-%d{MM-dd-yy-HH-mm-ss}-%i.log.gz",
        "immediateFlush" : false,
        "PatternLayout" : {
          "pattern" : "{timestamp : %timestamp, message: %m%n}"
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

run the class `SupplyChainPeaceLogger`

`cat async_rolled.log` would result 

```
{timestamp : Mon Dec 19 21:28:08 PST 2016, message: Peace is failing }
```