#!/bin/sh

CURRENTDIR=$(cd `dirname $0`;pwd)
PROGDIR=$(cd `dirname $0`/../..;pwd)  

MP_LIB=$PROGDIR/application/lib
LOCALCLASSPATH=
for i in `ls $MP_LIB/minuteKernel*.jar` `ls $MP_LIB/*.jar` `ls $MP_LIB/**/*.jar`
do
  LOCALCLASSPATH=${LOCALCLASSPATH}:"${i}"
done
LOCALCLASSPATH=${LOCALCLASSPATH}

"$JAVA_HOME/bin/java" -cp $LOCALCLASSPATH net.sf.minuteProject.application.ApplicationGenerator $1

