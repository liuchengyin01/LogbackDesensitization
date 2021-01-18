# Logback和slf4j的日志脱敏组件
#### 前言
对于日志脱敏的方式有很多，常见的有①使用conversionRule标签，继承MessageConverter②书写一个脱敏工具类，在打印日志的时候对特定特字段进行脱敏返回。
这两种方案都不太好使，对于有新的字段需要脱敏要加入没有特别好的方案去处理，其次就是同一字段其匹配规则不同可能对其脱敏的要求也有不同需求。

#### 本组件说明
本组件基于这样的考虑，采用yml配置文件来扩展脱敏字段及不同的脱敏规则。其核心思想就是：读取配置文件里的脱敏字段和其脱敏规则。在日志替换的时候将字段进行脱敏。
本组件基于logback+slf4j，暂不支持log4j，如需解决log4j的日志，可以参照本组件的思想去完成。
本组件支持的脱敏数据类型：八大基本类型及其包装类型、String类型、Map、List<Map>、JSON字符串、项目中的POJO对象。
注：Map、JSON、List<Map>、POJO对象的脱敏处理需要配置其对应的字段名即可,无法处理List<八大类型+字符串>类型。


#### 匹配规则：
key + 分割符 + value，如phone:{},即phone:13436781234。如email={},即email=123456789@qq.com
本组件默认只支持冒号和等号分割，如需其他方式可以修改正则匹配方式(本组件的正则匹配是匹配的key:value和key=value)
如：
```java
log.info("your email:{}, your phone:{}", "123456789@qq.com","15310763497");
log.info("your email={}, your cellphone={}", "123456789@qq.com","15310763497");
```

####
使用方式：
1、引入Jar包或其对应的pom依赖
```java
<dependency>
    <groupId>pers.liuchengyin</groupId>
    <artifactId>logback-desensitization</artifactId>
    <version>1.0.0</version>
</dependency>
```
2、配置logback-desensitize.yml(后文介绍)
3、在logback.xml中引入对应的Appender，使用组件里的类代替原来的
###### ①ConsoleAppender - 控制台脱敏
原配置类：
```java
ch.qos.logback.core.ConsoleAppender
```
替换类：
```java
pers.liuchengyin.logbackadvice.LcyConsoleAppender
```
###### ②RollingFileAppender - 滚动文件
原配置类：
```java
ch.qos.logback.core.rolling.RollingFileAppender
```
替换类：
```java
pers.liuchengyin.logbackadvice.LcyConsoleAppender
```
###### ③FileAppender - 文件
原配置类：
```java
ch.qos.logback.core.FileAppender
```
替换类：
```java
pers.liuchengyin.logbackadvice.LcyFileAppender
```

#### 脱敏规则配置文件 - logback-desensitize.yml
