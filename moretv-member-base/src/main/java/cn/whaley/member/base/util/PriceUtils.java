package cn.whaley.member.base.util;

/**
 * 价格工具方法
 * 
 * @author linfeng 2016年6月13日
 * 
 */
public final class PriceUtils {

	private PriceUtils() {
	}

	public static int calculatePrice(int duration, int totalPrice, int totalDuration) {
		int result = (int) Math.round(((double) duration / totalDuration) * totalPrice);
		return result;
	}
	
	public static int calculatePriceBysecondTime( int price, Long remaindTime,Long totalTime) {
		int result = (int) Math.round(((double) remaindTime / totalTime) * price);
		return result;
	}
	
	
	public static int ceilByTwo( int price) {
		int result = (int)(Math.ceil(price*1.0/100)*100);
		return result;
	}
}
