package q.rorbin.verticaltablayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Px;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import q.rorbin.badgeview.Badge;
import q.rorbin.verticaltablayout.util.DisplayUtil;
import q.rorbin.verticaltablayout.util.GlideUtils;

/**
 * @author Irvin
 */
public class QTabView extends TabView {
    private static final int TITLE_ID = 0x000001;
    private static final int ICON_ID = 0x000002;
    private static final int CONTAINER_ID = 0x000003;

    private Context mContext;
    private LinearLayout mViewContainer;
    private TextView mTitle;
    private ImageView mIcon;
    private Badge mBadgeView;
    private TabIcon mTabIcon;
    private TabTitle mTabTitle;
    private TabBadge mTabBadge;
    private boolean mChecked;
    private Drawable mDefaultBackground;
    private int mSelectedBackground;


    public QTabView(Context context) {
        super(context);
        mContext = context;
        mTabIcon = new TabIcon.Builder().build();
        mTabTitle = new TabTitle.Builder().build();
        mTabBadge = new TabBadge.Builder().build();
        initView();
        int[] attrs;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            attrs = new int[]{android.R.attr.selectableItemBackgroundBorderless};
        } else {
            attrs = new int[]{android.R.attr.selectableItemBackground};
        }
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs);
        mDefaultBackground = a.getDrawable(0);

        a.recycle();
        setDefaultBackground();
    }

    private void initView() {
        setMinimumHeight(DisplayUtil.dp2px(mContext,25));
        if (mViewContainer == null) {
            mViewContainer = new LinearLayout(mContext);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            mViewContainer.setLayoutParams(params);
            mViewContainer.setId(CONTAINER_ID);
            mViewContainer.setGravity(Gravity.CENTER);
            addView(mViewContainer);
        }

        initIconGravity();
        initTitleView();
        initIconView();
        initBadge();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setPaddingRelative(@Px int start, @Px int top, @Px int end, @Px int bottom) {
        mTitle.setPaddingRelative(start, top, end, bottom);
    }

    @Override
    public void setPadding(@Px int left, @Px int top, @Px int right, @Px int bottom) {
        mTitle.setPadding(left, top, right, bottom);
    }

    private void initBadge() {
        mBadgeView = TabBadgeView.bindTab(this);
        if (mTabBadge.getBackgroundColor() != 0xFFE84E40) {
            mBadgeView.setBadgeBackgroundColor(mTabBadge.getBackgroundColor());
        }
        if (mTabBadge.getBadgeTextColor() != 0xFFFFFFFF) {
            mBadgeView.setBadgeTextColor(mTabBadge.getBadgeTextColor());
        }
        if (mTabBadge.getStrokeColor() != Color.TRANSPARENT || mTabBadge.getStrokeWidth() != 0) {
            mBadgeView.stroke(mTabBadge.getStrokeColor(), mTabBadge.getStrokeWidth(), true);
        }
        if (mTabBadge.getDrawableBackground() != null || mTabBadge.isDrawableBackgroundClip()) {
            mBadgeView.setBadgeBackground(mTabBadge.getDrawableBackground(), mTabBadge.isDrawableBackgroundClip());
        }
        if (mTabBadge.getBadgeTextSize() != 11) {
            mBadgeView.setBadgeTextSize(mTabBadge.getBadgeTextSize(), true);
        }
        if (mTabBadge.getBadgePadding() != 5) {
            mBadgeView.setBadgePadding(mTabBadge.getBadgePadding(), true);
        }
        if (mTabBadge.getBadgeNumber() != 0) {
            mBadgeView.setBadgeNumber(mTabBadge.getBadgeNumber());
        }
        if (mTabBadge.getBadgeText() != null) {
            mBadgeView.setBadgeText(mTabBadge.getBadgeText());
        }
        if (mTabBadge.getBadgeGravity() != (Gravity.END | Gravity.TOP)) {
            mBadgeView.setBadgeGravity(mTabBadge.getBadgeGravity());
        }
        if (mTabBadge.getGravityOffsetX() != 5 || mTabBadge.getGravityOffsetY() != 5) {
            mBadgeView.setGravityOffset(mTabBadge.getGravityOffsetX(), mTabBadge.getGravityOffsetY(), true);
        }
        if (mTabBadge.isExactMode()) {
            mBadgeView.setExactMode(mTabBadge.isExactMode());
        }
        if (!mTabBadge.isShowShadow()) {
            mBadgeView.setShowShadow(mTabBadge.isShowShadow());
        }
        if (mTabBadge.getOnDragStateChangedListener() != null) {
            mBadgeView.setOnDragStateChangedListener(mTabBadge.getOnDragStateChangedListener());
        }
    }

    private void initIconGravity() {
        switch (mTabIcon.getIconGravity()) {
            case Gravity.START:
                mViewContainer.setOrientation(LinearLayout.HORIZONTAL);
                if (mIcon == null) {
                    mIcon = new ImageView(mContext);
                    mIcon.setId(ICON_ID);
                    mViewContainer.addView(mIcon);
                }
                if (mTitle == null) {
                    mTitle = new TextView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    mTitle.setLayoutParams(params);
                    mViewContainer.addView(mTitle);
                }
                break;
            case Gravity.TOP:
                mViewContainer.setOrientation(LinearLayout.VERTICAL);
                if (mIcon == null) {
                    mIcon = new ImageView(mContext);
                    mIcon.setId(ICON_ID);
                    mViewContainer.addView(mIcon);
                }
                if (mTitle == null) {
                    mTitle = new TextView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    mTitle.setLayoutParams(params);
                    mViewContainer.addView(mTitle);
                }
                break;
            case Gravity.END:
                mViewContainer.setOrientation(LinearLayout.HORIZONTAL);
                if (mTitle == null) {
                    mTitle = new TextView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                    mTitle.setLayoutParams(params);
                    mViewContainer.addView(mTitle);
                }
                if (mIcon == null) {
                    mIcon = new ImageView(mContext);
                    mIcon.setId(ICON_ID);
                    mViewContainer.addView(mIcon);
                }
                break;
            case Gravity.BOTTOM:
                if (mTitle == null) {
                    mTitle = new TextView(mContext);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                    mTitle.setLayoutParams(params);
                    mViewContainer.addView(mTitle);
                }
                mViewContainer.setOrientation(LinearLayout.VERTICAL);
                if (mIcon == null) {
                    mIcon = new ImageView(mContext);
                    mIcon.setId(ICON_ID);
                    mViewContainer.addView(mIcon);
                }
                break;
            default:
                break;
        }
    }

    private void initTitleView() {
        mTitle.setId(TITLE_ID);
        mTitle.setTextColor(isChecked() ? mTabTitle.getColorSelected() : mTabTitle.getColorNormal());
        mTitle.setTextSize(mTabTitle.getTitleTextSize());
        mTitle.setText(mTabTitle.getContent());
        mTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        mTitle.setEllipsize(TextUtils.TruncateAt.END);
        mTitle.setSingleLine();
        refreshDrawablePadding();
    }

    private void initIconView() {
        mIcon.setId(ICON_ID);
        int iconResid = mChecked ? mTabIcon.getSelectedIcon() : mTabIcon.getNormalIcon();
        String iconUrl = mChecked ? mTabIcon.getSelectedIconUrl() : mTabIcon.getNormalIconUrl();
        Drawable drawable;
        if (iconResid != 0) {
            int r, b;
            drawable = mContext.getResources().getDrawable(iconResid);
            if (mChecked) {
                r = mTabIcon.getIconSelectWidth() != -1 ? mTabIcon.getIconSelectWidth() : drawable.getIntrinsicWidth();
                b = mTabIcon.getIconSelectHeight() != -1 ? mTabIcon.getIconSelectHeight() : drawable.getIntrinsicHeight();
                mIcon.setBackgroundResource(mTabIcon.getIconBackground());
            } else {
                r = mTabIcon.getIconWidth() != -1 ? mTabIcon.getIconWidth() : drawable.getIntrinsicWidth();
                b = mTabIcon.getIconHeight() != -1 ? mTabIcon.getIconHeight() : drawable.getIntrinsicHeight();
                mIcon.setBackgroundResource(0);
            }
            mIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            drawable.setBounds(0, 0, r, b);
            mIcon.setImageDrawable(drawable);

            //设置大小
            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(r == 0 ? LayoutParams.WRAP_CONTENT : r,
                    b == 0 ? LayoutParams.WRAP_CONTENT : b);
            if (mTabIcon.getIconGravity() == Gravity.START || mTabIcon.getIconGravity() == Gravity.END) {
                ivParams.gravity = Gravity.CENTER_VERTICAL;
            } else if (mTabIcon.getIconGravity() == Gravity.TOP || mTabIcon.getIconGravity() == Gravity.BOTTOM) {
                ivParams.gravity = Gravity.CENTER_HORIZONTAL;
            }
            mIcon.setLayoutParams(ivParams);
        } else if (!TextUtils.isEmpty(iconUrl)) {
            GlideUtils.getImageDrawable(mContext, iconUrl, new GlideUtils.DrawableCallback() {
                @Override
                public void onGetDrawableDone(Drawable drawable) {
                    int r, b;
                    if (mChecked) {
                        r = mTabIcon.getIconSelectWidth() != -1 ? mTabIcon.getIconSelectWidth() : drawable.getIntrinsicWidth();
                        b = mTabIcon.getIconSelectHeight() != -1 ? mTabIcon.getIconSelectHeight() : drawable.getIntrinsicHeight();
                        mIcon.setBackgroundResource(mTabIcon.getIconBackground());
                    } else {
                        r = mTabIcon.getIconWidth() != -1 ? mTabIcon.getIconWidth() : drawable.getIntrinsicWidth();
                        b = mTabIcon.getIconHeight() != -1 ? mTabIcon.getIconHeight() : drawable.getIntrinsicHeight();
                        mIcon.setBackgroundResource(0);
                    }
                    mIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    drawable.setBounds(0, 0, r, b);
                    mIcon.setImageDrawable(drawable);

                    //设置大小
                    LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(r == 0 ? LayoutParams.WRAP_CONTENT : r,
                            b == 0 ? LayoutParams.WRAP_CONTENT : b);
                    if (mTabIcon.getIconGravity() == Gravity.START || mTabIcon.getIconGravity() == Gravity.END) {
                        ivParams.gravity = Gravity.CENTER_VERTICAL;
                    } else if (mTabIcon.getIconGravity() == Gravity.TOP || mTabIcon.getIconGravity() == Gravity.BOTTOM) {
                        ivParams.gravity = Gravity.CENTER_HORIZONTAL;
                    }
                    mIcon.setLayoutParams(ivParams);
                }
            });
        }
        refreshDrawablePadding();
    }

    private void refreshDrawablePadding() {
        int iconResid = mChecked ? mTabIcon.getSelectedIcon() : mTabIcon.getNormalIcon();
        String iconUrl = mChecked ? mTabIcon.getSelectedIconUrl() : mTabIcon.getNormalIconUrl();
        if (iconResid != 0 || !TextUtils.isEmpty(iconUrl)) {
            LinearLayout.LayoutParams ivParams = (LinearLayout.LayoutParams) mIcon.getLayoutParams();
            switch (mTabIcon.getIconGravity()) {
                case Gravity.START:
                    ivParams.setMarginEnd(mTabIcon.getMargin());
                    break;
                case Gravity.TOP:
                    ivParams.setMargins(0, 0, 0, mTabIcon.getMargin());
                    break;
                case Gravity.END:
                    ivParams.setMarginStart(mTabIcon.getMargin());
                    break;
                case Gravity.BOTTOM:
                    ivParams.setMargins(0, mTabIcon.getMargin(), 0, 0);
                    break;
                default:
                    break;
            }
            mIcon.setLayoutParams(ivParams);
        }
    }

    @Override
    public QTabView setBadge(TabBadge badge) {
        if (badge != null) {
            mTabBadge = badge;
        }
        initBadge();
        return this;
    }

    @Override
    public QTabView setIcon(TabIcon icon) {
        if (icon != null) {
            mTabIcon = icon;
        }
        initIconGravity();
        initIconView();
        return this;
    }

    @Override
    public QTabView setTitle(TabTitle title) {
        if (title != null) {
            mTabTitle = title;
        }
        initTitleView();
        return this;
    }

    /**
     * @param resId The Drawable res to use as the background, if less than 0 will to remove the
     *              background
     */
    @Override
    public QTabView setBackground(int resId) {
        if (resId == 0) {
            setDefaultBackground();
        } else if (resId <= 0) {
            setBackground(null);
        } else {
            super.setBackgroundResource(resId);
        }
        return this;
    }

    public QTabView setSelectedBackground(int resId) {
        if (resId <= 0 ) {
            setDefaultBackground();
        } else {
            mSelectedBackground = resId;
            super.setBackgroundResource(resId);
        }
        return this;
    }

    @Override
    public TabBadge getBadge() {
        return mTabBadge;
    }

    @Override
    public TabIcon getIcon() {
        return mTabIcon;
    }

    @Override
    public TabTitle getTitle() {
        return mTabTitle;
    }

    @Override
    @Deprecated
    public ImageView getIconView() {
        return null;
    }

    @Override
    public TextView getTitleView() {
        return mTitle;
    }

    @Override
    public Badge getBadgeView() {
        return mBadgeView;
    }

    @Override
    public void setBackground(Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            super.setBackground(background);
        } else {
            super.setBackgroundDrawable(background);
        }
    }

    @Override
    public void setBackgroundResource(int resid) {
        setBackground(resid);
    }

    private void setDefaultBackground() {
        if (getBackground() != mDefaultBackground) {
            setBackground(mDefaultBackground);
        }
    }

    private void setSelectedDefaultBackground() {
        if (mSelectedBackground > 0 && getBackground() != getResources().getDrawable(mSelectedBackground)) {
            setBackground(mSelectedBackground);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        setSelected(checked);
        refreshDrawableState();

        if (checked) {
            setSelectedDefaultBackground();
        } else {
            setDefaultBackground();
        }
        mTitle.setTextColor(checked ? mTabTitle.getColorSelected() : mTabTitle.getColorNormal());
        initIconView();
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}