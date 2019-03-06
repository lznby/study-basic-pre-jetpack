package com.lznby.jetpack.base;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.ListCompositeDisposable;

/**
 * @author Lznby
 */
public abstract class BaseFragment<VM extends BaseFragmentViewModel,V extends BaseActivity, E> extends Fragment {

    /**
     * this param user to unBinder butterKnife
     */
    protected Unbinder unbinder;

    /**
     * ViewModel
     */
    protected VM viewModel;

    /**
     * Manage rxJava lifecycle be use to release when activity onDestroy.
     */
    public ListCompositeDisposable list = new ListCompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        //configure viewModel
        viewModel = ViewModelProviders.of(this).get(getClazz());
        viewModel.setGetFragmentCallback(() -> this);
        viewModel.setGetActivityCallback(() ->(V) viewModel.fragment.getFragment().getActivity());
        final Observer<E> observable = this::bindView;
        viewModel.getLiveData().observe(this,observable);
        //Do something when fragment lifecycle was 'OnCreateView'.
        doOnCreateView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unBinder butterKnife when the fragment lifecycle was onDestroyView.
        unbinder.unbind();
        // Destroy rxJava thread when view was be destroy.
        list.dispose();
    }

    /**
     * this method was used to set fragment'layout like R.layout.xxx.
     *
     * @return
     */
    public abstract @LayoutRes
    int setLayout();

    /**
     * Collect rxJava'disposable.
     *
     * @param disposable from rxJava'Observable return result.
     */
    public void addDisposable(Disposable disposable) {
        list.add(disposable);
    }

    /**
     * do something when LiveData data was be changed
     *
     * @param entity default LiveData's data.
     */
    abstract protected void bindView(E entity);

    /**
     * This method be used to do something when fragment lifecycle was 'OnCreateView'.
     */
    protected abstract void doOnCreateView();

    /**
     * get T.class
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private Class<VM> getClazz() {
        return (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
