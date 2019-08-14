package q.rorbin.verticaltablayout.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * @author Irvin
 * @time 2018/12/28 0028
 */
public class GlideUtils {

    public static void setImage(Context context, String url, ImageView view, int size) {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.centerCrop();
        options.override(view.getWidth(), size);
        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(options)
                .into(view);
    }

    public static void setImage(Context context, String url, ImageView view, int width, int height) {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.centerInside();
        options.override(width, height);
        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(options)
                .into(view);
    }

    public static void getImageDrawable(Context context, String url, final DrawableCallback callback) {

        Glide.with(context).load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        callback.onGetDrawableDone(resource);
                    }
                });

    }

    public static void getImageDrawableWithSize(Context context, String url, int width, int height, final DrawableCallback callback) {
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        options.centerCrop();
        options.override(width, height);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        callback.onGetDrawableDone(resource);
                    }
                });

    }

    public interface DrawableCallback {
        /**
         * 获取drawable
         *
         * @param drawable
         */
        void onGetDrawableDone(Drawable drawable);
    }
}
