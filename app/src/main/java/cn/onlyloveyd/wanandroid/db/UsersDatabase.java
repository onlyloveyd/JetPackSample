package cn.onlyloveyd.wanandroid.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import cn.onlyloveyd.wanandroid.converter.DateConverter;
import cn.onlyloveyd.wanandroid.dao.UserDao;
import cn.onlyloveyd.wanandroid.entity.User;

/**
 * UserDatabase
 *
 * @author 45742
 * @date 2019/1/4 09:07
 */
@Database(entities = {User.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class UsersDatabase extends RoomDatabase {
    private static UsersDatabase INSTANCE;

    public abstract UserDao userDao();

    private static final Object sLock = new Object();

    public static UsersDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        UsersDatabase.class, "Sample.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
