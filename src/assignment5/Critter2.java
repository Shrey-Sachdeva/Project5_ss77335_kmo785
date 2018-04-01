package assignment5;
/* CRITTERS Critter2.java
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
 * Critter2 - The Fungus
 * Ecological Niche: Quickly colonizing animal, spreads quickly in all directions.
 * From just a glance at the simulated behavior, this Fungus like critter seems basic in that it just quickly
 * eats Algae and spreads throughout resource rich areas. However, after years of research by elite biologists,
 * this critter was found to possess a special talent which gives it a great advantage over other fungus-like
 * critters. Critter2 will only reproduce and fight when it is within its first three timesteps as kept track
 * by an age variable. The population with this young age will always be on the outer perimeter of the fungus
 * colony, and when more inner layers reproduce and spawn overlapping babies, the energy from the inner part
 * of the colony is transferred to the outer perimeter of the colony making it especially strong when colonizing
 * new area.
 */

public class Critter2 extends Critter {
    private int age;

    public Critter2() {
        age = 0;
    }

    @Override
    public String toString() {
        return "2";
    }

    @Override
    public void doTimeStep() {
        if(age < 3) {
            Critter2 offspring = new Critter2();
            int up = getRandomInt(4);
            reproduce(offspring, up);
            offspring = new Critter2();
            int down = getRandomInt(4) + 4;
            reproduce(offspring, down);
        }
        age++;
    }

    @Override
    // If it is a new fungus, it will fight
    public boolean fight(String opponent) {
        if(age < 3) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public static void runStats(java.util.List<Critter> twos) {
    	if(twos.size() == 0) {
    		System.out.println("0 total Critter2s");
    		return;
    	}
    	int minimumAge = ((Critter2)twos.get(0)).age;
    	int maximumAge = ((Critter2)twos.get(0)).age;
    	int averageAge = 0;
    	int averageEnergy = 0;
    	for(Object t : twos) {
    		Critter2 two = (Critter2) t;
    		averageAge += two.age;
    		if(two.age < minimumAge) {
    			minimumAge = two.age;
    		}
    		if(two.age > maximumAge) {
    			maximumAge = two.age;
    		}
    		averageEnergy += two.getEnergy();
    	}
    	averageAge /= twos.size();
    	averageEnergy /= twos.size();
    	System.out.println(twos.size() + " total Critter2s\tminimum age: " + minimumAge + "\tmaximum age:" + maximumAge + "\taverage age" + averageAge + "\taverage energy: "+averageEnergy);
    	
    	
    	
    }
}
