copy ..\..\as3\lib\Location.swc ..\temp\Location.swc
copy ..\..\java\app\build\outputs\aar\app-debug.aar ..\temp\libLocation.aar


7z.exe x ..\temp\Location.swc -o..\temp\swc
7z.exe x ..\temp\libLocation.aar -o..\temp\jar


move ..\temp\jar\classes.jar ..\Android-ARM\libLocation.jar
move ..\temp\swc\library.swf ..\Android-ARM\library.swf

rd ..\temp\jar /s /q
rd ..\temp\swc /s /q