@echo off
@rem set JAVA_HOME=..
set CP_ROOT=%1
set LOCALCLASSPATH=
for %%i in ("%CP_ROOT%\..\..\application\lib\minuteKernel*.jar") do call lcp %%i
for %%i in ("%CP_ROOT%\..\..\application\lib\*.jar") do call lcp %%i
for %%i in ("%CP_ROOT%\..\..\application\lib\extra\*.jar") do call lcp %%i
set LOCALCLASSPATH=%LOCALCLASSPATH%;config\
@rem
echo %LOCALCLASSPATH%
set CP=-cp %LOCALCLASSPATH%

"%JAVA_HOME%\bin\java" %CP% net.sf.minuteProject.application.ModelViewGenerator %2
