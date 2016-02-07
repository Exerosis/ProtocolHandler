package me.exerosis.packet.display.displayables.tab;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumGamemode;
import net.minecraft.server.v1_8_R1.PlayerInfoData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.UUID;

//TODO make builder!
public class TabListEntry {
    private String text;
    private int ping;
    private Property headTexture;

    public TabListEntry(String text, int ping) {
        this.text = text;
        this.ping = ping;
    }

    public TabListEntry(String text, String headTexture) {
        this.text = text;
        this.headTexture = new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:" + headTexture + "}}}"));
    }

    public TabListEntry(String text, UUID headTexture) {
        this.text = text;
        try {
            URLConnection url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + headTexture + "?unsigned=false").openConnection();
            String json = new Scanner(url.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONObject playerData = (JSONObject) new JSONParser().parse(json);
            JSONArray properties = (JSONArray) playerData.get("properties");
            JSONObject object = (JSONObject) properties.get(0);
            this.headTexture = new Property("textures", (String) object.get("value"), (String) object.get("signature"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public Property getHeadTexture() {
        return headTexture;
    }

    public String getText() {
        return text;
    }

    public int getPing() {
        return ping;
    }

    public Object getPlayerDataInfo() {
        return new PlayerInfoData(null, getGameProfile(), ping, EnumGamemode.getById(0), ChatSerializer.a(text));
    }

    public GameProfile getGameProfile() {
        GameProfile profile = new GameProfile(UUID.randomUUID(), text);
        if (headTexture != null)
            profile.getProperties().put("textures", headTexture);
        return profile;
    }
}