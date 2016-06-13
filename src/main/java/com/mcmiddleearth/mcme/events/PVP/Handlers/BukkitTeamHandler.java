/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcmiddleearth.mcme.events.PVP.Handlers;

import com.mcmiddleearth.mcme.events.PVP.Gamemode.BasePluginGamemode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author Eric
 */
public class BukkitTeamHandler {
    private static org.bukkit.scoreboard.Team aqua;
    private static org.bukkit.scoreboard.Team blue;
    private static org.bukkit.scoreboard.Team darkAqua;
    private static org.bukkit.scoreboard.Team darkBlue;
    private static org.bukkit.scoreboard.Team darkGray;
    private static org.bukkit.scoreboard.Team darkGreen;
    private static org.bukkit.scoreboard.Team darkPurple;
    private static org.bukkit.scoreboard.Team darkRed;
    private static org.bukkit.scoreboard.Team gold;
    private static org.bukkit.scoreboard.Team gray;
    private static org.bukkit.scoreboard.Team green;
    private static org.bukkit.scoreboard.Team lightPurple;
    private static org.bukkit.scoreboard.Team red;
    private static org.bukkit.scoreboard.Team yellow;
     
    
    public static void configureBukkitTeams(){
        aqua = BasePluginGamemode.getScoreboard().getTeam("aqua");
        blue = BasePluginGamemode.getScoreboard().getTeam("blue");
        darkAqua = BasePluginGamemode.getScoreboard().getTeam("darkaqua");
        darkBlue = BasePluginGamemode.getScoreboard().getTeam("darkblue");
        darkGray = BasePluginGamemode.getScoreboard().getTeam("darkgray");
        darkGreen = BasePluginGamemode.getScoreboard().getTeam("darkgreen");
        darkPurple = BasePluginGamemode.getScoreboard().getTeam("darkpurple");
        darkRed = BasePluginGamemode.getScoreboard().getTeam("darkred");
        gold = BasePluginGamemode.getScoreboard().getTeam("gold");
        gray = BasePluginGamemode.getScoreboard().getTeam("gray");
        green = BasePluginGamemode.getScoreboard().getTeam("green");
        lightPurple = BasePluginGamemode.getScoreboard().getTeam("lightpurple");
        red = BasePluginGamemode.getScoreboard().getTeam("red");
        yellow = BasePluginGamemode.getScoreboard().getTeam("yellow");
        
        if(aqua == null){
            aqua = BasePluginGamemode.getScoreboard().registerNewTeam("aqua");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option aqua color aqua");
        }
        if(blue == null){
            blue = BasePluginGamemode.getScoreboard().registerNewTeam("blue");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option blue color blue");
        }
        if(darkAqua == null){
            darkAqua = BasePluginGamemode.getScoreboard().registerNewTeam("darkaqua");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option darkaqua color darkaqua");
        }
        if(darkBlue == null){
            darkBlue = BasePluginGamemode.getScoreboard().registerNewTeam("darkblue");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option darkblue color darkblue");
        }
        if(darkGray == null){
            darkGray = BasePluginGamemode.getScoreboard().registerNewTeam("darkgray");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option darkgray color darkgray");
        }
        if(darkGreen == null){
            darkGreen = BasePluginGamemode.getScoreboard().registerNewTeam("darkgreen");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option darkgreen color darkgreen");
        }
        if(darkPurple == null){
            darkPurple = BasePluginGamemode.getScoreboard().registerNewTeam("darkpurple");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option darkpurple color darkpurple");
        }
        if(darkRed == null){
            darkRed = BasePluginGamemode.getScoreboard().registerNewTeam("darkred");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option darkred color darkred");
        }
        if(gold == null){
            gold = BasePluginGamemode.getScoreboard().registerNewTeam("gold");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option gold color gold");
        }
        if(gray == null){
            gray = BasePluginGamemode.getScoreboard().registerNewTeam("gray");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option gray color gray");
        }
        if(green == null){
            green = BasePluginGamemode.getScoreboard().registerNewTeam("green");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option green color green");
        }
        if(lightPurple == null){
            lightPurple = BasePluginGamemode.getScoreboard().registerNewTeam("lightpurple");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option lightpurple color lightpurple");
        }
        if(red == null){
            red = BasePluginGamemode.getScoreboard().registerNewTeam("red");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option red color red");
        }
        if(yellow == null){
            yellow = BasePluginGamemode.getScoreboard().registerNewTeam("yellow");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "scoreboard teams option yellow color yellow");
        }
    }
    
    public static void addToBukkitTeam(Player p, ChatColor c){
        
        switch(c){
            case AQUA:
                aqua.addPlayer(p);
                break;
            case BLUE:
                blue.addPlayer(p);
                break;
            case DARK_AQUA:
                darkAqua.addPlayer(p);
                break;
            case DARK_BLUE:
                darkBlue.addPlayer(p);
                break;
            case DARK_GRAY:
                darkGray.addPlayer(p);
                break;
            case DARK_GREEN:
                darkGreen.addPlayer(p);
                break;
            case DARK_PURPLE:
                darkPurple.addPlayer(p);
                break;
            case DARK_RED:
                darkRed.addPlayer(p);
                break;
            case GOLD:
                gold.addPlayer(p);
                break;
            case GRAY:
                gray.addPlayer(p);
                break;
            case GREEN:
                green.addPlayer(p);
                break;
            case LIGHT_PURPLE:
                lightPurple.addPlayer(p);
                break;
            case RED:
                red.addPlayer(p);
                break;
            case YELLOW:
                yellow.addPlayer(p);
                break;
        }
    }
    
    public static void removeFromBukkitTeam(Player p){
        if(aqua != null){
            if(aqua.hasPlayer(p)){
                aqua.removePlayer(p);
            }
        }
        if(blue != null){
            if(blue.hasPlayer(p)){
                blue.removePlayer(p);
            }
        }
        if(darkAqua != null){
            if(darkAqua.hasPlayer(p)){
                darkAqua.removePlayer(p);
            }
        }
        if(darkBlue != null){
            if(darkBlue.hasPlayer(p)){
                darkBlue.removePlayer(p);
            }
        }
        if(darkGray != null){
            if(darkGray.hasPlayer(p)){
                darkGray.removePlayer(p);
            }
        }
        if(darkGreen != null){
            if(darkGreen.hasPlayer(p)){
                darkGreen.removePlayer(p);
            }
        }
        if(darkPurple != null){
            if(darkPurple.hasPlayer(p)){
                darkPurple.removePlayer(p);
            }
        }
        if(darkRed != null){
            if(darkRed.hasPlayer(p)){
                darkRed.removePlayer(p);
            }
        }
        if(gold != null){
            if(gold.hasPlayer(p)){
                gold.removePlayer(p);
            }
        }
        if(gray != null){
            if(gray.hasPlayer(p)){
                gray.removePlayer(p);
            }
        }
        if(green != null){
            if(green.hasPlayer(p)){
                green.removePlayer(p);
            }
        }
        if(lightPurple != null){
            if(lightPurple.hasPlayer(p)){
                lightPurple.removePlayer(p);
            }
        }
        if(red != null){
            if(red.hasPlayer(p)){
                red.removePlayer(p);
            }
        }
        if(yellow != null){
            if(yellow.hasPlayer(p)){
                yellow.removePlayer(p);
            }
        }
    }
}
