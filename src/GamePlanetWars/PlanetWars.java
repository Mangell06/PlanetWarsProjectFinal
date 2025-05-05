package GamePlanetWars;

import java.util.ArrayList;

public class PlanetWars {

	public static void main(String[] args) {

	}

}

class Planet {
	private int technologyDefense;
	private int technologyAtack;
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
		this.technologyAtack = technologyAtack;
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
		return technologyAtack;
	}

	public void setTechnologyAtack(int technologyAtack) {
		this.technologyAtack = technologyAtack;
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
	}
	

	public void upgradeTechnologyAttack() {
		if (upgradeAttackTechnologyDeuteriumCost > deuterium) {
			new ResourceException("You don't have enough deuterium to upgrade attack technology.");
		}
		deuterium -= upgradeAttackTechnologyDeuteriumCost;
		technologyAtack += 1;
		upgradeAttackTechnologyDeuteriumCost *= 1.1;
	}
	
	public void newLightHunter(int n) {
		for (int i = 0; i < n; i++) {
			army[0].add(new LightHunter());
		}
	}
	
	public void newHeavyHunter(int n) {}
	
	public void newBattleShip(int n) {}
	
	public void newArmoredShip(int n) {}
	
	public void newMissileLauncher(int n) {}
	
	public void newIonCannon(int n) {}
	
	public void newPlasmaCannon(int n) {}
	
	public void printStats() {}
	
}

class ResourceException extends Exception {
	public ResourceException(String mensaje) {
		super(mensaje);
	}
}

class ship implements MilitaryUnit, Variables{
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

	public int attack() {
		return 0;
	}

	public void tekeDamage(int receivedDamage) {}

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

	public void resetArmor() {}
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
	final int armor_Ligthhunter = 1000;
	final int plus_armor_ligthhunter_by_technology = 5;
}

class LightHunter extends ship {

	public LightHunter(int armor, int baseDamage) {
		super(armor, baseDamage);
		// TODO Auto-generated constructor stub
	}}

class HeavyHunter extends ship {}

class BattleShip extends ship {}

class ArmoredShip extends ship {}


