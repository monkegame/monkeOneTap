package online.monkegame.monkebotplugin2;

import org.bukkit.plugin.java.*;
import org.bukkit.*;
import org.bukkit.command.*;
import java.sql.*;
import java.util.UUID;


/*
 * made by monkegame team
 * Mrs_Herobrine_
 * contact here: mrsherobrinenaomi@gmail.com, mrsherobrine (naomi)#6263
 * play.monkegame.online
 */



public final class pluginClass extends JavaPlugin{
	
	public int playerkills;
	public String killslog;
	@Override
    public void onEnable() {
		getLogger().info("Thanks for using/enabling monkebotplugin!");
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("playerkills")) {
			getLogger().info("Getting info...");
			for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
				playerkills = player.getStatistic(Statistic.PLAYER_KILLS);
				killslog = Integer.toString(playerkills);
				String playerName = player.getName();
				UUID playerUuid = player.getUniqueId();
				getLogger().info(playerName + " has " + killslog + " kills.");
				
			        // SQLite connection string
			        String url = "jdbc:sqlite:D://databases/kills.db";
			        //INSERT OR UPDATE INTO
			        // SQL statement for creating a new table
			        String sql = "INSERT OR REPLACE INTO kills (uuid, username, killcount)\n"
			        		+ "VALUES ('" + playerUuid + "','" + playerName + "','" + playerkills  + "');";
			        		/*+ "ON CONFLICT(" + playerUuid +", " + playerName + ") DO UPDATE SET " + playerUuid + "=excluded." + playerUuid +";"; */
			        
			        try (Connection conn = DriverManager.getConnection(url);
			                Statement stmt = conn.createStatement()) {
			            // create a new table
			            	stmt.execute(sql);
			        } catch (SQLException e) {
			            System.out.println(e.getMessage());
			        }
			    } return true;
		} else {
			return false; 
		}
	}
	
	@Override
    public void onDisable() {
		getLogger().info("oi cunt ya disabled meh");
	}
}
