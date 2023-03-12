/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.commands.bedwars.subcmds.regular;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.command.ParentCommand;
import com.andrei1058.bedwars.api.command.SubCommand;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.SetupSession;
import com.andrei1058.bedwars.commands.bedwars.MainCommand;
import com.andrei1058.bedwars.configuration.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.andrei1058.bedwars.api.language.Language.getMsg;

public class CmdArenaStart extends SubCommand {

    public CmdArenaStart(ParentCommand parent, String name) {
        super(parent, name);
        setPriority(15);
        showInList(true);
        setDisplayInfo(MainCommand.createTC(
                "§6 ▪ §7/"+ MainCommand.getInstance().getName() + " " + getSubCommandName() + " §8 - §eforce start an arena",
                "/" + getParent().getName() + " " + getSubCommandName(),
                "§fForcestart an arena.\n§fPermission: §c" + Permissions.PERMISSION_ARENASTART));
    }

    @Override
    public boolean execute(String[] args, CommandSender s) {
        Player p = (Player) s;
        IArena a = Arena.getArenaByName(args[0].toLowerCase());

        if (a.getPlayers().size() < 2) return true;
        if (a.getStatus() == GameState.playing) return true;
        if (a.getStatus() == GameState.restarting) return true;
        if (a.getStartingTask() == null){
            a.changeStatus(GameState.starting);
        }
        if (a.getStartingTask().getCountdown() < 5) return true;
        a.getStartingTask().setCountdown(5);
        p.sendMessage(getMsg(p, Messages.COMMAND_FORCESTART_SUCCESS));
        return true;
    }

    @Override
    public List<String> getTabComplete() {
        List<String> list = new ArrayList<>();

        for (IArena arena : Arena.getArenas()){
            list.add(arena.getArenaName());
        }

        return list;
    }

    @Override
    public boolean canSee(CommandSender s, com.andrei1058.bedwars.api.BedWars api) {
        Player p = (Player) s;

        if (SetupSession.isInSetupSession(p.getUniqueId())) return false;

        return s.hasPermission(Permissions.PERMISSION_ARENASTART);
    }
}
