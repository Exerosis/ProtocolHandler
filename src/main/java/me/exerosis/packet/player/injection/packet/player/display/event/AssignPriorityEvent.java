package me.exerosis.packet.player.injection.packet.player.display.event;

import me.exerosis.packet.player.injection.packet.player.display.Displayable;

public class AssignPriorityEvent extends DisplayEvent {
    private int _oldPriority;
    private int _newPriority;

    public AssignPriorityEvent(Displayable displayable, int oldPriority, int newPriority) {
        super(displayable);
        _oldPriority = oldPriority;
        _newPriority = newPriority;
    }

    public int getOldPriority() {
        return _oldPriority;
    }

    public int getNewPriority() {
        return _newPriority;
    }

    public void setNewPriority(int newPriority) {
        _newPriority = newPriority;
    }
}