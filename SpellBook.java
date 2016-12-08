import java.util.*;
import java.io.*;



class SpellBook{

	private static HashMap <String, Spell> spells = new HashMap <String, Spell>();
	private static HashMap <String, Karakter> karakterer = new HashMap <String, Karakter>();
	public static ArrayList <String> spellList = new ArrayList <String>();
	public static HashMap <String, Feat> feats = new HashMap <String, Feat>();

	public static void main(String[] args) throws Exception{
		lesSpellList("SpellList.txt");
		lesSpellDescs("SpellDescs.txt");
		lesKarakterer("Characters.txt");
		lesFeats("Feats.txt");
		meny();
	}

	static void lesSpellList(String filnavn) throws Exception{
		File fil = new File(filnavn);
		Scanner in = new Scanner(fil);
		while(in.hasNextLine()){
			spellList.add(in.nextLine());
		}
		
	}

	static void lesSpellDescs(String filnavn) throws Exception{

		File fil = new File (filnavn);
		Scanner in = new Scanner(fil);

		while(in.hasNextLine()){
		
			for(int i = 0; i < spellList.size(); i++){

				String spellCheck = spellList.get(i);
				String spellNavn = in.nextLine();

				

				if(spellNavn.equals(spellCheck)){
					String levelString = in.nextLine();
					int level = Integer.parseInt(levelString);
					String school = in.nextLine();
					String ritual = in.nextLine();
					String casttime;
					String range;
					String components;
					String duration;
					HashMap <String, String> klasser = new HashMap <String, String>();
					String description = "";
					if(ritual.equalsIgnoreCase("ritual")){
						casttime = in.nextLine();
						range = in.nextLine();
						components = in.nextLine();
						duration = in.nextLine();
						String klassene[] = in.nextLine().split(",");
						for(String klassName : klassene){
							klasser.put(klassName, klassName);
						}
						loopen: while(true){
							if(in.hasNextLine()){
								String sjekk = in.nextLine();
								if(sjekk.equals("Spell:")){
									break loopen;
								}
								else{
									description = description + sjekk;
								}
							}
							else{
								break loopen;
							}
						}
					}
					else{
						casttime = ritual;
						ritual = "Not a ritual";

						
						range = in.nextLine();
						components = in.nextLine();
						duration = in.nextLine();
						String klassene[] = in.nextLine().split(",");
						for(String klassName : klassene){
							klasser.put(klassName, klassName);
						}

						loopen2: while(true){
							if(in.hasNextLine()){
								String sjekk = in.nextLine();
								if(sjekk.equals("Spell:")){
									break loopen2;
								}
								else{
									description = description + sjekk;
								}
							}
							else{
								break loopen2;
							}
						}
					}
				
					Spell ny = new Spell(spellCheck, level, school, ritual, casttime, range, components, duration, klasser, description);
					spells.put(spellCheck, ny);	
				}
				else{
					System.out.println("SpellDescs2: 	" + spellNavn + "\nSpellList2:	" + spellCheck);
					break;
					
				}
			}
		}
	}

	static void lesKarakterer(String filnavn) throws Exception{
		File fil = new File (filnavn);
		Scanner in = new Scanner(fil);

		



		while(in.hasNextLine()){
			String navn;
			String rase;
			String klasse;

			int level;
			int spellSlots;
			int spellSlotsLevel;

			String spellCastingAbility;
			int spellSaveDC;
			int spellAttackBonus;
			ArrayList <String> knownSpells = new ArrayList <String>();

			navn = in.nextLine();
			rase = in.nextLine();
			klasse = in.nextLine();
			level = Integer.parseInt(in.nextLine());
			spellSlots = Integer.parseInt(in.nextLine());
			spellSlotsLevel = Integer.parseInt(in.nextLine());
			spellCastingAbility = in.nextLine();
			spellSaveDC = Integer.parseInt(in.nextLine());
			spellAttackBonus = Integer.parseInt(in.nextLine());
			
			whilen: while(true){
				if(in.hasNextLine()){
					String sjekk = in.nextLine();
					if(sjekk.equals("Character:")){
						break whilen;
					}
					else{
						knownSpells.add(sjekk);
					}
				}
				else{
					break whilen;
				}
			}

			Karakter ny = new Karakter(navn, rase, klasse, level, spellSlots, spellSlotsLevel, spellCastingAbility, spellSaveDC, spellAttackBonus);
			foren: for(int i = 0; i < knownSpells.size(); i++){
				String spellen = knownSpells.get(i);
				if(spells.size() == 0){
					break foren;
				}
				else{
					if(spells.get(spellen) == null){
						if(spellen.equals("")){

						}
						else{
							System.out.println("Could not find the spell '" + spellen + "'. The spell has not been added to " + navn + ".");
						}
					}
					else{
						if(ny.spellList.size() == 0){
							Spell magi = spells.get(spellen);
							ny.spellList.put(spellen, magi);
						}
						else if(ny.spellList.get(spellen) == null){
							Spell magi = spells.get(spellen);
							ny.spellList.put(spellen, magi);
						}
					}
					
				}
				
			}
			karakterer.put(navn, ny);
		}
	}

