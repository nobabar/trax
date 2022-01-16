Just a simple user interface for the board game [trax](http://www.gamerz.net/pbmserv/trax.html "rules"). This is the final project of a Java course during first year of a master's degree. 

If you don't want to import the whole project but just the `src/` file you can later compile it by running this command in your terminal :
```Bash
javac src/*.java -d bin
```

For launching you can simply open the `trax.jar` file, if it does nothing launch the `trax.bat` file or use this command in your terminal :
```Bash
java -jar trax.jar
```

If you want to generate your own .jar file from the import you will need the manifest provided in `src/META-INF` and then use the command :
```Bash
jar cvfm trax.jar "src/META-INF/MANIFEST.MF" bin/*.class
```

If you want to launch the application without the .jar file you can use the command :
```Bash
java -cp bin Game
```