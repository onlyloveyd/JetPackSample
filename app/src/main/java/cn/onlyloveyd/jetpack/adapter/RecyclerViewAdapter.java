package cn.onlyloveyd.jetpack.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

import cn.onlyloveyd.jetpack.ext.ItemType;

/**
 * RecyclerViewAdapter
 *
 * @author 45742
 * @date 2019/1/4 10:54
 */
public abstract class RecyclerViewAdapter<E, T extends RecyclerViewAdapter.BindingViewHolder>
        extends
        RecyclerView.Adapter<T> {
    protected Context mContext;
    protected LayoutInflater mInflater;
    private List<E> mDataList;

    public RecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public E getItem(int position) {
        return mDataList == null ? null : mDataList.get(position);
    }


    @Override
    public int getItemViewType(int position) {
        E item = getItem(position);
        if (item instanceof ItemType) {
            return ((ItemType) item).getType();
        }
        return super.getItemViewType(position);
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addData(List<E> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        int addCount = data.size();
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        int startPos = getItemCount();
        mDataList.addAll(data);
        notifyItemRangeInserted(startPos, addCount);
    }

    /**
     * 将数据添加到列表开始
     *
     * @param data 待添加数据
     */
    public void addDataToStart(E data) {
        if (data == null) {
            return;
        }
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.add(0, data);
        notifyItemInserted(0);
    }

    /**
     * 添加数据到列表最后
     *
     * @param data 待添加数据
     */
    public void addDataToTail(E data) {
        if (data == null) {
            return;
        }
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        int pos = getItemCount();
        mDataList.add(data);
        notifyItemInserted(pos);
    }

    /**
     * 情况列表数据
     */
    public void clearData() {
        if (mDataList != null && mDataList.size() > 0) {
            mDataList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 设置新的列表数据，将清空原来的列表数据
     *
     * @param data 新的列表数据
     */
    public void setData(List<E> data) {
        mDataList = data;
        notifyDataSetChanged();
    }


    /**
     * 获取列表数据
     *
     * @return 列表展示的数据列表
     */
    public List<E> getDataList() {
        return mDataList;
    }

    /**
     * 添加数据到列表的指定位置，注意位置大小，可能引起{@link IndexOutOfBoundsException}异常
     *
     * @param index 数据插入的位置
     * @param data  待添加数据
     */
    public void addData(int index, E data) {
        if (data == null) {
            return;
        }
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.add(index, data);
        notifyItemInserted(index);
    }

    /**
     * 移除列表指定位置的数据，注意位置大小，可能引起{@link IndexOutOfBoundsException}异常
     *
     * @param index 待移除数据的位置
     */
    public void removeData(int index) {
        if (mDataList == null) {
            return;
        }
        mDataList.remove(index);
        notifyItemRemoved(index);
    }

    public static class BindingViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding mBinding;

        public BindingViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
