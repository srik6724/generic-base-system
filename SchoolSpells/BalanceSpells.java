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

public class BalanceSpells {
	
	private WizHeap hp = new WizHeap(); 

	private List<List<Element>> fullDeck;
	
	public ArrayList<Spell> balanceSpells = new ArrayList<Spell>(); 
	
	BalanceSpells(int selectionNo) throws IOException
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
            		ResultSet rs = st1.executeQuery("SELECT * FROM wizard_schema.balance_spells");
            		while(rs.next())
            		{
            			String name = rs.getString("name"); 
            			String level = rs.getString("level"); 
            			String description = rs.getString("description"); 
            			String pip_chance = rs.getString("pip_chance");
            			String pips = rs.getString("pips"); 
									String school = rs.getString("school"); 
            			String balance_typeSpell = rs.getString("typeSpell"); 
            			
            			Spell spell = new Spell(name, level, description, pip_chance, pips, 1, school, balance_typeSpell); 
            			
            			balanceSpells.add(spell); 

									/*if(createdBalanceSpells != null)
									{
										System.out.println("Deck has been successfully created."); 
            				System.out.println("Here is the following information about your main Deck, tc Deck"); 
										deckInformation(createdBalanceSpells, givenBalanceSpells); 
									}*/
            		}
								conn1.close();
								List<List<Spell>> createdBalanceSpells = null; 
								Scanner sc = new Scanner(System.in); 
								System.out.println("Select YES for custom deck, or NO for default deck."); 
								String input = sc.nextLine();
								if(input.equals("YES"))
								{
									fullDeck = customDeck(balanceSpells, input, selectionNo);
								}
								else if(input.equals("NO"))
								{
									List<Map<String, List<String>>> givenBalanceSpells = defaultDeck(balanceSpells, selectionNo); 
									if(givenBalanceSpells != null)
									{
										System.out.println("Deck has been successfully created."); 
										//System.out.println("Here is the following information about your main Deck, tc Deck"); 
										deckInformation(createdBalanceSpells, givenBalanceSpells); 
									}
								}
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


	public List<List<Element>> retrieveFullDeck()
	{
		return fullDeck;
	}
	
	public ArrayList<Spell> retrieveBalanceSpells()
	{
		return balanceSpells; 
	}

	public List<Map<String, List<String>>> defaultDeck(ArrayList<Spell>spells, int selectionNo) throws InterruptedException, IOException
	{
		String input; 
		Scanner sc = new Scanner(System.in); 
		System.out.println("Select CD for custom deck, DD for default deck"); 
		input = sc.nextLine(); 

		if(input.equals("CD"))
		{
			hp.selectYESOption("Balance", "YES", selectionNo); 
			return null;
		}
		else if(input.equals("DD"))
		{
			List<Map<String,List<String>>> givenDeck = hp.selectNOoption("Balance", "NO", selectionNo); 
			return givenDeck;
		}
		return null;
	}

	public List<List<Element>> customDeck(ArrayList<Spell>spells, String input, int selectionNo) throws InterruptedException, IOException
	{
		List<List<Element>> fullDeck = new WizHeap().selectYESOption("Balance", "YES", selectionNo); 
		return fullDeck;
	}
	
	public void deckInformation(List<List<Spell>> spells, List<Map<String, List<String>>> setOfSpells)
	{
		if(setOfSpells != null)
		{
			computeMainDeckInformation(spells, setOfSpells); 
			//computeTCInformation(spells, setOfSpells); 
		}
	}
	
	public Set<String> computeTCInformation(List<Map<String,List<String>>> setOfSpells)
	{
		if(setOfSpells != null)
		{
			for(String spell: setOfSpells.get(0).keySet())
			{
				System.out.println("Spell Name: " + spell); 
				System.out.println("Count Of Spell: " + setOfSpells.get(0).get(spell).get(0)); 
				System.out.println("Pip Chance: " + setOfSpells.get(0).get(spell).get(1)); 
				System.out.println("Pips Of Spell: " + setOfSpells.get(0).get(spell).get(2)); 
				System.out.println("School Name: " + setOfSpells.get(0).get(spell).get(3)); 
				System.out.println("---------------------------------------------------------"); 
			}
		}
		return null; 
	}
	
	public Set<String> computeMainDeckInformation(List<List<Spell>> spells, List<Map<String,List<String>>> setOfSpells)
	{
		if(setOfSpells != null)
		{
			int cardNumber = 0; 
			for(String spellName: setOfSpells.get(0).keySet())
			{
				cardNumber = cardNumber + Integer.parseInt(setOfSpells.get(0).get(spellName).get(0)); 
				System.out.println("Spell Name: " + spellName); 
				System.out.println("Count Of Spell: " + setOfSpells.get(0).get(spellName).get(0)); 
				System.out.println("Pip Chance: " + setOfSpells.get(0).get(spellName).get(1)); 
				System.out.println("Pips Of Spell: " + setOfSpells.get(0).get(spellName).get(2)); 
				System.out.println("School Name: " + setOfSpells.get(0).get(spellName).get(3)); 
				System.out.println(cardNumber + "/" + "64 main deck spells"); 
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
	
	
}

