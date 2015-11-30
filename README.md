# ITCS 4102 Card API Project

##### Michael Pedersen, Colin Murphy, Kimberly Brown, James Schaffter, Khadija Almaskeen, Joao Caina

### Mac or Linux Install

1.  Download the latest Java - [https://www.java.com/en/](https://www.java.com/en/)
2.  Download the files submitted on Moodle (or alternatively download the zipped git repo from  
    [https://github.com/baocin/GroupProject-4102-ScalaAPI](https://github.com/baocin/GroupProject-4102-ScalaAPI)
3.  Unzip the file into a new directory (If needed download and install [http://www.7-zip.org/](http://www.7-zip.org/))
4.  Open a Terminal and navigate into the unzipped folder  
    ("cd" to change directory, "ls" to list files)
5.  Run the following commands:  
    (Ignore the $ and > they represent the terminal prompt and SBT prompt respectively)
```
// Execute SBT (Scala Build Tool) to compile everything and download necessary libraries. This can take a while (sorry). If you get an error message complaining about "Could not reserve enough space" try "./sbt -mem 1024". If you get an error message like "Could not find or load main class {username}..sbt" then add " -sbt-dir ." to the command. When a '>' shows up, do the next command.
$ ./sbt
// Once SBT is started the following command tells it to run the API web server.
> container:start
// Finally open your web browser and go to "http://localhost:8080/"
//To rebuild the project simply press Enter and re-enter the container:start command
//Press CTRL-C at any time to stop SBT
```
### Windows Install

1.  Follow instructions 1 through 3 from the Mac/Linux Install Instructions above
2.  Open Command Prompt (search "cmd" in start menu) and navigate into the unzipped folder  
    ("cd" to change directory, "dir" to list files)
3.  Run the following commands:  
    (Ignore the $ and > they represent the terminal prompt and SBT prompt respectively)
```
// Execute SBT (Scala Build Tool). For "not enough space error" try "sbt.bat -mem 1024". For "cannot load main class" error try "sbt.bat -mem 1024 -sbt-dir ." When a '>' shows up, do the next command.
$ sbt.bat
// Once SBT is started the following command tells it to run the API web server.
> container:start
// Finally open your web browser and go to "http://localhost:8080/"
//To rebuild the project simply press Enter and re-enter the container:start command
//Press CTRL-C at any time to stop SBT
```

**If you are unable to get it working please contact us.**  
**A backup will be available at [http://aoi.us.to:8080](http://aoi.us.to:8080)**

### Methods

Starting from the base URL of [http://localhost:8080/](http://localhost:8080/)
```
# Landing page of the API
GET /
# Create a new Deck. The JSON of the new deck is returned along with the deck's unique ID. The trailing backslash is optional. To make a deck of custom cards add "?cards=" to the end of this URL and then list the short names of the cards you wish to add, separated with commas.
# Card Short Names: 8H = Eight of Hearts, 10C = Ten of Clubs, AD = Ace of Diamonds, QS = Queen of Spades, etc - see 	      http://localhost:8080/deck/0/ for examples
GET /deck/new/?
# Retreive the JSON of a previously created deck by putting the deck ID in the URL
GET /deck/:id/?
# To do operations on a deck, first select it by its ID, name the operation, and add parameters, if needed
GET /deck/:id/draw/?
# To add a card to an existing deck pass the short name of the card as a parameter.
GET /deck/:id/add/:card/?
# Remove is setup the same as add. Be sure that card exists in the deck though!
GET /deck/:id/remove/:card/?
# Delete all cards from the specified Deck.
GET /deck/:id/remove/all/?
# Jumble up the deck to prevent cheating!
GET /deck/:id/shuffle/?
# Find the probability of drawing a specific card from a given deck.
GET /deck/:id/probability/:card/?
# Install instructions and a copy of this methods list!
GET /help
```

## Code
All project specific Scala code is inside of the cloned git repository. Specifically the path (assuming you're at the top of the cloned repository folder) is _src/main/scala/com/github/baocin/CardAPI/_. The main classes are **Card** and **Deck** which contain the majority of the code while the CardServlet contains all the routing logic. The CardapiStack file is boilerplate for the api to function.

These main code files can also be directly accessed from Github by the link below:  
[https://github.com/baocin/GroupProject-4102-ScalaAPI/tree/master/src/main/scala/com/github/baocin/CardAPI](https://github.com/baocin/GroupProject-4102-ScalaAPI/tree/master/src/main/scala/com/github/baocin/CardAPI)

