package dralmoraDungeons;

import java.util.Random;

public class Room {
	private Boolean Used, Cleared, isBoss, HasLadder;
	private int HasMonster, HasChest, HasTrap, HasMimic, type;
	private Monster Enemy;
	private String BossColor;
	
	public Room(){
		Enemy = new Monster((new Random()).nextInt(5)+1);
		type = 0 + (int)(Math.random() * ((3 - 0) + 1));
		Used = false;
		Cleared = false;
		isBoss = false;
		HasLadder = false;
		HasMonster = 0;
		HasChest = 0;
		HasTrap = 0;
		HasMimic = 0;
		int colorChooser = 0 + (int)(Math.random() * ((7 - 0) + 1));
		if(colorChooser == 1){//generates a random color for each room. This value is unused unless the room is the boss room. Color is dictated by the randomly generated number given.
			BossColor = "Red";
		}else if (colorChooser == 2){
			BossColor = "Blue";
		}else if (colorChooser == 3){
			BossColor = "Green";
		}else if (colorChooser == 4){
			BossColor = "Yellow";
		}else if (colorChooser == 5){
			BossColor = "Purple";
		}else if (colorChooser == 6){
			BossColor = "Pink";
		}else if (colorChooser == 7){
			BossColor = "Orange";
		}else if (colorChooser == 8){
			BossColor = "Black";
		}else{
			BossColor = "White";
		}
	}
	
	public void changeMimic(){
		Enemy.setMimic();
	}
	
	public int MonsterAttack(){
		return Enemy.getMinStrength() + (int)(Math.random() * ((Enemy.getMaxStrength() - Enemy.getMinStrength()) + 1));
	}
	
	public void MonsterDamaged(int dmg){
		Enemy.setHealth(Enemy.getHealth()-(dmg - Enemy.getArmor()));
	}
	
