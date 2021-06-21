package online.monkegame.monkebotplugin2;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/*
 * made by monkegame team
 * Mrs_Herobrine_
 * contact here: mrsherobrinenaomi@gmail.com
 * discord: mrsherobrine (naomi)#6263
 * play.monkegame.online
 */



public final class pluginClass extends JavaPlugin implements Listener {

	public int playerkills;
	final TextComponent updateMessage = Component.text()
			.content("[monkebot] ")
			.color(TextColor.color(0x4bcc7f))
			.append(Component.text("UPDATING THE DATABASE! EXPECT SOME LAG!")).color(TextColor.color(0xa61111))
			.build();
	final TextComponent updatedMessage = Component.text()
			.content("[monkebot] ")
			.color(TextColor.color(0x4bcc7f))
			.append(Component.text("Database updated!")).color(TextColor.color(0xbaffe6))
			.build();
	public long updateRate;
	public String databaseLocation;
	public String databaseTable;

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		updateRate = (this.getConfig().getLong("db.dbupdaterate") * 20);
		databaseLocation = this.getConfig().getString("db.dblocation");
		databaseTable = this.getConfig().getString("db.dbtable");
		if (databaseLocation == null) {
			getLogger().info("You haven't linked your database! Please do so!");
		} else if (databaseTable == null) {
			getLogger().info("No table found! Have you checked your database?");
		} else {
			Main();
		}
	}

	public void Main() {
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("Database found! Updates will now be made.");
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, () -> {
			long startTime = System.currentTimeMillis();
			getLogger().info("Getting info...");
			Bukkit.getServer().broadcast(updateMessage, "*.*");
			for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
				playerkills = player.getStatistic(Statistic.PLAYER_KILLS);
				String playerName = player.getName();
				UUID playerUuid = player.getUniqueId();

				// SQLite connection string
				String url = "jdbc:sqlite:" + databaseLocation;
				// SQL statement for updating the table
				String sql = "INSERT INTO " + databaseTable + " (uuid, username, killcount)\n"
						+ "VALUES ('" + playerUuid + "','" + playerName + "','" + playerkills + "')"
						+ "ON CONFLICT(uuid) DO UPDATE SET "
						+ "username=excluded.username, "
						+ "killcount=excluded.killcount;";
				try (Connection conn = DriverManager.getConnection(url);
					 Statement stmt = conn.createStatement()) {
					stmt.execute(sql);
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					System.out.println("have you configured your database correctly?");
				}
			}
			long endTime = System.currentTimeMillis();
			Bukkit.getServer().broadcast(updatedMessage, "*.*");
			getLogger().info("database updated! took " + (endTime - startTime) + " ms");
		}, 0L, updateRate);
	}



	@Override
	public void onDisable() {
		getLogger().info("database will no longer update periodically");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent evt) {
		this.saveDefaultConfig();
		final String tappingItem = this.getConfig().getString("onetapper.item");
		final String tappingItemName = (this.getConfig().getString("onetapper.name"));
		final Player p = evt.getPlayer();
		evt.joinMessage(Component.text(p.getName() + " has joined the battle!"));
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(this, () -> {
			PlayerInventory inv = p.getInventory();
			ItemStack tapper = new ItemStack(Material.valueOf(tappingItem));
			ItemMeta meta = tapper.getItemMeta();
			assert tappingItemName != null;
			meta.displayName(Component.text(tappingItemName));
			meta.addEnchant(Enchantment.DAMAGE_ALL, 1000, true);
			tapper.setItemMeta(meta);
			inv.setItem(0, tapper);
		}, 30L);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent qevt) {
		final Player p = qevt.getPlayer();
		qevt.quitMessage(Component.text(p.getName() + " took the coward's way out..."));
		PlayerInventory inv = p.getInventory();
		inv.clear();
	}
}