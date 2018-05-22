import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

public class test {

	
	public static void main(String args[]){
		System.out.println("xxxx");
		List<String> list = getLastTwelveMonth();
		System.out.println(list);
		
		
		
	}
	
	private static List<String> getLastTwelveMonth(){
		List list = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(calendar.MONTH, 1);
		
		for(int i=0; i < 12 ; i++){
			calendar.add(calendar.MONTH, -1);
			list.add(DateFormatUtils.format(calendar.getTime(),"yyyy-MM"));
		}
		return list;
		
	}
	
}
