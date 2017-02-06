#!/bin/sh
export JAVA_HOME=/home/mx-www/jdk1.7.0_79 
export PATH=$JAVA_HOME/bin:$PATH 
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar 

live=`ps aux |grep "MailSender-0.0.1-SNAPSHOT.jar"|grep -v "grep"`
if [ -z "$live" ]
then


	`/var/www/MailSender/start.sh`
	result=`ps aux |grep "MailSender-0.0.1-SNAPSHOT.jar"|grep -v "grep"`

	while [ -z "$result" ]
	do
		sleep 5
		
		`/var/www/MailSender/start.sh`
		result=`ps aux |grep "MailSender-0.0.1-SNAPSHOT.jar"|grep -v "grep"`
	done

	flag=`ps aux |grep "MailSender-0.0.1-SNAPSHOT.jar"|grep -v "grep"`

	if [ ! -z "$flag" ]
	then
		sleep 5
		/usr/sbin/sendmail -T fuhongzhen@moxiu.net < /var/www/MailSender/emailInfo/lostInfo
	fi

fi

