#!/bin/bash

cd `dirname $0`
gconftool-2 --set --type=string /desktop/gnome/url-handlers/minecraft/command "java -jar $PWD/mcurl.jar \"%s\""
gconftool-2 --set --type=bool /desktop/gnome/url-handlers/minecraft/enabled true
gconftool-2 --set --type=bool /desktop/gnome/url-handlers/minecraft/need-terminal false
