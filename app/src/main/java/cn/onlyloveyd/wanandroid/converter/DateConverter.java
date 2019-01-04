package cn.onlyloveyd.wanandroid.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * DateConverter
 *
 * @author 45742
 * @date 2019/1/4 09:05
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
