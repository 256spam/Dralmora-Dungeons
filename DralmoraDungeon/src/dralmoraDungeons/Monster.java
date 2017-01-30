package dralmoraDungeons;

public class Monster {
	private String Name;
	private int type, MinStrength, MaxStrength, Health, Armor;
	private boolean Active;
	
	public Monster(int IN){
		type = IN;
		if (type == 1){//properties of a slime enemy
			Name = "Slime";
			MinStrength = 0;
			MaxStrength = 3;
			Health = 3;
			Armor = 0;
			Active = true;
		}else if (type == 2){//properties of a giant bat enemy
			Name = "Giant Bat";
			MinStrength = 0;
			MaxStrength = 6;
			Health = 5;
			Armor = 0;
			Active = true;
		}else if (type == 3){//properties of a skeleton enemy
			Name = "Skeleton";
			MinStrength = 3;
			MaxStrength = 10;
			Health = 2;
			Armor = 0;
			Active = true;
		}else if (type == 4){//properties of an imp enemy.
			Name = "Imp";
			MinStrength = 3;
			MaxStrength = 10;
			Health = 8;
			Armor = 1;
			Active = true;
		}else if (type == 5){//properties of an armored skeleton enemy,
			Name = "Armored Skeleton";
			MinStrength = 3;
			MaxStrength = 10;
			Health = 5;
			Armor = 5;
			Active = true;
		}else if (type == 6){//properties of a mimic enemy. (most dangerous)
			Name = "Mimic";
			MinStrength = 5;
			MaxStrength = 15;
			Health = 15;
			Armor = 2;
			Active = false;
		}else{//backup incase program generates a monster poorly
			Name = "Slime";
			MinStrength = 0;
			MaxStrength = 3;
			Health = 3;
			Armor = 0;
			Active = true;
		}
	}
	
	public void setType(int IN){//command to set a stored value to the given value that is given when called by the room object.
		type = IN;
	}
	
	public int getType(){//command to give a value to the room object when called.
		return type;
	}
	
	public void setName(String IN){//command to set a stored value to the given value that is given when called by the room object.
		Name = IN;
	}
	
	public String getName(){//command to give a value to the room object when called.
		return Name;
	}
	
	public void setMinStrength(int IN){//command to set a stored value to the given value that is given when called by the room object.
		MinStrength = IN;
	}
	
	public int getMinStrength(){//command to give a value to the room object when called.
		return MinStrength;
	}
	
	public void setMaxStrength(int IN){//command to set a stored value to the given value that is given when called by the room object.
		MaxStrength = IN;
	}
	
	public int getMaxStrength(){//command to give a value to the room object when called.
		return MaxStrength;
	}
	
	public void setHealth(int IN){//command to set a stored value to the given value that is given when called by the room object.
		Health = IN;
	}
	
	public int getHealth(){//command to give a value to the room object when called.
		return Health;
	}
	
	public void setArmor(int IN){//command to set a stored value to the given value that is given when called by the room object.
		Armor = IN;
	}
	
	public int getArmor(){//command to give a value to the room object when called.
		return Armor;
	}
	
	public void setActive(boolean IN){//command to set a stored value to the given value that is given when called by the room object.
		Active = IN;
	}
	
	public boolean getActive(){//command to give a value to the room object when called.
		return Active;
	}

	public void setMimic() {
		Name = "Mimic";
		MinStrength = 5;
		MaxStrength = 15;
		Health = 15;
		Armor = 2;
		Active = false;
	}
}
