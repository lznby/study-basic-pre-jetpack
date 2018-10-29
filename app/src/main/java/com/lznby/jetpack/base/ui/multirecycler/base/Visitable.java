package com.lznby.jetpack.base.ui.multirecycler.base;


import com.lznby.jetpack.base.ui.multirecycler.factory.TypeFactory;

/**
 * @author Lznby
 * @time 2018/9/5 10:19
 * Class Note: 干部任免方案中 RecyclerView中的item bean 统一实现该接口
 */
public interface Visitable {
    /**
     * 所有 item bean 统一实现类型抽象方法
     * @param typeFactory
     * @return
     */
    int type(TypeFactory typeFactory);
}
