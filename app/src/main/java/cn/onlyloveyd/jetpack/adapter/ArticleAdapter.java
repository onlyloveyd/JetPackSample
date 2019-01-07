package cn.onlyloveyd.jetpack.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import cn.onlyloveyd.jetpack.databinding.RvItemArticleBinding;
import cn.onlyloveyd.jetpack.entity.Article;
import cn.onlyloveyd.jetpack.event.ArticleEvent;

/**
 * ArticleAdapter
 *
 * @author 45742
 * @date 2019/1/7 08:13
 */
public class ArticleAdapter extends
        RecyclerViewAdapter<Article, RecyclerViewAdapter.BindingViewHolder> {

    public ArticleAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RvItemArticleBinding binding = RvItemArticleBinding.inflate(mInflater, viewGroup, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder bindingViewHolder, int i) {
        RvItemArticleBinding binding = (RvItemArticleBinding) bindingViewHolder.mBinding;
        binding.setData(getItem(i));
        bindingViewHolder.itemView.setOnClickListener(v -> {
            EventBus.getDefault().post(new ArticleEvent(getItem(i)));
        });
    }
}
