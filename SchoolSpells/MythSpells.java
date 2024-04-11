package SchoolSpells;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import dataStructures.Element;
import dataStructures.WizHeap;

public class MythSpells {
	
	private WizHeap hp = new WizHeap(); 

	private List<List<Element>> fullDeck;
	
	public ArrayList<Spell> mythSpells = new ArrayList<Spell>(); 
	
	MythSpells(int selectionNo) throws IOException
	{
		Connection conn1 = null;
        try {
            String url1 = "jdbc:mysql://localhost:3306/wizard_schema";
            String user = "srik6724";
            String password = "28892K0shair!";
 
            conn1 = DriverManager.getConnection(url1, user, password);
            if (conn1 != null) {
            	Statement st1 = conn1.createStatement(); 
            	if(st1 != null)
            	{
            		ResultSet rs = st1.executeQuery("SELECT * FROM wizard_schema.myth_spells");
            		while(rs.next())
            		{
            			String name = rs.getString("name"); 
            			String level = rs.getString("level"); 
            			String description = rs.getString("description"); 
            			String pip_chance = rs.getString("pip_chance");
            			String pips = rs.getString("pips"); 
									String school = rs.getString("school"); 
            			String myth_typeSpell = rs.getString("typeSpell"); 
            			
            			Spell spell = new Spell(name, level, description, pip_chance, pips, 1, school, myth_typeSpell); 
            			
            			mythSpells.add(spell); 
            		}
								conn1.close();
								fullDeck = anotherDefaultDeck(mythSpells, selectionNo); 
            		System.out.println("Execution is done.");
            	}
            	else
            	{
            		System.out.println("Cannot execute database statement.");
            	}
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
	}

	public List<List<Element>> anotherDefaultDeck(ArrayList<Spell>spells, int selectionNo) throws InterruptedException, IOException
	{
		String input; 
		Scanner sc = new Scanner(System.in); 
		System.out.println("Select CD for custom deck, DD for default deck"); 
		input = sc.nextLine(); 

		if(input.equals("CD"))
		{
			List<List<Element>> fullDeck = hp.selectYESOption("Myth", "YES", selectionNo); 
			return fullDeck; 
		}
		else if(input.equals("DD"))
		{
			List<Map<String,List<String>>> givenDeck = hp.selectNOoption("Myth", "NO", selectionNo); 
			return null;
		}
		return null;
	}

	public List<List<Element>> retrieveFullDeck()
	{
		return fullDeck;
	}
	
	public ArrayList<Spell> retrieveMythSpells()
	{
		return mythSpells; 
	}
	
	public void deckInformation(List<Map<String, List<String>>> setOfSpells)
	{
		if(setOfSpells != null)
		{
			computeMainDeckInformation(setOfSpells); 
			computeTCInformation(setOfSpells); 
		}
	}
	
	public Set<String> computeTCInformation(List<Map<String,List<String>>> setOfSpells)
	{
		if(setOfSpells != null)
		{
			for(String spell: setOfSpells.get(0).keySet())
			{
				System.out.println("Spell Name: " + spell); 
				System.out.println("Count of Spell: " + setOfSpells.get(0).get(spell).get(0)); 
				System.out.println("Pip Chance: " + setOfSpells.get(0).get(spell).get(1)); 
				System.out.println("Pips of Spell: " + setOfSpells.get(0).get(spell).get(2)); 
				System.out.println("School Name: " + setOfSpells.get(0).get(spell).get(3)); 
				System.out.println("---------------------------------------------------------"); 
			}
		}
		return null; 
	}
	
	public Set<String> computeMainDeckInformation(List<Map<String,List<String>>> setOfSpells)
	{
		if(setOfSpells != null)
		{
			for(String spell: setOfSpells.get(0).keySet())
			{
				System.out.println("Spell Name: " + spell); 
				System.out.println("Count of Spell: " + setOfSpells.get(0).get(spell).get(0)); 
				System.out.println("Pip Chance: " + setOfSpells.get(0).get(spell).get(1)); 
				System.out.println("Pips of Spell: " + setOfSpells.get(0).get(spell).get(2)); 
				System.out.println("School Name: " + setOfSpells.get(0).get(spell).get(3)); 
				System.out.println("---------------------------------------------------------"); 
			}
		}
		return null; 
	}
	
	public void countOfEachSpell(String name, List<Spell> spells, int capacity)
	{
		int count = 0; 
		for(Spell spell: spells)
		{
			if(spell.getName().equals(name))
			{
				count = count + 1; 
			}
		}
		System.out.println(count + "/" + capacity + name + "found."); 
	}
	
	public static void main(String[]args)
	{
		//MythSpells spells = new MythSpells(); 
	}
	
	
}



