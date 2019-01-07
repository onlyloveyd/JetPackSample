package cn.onlyloveyd.jetpack;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.onlyloveyd.jetpack.adapter.ArticleAdapter;
import cn.onlyloveyd.jetpack.adapter.UserSpinnerAdapter;
import cn.onlyloveyd.jetpack.databinding.ActivityArticleBinding;
import cn.onlyloveyd.jetpack.db.JetpackDatabase;
import cn.onlyloveyd.jetpack.entity.Article;
import cn.onlyloveyd.jetpack.entity.User;
import cn.onlyloveyd.jetpack.event.ArticleEvent;
import cn.onlyloveyd.jetpack.ext.RandomStringGenerator;
import cn.onlyloveyd.jetpack.repository.ArticleRepository;
import cn.onlyloveyd.jetpack.repository.UserRepository;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ArticleActivity
 *
 * @author 45742
 * @date 2019/1/7 08:02
 */
public class ArticleActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    ActivityArticleBinding mBinding;
    UserRepository mUserRepository;
    ArticleRepository mArticleRepository;

    ArticleAdapter mArticleAdapter;
    UserSpinnerAdapter mUserSpinnerAdapter;


    public static void launch(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ArticleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article);

        mUserRepository = new UserRepository(JetpackDatabase.getInstance(getApplicationContext()));
        mArticleRepository = new ArticleRepository(
                JetpackDatabase.getInstance(getApplicationContext()));

        mUserSpinnerAdapter = new UserSpinnerAdapter();
        mArticleAdapter = new ArticleAdapter(this);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mBinding.rvArticle.setAdapter(mArticleAdapter);
        mBinding.rvArticle.setLayoutManager(llm);

        mBinding.srl.setOnRefreshListener(this);

        mBinding.btInsert.setOnClickListener(v -> insertArticle());

        mBinding.btQuery.setOnClickListener(v -> loadArticles());

        mBinding.btRandom.setOnClickListener(
                v -> mBinding.etArticleTitle.setText(RandomStringGenerator.getRandomString(10)));


        mBinding.spUser.setAdapter(mUserSpinnerAdapter);

        onRefresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        loadUsers();
        loadArticles();
    }

    private void loadUsers() {
        Observer<List<User>> observer = new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(List<User> results) {
                mUserSpinnerAdapter.setData(results);
                mUserSpinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        mUserRepository.loadUsers().subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(observer);
    }

    private void loadArticles() {
        Observer<List<Article>> observer = new Observer<List<Article>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mBinding.srl.setRefreshing(true);
            }

            @Override
            public void onNext(List<Article> results) {
                mBinding.srl.setRefreshing(false);
                if (results.isEmpty()) {
                    mBinding.tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    mBinding.tvEmpty.setVisibility(View.INVISIBLE);
                }
                mArticleAdapter.setData(results);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        mArticleRepository.laodArticles().subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(observer);
    }

    private void insertArticle() {
        Object selectedItem = mBinding.spUser.getSelectedItem();
        String userid = "";
        if (selectedItem instanceof User) {
            userid = ((User) selectedItem).getId();
        }
        if (!userid.isEmpty()) {
            Article article = new Article(mBinding.etArticleTitle.getText().toString(), userid);
            Observer<Long> observer = new Observer<Long>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(Long o) {
                    if (o != null) {
                        loadArticles();
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            mArticleRepository.insertArticle(article).subscribeOn(Schedulers.newThread()).observeOn(
                    AndroidSchedulers.mainThread()).subscribe(observer);
        }
    }


    private void deleteArticle(Article article) {
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer o) {
                loadArticles();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        mArticleRepository.deleteArticle(article).subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(observer);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onArticleEvent(ArticleEvent articleEvent) {
        Article article = articleEvent.getArticle();
        deleteArticle(article);
    }
}
