import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.util.StringUtils;

public class test {

	private static int num = 100;
	
	public static void main(String args[]){
		System.out.println("xxxx");
		System.out.println(getReturnNo("TH"));
		
		
		
	}
	
	public static synchronized String getReturnNo(String sign) {
		String temp = "SD";
		if (sign != null) {
			if (!StringUtils.isEmpty(sign)) {
				temp = sign;
			}
		}
		long time = System.currentTimeMillis();
		String result = temp + time + num;
		num++;
		if (num == 1000) {
			num = 100;
		}
		return result;
	}
	
}
