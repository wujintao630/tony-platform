#基础镜像
FROM openjdk:8-jdk-alpine

#作者
MAINTAINER tonytaotao

#在容器中创建挂载点，可以多个
VOLUME  /tmp

#定义参数
ARG JAR_FILE

#拷贝本地文件到镜像中
ADD ${JAR_FILE} /app/app.jar

WORKDIR /app

#端口
EXPOSE 9005

#容器启动时命令
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "./app.jar"]