package easy.tuto.easycalculator;
import android.provider.BaseColumns;

public interface Constants extends BaseColumns {
    public static final String TABLE_NAME = "events";
    public static final String _ID = "_id";
    public static final String TIME = "time";
    public static final String SOLUTION = "solution";

    public static final String RESULT = "result";

    public static final String DATABASE_NAME = "events.db";
    public static final int DATABASE_VERSION = 1;

}