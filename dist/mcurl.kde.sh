#!/bin/bash

cd `dirname $0`

# darn kde and its different directories
if [ -d ~/.kde/ ]; then
	mkdir -p ~/.kde/share/kde4/services/
	f=~/.kde/share/kde4/services/minecraft.protocol

	echo "[Protocol]" >$f
	echo "exec=java -jar $PWD/mcurl.jar %u" >>$f
	echo "protocol=minecraft" >>$f
fi

if [ -d ~/.kde4/ ]; then
	mkdir -p ~/.kde4/share/kde4/services/
	f=~/.kde4/share/kde4/services/minecraft.protocol

	echo "[Protocol]" >$f
	echo "exec=java -jar $PWD/mcurl.jar %u" >>$f
	echo "protocol=minecraft" >>$f
fi