	public String Describe(){
		if (isBoss == false){//making sure the room isn't the final boss room, which has a specific description instead of a randomly generated one.
			String compiledMessage = null;
			if (type ==1 ){//four types of rooms. it increases variety and can be expanded upon later.
				compiledMessage = "You are in a dark dank room with large, uneven rough cut brick walls. It looks like a dark substance is excreting from the gaps between the walls.\nThere are four large pillars in the room with no form of marking at all aside from what appear to be embedded skulls.";
			}else if (type == 2){
				compiledMessage = "Your in a room with smooth concrete walls. No marking are anywhere within the room, aside from a blood splatter in the corner, peices of bone resting there.\n A large central pillar rest in the middle of the room, with ornate copper peices indented into it at random.";
			}else if (type == 3){
				compiledMessage = "The room you are in is odd. It has a polished, although scratched through the tests of time, floor. The walls are paneled in a dark wood.\nIt looks almost like it was inteded to be a place for someone to live, someone with high tastes at that.";
			}else{
				compiledMessage = "You are in a room with a horrible odor, as if something had taken a corpse in there and deficated upon it.\n It would be wise to move quickly out as other than the smell, the room is entirely unremarkable.";
			}
			if (HasChest == 1){//possible chest descriptions.
				compiledMessage = compiledMessage + "\nThere is an un opened chest in the room, treasure awaits!";
			}else if (HasChest == 2){
				compiledMessage = compiledMessage + "\nThere is an opened chest in the room. You feel happy knowing that it was you that opened it.";
			}else if (HasChest == 3){
				compiledMessage = compiledMessage + "\nThere are the wooden, splintered remains of a chest in the room. It's contents destroyed and unrecoverable. At least it wasnt a mimic.";
			}
			if ((HasTrap == 2)||(HasTrap == 5)||(HasTrap == 8)){//possible trap descriptions. certain criteria must be met in order to have it show up in description.
				compiledMessage = compiledMessage + "\nThere is an armed, but inactive trap. It is impossible to tell what will happen when it is triggered.";
			}else if ((HasTrap == 3)||(HasTrap == 6)||(HasTrap == 9)){
				compiledMessage = compiledMessage + "\nThere is a disarmed trap in the room. The disarming process made it impossible to tell what kind of trap it was.";
			}else if (HasTrap == 10){
				compiledMessage = compiledMessage + "\nThere is a previously triggered trap in the room. That sucked";
			}
			if (HasMimic == 1){//possible mimic room descriptions. certain criteria must be met in order to have it described differently than a chest.
				compiledMessage = compiledMessage + "\nThere is an un opened chest in the room, treasure awaits!";
			}else if ((HasMimic == 2)&&(Enemy.getActive() == false)){
				compiledMessage = compiledMessage + "\nThere is a sleeping mimic in the room. Either dont disturb it and leave, or suprise attack it";
			}else if ((HasMimic == 1)||(HasMimic == 2)&&(Enemy.getActive() == true)){
				compiledMessage = compiledMessage + "\nThere is an angry mimic in the room, its mouth gaping open and tongue hanging out between its razor sharp teeth.";
			}else if (HasMimic == 3){
				compiledMessage = compiledMessage + "\nThere is a dead mimic in the room, a pool of drool and blood dripping onto the floor from its mouth.";
			}
			if ((HasMonster == 1)&&(Enemy.getActive()==true)){//descriptions for the various living forms of monsters, not including the mimic.
				if (Enemy.getType() == 1){
					compiledMessage = compiledMessage + "\nThere is a gross, slimy blob of gelatinous slime in the room. Peices of bone, including a skull, drift within it. Its moving towards you.";
				}else if (Enemy.getType() == 2){
					compiledMessage = compiledMessage + "\nA giant bat is in the room. ugly, matted fur stained with blood is on it, paired with scarred, thick wings and razor sharp talons. It is clearly hostile.";
				}else if (Enemy.getType() == 3){
					compiledMessage = compiledMessage + "\nA pile of bones is somehow animated, forming a skeleton. it is armed with a crude but clearly effective axe, judging by the blood stains on it.";
				}else if (Enemy.getType() == 4){
					compiledMessage = compiledMessage + "\nA small looking demon is in the room, looking at you with hostile curiosity. Its red, scaly skin glimmering in the lanternlight.";
				}else if (Enemy.getType() == 5){
					compiledMessage = compiledMessage + "\nA skeleton lumbers towards you, clad in crude sheets of metal, bolted into its bones, reinforcing its body. it carries a greatsword, sharp and jagged, not fit for a person to hold reasonably.";
				}
			}else if (HasMonster == 2){//descriptions for the various dead forms of monsters, not including the mimic.
				if (Enemy.getType() == 1){
					compiledMessage = compiledMessage + "\nSlimy remains lay on the floor, freed from the body mass of the horrible smelling creature that held them. Your covered in stinky slime.";
				}else if (Enemy.getType() == 2){
					compiledMessage = compiledMessage + "\nA bloody ball of fur sits on the ground, with slashed wings and mangled talons. it smells foul, yet in one part of your mind, you think it might be edible.";
				}else if (Enemy.getType() == 3){
					compiledMessage = compiledMessage + "\nThe one animated set of bones now returns to rest, peices shattered and broken as the pile sits on the ground. the axe it once held looks to be too sharp and badly made to weild.";
				}else if (Enemy.getType() == 4){
					compiledMessage = compiledMessage + "\nThe smell of brimstone is in the air, a pile of ashes are where moments ago, the body of an imp lay.";
				}else if (Enemy.getType() == 5){
					compiledMessage = compiledMessage + "\nA pile of metal and bone scrap is scattered across the room, the greatsword once weilded by the skelton looks unusable. Besides, your sword is better looking.";
				}
			}
			if(HasLadder == true){
				compiledMessage = compiledMessage + "\nThere is a ladder in this room, leading deeper into the depths.";
			}
			return(compiledMessage);
		}else{
			//unique room description for the boss's chambers.
			return("You step into a fancy and highly decorated room. " + BossColor + " tapestries hang from the ceiling. The floor appears to be perfectly polished marble, with walls of black bricks. \nThere is a potent smell of brimstone. A throne made of skulls sits in the middle of the room, a curvy female demon, with large horns and a long slender tail sits upon it, the creatue is a succubus.");
		}
	}
	
