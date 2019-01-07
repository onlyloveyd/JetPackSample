package cn.onlyloveyd.jetpack.event;

import cn.onlyloveyd.jetpack.entity.Article;

/**
 * ArticleEvent
 *
 * @author 45742
 * @date 2019/1/7 08:19
 */
public class ArticleEvent {
    private Article article;

    public ArticleEvent(Article article) {
        this.article = article;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
