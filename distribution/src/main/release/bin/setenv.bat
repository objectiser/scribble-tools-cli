@echo off

set DIRNAME=.\

pushd %DIRNAME%..

if "x%SCRIBBLE_HOME%" == "x" (
	set "SCRIBBLE_HOME=%CD%"
)

popd

set DIRNAME=

set FELIX_OPTIONS=-Dfelix.auto.deploy.dir=%SCRIBBLE_HOME%\bundle -Dfelix.config.properties=file:%SCRIBBLE_HOME%\conf\config.properties
set CLASSPATH=%SCRIBBLE_HOME%\lib\org.apache.felix.framework.jar
set CLASSPATH=%CLASSPATH%;%SCRIBBLE_HOME%\lib\org.apache.felix.main.jar
set CLASSPATH=%CLASSPATH%;%SCRIBBLE_HOME%\lib\org.osgi.core.jar
set CLASSPATH=%CLASSPATH%;%SCRIBBLE_HOME%\bundle\org.scribble.command.jar
set CLASSPATH=%CLASSPATH%;%SCRIBBLE_HOME%\lib\org.scribble.cli.jar

