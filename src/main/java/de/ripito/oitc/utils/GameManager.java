package de.ripito.oitc.utils;

import de.ripito.oitc.main.OneInTheChamber;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

/**
 * Created by ripito on 15.11.2015.
 */
public class GameManager {

    private final OneInTheChamber plugin = OneInTheChamber.getInstance();

    @Getter
    private final World lobby = Bukkit.getWorld( plugin.getConfig().getString( "lobby" ) );

    @Getter
    private final World battleworld = Bukkit.getWorld( plugin.getConfig().getString( "battleworld" ) );

    @Getter
    @Setter
    private GameState gameState = GameState.LOBBY;

    private final List<Location> spawns = new ArrayList<>();
    private final Map<Player, Integer> scores = new HashMap<>();

    public GameManager() {

        spawns.add( new Location( battleworld, plugin.getConfig().getInt( "spawn1.x" ), plugin.getConfig()
                .getInt( "spawn1.y" ), plugin.getConfig().getInt( "spawn1.z" ) ) );
        spawns.add( new Location( battleworld, plugin.getConfig().getInt( "spawn2.x" ), plugin.getConfig()
                .getInt( "spawn2.y" ), plugin.getConfig().getInt( "spawn2.z" ) ) );
        spawns.add( new Location( battleworld, plugin.getConfig().getInt( "spawn3.x" ), plugin.getConfig()
                .getInt( "spawn3.y" ), plugin.getConfig().getInt( "spawn3.z" ) ) );
        spawns.add( new Location( battleworld, plugin.getConfig().getInt( "spawn4.x" ), plugin.getConfig()
                .getInt( "spawn4.y" ), plugin.getConfig().getInt( "spawn4.z" ) ) );
        spawns.add( new Location( battleworld, plugin.getConfig().getInt( "spawn5.x" ), plugin.getConfig()
                .getInt( "spawn5.y" ), plugin.getConfig().getInt( "spawn5.z" ) ) );

    }

    public void setGameInventory( Player player ) {

        Inventory inventory = player.getInventory();

        inventory.clear();

        inventory.addItem( new ItemStack( Material.BOW ) );
        inventory.addItem( new ItemStack( Material.IRON_SWORD ) );
        inventory.addItem( new ItemStack( Material.ARROW ) );

    }

    public void teleportToRandomSpawn( Player player ) {

        player.teleport( spawns.get( new Random().nextInt( spawns.size() ) ) );

    }

    public void addScore( Player player ) {

        if ( plugin.getConfig().getInt( "score-goal" ) == scores.get( player ) ) {

            Bukkit.broadcastMessage( plugin.prefix + ChatColor.YELLOW + player.getDisplayName() + ChatColor.DARK_AQUA +
                    " has won!" );

            for ( Player onlinePlayers : Bukkit.getOnlinePlayers() ) {

                onlinePlayers.teleport( lobby.getSpawnLocation() );
                gameState = GameState.LOBBY;

            }

        }

        if ( !scores.containsKey( player ) )
            scores.put( player, 0 );

        scores.put( player, scores.get( player ) + 1 );
        updateGameScoreboard();

    }

    public void updateGameScoreboard() {

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective info = board.registerNewObjective( "info", "dummy" );

        info.setDisplayName( ChatColor.AQUA + "OITC" + ChatColor.DARK_GRAY + " | " + ChatColor.DARK_AQUA + "Goal: " +
                plugin.getConfig().getInt( "score-goal" ) );
        info.setDisplaySlot( DisplaySlot.SIDEBAR );

        for ( Player onlinePlayers : Bukkit.getOnlinePlayers() ) {

            info.getScore( ChatColor.YELLOW + onlinePlayers.getDisplayName() + ": " )
                    .setScore( scores.containsKey( onlinePlayers ) ? scores.get( onlinePlayers ) : 0 );

        }

    }

}
