package cn.onlyloveyd.jetpack.adapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import cn.onlyloveyd.jetpack.entity.User;

/**
 * UserSpinnerAdapter
 *
 * @author 45742
 * @date 2019/1/7 08:37
 */
public class UserSpinnerAdapter extends BaseAdapter {

    List<User> mData;

    public List<User> getData() {
        return mData;
    }

    public void setData(List<User> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(parent.getContext());
        text.setTextSize(18f);
        text.setPadding(0, 20, 0, 20);
        text.setGravity(Gravity.CENTER);
        text.setTag(mData.get(position).getId());
        text.setText(
                String.format(Locale.CHINESE, "name:%s, \nid:%s", mData.get(position).getUserName(),
                        mData.get(position).getId()));
        return text;
    }
}
