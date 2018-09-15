#!/usr/bin/env bash
ulimit -s 20480

cd `dirname $0`
APP_HOME=`pwd`

LIB_JARS=""
for i in "$APP_HOME"/lib/*.war;do
    LIB_JARS="$i"
done

HTTP_PORT=`sed '/server.port/!d;s/.*=//' config/application.properties | tr -d '\r'`

if [ x"$JAVA_OPTS" == x ];then
    JAVA_OPTS="-Djava.awt.headless=true -Djava.net.preferIPv4Stack=true"
fi
if [ x"$JAVA_MEM_OPTS" == x ];then
    JAVA_MEM_OPTS="-server -Xms1024m -Xmx3800m -Xmn256m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
fi

START_WAIT_TIMEOUT=30

GET_PID_BY_ALL_PORT() {
    pidUsed=$(GET_PID_BY_HTTP_PORT)
    echo ${pidUsed}
}

GET_PID_BY_HTTP_PORT() {
    if [ x"$HTTP_PORT" != x ];then
        echo `lsof -i :${HTTP_PORT} | grep LISTEN | awk '{print $2}'`
    fi
}

start() {
    PID=$(GET_PID_BY_ALL_PORT)

    if [ x"$PID" != x ];then
        echo "==================== Failed! ====================="
        isSystem=`ps -ef | grep ${PID} | grep -v grep `

        if [ x"$isSystem" != x ]; then
            isThisSystem=`ps -ef | grep ${PID} | grep -v grep | grep "$APP_HOME"`
            if [ x"$isThisSystem" != x ]; then
                echo "========    System is already started!    ========"
            else
                echo "========   Port is used by other System!  ========"
            fi
        else
            echo "========  Port is used by other process!  ========"
        fi
        echo "=================================================="
        echo "try: ps -ef | grep ${PID} | grep -v grep"
        status
    else
        echo "               Starting Server ..."
        echo

        java ${JAVA_OPTS} ${JAVA_MEM_OPTS} -jar ${LIB_JARS} --spring.config.location=conf &
        sleep 3
        starttime=0
        while  [ x"$PID" == x ]; do
            if [[ "$starttime" -lt ${START_WAIT_TIMEOUT} ]]; then
                sleep 1
                ((starttime++))
                PID=$(GET_PID_BY_HTTP_PORT)
            else
                echo "System Server failed to start"
                echo "The port HTTP_PORT doesn't open in ${START_WAIT_TIMEOUT} seconds!"
                status
                exit -1
            fi
        done
        echo "The process of System Server is started: pid=$PID"
        status
    fi
}

stop() {
    if [ x"$HTTP_PORT" != x ]; then
        PID=$(GET_PID_BY_HTTP_PORT)
        if [ x"$PID" == x ]; then
            echo "==================== Failed! ====================="
            echo "========      System is not started!      ========"
            echo "=================================================="
            status
            return
        fi
    fi

    isThisSystem=`ps -ef | grep ${PID} | grep -v grep | grep "$APP_HOME"`
    if [ x"$isThisSystem" == x ]; then
        echo "==================== Failed! ====================="
        echo "======== Another Program is using the port! ======"
        echo "=================================================="
        echo "try: ps -ef | grep $PID | grep -v grep"
        status
        return
    fi

    echo "System Server is running with port $HTTP_PORT: pid=$PID"
    echo "trying to stop System Server (pid=$PID) ..."
    kill -15 ${PID}
    sleep 3
    PID=$(GET_PID_BY_HTTP_PORT)
    while [ x"$PID" != x ]; do
        kill ${PID}
        sleep 1
        PID=$(GET_PID_BY_HTTP_PORT)
    done

    echo
    echo "====================    OK    ===================="
    echo "========       System Server stopped      ========"
    echo "=================================================="
    status
}

status() {
    PID=$(GET_PID_BY_ALL_PORT)

    echo "====================  status  ===================="

    if [ x"$PID" != x ]; then
        isSystem=`ps -ef | grep ${PID} | grep -v grep `
        if [ x"$isSystem" != x ]; then
            isThisSystem=`ps -ef | grep ${PID} | grep -v grep | grep "$APP_HOME"`
            if [ x"$isThisSystem" != x ]; then
                echo "System server (pid=$PID) is running and using port:${HTTP_PORT}"
            else
                echo "Another server (pid=$PID) is running and using port:${HTTP_PORT}"
            fi
        else
            echo "Another process (pid=$PID) is using port:${HTTP_PORT}"
        fi
    else
        echo "port (${HTTP_PORT}) are not in use!"
    fi
        echo "=================================================="

    if [ x"$HTTP_PORT" != x ]; then
        PID=$(GET_PID_BY_HTTP_PORT)
        if [ x"$PID" != x ]; then
            echo "HTTP_PORT ${HTTP_PORT} is in use by process (pid=$PID)"
        else
            echo "HTTP_PORT ${HTTP_PORT} is not in use"
        fi
    fi
}

info() {
    echo "********             [OS]                 ********"
    echo "OS Infomation: " `uname -a`
    echo
    echo "********             [JVM]                ********"
    echo "JAVA_HOME: $JAVA_HOME"
    echo "JAVA_OPTS: $JAVA_OPTS"
    echo "JAVA_MEM_OPTS: $JAVA_MEM_OPTS"
    echo
    echo "********             [CLASSPATH]          ********"
    echo "CLASSPATH: $CLASSPATH"
    echo
    echo "********             [APPLICATION]        ********"
    echo "JAVA_MAIN_CLASS: $APP_MAIN_CLASS"
    echo "APP_HOME=$APP_HOME"
    echo "**************************************************"
}

case "$1" in
    'start')
        start
        ;;
    'stop')
        stop
        ;;
    'restart')
        stop
        start
        ;;
    'status')
        status
        ;;
    'info')
        info
        ;;
    *)
    echo "Usage: $0 {start|stop|restart|status|info}"
    exit 1
esac
