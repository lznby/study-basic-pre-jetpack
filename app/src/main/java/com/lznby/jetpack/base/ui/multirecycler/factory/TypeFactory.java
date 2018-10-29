package com.lznby.jetpack.base.ui.multirecycler.factory;

import android.view.View;

import com.lznby.jetpack.base.ui.multirecycler.base.BaseViewHolder;


/**
 * @author Lznby
 * @time 2018/9/5 10:24
 * Class Note:bean type 工厂类型接口
 */
public interface TypeFactory {

    /**
     * T implements Visitable.
     * T is multi holder's model.
     */
    //int type (T tModel);

    //int type (K kModel);

    //...

    /**
     * 根据type创建对应的viewHolder
     * @param type
     * @param itemView
     * @return
     */
    BaseViewHolder createViewHolder(int type, View itemView);
}
