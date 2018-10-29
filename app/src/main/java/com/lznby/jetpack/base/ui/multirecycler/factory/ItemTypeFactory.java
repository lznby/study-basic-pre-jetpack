package com.lznby.jetpack.base.ui.multirecycler.factory;

import android.view.View;

import com.lznby.jetpack.base.ui.multirecycler.base.BaseViewHolder;


/**
 * @author Lznby
 * @time 2018/9/5 11:21
 * Class Note: 实现type工厂接口类
 */
public class ItemTypeFactory implements TypeFactory {

    //T is multi holder's layout.
    //K is multi holder's layout.
    //private static final T_LAYOUT = R.layout.T;
    //private static final K_LAYOUT = R.layout.K;


    //Return holder's layout.
    //@Override
    //public int type(T tType) {return T_LAYOUT;}
    //public int type(K kType) {return K_LAYOUT;}

    @Override
    public BaseViewHolder createViewHolder(int type, View itemView) {
        switch (type) {
            //TViewHolder & KViewHolder extends BaseViewHolder.
            //case T_LAYOUT:
            //  return new TViewHolder(itemView);
            //case K_Layout:
            //  return new KViewHolder(itemView)
            default:
                return null;
        }
    }
}
