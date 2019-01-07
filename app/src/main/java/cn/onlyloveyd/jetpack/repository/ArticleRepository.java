package cn.onlyloveyd.jetpack.repository;

import java.util.List;

import cn.onlyloveyd.jetpack.db.JetpackDatabase;
import cn.onlyloveyd.jetpack.entity.Article;
import io.reactivex.Observable;

/**
 * ArticleRepository
 *
 * @author 45742
 * @date 2019/1/7 08:08
 */
public class ArticleRepository {
    private JetpackDatabase mJetpackDatabase;

    public ArticleRepository(JetpackDatabase jetpackDatabase) {
        mJetpackDatabase = jetpackDatabase;
    }

    public Observable<List<Article>> laodArticles() {
        Observable<List<Article>> observable = Observable.create(
                e -> {
                    List<Article> results = mJetpackDatabase.articelDao().getAllArticles();
                    e.onNext(results);
                    e.onComplete();
                });
        return observable;
    }

    public Observable<Long> insertArticle(final Article article) {
        Observable observable = Observable.create(
                e -> {
                    Long rows = mJetpackDatabase.articelDao().insert(article);
                    e.onNext(rows);
                    e.onComplete();
                });
        return observable;
    }

    public Observable<Integer> deleteArticle(Article article) {
        Observable observable = Observable.create(e -> {
            int result = mJetpackDatabase.articelDao().delete(article);
            e.onNext(result);
            e.onComplete();
        });
        return observable;
    }
}
