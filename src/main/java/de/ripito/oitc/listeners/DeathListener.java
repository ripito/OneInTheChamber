package de.ripito.oitc.listeners;

import de.ripito.oitc.main.OneInTheChamber;
import de.ripito.oitc.utils.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by ripito on 15.11.2015.
 */
public class DeathListener implements Listener {

    private final OneInTheChamber plugin = OneInTheChamber.getInstance();

    @EventHandler
    public void onDeath( PlayerDeathEvent event ) {

        if ( plugin.getGameManager().getGameState() == GameState.LOBBY )
            return;

        Player player = event.getEntity();

        EntityDamageEvent.DamageCause lastDamageCause = player.getLastDamageCause().getCause();

        if ( ( lastDamageCause == EntityDamageEvent.DamageCause.ENTITY_ATTACK ) ) {

            Player killer = player.getKiller();

            killer.getInventory().addItem( new ItemStack( Material.ARROW ) );
            event.setDeathMessage( plugin.prefix + ChatColor.YELLOW + player.getDisplayName() + ChatColor.AQUA +
                    " was killed by " + ChatColor.YELLOW + killer.getDisplayName() + ChatColor.AQUA + "!" );


        } else if ( ! ( lastDamageCause == EntityDamageEvent.DamageCause.PROJECTILE ) ) {

            event.setDeathMessage( plugin.prefix + ChatColor.YELLOW + player.getDisplayName() + ChatColor.AQUA
            + " has died!" );

        }

    }

}
