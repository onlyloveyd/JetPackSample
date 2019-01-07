package cn.onlyloveyd.jetpack.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import cn.onlyloveyd.jetpack.entity.Article;

/**
 * ArticleDao
 *
 * @author 45742
 * @date 2019/1/7 08:05
 */
@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articels")
    List<Article> getAllArticles();

    @Query("SELECT * FROM articels WHERE article_id = :uid")
    Article findByArticleId(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(Article... articles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Article> articles);

    @Update()
    int update(Article article);

    @Update()
    int updateAll(Article... articles);

    @Update()
    int updateAll(List<Article> articles);

    @Delete
    int delete(Article article);

    @Delete
    int deleteAll(List<Article> articles);

    @Delete
    int deleteAll(Article... articles);
}
