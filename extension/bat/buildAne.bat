set OUT_ANE=..\out\Location.ane
set EXT_XML=..\xmls\extension.xml
set SWC=..\temp\Location.swc

"C:\flex-415-air-22b\bin\adt" -package -target ane %OUT_ANE% %EXT_XML% -swc %SWC% -platform Android-ARM -C ..\Android-ARM .