	static void lesFeats(String filnavn) throws Exception{
		File fil = new File (filnavn);
		Scanner in = new Scanner(fil);
		
		while(in.hasNextLine()){
			String navn;
			String description = "";

			navn = in.nextLine();

			loopen: while(true){
				if(in.hasNextLine()){
					String sjekk = in.nextLine();
					if(sjekk.equals("Feat:")){
						break loopen;
					}
					else{
						description = description + sjekk;
					}
				}
				else{
					break loopen;
				}
			}
			Feat ny = new Feat(navn, description);
			feats.put(navn, ny);
		}
	}



	public static void meny(){
		Scanner sc = new Scanner (System.in);
		
		loop: while(true){
			System.out.println("\n\nType \"V\" to go to the 'view-menu'\nType \"AC\" to add a new character\nType \"AS\" To add a spell\nType \"ES\" to edit spells\nType \"EC\" to edit characters\nType \"VCS\" to view a character's spells\nType \"ACS\" to add a spell to a character\nType \"exit\" to leave the program.\n");
			String valg = sc.nextLine();
			valg = valg.toUpperCase();

			switch(valg){
				case "AC": 
				registrerKarakter();
				break;
				case "AS":
				registrerSpell();
				break;
				case "VCS":
				if(karakterer.size() == 0){
					System.out.println("\nNo characters registered yet! Please register a character before trying to view a character's spells.");
				}
				else{
					System.out.println("\nWhich characters spells do you want to view? (Case sensitive!)");
					String kar = sc.nextLine();
					if(karakterer.get(kar) == null){
						System.out.println("Could not find said character. Please try again. ");
					}
					else{
						System.out.println(karakterer.get(kar).visSpells());
					}
				}
				break;
				case "ACS":
				if(karakterer.size() == 0){
					System.out.println("\nNo characters registered yet! Please register a character before trying to add to a character's spells.");
				}
				else{
					System.out.println("\nWhich character do you want to add a spell to? (Case sensitive!)");
					String kar = sc.nextLine();
					if(karakterer.get(kar) == null){
						System.out.println("Could not find said character. Please try again.");
					}
					else{
						if(spells.size() == 0){
							System.out.println("There are no spells registered yet! Please register a spell first.");
						}
						else{
							System.out.println("Which spell do you want to add to this character? (Case sensitive!)");
							int teller = 0;
							for(Spell s : spells.values()){
								if(teller < 6){
									System.out.print(s.getName() + " ");
									teller++;
								}
								else{
									System.out.println(s.getName());
									teller = 0;
								}
							}
							String spel = sc.nextLine();
							if(spells.get(spel) == null){
								System.out.println("The spell you are trying to add is not in the spell list. Register it first.");
							}
							else{
								karakterer.get(kar).addSpell(spells.get(spel));
								System.out.println(spel + " added to " + kar + "!");
							}
						}

					}
				}
				break;
				case "V":
				viewMenu();
				break;
				case "ES":
				editSpell();
				break;
				case "EC":
				editCharacter();
				break;
				case "EXIT":
				break loop;
				default:
				System.out.println("Unknown command! Please try again.");
				break;

				
			}
		}
	}

