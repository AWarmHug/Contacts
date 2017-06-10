package me.rebi.contactsdemo.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.rebi.contactsdemo.R;

/**
 * Created by warm on 17/6/9.
 */

public class Header extends RelativeLayout implements PullToRefreshLayout.OnPullStateChange {
    private static final String TAG = "Header";
    private TextView tv_state;
    private ProgressBar pb;
    private PullToRefreshLayout pullToRefreshLayout;

    public Header(Context context) {
        this(context, null);
    }

    public Header(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Header(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.header,this);
        pullToRefreshLayout= (PullToRefreshLayout) getParent();
        pullToRefreshLayout.setOnPullStateChange(this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_state= (TextView) findViewById(R.id.tv_state);
        pb= (ProgressBar) findViewById(R.id.pb);
    }

    @Override
    public void pullState(int state) {
        switch (state){
            case State.TOUCH:

                break;
            case State.PUSHING:
                pb.setVisibility(GONE);
                tv_state.setText("下拉刷新");

                break;
            case State.LOOSEN:
                pb.setVisibility(VISIBLE);
                tv_state.setText("正在加载...");

                pb.setVisibility(GONE);
                tv_state.setText("加载成功！");
                break;
        }

    }

    @Override
    public void pulling(float y) {
        Log.d(TAG, "pulling: "+y);

    }
}
