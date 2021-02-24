@echo off
@rem set JAVA_HOME=.. 
set LOCALCLASSPATH= 
for %%i in ("..\application\lib\minuteKernel*.jar") do call lcp %%i 
for %%i in ("..\application\lib\*.jar") do call lcp %%i 
for %%i in ("..\application\lib\extra\*.jar") do call lcp %%i 
set LOCALCLASSPATH=%LOCALCLASSPATH%;../ 
@rem 
set CP=-cp %LOCALCLASSPATH%

"%JAVA_HOME%\bin\java" %CP% net.sf.minuteProject.console.ConsoleSample ../template

