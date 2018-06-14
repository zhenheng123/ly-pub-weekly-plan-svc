#基础镜像
FROM 192.168.2.78:5000/library/centos-jdk:1.7.79
#作者
MAINTAINER WenboLi liwenbo@ly-sky.com
#执行命令，主要用来安装相关的软件
#RUN 
#添加文件
ADD ly-pub-weekly-plan-svc-0.0.1-SNAPSHOT.jar /usr/local
RUN chmod u+x /usr/local/ly-pub-weekly-plan-svc-0.0.1-SNAPSHOT.jar
#挂载目录到容器
#VOLUME ["/data"]
#环境变量设置
#ENV 
#开放端口
EXPOSE 1451
#启动时执行的命令
CMD ["/bin/bash"]
#启动时执行的命令
ENTRYPOINT ["java","-jar","-Xms2048m", "-Xmx2048m", "-XX:PermSize=256M", "-XX:MaxPermSize=256M","/usr/local/ly-pub-weekly-plan-svc-0.0.1-SNAPSHOT.jar"]