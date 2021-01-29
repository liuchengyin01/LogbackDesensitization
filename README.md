# Logback和slf4j的日志脱敏组件
#### 前言
对于日志脱敏的方式有很多，常见的有①使用conversionRule标签，继承MessageConverter②书写一个脱敏工具类，在打印日志的时候对特定特字段进行脱敏返回。
两种方式各有优缺点：第一种方式需要修改代码，不符合开闭原则。第二种方式，需要在日志方法的参数进行脱敏，对原生日志有入侵行为。


#### 本组件说明
本组件基于非入侵及脱敏字段扩展考虑，采用yml配置文件来扩展脱敏字段及不同的脱敏规则。其核心思想就是：读取配置文件里的脱敏字段和其脱敏规则。在日志替换的时候将字段进行脱敏。
本组件基于logback+slf4j，暂不支持log4j，如需解决log4j的日志，可以参照本组件的思想去完成。
本组件支持的脱敏数据类型：八大基本类型及其包装类型、String类型、Map、List<Map>、JSON字符串、项目中的POJO对象。
注：Map、JSON、List<Map>、POJO、List<Pojo>对象的脱敏处理需要配置其对应的字段名即可,无法处理List<八大类型+字符串>类型。
在使用本组件的时候，一定要注重日志打印规范。
	
具体使用方式见博客(重要！重要！重要！)：https://blog.csdn.net/qq_40885085/article/details/113385261

#### 匹配规则：
key + 分割符 + value，如phone:{},即phone:13436781234。如email={},即email=123456789@qq.com
本组件默认只支持冒号和等号分割，如需其他方式可以修改正则匹配方式(本组件的正则匹配是匹配的key:value和key=value)
如：
```java
log.info("your email:{}, your phone:{}", "123456789@qq.com","15310763497");
log.info("your email={}, your cellphone={}", "123456789@qq.com","15310763497");
```

#### 打入本地仓库
我原本是想将Jar包发布在Github，使用Github作为Maven仓库。尝试过，但是没有成功(自己太菜了)。
因此，我只能将Jar包上传到Github，读者若需要可以直接下载Jar包打入本地仓库即可使用(也可以直接引入Jar包)。
Jar包在/repo/pers/liuchengyin/logback-desensitization/1.0.0文件夹下，名为`logback-desensitization-1.0.0.jar`
##### 打入方法：
1、将jar包放入某个文件夹内，在这个文件夹内打开cmd（前提，Maven配置无误，可以使用mvn -v检查）
2、使用如下命令即可
```java
mvn install:install-file -DgroupId=pers.liuchengyin -DartifactId=logback-desensitization -Dversion=1.0.0 -Dpackaging=jar -Dfile=logback-desensitization-1.0.0.jar
```
3、命令说明
```java
  -DgroupId
	表示jar对应的groupId  
	<groupId>pers.liuchengyin</groupId>
 -DartifactId:
	表示jar对应的artifactId
	<artifactId>logback-desensitization</artifactId>
 -Dversion
	表示jar对应的 version
	<version>1.0.0</version>
```

#### 使用方式
1、引入Jar包或其对应的pom依赖
```java
<dependency>
    <groupId>pers.liuchengyin</groupId>
    <artifactId>logback-desensitization</artifactId>
    <version>1.0.0</version>
</dependency>
```
2、配置logback-desensitize.yml
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
pers.liuchengyin.logbackadvice.LcyRollingFileAppender
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
