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
package com.mcmiddleearth.mcme.events.Util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import lombok.Getter;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author Donovan <dallen@dallen.xyz>
 */
public class DBmanager {
    
    @Getter
    private static ObjectMapper JSonParser;
    
    public static boolean saveObj(Object obj, File loc){
        boolean success = true;
        File locStart = new File(loc+".new");
        try {
           JSonParser.writeValue(locStart, obj);
        } catch (IOException ex) {
            success = false;
        } finally {
            if (success) {
                if (loc.exists()) {
                    loc.delete();
                }
                locStart.renameTo(loc);
            }
        }
        return success;
    }
    
    public static Object loadObj(Class type, File loc){
        Object obj;
        try {
            obj = JSonParser.readValue(loc, type);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    public static HashMap<String, Object> loadAllObj(Class Type, File loc){
        HashMap<String, Object> rtn = new HashMap<String, Object>();
        for(File f : loc.listFiles()){
            rtn.put(f.getName(), loadObj(Type, f));
        }
        return rtn;
    }
}