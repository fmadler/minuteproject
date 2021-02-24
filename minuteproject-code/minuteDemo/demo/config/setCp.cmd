set LOCALCLASSPATH=
for %%i in ("..\..\application\lib\*.jar") do call "lcp.cmd" %%i
for %%i in ("..\..\application\lib\extra\*.jar") do call "lcp.cmd" %%i
set LOCALCLASSPATH=%LOCALCLASSPATH%;config\
@rem
echo %LOCALCLASSPATH%
set CP=-cp %LOCALCLASSPATH%