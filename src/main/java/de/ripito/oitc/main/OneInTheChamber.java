package de.ripito.oitc.main;

import de.ripito.oitc.listeners.DeathListener;
import de.ripito.oitc.listeners.EntityDamageByEntityListener;
import de.ripito.oitc.listeners.JoinLoginAndQuitEvent;
import de.ripito.oitc.listeners.RespawnListener;
import de.ripito.oitc.utils.AppearanceManager;
import de.ripito.oitc.utils.GameManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ripito on 15.11.2015.
 */
public class OneInTheChamber extends JavaPlugin {

    public final String prefix = ChatColor.GRAY + "[" + ChatColor.AQUA + "OITC" + ChatColor.GRAY + "] " + ChatColor.RESET;

    @Getter
    private static OneInTheChamber instance;

    @Getter
    private AppearanceManager appearanceManager = new AppearanceManager();

    @Getter
    private GameManager gameManager = new GameManager();

    @Override
    public void onEnable() {

        instance = this;
        this.initializeConfig();
        this.registerEvents();

    }

    private void initializeConfig() {

        getConfig().options().copyDefaults( true );

        getConfig().addDefault( "required-players", 10 );
        getConfig().addDefault( "score-goal",  10 );
        getConfig().addDefault( "lobby",  "world" );
        getConfig().addDefault( "battleworld",  "ExpMap" );

        getConfig().addDefault( "spawn1.x",  0 );
        getConfig().addDefault( "spawn1.y",  0 );
        getConfig().addDefault( "spawn1.z",  0 );

        getConfig().addDefault( "spawn2.x",  0 );
        getConfig().addDefault( "spawn2.y",  0 );
        getConfig().addDefault( "spawn2.z",  0 );

        getConfig().addDefault( "spawn3.x",  0 );
        getConfig().addDefault( "spawn3.y",  0 );
        getConfig().addDefault( "spawn3.z",  0 );

        getConfig().addDefault( "spawn4.x",  0 );
        getConfig().addDefault( "spawn4.y",  0 );
        getConfig().addDefault( "spawn4.z",  0 );

        getConfig().addDefault( "spawn5.x",  0 );
        getConfig().addDefault( "spawn5.y",  0 );
        getConfig().addDefault( "spawn5.z",  0 );

        this.saveConfig();

    }

    private void registerEvents() {

        List<Listener> listenerList = new ArrayList<>();

        listenerList.add( new DeathListener() );
        listenerList.add( new EntityDamageByEntityListener() );
        listenerList.add( new JoinLoginAndQuitEvent() );
        listenerList.add( new RespawnListener() );

        for ( Listener listeners : listenerList )
            Bukkit.getPluginManager().registerEvents( listeners, this );

    }
}