	public static void registrerKarakter(){
		Scanner sc = new Scanner (System.in);

		String charName;
		String charRace;
		String charClass;
		int charLevel;
		int charSpellSlots;
		int charSpellSlotsLevel;

		String charSpellAbility;
		int charSpellDC;
		int charSpellAttack;
		
		System.out.println("Character name:");
		charName = sc.nextLine();

		if(karakterer.size() != 0){
			if(karakterer.get(charName) != null){
				System.out.println("Character " + charName + " is already registered!");
				return;
			}
		}

		System.out.println("Character race:");
		charRace = sc.nextLine();

		System.out.println("Character class:");
		charClass = sc.nextLine();

		System.out.println("Character level (just the number):");
		charLevel = Integer.parseInt(sc.nextLine());

		System.out.println("Character spell slots (just the number):");
		charSpellSlots = Integer.parseInt(sc.nextLine());

		System.out.println("The level for characters " + charSpellSlots + " spellslots (just the number):");
		charSpellSlotsLevel = Integer.parseInt(sc.nextLine());

		System.out.println("Character spellcasting ability:");
		charSpellAbility = sc.nextLine();

		System.out.println("Character spell save DC (just the number):");
		charSpellDC = Integer.parseInt(sc.nextLine());

		System.out.println("Character spell attack bonus (just the number):");
		charSpellAttack = Integer.parseInt(sc.nextLine());

		Karakter nyKarakter = new Karakter(charName, charRace, charClass, charLevel, charSpellSlots, charSpellSlotsLevel, charSpellAbility, charSpellDC, charSpellAttack);
		karakterer.put(charName, nyKarakter);
		System.out.println("Added " + charName + " to registered characters!");
	}

	public static void registrerSpell(){
		Scanner sc = new Scanner (System.in);

		String spellName;
		int spellLevel;
		String spellSchool;
		String spellRitual;
		String spellCastTime;
		String spellRange;
		String spellComponents;
		String spellDuration;
		HashMap <String, String> spellClasses = new HashMap <String, String>();
		String spellDescription;

		System.out.println("What is the spell's name? (Remember that this is case sensitive for future treatments of this spell, if you for instance want to add it to a character or something.\n");
		spellName = sc.nextLine();
		if(spells.size() > 0){
			if(spells.get(spellName) != null){
				System.out.println("Spell " + spellName + " is already registered in the spell list!");
				return;
			}
		}

		System.out.println("What is the spell's level?");
		String levelToInt = sc.nextLine();
		spellLevel = Integer.parseInt(levelToInt);

		System.out.println("What is the spell's school?");
		spellSchool = sc.nextLine();

		while(true){
			System.out.println("Is the spell a ritual? 'Y' or 'N' (Yes or No):");
			String valg = sc.nextLine();
			if(valg.equalsIgnoreCase("Y")){
				spellRitual = "Ritual";
				break;
			}
			else if(valg.equalsIgnoreCase("N")){
				spellRitual = "Not a ritual";
				break;
			}
			else{
				System.out.println("");
			}
		}

		System.out.println("What is the spell's cast time? (1 action, for instance)");
		spellCastTime = sc.nextLine();

		System.out.println("What is the spell's components? (V, S, M(x))");
		spellComponents = sc.nextLine();

		System.out.println("What is the spell's description? (includes what the spell does, damage dice (increased damage dice)\nand other effects)");
		spellDescription = sc.nextLine();
		while(true){
			System.out.println("Is the description done? Y or N (Yes or No):");
			String valg = sc.nextLine();
			if(sc.nextLine().equals("Y")){
				break;
			}
			else if(sc.nextLine().equals("N")){
				System.out.println("Please add more to the description. Please note that this inserts a new line into the spell's description!");
				String addToDesc = sc.nextLine();
				spellDescription = spellDescription + "\n\n" + addToDesc;
			}
			else{
				System.out.println("");
			}
		}


		System.out.println("What is the spell's duration? (Concentration, instantaneous, rounds, hours, minutes and so on.)");
		spellDuration = sc.nextLine();

		

		System.out.println("What is the spell's range? (just the number, in feet)");
		spellRange = sc.nextLine() + " feet";

		

		System.out.println("What classes can learn this spell? Please write down one at a time.");
		while(true){
			System.out.println("Type 'done' when you're done adding classes to this spell. Otherwise, just type in a class to be added!");
			String valg = sc.nextLine();
			if(valg.equals("done")){
				break;
			}
			else{
				spellClasses.put(valg, valg);
			}
		}

		Spell ny = new Spell(spellName, spellLevel, spellSchool, spellRitual, spellCastTime, spellRange, spellComponents, spellDuration, spellClasses, spellDescription);
		spells.put(spellName, ny);
		spellList.add(spellName);
		System.out.println("Spell \"" + spellName + "\" added to the list! It can now be assigned to a character.");


		

	}

