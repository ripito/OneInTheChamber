package de.ripito.oitc.utils;

import de.ripito.oitc.main.OneInTheChamber;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ripito on 15.11.2015.
 */
public class GameStartingProcess {

    private final OneInTheChamber plugin = OneInTheChamber.getInstance();
    private int remainingTimeToStart = plugin.getConfig().getInt( "time-to-start" );
    private BukkitTask task;
    private final AppearanceManager appearanceManager = plugin.getAppearanceManager();
    private boolean running = false;
    private final GameManager gameManager = plugin.getGameManager();


    public void start() {

        if ( running )
            return;

        running = true;

        task = Bukkit.getScheduler().runTaskTimer( plugin, () -> {

            if ( remainingTimeToStart % 10 == 0 || remainingTimeToStart <= 5 && remainingTimeToStart != 0 ) {

                Bukkit.broadcastMessage( plugin.prefix + ChatColor.DARK_AQUA + "The teleport to the arena starts in "
                        + ChatColor.YELLOW + remainingTimeToStart + ChatColor.DARK_AQUA + " second"
                        + ( remainingTimeToStart == 1 ? "" : "s" ) + "!" );

                for ( Player onlinePlayers : Bukkit.getOnlinePlayers() )
                    onlinePlayers.playSound( onlinePlayers.getLocation(), Sound.CLICK, 1, 1 );

            }

            if ( remainingTimeToStart == 5 )
                for ( Player onlinePlayers : Bukkit.getOnlinePlayers() )
                    appearanceManager.showTitle( ChatColor.GOLD + "Map:",
                        plugin.getGameManager().getBattleworld().getName(), onlinePlayers );

            if ( remainingTimeToStart == 0 ) {

                if ( Bukkit.getOnlinePlayers().size() != 10 ) {

                    Bukkit.broadcastMessage( plugin.prefix + ChatColor.RED + "They aren't enough players online to "
                            + "start! Teleport cancelled." );
                    running = false;
                    return;

                }

                Bukkit.broadcastMessage( plugin.prefix + ChatColor.DARK_AQUA + "The teleport to the arena starts"
                        + ChatColor.YELLOW + " now" + ChatColor.DARK_AQUA + "!" );

                for ( Player onlinePlayers : Bukkit.getOnlinePlayers() ) {

                    onlinePlayers.playSound( onlinePlayers.getLocation(), Sound.FUSE, 1, 1 );
                    gameManager.updateGameScoreboard();
                    gameManager.setGameInventory( onlinePlayers );
                    gameManager.teleportToRandomSpawn( onlinePlayers );
                    gameManager.setGameState( GameState.GAME );


                }

                this.stop();

            }

            remainingTimeToStart--;

        }, 0L, 20L );

    }

    private void stop() {

        task.cancel();

    }

}
