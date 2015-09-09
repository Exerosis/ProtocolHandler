package me.exerosis.packet.player.injection.packets.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.exerosis.reflection.Reflect;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("static-method")
public abstract class PacketEntityPlayer extends Entity {
    private static final long serialVersionUID = -1648949088389846804L;
    private UUID _skinUUID;
    private EntityPlayer _craftPlayer;

    public PacketEntityPlayer(String name, Location location, UUID skinUUID) {
        this(name, location);
        _skinUUID = skinUUID;
    }

    public PacketEntityPlayer(String name, Location location) {
        super(name, location);
        //createNPC();
    }

    /**
     * Creates a GameProfile with a name, skin, and uuid.
     *
     * @param name
     * @param skinId
     * @param npcID
     * @return
     */
    public static GameProfile makeProfile(String name, UUID skinId, UUID npcID) {
        GameProfile profile = new GameProfile(npcID, name);
        if (skinId == null)
            return profile;

        MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
        GameProfile skin = new GameProfile(skinId, null);
        skin = nmsServer.aB().fillProfileProperties(skin, true);
        if (skin.getProperties().get("textures") == null || !skin.getProperties().get("textures").isEmpty()) {
            Property textures = skin.getProperties().get("textures").iterator().next();
            profile.getProperties().put("textures", textures);
        }

        return profile;
    }

    /**
     * Creates an empty DataWatcher for a fake player.
     *
     * @param player
     * @return
     */
    public static DataWatcher emptyPlayerDataWatcher(Player player, int fakeEID) {
        EntityHuman entityHuman = new EntityHuman(((CraftWorld) player.getWorld()).getHandle(), ((CraftPlayer) player).getProfile()) {
            public void sendMessage(IChatBaseComponent arg0) {
            }

            public boolean a(int arg0, String arg1) {
                return false;
            }

            public BlockPosition getChunkCoordinates() {
                return null;
            }

            public boolean v() {
                return false;
            }
        };

        entityHuman.d(fakeEID);
        return entityHuman.getDataWatcher();
    }

    public int getPing() {
        return 0;
    }

    public DataWatcher getDataWatcher() {
        return null;
    }

    public final void createNPC() {
        WorldServer world = ((CraftWorld) getLocation().getWorld()).getHandle();
        MinecraftServer server = world.getMinecraftServer();
        GameProfile profile = makeProfile(getName(), _skinUUID, UUID.randomUUID());
        PlayerInteractManager interactManager = new PlayerInteractManager(world);

        _craftPlayer = new EntityPlayer(server, world, profile, interactManager);

        double x = getLocation().getX();
        double y = getLocation().getY();
        double z = getLocation().getZ();
        float pitch = getLocation().getPitch();
        float yaw = getLocation().getYaw();
        _craftPlayer.setLocation(x, y, z, pitch, yaw);

        if (getDataWatcher() != null)
            Reflect.Field(_craftPlayer, DataWatcher.class, "datawatcher").setValue(getDataWatcher());
        Reflect.Field(_craftPlayer, int.class, "ping").setValue(getPing());
    }

    public Packet getSpawnPacket() {
        return new PacketPlayOutSpawnEntityLiving(_craftPlayer);
    }

    public Packet getAddTabPacket() {
        return new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, _craftPlayer);
    }

    public Packet getRemoveTabPacket() {
        return new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, _craftPlayer);
    }

    public int[] getEID() {
        return new int[]{_craftPlayer.getId()};
    }

    //Getters
    public EntityPlayer getCraftPlayer() {
        return _craftPlayer;
    }

    public void setCraftPlayer(EntityPlayer craftPlayer) {
        this._craftPlayer = craftPlayer;
    }
}
/*
 *	public static class Builder {
		protected String _name;
		protected Location _location;
		protected UUID _skinUUID;
		protected int _ping;
		protected DataWatcher _watcher;
		private Class<?> _clazz;

		public Builder(String name, Location location) {
			_name = name;
			_location = location;
			try {
				_clazz = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
			} catch (ReflectClassNotFoundException e) {e.printStackTrace();}
		}
		public Builder skinUUID(UUID skinUUID) {
			_skinUUID = skinUUID;
			return this;
		}
		public Builder ping(int ping) {
			_ping = ping;
			return this;
		}
		public Builder dataWatcher(DataWatcher watcher) {
			_watcher = watcher;
			return this;
		}
		public PacketEntityPlayer build(Object... objects) {
			try {
				List<Object> params = Arrays.asList(objects);
				params.add(0, this);

				return (PacketEntityPlayer) _clazz.getConstructors()[0].newInstance(this, params);
			} catch (Exception e) {e.printStackTrace();}
			return null;
		}
	}
 */