	public static void editSpell(){
		Scanner sc = new Scanner(System.in);
		boolean edit = false;
		String spellName;
		while(true){

			System.out.println("\nWhat spell do you want to edit? Type \"none\" when you are finished editing spells, or \"view\" to see a view of available spells.");
			spellName = sc.nextLine();
			edit = false;
			
			if(spellName.equalsIgnoreCase("none")){
				return;
			}
			else if(spellName.equalsIgnoreCase("view")){
				int teller = 0;
				for(Spell s : spells.values()){
					if(teller < 6){
						System.out.print(s.getName() + " // ");
						teller++;
					}
					else{
						System.out.println(s.getName());
						System.out.println("");
						teller = 0;
					}
				}
				System.out.println("Total: " + spells.size());

			}
			else if(spells.size() > 0){
				for(int i = 0; i < spells.size(); i++){
					if(spells.get(spellName) == null){
						System.out.println("Spell is not registered in the spell list!");
						return;
					}
				}
				edit = true;
			}
			else{
				System.out.println("Unknown command! Try again. Type \"done\" when you are done editing spells.");
			}
			loop: while(edit == true){
			System.out.println("\nWhat aspect of " + spellName + " do you wish to edit? Type one of the following choices:\n\n 	Name\n	Level\n		School\n 	Ritual\n 	Cast time\n 	Range\n 	Components\n 	Duration\n 	Classes\n 	Description\n\nType \"done\" when you are finished editing, or \"view\" to see all spells again.");

			String valg = sc.nextLine();
			valg = valg.toLowerCase();

			switch(valg){
				case "name":
				System.out.println("Type in the new name for " + spellName + ":");
				String newName = sc.nextLine();
				spells.get(spellName).changeName(newName);
				spells.put(newName, spells.get(spellName));
				spells.remove(spellName);
				System.out.println("Name for " + spellName + " changed to " + newName + ".");
				spellName = newName;
				break;
				case "cast time":
				System.out.println("Type in the new cast time for " + spellName + ":");
				String newCast = sc.nextLine();
				spells.get(spellName).changeCast(newCast);
				System.out.println("Cast time for " + spellName + " updated to " + newCast + ".");
				break;
				case "components":
				System.out.println("Type in the new components for " + spellName + ":");
				String spellComp = sc.nextLine();
				spells.get(spellName).changeComp(spellComp);
				System.out.println("Components for " + spellName + " has been changed to " + spellComp + ".");
				break;
				case "description":
				System.out.println("\nDo you want to write an entirely new description, or add to the existing one?");
				System.out.println("The description for " + spellName + " is currently:\n");
				String oldDesc = spells.get(spellName).getDesc();
				System.out.println(oldDesc);
				System.out.println("\nChoose either \"Keep\" or \"New\":");
				String option = sc.nextLine();
				switch(option){
					case "keep":
					System.out.println("Write what is to be added to the end of the description of " + spellName + ":");
					String end = sc.nextLine();
					String appendDesc = oldDesc + " " + end;
					spells.get(spellName).changeDesc(appendDesc);
					System.out.println("Description for " + spellName + " has been updated!");
					break;
					case "new":
					System.out.println("Write the new description for " + spellName + ":");
					String newDesc = sc.nextLine();
					spells.get(spellName).changeDesc(newDesc);
					System.out.println("Description for " + spellName + " has been updated!");
					break;
				}
				break;
				case "duration":
				System.out.println("Type the new duration for " + spellName + ":");
				String newDur = sc.nextLine();
				spells.get(spellName).changeDur(newDur);
				System.out.println("Duration for " + spellName + " has been updated!");
				break;
				case "level":
				System.out.println("Type in the new level for " + spellName + ":");
				String newLvl = sc.nextLine();
				int intNewLvl = Integer.parseInt(newLvl);
				spells.get(spellName).changeLevel(intNewLvl);
				System.out.println("Level for " + spellName + " has been updated to " + newLvl + "!");
				break;
				case "range":
				System.out.println("Type in the new range for " + spellName + ":");
				String newRng = sc.nextLine();
				spells.get(spellName).changeRange(newRng);
				System.out.println("Range for " + spellName + " has been updated to " + newRng + "!");
				break;
				case "school":
				System.out.println("Type in the new school for " + spellName + ":");
				String newSch = sc.nextLine();
				spells.get(spellName).changeSchool(newSch);
				System.out.println("School for " + spellName + " has been updated to " + newSch + "!");
				break;
				case "classes":
				while(true){
					System.out.println("Do you want to 'add' or 'remove' a class from the spell? Type 'done' when done editing.");
					System.out.println(spellName + " is known by these classes:");
					HashMap <String, String> klassene = spells.get(spellName).getClasses();
					for(String s : klassene.values()){
						int teller = 0;
						if(teller < 6){
							System.out.print(s + " // ");
							teller++;
						}
						else{
							System.out.println(s);
							System.out.println("");
							teller = 0;
						}
					}
					valg = sc.nextLine();
					if(valg.equals("add")){
						System.out.println("What class do you want to be able to use " + spellName + "?");
						String nyKlass = sc.nextLine();
						klassene.put(nyKlass, nyKlass);
					}
					else if(valg.equals("remove")){
						System.out.println("Which class would you like to unlearn " + spellName + "?");
						valg = sc.nextLine();
						if(klassene.get(valg) == null){
							System.out.println("Class does not know of this spell! Please choose a class which knows it.");
						}
						else{
							klassene.remove(valg);
						}
					}
					else if(valg.equals("done")){
						break;
					}
					else{
						System.out.println("Sorry, try again!");
					}
				}
				case "view":
				int teller = 0;
				for(Spell s : spells.values()){
					if(teller < 6){
						System.out.print(s.getName() + " // ");
						teller++;
					}
					else{
						System.out.println(s.getName());
						System.out.println("");
						teller = 0;
					}
					System.out.println("Total: " + spells.size());
				}
				case "done":
				break loop;
				default:
				System.out.println("Unknown command! Try again, or type \"done\" if you are done editing " + spellName);
				break;
				}
			}

		}

	}

