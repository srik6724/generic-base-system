package NightMireStats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Credentials.WizCredentials;
import Gear.Hat;
import Gear.StatsInfo;
import Sockets.Socket;

public class NightMireHatStats extends Hat implements StatsInfo {
  private int health;
  private int power_pip; 
  private int accuracy; 
  private int critical; 
  private int block; 
  private int damage; 
  private int resist; 
  private int pierce; 
  private Socket socket1; 
  private Socket socket2; 
  private Socket socket3; 
  private int level; 
  private String school; 
  private Connection conn1; 

  public NightMireHatStats(String name)
  {
    super(name); 
    try
    {
      String db_url = WizCredentials.getDB_URL(); 
      String user = WizCredentials.getDB_USERNAME(); 
      String password = WizCredentials.getDB_PASSWORD(); 

      if(WizCredentials.authenticate(user, password))
      {
        System.out.println("Authentication successful"); 
      }
      else 
      {
        System.out.println("Authentication failed"); 
      }

      conn1 = DriverManager.getConnection(db_url, user, password);

      if(conn1 != null)
      {
        String sql = "SELECT health, power_pip, block, resist, accuracy, critical, damage, school, level, socket1, socket2, socket3 FROM wizard_schema.nightmire_hats WHERE name = ?"; 
        PreparedStatement stmt = conn1.prepareStatement(sql); 
        System.out.println(name); 
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery(); 

        while(rs.next())
        {
          health = Integer.parseInt(rs.getString("health")); 
          power_pip = Integer.parseInt(rs.getString("power_pip")); 
          block = Integer.parseInt(rs.getString("block")); 
          resist = Integer.parseInt(rs.getString("resist")); 
          accuracy = Integer.parseInt(rs.getString("accuracy")); 
          critical = Integer.parseInt(rs.getString("critical")); 
          damage = Integer.parseInt(rs.getString("damage")); 
          school = rs.getString("school"); 
          level = Integer.parseInt("level"); 
          socket1 = new Socket(rs.getString("socket1"), "shield", school);
          socket2 = new Socket(rs.getString("socket2"), "sword", school);
          socket3 = new Socket(rs.getString("socket3"), "sword", school);
        }
        NightMireHatStats createObj = new NightMireHatStats(name, health, power_pip, accuracy, critical, block, damage, resist, pierce, socket1, socket2, socket3, level, school); 
        createObj.createSocketAttachment(socket1); 
        createObj.createSocketAttachment(socket2); 
        createObj.createSocketAttachment(socket3); 
        createObj.statsInformation();
      }
    }
    catch(SQLException e)
    {
      System.out.println("Sorry an exception occurred."); 
    }
  }

  public NightMireHatStats(String name, int health, int power_pip, int accuracy, int critical, int block, int damage, int resist, int pierce, Socket socket1, Socket socket2, Socket socket3, int level, String school)
  {
    super(name); 
    this.health = health; 
    this.power_pip = power_pip; 
    this.accuracy = accuracy; 
    this.critical = critical; 
    this.block = block; 
    this.damage = damage; 
    this.resist = resist; 
    this.pierce = pierce; 
    this.socket1 = socket1; 
    this.socket2 = socket2; 
    this.socket3 = socket3; 
    this.level = level; 
    this.school = school; 
  }



  @Override
  public void statsInformation() {
    System.out.println("Here is the information about the hat chosen."); 
    System.out.println("Health: " + health); 
    System.out.println("Power Pip: " + power_pip); 
    System.out.println("Accuracy: " + accuracy); 
    System.out.println("Critical: " + critical); 
    System.out.println("Block: " + block); 
    System.out.println("Damage: " + damage); 
    System.out.println("Resist: " + resist); 
    System.out.println("Pierce: " + pierce); 
    System.out.println("Socket 1: " + socket1.getDescription());
    System.out.println("Socket 2: " + socket1.getDescription());
    System.out.println("Socket 3: " + socket1.getDescription());
  }

  Socket createSocketAttachment(Socket socket) {

		if(socket.getDescription().equals("unused"))
		{
			try {
        String db_url = WizCredentials.getDB_URL(); 
        String user = WizCredentials.getDB_USERNAME(); 
        String password = WizCredentials.getDB_PASSWORD(); 

        if(WizCredentials.authenticate(user, password))
        {
          System.out.println("Authentication successful"); 
        }
        else 
        {
          System.out.println("Authentication failed"); 
        }

        conn1 = DriverManager.getConnection(db_url, user, password);
				if(conn1 != null)
				{
					Scanner sc = new Scanner(System.in); 

					String firstInput; 
					String addAttachment; 
					System.out.println("Would you like to add socket attachments to your gear?"); 
					System.out.println("Keep in mind, you can only add sockets of type: " + socket.getType()); 
					System.out.println("You will have to use the exact name for the time being. So, please make sure to spell it correctly.");
					firstInput = sc.nextLine(); 
					if(firstInput.equals("NO"))
					{
						if(!(sc.hasNextLine()))
						{
							sc.close();
						}
						return socket;
					}
					else 
					{
						boolean cont = true; 
						String nameOfSocket = ""; 
						String school = ""; 
						String description = ""; 
						while(cont && firstInput.equals("YES"))
						{
							System.out.println("Choose a socket of type " + socket.getType()); 
							addAttachment = sc.nextLine(); 
							if(!(sc.hasNextLine()))
							{
								sc.close();
							}
							Statement statement = conn1.createStatement();
							String sqlString = "SELECT * FROM wizard_schema." + socket.getType() + "_sockets";
							ResultSet rs = statement.executeQuery(sqlString); 
							while(rs.next())
							{
								nameOfSocket = rs.getString("name"); 
								school = rs.getString("school");
								description = rs.getString("description"); 
								if(nameOfSocket.toLowerCase().equals(addAttachment.toLowerCase()))
								{
									cont = false;
									break; 
								}
							}
							if(!(nameOfSocket.equals(addAttachment)))
							{
								System.out.println("Name of socket in database: " + nameOfSocket + " does not match " + addAttachment);
								System.out.println("Try again."); 
								cont = true; 
							}
							else 
							{
								System.out.println("Name of socket in database: " + nameOfSocket + " matches " + addAttachment); 
                if(school.equals("Any School"))
                {
                  System.out.println("Name of socket school in database: " + " is compatible with any school."); 
                }
								else if(!(socket.getSchool().toLowerCase().equals(school.toLowerCase())))
								{
									System.out.println("Name of socket school in database: " + school + " does not match " + socket.getSchool());
									System.out.println("Try again."); 
									cont = true; 
								}
								else 
								{
									System.out.println("Name of socket school in database: " + school + " matches " + socket.getSchool()); 
								}
							}
						}
						socket.setDescription(description);
						socket = new Socket(nameOfSocket, socket.getType(), socket.getSchool(), socket.getDescription()); 
						System.out.println("Socket of type " + socket.getType() + " of school " + socket.getSchool() + " and of description " + socket.getDescription() + " added."); 
						return socket;
					}
				}
				return null;
			}
			catch(SQLException e)
			{
				System.out.println("An exception occurred here."); 
				return null;
			}
		}
		else 
		{
			return socket;
		}

	}
}
