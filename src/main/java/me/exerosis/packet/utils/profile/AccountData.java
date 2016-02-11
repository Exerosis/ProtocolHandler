package me.exerosis.packet.utils.profile;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by Exerosis on 2/11/2016.
 */
public class AccountData {
    public static final Map<UUID, AccountData> ACCOUNT_DATA_INSTANCES = new HashMap<>();
    private String name;
    private String signature;
    private String skinURL;
    private String capeURL;
    private UUID UUID;

    public AccountData(String name, String signature, String skinURL, String capeURL, UUID UUID) {
        this.name = name;
        this.signature = signature;
        this.skinURL = skinURL;
        this.capeURL = capeURL;
        this.UUID = UUID;
    }

    public static AccountData get(UUID UUID) {
        if (!ACCOUNT_DATA_INSTANCES.containsKey(UUID))
            try {
                URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + UUID.toString() + "?unsigned=false");

                URLConnection uc = url.openConnection();
                uc.setUseCaches(false);
                uc.setDefaultUseCaches(false);
                uc.addRequestProperty("User-Agent", "Mozilla/5.0");
                uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
                uc.addRequestProperty("Pragma", "no-cache");

                // Parse it
                String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
                JSONParser parser = new JSONParser();

                JSONObject jsonTextureProperties = (JSONObject) ((JSONArray) ((JSONObject) parser.parse(json)).get("properties")).get(0);
                JSONObject textureProperties = (JSONObject) parser.parse(Base64Coder.decodeString((String) jsonTextureProperties.get("value")));
                JSONObject textures = (JSONObject) textureProperties.get("textureProperties");

                String name = (String) textureProperties.get("profileName");
                String signature = (String) jsonTextureProperties.get("signature");
                String skinURL = (String) ((JSONObject) textures.get("SKIN")).get("url");
                String capeURL = (String) ((JSONObject) textures.get("CAPE")).get("url");

                ACCOUNT_DATA_INSTANCES.put(UUID, new AccountData(name, signature, skinURL, capeURL, UUID));
            } catch (Exception e) {
                System.err.println("Failed to get skin/cape for: " + UUID.toString());
            }
        return (ACCOUNT_DATA_INSTANCES.get(UUID));
    }

    public UUID getUUID() {
        return UUID;
    }

    public String getSkinURL() {
        return skinURL;
    }

    public String getName() {
        return name;
    }

    public String getCapeURL() {
        return capeURL;
    }

    public String getSignature() {
        return signature;
    }
}