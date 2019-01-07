package cn.onlyloveyd.jetpack.event;

import cn.onlyloveyd.jetpack.entity.User;

/**
 * MessageEvent
 *
 * @author 45742
 * @date 2019/1/4 10:43
 */
public class UserEvent {
    private User mUser;

    public UserEvent(User user) {
        this.mUser = user;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

}
