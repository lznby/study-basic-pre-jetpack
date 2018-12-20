package com.lznby.jetpack.content.design.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lznby.jetpack.R;
import com.lznby.jetpack.utils.DensityUtil;

/**
 * @author Lznby
 * @time 2018/11/11 15:52
 * Class Note:
 */
public class TitleEditText extends LinearLayout {

    private LinearLayout llLayout;
    private TextView tvLeft;
    private EditText etContent;
    private ImageView ivRight;

    public TitleEditText(Context context) {
        super(context);
        initView(context, null);
    }

    public TitleEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public TitleEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public TitleEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    public void initView(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.title_text_view, this, true);
        llLayout = findViewById(R.id.ll_layout);
        tvLeft = findViewById(R.id.itv_left);
        etContent = findViewById(R.id.et_content);
        ivRight = findViewById(R.id.itv_right);

        //获取TitleEditText在布局文件中的属性
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TitleEditText);

        if (attributes != null) {

            //标题文字说明
            String title = attributes.getString(R.styleable.TitleEditText_titleText);
            if (!TextUtils.isEmpty(title)) {
                tvLeft.setText(title);
            }

            //设置内容文字
            String content = attributes.getString(R.styleable.TitleEditText_contentText);
            if (!TextUtils.isEmpty(content)) {
                etContent.setText(content);
            }

            //设置文字大小
            float textSize = attributes.getDimensionPixelOffset(R.styleable.TitleEditText_textSize, DensityUtil.sp2px(context, 15));
            tvLeft.setTextSize(DensityUtil.px2sp(context, textSize));
            etContent.setTextSize(DensityUtil.px2sp(context, textSize));

            //设置标题字体颜色
            int leftTitleTextColor = attributes.getColor(R.styleable.TitleEditText_leftTitleTextColor,-1);
            if (leftTitleTextColor != -1) {
                tvLeft.setTextColor(leftTitleTextColor);
            }

            //设置内容字体颜色
            int contentTextColor = attributes.getColor(R.styleable.TitleEditText_contentTextColor,-1);
            if (contentTextColor != -1) {
                etContent.setTextColor(contentTextColor);
            }

            //设置EditText(content)是否可编辑
            boolean enable = attributes.getBoolean(R.styleable.TitleEditText_enable, false);
            etContent.setEnabled(enable);

            //设置资源图片
            int resourceId = attributes.getResourceId(R.styleable.TitleEditText_imageSrc, -1);
            if (resourceId != -1) {
                ivRight.setImageResource(resourceId);
            }

            //设置layoutMargin
            int[] layoutMargin = {
                    attributes.getDimensionPixelOffset(R.styleable.TitleEditText_layoutMarginLeft, 0),
                    attributes.getDimensionPixelOffset(R.styleable.TitleEditText_layoutMarginTop, 0),
                    attributes.getDimensionPixelOffset(R.styleable.TitleEditText_layoutMarginRight, 0),
                    attributes.getDimensionPixelOffset(R.styleable.TitleEditText_layoutMarginButton, 0),
            };
            llLayout.setPadding(
                    layoutMargin[0],
                    layoutMargin[1],
                    layoutMargin[2],
                    layoutMargin[3]
            );

            //设置contentMargin
            int[] contentMargin = {
                    attributes.getDimensionPixelOffset(R.styleable.TitleEditText_contentMarginLeft, 0),
                    0,
                    attributes.getDimensionPixelOffset(R.styleable.TitleEditText_contentMarginRight, 0),
                    0,
            };
            etContent.setPadding(
                    contentMargin[0],
                    contentMargin[1],
                    contentMargin[2],
                    contentMargin[3]
            );

            //设置imageSize
            int[] imageSize = {
                    attributes.getDimensionPixelOffset(R.styleable.TitleEditText_imageWidth, 0),
                    attributes.getDimensionPixelOffset(R.styleable.TitleEditText_imageHeight, 0),
            };
            ivRight.getLayoutParams().width = imageSize[0];
            ivRight.getLayoutParams().height = imageSize[1];
            //获取资源后要及时回收
            attributes.recycle();

        }
    }

    public void setTitle(String title) {
        tvLeft.setText(title);
    }

    public void setContent(String content) {
        etContent.setText(content);
    }

    public String getContent() {
        return etContent.getText().toString().trim();
    }

    public void setImageSrc(@DrawableRes int res) {
        ivRight.setImageResource(res);
    }

    public void setOnclickListener(OnClickListener clickListener) {
        llLayout.setOnClickListener(clickListener);
    }

    public void setClickEnable(boolean enable) {
        llLayout.setClickable(enable);
    }

    public void setBackground(@ColorRes int color) {
        llLayout.setBackgroundResource(color);
    }

    public void setEnableEdit(boolean isEdit) {
        etContent.setEnabled(isEdit);
    }

}
