@echo off

call setenv.bat

java -classpath %CLASSPATH% %FELIX_OPTIONS% org.scribble.commandline.ScribbleCL project %1 %2