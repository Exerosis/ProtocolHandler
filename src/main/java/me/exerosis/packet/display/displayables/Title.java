package me.exerosis.packet.display.displayables;

import me.exerosis.packet.display.DisplayUtils;
import me.exerosis.packet.display.Displayable;
import me.exerosis.packet.injection.PacketPlayer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.Packet;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import org.bukkit.ChatColor;

import java.util.HashSet;
import java.util.Set;

//TODO Reformat java docs on this class.
public class Title extends Displayable {
    private String title = "";
    private ChatColor titleColor = ChatColor.WHITE;

    private String subtitle = "";
    private ChatColor subtitleColor = ChatColor.WHITE;

    private int fadeInTime, stayTime, fadeOutTime;
    private boolean ticks = false;

    private Set<PacketPlayer> _players = new HashSet<>();

    /**
     * Create a new title
     *
     * @param title Title
     */
    public Title(int priority, String title) {
        this(priority, title, "");
        this.title = title;
    }

    /**
     * Create a new title
     *
     * @param title    Title text
     * @param subtitle Subtitle text
     */
    public Title(int priority, String title, String subtitle) {
        this(priority, title, subtitle, -1, -1, -1);
        this.title = title;
        this.subtitle = subtitle;
    }

    /**
     * Create a new title
     *
     * @param title       Title text
     * @param subtitle    Subtitle text
     * @param fadeInTime  Fade in time
     * @param stayTime    Stay on screen time
     * @param fadeOutTime Fade out time
     */
    public Title(int priority, String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
        super(priority);
        this.title = title;
        this.subtitle = subtitle;
        this.fadeInTime = fadeInTime;
        this.stayTime = stayTime;
        this.fadeOutTime = fadeOutTime;
    }

    /**
     * Copy title
     *
     * @param title Title
     */
    public Title(int priority, Title title) {
        this(priority, title.getTitle(), title.getSubtitle(), title.getFadeInTime(), title.getStayTime(), title.getFadeOutTime());
        this.titleColor = title.getTitleColor();
        this.subtitleColor = title.getSubtitleColor();
        this.ticks = title.isInTicks();
    }

    public static Packet getClearPacket() {
        return new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
    }

    public static Packet getResetPacket() {
        return new PacketPlayOutTitle(EnumTitleAction.RESET, null);
    }

    public Packet getTimePacket() {
        return new PacketPlayOutTitle(EnumTitleAction.TIMES, null, getFadeInTime(), getStayTime(), getFadeOutTime());
    }

    public Packet getTitlePacket() {
        return new PacketPlayOutTitle(EnumTitleAction.TITLE, DisplayUtils.serializeText(title, titleColor));
    }

    public Packet getSubtitlePacket() {
        return new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, DisplayUtils.serializeText(subtitle, subtitleColor));
    }

    @Override
    public void show(PacketPlayer player) {
        _players.add(player);
        player.sendPacket(getResetPacket());
        refreshTitle();
    }

    @Override
    public void hide(PacketPlayer player) {
        player.sendPacket(getClearPacket());
        _players.remove(player);
    }

    public void refreshTitle() {
        for (PacketPlayer player : _players) {
            if (fadeInTime != -1 && fadeOutTime != -1 && stayTime != -1)
                player.sendPacket(getTimePacket());
            if (!title.equals(""))
                player.sendPacket(getTitlePacket());
            if (!subtitle.equals(""))
                player.sendPacket(getSubtitlePacket());
        }
    }

    @Override
    public String toString() {
        return title;
    }

    /**
     * Get fade in time
     *
     * @return fade in time
     */
    public int getFadeInTime() {
        return fadeInTime * (ticks ? 1 : 20);
    }

    /**
     * Set title fade in time
     *
     * @param time Time
     */
    public void setFadeInTime(int time) {
        this.fadeInTime = time;
        refreshTitle();
    }

    /**
     * Get stay time
     *
     * @return stay time
     */
    public int getStayTime() {
        return stayTime * (ticks ? 1 : 20);
    }

    /**
     * Set title stay time
     *
     * @param time Time
     */
    public void setStayTime(int time) {
        this.stayTime = time;
        refreshTitle();
    }

    /**
     * Get fade out time
     *
     * @return fade out time
     */
    public int getFadeOutTime() {
        return fadeOutTime * (ticks ? 1 : 20);
    }

    /**
     * Set title fade out time
     *
     * @param time Time
     */
    public void setFadeOutTime(int time) {
        this.fadeOutTime = time;
        refreshTitle();
    }

    /**
     * Get title text
     *
     * @return Title text
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set title text
     *
     * @param title Title
     */
    public void setTitle(String title) {
        this.title = title;
        refreshTitle();
    }

    /**
     * Get subtitle text
     *
     * @return Subtitle text
     */
    public String getSubtitle() {
        return this.subtitle;
    }

    /**
     * Set subtitle text
     *
     * @param subtitle Subtitle text
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        refreshTitle();
    }

    /**
     * Get the title color
     *
     * @return tile color
     */
    public ChatColor getTitleColor() {
        return titleColor;
    }

    /**
     * Set the title color
     *
     * @param color Chat color
     */
    public void setTitleColor(ChatColor color) {
        this.titleColor = color;
        refreshTitle();
    }

    /**
     * Get the subtitle color
     *
     * @return subtitle color
     */
    public ChatColor getSubtitleColor() {
        return subtitleColor;
    }

    /**
     * Set the subtitle color
     *
     * @param color Chat color
     */
    public void setSubtitleColor(ChatColor color) {
        this.subtitleColor = color;
        refreshTitle();
    }

    /**
     * Check if the timings are in ticks.
     *
     * @return
     */
    public boolean isInTicks() {
        return ticks;
    }

    /**
     * Set timings to ticks
     */
    public void setInTicks(boolean inTicks) {
        ticks = inTicks;
        refreshTitle();
    }
}