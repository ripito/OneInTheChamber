package de.ripito.oitc.listeners;

import de.ripito.oitc.main.OneInTheChamber;
import de.ripito.oitc.utils.GameManager;
import de.ripito.oitc.utils.GameState;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by ripito on 15.11.2015.
 */
public class EntityDamageByEntityListener implements Listener {

    private final GameManager gameManager = OneInTheChamber.getInstance().getGameManager();

    @EventHandler
    public void onDamage( EntityDamageByEntityEvent event ) {

        if ( gameManager.getGameState() == GameState.LOBBY )
            return;

        if( event.getDamager() instanceof Arrow && event.getEntity() instanceof Player ) {

            Arrow arrow = ( Arrow ) event.getDamager();

            if( arrow.getShooter() instanceof Player ) {

                Player player = ( Player ) event.getEntity();
                Player killer = ( Player ) arrow.getShooter();

                player.damage( 20.0 );

                killer.playSound( killer.getLocation(), Sound.LEVEL_UP, 1, 1 );

                gameManager.addScore( killer );

            }



        }

    }

}
