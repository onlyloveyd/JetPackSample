package cn.onlyloveyd.wanandroid.event;

import cn.onlyloveyd.wanandroid.entity.User;

/**
 * MessageEvent
 *
 * @author 45742
 * @date 2019/1/4 10:43
 */
public class UserEvent {
    private int index;
    private User mUser;

    public UserEvent(int index, User user) {
        this.index = index;
        this.mUser = user;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
