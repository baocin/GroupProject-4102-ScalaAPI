ITCS 4102 Card API Project
=======================
####Group Members: Michael Pedersen, Colin Murphy, Kim Brown, James Schaffter, Khadija Almaskeen, Joao Caina 

###Mac or Linux Install
1. Download a git client:
	- Terminal Client: https://www.git-scm.com/downloads
	- GUI Client: https://www.git-scm.com/downloads/guis
2. Clone the project repository from here: 
	- https://github.com/baocin/GroupProject-4102-ScalaAPI
3. Run the following commands. 
    (Ignore the $ and > they represent the terminal prompt)
```
// Navigate into the cloned directory
$ cd GroupProject-4102-ScalaAPI
// Execute SBT (Scala Build Program) to compile everything and download necessary libraries.
$ ./sbt
// The following command tells SBT to run the API. 
> container:start
// Finally "browse" should open your default web 
> browse
```

###Windows Install
1. Download and Install [Cygwin](https://www.cygwin.com/)
2. Be sure to select during install the "git" package from the "devel" folder during install. It should show up just by searching for "git".
 ![Where Git is located in the Cygwin install](http://i.stack.imgur.com/zUg4N.png)
3. Open up a Cygwin terminal and then follow the Mac or Linux instructions above.

**If you are unable to get it working please contact us.**
**A backup will be available at <http://aoi.us.to:8080>**

###Methods
Starting from the base URL of "http://localhost:8080/

```
# Landing page of the API to explain its purpose
GET /
# Create a new Deck. The JSON of the new deck is returned along with the deck's unique ID. The trailing backslash does not matter (as denoted by the '?')
GET /deck/new/?
# Retreive the JSON of a previously created deck by putting the deck ID in the URL
GET /deck/:id/?
# To do operations on a deck, first select it by its ID, name the operation, and add parameters depending on the operation
GET /deck/:id/draw/?
# To add a card to an existing deck pass the short name of the card as a parameter (8H = Eight of Hearts, 10C = Ten of Clubs, AD = Ace of Diamonds, QS = Queen of Spades, etc)
GET /deck/:id/add/:card/?
# Remove is setup the same as add. Be sure that card exists in the deck though!
GET /deck/:id/remove/:card/?
# Jumble up the deck to prevent cheating!
GET /deck/:id/shuffle/?
# Delete all cards from the specified Deck.
GET /deck/:id/removeAll/?
# This page but on the Internet. Woah.
GET /help
```
##Code
-------------------
All project specific Scala code is inside of the cloned git repository. Specifically the path (assuming you're at the top of the cloned repository folder) is *src/main/scala/com/github/baocin/CardAPI/*. The main classes are **Card** and **Deck** contain the majority of the code while the CardServlet contains all the routing logic. The CardapiStack file is boilerplate for the api to function.

These main code files can also be directly accessed from Github by the link below:
<https://github.com/baocin/GroupProject-4102-ScalaAPI/tree/master/src/main/scala/com/github/baocin/CardAPI>
