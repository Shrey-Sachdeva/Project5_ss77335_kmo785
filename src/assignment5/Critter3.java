package assignment5;
/* CRITTERS Critter3.java
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
 * Critter3 - The Mountain Lion
 * Ecological Niche: Solitary, territorial, this animal is an apex predator and only stays within its territory
 * Strong, intelligent, and with the ability to amass thousands of energy, this Critter3 is the strongest critter
 * in the entire world. The Critter3 lives its entire life traversing the perimeter of a constant size territory.
 * At a specific point, the top right of the territory, the Critter3 will create an offspring to conquer the
 * territory to the right and also granting the offspring a slightly larger territory box. Biologists have
 * observed this critter in the wild to always cause Craig extinction, which average 106 energy after 100000 steps,
 * while Critter3s average 1502 energy after 100000. The amassing of energy of Critter3s is due to its unique
 * behavior of taking a very long time to produce children, yet the first generations produce children relatively
 * quickly. It is the later generations that can take over 500 steps to produce a child.
 */

public class Critter3 extends Critter {

    final int RIGHT = 0;
    final int UP = 2;
    final int LEFT = 4;
    final int DOWN = 6;
    private int right = 4;
    private int down = 4;
    private int left = 4;
    private int up = 4;

    private int rightMoves;
    private int downMoves;
    private int leftMoves;
    private int upMoves;

    public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.DARKRED; }
    
    public Critter3() {
        rightMoves = 4;
        downMoves = 4;
        leftMoves = 4;
        upMoves = 4;
    }

    public Critter3(int right, int down, int left, int up){
        this.right = right;
        this.left = left;
        this.up = up;
        this.down = down;
        rightMoves = right;
        leftMoves = left;
        upMoves = up;
        downMoves = down;
    }

    @Override
    public String toString() {
        return "3";
    }

    @Override
    // Do they move down?
    public void doTimeStep() {
        lookDuringTimeStep = true;
        if(rightMoves > 0) {
            String occupant = look(RIGHT, true);               // ??
            rightMoves--;
            walk(RIGHT);
        }
        else if(downMoves > 0) {
            if(downMoves == down) {
                Critter3 offspring  = new Critter3(right, down+1, left, up+1);
                reproduce(offspring, RIGHT);
            }
            String occupant = look(DOWN, true);                // ??
            downMoves--;
            walk(DOWN);
        }
        else if(leftMoves > 0) {
            String occupant = look(LEFT, true);                // ??
            leftMoves--;
            walk(LEFT);
        }
        else if(upMoves > 0) {
            String occupant = look(UP, true);                  // ??
            upMoves--;
            walk(UP);
        }
        else {
            rightMoves = this.right-1;
            downMoves = this.down;
            leftMoves = this.left;
            upMoves = this.up;
            String occupant = look(RIGHT, true);               // ??
            walk(RIGHT);
        }
        lookDuringTimeStep = false;
    }

    @Override
    public boolean fight(String opponent) {
        return true;
    }
    
    private int getBoxPerimeter() {
    	return right + down + left + up;
    }
    
    public static void runStats(java.util.List<Critter> threes) {
    	
    	if(threes.size() == 0) {
    		System.out.println("0 total Critter3s");
    		return;
    	}
    	int minimumBoxPerimeter = ((Critter3)threes.get(0)).getBoxPerimeter();
    	int maximumBoxPerimeter = 0;
    	int averageBoxPerimeter = 0;
    	int averageEnergy = 0;
    	
    	for(Object t : threes) {
    		Critter3 three = (Critter3) t;
    		averageBoxPerimeter += three.getBoxPerimeter();
    		if(three.getBoxPerimeter() < minimumBoxPerimeter) {
    			minimumBoxPerimeter = three.getBoxPerimeter();
    		}
    		if(three.getBoxPerimeter() > maximumBoxPerimeter) {
    			maximumBoxPerimeter = three.getBoxPerimeter();
    		}
    		averageEnergy += three.getEnergy();
    	}
    	averageBoxPerimeter /= threes.size();
    	averageEnergy /= threes.size();
    	System.out.println(threes.size() + " total Critter3s being territorial. Territory Perimeter stats:\tminimum: "+minimumBoxPerimeter+"\tmaximum: "+maximumBoxPerimeter + "\taverage: " +averageBoxPerimeter  + "\taverage energy: " + averageEnergy);
    }

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}
}
