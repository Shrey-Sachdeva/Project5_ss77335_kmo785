package assignment5;
/* CRITTERS Main.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {
    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console
    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        Command command;
        while( (command = getCommand(kb)).getCommandType() != Command.CommandType.QUIT) {
            //Main loop, use info from command variable to decide what to do
            switch(command.getCommandType()) {
                case SHOW:
                    Critter.displayWorld();
                    break;
                case STEP:
                    int steps = command.getCount();
                    if(steps > 0) {//If there is a number of steps specified (steps != -1) then do that number
                        while(steps > 0) {
                            Critter.worldTimeStep();
                            steps--;
                        }
                    }else {//If there is not a specified amount, just do 1 time step
                        Critter.worldTimeStep();
                    }
                    break;
                case SEED:
                    Critter.setSeed(command.getNumber());
                    break;
                case MAKE:
                    try {
                        int number = command.getCount();
                        if(number > 0) {//if there is a specified number of critters to make
                            while(number > 0) {//make that number of critters
                                Critter.makeCritter(command.getClassName());
                                number--;
                            }
                        }else {
                            Critter.makeCritter(command.getClassName());
                        }
                    } catch (InvalidCritterException e) {
                        System.out.println("Invalid critter exception from input " + command.getClassName());
                    }
                    break;
                case STATS:
                    //Get instances
                    //Then runStats for the different classes?
                    try {
                        List<Critter> critterList = Critter.getInstances(command.getClassName());
                        Method runStatsMethod;
                        try {
                            Class<Critter> newCritter = (Class<Critter>) Class.forName(command.getClassName());
                            runStatsMethod = newCritter.getMethod("runStats", List.class);
                            runStatsMethod.invoke(newCritter, critterList);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    } catch (InvalidCritterException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
            }
        }
        /* Write your code above */
        System.out.flush();
    }


    /**
     * getCommand is the heart of the controller. This function requests user input, then processes that input
     * and initializes a Command object with the given user input. The Command object will contain CommandType.ERROR
     * if there was an error in the user input.
     * @param kb : the scanner for the keyboard
     * @return Command object with fields representing the user input
     */
    private static Command getCommand(Scanner kb) {
        ArrayList<String> input = parse(kb);
        Command retCommand = new Command(Command.CommandType.ERROR);
        if(input.size() == 0) {
            return retCommand;
        }
        String commandString = input.get(0);
        if(commandString.equals("quit")){
            if(input.size() == 1) {
                retCommand = new Command(Command.CommandType.QUIT);
            }else {
                System.out.println("error processing: " + unParse(input));
            }
            return retCommand;
        }else if(commandString.equals("show")){
            if(input.size() == 1) {
                retCommand = new Command(Command.CommandType.SHOW);
            }else {
                System.out.println("error processing: " + unParse(input));
            }
            return retCommand;
        }else if(commandString.equals("step")){ // [count]
            //First check if there is a count.
            if(input.size() == 2) {
                //There is a count
                int count = getNumberFromString(input.get(1));
                if(count <= 0) {
                    System.out.println("error processing: " + unParse(input));
                    return retCommand;
                }
                retCommand = new Command(Command.CommandType.STEP, count);
                return retCommand;
            }else if(input.size() == 1) {
                retCommand = new Command(Command.CommandType.STEP);
                return retCommand;
            }
            else {
                System.out.println("error processing: " + unParse(input));
                return retCommand;
            }
        }else if(commandString.equals("seed")){ // number
            if(input.size() == 2) {
                int number = getNumberFromString(input.get(1));
                if(number == -1) {
                    System.out.println("error processing: " + unParse(input));
                    return retCommand;
                }
                retCommand = new Command(Command.CommandType.SEED, number);
                return retCommand;
            }else{
                System.out.println("error processing: " + unParse(input));
                return retCommand;
            }
        }else if(commandString.equals("make")) { //class name, [count]
            if(input.size() == 3) {//class name and count present
                String className = "assignment4."+input.get(1);
                if(isValidClass(className)) {
                    //The class is valid
                    int count = getNumberFromString(input.get(2));
                    if(count <= 0) {
                        System.out.println("error processing: " + unParse(input));
                        return retCommand;
                    }
                    retCommand = new Command(Command.CommandType.MAKE, className , count);
                    return retCommand;
                }
                else {
                    System.out.println("error processing: " + unParse(input));
                    return retCommand;
                }
            }else if(input.size() == 2) {// just class name present
                String className = "assignment4."+input.get(1);
                if(isValidClass(className)) {
                    retCommand = new Command(Command.CommandType.MAKE, className);
                    return retCommand;
                }else {
                    System.out.println("error processing: " + unParse(input));
                }
            }else{
                System.out.println("error processing: " + unParse(input));
                return retCommand;
            }
        }else if(commandString.equals("stats")) { // class name
            if(input.size() == 2) {
                String className = "assignment4."+input.get(1);
                if(isValidClass(className)) {
                    retCommand = new Command(Command.CommandType.STATS, className);
                    return retCommand;
                }else {
                    System.out.println("error processing: " + unParse(input));
                }
            }else{
                System.out.println("error processing: " + unParse(input));
                return retCommand;
            }
        }else {
            System.out.println("invalid command: " + unParse(input));
        }
        return retCommand;
    }

    /**
     * Reads a line of input from the user and returns an array list of strings of the input split by spaces
     * @param kb
     * @return ArrayList<String>, ex: ["step", "20"]  or ["make", "Craig", "50"]
     */
    private static ArrayList<String> parse(Scanner kb) {
        ArrayList<String> input = new ArrayList<String>();
        kb.useDelimiter("\n");
        String next = kb.next();
        String[] args = next.split(" ");
        for(String arg : args) {
            input.add(arg.trim());
        }
        return input;
    }

    /**
     * Convert an arraylist back into the original string format
     * @param input ex: ["step", "craig"]
     * @return ex: "step craig"
     */
    private static String unParse(ArrayList<String> input) {
        String ret = "";
        for(String in : input) {
            ret = ret +in + " ";
        }
        return "hello";
    }

    /**
     * Checks to see if a class name is a valid class
     * @param className
     * @return true if the class name is valid
     */
    private static boolean isValidClass(String className) {
        Class c;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException | NoClassDefFoundError e1) {
            return false;
        }
        return true;
    }

    /**
     * Gets a number from the string or returns -1 if it is invalid.
     * @param numberString ex: "20" or invalid: "craig"
     * @return -1 if invalid, number if valid
     */
    private static int getNumberFromString(String numberString) {
        int number = -1;
        try {
            number = Integer.parseInt(numberString);
        }catch(NumberFormatException e) {
            return -1;
        }
        return number;
    }
}