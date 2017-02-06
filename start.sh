#!/bin/bash
source ~/.bash_profile
#export JAVA_HOME=/home/mx-www/jdk1.7.0_79 
#export PATH=$JAVA_HOME/bin:$PATH 
#export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
source /etc/profile
JAVA_OPTS="-Xms1024m -Xmx1024m -XX:MaxNewSize=350m -XX:PermSize=256M -XX:MaxPermSize=1024m"
nohup java $JAVA_OPTS -jar /var/www/MailSender/MailSender-0.0.1-SNAPSHOT.jar >/dev/null &
