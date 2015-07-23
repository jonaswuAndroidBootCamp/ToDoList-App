package androidcourse.android.jonaswu.yahoo.com.todolists;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jonaswu on 2015/7/23.
 */
public class utils {
    public String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date());
    }
}
