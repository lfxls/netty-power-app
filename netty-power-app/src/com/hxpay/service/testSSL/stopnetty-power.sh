#!/bin/sh
# stopTDS.sh:  Date: 2012.7.31
echo  "    +-----------------------------------------------------------------+"
echo  "    +-                                                               -+"
echo  "    +-    Company:  Hangzhou Hexing Information Technology Co.,Ltd   -+"
echo  "    +-    DepartMent: The third party payment business department    -+"
echo  "    +-                                                               -+"
echo  "    +-    Function: Stop netty-power-app Service                                 -+"
echo  "    +-                                                               -+"
echo  "    +-----------------------------------------------------------------+"
echo  "    "
echo  "    "
echo  "     begin stop netty-power-app Service ..."

pid=`ps -fe | grep java | grep "tdshome/netty-power-app.jar" | grep -v oracle |grep -v grep|grep -v resin |grep -v hudson|grep -v tomcat|awk '{print $2}'`
if [ "$pid" != "" ]; then
  echo "     shutdown netty-power-app Service, process ID=[$pid]."
  kill -9 $pid
  if [ $? -eq 1 ]; then
    echo "     shutdown netty-power-app service Failure."
  else
    echo "     stop netty-power-app Service succss!"
    
  fi
else
  echo "     netty-power-app Service is not running. "
fi

echo '    '