	public static void viewSpells(){
		Scanner sc = new Scanner(System.in);
		String spellName;
		int teller = 0;
		while(true){

			System.out.println("\nWhich spell do you want to get details on? Type \"none\" when you are finished viewing spells, or \"view\" to see a view of available spells.");
			spellName = sc.nextLine();

			if(spellName.equalsIgnoreCase("none")){
				return;
			}
			else if(spellName.equalsIgnoreCase("view")){
				for(Spell s : spells.values()){
					if(teller < 6){
						System.out.print(s.getName() + " // ");
						teller++;
					}
					else{
						System.out.println(s.getName());
						System.out.println("");
						teller = 0;
					}
				}
				System.out.println("Total: " + spells.size());
				teller = 0;
			}
			else if(spells.size() > 0){
				for(int i = 0; i < spells.size(); i++){
					if(spells.get(spellName) == null){
						System.out.println("Spell is not registered in the spell list!");
						return;
					}
					else if(spells.get(spellName).getName().equals(spellName)){
						System.out.println("\nDetailed view for " + spellName + ":\n");
						Spell detailed = spells.get(spellName);
						System.out.println(" 	Name: " + detailed.navn);
						System.out.println(" 	Level: " + detailed.level);
						System.out.println(" 	School: " + detailed.school);
						System.out.println(" 	Cast Time: " + detailed.castTime);
						System.out.println(" 	Range: " + detailed.range);
						System.out.println(" 	Components: " + detailed.components);
						System.out.println(" 	Duration: " + detailed.duration);
						System.out.println("	Classes: \n" + detailed.classes);
						System.out.println(" 	Description: " + detailed.description);
						System.out.println("");
						break;
					}
					
				}
			}
			else{
				System.out.println("Unknown command!");
				
			}
		}
	}

