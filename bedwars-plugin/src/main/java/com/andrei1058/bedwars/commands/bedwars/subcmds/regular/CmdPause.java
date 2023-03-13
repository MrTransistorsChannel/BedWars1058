package com.andrei1058.bedwars.commands.bedwars.subcmds.regular;

import com.andrei1058.bedwars.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.api.tasks.PlayingTask;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.Misc;
import com.andrei1058.bedwars.arena.tasks.GamePlayingTask;
import com.andrei1058.bedwars.configuration.Permissions;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class CmdPause extends SubCommand {

    public CmdPause(ParentCommand parent, String name) {
        super(parent, name);
        setDisplayInfo(Misc.msgHoverClick("§6 ▪ §7/" + getParent().getName() + " " + getSubCommandName() + " §6<worldName>", "§fPause arena and prevent players from moving.",
                "/" + getParent().getName() + " " + getSubCommandName() + " ", ClickEvent.Action.SUGGEST_COMMAND));
        showInList(true);
        setPriority(10);
        setPermission(Permissions.PERMISSION_ARENA_ENABLE);
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        if (args.length != 1) {
            s.sendMessage("§c▪ §7Usage: §o/" + getParent().getName() + " pause <mapName>");
            return true;
        }

        Arena a = (Arena) Arena.getArenaByName(args[0].toLowerCase());
        if (a == null) {
            s.sendMessage(ChatColor.RED + "There`s no such arena");
            return true;
        }
        PlayingTask playingTask = a.getPlayingTask();
        if (playingTask == null) {
            s.sendMessage(ChatColor.RED + "This arena is not running");
            return true;
        }

        if(a.getStatus() == GameState.playing)
            a.setStatus(GameState.paused);
        else if(a.getStatus() == GameState.paused)
            a.setStatus(GameState.playing);

        return true;
    }

    @Override
    public List<String> getTabComplete() {
        return null;
    }
}
