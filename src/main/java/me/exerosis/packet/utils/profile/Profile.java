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
    private String signature;
    private String name;

    public Profile(AccountData accountData) {
        UUID = accountData.getUUID();
        skinURL = accountData.getSkinURL();
        capeURL = accountData.getCapeURL();
        signature = accountData.getSignature();
        name = accountData.getName();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(AccountData accountData) {
        signature = accountData.getSignature();
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setSignature(UUID UUID) {
        signature = AccountData.get(UUID).getSignature();
    }

    public String getCapeURL() {
        return capeURL;
    }

    public void setCapeURL(AccountData accountData) {
        capeURL = accountData.getCapeURL();
    }

    public void setCapeURL(String capeURL) {
        this.capeURL = capeURL;
    }

    public void setCapeURL(UUID UUID) {
        capeURL = AccountData.get(UUID).getCapeURL();
    }

    public String getName() {
        return name;
    }

    public void setName(AccountData accountData) {
        capeURL = accountData.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName(UUID UUID) {
        capeURL = AccountData.get(UUID).getName();
    }

    public String getSkinURL() {
        return skinURL;
    }

    public void setSkinURL(AccountData accountData) {
        capeURL = accountData.getSkinURL();
    }

    public void setSkinURL(String skinURL) {
        this.skinURL = skinURL;
    }

    public void setSkinURL(UUID UUID) {
        capeURL = AccountData.get(UUID).getSkinURL();
    }

    public UUID getUUID() {
        return UUID;
    }

    public void setUUID(UUID UUID) {
        this.UUID = UUID;
    }

    public GameProfile toGameProfile() {
        GameProfile profile = new GameProfile(UUID, name);
        String textures = "{textures:{SKIN:{url:\"" + skinURL + "\"}, CAPE:{url:\"" + capeURL + "\"}}}";
        profile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString(textures), signature));
        return profile;
    }
}