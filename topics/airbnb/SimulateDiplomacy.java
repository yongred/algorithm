/**
Simulate Diplomacy

Input (army_name, location, action())

action() could be:

move(new_location) then army_name to new_location, If there is an army at new_location, then company strength of two aramy:
The army have higher strength stay at new_location, the lower army is disappeared.
If two army have the same strength, both are disppeared.

hold() stay at the same location

support(army_name) supoort another army. The supported army have one more strength.
The army's strength are intialized as the same.

Also known as OA 4
Leetcode 相似问题: Leetcode #289 Game of Life

http://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=298214

外交游戏可以参考 http://www.backstabbr.com/rules 这个链接里的规则,当然,题目做了简化。

输入是 "军队名 地点 1 action (地点 2/军队名)",比如“A Paris move London“,action 有 move,
support, hold 三种,move 是从地点 1 到地点 2, hold 是留在地点 1,support 也留在原地,support
的对象是另一支军队.

主要需要计算的是 move 引起的军队地点变化和属性变化。move 的目的地如果别的军队,就会发
生战斗,战斗的结果取决于 strength 大小,如果 strength 相等,那么都挂,不等的时候 strength
大的胜,别的挂。

strength 默认初始都一样,可以通过别人的 support 提升,但是 support 的军队的驻地如果被
attack,support 是无效的。

总体理解题意以后算法是蛮直接的,没什么技巧,祝大家都好运!

所以这题两个 strength 相等是都挂,但原游戏其实是都撤退?

补充一下 followup: 白人小哥,walk through the solution, 分析优化,然后提问题,这题没太多可说
的,很快问完,聊的很愉快
*/

/**
Army: name, strength;
Locations: 1 army at a location.
Actions: 
	Hold(): stay in place, do nothing.
	Support(army): stay in place, take a armyName as param. target army.strength + 1;
	Move(Location): Move to that location, if newLocation already has an army Battle, curArmy.strength ><= tarArmy.strength; (Destroy less strength army, if == strength, both destroyed.)
*/

import java.io.*;
import java.util.*;

public class SimulateDiplomacy {

	class Army {
		int strength;
		String name;
		Army supporting;
		boolean dead;

		Army(String name) {
			this.name = name;
			// strength default to 1. Increase when got support
			this.strength = 1;
			this.supporting = null;
			this.dead = false;
		}

		public void hold(String loc, Map<String, String> armyLocations) {
			// check if dead
			if (this.dead) {
				System.out.println("Army " + this.name + " Dead");
				return;
			}
			// if not in loc yet, put army there.
			armyLocations.put(loc, this.name);
			System.out.println("Hold: At " + loc + " army: " + this.name);
		}

		public void support(Army targetArmy) {
			// check if dead
			if (this.dead || targetArmy.dead) {
				System.out.println("Army " + targetArmy.name + " Dead");
				return;
			}
			this.supporting = targetArmy;
			this.supporting.strength++;
			System.out.println("Supporting: " + targetArmy.name + " strength: " + targetArmy.strength);
		}

		public void move(String newLocation, Map<String, String> armyLocations,
				Map<String, Army> armies) {
			if (this.dead) {
				System.out.println("Army " + this.name + " Dead");
				return;
			}
			// check if new location already has an army.
			if (armyLocations.containsKey(newLocation)) {
				String enemyName = armyLocations.get(newLocation);
				// check if enemyName exist, 
				if (armies.containsKey(enemyName)) {
					// compare strength;
					Army enemy = armies.get(enemyName);
					// check if enemy if Dead
					if (this.strength > enemy.strength || enemy.dead) {
						// cur is stronger, loc = this. remove enemy
						armyLocations.put(newLocation, this.name);
						// enemy died.
						enemy.destroyed();
						System.out.println("Move: At " + newLocation + " located army: " + enemy.name + " destroyed by " + this.name);
					} else if (enemy.strength > this.strength) {
						// enenmy stays, this army dies
						this.destroyed();
						System.out.println("Move: At " + newLocation + " invading army: " + this.name + " destroyed by " + enemy.name);
					} else {
						// == in strength; both destroyed, empty the location. "" empty string as no-army.
						armyLocations.put(newLocation, "");
						this.destroyed();
						enemy.destroyed();
						System.out.println("Move: At " + newLocation + " both armies: " + this.name + ", " + enemy.name + " destroyed");
					}
				} else {
					// no Army found in location, empty spot.
					armyLocations.put(newLocation, this.name);
					System.out.println("Move: At " + newLocation + " army: " + this.name + "occupied");
				}
			} else {
				// location not occupied, not exist.
				armyLocations.put(newLocation, this.name);
				System.out.println("Move: At " + newLocation + " army: " + this.name + "occupied");
			}
		}

		public void destroyed() {
			if (this.dead) {
				System.out.println("Army " + this.name + " is Already Dead");
				return;
			}
			// check if this is supporting anybody
			if (supporting != null) {
				supporting.strength--;
				System.out.println("Supported Army " + supporting.name + " strength -- " + supporting.strength);
			}
			this.dead = true;

		}
	}

	class Action {
		// 0 hold, 1 support, 2 move;
		int actionType;
		// for supporting an army. Null if not Support.
		Army targetArmy;
		// for move to newLocation. Null if not Move.
		String newLocation;

		Action(int actionType, Army targetArmy, String newLocation) {
			this.actionType = actionType;
			this.targetArmy = targetArmy;
			this.newLocation = newLocation;
		}
	}

	// Map of armyNames -> army;
	public Map<String, Army> armies = new HashMap<>();
	// hashMap <Location, ArmyName>
	public Map<String, String> armyLocations = new HashMap<>();

	public void simulate(String armyName, String loc, Action action) {
		// check army in armies map;
		if (!armies.containsKey(armyName)) {
			armies.put(armyName, new Army(armyName));
		}
		Army army = armies.get(armyName);
		// perform Action
		switch(action.actionType) {
			case 0: // hold
				army.hold(loc, armyLocations);
				break;
			case 1: // support
				army.support(action.targetArmy);
				break;
			case 2: // move
				army.move(action.newLocation, armyLocations, armies);
				break;
		}
	}


	public static void main(String[] args) {
		SimulateDiplomacy obj = new SimulateDiplomacy();
		String[] armyNames = new String[] {"A", "B", "C", "D"};
		String[] locations = new String[] {"loc1", "loc2", "loc3", "loc4", "loc5"};
		// holds in place
		obj.simulate(armyNames[0], locations[0], obj.new Action(0, null, null));
		obj.simulate(armyNames[1], locations[1], obj.new Action(0, null, null));
		obj.simulate(armyNames[2], locations[2], obj.new Action(0, null, null));
		obj.simulate(armyNames[3], locations[3], obj.new Action(0, null, null));
		// support B->A;
		obj.simulate(armyNames[1], locations[1], obj.new Action(1, obj.armies.get(armyNames[0]), null));
		// support D->C;
		obj.simulate(armyNames[3], locations[3], obj.new Action(1, obj.armies.get(armyNames[2]), null));
		// move A->D;
		obj.simulate(armyNames[0], locations[0], obj.new Action(2, null, locations[3]));
		// move C->B;
		obj.simulate(armyNames[2], locations[2], obj.new Action(2, null, locations[1]));
		// move C->A;
		obj.simulate(armyNames[2], locations[1], obj.new Action(2, null, locations[3]));
	}
}
 
