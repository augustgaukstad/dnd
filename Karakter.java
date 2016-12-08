import java.io.*;
import java.util.*;

class Karakter{

	String name;
	String race;
	String clas;

	int level;
	int spellSlots;
	int spellSlotsLevel;

	String spellCastingAbility;
	int spellSaveDC;
	int spellAttackBonus;
	
	int curSpellSlots;
	HashMap <String, Spell> spellList = new HashMap <String, Spell>();
	HashMap <String, Feat> featList = new HashMap <String, Feat>();

	public Karakter(String navn, String rase, String klas, int lvl, int slots, int slotsLvl, String ability, int savedc, int attackBonus){
		name = navn;
		race = rase;
		clas = klas;
		spellSlots = slots;
		level = lvl;
		spellSlotsLevel = slotsLvl;
		spellCastingAbility = ability;
		spellSaveDC = savedc;
		spellAttackBonus = attackBonus;
	}

	public void addSpell(Spell spl){
		spellList.put(spl.navn, spl);
		System.out.println("Added " + spl.navn + " to " + name);
	}

	public void addFeat(Feat ft){
		if(featList.size() != 0){
			if(featList.get(ft.name) == null){
				featList.put(ft.name, ft);
			}
			else{
				System.out.println(name + " already has this feat!");
			}
		}
		else{
			featList.put(ft.name, ft);
		}
	}

	public void increaseSlots(int ok){
		spellSlots = spellSlots+ok;
	}

	public void okLvl(int ok){
		level = level+ok;
	}

	public String visSpells(){
		String returStreng = "";
		if(spellList.size() == 0){
			System.out.println(name + " does not know any spells!");
		}
		else{
			int teller = 0;
			for(Spell s : spellList.values()){
				if(teller < 6){
					returStreng = returStreng + " // " + s.navn;
					teller++;
				}
				else{
					returStreng = returStreng + " // " + s.navn;
					returStreng = returStreng + "\n";
					teller = 0;
				}
			}
		}
		return returStreng;
	}

	public void changeName(String navn){
		name = navn;
	}

	public void changeLevel(int lvl){
		level = lvl;
	}

	public void changeSpellSlots(int splslts){
		spellSlots = splslts;
	}

	public void changeClass(String cls){
		clas = cls;
	}

	public String getName(){
		return name;
	}

	public int getLevel(){
		return level;
	}

	public int getSlots(){
		return spellSlots;
	}

	public String getSlotsInfo(){
		return spellSlots + " of level " + spellSlotsLevel;
	}

	public String getClas(){
		return clas;
	}
}