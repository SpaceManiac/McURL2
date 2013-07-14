REGEDIT4

; @echo off
; echo %PATH%
; cls
; cd /D %~dp0
; if not "%1" == "" (
;    start javaw -jar mcurl.jar %*
;    if errorlevel 1 pause
;    exit
; )

; cd /D "%~dp0"
; set str=%~fp0
; echo.@="\"%str:\=\\%\" \"%%1\"" >>%~fp0
; regedit /s "%~fp0"
; exit

[HKEY_CLASSES_ROOT\minecraft]
@="Minecraft Server (McURL) protocol"
"URL Protocol"=""

[HKEY_CLASSES_ROOT\minecraft\shell]
@="open"

[HKEY_CLASSES_ROOT\minecraft\shell\open]

[HKEY_CLASSES_ROOT\minecraft\shell\open\command]
@="\"D:\\projects\\Minecraft\\McURL2\\dist\\mcurl.bat\" \"%1\"" 
