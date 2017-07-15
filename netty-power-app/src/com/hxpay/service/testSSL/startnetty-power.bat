chcp 65001
echo  "    +-----------------------------------------------------------------+"
echo  "    +-                                                               -+"
echo  "    +-    Company:  Hangzhou Hexing Information Technology Co.,Ltd   -+"
echo  "    +-    DepartMent: The third party payment business department    -+"
echo  "    +-                                                               -+"
echo  "    +-    Function: Startup TDS Service                              -+"
echo  "    +-                                                               -+"
echo  "    +-----------------------------------------------------------------+"
echo  "    "
echo  "    "
echo  "     Function: Startup TDS Service"
echo  '    '
echo  "     begin startup TDS Service ..."

java  -Dfile.encoding=utf-8 -jar -Xms256m -Xmx512m netty-power-app.jar 
PAUSE
