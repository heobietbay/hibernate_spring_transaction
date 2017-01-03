package khoa.training.hibernate.util;

import org.hibernate.EmptyInterceptor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by trandangkhoa on 12/26/2016.
 */
public class MyInterceptor extends EmptyInterceptor {
    @Override
    public String onPrepareStatement(String sql) {
        String indexClause = extractIndexClause(sql);
        if(indexClause != null)
        {
            sql = sql + " /* FOR TESTING khoa.training.hibernate.util.MyInterceptor */";
        }
        return super.onPrepareStatement(sql);
    }

    private String extractIndexClause(String sql)
    {
        String regex = "useindex(.*\\))";
        Matcher m = Pattern.compile(regex).matcher(sql);
        while(m.find()) {
            return m.group(0);
        }
        return null;
    }
}
