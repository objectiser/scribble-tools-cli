DIRNAME=`dirname $0`

SCRIBBLE_HOME=$DIRNAME/..

FELIX_OPTIONS="-Dfelix.auto.deploy.dir=$SCRIBBLE_HOME/bundle -Dfelix.config.properties=file:$SCRIBBLE_HOME/conf/config.properties"

CLASSPATH=$SCRIBBLE_HOME/lib/org.apache.felix.framework.jar
CLASSPATH=$CLASSPATH:$SCRIBBLE_HOME/lib/org.apache.felix.main.jar
CLASSPATH=$CLASSPATH:$SCRIBBLE_HOME/lib/org.osgi.core.jar
CLASSPATH=$CLASSPATH:$SCRIBBLE_HOME/bundle/org.scribble.command.jar
CLASSPATH=$CLASSPATH:$SCRIBBLE_HOME/lib/org.scribble.cli.jar

export CLASSPATH FELIX_OPTIONS SCRIBBLE_HOME
