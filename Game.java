/*
 * Code written by: Parth Nimbalkar
 */

package zuul;

import java.util.ArrayList;

/*
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0 (February 2002)
 */

class Game 
{
    private Parser parser;
    private Room currentRoom;
    Room outside, theatre, pub, lab, office, onetwenty, biolab, lawclass,worldlanghall,
    wellnessroom, gym, cafeteria, lecturehall, researchlab, historysemhall, chemlab;
    
    ArrayList<Item> inventory = new ArrayList<Item>();
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }
    
    public static void main(String [] args) {
    	Game mygame = new Game();
    	mygame.play();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        onetwenty = new Room ("in the coolest place in the world.");
        worldlanghall = new Room ("in the world of languages");
        biolab = new Room ("in world of dissecting a frog!");
        lawclass = new Room ("in the room for justice and law");
        wellnessroom = new Room ("in the room to get better");
        gym = new Room ("to get a fit and strong");
        cafeteria = new Room ("get some tasty food");
        lecturehall = new Room ("listen to some boring lectures!");
        researchlab = new Room ("In progress...doing some research");
        historysemhall = new Room ("Get to know history");
        chemlab = new Room ("You can't trust atoms, they make up everything");
        
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north",onetwenty);

        // theatre exits
        theatre.setExit("west", outside);
        theatre.setExit("east", lecturehall);
        theatre.setExit("north", biolab);
        
        //biolab exits
        biolab.setExit("north", chemlab);
        biolab.setExit("south", theatre);
        biolab.setExit("east", lawclass);
        biolab.setExit("west", onetwenty);
        
        //law class exits
        lawclass.setExit("south", lecturehall);
        lawclass.setExit("west", biolab);

        // lecture hall exits
        lecturehall.setExit("north", lawclass);
        lecturehall.setExit("west", theatre);
        lecturehall.setExit("east", researchlab);
        
        //research lab exits
        researchlab.setExit("west", lecturehall);
        researchlab.setExit("south", historysemhall);
        
        //history seminar hall exits
        historysemhall.setExit("north", lecturehall);
        
        //chem lab exits
        chemlab.setExit("south", biolab);
        
        //pub exits
        pub.setExit("east", outside);
        pub.setExit("north", worldlanghall);
        pub.setExit("south", cafeteria);
        
        //cafeteria exits
        cafeteria.setExit("north", pub);
        cafeteria.setExit("west", gym);
        cafeteria.setExit("east", lab);
        
        //wellness room exits
        wellnessroom.setExit("east", worldlanghall);

        // lab exits
        lab.setExit("north", outside);
        lab.setExit("west", cafeteria);

        office.setExit("west", lab);
        
        //onetwenty exits
        onetwenty.setExit("south", outside);
        onetwenty.setExit("west", worldlanghall);
        onetwenty.setExit("east", biolab);
        
        //gym exits
        gym.setExit("east", cafeteria);
        
        // world languages hall exits
        worldlanghall.setExit("south", pub);
        worldlanghall.setExit("west", wellnessroom);
        worldlanghall.setExit("east", onetwenty);
        

        currentRoom = outside;  // start game outside
        
        inventory.add(new Item ("Computer"));
        onetwenty.setItem(new Item("Robot"));
        theatre.setItem(new Item("Projector"));
        wellnessroom.setItem(new Item("Stress Ball"));
        chemlab.setItem(new Item("Flask"));
        pub.setItem(new Item("Glass"));
        biolab.setItem(new Item("Frog"));
        worldlanghall.setItem(new Item("French Textbook"));
        lawclass.setItem(new Item("Law pad"));
        researchlab.setItem(new Item("Micro scope"));
        historysemhall.setItem(new Item("Biography"));
        lecturehall.setItem(new Item("Microphone"));
        lab.setItem(new Item("Weight scale"));
        cafeteria.setItem(new Item("Chocolate milk"));
        gym.setItem(new Item("Basketball"));
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Adventure!");
        System.out.println("Adventure is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
        	printHelp();
        }
        else if (commandWord.equals("go")) {
        	wantToQuit = goRoom(command);
        }  
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("inventory")) {
        	printInventory();
        }
        else if(commandWord.equals("get")) {
        	getItem(command);
        }
        else if(commandWord.equals("drop")) {
        	dropItem(command);
        }
        return wantToQuit;
    }
    private void dropItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }

        String Item = command.getSecondWord();

        // Try to leave current room.
        Item newItem = null;
        int index = 0;
        for (int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).getDescription().equals(Item)) {
				newItem = inventory.get(i);
				index = i;
			}
		}

        if (newItem == null) {
        	System.out.println("That item is not in your inventory!");
        }
        else {
            inventory.remove(index);
            currentRoom.setItem(new Item(Item));
            System.out.println("Dropped:" + Item);
        }
    }
    
    
    private void getItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Get what?");
            return;
        }

        String Item = command.getSecondWord();

        // Try to leave current room.
        Item newItem = currentRoom.getItem(Item);

        if (newItem == null) {
        	System.out.println("The item is not here!");
        }
        else {
            inventory.add(newItem);
            currentRoom.removeItem(Item);
            System.out.println("Picked up:" + Item);
        }
    }
    
    private void printInventory() {
    	String output = "";
    	for (int i = 0; i < inventory.size(); i++) {
			output += inventory.get(i).getDescription()+ " ";
		}
    	System.out.println("You are carrying:");
    	System.out.println(output);
	}

	// implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        

        if (nextRoom == null)
            System.out.println("There is no door!");
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            if (currentRoom == cafeteria && winItem("Frog")) {
            	System.out.println("You win!");
            	return true;
            }
        }
        return false;
    }
    
    boolean winItem(String winItem) {
    	for (int a = 0; a < inventory.size(); a++) {
    		if (winItem.equals(inventory.get(a).getDescription())== true) {
    			return true;
    		}
		}
    	return false;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game. Return true, if this command
     * quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else
            return true;  // signal that we want to quit
    }
}
