package GamePlanetWars;

import java.util.ArrayList;
import java.util.Random;

public class Battle {
	private ArrayList<MilitaryUnit>[] planetArmy;
	private ArrayList<MilitaryUnit>[] enemyArmy;
	private ArrayList[][] armies;
	private String battleDevelopment;
	private int[][] initialCostFleet;
	private int initialNumberUnitsPlanet, initialNumberUnitsEnemy;
	private int[] wasteMetalDeuterium;
	private int[] enemyDrops;
	private int[] planetDrops;
	private int[][] resourcesLooses;
	private int[][] initialArmies;
	private int[] actualNumberUnitsPlanet, actualNumberUnitsEnemy;
	private Random rand;
	
	public int remainderPercentageFleet(ArrayList<MilitaryUnit>[] army) {
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
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return (total * 100) / initialUnits;
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
}
