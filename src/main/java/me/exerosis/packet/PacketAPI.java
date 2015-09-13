package me.exerosis.packet;

import me.exerosis.packet.event.PacketListener;
import me.exerosis.packet.player.injection.packet.player.handlers.PlayerHandler;
import me.exerosis.packet.wrappers.in.PacketWrapperInChat;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PacketAPI extends JavaPlugin implements Listener {
    public static Plugin plugin;

    public static PlayerHandler getPlayerHandler() {
        return PlayerHandler.getInstance();
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        plugin = this;
        PlayerHandler.getInstance();
       new PacketListener<PacketWrapperInChat>() {
           @Override
           public void onPacket() {
               System.out.println();
           }
       }.register(PacketWrapperInChat.class);
    }
}

/*
    @SuppressWarnings("static-method")
	@EventHandler
	public void onClick(PlayerInteractEvent event){
		Disguise disguise = new Disguise(event.getPlayer(), ChatColor.RED + "TestName", EntityType.CREEPER);
		disguise.addAllBut(event.getPlayer());
		disguise.sendCommand("Destroy, Spawn");	
		//disguise.sendCommand("RemoveTab, Destroy, AddTab, Spawn");	
		PacketEntity.refreshPlayer(event.getPlayer());
	}

@SuppressWarnings("static-method")
@EventHandler
public void onChat(PacketEventInChat event){
	if (event.getMessage().contains("pack")){
		event.getPlayer().getPlayer().setResourcePack("https://www.dropbox.com/s/08zecg17mw1rqha/testPack.zip?dl=1");
		event.setCancelled(true);
	}
	if (event.getMessage().contains("title")){
		ActionBar title = new ActionBar("&1test bar"); 
		event.getPlayer().display(title);
	}
	//new PlayerNameChange(event.getPlayer(), "test").send(event.getPlayer());
}

@SuppressWarnings("static-method")
@EventHandler
public void onConfirmPack(PacketEventInResourcePackStatus event){
	if(event.getStatus() == 3 || event.getStatus() == 0)
		return;
	//event.getPlayer().kickPlayer(ChatColor.RED + "You must use the servers resource pack! Please reconnect and click accept!");	
}
 */