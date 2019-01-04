package cn.onlyloveyd.wanandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import cn.onlyloveyd.wanandroid.databinding.RvItemUserBinding;
import cn.onlyloveyd.wanandroid.entity.User;
import cn.onlyloveyd.wanandroid.event.UserEvent;

/**
 * UserAdapter
 *
 * @author 45742
 * @date 2019/1/4 11:05
 */
public class UserAdapter extends RecyclerViewAdapter<User, RecyclerViewAdapter.BindingViewHolder> {

    public UserAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RvItemUserBinding binding = RvItemUserBinding.inflate(mInflater, viewGroup, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder bindingViewHolder, int i) {
        RvItemUserBinding binding = (RvItemUserBinding) bindingViewHolder.mBinding;
        binding.setData(getItem(i));
        bindingViewHolder.itemView.setOnLongClickListener(v -> {
            EventBus.getDefault().post(new UserEvent(i, getItem(i)));
            return false;
        });

    }
}
