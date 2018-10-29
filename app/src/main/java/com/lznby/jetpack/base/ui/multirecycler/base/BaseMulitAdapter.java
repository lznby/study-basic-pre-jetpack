package com.lznby.jetpack.base.ui.multirecycler.base;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lznby.jetpack.base.ui.multirecycler.factory.TypeFactory;

import java.util.List;

/**
 * @author Lznby
 * @time 2018/10/29 13:09
 * Class Note:
 * MulitAdapter
 *
 */
public class BaseMulitAdapter<TF extends TypeFactory> extends RecyclerView.Adapter<BaseViewHolder> {

    private TF typefactory;

    private List<Visitable> mVisitables;

    public BaseMulitAdapter(List<Visitable> visitables, TF typefactory) {
        this.typefactory = typefactory;
        mVisitables = visitables;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        //setting onClick event

        //onClick event code end.
        return typefactory.createViewHolder(viewType, view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.bindViewData(mVisitables.get(position));
    }

    @Override
    public int getItemCount() {
        return mVisitables != null ? mVisitables.size() : 0;
    }
}