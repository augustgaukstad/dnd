import java.io.*;
import java.util.*;

class Spell{

	String navn;
	int level;
	String school;
	String ritual;
	String castTime;
	String range;
	String components;
	String duration;
	HashMap <String, String> classes = new HashMap <String, String>();
	String description;

	public Spell(String name, int lvl, String scl, String rtl, String time, String rng, String compo, String dur, HashMap <String, String> classs, String desc){
		navn = name;
		level = lvl;
		school = scl;
		ritual = rtl;
		castTime = time;
		range = rng;
		components = compo;
		classes = classs;
		duration = dur;
		description = desc;
		
	}

	///GET METODER
	public String getName(){
		return navn;
	}

	public String getCastTime(){
		return castTime;
	}

	public String getComp(){
		return components;
	}

	public String getDesc(){
		return description;
	}

	public String getDur(){
		return duration;
	}

	public int getLevel(){
		return level;
	}

	public String getRange(){
		return range;
	}

	public String getSchool(){
		return school;
	}

	public HashMap <String, String> getClasses(){
		return classes;
	}

	public String viewClasses(){
		String returStreng = "";
		for(String s : classes.values()){
			int teller = 0;
			if(teller < 6){
				returStreng = returStreng + " // " + s;
				teller++;
			}
			else{
				returStreng = returStreng + s;
				returStreng = returStreng + "\n";
				teller = 0;
			}
		}
		return returStreng;
	}


	///CHANGE METODER
	public void changeName(String name){
		navn = name;
	}

	public void changeCast(String cast){
		castTime = cast;
	}

	public void changeComp(String comp){
		components = comp;
	}

	public void changeDesc(String dsc){
		description = dsc;
	}

	public void changeDur(String dur){
		duration = dur;
	}

	public void changeLevel(int lvl){
		level = lvl;
	}

	public void changeRange(String rng){
		range = rng;
	}

	public void changeSchool(String sch){
		school = sch;
	}

	public void changeClasses(HashMap <String, String> cls){
		classes = cls;
	}
}