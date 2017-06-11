package me.rebi.contactsdemo.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.rebi.contactsdemo.R;

/**
 * Created by warm on 17/6/9.
 */

public class Header extends BaseHeader  {
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
        inflate(context, R.layout.header, this);
        tv_state = (TextView) findViewById(R.id.tv_state);
        pb = (ProgressBar) findViewById(R.id.pb);
    }

    @Override
    protected void changeState(int state) {
        switch (state) {

            case State.PUSH_NO_OK:
                pb.setVisibility(GONE);
                tv_state.setText("下拉刷新");

                break;
            case State.PUSH_OK:
                tv_state.setText("松开刷新");
                break;
            case State.REFRESHING:
                pb.setVisibility(VISIBLE);
                tv_state.setText("正在加载...");

                break;
            case State.END:
                pb.setVisibility(GONE);
                tv_state.setText("下拉刷新");
                break;

        }
    }

    @Override
    protected void changePull(float y) {

    }




}
