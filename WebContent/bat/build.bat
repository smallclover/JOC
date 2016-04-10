@echo off
set javac=javac
set java=java
set suffix=.java
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_40
set classpath=.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar
set path=C:\Program Files\Java\jdk1.8.0_40\bin
::/d参数必须加否则不会调到指定的路径

set /p codePath=
cd /d %codePath%
set /p className=

%javac% %className%%suffix%
if errorlevel 1 goto error
if errorlevel 0 goto run

:run
%java% %className%

:error