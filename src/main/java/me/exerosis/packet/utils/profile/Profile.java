package me.exerosis.packet.utils.profile;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.util.UUID;

/**
 * Created by Exerosis on 2/7/2016.
 */
public class Profile {
    private UUID UUID;
    private String skinURL;
    private String capeURL;
    private String name;

    public Profile() {
    }

    public String getCapeURL() {
        return capeURL;
    }

    public void setCapeURL(String capeURL) {
        this.capeURL = capeURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkinURL() {
        return skinURL;
    }

    public void setSkinURL(String skinURL) {
        this.skinURL = skinURL;
    }

    public java.util.UUID getUUID() {
        return UUID;
    }

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }

    public GameProfile toGameProfile() {
        GameProfile profile = new GameProfile(UUID, name);
        String textures = "{textures:{SKIN:{url:\"" + skinURL + "\"}, CAPE:{url:\"" + capeURL + "\"}}}";
        profile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString(textures)));
        return profile;
    }
}
