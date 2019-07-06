package com.andrei1058.bedwars.upgrades;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.arena.BedWarsTeam;

public class EnemyBaseEnterAction extends UpgradeAction {

    private String name;
    private boolean chat = false, action = false, title = false, subtitle = false;

    public EnemyBaseEnterAction(String name){
        this.name = name;
        Main.debug("loading new EnemyBaseEnterAction: "+getName());
    }

    @Override
    public void execute(BedWarsTeam bwt, int slot) {
        bwt.enableTrap(slot);
        bwt.setTrapAction(isAction());
        bwt.setTrapChat(isChat());
        bwt.setTrapSubtitle(isSubtitle());
        bwt.setTrapTitle(isTitle());
    }

    public String getName() {
        return name;
    }

    public boolean isChat() {
        return chat;
    }

    public boolean isAction() {
        return action;
    }

    public boolean isSubtitle() {
        return subtitle;
    }

    public boolean isTitle() {
        return title;
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public void setSubtitle(boolean subtitle) {
        this.subtitle = subtitle;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public boolean isSomethingTrue(){
        return isChat() || isAction() || isSubtitle() || isTitle();
    }
}
