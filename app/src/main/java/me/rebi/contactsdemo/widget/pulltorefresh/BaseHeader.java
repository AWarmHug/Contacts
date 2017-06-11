package me.rebi.contactsdemo.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by warm on 17/6/11.
 */

public abstract class BaseHeader extends RelativeLayout implements PullToRefreshLayout.OnPullStateChange{
    private static final String TAG = "BaseHeader";
    public BaseHeader(Context context) {
        this(context, null);
    }

    public BaseHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setVisibility(VISIBLE);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility==VISIBLE){
            ((PullToRefreshLayout)getParent()).setOnPullStateChange(this);
        }
    }

    @Override
    public void pullState(int state) {
        changeState(state);

    }

    @Override
    public void pulling(float y) {
        changePull(y);

    }


    protected abstract void changeState(int state);

    protected abstract void changePull(float y);

    
}
