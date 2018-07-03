@echo off
rmdir /S /Q replays
for /f "Delims=" %%i in ('dir /ad /b play*') do (
    cd "%%i"
	echo %%i
    java -jar ../game-runner-2.0.3-jar-with-dependencies.jar > match.log 2> error.log
    cd ..
)
cd ..\..\replay-viewer\bin\neko\bin
replay-viewer.exe