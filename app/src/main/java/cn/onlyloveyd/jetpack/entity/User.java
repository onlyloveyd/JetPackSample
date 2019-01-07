package cn.onlyloveyd.jetpack.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;


/**
 * User
 *
 * @author 45742
 * @date 2019/1/4 08:24
 */
@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @ColumnInfo(name = "userid")
    @NonNull
    private String mId;

    @ColumnInfo(name = "username")
    private String mUserName;

    @ColumnInfo(name = "last_update")
    private Date mDate;


    @Ignore
    public User(String userName) {
        mUserName = userName;
        mId=UUID.randomUUID().toString();
        mDate = new Date(System.currentTimeMillis());
    }

    public User(@NonNull String id, String userName, Date date) {
        mId = id;
        mUserName = userName;
        mDate = date;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
