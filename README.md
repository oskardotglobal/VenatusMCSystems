# Venatus MC Systems
All plugins that are part of Venatus' MC Systems collection

## Current Features

#### VUtils
- Timer (display your time spent playing)

#### LevelBorder
- Play the level=border project by BastiGHG on your own
- Your levels and experience are synchronized with every other player

#### RandomMLG (WIP)
- Play the MLG challenge by BastiGHG on your own

## Building
Java 17 and Maven is required. <br>
In order to build this project you need to first compile spigot with NMS using this command:
```shell
java -jar BuildTools.jar --rev 1.19.2 --remapped
```
Then run `mvn package` on the root project.  The build output can be found in each module in the `target` directory.

## Contributors
Forked & updated to 1.19.2 by [oskardotglobal](https://github.com/oskardotglobal)