package online.monkegame.monkebotplugin2;

import org.bukkit.plugin.java.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;

/*
 * made by Mrs_Herobrine_
 * mrsherobrinenaomi@gmail.com
 * mrsherobrine (naomi)#6263
 * play.monkegame.online
 */


public final class pluginClass extends JavaPlugin{
	
	public int playerkills;
	public String killslog;
	@Override
    public void onEnable() {
		getLogger().info("Thanks for using monkebotplugin!");
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("playerkills")) {
			for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
				playerkills = player.getStatistic(Statistic.PLAYER_KILLS);
				killslog = Integer.toString(playerkills);
				getLogger().info(killslog + player.getName());
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
