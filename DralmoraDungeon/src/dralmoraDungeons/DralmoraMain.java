package dralmoraDungeons;

import java.awt.Font;
import java.io.*;
import java.util.Random;

import javax.swing.*;

public class DralmoraMain {
	static Room[][][] DungeonMap;
	static Character player;
	static String Title,CharacterSave,MapSave;
	static int startX, startY;
	static Random r = new Random();

	//______           _                           
	//|  _  |         | |                          
	//| | | |_ __ __ _| |_ __ ___   ___  _ __ __ _ 
	//| | | | '__/ _` | | '_ ` _ | / _ || '__/ _` |
	//| |/ /| | | (_| | | | | | | | (_) | | | (_| |
	//|___/ |_|  |__,_|_|_| |_| |_||___/|_|  |__,_|
	//______                                        
	//|  _  |                                       
	//| | | |_   _ _ __   __ _  ___  ___  _ __  ___ 
	//| | | | | | | '_ | / _` |/ _ |/ _ || '_ |/ __|
	//| |/ /| |_| | | | | (_| |  __/ (_) | | | |__ |
	//|___/  |__,_|_| |_||__, ||___||___/|_| |_|___/
	//                    __/ |                     
	//                   |___/                      
	
	public static void main (String[] args) throws IOException{
		DungeonMap = new Room[10][10][5];
		ResetMap();
		Title = "______           _                           \n|  _  |         | |                          \n| | | |_ __ __ _| |_ __ ___   ___  _ __ __ _ \n| | | | '__/ _` | | '_ ` _ | / _ || '__/ _` |\n| |/ /| | | (_| | | | | | | | (_) | | | (_| |\n|___/ |_|  |__,_|_|_| |_| |_||___/|_|  |__,_|\n______                                        \n|  _  |                                       \n| | | |_   _ _ __   __ _  ___  ___  _ __  ___ \n| | | | | | | '_ | / _` |/ _ |/ _ || '_ |/ __|\n| |/ /| |_| | | | | (_| |  __/ (_) | | | |__ |\n| |/ /| |_| | | | | (_| |  __/ (_) | | | |__ |\n|___/  |__,_|_| |_||__, ||___||___/|_| |_|___/\n                    __/ |                     \n                   |___/                      ";
		//The title variable is an incredibly long string that is the Ascii art for the title. each \n is a break to drop to the next line.
		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 10);//setting up a new font
		font = font.deriveFont(15.0f);//setting the size of the new font
		UIManager.put("OptionPane.messageFont", font);//setting the global font
		JOptionPane.showMessageDialog(null,Title + "\n\n\nWelcome to the game, press OK to continue.");
		int saveLoad = getIntInput("Type 1 for a new game. Type 2 to continue game.");
		if (saveLoad == 1){//start new game
			NewGame();
			startX = 0 + (int)(Math.random() * ((9 - 0)));
			startY = 0 + (int)(Math.random() * ((9 - 0)));
			Generate(startX,startY,0,1);
			Character.setLastX(startX);
			Character.setLastY(startY);
			Character.setLastZ(0);
			SaveGame();
			JOptionPane.showMessageDialog(null,"Reminder. Do not put punctuation into any input.");
			MainLoop();
		}else if (saveLoad == 2){//load previous game.
			LoadGame();
			SaveGame();
			JOptionPane.showMessageDialog(null,"Reminder. Do not put punctuation into any input.");
			MainLoop();
		}else{
			JOptionPane.showMessageDialog(null, "You entered an incorrect option, aborting game.\nHave a nice day!");//error catch.
		}
		
	}
	public static int getIntInput(String dialogue){ //the getIntInput is a method used to get integer related input from the user WITH error checking. requires a string to be given when run, and returns a value raw. (so when used it should be run in a form such as int test = getIntInput("Enter the number 5);)
		boolean noError = true; 
		int processesdInput = 0;
		while (noError == true) {//program will continue to ask for input until the user gives an integer as an input.
			String Tempinput = JOptionPane.showInputDialog(dialogue);
			try {
				processesdInput = Integer.parseInt(Tempinput);
				noError = false;
			} catch (NumberFormatException nfe) {
			}
		}
		return processesdInput;
	}
	public static String getFilePath(String Dialogue){
		boolean noError = true;//sets the noError boolean to true every time this runs, (even though it only runs once, but its a precaution)
		String input = null;
		while (noError == true) { //runs a loop so long as noError is equal to true.
			input = getStringInput(Dialogue); //creates a dialogue box, prompting the user to enter the file location for the save information, immediatly setting that string as the input variable.
			try { //tries the next set of code.
				BufferedReader testFile = new BufferedReader( new FileReader(input));//creates a temporary file reader in order to test that the file path given is a valid file path
				testFile.close();// closes the temporary buffered reader when it works in order to not cause a memory leak.
				noError = false; // sets the noerror variable to false, allowing the program to continue working.
			} catch (IOException nfe) {
			}
		}
		return input;
	}
	public static String getStringInput(String Dialogue){
		boolean noError = true;//sets the noError boolean to true every time this runs, (even though it only runs once, but its a precaution)
		String input = null;
		while (noError == true) { //runs a loop so long as noError is equal to true.
			input = JOptionPane.showInputDialog(Dialogue);
			try { //tries the next set of code.
				if (input.matches("(?i).*temp*") == true){//tries to perform an action with the input string that would cause a nullpointerexception if it had a value of null, triggering the catch if it is a null value.
				}
				noError = false;
			} catch (NullPointerException npe) {
			}
		}
		return input;
	}
	public static void NewGame(){//asks for locations to store save data. Required in order to save data later on.
		CharacterSave = getFilePath("Please give a file path to a .txt file you wish to use as the character save file. If you already have one to use, give it's file path.");
		MapSave = getFilePath("Please give a file path to a .txt file you wish to use as the map save file. If you already have one to use, give it's file path.");
		Character.Reset();
		JOptionPane.showMessageDialog(null, "You are an adventurer exploring the world, helping those in need if the pay is right.\nOnce day you stumbled into a town. The villagers plead and beg for you to help them. A horrible beast created some form of lair nearby and it has been spewing forth monsters every night.\nThe people of the village know the weakness of the monster, they give you a golden, shimmering sword with a compass built into the hilt.\nThey promise you can keep the sword, and any of the riches found in the lair, and town fairest maidian will be wed to you if you defeat the beast.");
		//^ Game backstory ^
		Character.setName(getStringInput("Please enter your name."));
		
	}
	public static void LoadGame() throws IOException{
		CharacterSave = getFilePath("Please give the file path to the character save .txt file.");
		MapSave = getFilePath("Please give the file path to the map save .txt file.");
		BufferedReader readFile1 = new BufferedReader( new FileReader(CharacterSave));
		Character.setName(readFile1.readLine());
		Character.setHealth(Integer.parseInt(readFile1.readLine()));
		Character.setGold(Integer.parseInt(readFile1.readLine()));
		Character.setArmor(Integer.parseInt(readFile1.readLine()));
		Character.setPotions(Integer.parseInt(readFile1.readLine()));
		Character.setLastX(Integer.parseInt(readFile1.readLine()));
		Character.setLastY(Integer.parseInt(readFile1.readLine()));
		Character.setLastZ(Integer.parseInt(readFile1.readLine()));
		readFile1.close();
		
		BufferedReader readFile2 = new BufferedReader( new FileReader(MapSave));
		for (int z = 0; z < 5; z++){//three loops are run. one is X, one is Y, and one is Z for the dungeon map co-ordinates. Loads all settings for each room and puts them in to each one.
			for (int y = 0; y < 10; y++){
				for (int x = 0; x < 10; x++){
					DungeonMap[x][y][z].setType(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setUsed(java.lang.Boolean.parseBoolean(readFile2.readLine()));
					DungeonMap[x][y][z].setCleared(java.lang.Boolean.parseBoolean(readFile2.readLine()));
					DungeonMap[x][y][z].setIsBoss(java.lang.Boolean.parseBoolean(readFile2.readLine()));
					DungeonMap[x][y][z].setLadder(java.lang.Boolean.parseBoolean(readFile2.readLine()));
					DungeonMap[x][y][z].setHasMonster(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setHasChest(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setHasTrap(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setHasMimic(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setBossColor(readFile2.readLine());
					DungeonMap[x][y][z].setMonsterType(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setMonsterName(readFile2.readLine());
					DungeonMap[x][y][z].setMonsterMinStrength(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setMonsterMaxStrength(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setMonsterHealth(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setMonsterArmor(Integer.parseInt(readFile2.readLine()));
					DungeonMap[x][y][z].setMonsterActive(java.lang.Boolean.parseBoolean(readFile2.readLine()));
				}
			}
		}
		readFile2.close();
	}
	public static void Generate(int x, int y, int z,int roomCount){
		boolean nextfloor = false;
		float chance = r.nextFloat();
		DungeonMap[x][y][z].setUsed(true);
		if ((z == 0)&&(roomCount == 4)){//checks the number of rooms already generated on the floor. If it has reached the point of the floor's maximum, it puts the way down in.
			DungeonMap[x][y][z].setLadder(true);
			nextfloor = true;
		}else if ((z == 1)&&(roomCount == 6)){//checks the number of rooms already generated on the floor. If it has reached the point of the floor's maximum, it puts the way down in.
			DungeonMap[x][y][z].setLadder(true);
			nextfloor = true;
		}else if ((z == 2)&&(roomCount == 8)){//checks the number of rooms already generated on the floor. If it has reached the point of the floor's maximum, it puts the way down in.
			DungeonMap[x][y][z].setLadder(true);
			nextfloor = true;
		}else if ((z == 3)&&(roomCount == 10)){//checks the number of rooms already generated on the floor. If it has reached the point of the floor's maximum, it puts the way down in.
			DungeonMap[x][y][z].setLadder(true);
			nextfloor = true;
		}else if ((z == 4)&&(roomCount == 15)){//checks the number of rooms already generated on the floor. If it has reached the point of the floor's maximum, it puts the final boss in.
			DungeonMap[x][y][z].setIsBoss(true);
		}
		if(DungeonMap[x][y][z].getIsBoss()== false){
			if (chance <= 0.75f){
				DungeonMap[x][y][z].setHasMonster(1);//sets the monster generated with the room to be used.
			}
			if (chance <= 0.35f){
				DungeonMap[x][y][z].setHasChest(1);//creates an unopened chest
			}
			if (chance <= 0.10f){
				DungeonMap[x][y][z].setHasMimic(1);//creates a mimic object in the room
				DungeonMap[x][y][z].setHasChest(0);//mimic overwrites a chest object, thus if a chest was generated it is replaced by the mimic.
				DungeonMap[x][y][z].setHasMonster(0);//Mimic overwrites a monster so that no double-teaming occurs as that would be highly unfair.
				DungeonMap[x][y][z].changeMimic();
			}
			if (chance <= 0.25f){
				if(DungeonMap[x][y][z].getHasMonster()==1){
					DungeonMap[x][y][z].setHasTrap(7);//sets a monster trap, makes the generated monster inactive until the trap is triggered.
					DungeonMap[x][y][z].setMonsterActive(false);
				}else{
					if(chance <= 0.50f){
						DungeonMap[x][y][z].setHasTrap(1);//sets a spike trap in the room
					}else{
						DungeonMap[x][y][z].setHasTrap(4);//sets a gas trap in the room.
					}
				}
			}
		}
		if (DungeonMap[x][y][z].getIsBoss() == false){
			if (nextfloor == true){
				Generate(x,y,(z+1),1);
			}else{
				int temp;
				if ((x == 0)&&(y == 0)){//checks if the generation method has hit a map boundary. If it has the program restricts where it can generate to next. Program CAN overwrite previous rooms to create floors with less rooms.
					temp = 0 + (int)(Math.random() * ((1 - 0) + 1));
					if (temp == 0){
						Generate(x+1,y,z,roomCount+1);
					}else{
						Generate(x,y+1,z,roomCount+1);
					}
				}else if ((x == 9)&&(y == 0)){
					temp = 0 + (int)(Math.random() * ((1 - 0) + 1));
					if (temp == 0){
						Generate(x-1,y,z,roomCount+1);
					}else{
						Generate(x,y+1,z,roomCount+1);
					}
				}else if ((x == 0)&&(y == 9)){
					temp = 0 + (int)(Math.random() * ((1 - 0) + 1));
					if (temp == 0){
						Generate(x+1,y,z,roomCount+1);
					}else{
						Generate(x,y-1,z,roomCount+1);
					}
				}else if ((x == 9)&&(y == 9)){
					temp = 0 + (int)(Math.random() * ((1 - 0) + 1));
					if (temp == 0){
						Generate(x-1,y,z,roomCount+1);
					}else{
						Generate(x,y-1,z,roomCount+1);
					}
				}else if (x == 0){
					temp = 0 + (int)(Math.random() * ((2 - 0) + 1));
					if (temp == 0){
						Generate(x+1,y,z,roomCount+1);
					}else if (temp == 1){
						Generate(x,y+1,z,roomCount+1);
					}else{
						Generate(x,y-1,z,roomCount+1);
					}
				}else if (x == 9){
					temp = 0 + (int)(Math.random() * ((2 - 0) + 1));
					if (temp == 0){
						Generate(x-1,y,z,roomCount+1);
					}else if (temp == 1){
						Generate(x,y+1,z,roomCount+1);
					}else{
						Generate(x,y-1,z,roomCount+1);
					}
				}else if (y == 0){
					temp = 0 + (int)(Math.random() * ((2 - 0) + 1));
					if (temp == 0){
						Generate(x+1,y,z,roomCount+1);
					}else if (temp == 1){
						Generate(x-1,y,z,roomCount+1);
					}else{
						Generate(x,y+1,z,roomCount+1);
					}
				}else if (y == 9){
					temp = 0 + (int)(Math.random() * ((2 - 0) + 1));
					if (temp == 0){
						Generate(x+1,y,z,roomCount+1);
					}else if (temp == 1){
						Generate(x-1,y,z,roomCount+1);
					}else{
						Generate(x,y-1,z,roomCount+1);
					}
				}else{
					temp = 0 + (int)(Math.random() * ((3 - 0) + 1));
					if (temp == 0){
						Generate(x+1,y,z,roomCount+1);
					}else if (temp == 1){
						Generate(x-1,y,z,roomCount+1);
					}else if (temp == 2){
						Generate(x,y+1,z,roomCount+1);
					}else{
						Generate(x,y-1,z,roomCount+1);
					}
				}
			}
		}
	}
	public static void SaveGame() throws IOException{
		PrintWriter fileOut1 = new PrintWriter(new FileWriter(CharacterSave));//file writer for the character's information.
		fileOut1.println(Character.getName());
		fileOut1.println(Character.getHealth());
		fileOut1.println(Character.getGold());
		fileOut1.println(Character.getArmor());
		fileOut1.println(Character.getPotions());
		fileOut1.println(Character.getLastX());
		fileOut1.println(Character.getLastY());
		fileOut1.println(Character.getLastZ());
		fileOut1.close();
		
		PrintWriter fileOut2 = new PrintWriter(new FileWriter(MapSave));
		
		for (int z = 0; z < 5; z++){//three loops are run. one is X, one is Y, and one is Z for the dungeon map co-ordinates. Saves all room information to load after.
			for (int y = 0; y < 10; y++){
				for (int x = 0; x < 10; x++){
					fileOut2.println(DungeonMap[x][y][z].getType());
					fileOut2.println(DungeonMap[x][y][z].getUsed());
					fileOut2.println(DungeonMap[x][y][z].getCleared());
					fileOut2.println(DungeonMap[x][y][z].getIsBoss());
					fileOut2.println(DungeonMap[x][y][z].getLadder());
					fileOut2.println(DungeonMap[x][y][z].getHasMonster());
					fileOut2.println(DungeonMap[x][y][z].getHasChest());
					fileOut2.println(DungeonMap[x][y][z].getHasTrap());
					fileOut2.println(DungeonMap[x][y][z].getHasMimic());
					fileOut2.println(DungeonMap[x][y][z].getBossColor());
					fileOut2.println(DungeonMap[x][y][z].getMonsterType());
					fileOut2.println(DungeonMap[x][y][z].getMonsterName());
					fileOut2.println(DungeonMap[x][y][z].getMonsterMinStrength());
					fileOut2.println(DungeonMap[x][y][z].getMonsterMaxStrength());
					fileOut2.println(DungeonMap[x][y][z].getMonsterHealth());
					fileOut2.println(DungeonMap[x][y][z].getMonsterArmor());
					fileOut2.println(DungeonMap[x][y][z].getMonsterActive());
				}
			}
		}
		fileOut2.println();
		
		fileOut2.close();
	}
	public static void MainLoop() throws IOException{
		
		boolean exitprogram = false;
		boolean justEntered = true;
		String action = null;
		String preformedEvents = "";
		while (exitprogram == false){
			if (justEntered == true){
				action = getStringInput(DescribeRoom()+playerStatus());
				justEntered = false;
				preformedEvents = "";
			}else{
				action = getStringInput(preformedEvents+playerStatus());
				preformedEvents = "";
			}
			
			if (action.toLowerCase().contains("quit") == true){
				SaveGame();
				JOptionPane.showMessageDialog(null,"See you next time player! your game has been saved!");
				exitprogram = true;
				break;
			}
			//If a monster is in the room and you do anything other than quit, it hits you. Only thing that happens first is quitting the game.
			if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getIsBoss()==false){
				if ((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMonster()== 1)&&(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterActive()==true)){
					int dmg = (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].MonsterAttack()-Character.getArmor()); 
					Character.setHealth(Character.getHealth()-dmg);
					preformedEvents = preformedEvents + ("\n" + DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+" Attacks you. It deals " + dmg + " damage to you.");
				}else if (((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==1)||(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==2))&&(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterActive()==true)){
					int dmg = (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].MonsterAttack()-Character.getArmor()); 
					Character.setHealth(Character.getHealth()-dmg);
					preformedEvents = preformedEvents + ("\n" + DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+" Attacks you. It deals " + dmg + " damage to you.");
				}
				if (Character.getHealth() <= 0){
					GameOver();
					exitprogram = true;
					break;
				}
			
				if ((action.toLowerCase().contains("examine") == true)||(action.toLowerCase().contains("look") == true)||(action.toLowerCase().contains("search") == true)){
					if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==1){
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(2);
					}else if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==4){
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(5);
					}else if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==7){
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(8);
					}
					if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==1){
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasMimic(2);
					}
					preformedEvents = preformedEvents+"\n" + DescribeRoom();
				}else if((action.toLowerCase().contains("disarm") == true)) {
					if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==2){
						if (0 + (int)(Math.random() * ((1 - 0)))==1){//coin flip to disarm or trigger
							DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(3);
						}else{//failed disarm
							preformedEvents = preformedEvents + ("\n" + "You fail to disarm the spike trap, it sets it off, dealing "+TriggerTrap(1)+" to you.");
							DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(10);
						}
					}else if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==5){
						if (0 + (int)(Math.random() * ((1 - 0)))==1){//coin flip to disarm or trigger
							DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(6);
						}else{//failed disarm
							preformedEvents = preformedEvents + ("\n" + "You fail to disarm the gas trap, it sets it off, dealing "+TriggerTrap(2)+" to you.");
							DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(10);
						}
					}else if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==8){
						if (0 + (int)(Math.random() * ((1 - 0)))==1){//coin flip to disarm or trigger
							DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(9);
							preformedEvents = preformedEvents + ("\n" + "You disarmed the trap");
						}else{//failed disarm
							preformedEvents = preformedEvents + ("\n" + "You fail to disarm the spawner trap, it sets it off, causing a  "+DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+" to drop into the room.");
							TriggerTrap(3);
							DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(10);
						}
					}
				//check if player is attacking a monster
				}else if ((action.toLowerCase().contains("strike") == true)||(action.toLowerCase().contains("attack") == true)||(action.toLowerCase().contains("swing") == true)){
					if ((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMonster()==1)&&(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterActive()==true)){
						int dmg =1 + (int)(Math.random() * ((10 - 1)));
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].MonsterDamaged(dmg);
						preformedEvents = preformedEvents + ("\n" + "You swing at the "+DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+" with all your might. You deal "+dmg+" damage to it.");
						if ((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterHealth() <= 0)){
							if((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==1)||(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==2)){
								DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasMimic(3);
								int income = 10 + (int)(Math.random() * ((40 - 10)));
								preformedEvents = preformedEvents + ("\n" +"You have slain the fowl mimic. You get " + income + " gold from it's remains.");
								Character.setGold(Character.getGold()+income);
							}else if((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()!=1)&&(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()!=2)){
								DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasMonster(2);
								int income = 1 + (int)(Math.random() * ((10 - 1)));
								preformedEvents = preformedEvents + ("\n" +"You have slain the "+DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+". You get " + income + " gold from it's remains.");
								Character.setGold(Character.getGold()+income);
							}
						}
					}else if ((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==1)||(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==2)){
						int dmg =1 + (int)(Math.random() * ((10 - 1)));
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].MonsterDamaged(dmg);
						preformedEvents = preformedEvents + ("\n" + "You swing at the "+DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+" with all your might. You deal "+dmg+" damage to it.");
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setMonsterActive(true);
						if ((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterHealth() <= 0)){
							if((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==1)||(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==2)){
								DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasMimic(3);
								int income = 10 + (int)(Math.random() * ((40 - 10)));
								preformedEvents = preformedEvents + ("\n" +"You have slain the fowl mimic. You get " + income + " gold from it's remains.");
								Character.setGold(Character.getGold()+income);
							}else if((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()!=1)&&(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()!=2)){
								DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasMonster(2);
								int income = 1 + (int)(Math.random() * ((10 - 1)));
								preformedEvents = preformedEvents + ("\n" +"You have slain the "+DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+". You get " + income + " gold from it's remains.");
								Character.setGold(Character.getGold()+income);
							}
						}
					}
					
					
				//check if player is attacking a chest
				}else if (((action.toLowerCase().contains("strike") == true)||(action.toLowerCase().contains("attack") == true)||(action.toLowerCase().contains("swing") == true))&&DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasChest()==1){
					preformedEvents = preformedEvents + ("\n" + "You smash the chest into peices, destroying whatever was inside.");
					DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasChest(3);
				}else if (action.toLowerCase().contains("potion") == true){//check if drinking potion.
					int heal = 11 + (int)(Math.random() * ((20 - 11)));
					Character.setHealth(Character.getHealth()+heal);
					preformedEvents = preformedEvents + ("\n" + "You drink a potion, healing you for " +heal+" health.");
					Character.setPotions(Character.getPotions()-1);
				}else if ((action.toLowerCase().contains("open") == true)||(action.toLowerCase().contains("chest") == true)){
					if ((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==1)||(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==2)){
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(10);
						preformedEvents = preformedEvents + ("\n" + "You trigger a spike trap, it deals "+TriggerTrap(1)+" to you.");
					}else if ((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==4)||(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==5)){
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(10);
						preformedEvents = preformedEvents + ("\n" + "You trigger a gas trap, it "+TriggerTrap(2)+" to you.");
					}else if ((DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==7)||(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasTrap()==8)){
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasTrap(10);
						preformedEvents = preformedEvents + ("\n" + "You set off a spawner trap, causing a  "+DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+" to drop into the room.");
						TriggerTrap(3);
					}
					
					if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasChest()==1){
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setHasChest(2);
						int temp = 0 + (int)(Math.random() * ((9 - 0)));
						if (temp <= 4){
							int income = 5 + (int)(Math.random() * ((15 - 5)));
							preformedEvents = preformedEvents + ("\n" + "You open the chest. There is "+income+" gold inside!");
							Character.setGold(Character.getGold()+income);
						}else if ((temp == 5)||(temp == 6)||(temp == 7)){
							int income = 1 + (int)(Math.random() * ((3 - 1)));
							preformedEvents = preformedEvents + ("\n" + "You open the chest. There are "+income+" potions inside!");
							Character.setPotions(Character.getPotions()+income);
						}else if (temp >= 8){
							preformedEvents = preformedEvents + ("\n" + "You open the chest. There an armor upgrade inside! Your defense has increased by two!");
							Character.setArmor(Character.getArmor()+2);
						}
					}else if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasChest()==2){
						preformedEvents = preformedEvents + ("\n" + "No matter how many times you open and close the chest,there is no new loot in it.");
					}else if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasChest()==3){
						preformedEvents = preformedEvents + ("\n" + "You attempt to put the splinters of the chest back together to open it. You fail.");
					}else if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==1){
						preformedEvents = preformedEvents + ("\n" + "The chest was a mimic! As soon as you try to open it, it gets a free attack off on you.");
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setMonsterActive(true);
						int dmg = (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].MonsterAttack()-Character.getArmor()); 
						Character.setHealth(Character.getHealth()-dmg);
						preformedEvents = preformedEvents + ("\n" + DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+" Attacks you. It deals " + dmg + " damage to you.");
					}else if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getHasMimic()==2){
						preformedEvents = preformedEvents + ("\n" + "The chest was a mimic! As soon as you try to open it, it gets a free attack off on you.");
						DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setMonsterActive(true);
						int dmg = (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].MonsterAttack()-Character.getArmor()); 
						Character.setHealth(Character.getHealth()-dmg);
						preformedEvents = preformedEvents + ("\n" + DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getMonsterName()+" Attacks you. It deals " + dmg + " damage to you.");
					}
				}else if (action.toLowerCase().contains("north") == true){//moving room
					justEntered = true;
					JOptionPane.showMessageDialog(null,MoveRoom(1));
				}else if (action.toLowerCase().contains("south") == true){//moving room
					justEntered = true;
					JOptionPane.showMessageDialog(null,MoveRoom(3));
				}else if (action.toLowerCase().contains("east") == true){//moving room
					justEntered = true;
					JOptionPane.showMessageDialog(null,MoveRoom(2));
				}else if (action.toLowerCase().contains("west") == true){//moving room
					justEntered = true;
					JOptionPane.showMessageDialog(null,MoveRoom(4));
				}else if (((action.toLowerCase().contains("down") == true)||(action.toLowerCase().contains("ladder") == true))&&DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getLadder()==true){
					justEntered = true;
					JOptionPane.showMessageDialog(null,"You climb down the ladder into the depths of the dungeon.");
					Character.setLastZ(Character.getLastZ()+1);
				}else{
					preformedEvents = preformedEvents + ("\n" + "You cant do that. Try entering it in a different way.");
				}
			}else if (DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getIsBoss()==true){
				int question = 1;
				if (justEntered == true){
					action = getStringInput(DescribeRoom()+playerStatus()+"\n The succubus stands and walks over to you, making no attempts to seduce you suprisingly. It grins as it stands just out of striking distance.\n 'So you've come to slay the big bad demon huh? Well,  its not going to be easy for you. Make any sudden moves and I will kill you instantly.'\n'Your going to answer my questions. Get any wrong and your dead. if you get them correct however, I will let you kill me.'\n'First Question. What is your name?");
					justEntered = false;
					preformedEvents = "";
				}else{
					action = getStringInput(preformedEvents+playerStatus());
					preformedEvents = "";
				}
				if (action.toLowerCase().contains("quit") == true){
					SaveGame();
					JOptionPane.showMessageDialog(null,"See you next time player! your game has been saved!");
					exitprogram = true;
					break;
				}
				
				if (question == 1){
					if (action.toLowerCase().contains(Character.getName()) == true){
						preformedEvents = preformedEvents + ("\n" +"'Good to know you arent incompetent sugar. Next question. How many floors are in this dungeon?'");
						question = 2;
					}else if ((action.toLowerCase().contains("strike") == true)||(action.toLowerCase().contains("attack") == true)||(action.toLowerCase().contains("swing") == true)){
						//death
						JOptionPane.showMessageDialog(null,"'Nice try, but your weak sugar. Watch a real warrior in action.'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}else if (action.toLowerCase().contains("flirt") == true){//death
						JOptionPane.showMessageDialog(null,"'Well now. Looks like we have a horny adventurer. Too bad I've grown sick of that sugar, and sick of horny people like you.'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}else{//death
						JOptionPane.showMessageDialog(null,"'Wrong answer. Thanks for trying!'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}
				}else if (question == 2){
					if ((action.toLowerCase().contains("5") == true)||(action.toLowerCase().contains("five") == true)){
						preformedEvents = preformedEvents + ("\n" +"'Perceptive are we? Good to know. Lets see if you were perceptive enough.'\n'train 1 is moving WEst At a speed of fouRty milEs per hour. meaNwhile train 2 is moving at an Unknown speed towards train 1'\n'a nearby Monster truck is moving at the same speed as train 2, which happens to BE the same numbeR of miles per hOur as there are rooms in this duNgEon.'\n'What is my favorite color?'");
						//this is meant to cause the player to likely forget the answer which is the color of the tapestries when you enterd the room. The capitalized letters in the question spell out a phrase for an easter egg.
						question = 3;
					}else if ((action.toLowerCase().contains("strike") == true)||(action.toLowerCase().contains("attack") == true)||(action.toLowerCase().contains("swing") == true)){
						//death
						JOptionPane.showMessageDialog(null,"'Nice try, but your weak sugar. Watch a real warrior in action.'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}else if (action.toLowerCase().contains("flirt") == true){//death
						JOptionPane.showMessageDialog(null,"'Well now. Looks like we have a horny adventurer. Too bad I've grown sick of that sugar, and sick of horny people like you.'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}else{//death
						JOptionPane.showMessageDialog(null,"'Wrong answer. Thanks for trying!'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}
				}else if (question == 3){
					if (action.toLowerCase().contains(DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].getBossColor()) == true){
						preformedEvents = preformedEvents + ("\n" +"'Color me impressed human. You have answered all three questions sucessfully. A demon always keeps their promise, you may attempt to smite me down now.'");
						question = 4;
					}else if ((action.toLowerCase().contains("strike") == true)||(action.toLowerCase().contains("attack") == true)||(action.toLowerCase().contains("swing") == true)){
						//death
						JOptionPane.showMessageDialog(null,"'Nice try, but your weak sugar. Watch a real warrior in action.'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}else if (action.toLowerCase().contains("flirt") == true){//death
						JOptionPane.showMessageDialog(null,"'Well now. Looks like we have a horny adventurer. Too bad I've grown sick of that sugar, and sick of horny people like you.'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}else if (action.toLowerCase() == "we are number one"){
						JOptionPane.showMessageDialog(null,"'Wait, what?'");
						JOptionPane.showMessageDialog(null,"A giant net smashes through the roof of the room, ensnaring the demon before they can do anything. You hear a heavenly voice from above 'Now look at this net, that I just found.'\nThe succubus attmpts to break out of the net, freeing one arm and attempting to slash at you before being lifted into the air as a ray of light shines down. 'No don't touch that!'\nThe ray of light harshens, shining only over the succubus as it fries her in a holy light, causing her to disintergate.\nThe net vanishes, leaving only a rotten apple in its place, sitting ontop of the piles of ashes that used to be the demon.");
						JOptionPane.showMessageDialog(null,"CONGLADURATIONS!\n You win and managed to get out with " + Character.getGold()+" gold!\nYou return to the village triumphent, a festival is held to celebrate your victory, with your wedding the next day.");
						Character.Reset();
						ResetMap();
						exitprogram = true;
						break;
					}else{//death
						JOptionPane.showMessageDialog(null,"'Wrong answer. Thanks for trying!'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}
				}else if (question == 4){
					if ((action.toLowerCase().contains("strike") == true)||(action.toLowerCase().contains("attack") == true)||(action.toLowerCase().contains("swing") == true)){
						JOptionPane.showMessageDialog(null,"You thrust the sword deep into the succubus's chest, through her heart. The sword glows a dazzling white, the compass embedded into the hilt spawns two large balls of white light.\nThe balls shape into two more holy swords that proceed to drive themselves into the  succubus's head. The demon starts to convulse before disintergrating into ash right before your eyes.");
						JOptionPane.showMessageDialog(null,"CONGLADURATIONS!\n You win and managed to get out with " + Character.getGold()+" gold!\nYou return to the village triumphent, a festival is held to celebrate your victory, with your wedding the next day.");
						Character.Reset();
						ResetMap();
						exitprogram = true;
						break;
					}else{//death
						JOptionPane.showMessageDialog(null,"'Your going to spare me? Cute, but a bad idea.'\nWith one swift movement, she slices your head straight off with a demonic dagger. The last thing you see before you die and your soul is claimed is the succubus's lovely chest.");
						GameOver();
						exitprogram = true;
						break;
					}
				}
			}
		}	
	}
	public static String DescribeRoom(){
		String compiledString = DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].Describe();
		if ((Character.getLastX() == 0)&&(Character.getLastY() == 0)){//Makes sure the program isnt going to try and check outside the array.
			if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the south";
			} else if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the east";
			}
		}else if ((Character.getLastX() == 9)&&(Character.getLastY() == 0)){//Makes sure the program isnt going to try and check outside the array.
			if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the south";
			}if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the west";
			}
		}else if ((Character.getLastX() == 0)&&(Character.getLastY() == 9)){//Makes sure the program isnt going to try and check outside the array.
			if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the north";
			}if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the east";
			}
		}else if ((Character.getLastX() == 9)&&(Character.getLastY() == 9)){//Makes sure the program isnt going to try and check outside the array.
			if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the north";
			}if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the west";
			}
		}else if (Character.getLastX() == 0){//Makes sure the program isnt going to try and check outside the array.
			if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the north";
			}if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the south";
			}if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the east";
			}
		}else if (Character.getLastX() == 9){//Makes sure the program isnt going to try and check outside the array.
			if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the north";
			}if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the south";
			}if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the west";
			}
		}else if (Character.getLastY() == 0){//Makes sure the program isnt going to try and check outside the array.
			if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the south";
			}if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the east";
			}if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the west";
			}
		}else if (Character.getLastY() == 9){//Makes sure the program isnt going to try and check outside the array.
			if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the north";
			}if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the east";
			}if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the west";
			}
		}else{//Makes sure the program isnt going to try and check outside the array.
			if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the north";
			}if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the south";
			}if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the east";
			}if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
				compiledString = compiledString + "\nThere is a door to the west";
			}
		}
		return compiledString;
	}
	public static String playerStatus(){
		String compiledMessage = null;
		compiledMessage =("\nYou have " + Character.getHealth() + " health left.");
		compiledMessage =compiledMessage +("\nYou have " + Character.getGold() + " total gold peices.");
		compiledMessage =compiledMessage +("\nYou have " + Character.getPotions() + " potions left.");
		return compiledMessage;
	}
	public static void GameOver() throws IOException{
		JOptionPane.showMessageDialog(null,"You have died. \nYou managed to get a total of " + Character.getGold()+" gold and traversed to floor number " +(Character.getLastZ()+1)+"\nNow the town waits for a new hero to arrive. Perhaps after a reload you can be the hero.");
		Character.Reset();
		ResetMap();
		startX = 0 + (int)(Math.random() * ((9 - 0)));
		startY = 0 + (int)(Math.random() * ((9 - 0)));
		Generate(startX,startY,0,1);
		SaveGame();
	}
	public static void ResetMap(){
		for (int z = 0; z < 5; z++){//three loops are run. one is X, one is Y, and one is Z for the dungeon map co-ordinates. Loads all settings for each room and puts them in to each one.
			for (int y = 0; y < 10; y++){
				for (int x = 0; x < 10; x++){
					DungeonMap[x][y][z] = new Room();
				}
			}
		}
	}
	public static int TriggerTrap(int type){
		if (type == 1){
			int dmg = (5-Character.getArmor());
			Character.setHealth(Character.getHealth()-dmg);
			return dmg;
		} else if (type == 2){
			Character.setHealth(Character.getHealth()-5);
			return 5;
		} else if (type == 3){
			DungeonMap[Character.getLastX()][Character.getLastY()][Character.getLastZ()].setMonsterActive(true);
			return 0;
		} else {
			return 0;
		}
	}
	public static String MoveRoom(int direction){
		String movement = "";
		if ((Character.getLastX() == 0)&&(Character.getLastY() == 0)){//Makes sure the program isnt going to try and check outside the array.
			if (direction == 1){//north
				movement = "you can't walk through walls";
			}else if (direction == 2){//east
				if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()+1);
					movement = "you go through the east door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 3){//south
				if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()+1);
					movement = "you go through the south door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 4){//west
				movement = "you can't walk through walls";
			}
		}else if ((Character.getLastX() == 9)&&(Character.getLastY() == 0)){//Makes sure the program isnt going to try and check outside the array.
			if (direction == 1){//north
				movement = "you can't walk through walls";
			}else if (direction == 2){//east
				movement = "you can't walk through walls";
			}else if (direction == 3){//south
				if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()+1);
					movement = "you go through the south door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 4){//west
				if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()-1);
					movement = "you go through the west door";
				}else{
					movement = "you can't walk through walls";
				}
			}
		}else if ((Character.getLastX() == 0)&&(Character.getLastY() == 9)){//Makes sure the program isnt going to try and check outside the array.
			if (direction == 1){//north
				if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()-1);
					movement = "you go through the north door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 2){//east
				if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()+1);
					movement = "you go through the east door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 3){//south
				movement = "you can't walk through walls";
			}else if (direction == 4){//west
				movement = "you can't walk through walls";
			}
		}else if ((Character.getLastX() == 9)&&(Character.getLastY() == 9)){//Makes sure the program isnt going to try and check outside the array.
			if (direction == 1){//north
				if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()-1);
					movement = "you go through the north door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 2){//east
				movement = "you can't walk through walls";
			}else if (direction == 3){//south
				movement = "you can't walk through walls";
			}else if (direction == 4){//west
				if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()-1);
					movement = "you go through the west door";
				}else{
					movement = "you can't walk through walls";
				}
			}
		}else if (Character.getLastX() == 0){//Makes sure the program isnt going to try and check outside the array.
			if (direction == 1){//north
				if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()-1);
					movement = "you go through the north door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 2){//east
				if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()+1);
					movement = "you go through the east door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 3){//south
				if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()+1);
					movement = "you go through the south door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 4){//west
				movement = "you can't walk through walls";
			}
		}else if (Character.getLastX() == 9){//Makes sure the program isnt going to try and check outside the array.
			if (direction == 1){//north
				if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()-1);
					movement = "you go through the north door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 2){//east
				movement = "you can't walk through walls";
			}else if (direction == 3){//south
				if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()+1);
					movement = "you go through the south door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 4){//west
				if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()-1);
					movement = "you go through the west door";
				}else{
					movement = "you can't walk through walls";
				}
			}
		}else if (Character.getLastY() == 0){//Makes sure the program isnt going to try and check outside the array.
			if (direction == 1){//north
				movement = "you can't walk through walls";
			}else if (direction == 2){//east
				if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()+1);
					movement = "you go through the east door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 3){//south
				if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()+1);
					movement = "you go through the south door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 4){//west
				if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()-1);
					movement = "you go through the west door";
				}else{
					movement = "you can't walk through walls";
				}
			}
		}else if (Character.getLastY() == 9){//Makes sure the program isnt going to try and check outside the array.
			if (direction == 1){//north
				if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()-1);
					movement = "you go through the north door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 2){//east
				if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()+1);
					movement = "you go through the east door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 3){//south
				movement = "you can't walk through walls";
			}else if (direction == 4){//west
				if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()-1);
					movement = "you go through the west door";
				}else{
					movement = "you can't walk through walls";
				}
			}
		}else{//Makes sure the program isnt going to try and check outside the array.
			if (direction == 1){//north
				if (DungeonMap[Character.getLastX()][Character.getLastY()-1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()-1);
					movement = "you go through the north door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 2){//east
				if (DungeonMap[Character.getLastX()+1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()+1);
					movement = "you go through the east door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 3){//south
				if (DungeonMap[Character.getLastX()][Character.getLastY()+1][Character.getLastZ()].getUsed() == true){
					Character.setLastY(Character.getLastY()+1);
					movement = "you go through the south door";
				}else{
					movement = "you can't walk through walls";
				}
			}else if (direction == 4){//west
				if (DungeonMap[Character.getLastX()-1][Character.getLastY()][Character.getLastZ()].getUsed() == true){
					Character.setLastX(Character.getLastX()-1);
					movement = "you go through the west door";
				}else{
					movement = "you can't walk through walls";
				}
			}
		}
		return movement;
	}
}
