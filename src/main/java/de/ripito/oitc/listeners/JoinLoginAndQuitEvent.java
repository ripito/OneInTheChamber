package de.ripito.oitc.listeners;

import de.ripito.oitc.main.OneInTheChamber;
import de.ripito.oitc.utils.GameManager;
import de.ripito.oitc.utils.GameStartingProcess;
import de.ripito.oitc.utils.GameState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by ripito on 15.11.2015.
 */
public class JoinLoginAndQuitEvent implements Listener {

    private final OneInTheChamber plugin = OneInTheChamber.getInstance();
    private final GameManager gameManager = plugin.getGameManager();

    @EventHandler
    public void onJoin( PlayerJoinEvent event ) {

        Bukkit.broadcastMessage( plugin.prefix + ChatColor.GOLD + event.getPlayer().getDisplayName()
                + ChatColor.DARK_AQUA + " has joined the game!" );

        if ( plugin.getConfig().getInt( "required-players" ) == Bukkit.getOnlinePlayers().size() )
            new GameStartingProcess().start();

    }

    @EventHandler
    public void onLogin( PlayerLoginEvent event ) {

        if ( ! ( plugin.getGameManager().getGameState() == GameState.LOBBY ) )
            event.disallow( PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "The game is running!" );

    }

    @EventHandler
    public void onQuit( PlayerQuitEvent event ) {

        Bukkit.broadcastMessage( plugin.prefix + ChatColor.GOLD + event.getPlayer().getDisplayName()
                + ChatColor.DARK_AQUA + " has leaved the game!" );

        if ( Bukkit.getOnlinePlayers().size() == 1 ) {

            Bukkit.broadcastMessage( plugin.prefix + ChatColor.YELLOW + "Nobody" + ChatColor.DARK_AQUA +
                    " has won!" );

            for ( Player onlinePlayers : Bukkit.getOnlinePlayers() ) {

                onlinePlayers.teleport( gameManager.getLobby().getSpawnLocation() );
                gameManager.setGameState( GameState.LOBBY );

            }

        }

    }

}
