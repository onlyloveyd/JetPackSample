package cn.onlyloveyd.wanandroid.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import cn.onlyloveyd.wanandroid.entity.User;

/**
 * UserDao
 *
 * @author 45742
 * @date 2019 /1/4 08:29
 */
@Dao
public interface UserDao {

    @Query("SELECT * FROM Users")
    List<User> getAllUsers();

    @Query("SELECT * FROM Users WHERE userid LIKE :username LIMIT 1")
    User findByUserName(String username);

    @Query("SELECT * FROM Users WHERE userid = :uid")
    User findByUserId(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(User... users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<User> users);

    @Update()
    int update(User user);

    @Update()
    int updateAll(User... user);

    @Update()
    int updateAll(List<User> user);

    @Delete
    int delete(User user);

    @Delete
    int deleteAll(List<User> users);

    @Delete
    int deleteAll(User... users);
}
