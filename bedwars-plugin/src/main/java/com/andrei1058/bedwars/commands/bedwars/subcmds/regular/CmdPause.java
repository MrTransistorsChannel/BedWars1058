package com.andrei1058.bedwars.commands.bedwars.subcmds.regular;

import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public class CmdPause extends SubCommand {

    public CmdPause(ParentCommand parent, String name) {
        super(parent, name);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " "+getSubCommandName()+" §6<worldName>","§fPause arena and prevent players from moving.",
                "/" + getParent().getName() + " "+getSubCommandName()+ " ", ClickEvent.Action.SUGGEST_COMMAND));
        showInList(true);
        setPriority(10);
        setPermission(Permissions.PERMISSION_ARENA_ENABLE);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (s instanceof ConsoleCommandSender) return false;

        s.sendMessage(args);

        return true;
        /*if (args.length != 1) {
            s.sendMessage("§c▪ §7Usage: §o/" + getParent().getName() + " pause <mapName>");
            return true;
        }
        return true;*/
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }
}
