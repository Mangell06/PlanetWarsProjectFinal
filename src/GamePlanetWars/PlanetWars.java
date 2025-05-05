package GamePlanetWars;

import java.util.ArrayList;

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

	public void upgradeTechnologyDefense() {
		if (upgradeDefenseTechnologyDeuteriumCost > deuterium) {
			new ResourceException("You don't have enough deuterium to upgrade defense technology.");
		}
		deuterium -= upgradeDefenseTechnologyDeuteriumCost;
		technologyDefense += 1;
		upgradeDefenseTechnologyDeuteriumCost *= 1.1;
	}
	

	public void upgradeTechnologyAttack() {
		if (upgradeAttackTechnologyDeuteriumCost > deuterium) {
			new ResourceException("You don't have enough deuterium to upgrade attack technology.");
		}
		deuterium -= upgradeAttackTechnologyDeuteriumCost;
		technologyAttack += 1;
		upgradeAttackTechnologyDeuteriumCost *= 1.1;
	}
	
	public void newLightHunter(int n) {
		for (int i = 0; i <= n; i++) {
			if (Variables.METAL_COST_LIGTHHUNTER > metal || Variables.DEUTERIUM_COST_LIGTHHUNTER > deuterium) {
				new ResourceException("You don't have enough resource");
			}
			army[0].add(new LightHunter(Variables.ARMOR_LIGTHHUNTER + (technologyDefense*Variables.PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY)%1000,Variables.BASE_DAMAGE_LIGTHHUNTER + (technologyAttack*Variables.PLUS_ATTACK_LIGTHHUNTER_BY_TECHNOLOGY)%1000));
		}
	}
	
	public void newHeavyHunter(int n) {
		for (int i = 0; i <= n; i++) {
			if (Variables.METAL_COST_HEAVYHUNTER > metal || Variables.DEUTERIUM_COST_HEAVYHUNTER > deuterium) {
				new ResourceException("You don't have enough resource");
			}
			army[1].add(new HeavyHunter(Variables.ARMOR_HEAVYHUNTER + (technologyDefense*Variables.PLUS_ARMOR_HEAVYHUNTER_BY_TECHNOLOGY)%1000,Variables.BASE_DAMAGE_HEAVYHUNTER + (technologyAttack*Variables.PLUS_ATTACK_HEAVYHUNTER_BY_TECHNOLOGY)%1000));
		}
	}
	
	public void newBattleShip(int n) {
		for (int i = 0; i <= n; i++) {
			if (Variables.METAL_COST_BATTLESHIP > metal || Variables.DEUTERIUM_COST_BATTLESHIP > deuterium) {
				new ResourceException("You don't have enough resource");
			}
			army[2].add(new BattleShip(Variables.ARMOR_BATTLESHIP + (technologyDefense*Variables.PLUS_ARMOR_BATTLESHIP_BY_TECHNOLOGY)%1000,Variables.BASE_DAMAGE_BATTLESHIP + (technologyAttack*Variables.PLUS_ATTACK_BATTLESHIP_BY_TECHNOLOGY)%1000));
		}
	}
	
	public void newArmoredShip(int n) {
		for (int i = 0; i <= n; i++) {
			if (Variables.METAL_COST_BATTLESHIP > metal || Variables.DEUTERIUM_COST_BATTLESHIP > deuterium) {
				new ResourceException("You don't have enough resource");
			}
			army[3].add(new BattleShip(Variables.ARMOR_BATTLESHIP + (technologyDefense*Variables.PLUS_ARMOR_BATTLESHIP_BY_TECHNOLOGY)%1000,Variables.BASE_DAMAGE_BATTLESHIP + (technologyAttack*Variables.PLUS_ATTACK_BATTLESHIP_BY_TECHNOLOGY)%1000));
		}
	}
	
	public void newMissileLauncher(int n) {}
	
	public void newIonCannon(int n) {}
	
	public void newPlasmaCannon(int n) {}
	
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
        mostrar += String.format("\n%-30s%d", "Armord Ship", army[0].size());
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
	public static final int DEUTERIUM_COST_HEAVYHUNTERU = 0;
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
}

class LightHunter extends ship {

	public LightHunter(int armor, int baseDamage) {
		super(armor,baseDamage);
	}

	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		int damage = getArmor() - receivedDamage;
		if (damage <= 0) {
			damage = 0;
		}
		
		setArmor(damage);
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
	
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		int damage = getArmor() - receivedDamage;
		if (damage <= 0) {
			damage = 0;
		}
		
		setArmor(damage);
	}

	public int getActualArmor() {
		return getArmor();
	}

	public int getMetalCost() {
		return METAL_COST_HEAVYHUNTER;
	}

	public int getDeuteriumCost() {
		return DEUTERIUM_COST_HEAVYHUNTERU;
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
	
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		int damage = getArmor() - receivedDamage;
		if (damage <= 0) {
			damage = 0;
		}
		
		setArmor(damage);
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
	
	public int attack() {
		return getBaseDamage();
	}

	public void tekeDamage(int receivedDamage) {
		int damage = getArmor() - receivedDamage;
		if (damage <= 0) {
			damage = 0;
		}
		
		setArmor(damage);
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
	@Override
	public int attack() {
		return getBaseDamage();
	}
	
	@Override
	public void tekeDamage(int receivedDamage) {
		int damage = getArmor() - receivedDamage;
		if (damage < 0) {
			damage = 0;
		}
		
		setArmor(damage);
	}
	
	@Override
	public int getActualArmor() {
		return getArmor();
	}
	
	@Override
	public int getMetalCost() {
		return METAL_COST_MISSILELAUNCHER;
	}

	@Override
	public int getDeuteriumCost() {
		return DEUTERIUM_COST_MISSILELAUNCHER;
	}

	@Override
	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_MISSILELAUNCHER;
	}

	@Override
	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_MISSILELAUNCHER;
	}

	@Override
	public void resetArmor() {
		setArmor(getInitialArmor());
	}
}

class IonCannon extends Defense {
	public IonCannon(int armor, int baseDamage) {
		super(armor, baseDamage);	
	}
	
	// === METODOS INTERFAZ ===
	@Override
	public int attack() {
		return getBaseDamage();
	}

	@Override
	public void tekeDamage(int receivedDamage) {
		int damage = getArmor() - receivedDamage;
		if (damage < 0) {
			damage = 0;
		}
		
		setArmor(damage);
	}

	@Override
	public int getActualArmor() {
		return getArmor();
	}

	@Override
	public int getMetalCost() {
		return METAL_COST_IONCANNON;
	}

	@Override
	public int getDeuteriumCost() {
		return DEUTERIUM_COST_IONCANNON;
	}

	@Override
	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_IONCANNON;
	}

	@Override
	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_IONCANNON;
	}

	@Override
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
	
	
}

class PlasmaCannon extends Defense {

	public PlasmaCannon(int armor, int baseDamage) {
		super(armor, baseDamage);
	}

	public int attack() {
		return 0;
	}

	public void tekeDamage(int receivedDamage) {		
	}

	public int getActualArmor() {
		return 0;
	}

	public int getMetalCost() {
		return 0;
	}

	public int getDeuteriumCost() {
		return 0;
	}

	public int getChanceGeneratinWaste() {
		return 0;
	}

	public int getChanceAttackAgain() {
		return 0;
	}

	public void resetArmor() {
		
	}
	
}
