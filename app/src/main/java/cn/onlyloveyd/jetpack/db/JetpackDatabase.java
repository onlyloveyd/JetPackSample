package cn.onlyloveyd.jetpack.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import cn.onlyloveyd.jetpack.converter.DateConverter;
import cn.onlyloveyd.jetpack.dao.ArticleDao;
import cn.onlyloveyd.jetpack.dao.UserDao;
import cn.onlyloveyd.jetpack.entity.Article;
import cn.onlyloveyd.jetpack.entity.User;

/**
 * UserDatabase
 *
 * @author 45742
 * @date 2019/1/4 09:07
 */
@Database(entities = {User.class, Article.class}, version = 3)
@TypeConverters(DateConverter.class)
public abstract class JetpackDatabase extends RoomDatabase {
    private static final Object sLock = new Object();

    public abstract UserDao userDao();

    private static JetpackDatabase INSTANCE;

    public static JetpackDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        JetpackDatabase.class, "Sample.db")
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract ArticleDao articelDao();
}
