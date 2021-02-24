#!/bin/sh

PROGDIR=$(cd `dirname $0`/../../..;pwd)  
echo progdir = $PROGDIR

MP_LIB=$PROGDIR/minuteKernel/lib
#MP_LIB=$PROGDIR/application/lib
LOCALCLASSPATH=
for i in `ls $MP_LIB/*.jar` `ls $MP_LIB/**/*.jar`
do
  LOCALCLASSPATH=${LOCALCLASSPATH}:"${i}"
done

export $LOCALCLASSPATH
