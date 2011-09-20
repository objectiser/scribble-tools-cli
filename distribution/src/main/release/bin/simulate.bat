@echo off

call setenv.bat

java -classpath %CLASSPATH% %FELIX_OPTIONS% org.scribble.commandline.ScribbleCL simulate %1 %2