package online.monkegame.monkeonetap;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.*;
import java.util.UUID;

/*
 * made by monkegame team
 * Mrs_Herobrine_
 * contact here: mrsherobrinenaomi@gmail.com
 * discord: mrsherobrine (naomi)#6263
 * play.monkegame.online
 */

public final class Main extends JavaPlugin {

	public TextComponent updateMessage = Component.text("[monkebot] ",TextColor.color(0x4bcc7f))
			.append(Component.text("UPDATING THE DATABASE! EXPECT SOME LAG!")).color(TextColor.color(0xa61111));
	public TextComponent updatedMessage = Component.text("[monkebot] ", TextColor.color(0x4bcc7f))
			.append(Component.text("Database updated!")).color(TextColor.color(0xbaffe6));
	public long updateRate;
	public String databaseLocation;
	public String databaseTable;
	public String tappingItem;
	public String tappingItemName;

	@Override
	public void onEnable() {

		this.saveDefaultConfig();

		updateRate = (this.getConfig().getLong("db.dbupdaterate") * 20);
		databaseLocation = this.getConfig().getString("db.dblocation");
		databaseTable = this.getConfig().getString("db.dbtable");
		tappingItem = this.getConfig().getString("onetapper.item");
		tappingItemName = (this.getConfig().getString("onetapper.name"));

		if (databaseLocation == null) {
			getLogger().info("You haven't linked your database! Please do so!");
			getServer().getPluginManager().disablePlugin(this);
		} else if (databaseTable == null) {
			getLogger().info("No table found! Have you checked your database?");
			getServer().getPluginManager().disablePlugin(this);
		} else {
			getServer().getPluginManager().registerEvents(new Listeners(this, getLogger(), tappingItem, tappingItemName, updateRate), this);
			main();
			getLogger().info("Database found! Updates will now be made every " + updateRate/20 + " seconds.");
		}
	}

	public String playerName = "";
	public int playerKills = 0;
	public UUID playerUuid;

	public void main() {
		String sql = "INSERT INTO " + databaseTable + " (uuid, username, killcount)\n"
				+ "VALUES ('" + playerUuid + "','" + playerName + "','" + playerKills + "')"
				+ "ON CONFLICT(uuid) DO UPDATE SET "
				+ "username=excluded.username, "
				+ "killcount=excluded.killcount;";
		String url = "jdbc:sqlite:" + databaseLocation;

		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, () -> {
			Bukkit.broadcast(updateMessage, "*.*");
			long startTime = System.currentTimeMillis();

			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				playerKills = player.getStatistic(Statistic.PLAYER_KILLS);
				playerName = player.getName();
				playerUuid = player.getUniqueId();
				scheduler.runTaskAsynchronously(this, ()-> {
					try (Connection conn = DriverManager.getConnection(url);
						 Statement stmt = conn.createStatement()) {
						stmt.execute(sql);
					} catch (SQLException e) {
						getLogger().severe("Encountered an error: " + e.getMessage());
						getLogger().info("Are you sure you configured your database correctly?");
					}
				});
			}

			long endTime = System.currentTimeMillis();
			getLogger().info("DB update took " + (endTime - startTime) + " ms");
			Bukkit.broadcast(updatedMessage, "*.*");
		}, 0L, updateRate);
	}

	@Override
	public void onDisable() {
		getLogger().info("database will no longer update periodically");
	}

}