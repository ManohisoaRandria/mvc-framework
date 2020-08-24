/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author P11A-MANOHISOA
 */
public class MainConverter {
    public static Object convert(String value,Class<?> type)throws Exception{
        Object retour=new Object();
        try{
             switch (type.getName()){
                    case "java.sql.Date":
                        SimpleDateFormat form=new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date d31=form.parse(value);
                        retour=d31;
                    break;
                    case "java.util.Date":retour=Date.valueOf(value);
                    break;
                    case "int":retour=Integer.parseInt(value);
                    break;
                    case "java.lang.String":retour=value;
                    break;
                    case "java.lang.Integer":retour=Integer.valueOf(value);
                    break;
                    case "double":retour=Double.parseDouble(value);
                    break;
                    case "java.lang.Double":retour=Double.valueOf(value);
                    break;
                    case "float":retour=Float.parseFloat(value);
                    break;
                    case "java.lang.Float":retour=Float.valueOf(value);
                    break;
                    case "long":retour=Long.parseLong(value);
                    break;
                    case "java.lang.Long":retour=Long.valueOf(value);
                    break;
                    case "boolean":retour=Boolean.parseBoolean(value);
                    break;
                    case "java.lang.Boolean":retour=Boolean.valueOf(value);
                    break;
                    case "short":retour=Short.parseShort(value);
                    break;
                    case "java.lang.Short":retour=Short.valueOf(value);
                    break;
                    case "byte":retour=Byte.parseByte(value);
                    break;
                    case "java.lang.Byte":retour=Byte.valueOf(value);
                    break;
                    default:throw new NotParseableException("Tsy anatn type parseable ny argument anao de type "+type.getName());
            }
        }catch(NumberFormatException | NotParseableException ex){
            throw ex;
        }
       
        return retour;
    }
}
