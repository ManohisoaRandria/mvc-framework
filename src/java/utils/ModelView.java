/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;

/**
 *
 * @author P11A-MANOHISOA
 */
public class ModelView {
    private static final HashMap<String,Object>DATA=new HashMap<>();

    public ModelView() {
        DATA.clear();
    }
    public void add(String name,Object data){
        DATA.put(name, data);
    }
    public Object get(String name){
        return DATA.get(name);
    }
//    public static String findDay(int month, int day, int year) {
//        if(year>3000 || year<2000)return null;
//       
//        
//        Calendar cal=new GregorianCalendar(year,month-1,day);
//        cal.set(year,month-1,day);
//        int days=cal.get(Calendar.DAY_OF_WEEK);
//     
//        HashMap<Integer,String>data=new HashMap<>();
//        data.put(Calendar.MONDAY,cal.getDisplayName(Calendar.MONDAY,Calendar.LONG, Locale.US));
//        data.put(Calendar.TUESDAY,cal.getDisplayName(Calendar.TUESDAY,Calendar.LONG, Locale.US));
//        data.put(Calendar.WEDNESDAY,cal.getDisplayName(Calendar.WEDNESDAY,Calendar.LONG, Locale.US));
//        data.put(Calendar.THURSDAY,cal.getDisplayName(Calendar.THURSDAY,Calendar.LONG, Locale.US));
//        data.put(Calendar.FRIDAY,cal.getDisplayName(Calendar.FRIDAY,Calendar.LONG, Locale.US));
//        data.put(Calendar.SATURDAY,cal.getDisplayName(Calendar.SATURDAY,Calendar.LONG, Locale.US));
//        data.put(Calendar.SUNDAY,cal.getDisplayName(Calendar.SUNDAY,Calendar.LONG, Locale.US));
//       
//        return data.get(days);
//    }
     
}
