package com.bingo.ui.dialog;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bingo.ui.R;
import com.bingo.ui.adapter.CommonAdapter;
import com.bingo.ui.adapter.CommonHolder;

import java.util.List;

/**
 * OperationDialog
 */
public class OperationDialog extends AbsSheetDialog<OperationDialog.Bean> {
    private boolean isChecked;

    public OperationDialog(Context context, String title, List<Bean> datas) {
        super(context, R.style.lib_pub_dialog_style, false, 0, 0, 0);
        this.title = title;
        this.datas = datas;
        initView(rootView);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.lib_pub_dialog_operation;
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        int adapterLayoutId = R.layout.lib_pub_adapter_dlg_bottom_ver;
        if (datas != null && datas.size() > 0) {
            int size = datas.size();
            for (int i = 0; i < size; i++) {
                if (datas.get(i).isChecked) {
                    adapterLayoutId = R.layout.lib_pub_adapter_dlg_bottom_ver_check;
                    isChecked = true;
                    break;
                }
            }
        }
        return new SheetAdapter(context, datas, adapterLayoutId);
    }

    @Override
    protected void initView(View rootView) {
        initRecyclerList(rootView, R.id.rv_list, LinearLayoutManager.VERTICAL);

        TextView tvCancle = (TextView) rootView.findViewById(R.id.tv_cancle);
        TextView tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        View vLineTop = rootView.findViewById(R.id.line_top);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
            vLineTop.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
            vLineTop.setVisibility(View.GONE);
        }

        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(-1, null);
            }
        });
    }

    public class SheetAdapter extends CommonAdapter<Bean> {
        SheetAdapter(Context context, List<Bean> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(final int position, CommonHolder holder, final Bean item) {
            holder.setText(R.id.tv_item, item.item);
            holder.setTextColor(R.id.tv_item, ContextCompat.getColor(mContext, item.color));
            holder.setViewVisibility(R.id.iv_check, item.isChecked ? View.VISIBLE : View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isChecked) {
                        item.isChecked = true;
                        notifyDataSetChanged();
                    }
                    onItemClick(position, item);
                }
            });
        }
    }

    public static class Bean {
        public String item;
        public int color;
        public boolean isChecked;

        public Bean(String item, int color, boolean isChecked) {
            this.item = item;
            this.color = color;
            this.isChecked = isChecked;
        }
    }
}
