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
package com.mcmiddleearth.mcme.events.PVP.Handlers;

import com.mcmiddleearth.mcme.events.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Eric
 */
public class ArrowHandler implements Listener{
    
    public static void initializeArrowHandling(){
        Bukkit.getPluginManager().registerEvents(new ArrowHandler(), Main.getPlugin());
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), despawnArrows, 0, 5);
    }
    
    public static Runnable despawnArrows = new Runnable(){
        @Override
        public void run(){
            if(Bukkit.getOnlinePlayers().size() > 0){
                Player p = (Player) Bukkit.getOnlinePlayers().toArray()[0];
            
                for(Arrow arrow : p.getWorld().getEntitiesByClass(Arrow.class)){
                
                    if(arrow.isOnGround()){
                        arrow.remove();
                    }
                
                }
            }
            
        }
    };
    
    @EventHandler
    public void onArrowPickup (PlayerPickupArrowEvent e){
        e.setCancelled(true);
    }
    
}
