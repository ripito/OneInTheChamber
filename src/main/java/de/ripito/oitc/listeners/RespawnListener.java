package de.ripito.oitc.listeners;

import de.ripito.oitc.main.OneInTheChamber;
import de.ripito.oitc.utils.GameManager;
import de.ripito.oitc.utils.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Created by ripito on 15.11.2015.
 */
public class RespawnListener implements Listener {

    private final OneInTheChamber plugin = OneInTheChamber.getInstance();
    private final GameManager gameManager = plugin.getGameManager();

    @EventHandler
    public void onRespawn( PlayerRespawnEvent event ) {

        if ( plugin.getGameManager().getGameState() == GameState.LOBBY )
            return;

        Player player = event.getPlayer();

        gameManager.teleportToRandomSpawn( player );
        gameManager.setGameInventory( player );

    }

}
