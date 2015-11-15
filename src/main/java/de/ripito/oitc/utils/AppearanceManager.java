package de.ripito.oitc.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by ripito on 15.11.2015.
 */
public class AppearanceManager {

    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    /**
     * Shows a title and a subtitle to a player
     * @param title the title
     * @param subtitle the subtitle
     * @param player the player that will see the title
     */
    public void showTitle( String title, String subtitle, Player player ) {

        PacketContainer titleContainer = protocolManager.createPacket( PacketType.Play.Server.TITLE );
        PacketContainer subtitleContainer = protocolManager.createPacket( PacketType.Play.Server.TITLE );

        titleContainer.getIntegers().write( 0, 0 );
        titleContainer.getChatComponents().write( 1, WrappedChatComponent.fromText( title ) );

        subtitleContainer.getIntegers().write( 0, 1 );
        subtitleContainer.getChatComponents().write( 1, WrappedChatComponent.fromText( subtitle ) );

        try {

            protocolManager.sendServerPacket( player, titleContainer );
            protocolManager.sendServerPacket( player, subtitleContainer );

        } catch ( InvocationTargetException e ) {

            e.printStackTrace();

        }

    }

}
