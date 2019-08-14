package q.rorbin.verticaltablayoutdemo;

/**
 * Created by Irvin on 2016/8/3.
 */
public class MenuBean {
    public int mSelectIcon;
    public int mNormalIcon;
    public String mTitle;

    public MenuBean(String title) {
        mTitle = title;
    }

    public MenuBean(int selectIcon, int normalIcon) {
        mSelectIcon = selectIcon;
        mNormalIcon = normalIcon;
    }

    public MenuBean(int mSelectIcon, int mNormalIcon, String mTitle) {
        this.mSelectIcon = mSelectIcon;
        this.mNormalIcon = mNormalIcon;
        this.mTitle = mTitle;
    }
}

