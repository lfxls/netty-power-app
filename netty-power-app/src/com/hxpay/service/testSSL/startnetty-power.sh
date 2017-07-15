# startTDS.sh:  Date: 2012.7.31
if [ `id -u` -eq 0 ]; then
    echo "启动失败，当前用户为root用户，请使用pay用户启动服务!"
        exit 
fi 
if [ `whoami` != "pay" ];then  
	echo "启动失败，请使用pay用户启动"
	exit 
fi 
./stopTDS.sh

echo  "    +-----------------------------------------------------------------+"
echo  "    +-                                                               -+"
echo  "    +-    Company:  Hangzhou Hexing Information Technology Co.,Ltd   -+"
echo  "    +-    DepartMent: The third party payment business department    -+"
echo  "    +-                                                               -+"
echo  "    +-    Function: Startup netty-power-app Service                  -+"
echo  "    +-                                                               -+"
echo  "    +-----------------------------------------------------------------+"
echo  "    "
echo  "    "
echo  "     Function: Startup netty-power-app Service"
echo  '    '
echo  "     begin startup netty-power-app Service ..."

# Set Lang
export LANG=en_US.UTF-8
echo "     Set System LANG=$LANG is success"

#nohup /usr/share/jdk1.6.0_27/bin/java -jar -Xms256m -Xmx1024m -Dcom.sun.management.jmxremote.port=8187 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false  start.jar & 
nohup $JAVA_HOME/bin/java -jar -Xms256m -Xmx512m ../tdshome/netty-power-app.jar >> netty.out &
pid=`ps -fe | grep java | grep "tdshome/netty-power-app.jar" | grep -v oracle |grep -v grep|grep -v resin |grep -v hudson|grep -v tomcat|awk '{print $2}'`
if [ "$pid" != "" ]; then
  echo "     Startup netty-power-app Service succss, process ID=[$pid]."
  echo "     netty-power-app Log is appending to ~/tdshome/nohup.out."
else
  echo "     Startup netty-power-app service Failure."
  echo "     netty-power-app Service is not running. "
fi

echo '    '