	public void setUsed(boolean IN){//command to set a stored value to the given value that is given when called by the main program.
		Used = IN;
	}
	
	public boolean getUsed(){//command to give a value to the main program when called.
		return Used;
	}
	
	public void setCleared(boolean IN){//command to set a stored value to the given value that is given when called by the main program.
		Cleared = IN;
	}
	
	public boolean getCleared(){//command to give a value to the main program when called.
		return Cleared;
	}
	
	public void setIsBoss(boolean IN){//command to set a stored value to the given value that is given when called by the main program.
		isBoss = IN;
	}
	
	public boolean getIsBoss(){//command to give a value to the main program when called.
		return isBoss;
	}
	
	public void setHasMonster(int IN){//command to set a stored value to the given value that is given when called by the main program.
		HasMonster = IN;
	}
	
	public int getHasMonster(){//command to give a value to the main program when called.
		return HasMonster;
	}
	
	public void setHasChest(int IN){//command to set a stored value to the given value that is given when called by the main program.
		HasChest = IN;
	}
	
	public int getHasChest(){//command to give a value to the main program when called.
		return HasChest;
	}
	
	public void setHasTrap(int IN){//command to set a stored value to the given value that is given when called by the main program.
		HasTrap = IN;
	}
	
	public int getHasTrap(){//command to give a value to the main program when called.
		return HasTrap;
	}
	
	public void setHasMimic(int IN){//command to set a stored value to the given value that is given when called by the main program.
		HasMimic = IN;
	}
	
	public int getHasMimic(){//command to give a value to the main program when called.
		return HasMimic;
	}
	
	public void setType(int IN){//command to set a stored value to the given value that is given when called by the main program.
		type = IN;
	}
	
	public int getType(){//command to give a value to the main program when called.
		return type;
	}
	
	public void setLadder(boolean IN){//command to set a stored value to the given value that is given when called by the main program.
		HasLadder = IN;
	}
	
	public boolean getLadder(){//command to give a value to the main program when called.
		return HasLadder;
	}
	
	public void setBossColor(String IN){//command to set a stored value to the given value that is given when called by the main program.
		BossColor = IN;
	}
	
	public String getBossColor(){//command to give a value to the main program when called.
		return BossColor;
	}
	
	public void setMonsterType(int IN){//command to set a value of the monster object in the room object.
		Enemy.setType(IN);
	}
	
	public int getMonsterType(){//command to get a value from the monster object in the room object.
		return Enemy.getType();
	}
	
	public void setMonsterName(String IN){//command to set a value of the monster object in the room object.
		Enemy.setName(IN);
	}
	
	public String getMonsterName(){//command to get a value from the monster object in the room object.
		return Enemy.getName();
	}
	
	public void setMonsterMinStrength(int IN){//command to set a value of the monster object in the room object.
		Enemy.setMinStrength(IN);
	}
	
	public int getMonsterMinStrength(){//command to get a value from the monster object in the room object.
		return Enemy.getMinStrength();
	}
	
	public void setMonsterMaxStrength(int IN){//command to set a value of the monster object in the room object.
		Enemy.setMaxStrength(IN);
	}
	
	public int getMonsterMaxStrength(){//command to get a value from the monster object in the room object.
		return Enemy.getMaxStrength();
	}
	
	public void setMonsterHealth(int IN){//command to set a value of the monster object in the room object.
		Enemy.setHealth(IN);
	}
	
	public int getMonsterHealth(){//command to get a value from the monster object in the room object.
		return Enemy.getHealth();
	}
	
	public void setMonsterArmor(int IN){//command to set a value of the monster object in the room object.
		Enemy.setArmor(IN);
	}
	
	public int getMonsterArmor(){//command to get a value from the monster object in the room object.
		return Enemy.getArmor();
	}
	
	public void setMonsterActive(boolean IN){//command to set a value of the monster object in the room object.
		Enemy.setActive(IN);
	}
	
	public boolean getMonsterActive(){//command to get a value from the monster object in the room object.
		return Enemy.getActive();
	}
}
