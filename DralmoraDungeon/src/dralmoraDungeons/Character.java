package dralmoraDungeons;

public class Character {
		private static String Name;
		private static int Health, Gold, Armor, Potions, lastX, lastY, lastZ;
	
	public Character(){
		Reset();//sets all values to default when created.
	}
	
	public static void Reset(){//command used upon dying, resetting character to the beginning values. like when starting a new game.
		Name = null;
		Health = 30;
		Gold = 0;
		Armor = 2;
		Potions = 2;
		lastX = 0;
		lastY = 0;
		lastZ = 0;
	}
	
	public static void setName(String IN){//command to set a stored value to the given value that is given when called by the main program.
		Name = IN;
	}
	
	public static String getName(){//command to give a value to the main program when called.
		return Name;
	}
	
	public static void setHealth(int IN){//command to set a stored value to the given value that is given when called by the main program.
		Health = IN;
	}
	
	public static int getHealth(){//command to give a value to the main program when called.
		return Health;
	}
	
	public static void setGold(int IN){//command to set a stored value to the given value that is given when called by the main program.
		Gold = IN;
	}
	
	public static int getGold(){//command to give a value to the main program when called.
		return Gold;
	}
	
	public static void setArmor(int IN){//command to set a stored value to the given value that is given when called by the main program.
		Armor = IN;
	}
	
	public static int getArmor(){//command to give a value to the main program when called.
		return Armor;
	}
	
	public static void setPotions(int IN){//command to set a stored value to the given value that is given when called by the main program.
		Potions = IN;
	}
	
	public static int getPotions(){//command to give a value to the main program when called.
		return Potions;
	}
	
	public static void setLastX(int IN){//command to set a stored value to the given value that is given when called by the main program.
		lastX = IN;
	}
	
	public static int getLastX(){//command to give a value to the main program when called.
		return lastX;
	}
	
	public static void setLastY(int IN){//command to set a stored value to the given value that is given when called by the main program.
		lastY = IN;
	}
	
	public static int getLastY(){//command to give a value to the main program when called.
		return lastY;
	}
	
	public static void setLastZ(int IN){//command to set a stored value to the given value that is given when called by the main program.
		lastZ = IN;
	}
	
	public static int getLastZ(){//command to give a value to the main program when called.
		return lastZ;
	}
}
