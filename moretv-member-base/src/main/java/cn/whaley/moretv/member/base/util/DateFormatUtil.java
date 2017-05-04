package cn.whaley.moretv.member.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.whaley.moretv.member.base.dto.MonthAndDay;

public class DateFormatUtil {  
    /** 
     * 字符串转为日期格式 
     * @param dateString 
     * @return 
     * @throws ParseException 
     */  
    public static Date stringFormatDate(String dateString) {  
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");   
        Date date;
		try {
			date = bartDateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}   
        return date;  
    }  
    /** 
     * 字符串转为日期格式 
     * @param dateString 
     * @return 
     * @throws ParseException 
     */  
    public static Date stringFormatDateTime(String dateString){  
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        Date date;
		try {
			date = bartDateFormat.parse(dateString);
		} catch (ParseException e) {
			return null;
		}   
        return date;  
    }
    
    /** 
     * 将时间格式化为含时分秒的字符串 
     * @param date 
     * @return 
     * @throws ParseException 
     */  
    public static String dateTimeFormatString(Date date){  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        return dateFormat.format(date);  
    }  
      
    /** 
    * 按日加 
    *  
    * @param value 
    * @return 
    */  
    public static Date addDay(int value) {  
       Calendar now = Calendar.getInstance();  
       now.add(Calendar.DAY_OF_YEAR, value);  
       return now.getTime();  
    }
    
    /** 
     * 按分钟加 
     *  
     * @param value 
     * @return 
     */  
     public static Date addMinute(int value) {  
        Calendar now = Calendar.getInstance();  
        now.add(Calendar.MINUTE, value);  
        return now.getTime();  
     }
    
    /** 
     * 按日加 
     *  
     * @param value 
     * @return 
     */  
     public static Date addDay(Date date,int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_YEAR, value);  
        return calendar.getTime();  
     } 
     
     /** 
      * 按月加 
      *  
      * @param value 
      * @return 
      */  
      public static Date addMonth(int value) {  
         Calendar now = Calendar.getInstance();  
         now.add(Calendar.MONTH, value);  
         return now.getTime();  
      }
      
      /** 
       * 按月加 
       *  
       * @param value 
       * @return 
       */ 
       public static Date addMonth(Date month,int value) {
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(month);
          calendar.add(Calendar.MONTH, value);  
          return calendar.getTime();  
       }
       
       /** 
        * 按月加 
        *  
        * @param value 
        * @return 
        */  
        public static Date addMonthAndDay(Date date,int valueMonth,int valueDay) {
           Calendar calendar = Calendar.getInstance();
           calendar.setTime(date);
           if (valueMonth!=0) {
        	   calendar.add(Calendar.MONTH, valueMonth); 
           }
           if (valueDay!=0) {
        	   calendar.add(Calendar.DATE, valueDay);  
           }
           return calendar.getTime();  
        }
       
       /** 
        * 按月时加
        *  
        * @param value 
        * @return 
        */  
        public static Date addHours(Date hours,int value) {
           Calendar calendar = Calendar.getInstance();
           calendar.setTime(hours);
       	calendar.add(Calendar.HOUR, value);  
           return calendar.getTime();  
        } 
       
       /**
        * 得到两日期相差几个月
        * 
        * @param String
        * @return
        */
       public static int getMonth(Date startDate1, Date endDate1) {
    	   
    	   int monthday;
           Calendar starCal = Calendar.getInstance();
           starCal.setTime(startDate1);

           int sYear = starCal.get(Calendar.YEAR);
           int sMonth = starCal.get(Calendar.MONTH);
           int sDay = starCal.get(Calendar.DATE);

           Calendar endCal = Calendar.getInstance();
           endCal.setTime(endDate1);
           int eYear = endCal.get(Calendar.YEAR);
           int eMonth = endCal.get(Calendar.MONTH);
           int eDay = endCal.get(Calendar.DATE);

           monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

           if (sDay < eDay) {
               monthday = monthday + 1;
           }
           return monthday;
       }
       
       /**
        * 得到两日期相差几个月
        * 
        * @param String
        * @return
        */
       public static MonthAndDay getMonthAndDay(Date startDate1, Date endDate1) {
    	   
    	   MonthAndDay monthAndDay = new MonthAndDay();
    	   
    	   int monthday;
           Calendar starCal = Calendar.getInstance();
           starCal.setTime(startDate1);

           int sYear = starCal.get(Calendar.YEAR);
           int sMonth = starCal.get(Calendar.MONTH);
           int sDay = starCal.get(Calendar.DATE);

           Calendar endCal = Calendar.getInstance();
           endCal.setTime(endDate1);
           int eYear = endCal.get(Calendar.YEAR);
           int eMonth = endCal.get(Calendar.MONTH);
           int eDay = endCal.get(Calendar.DATE);

           monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));
           if (eDay < sDay) {
               monthday = monthday - 1;
           }

           starCal.add(Calendar.MONTH,monthday);
           Long sencondMini = endCal.getTimeInMillis() - starCal.getTimeInMillis();
           int day = (int) Math.ceil(sencondMini/(24*3600*1000*1.0));
           
           if (day ==31) {
        	   monthday = monthday + 1;
        	   day=0;
           }
           
           monthAndDay.setMonthValue(monthday);
           monthAndDay.setDayValue(day);
           return monthAndDay;
       }
       
       /**
        * 相差天数
        * 
        * @param String
        * @return
        */
       public static int getDifferenceDay(Date startDate, Date endDate) {
    	   Long differenceLong=endDate.getTime()-startDate.getTime();
    	   int day = (int) Math.ceil(differenceLong/(24*3600*1000*1.0));
           return day;
       }
}  