	public static void editCharacter(){
		Scanner sc = new Scanner(System.in);

		String charName;
		while(true){

			System.out.println("\nWhat character do you want to edit? Type \"none\" when you are finished editing characters.");
			charName = sc.nextLine();
			if(karakterer.size() > 0){
				for(int i = 0; i < karakterer.size(); i++){
					if(karakterer.get(charName) == null){
						System.out.println("Character is not registered in the character list!");
						return;
					}
				}
			}
			if(charName.equalsIgnoreCase("none")){
				return;
			}
			loop: while(true){
			System.out.println("\nWhat aspect of the character do you wish to edit? Choose one of the following:\n\nName\nLevel\nSpell Slots\n\nType \"done\" when you are finished editing, or \"view\" to see all characters again.");

			String valg = sc.nextLine();

			switch(valg){
				case "Name":
				System.out.println("Type in the new name for for " + charName + ".");
				String newName = sc.nextLine();
				karakterer.get(charName).changeName(newName);
				karakterer.put(newName, karakterer.get(charName));
				karakterer.remove(charName);
				System.out.println("Name for " + charName + " changed to " + newName + ".");
				break;
				case "Level":
				System.out.println("Type in the new level for for " + charName + " - only the number.");
				String newLevel = sc.nextLine();
				int level = Integer.parseInt(newLevel);
				karakterer.get(charName).changeLevel(level);
				System.out.println("Level for " + charName + " changed to " + level + ".");
				break;
				case "Spell Slots":
				System.out.println("Type in the new amount of spell slots for " + charName + ":");
				String newSlots = sc.nextLine();
				int slots = Integer.parseInt(newSlots);
				karakterer.get(charName).changeSpellSlots(slots);
				System.out.println("Spell slots for " + charName + " changed to " + slots + ".");
				break;
				case "Class":
				System.out.println("Type in the new class for " + charName + ":");
				String newCls = sc.nextLine();
				karakterer.get(charName).changeClass(newCls);
				System.out.println("Class for " + charName + " updated to " + newCls + ".");
				break;
				case "view":
				int teller = 0;
				for(Karakter k : karakterer.values()){
					if(teller < 6){
						System.out.print(k.getName() + " // ");
						teller++;
					}
					else{
						System.out.println(k.getName());
						System.out.println("");
						teller = 0;
					}
				}
				case "done":
				break loop;
				default:
				System.out.println("Unknown command! Try again, or type \"done\" if you are done editing " + charName);
				break;
				}
			}

		}

	}

	public static void viewCharacter(){
		Scanner sc = new Scanner(System.in);
		String charName;
		int teller = 0;
		while(true){

			System.out.println("\nWhich character do you want to get details on? Type \"none\" when you are finished viewing characters, or \"view\" to see a view of registered characters.");
			charName = sc.nextLine();

			if(charName.equalsIgnoreCase("none")){
				return;
			}
			else if(charName.equalsIgnoreCase("view")){
				for(Karakter k : karakterer.values()){
					if(teller < 6){
						System.out.print(k.getName() + " // ");
						teller++;
					}
					else{
						System.out.println(k.getName());
						System.out.println("");
						teller = 0;
					}
				}
				System.out.println("Total: " + karakterer.size());
				teller = 0;
			}
			else if(karakterer.size() > 0){
				for(int i = 0; i < karakterer.size(); i++){
					if(karakterer.get(charName) == null){
						System.out.println("Character is not registered in the spell list!");
						return;
					}
					else if(karakterer.get(charName).getName().equals(charName)){
						System.out.println("\nDetailed view for " + charName + ":\n");
						Karakter detailed = karakterer.get(charName);
						System.out.println(" 	Name: " + detailed.getName());
						System.out.println(" 	Level: " + detailed.getLevel());
						System.out.println(" 	Class: " + detailed.getClas());
						System.out.println(" 	Spell Slots: " + detailed.getSlots());
						System.out.println("");
						System.out.println("	Known spells: \n" + detailed.visSpells());
						break;
					}
					
				}
			}
			else{
				System.out.println("Unknown command!");
				
			}
		}
	}

	public static void viewMenu(){
		Scanner sc = new Scanner (System.in);
		String spellName;
		String charName;
		int teller = 0;
		
		loop: while(true){
			System.out.println("\nType \"VS\" to view all spells\nType \"VC\" to view all characters\nType \"VDS\" to get more detailed info on a spell\nType \"VDC\" to get more details on a character\n\nType \"done\" to go back to the main menu.\n");
			String valg = sc.nextLine();
			switch(valg){
				case "VS":
				for(Spell s : spells.values()){
					if(teller < 6){
						System.out.print(s.getName() + " // ");
						teller++;
					}
					else{
						System.out.println(s.getName());
						System.out.println("");
						teller = 0;
					}
				}
				System.out.println("Total: " + spells.size());
				teller = 0;
				break;
				case "VC":
				for(Karakter k : karakterer.values()){
					if(teller < 6){
						System.out.print(k.getName() + " // ");
						teller++;
					}
					else{
						System.out.println(k.getName());
						System.out.println("");
						teller = 0;
					}
				}
				System.out.println("Total: " + karakterer.size());
				teller = 0;
				break;
				case "VDS":
				viewSpells();
				break;
				case "VDC":
				viewCharacter();
				break;
				case "done":
				break loop;
				default:
				System.out.println("Unknown command!");
				break;

			}
		}
	}
}
