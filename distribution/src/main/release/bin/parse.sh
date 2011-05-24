DIRNAME=`dirname $0`

SCRIBBLE_HOME=$DIRNAME/..

. $SCRIBBLE_HOME/bin/env.sh

java -classpath $CLASSPATH $FELIX_OPTIONS org.scribble.commandline.ScribbleCL parse $1
