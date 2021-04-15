#指定基础镜像，在其上进行定制
FROM welkincode/jdk8:1.0
#维护者信息
MAINTAINER yuanjinguang <yjg_678@163.com>
#系统编码
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8
#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
VOLUME /tmp
#复制上下文目录下的target/demo-1.0.0.jar 到容器里
COPY ./target/welkin-admin-0.0.1-SNAPSHOT.jar welkin-admin-0.0.1-SNAPSHOT.jar
#声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 8080
#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","welkin-admin-0.0.1-SNAPSHOT.jar"]
