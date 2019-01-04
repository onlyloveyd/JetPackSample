package cn.onlyloveyd.wanandroid;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.idescout.sql.SqlScoutServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.onlyloveyd.wanandroid.adapter.UserAdapter;
import cn.onlyloveyd.wanandroid.databinding.ActivityMainBinding;
import cn.onlyloveyd.wanandroid.db.UsersDatabase;
import cn.onlyloveyd.wanandroid.entity.User;
import cn.onlyloveyd.wanandroid.event.UserEvent;
import cn.onlyloveyd.wanandroid.ext.RandomStringGenerator;
import cn.onlyloveyd.wanandroid.repository.UserRepository;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements
        SwipeRefreshLayout.OnRefreshListener {

    ActivityMainBinding mBinding;
    UserRepository mUserRepository;

    UserAdapter mUserAdapter;

    private SqlScoutServer sqlScoutServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        sqlScoutServer = SqlScoutServer.create(this, getPackageName());
        mUserRepository = new UserRepository(UsersDatabase.getInstance(getApplicationContext()));
        mUserAdapter = new UserAdapter(this);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mBinding.rvUser.setAdapter(mUserAdapter);
        mBinding.rvUser.setLayoutManager(llm);

        mBinding.srl.setOnRefreshListener(this);

        mBinding.btInsert.setOnClickListener(v -> insertUser());

        mBinding.btQuery.setOnClickListener(v -> loadUsers());

        mBinding.btRandom.setOnClickListener(v -> {
            mBinding.etUsername.setText(RandomStringGenerator.getRandomString(10));
        });

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
    protected void onResume() {
        super.onResume();
        sqlScoutServer.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sqlScoutServer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqlScoutServer.destroy();
    }

    @Override
    public void onRefresh() {
        loadUsers();
    }

    private void loadUsers() {
        Observer<List<User>> observer = new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mBinding.srl.setRefreshing(true);
            }

            @Override
            public void onNext(List<User> results) {
                mBinding.srl.setRefreshing(false);
                if (results.isEmpty()) {
                    mBinding.tvEmpty.setVisibility(View.VISIBLE);
                } else {
                    mBinding.tvEmpty.setVisibility(View.INVISIBLE);
                }
                mUserAdapter.setData(results);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        mUserRepository.LoadUsers().subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(observer);
    }

    private void insertUser() {
        User user = new User(mBinding.etUsername.getText().toString());
        Observer<Long> observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Long o) {
                if (o != null) {
                    loadUsers();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        mUserRepository.insertUser(user).subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(observer);
    }


    private void deleteUser(User user) {
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Integer o) {
                System.err.println("yidong -- o = " + o);
                loadUsers();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        mUserRepository.deleteUser(user).subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(observer);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(UserEvent userEvent) {
        int position = userEvent.getIndex();
        User user = userEvent.getUser();
        deleteUser(user);
    }
}
