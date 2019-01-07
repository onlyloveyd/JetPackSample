/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.onlyloveyd.jetpack.repository;

import java.util.List;

import cn.onlyloveyd.jetpack.db.JetpackDatabase;
import cn.onlyloveyd.jetpack.entity.User;
import io.reactivex.Observable;

/**
 * The repository is responsible of handling user data operations.
 * @author 45742
 */
public class UserRepository {
    private JetpackDatabase mJetpackDatabase;

    public UserRepository(JetpackDatabase jetpackDatabase) {
        mJetpackDatabase = jetpackDatabase;
    }

    public Observable<List<User>> loadUsers() {
        Observable<List<User>> observable = Observable.create(
                e -> {
                    List<User> results = mJetpackDatabase.userDao().getAllUsers();
                    e.onNext(results);
                    e.onComplete();
                });
        return observable;
    }

    public Observable<Long> insertUser(final User user) {
        Observable observable = Observable.create(
                e -> {
                    Long rows = mJetpackDatabase.userDao().insert(user);
                    e.onNext(rows);
                    e.onComplete();
                });
        return observable;
    }

    public Observable<Integer> deleteUser(User user) {
        Observable observable = Observable.create(e ->{
            int result = mJetpackDatabase.userDao().delete(user);
          e.onNext(result);
          e.onComplete();
        });
        return observable;
    }
}
