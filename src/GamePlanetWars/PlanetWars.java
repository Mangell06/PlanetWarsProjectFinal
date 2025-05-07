package GamePlanetWars;

import java.util.ArrayList;
import java.util.Random;

public class PlanetWars {

	public static void main(String[] args) {

	}
}

class Planet {
	private int technologyDefense;
	private int technologyAttack;
	private int metal;
	private int deuterium;
	private int upgradeDefenseTechnologyDeuteriumCost;
	private int upgradeAttackTechnologyDeuteriumCost;
	private ArrayList<MilitaryUnit>[] army;
	
	@SuppressWarnings("unchecked")
	public Planet(int technologyDefense, int technologyAtack, int metal, int deuterium,
			int upgradeDefenseTechnologyDeuteriumCost, int upgradeAttackTechnologyDeuteriumCost) {
		super();
		this.technologyDefense = technologyDefense;
		this.technologyAttack = technologyAtack;
		this.metal = metal;
		this.deuterium = deuterium;
		this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
		this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
		this.army = (ArrayList<MilitaryUnit>[]) new ArrayList[7];
		for (int i = 0; i < army.length; i++) {
		    army[i] = new ArrayList<>();
		}
	}
	
	public int getTechnologyDefense() {
		return technologyDefense;
	}

	public void setTechnologyDefense(int technologyDefense) {
		this.technologyDefense = technologyDefense;
	}

	public int getTechnologyAtack() {
		return technologyAttack;
	}

	public void setTechnologyAtack(int technologyAtack) {
		this.technologyAttack = technologyAtack;
	}

	public int getMetal() {
		return metal;
	}

	public void setMetal(int metal) {
		this.metal = metal;
	}

	public int getDeuterium() {
		return deuterium;
	}

	public void setDeuterium(int deuterium) {
		this.deuterium = deuterium;
	}

	public int getUpgradeDefenseTechnologyDeuteriumCost() {
		return upgradeDefenseTechnologyDeuteriumCost;
	}

	public void setUpgradeDefenseTechnologyDeuteriumCost(int upgradeDefenseTechnologyDeuteriumCost) {
		this.upgradeDefenseTechnologyDeuteriumCost = upgradeDefenseTechnologyDeuteriumCost;
	}

	public int getUpgradeAttackTechnologyDeuteriumCost() {
		return upgradeAttackTechnologyDeuteriumCost;
	}

	public void setUpgradeAttackTechnologyDeuteriumCost(int upgradeAttackTechnologyDeuteriumCost) {
		this.upgradeAttackTechnologyDeuteriumCost = upgradeAttackTechnologyDeuteriumCost;
	}

	public ArrayList<MilitaryUnit>[] getArmy() {
		return army;
	}

	public void setArmy(ArrayList<MilitaryUnit>[] army) {
		this.army = army;
	}

