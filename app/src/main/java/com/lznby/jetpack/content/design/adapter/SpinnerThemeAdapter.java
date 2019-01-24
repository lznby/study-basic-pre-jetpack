package com.lznby.jetpack.content.design.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.content.design.entity.ThemeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建主题中Theme选择
 *
 * @author Lznby
 */
public class SpinnerThemeAdapter extends BaseAdapter {

    private List<ThemeEntity> themes;
    private Context context;

    public SpinnerThemeAdapter(List<ThemeEntity> themes, Context context) {
        this.themes = themes;
        this.context = context;
    }

    public SpinnerThemeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return getThemes().size();
    }

    @Override
    public Object getItem(int position) {
        return getThemes().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.item_spiner_theme,null);
        if (convertView!=null) {
            TextView tvTheme = convertView.findViewById(R.id.tv_theme);
            tvTheme.setText(getThemes().get(position).getThemeName());
        }

        return convertView;
    }

    public List<ThemeEntity> getThemes() {
        if (themes == null) {
            themes = new ArrayList<>();
        }
        return themes;
    }

    public void setThemes(List<ThemeEntity> themes) {
        this.themes = themes;
        notifyDataSetChanged();
    }
}
