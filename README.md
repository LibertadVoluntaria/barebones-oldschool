barebones-client
================

An OldSchool RuneScape client without the bells and whistles.

The default world is 327 (my home world), but you can currently set it to whatever
you want (including the bot worlds 385 and 386) by making a shortcut to the executable, right-clicking
into Properties, and appending the world number in the Target field.
Example: If you wanted to set it to 330, and the executable was in your Downloads
directory, you'd make sure the Target field for your shortcut looks like this:
```
"C:\Users\You\Downloads\OldSchool RuneScape.exe" 330
```

You may also pass world numbers in a more casual notation, like so:
```
"C:\Users\You\Downloads\OldSchool RuneScape.exe" 30
```

You can make it load into any world you'd like just for the session by passing the world number as an argument
into your Terminal (cmd or Powershell for Windows users):
```
"OldSchool RuneScape.exe" 330
```

### Build
Build using Ant.
```
$ ant build
```

### Binaries
These binaries require Java 1.8.0 or later to be installed.

A cross-platform Java executable is available at <https://github.com/LibertadVoluntaria/barebones-oldschool/blob/master/bin/OldSchool%20RuneScape.jar?raw=true>.

A Windows Vista/7/8 executable with a nice icon is available at <https://github.com/LibertadVoluntaria/barebones-oldschool/blob/master/bin/win32-x86_32/OldSchool%20RuneScape.exe?raw=true>.

### Legalese

Copyright 2014-2015 Luis.

Barebones OldSchool is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Barebones OldSchool is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

OldSchool RuneScape is Copyright 2013-2015 Jagex Ltd. I am not
affiliated with Jagex Ltd. You may play OldSchool Runescape
here <http://oldschool.runescape.com>.

The images/logos used in this program are Copyright 2013 Jagex Ltd.
