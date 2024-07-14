import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class Date_related_lab {
    public static void main(String[] args) throws ParseException {
        // TODO: 1. get current date
        // System.currentTimeMillis() Returns the current time in milliseconds.
        System.out.println("1========================");
        Date curTime = new Date(System.currentTimeMillis());
        System.out.println(curTime);

        // TODO: 2. self-defined date
        System.out.println("2========================");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy", Locale.CHINA);
        Date any_date = sdf1.parse("1999-04-31 09:30:21");
        Date only_year = sdf2.parse("1999");
        System.out.println(any_date);

        // TODO: 3. date operations (i.e. add 3 days to current date)
        System.out.println("3========================");
        long year = 31104000000L;
        long month = 2592000000L;
        long day = 86400000L;
        long ms = any_date.getTime();
        // 1999-01-31 09:30:21 减少3年
        System.out.println("minus 3 years: " + new Date(ms - 3*year));
        System.out.println("minus 3 months: " + new Date(ms - 3*month));
        System.out.println("minus 3 days: " + new Date(ms - 3*day));

        // TODO: 4. toString()
        System.out.println("4========================");
        String strDate = any_date.toString();
        // 我们发现在直接print的时候，它会自动把Date转成String类型
        System.out.println(strDate);

        // TODO: 5. after(); before(); equals()
        System.out.println("5========================");
        // A.after(B) 判断 A 的日期是否在 B 之后，如果是，则返回true
        System.out.println(any_date.after(only_year)); // 1999 < 1999-04-31
        // A.before(B) 判断 A 的日期是否在 B 之前，如果是，则返回true
        System.out.println(any_date.before(new Date(ms - 3*year)));
        // A.equals(B) 判断 A 的日期是否和 B 相等，如果是，则返回true
        System.out.println(any_date.equals(any_date));

        // TODO: 6. compareTo()
        System.out.println("6========================");
        // A.compareTo(B)
        //0  : if the A = B.
        System.out.println(any_date.compareTo(any_date));
        //1 : if A > B.
        System.out.println(any_date.compareTo(new Date(ms - 3*year)));
        //-1  : if A < B.
        System.out.println(any_date.compareTo(new Date(ms + 3*year)));

        // TODO: getTime(); getYear(); getMonth()
        System.out.println("========================");
        long milliseconds = any_date.getTime();
        // getYear() 返回的是从1900开始计算的，即2024会返回124
        int iYear = curTime.getYear();
        // getMonth() 返回0-11之间的int
        int iMonth = any_date.getMonth();
        System.out.println("seconds "+milliseconds);
        System.out.println("year "+iYear);
        System.out.println("month "+iMonth);


    }
}