/*
 * This file is part of MCME-Events.
 * 
 * MCME-Events is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MCME-Events is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MCME-Events.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 */
package com.mcmiddleearth.mcme.events.PVP.Gamemode;

import com.mcmiddleearth.mcme.events.Main;
import com.mcmiddleearth.mcme.events.PVP.Handlers.GearHandler;
import com.mcmiddleearth.mcme.events.PVP.Handlers.GearHandler.SpecialGear;
import com.mcmiddleearth.mcme.events.PVP.PVPCommandCore;
import com.mcmiddleearth.mcme.events.PVP.PVPCore;
import com.mcmiddleearth.mcme.events.PVP.maps.Map;
import com.mcmiddleearth.mcme.events.PVP.PlayerStat;
import com.mcmiddleearth.mcme.events.PVP.Team;
import com.mcmiddleearth.mcme.events.PVP.Team.Teams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

/**
 *
 * @author Donovan <dallen@dallen.xyz>
 */
public class TeamSlayer extends BasePluginGamemode{
    
    private int target;
    
    private boolean eventsRegistered = false;
    
    @Getter
    private final ArrayList<String> NeededPoints = new ArrayList<String>(Arrays.asList(new String[] {
        "RedSpawn1",
        "RedSpawn2",
        "RedSpawn3",
        "BlueSpawn1",
        "BlueSpawn2",
        "BlueSpawn3",
    }));
    
    @Getter
    private GameState state;
    
    private final int midgameJoinPointThreshold = 10;
    
    private final int giveTntPointThreshold = 1;//30
    
    private boolean givenTnt = false;
    
    Map map;
    
    private int count;
    
    @Getter
    private Objective Points;
    
    private GameEvents events;
    
    @Getter
    private boolean midgameJoin = true;
    
    public TeamSlayer(){
        state = GameState.IDLE;
    }
    
