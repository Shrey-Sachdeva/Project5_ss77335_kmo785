package assignment5;
/* CRITTERS Critter1.java
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
 * Critter1 - The Buffalo
 * Ecological Niche: Nomadic grazing animal, forms herds with its own kind and eats algae as it traverses the world
 * This unique Critter has the special property of only being able to move in one direction - right.
 * At first one may consider this a boring characteristic, however any more-than-casual observer will find
 * that from this characteristic emerges the phenomena of only traveling in groups which resemble herds.
 * The Critter1 is a very ecologically sustainable animal. As the herd travels to the right it consumes all Algae
 * in its path. However, the newly open space is quickly replenished over time as the herd takes its time to come
 * back around the torus world and eat even more dense algae.
 */

public class Critter1 extends Critter {
    private int direction;

    public Critter1() {
        direction = 0;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public void doTimeStep() {
        lookDuringTimeStep = true;
        String occupant = look(direction, true);                  // ??
    	run(direction);
    	lookDuringTimeStep = false;
        Critter1 offspring = new Critter1();
        reproduce(offspring, getRandomInt(8));
    }

    @Override
    public boolean fight(String opponent) {
       if(opponent.equals("@")) {// buffalo eat grass
           return true;
       }
        else {
           tryingToFlee = true;
           String occupant = look(4, true);                  // ??
           walk(4);//move back in the herd
           return false;
       }
    }
    
    public static void runStats(java.util.List<Critter> ones) {
    	if(ones.size() == 0) {
    		System.out.println("0 total Critter1s, how sad");
    		return;
    	}
    	
    	int averageEnergy  = 0;
    	for(Object o : ones) {
    		Critter1 one = (Critter1) o;
    		averageEnergy += one.getEnergy();
    	}
    	averageEnergy /= ones.size();
    	System.out.println(ones.size() + " total Critter1s running to the right with average energy: " + averageEnergy);
    	
    }

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return CritterShape.TRIANGLE;
	}
	
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.PURPLE; }
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLUE; }
}