	public void upgradeTechnologyDefense() throws ResourceException {
		try {
			if (upgradeDefenseTechnologyDeuteriumCost > deuterium) {
				throw new ResourceException("You don't have enough deuterium to upgrade defense technology.");
			}
			deuterium -= upgradeDefenseTechnologyDeuteriumCost;
			technologyDefense += 1;
			upgradeDefenseTechnologyDeuteriumCost *= 1.1;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
	}
	

	public void upgradeTechnologyAttack() {
		try {
			if (upgradeAttackTechnologyDeuteriumCost > deuterium) {
				throw new ResourceException("You don't have enough deuterium to upgrade attack technology.");
			}
			deuterium -= upgradeAttackTechnologyDeuteriumCost;
			technologyAttack += 1;
			upgradeAttackTechnologyDeuteriumCost *= 1.1;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void newLightHunter(int n) {
		for (int i = 0; i <= n; i++) {
			try {
				if (Variables.METAL_COST_LIGTHHUNTER > metal || Variables.DEUTERIUM_COST_LIGTHHUNTER > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
			  army[0].add(new LigthHunter(Variables.ARMOR_LIGTHHUNTER + ((technologyDefense * Variables.PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY) % 1000) ,Variables.BASE_DAMAGE_LIGTHHUNTER + ((technologyAttack*Variables.PLUS_ATTACK_LIGTHHUNTER_BY_TECHNOLOGY)%1000)));
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newHeavyHunter(int n) {
		for (int i = 0; i <= n; i++) {
			try {
				if (Variables.METAL_COST_HEAVYHUNTER > metal || Variables.DEUTERIUM_COST_HEAVYHUNTER > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
			  army[1].add(new HeavyHunter(Variables.ARMOR_HEAVYHUNTER + ((technologyDefense*Variables.PLUS_ARMOR_HEAVYHUNTER_BY_TECHNOLOGY)%1000),Variables.BASE_DAMAGE_HEAVYHUNTER + ((technologyAttack*Variables.PLUS_ATTACK_HEAVYHUNTER_BY_TECHNOLOGY)%1000)));
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newBattleShip(int n) {
		for (int i = 0; i <= n; i++) {
			try {
				if (Variables.METAL_COST_BATTLESHIP > metal || Variables.DEUTERIUM_COST_BATTLESHIP > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
			  army[2].add(new BattleShip(Variables.ARMOR_BATTLESHIP + ((technologyDefense*Variables.PLUS_ARMOR_BATTLESHIP_BY_TECHNOLOGY)%1000),Variables.BASE_DAMAGE_BATTLESHIP + ((technologyAttack*Variables.PLUS_ATTACK_BATTLESHIP_BY_TECHNOLOGY)%1000)));
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newArmoredShip(int n) {
		for (int i = 0; i <= n; i++) {
			try {
				if (Variables.METAL_COST_ARMOREDSHIP > metal || Variables.DEUTERIUM_COST_ARMOREDSHIP > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
			  army[3].add(new ArmoredShip(Variables.ARMOR_ARMOREDSHIP + ((technologyDefense*Variables.PLUS_ARMOR_ARMOREDSHIP_BY_TECHNOLOGY)%1000),Variables.BASE_DAMAGE_ARMOREDSHIP + ((technologyAttack*Variables.PLUS_ATTACK_ARMOREDSHIP_BY_TECHNOLOGY)%1000)));
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newMissileLauncher(int n) {
		for (int i = 0; i <= n; i++) {
			try {
				if (Variables.METAL_COST_MISSILELAUNCHER > metal || Variables.DEUTERIUM_COST_MISSILELAUNCHER > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
			  army[4].add(new MissileLauncher(Variables.ARMOR_MISSILELAUNCHER + ((technologyDefense * Variables.PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY) % 1000), Variables.BASE_DAMAGE_MISSILELAUNCHER + ((technologyAttack * Variables.PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY) % 1000)));
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newIonCannon(int n) {
		for (int i = 0; i <= n; i++) {
			try {
				if (Variables.METAL_COST_IONCANNON > metal || Variables.DEUTERIUM_COST_IONCANNON > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
			  army[5].add(new IonCannon(Variables.ARMOR_IONCANNON + ((technologyDefense * Variables.PLUS_ARMOR_IONCANNON_BY_TECHNOLOGY) % 1000), Variables.BASE_DAMAGE_IONCANNON + ((technologyAttack * Variables.PLUS_ATTACK_IONCANNON_BY_TECHNOLOGY) % 1000)));
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void newPlasmaCannon(int n) {
		for (int i = 0; i <= n; i++) {
			try {
				if (Variables.METAL_COST_PLASMACANNON > metal || Variables.DEUTERIUM_COST_PLASMACANNON > deuterium) {
					throw new ResourceException("You don't have enough resource");
				}
			  army[6].add(new PlasmaCannon(Variables.ARMOR_PLASMACANNON + ((technologyDefense * Variables.PLUS_ARMOR_PLASMACANNON_BY_TECHNOLOGY) % 1000), Variables.BASE_DAMAGE_PLASMACANNON + ((technologyAttack * Variables.PLUS_ATTACK_PLASMACANNON_BY_TECHNOLOGY) % 1000)));
			} catch (ResourceException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public void printStats() {
        String mostrar = "Planet Stats:\n";
        mostrar += "\nTECHNOLOGY\n";
        mostrar += String.format("\n%-30s%d", "Attack Technology", technologyAttack);
        mostrar += String.format("\n%-30s%d\n", "Defense Technology", technologyDefense);
        mostrar += "\nDEFENSES\n";
        mostrar += String.format("\n%-30s%d", "Missile Launcher", army[4].size());
        mostrar += String.format("\n%-30s%d", "Ion Cannon", army[5].size());
        mostrar += String.format("\n%-30s%d", "Plasma Cannon", army[6].size());
        mostrar += "\nFLEET\n";
        mostrar += String.format("\n%-30s%d", "Light Hunter", army[0].size());
        mostrar += String.format("\n%-30s%d", "Heavy Hunter", army[1].size());
        mostrar += String.format("\n%-30s%d", "Battle Ship", army[2].size());
        mostrar += String.format("\n%-30s%d", "Armord Ship", army[3].size());
        mostrar += "\nRESOURCES\n";
        mostrar += String.format("\n%-30s%d", "Metal", metal);
        mostrar += String.format("\n%-30s%d", "Deuterium", deuterium);
        System.out.println(mostrar);
    }
}

class ResourceException extends Exception {
	public ResourceException(String mensaje) {
		super(mensaje);
	}
}

abstract class ship implements MilitaryUnit, Variables{
	private int armor;
	private int initialArmor;
	private int baseDamage;
	
	public ship(int armor, int baseDamage) {
		super();
		this.armor = armor;
		this.initialArmor = armor;
		this.baseDamage = baseDamage;
	}

	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}

	public int getInitialArmor() {
		return initialArmor;
	}
	public void setInitialArmor(int initialArmor) {
		this.initialArmor = initialArmor;
	}

	public int getBaseDamage() {
		return baseDamage;
	}
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
}

interface MilitaryUnit {
	abstract int attack();
	abstract void tekeDamage(int receivedDamage);
	abstract int getActualArmor();
	abstract int getMetalCost();
	abstract int getDeuteriumCost();
	abstract int getChanceGeneratinWaste();
	abstract int getChanceAttackAgain();
	abstract void resetArmor();
}

interface Variables {
	// resources available to create the first enemy fleet
	public final int DEUTERIUM_BASE_ENEMY_ARMY = 26000;
	public final int METAL_BASE_ENEMY_ARMY = 180000;
	// percentage increase of resources available to create enemy fleet
	public final int ENEMY_FLEET_INCREASE = 6;
	// resources increment every minute
	public final int PLANET_DEUTERIUM_GENERATED = 1500;
	public final int PLANET_METAL_GENERATED = 5000;
	// TECHNOLOGY COST
	public final int UPGRADE_BASE_DEFENSE_TECHNOLOGY_DEUTERIUM_COST = 2000;
	public final int UPGRADE_BASE_ATTACK_TECHNOLOGY_DEUTERIUM_COST = 2000;
	public final int UPGRADE_PLUS_DEFENSE_TECHNOLOGY_DEUTERIUM_COST = 60;
	public final int UPGRADE_PLUS_ATTACK_TECHNOLOGY_DEUTERIUM_COST = 60;
	// COST SHIPS
	public final int METAL_COST_LIGTHHUNTER = 3000;
	public final int METAL_COST_HEAVYHUNTER = 6500;
	public final int METAL_COST_BATTLESHIP = 45000;
	public final int METAL_COST_ARMOREDSHIP = 30000;
	public final int DEUTERIUM_COST_LIGTHHUNTER = 50;
	public final int DEUTERIUM_COST_HEAVYHUNTER = 50;
	public final int DEUTERIUM_COST_BATTLESHIP = 7000;
	public final int DEUTERIUM_COST_ARMOREDSHIP = 15000;
	// COST DEFENSES
	public final int DEUTERIUM_COST_MISSILELAUNCHER = 0;
	public final int DEUTERIUM_COST_IONCANNON = 500;
	public final int DEUTERIUM_COST_PLASMACANNON = 5000;
	public final int METAL_COST_MISSILELAUNCHER = 2000;
	public final int METAL_COST_IONCANNON = 4000;
	public final int METAL_COST_PLASMACANNON = 50000;
	// array units costs
	public final int[] METAL_COST_UNITS =
	{METAL_COST_LIGTHHUNTER,METAL_COST_HEAVYHUNTER,METAL_COST_BATTLESHIP,METAL_COST_ARMOREDSHIP,METAL_COST_MISSILELAUNCHER,METAL_COST_IONCANNON,METAL_COST_PLASMACANNON};
	public final int[] DEUTERIUM_COST_UNITS = {DEUTERIUM_COST_LIGTHHUNTER,DEUTERIUM_COST_HEAVYHUNTER,DEUTERIUM_COST_BATTLESHIP,DEUTERIUM_COST_ARMOREDSHIP,DEUTERIUM_COST_MISSILELAUNCHER,DEUTERIUM_COST_IONCANNON,DEUTERIUM_COST_PLASMACANNON};
	// BASE DAMAGE SHIPS
	public final int BASE_DAMAGE_LIGTHHUNTER = 80;
	public final int BASE_DAMAGE_HEAVYHUNTER = 150;
	public final int BASE_DAMAGE_BATTLESHIP = 1000;
	public final int BASE_DAMAGE_ARMOREDSHIP = 700;
	// BASE DAMAGE DEFENSES
	public final int BASE_DAMAGE_MISSILELAUNCHER = 80;
	public final int BASE_DAMAGE_IONCANNON = 250;
	public final int BASE_DAMAGE_PLASMACANNON = 2000;
	// REDUCTION_DEFENSE
	public final int REDUCTION_DEFENSE_IONCANNON = 100;
	// ARMOR SHIPS
	public final int ARMOR_LIGTHHUNTER = 400;
	public final int ARMOR_HEAVYHUNTER = 1000;
	public final int ARMOR_BATTLESHIP = 6000;
	public final int ARMOR_ARMOREDSHIP = 8000;
	// ARMOR DEFENSES
	public final int ARMOR_MISSILELAUNCHER = 200;
	public final int ARMOR_IONCANNON = 1200;
	public final int ARMOR_PLASMACANNON = 7000;
	//fleet armor increase percentage per tech level
	public final int PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_HEAVYHUNTER_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_BATTLESHIP_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_ARMOREDSHIP_BY_TECHNOLOGY = 5;
	// defense armor increase percentage per tech level
	public final int PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_IONCANNON_BY_TECHNOLOGY = 5;
	public final int PLUS_ARMOR_PLASMACANNON_BY_TECHNOLOGY = 5;
	// fleet attack power increase percentage per tech level
	public final int PLUS_ATTACK_LIGTHHUNTER_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_HEAVYHUNTER_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_BATTLESHIP_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_ARMOREDSHIP_BY_TECHNOLOGY = 5;
	// Defense attack power increase percentage per tech level
	public final int PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_IONCANNON_BY_TECHNOLOGY = 5;
	public final int PLUS_ATTACK_PLASMACANNON_BY_TECHNOLOGY = 5;
	// fleet probability of generating waste
	public final int CHANCE_GENERATNG_WASTE_LIGTHHUNTER = 55;
	public final int CHANCE_GENERATNG_WASTE_HEAVYHUNTER = 65;
	public final int CHANCE_GENERATNG_WASTE_BATTLESHIP = 80;
	public final int CHANCE_GENERATNG_WASTE_ARMOREDSHIP = 90;
	// Defense probability of generating waste
	public final int CHANCE_GENERATNG_WASTE_MISSILELAUNCHER = 55;
	public final int CHANCE_GENERATNG_WASTE_IONCANNON = 65;
	public final int CHANCE_GENERATNG_WASTE_PLASMACANNON = 75;
	// fleet chance to attack again
	public final int CHANCE_ATTACK_AGAIN_LIGTHHUNTER = 3;
	public final int CHANCE_ATTACK_AGAIN_HEAVYHUNTER = 7;
	public final int CHANCE_ATTACK_AGAIN_BATTLESHIP = 45;
	public final int CHANCE_ATTACK_AGAIN_ARMOREDSHIP = 70;
	//Defense chance to attack again
	public final int CHANCE_ATTACK_AGAIN_MISSILELAUNCHER = 5;
	public final int CHANCE_ATTACK_AGAIN_IONCANNON = 12;
	public final int CHANCE_ATTACK_AGAIN_PLASMACANNON = 30;
	// CHANCE ATTACK EVERY UNIT
	// LIGTHHUNTER, HEAVYHUNTER, BATTLESHIP, ARMOREDSHIP, MISSILELAUNCHER, IONCANNON, PLASMACANNON
	public final int[] CHANCE_ATTACK_PLANET_UNITS = {5,10,15,40,5,10,15};
	// LIGTHHUNTER, HEAVYHUNTER, BATTLESHIP, ARMOREDSHIP
	public final int[] CHANCE_ATTACK_ENEMY_UNITS = {10,20,30,40};
	// percentage of waste that will be generated with respect to the cost of the units
	public final int PERCENTATGE_WASTE = 70;
	public final int MIN_PERCENTAGE_TO_WIN = 20;
}

class LigthHunter extends ship {

	public LigthHunter(int armor, int baseDamage) {
		super(armor,baseDamage);
	}
	
	public LigthHunter() {
		super(ARMOR_LIGTHHUNTER,BASE_DAMAGE_LIGTHHUNTER);
	}

	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_LIGTHHUNTER;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_LIGTHHUNTER;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_LIGTHHUNTER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_LIGTHHUNTER;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
}

class HeavyHunter extends ship {

	public HeavyHunter(int armor, int baseDamage) {
		super(armor, baseDamage);
	}
	
	public HeavyHunter() {
		super(ARMOR_HEAVYHUNTER,BASE_DAMAGE_HEAVYHUNTER);
	}
	
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_HEAVYHUNTER;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_HEAVYHUNTER;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_HEAVYHUNTER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_HEAVYHUNTER;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
	
}

class BattleShip extends ship {

	public BattleShip(int armor, int baseDamage) {
		super(armor, baseDamage);
	}
	
	public BattleShip() {
		super(ARMOR_BATTLESHIP,BASE_DAMAGE_BATTLESHIP);
	}
	
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_BATTLESHIP;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_BATTLESHIP;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_BATTLESHIP;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_BATTLESHIP;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
	
}

class ArmoredShip extends ship {

	public ArmoredShip(int armor, int baseDamage) {
		super(armor, baseDamage);
	}
	
	public ArmoredShip() {
		super(ARMOR_ARMOREDSHIP,BASE_DAMAGE_ARMOREDSHIP);
	}
	
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_ARMOREDSHIP;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_ARMOREDSHIP;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_ARMOREDSHIP;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_ARMOREDSHIP;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
}

abstract class Defense implements MilitaryUnit,  Variables{
	private int armor;
	private int initialArmor;
	private int baseDamage;
	
	public Defense(int armor, int baseDamage) {
		super();
		this.armor = armor;
		this.initialArmor = armor;
		this.baseDamage = baseDamage;
	}
	
	public int getArmor() {
		return armor;
	}
	public void setArmor(int armor) {
		this.armor = armor;
	}
	
	public int getInitialArmor() {
		return initialArmor;
	}
	public void setInitialArmor(int initialArmor) {
		this.initialArmor = initialArmor;
	}
	
	public int getBaseDamage() {
		return baseDamage;
	}
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
}

class MissileLauncher extends Defense {
	
	public MissileLauncher(int armor, int baseDamage) {
		super(armor, baseDamage);
	}
	
	// === METODOS INTERFAZ ===
	public int attack() {
		return getBaseDamage();
	}
	
	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}
	
	public int getActualArmor() {
		return getArmor();
	}
	
	public int getMetalCost() {
		return METAL_COST_MISSILELAUNCHER;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_MISSILELAUNCHER;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_MISSILELAUNCHER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_MISSILELAUNCHER;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
}

class IonCannon extends Defense {
	
	public IonCannon(int armor, int baseDamage) {
		super(armor, baseDamage);
	}
	
	// === METODOS INTERFAZ ===
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_IONCANNON;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_IONCANNON;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_IONCANNON;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_IONCANNON;
	}

	public void resetArmor() {
		setArmor(getInitialArmor());
	}
}

class PlasmaCannon extends Defense {

	public PlasmaCannon(int armor, int baseDamage) {
		super(armor, baseDamage);
	}

	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {	
		setArmor(Math.max(0, getArmor() - receivedDamage));
	}
  
  public int getActualArmor() {
			return getArmor();
	}
  
  public int getMetalCost() {
			return METAL_COST_PLASMACANNON;
	}

  public int getDeuteriumCost() {
    return DEUTERIUM_COST_PLASMACANNON;
  }

  public int getChanceGeneratinWaste() {
    return CHANCE_GENERATNG_WASTE_PLASMACANNON;
  }

  public int getChanceAttackAgain() {
    return CHANCE_ATTACK_AGAIN_PLASMACANNON;
  }

  public void resetArmor() {
    setArmor(getInitialArmor());
  }
	
}

class Battle implements Variables {
	private ArrayList<MilitaryUnit>[] planetArmy, enemyArmy;
	private ArrayList[][] armies;
	private String battleDevelopment;
	private int[][] initialCostFleet, resourcesLooses, initialArmies;
	private int initialNumberUnitsPlanet, initialNumberUnitsEnemy;
	private int[] wasteMetalDeuterium, enemyDrops, planetDrops, actualNumberUnitsPlanet, actualNumberUnitsEnemy;
	private Random rand;
	
	public Battle(ArrayList<MilitaryUnit>[] planetArmy, ArrayList<MilitaryUnit>[] enemyArmy) {
	    this.planetArmy = planetArmy;
	    this.enemyArmy = enemyArmy;
	    this.armies = new ArrayList[2][7];
	    armies[0] = planetArmy;
	    armies[1] = enemyArmy;
	    this.battleDevelopment = "";
	    this.initialNumberUnitsPlanet = initialFleetNumber(planetArmy);
	    this.initialNumberUnitsEnemy = initialFleetNumber(enemyArmy);
	    this.initialCostFleet = new int[2][2];
	    this.initialCostFleet[0] = fleetResourceCost(planetArmy);
	    this.initialCostFleet[1] = fleetResourceCost(enemyArmy);
	    this.initialNumberUnitsPlanet = initialFleetNumber(planetArmy);
	    this.initialNumberUnitsEnemy = initialFleetNumber(enemyArmy);
	    this.wasteMetalDeuterium = new int[2];
	    this.planetDrops = new int[2];
	    this.resourcesLooses = new int[2][2];
	    this.rand = new Random();
	    
	}
	
	public String getBattleReport(int battles) {
		String mostrar = "";
		String[] unitNames = {"Light Hunter","Heavy Hunter","Battle Ship","Armored Ship","Missile Launcher","Ion Cannon","Plasma Cannon"};
		mostrar += "BATTLE NUMBER: " + battles;
		mostrar += "\nBATTLE STATISTICS\n";
		mostrar += String.format("\n%15s%-5s%-5s%-5s%-5s%-5s", "Initial Army Planet", "Units", "Drops", "Initial Army Enemy", "Units", "Drops");
	    for (int i = 0; i < 7; i++) {
	        int initialPlanet = initialArmies[0][i];
	        int actualPlanet = actualNumberUnitsPlanet[i];
	        int dropsPlanet = initialPlanet - actualPlanet;
	        int initialEnemy = initialArmies[1][i];
	        int actualEnemy = actualNumberUnitsEnemy[i];
	        int dropsEnemy = initialEnemy - actualEnemy;
	        mostrar += String.format("\n%-25s%-10d%-10d%-25s%-10d%-10d",unitNames[i], initialPlanet, dropsPlanet,unitNames[i], initialEnemy, dropsEnemy);
	    }
	    mostrar += "\n\nRESOURCES LOSSES:";
	    mostrar += String.format("\n%-25s%-10s%-10s", "", "Metal", "Deuterium");
	    mostrar += String.format("\n%-25s%-10d%-10d", "Planet", resourcesLooses[0][0], resourcesLooses[0][1]);
	    mostrar += String.format("\n%-25s%-10d%-10d", "Enemy", resourcesLooses[1][0], resourcesLooses[1][1]);
	    mostrar += "\n\nWASTE GENERATED:";
	    mostrar += String.format("\n%-25s%-10d%-10d", "Total Waste", wasteMetalDeuterium[0], wasteMetalDeuterium[1]);
	    mostrar += String.format("\n%-25s%-10d%-10d", "Planet Won", planetDrops[0], planetDrops[1]);
	    mostrar += String.format("\n%-25s%-10d%-10d", "Enemy Won", enemyDrops[0], enemyDrops[1]);
	    int totalPlanetLoss = resourcesLooses[0][0] + resourcesLooses[0][1];
	    int totalEnemyLoss = resourcesLooses[1][0] + resourcesLooses[1][1];
	    String winner = (totalPlanetLoss < totalEnemyLoss) ? "Planet" : "Enemy";
	    mostrar += "\n\nWINNER: " + winner;
		return mostrar;
	}
	
	public String getBattleDevelopment() {
		return battleDevelopment;
	}
	public void initInitialArmies() {
	    this.initialArmies = new int[2][7];
		for (int i = 0; i < initialArmies[0].length; i++) {
			initialArmies[0][i] = planetArmy[i].size();
			initialArmies[1][i] = enemyArmy[i].size();
		}
	}
	
	public int[] countArmyforType(ArrayList<MilitaryUnit>[] army) {
		int[] count = new int[army.length];
		for (int i = 0; i < army.length; i++) {
			count[i] = army[i].size();
		}
		return count;
	}
	
	public void updateResourcesLoose() {
		int[][] coste_recursos_actuales = new int[2][3];
		coste_recursos_actuales[0] = fleetResourceCost(planetArmy);
		coste_recursos_actuales[1] = fleetResourceCost(enemyArmy);
		resourcesLooses[0][0] = coste_recursos_actuales[0][0] - initialCostFleet[0][0];
		resourcesLooses[0][1] = coste_recursos_actuales[0][1] - initialCostFleet[0][1];
		resourcesLooses[1][0] = coste_recursos_actuales[1][0] - initialCostFleet[1][0];
		resourcesLooses[1][1] = coste_recursos_actuales[1][1] - initialCostFleet[1][1];
		resourcesLooses[0][2] = resourcesLooses[0][0] + 5 * resourcesLooses[0][1];
		resourcesLooses[1][2] = resourcesLooses[1][0] + 5 * resourcesLooses[1][1];
	}
	
	public int[] fleetResourceCost(ArrayList<MilitaryUnit>[] army) {
	    int[] costs = new int[2];
	    costs[0] = 0;
	    costs[1] = 0;
	    for (int i = 0; i < army.length; i++) {
	        for (MilitaryUnit unit : army[i]) {
	            costs[0] += unit.getMetalCost();
	            costs[1] += unit.getDeuteriumCost();
	        }
	    }
	    return costs;
	}
	
	public int initialFleetNumber(ArrayList<MilitaryUnit>[] army) {
		int countfleet = 0;
		for (int i = 0; i < army.length; i++ ) {
			countfleet += army[i].size();
		}
		
		return countfleet;
	}
	
	public void resetArmyArmor() {
		for (int i = 0; i < planetArmy.length; i++) {
			for (int j = 0; j < planetArmy[i].size(); j++) {
				planetArmy[i].get(j).resetArmor();
			}
		}
	}
	
	public void updateactualunits() {
		actualNumberUnitsEnemy = countArmyforType(enemyArmy);
		actualNumberUnitsPlanet = countArmyforType(planetArmy);
	}
	
	public int remainderPercentatgeFleet(ArrayList<MilitaryUnit>[] army) {
		int total = 0;
		for (ArrayList<MilitaryUnit> group : army) {
			total += group.size();
		}
		
		int initialUnits = 0;
		
		try {
			if (army == planetArmy) {
				initialUnits = initialNumberUnitsPlanet;
			} else if (army == enemyArmy) {
				initialUnits = initialNumberUnitsEnemy;
			}
			return (total * 100) / initialUnits;
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public int getGroupDefender(ArrayList<MilitaryUnit>[] army) {
		int total = 0;
		for (ArrayList<MilitaryUnit> group : army) {
			total += group.size(); // Calculo de las unidades totales
		}
		
		int initialUnits;
		// Comprobaciones para saber el tipo de unidades que calculamos
		if (army == planetArmy) {
			initialUnits = initialNumberUnitsPlanet;
		} else if (army == enemyArmy) {
			initialUnits = initialNumberUnitsEnemy;
		} else {
			throw new IllegalArgumentException("Unknown army reference passed.");
		}
		
		int totalUnits = initialUnits;
		if (totalUnits <= 0) {
			return -1; // Ya no tiene unidades para defenderse
		}
		
		int random = rand.nextInt(totalUnits);
		int cumulative = 0;
		
		for (int i = 0; i < army.length; i++) {
			cumulative += army[i].size();
			if (random < cumulative) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int getPlanetGroupAttacker() {
		int[] probabilities = Variables.CHANCE_ATTACK_PLANET_UNITS;
		int total = 0;
		for (int probability : probabilities) {
			total += probability;
		}
		
		int random = rand.nextInt(total);
		int cumulative = 0;
		
		for (int i = 0; i < probabilities.length; i++) {
			cumulative += probabilities[i];
			if (random < cumulative) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int getEnemyGroupAttacker() {
		int[] probabilities = Variables.CHANCE_ATTACK_ENEMY_UNITS;
		int total = 0;
		for (int probability : probabilities) {
			total += probability;
		}
		
		int random = rand.nextInt(total);
		int cumulative = 0;
		
		for (int i = 0; i < probabilities.length; i++) {
			cumulative += probabilities[i];
			if (random < cumulative) {
				return i;
			}
		}
		return -1;
	}
	
	// Cuando una nave ataca a otra nave.
	public void ataque_nave(MilitaryUnit atacante, MilitaryUnit atacara,boolean atacamos) {
        atacara.tekeDamage(atacante.attack());
        if (atacara.getActualArmor() <= 0) {
            if (atacara.getChanceGeneratinWaste() > (int) (Math.random()*100+1)) {
                wasteMetalDeuterium[0] += atacara.getMetalCost();
                wasteMetalDeuterium[1] += atacara.getDeuteriumCost();
                if (atacamos) {
                    enemyDrops[0] += atacara.getMetalCost();
                    enemyDrops[1] += atacara.getDeuteriumCost();
                } else {
                    planetDrops[0] += atacara.getMetalCost();
                    planetDrops[1] += atacara.getDeuteriumCost();
                }
            }
            removedestroyships();
        }
    }
	
	// Elimina todas las naves con la armadura por debajo o igual a 0.
	public void removedestroyships() {
		for (int i = 0; i < planetArmy.length; i++) {
			for (int j = 0; j < planetArmy[i].size(); j++) {
				if (planetArmy[i].get(j).getActualArmor() <= 0) {
					planetArmy[i].remove(j);
				}
			}
		}
		for (int i = 0; i < enemyArmy.length; i++) {
			for (int j = 0; j < enemyArmy[i].size(); j++) {
				if (enemyArmy[i].get(j).getActualArmor() <= 0) {
					enemyArmy[i].remove(j);
				}
			}
		}
	}

	
	// Se mira que MilitaryUnit es y de ahí se hace de manera random en base a su % de CHANGE_ATTACK_AGAIN si vuelve a atacar o no.
	public boolean againattack(MilitaryUnit atacante) {
		if (atacante.getChanceAttackAgain() > (int) (Math.random()*100+1)) {
			return true;
		}
		return false;
	}
  
	// Aqui se reune toda la batalla.
	public void startBattle() {
	    rand = new Random();
	    wasteMetalDeuterium = new int[2];
	    enemyDrops = new int[2];
	    planetDrops = new int[2];
	    resourcesLooses = new int[2][3];

	    initInitialArmies(); // Guarda conteo inicial
	    resetArmyArmor();    // Reinicia armaduras

	    boolean planetAttacks = rand.nextBoolean(); // decide quién empieza

	    while (remainderPercentatgeFleet(planetArmy) > MIN_PERCENTAGE_TO_WIN &&
	           remainderPercentatgeFleet(enemyArmy) > MIN_PERCENTAGE_TO_WIN) {

	        updateactualunits(); // Actualiza unidades actuales

	        battleDevelopment += "********************CHANGE ATTACKER********************\n";

	        ArrayList<MilitaryUnit>[] attackingArmy;
	        ArrayList<MilitaryUnit>[] defendingArmy;
	        String atacante;

	        if (planetAttacks) {
	            attackingArmy = planetArmy;
	            defendingArmy = enemyArmy;
	            atacante = "Planet";
	        } else {
	            attackingArmy = enemyArmy;
	            defendingArmy = planetArmy;
	            atacante = "Fleet Enemy";
	        }

	        int attackingGroup;
	        do {
		        if (planetAttacks) {
		            attackingGroup = getPlanetGroupAttacker();
		        } else {
		            attackingGroup = getEnemyGroupAttacker();
		        }
	        } while (attackingArmy[attackingGroup].size() == 0);

	        if (attackingGroup == -1 || attackingArmy[attackingGroup].isEmpty()) {
	            planetAttacks = !planetAttacks;
	            continue;
	        }
	        
	        
	        MilitaryUnit attacker = attackingArmy[attackingGroup].get(attackingArmy[attackingGroup].size()-1);

	        boolean repeatAttack;
	        do {
	            int defendingGroup = getGroupDefender(defendingArmy);
	            if (defendingGroup == -1 || defendingArmy[defendingGroup].isEmpty()) break;

	            MilitaryUnit defender = defendingArmy[defendingGroup].get(defendingArmy[defendingGroup].size()-1);

	            battleDevelopment += String.format("Attacks %s: %s attacks %s\n",
	                    atacante,
	                    attacker.getClass().getSimpleName(),
	                    defender.getClass().getSimpleName());

	            int damage = attacker.attack();
	            battleDevelopment += attacker.getClass().getSimpleName() + " generates the damage = " + damage + "\n";

	            int newArmor = Math.max(0, defender.getActualArmor() - damage);
	            battleDevelopment += defender.getClass().getSimpleName() + " stays with armor = " + newArmor + "\n";

	            ataque_nave(attacker, defender, planetAttacks);

	            repeatAttack = againattack(attacker);

	        } while (repeatAttack);

	        removedestroyships();
	        planetAttacks = !planetAttacks; // cambia el turno
	    }
	    updateResourcesLoose(); // Calcula pérdidas al finalizar
	    resetArmyArmor();    // Reinicia armaduras al final
	}
}
