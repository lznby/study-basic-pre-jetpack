package com.lznby.jetpack.content.livedata;

/**
 * @author Lznby
 */
public interface LiveDataOverview {

    /**
     * LiveData
     *
     * 1.使用LiveData优点
     *
     * 2.使用LiveData对象
     * 2.1. 创建LiveData对象
     * 2.2. 观察LiveData对象
     * 2.3. 更新LiveData对象
     * 2.4. Room与LiveData对象结合使用
     *
     * 3.继承LiveData (Extends LiveData)
     *
     * 4.转换LiveData (Transform LiveData)
     * 4.1 创建新的转换 (Create new transformations)
     *
     * 5. 合并多个LiveData源 (Merge multiple LiveData sources)
     *
     * 6. 其他资源 (Additional resources)
     */


    /**
     * 1. 使用LiveData优点
     *
     * a) observable的数据持有类
     * b) 可感知生命周期,LiveData更新仅在处于活动生命周期中的Observable.
     * c) LiveData会及时更新到UI界面上
     * d) 无内存泄漏,观察者绑定Lifecycle对象并在相关生命周期结束后制动销毁清除
     * e) 观察者的生命周期处于非活动状态时,它不会接受任何LiveData事件
     * f) 观察(订阅)LiveData相关数据的UI组件,不会因生命周期的变化,而停止或恢复对LiveData数据的观察,LiveData会自动处理。
     * g) 数据始终保持最新,如由非活动状态再次变回活动状态时会接收最新数据。
     * f) 界面旋转等问题导致的Activity或Fragment重建,会及时接收到最新的可用数据
     * g) LiveData使用单例模式,可以拓展连接到Service类,用于在整个应用中共享LiveData数据。即LiveData与后台数据连接后,需要该资源的观察者只需要观察该LiveData对象即可
     *
     * 2. 使用LiveData对象
     *
     * 2.1 创建LiveData的步骤
     * 1) 创建一个用于保存特定类型数据的LiveData的实例,通常LiveData创建在ViewModel中.
     * 2) 创建一个Observer,并定义(重写)它的onChanged方法,该方法中处理LiveData数据变动时事务(如将控件显示内容如LiveData数据绑定,以及时更新UI),这一步一般在Fragment或Activity中进行.
     * 3) 步骤2中的Observer订阅LiveData通过LiveData的.observe方法进行订阅
     *
     * 2.2 观察LiveData对象
     * 1) 大多数情况下,组件一般在onCreate()方法开始时对LiveData进行观察(订阅).
     * 原因:
     * 1.避免在onResume()方法中重复调用.
     * 2.能够确保Activity或Fragment在处于STARTED状态是及时接收到LiveData的最新值.
     *
     * 2.3 更新LiveData对象
     * 1) LiveData没有公开用于用于更新数据的方法(即setXxx方法),如果需要将一个EditText中的值赋值给LiveData对象,则需要使用MutableLiveData类,并公开setValue(T)或postValue(T)方法.
     * 2) 在主线程上更新LiveData对象使用:setValue(T)方法.
     * 3) 在子线程上更新LiveData对象使用:postValue(T)方法.
     *
     * 2.4 LiveData结合Room使用
     * 参考另一个项目中的使用！
     *
     * 3. 继承LiveData
     * 1) 参考LieDataOverview中的方说明.
     *
     * 4.转换LiveData
     * 1) 通过Transformations.map()转换
     * 2) 通过Transformations.switchMap()转换
     */

}
