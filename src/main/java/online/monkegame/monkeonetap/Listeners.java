package online.monkegame.monkeonetap;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.logging.Logger;

public class Listeners implements Listener {

    public Main plugin;
    public Logger log;
    public String tappingItem;
    public String tappingItemName;
    public long updateRate;
    BukkitScheduler scheduler = Bukkit.getScheduler();

    public Listeners(Main main, Logger logger, String tappingItem, String tappingItemName, long dbUpdate) {
        this.plugin = main;
        this.log = logger;
        this.tappingItem = tappingItem;
        this.tappingItemName = tappingItemName;
        this.updateRate = dbUpdate;
    }

    //event handlers
    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        final Player p = evt.getPlayer();
        evt.joinMessage(Component.text(p.getName() + " has joined the battle!"));
        scheduler.runTaskLaterAsynchronously(plugin, () -> {
            PlayerInventory inv = p.getInventory();
            ItemStack tapper = new ItemStack(Material.valueOf(tappingItem));
            ItemMeta meta = tapper.getItemMeta();
            assert tappingItemName != null;
            meta.displayName(Component.text(tappingItemName).decoration(TextDecoration.ITALIC, false));
            meta.addEnchant(Enchantment.DAMAGE_ALL, 1000, true);
            tapper.setItemMeta(meta);
            inv.setItem(0, tapper);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) updateRate, 3, false, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, (int) updateRate, 0, false, false));
        }, 10L);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent qevt) {
        final Player p = qevt.getPlayer();
        qevt.quitMessage(Component.text(p.getName() + " took the coward's way out..."));
        PlayerInventory inv = p.getInventory();
        inv.clear();
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent dievt) {
        dievt.setCancelled(true);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent revt) {
        Player p = revt.getPlayer();
        scheduler.runTaskLaterAsynchronously(plugin, ()-> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999, 3, false, false));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 9999, 0, false, false));
        }, 5L);
    }

    @EventHandler
    public void setExp(PlayerDeathEvent devt) {
        if (devt.getEntity().getKiller() != null) {
            devt.getEntity().getKiller().setLevel(devt.getEntity().getKiller().getStatistic(Statistic.PLAYER_KILLS));
            devt.deathMessage(
                    Component.text(devt.getEntity().getKiller().getName(), NamedTextColor.RED)
                    .append(Component.text(">>", NamedTextColor.WHITE, TextDecoration.BOLD))
                    .append(Component.text(devt.getEntity().getName(), NamedTextColor.DARK_RED)).decoration(TextDecoration.BOLD, false)
            );
        }
    }

}
