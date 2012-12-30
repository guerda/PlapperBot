PlapperBot
==========

A Markov chain generator based on ICQ log files and other input files. The name comes from the German word for chatting: Plappern.

Instructions
-
1. Create a file named filelist.txt in the folder src/main/resources containing a list of absolute paths to files with the input sentences.
2. Compile the application with maven via mvn clean compile.
3. Execute Plapperbot.sh
4. ???
5. Profit!

Installation
-
Statusy uses Maven. Please install Maven for your platform. Then you can start right away with the following commands:

    mvn clean compile
    mvn exec:java -Dexec.mainClass="de.guerda.plapperbot.PlapperBot"


License
-
The Plapperbot is licensed under GPL v3 or newer. The complete license can be found under LICENSE
