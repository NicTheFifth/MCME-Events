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
package com.mcmiddleearth.mcme.events.PVP;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mcmiddleearth.mcme.events.Main;
import com.mcmiddleearth.mcme.events.Util.DBmanager;
import com.mcmiddleearth.mcme.events.PVP.Gamemode.Gamemode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 *
 * @author Donovan <dallen@dallen.xyz>
 */
public class PlayerStat {
    
    @Getter @Setter
    private ArrayList<String> Kills = new ArrayList<String>();
    
    @Getter @Setter
    private int Deaths;
    
    @Getter @Setter
    private String rank;
    
    @Getter @Setter
    private int GamesPlayed;
    
    @Getter @Setter
    private int Suicides;
    
    @Getter @Setter
    private int Score;
    
    @Getter @Setter @JsonIgnore
    private ArrayList<Achivement> chives = new ArrayList<>();
    
    @Getter @Setter
    private HashMap<Gamemode, Integer> favGames = new HashMap<>();
    
    @Getter @Setter
    private static HashMap<String, PlayerStat> playerStats = new HashMap<>();
    
    @Getter @Setter @JsonIgnore    
    private String name;
    
    public PlayerStat(){}
    
    public static boolean loadStat(OfflinePlayer p){
        File loc = new File(PVPCore.getSaveLoc() + Main.getFileSep() + "stats" + Main.getFileSep() + p.getUniqueId());
        if(loc.exists()){
            PlayerStat ps = (PlayerStat) DBmanager.loadObj(PlayerStat.class, loc);
            ps.setName(p.getName());
            playerStats.put(p.getName(), ps);
            return true;
        }else{
            playerStats.put(p.getName(), new PlayerStat());
            return false;
        }
    }
    
    public void saveStat(){
        File loc = new File(PVPCore.getSaveLoc() + Main.getFileSep() + "stats");
        DBmanager.saveObj(PlayerStat.class, loc, name);
    }
    
    public void addDeath(){Deaths++;}
    public void addSuicide(){Suicides++;}
    public void addKill(String k){Kills.add(k);}
//    public void addScore(int score){Score+=score;}
    public void addPlayedGame(Gamemode g){favGames.put(g, favGames.get(g));}
    
    public static class Achivement{
        @Getter @Setter
        private String name;
        
        @Getter @Setter
        private int Points;
    }
    
    public static class StatLitener implements Listener{
        
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent e){
            Player d = e.getEntity();
            if(PVPCore.getPlaying().containsKey(d.getName())){
                PlayerStat ps = PlayerStat.getPlayerStats().get(d.getName());
                if(d.getKiller() != null){
                    Player k = d.getKiller();
                    if(PVPCore.getPlaying().containsKey(k.getName())){
                        PlayerStat.getPlayerStats().get(k.getName()).addKill(d.getName());
                    }
                }else{
                    ps.addSuicide();
                }
                ps.setDeaths(ps.getDeaths()+1);
            }
        }
    }
}
