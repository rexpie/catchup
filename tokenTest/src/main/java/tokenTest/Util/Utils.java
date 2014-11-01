package tokenTest.Util;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class Utils {
	public static Integer getAge(Date birthday){
		if (birthday == null) return 0;
		
		LocalDate birthdate = new LocalDate (birthday.getTime());          //Birth date
		LocalDate now = new LocalDate();                    //Today's date
		Period period = new Period(birthdate, now, PeriodType.yearMonthDay());
		
		return period.getYears();
		
	}
}