    @Override
    public void Start(Map m, int parameter){
        count = PVPCore.getCountdownTime();
        state = GameState.COUNTDOWN;
        givenTnt = false;
        int lastRedSpawn = 3;
        int lastBlueSpawn = 3;
        super.Start(m, parameter);
        this.map = m;
        target = parameter;
        
        if(!map.getImportantPoints().keySet().containsAll(NeededPoints)){
            for(Player p : players){
                p.sendMessage(ChatColor.RED + "Game cannot start! Not all needed points have been added!");
            }
            End(m);
        }
        
        if(!eventsRegistered){
            events = new GameEvents();
            PluginManager pm = Main.getServerInstance().getPluginManager();
            pm.registerEvents(events, Main.getPlugin());
            eventsRegistered = true;
        }
        for(Player p : players){
            if(Team.getRed().size() <= Team.getBlue().size()){
                Team.getRed().add(p);
                switch(lastRedSpawn){
                    case 1:
                        p.teleport(m.getImportantPoints().get("RedSpawn2").toBukkitLoc().add(0, 2, 0));
                        lastRedSpawn = 2;
                        break;
                    case 2:
                        p.teleport(m.getImportantPoints().get("RedSpawn3").toBukkitLoc().add(0, 2, 0));
                        lastRedSpawn = 3;
                        break;
                    case 3:
                        p.teleport(m.getImportantPoints().get("RedSpawn1").toBukkitLoc().add(0, 2, 0));
                        lastRedSpawn = 1;
                        break;
                    default:
                        p.teleport(m.getImportantPoints().get("RedSpawn1").toBukkitLoc().add(0, 2, 0));
                        lastRedSpawn = 1;
                        break;
                }
            }
            else if(Team.getBlue().size() < Team.getRed().size()){
                Team.getBlue().add(p);
                switch(lastBlueSpawn){
                    case 1:
                        p.teleport(m.getImportantPoints().get("BlueSpawn2").toBukkitLoc().add(0, 2, 0));
                        lastBlueSpawn = 2;
                        break;
                    case 2:
                        p.teleport(m.getImportantPoints().get("BlueSpawn3").toBukkitLoc().add(0, 2, 0));
                        lastBlueSpawn = 3;
                        break;
                    case 3:
                        p.teleport(m.getImportantPoints().get("BlueSpawn1").toBukkitLoc().add(0, 2, 0));
                        lastBlueSpawn = 1;
                        break;
                    default:
                        p.teleport(m.getImportantPoints().get("BlueSpawn1").toBukkitLoc().add(0, 2, 0));
                        lastBlueSpawn = 1;
                        break;
                }
            }
        }
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(!Team.getBlue().getMembers().contains(player) && !Team.getRed().getMembers().contains(player)){
                Team.getSpectator().add(player);
                player.teleport(m.getSpawn().toBukkitLoc().add(0, 2, 0));
            }
        }
            Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable(){
                @Override
                public void run() {
                    if(count == 0){
                        if(state == GameState.RUNNING){
                            return;
                        }

                        Points = getScoreboard().registerNewObjective("Score", "dummy");
                        Points.setDisplayName("Score");
                        Points.getScore(ChatColor.WHITE + "Goal:").setScore(target);
                        Points.getScore(ChatColor.BLUE + "Blue:").setScore(0);
                        Points.getScore(ChatColor.RED + "Red:").setScore(0);
                        Points.setDisplaySlot(DisplaySlot.SIDEBAR);
                        
                        for(Player p : Bukkit.getServer().getOnlinePlayers()){
                            p.sendMessage(ChatColor.GREEN + "Game Start!");
                            p.setScoreboard(getScoreboard());
                        }
                        
                        for(Player p : Team.getRed().getMembers()){
                            GearHandler.giveGear(p, ChatColor.RED, SpecialGear.NONE);
                        }
                        for(Player p : Team.getBlue().getMembers()){
                            GearHandler.giveGear(p, ChatColor.BLUE, SpecialGear.NONE);
                        }
                        state = GameState.RUNNING;
                        count = -1;
                        
                        for(Player p : players){
                            p.sendMessage(ChatColor.GRAY + "Use " + ChatColor.GREEN + "/unstuck" + ChatColor.GRAY + " if you're stuck in a block!");
                        }
                        
                    }
                    else if(count != -1){
                        for(Player p : Bukkit.getServer().getOnlinePlayers()){
                            p.sendMessage(ChatColor.GREEN + "Game begins in " + count);
                        }
                        count--;
                    }
                }

            }, 40, 20);
    }
    
    public void End(Map m){
        state = GameState.IDLE;

        getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        m.playerLeaveAll();
        
        super.End(m);
    }
    
    @Override
    public void playerLeave(Player p){
        Team.removeFromTeam(p);
    }
    
    public boolean midgamePlayerJoin(Player p){
        
        if(Team.getRed().getAllMembers().contains(p)){
            addToTeam(p, Teams.RED);
        }
        else if(Team.getBlue().getAllMembers().contains(p)){
            addToTeam(p, Teams.BLUE);
        }
        
        else if(Points.getScore(ChatColor.RED + "Red:").getScore() - Points.getScore(ChatColor.BLUE + "Blue:").getScore() >= midgameJoinPointThreshold){
            addToTeam(p, Teams.BLUE);
            
        }
        else if(Points.getScore(ChatColor.BLUE + "Blue:").getScore() - Points.getScore(ChatColor.RED + "Red:").getScore() >= midgameJoinPointThreshold){
            addToTeam(p, Teams.RED);
        }
        else{
            if(Team.getRed().size() >= Team.getBlue().size()){
               addToTeam(p, Teams.BLUE);
            }
            else{
                addToTeam(p, Teams.RED);
            }
        }
        super.midgamePlayerJoin(p);
        return true;
    }
    
    private void addToTeam(Player p, Teams t){
        if(t == Teams.RED){
            Team.getRed().add(p);
            p.teleport(map.getImportantPoints().get("RedSpawn1").toBukkitLoc().add(0, 2, 0));
            GearHandler.giveGear(p, ChatColor.RED, SpecialGear.NONE);
        }
        else{
            Team.getBlue().add(p);
            p.teleport(map.getImportantPoints().get("BlueSpawn1").toBukkitLoc().add(0, 2, 0));
            GearHandler.giveGear(p, ChatColor.BLUE, SpecialGear.NONE);
        }
    }
    
    public String requiresParameter(){
        return "end kill number";
    }
    
    private class GameEvents implements Listener{
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent e){

            if(state == GameState.RUNNING){
                Player p = null;
                int redScore = Points.getScore(ChatColor.RED + "Red:").getScore();
                int blueScore = Points.getScore(ChatColor.BLUE + "Blue:").getScore();
                if(e.getEntity() instanceof Player){
                    p = (Player) e.getEntity();
                }
                
                if(p != null){
                    if(Team.getRed().getMembers().contains(p)){
                        Points.getScore(ChatColor.BLUE + "Blue:").setScore(blueScore + 1);
                    }
                    if(Team.getBlue().getMembers().contains(p)){
                        Points.getScore(ChatColor.RED + "Red:").setScore(redScore + 1);
                    }
                }
            
                if(PVPCommandCore.getRunningGame().getTitle().equals("Helms_Deep") &&
                        Points.getScore(ChatColor.RED + "Red:").getScore() >= giveTntPointThreshold &&
                        !givenTnt){
                    Random r = new Random();
                    
                    Player randomPlayer = (Player) Team.getRed().getMembers().toArray()[r.nextInt(Team.getRed().getMembers().size())];
                    
                    GearHandler.giveCustomItem(randomPlayer, GearHandler.CustomItem.TNT);
                    givenTnt = true;
                    
                }
                
                if(Points.getScore(ChatColor.RED + "Red:").getScore() >= target){
                                
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.sendMessage(ChatColor.RED + "Game over!");
                        player.sendMessage(ChatColor.RED + "Red Team Wins!");
                    }
                    PlayerStat.addGameWon(Teams.RED);
                    PlayerStat.addGameLost(Teams.BLUE);
                    PlayerStat.addGameSpectatedAll();
                    End(map);
                
                }
                else if(Points.getScore(ChatColor.BLUE + "Blue:").getScore() >= target){
                
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.sendMessage(ChatColor.BLUE + "Game over!");
                        player.sendMessage(ChatColor.BLUE + "Blue Team Wins!");
                    }
                    PlayerStat.addGameWon(Teams.BLUE);
                    PlayerStat.addGameLost(Teams.RED);
                    PlayerStat.addGameSpectatedAll();
                    End(map);
                
                }
            }
        }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){

        if(state == GameState.RUNNING || state == GameState.COUNTDOWN){
            Team.removeFromTeam(e.getPlayer());
            
            if(Team.getRed().size() <= 0){
                
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.sendMessage(ChatColor.BLUE + "Game over!");
                    p.sendMessage(ChatColor.BLUE + "Blue Team Wins!");
                }
                PlayerStat.addGameWon(Teams.BLUE);
                PlayerStat.addGameLost(Teams.RED);
                PlayerStat.addGameSpectatedAll();
                End(map);
            }
            if(Team.getBlue().size() <= 0){
                
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.sendMessage(ChatColor.RED + "Game over!");
                    p.sendMessage(ChatColor.RED + "Red Team Wins!");
                }
                PlayerStat.addGameWon(Teams.RED);
                PlayerStat.addGameLost(Teams.BLUE);
                PlayerStat.addGameSpectatedAll();
                End(map);
                
            }
        }
    }
        @EventHandler
        public void onPlayerRespawn(PlayerRespawnEvent e){

            if(state == GameState.RUNNING){
                Random random = new Random();
                int spawn = random.nextInt(3) + 1;
                if(Team.getRed().getMembers().contains(e.getPlayer())){
                    switch(spawn){
                        case 1:
                            e.setRespawnLocation(map.getImportantPoints().get("RedSpawn1").toBukkitLoc().add(0, 2, 0));
                            break;
                        case 2:
                            e.setRespawnLocation(map.getImportantPoints().get("RedSpawn2").toBukkitLoc().add(0, 2, 0));
                            break;
                        case 3:
                            e.setRespawnLocation(map.getImportantPoints().get("RedSpawn3").toBukkitLoc().add(0, 2, 0));
                            break;
                    }
                }
                if(Team.getBlue().getMembers().contains(e.getPlayer())){
                    switch(spawn){
                        case 1:
                            e.setRespawnLocation(map.getImportantPoints().get("BlueSpawn1").toBukkitLoc().add(0, 2, 0));
                            break;
                        case 2:
                            e.setRespawnLocation(map.getImportantPoints().get("BlueSpawn2").toBukkitLoc().add(0, 2, 0));
                            break;
                        case 3:
                            e.setRespawnLocation(map.getImportantPoints().get("BlueSpawn3").toBukkitLoc().add(0, 2, 0));
                            break;
                    }
                }
            }
        }
    }
}
