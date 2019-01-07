package cn.onlyloveyd.jetpack.entity;

import static android.arch.persistence.room.ForeignKey.CASCADE;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Article
 *
 * @author 45742
 * @date 2019/1/4 11:21
 */
@Entity(tableName = "articels", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "userid",
        childColumns = "owner",
        onDelete = CASCADE), indices = {@Index("owner")})
public class Article {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "article_id")
    private String id;


    @ColumnInfo(name = "article_title")
    private String title;

    @ColumnInfo(name = "owner")
    private String userid;


    public Article(String title, String userid) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.userid = userid;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
