package assignment5;
/* CRITTERS Critter4.java
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
 * Critter4 - The Starfish
 * Ecological Niche - Stays in one place until it is disturbed which initiates the splitting of the self into offspring
 * Starfish are creatures which hold onto their ground and possess the ability to regenerate their limbs.
 * Critter4 is very similar. Critter4 does not move: it collects any Algae that lands on it for energy.
 * When the Critter4 encounters any other critter, including Algae, it will immediately split itself into
 * as many pieces as possible in all directions as an attempt to preserve the species. Biologists are still unsure
 * of why the animal does this as a reaction to Algae, but even with this strategy the Critter4 population
 * can still average an energy of 26 after many time steps.
 */

public class Critter4 extends Critter {
	
	private int kids = 0;
	private int fights = 0;
	
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.PURPLE; }
    public Critter4() {

    }

    @Override
    public String toString() {
        return "4";
    }

    @Override
    public void doTimeStep() {

    }

    @Override
    public boolean fight(String opponent) {
    	fights++;
        while (this.getEnergy() >= Params.min_reproduce_energy) {
            Critter4 offspring = new Critter4();
            reproduce(offspring, getRandomInt(8));
            kids++;
        }
        if (opponent.equals("@")) {
            return true;
        }
        return false;
    }
    
    public static void runStats(java.util.List<Critter> fours) {
    	if(fours.size() == 0) {
    		System.out.println("0 total Critter4s");
    		return;
    	}
    	
    	int averageKids = 0;
    	int averageFights = 0;
    	double averageKidsPerFight = 0;
    	int averageEnergy = 0;
    	
    	for(Object f : fours) {
    		Critter4 four = (Critter4) f;
    		averageKids += four.kids;
    		averageFights += four.fights;
    		averageEnergy += four.getEnergy();
    		
    	}
    	averageEnergy /= fours.size();
    	averageKids /= fours.size();
    	averageFights /= fours.size();
    	averageKidsPerFight = averageKids/((double)averageFights);
    	
    	System.out.println(fours.size() + " total Critter4s birthing babies\taverage #kids: " + averageKids + "\taverage #fights: "+averageFights + "\taverage kids per fight: "+ averageKidsPerFight + "\taverage energy: "+averageEnergy);
    }

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}
}
