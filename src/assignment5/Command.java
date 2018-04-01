package assignment5;
/* CRITTERS Command.java
 * EE422C Project 4 submission by
 * Shrey Sachdeva
 * ss77335
 * 15455
 * Kylar Osborne
 * kmo785
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */


/**
 * The Command class is used to represent User input in an easily accessible form
 * @author Kylar
 *
 */
public class Command {
	public static enum CommandType {
    	ERROR, QUIT, SHOW, STEP, SEED, MAKE, STATS
    }
	private CommandType commandType;	//contains the type of command
	private int number = -1;			//contains the count or number field of an input
	private String className = "";		//contains the class name given in the input
	
	/**
	 * Unused constructor
	 */
	public Command() {}
	
	/**
	 * Initializes the Command object with just a type
	 * @param command
	 */
	public Command(CommandType command) {
		this.commandType = command;
	}
	
	/**
	 * Initializes command object with a type and a class name
	 * @param command
	 * @param className
	 */
	public Command(CommandType command, String className) {
		this.className = className;
		this.commandType = command;
	}
	
	/**
	 * Initializes a command object with a type and a number to represent count
	 * @param command
	 * @param number
	 */
	public Command(CommandType command, int number) {
		this.number = number;
		this.commandType = command;
	}
	
	/**
	 * Initializes a command object with a type, class name, and number
	 * @param command
	 * @param className
	 * @param number
	 */
	public Command(CommandType command, String className, int number) {
		this.number = number;
		this.className = className;
		this.commandType = command;
	}
	
	/**
	 * Returns the number from the user input
	 * @return
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * Returns the count from the user input
	 * @return
	 */
	public int getCount() {
		return this.number;
	}
	
	/**
	 * Returns the class name (string) inputted by the user
	 * @return
	 */
	public String getClassName() {
		return this.className;
	}
	
	/**
	 * Returns the type of command this is
	 * @return
	 */
	public CommandType getCommandType() {
		return this.commandType;
	}
	
	/**
	 * Returns a string representation of the command
	 */
	public String toString() {
		return "Command:: " + commandTypeToString(this.commandType) + ": "+this.className + " , " + number;
	}
	
	/**
	 * Translates the CommandType enum to a string for printing
	 * @param com
	 * @return
	 */
	private String commandTypeToString(CommandType com) {
		switch(com) {
		case ERROR:
			return "Error";
		case QUIT:
			return "Quit";
		case SHOW:
			return "Show";
		case STEP:
			return "Step";
		case SEED:
			return "Seed";
		case MAKE:
			return "Make";
		case STATS:
			return "Stats";
		default:
			return "invalid";
		}
		
	}
}