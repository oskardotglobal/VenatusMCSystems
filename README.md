# Venatus MC Systems
All plugins that are part of Venatus' MC Systems collection

### Current Features (VUtils)
- Timer (display your time spent playing)

### Current Features (LevelBorder)
- Play the level=border project from BastiGHG on your own
- Your levels and experience are synchronized with every other player

## Building

Java 17 and Maven is required.

In order to build this project you need to first compile spigot with NMS using this command:
```shell
java -jar BuildTools.jar --rev 1.19.2 --remapped
```

Then run `mvn package` on the root project.
The build output can be found in each module in the `target` directory.

