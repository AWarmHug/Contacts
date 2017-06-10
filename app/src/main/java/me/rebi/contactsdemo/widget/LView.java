package me.rebi.contactsdemo.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.rebi.contactsdemo.R;
import me.rebi.contactsdemo.adapter.RecyAdapter;
import me.rebi.contactsdemo.bean.Contact;
import me.rebi.contactsdemo.util.FirstLetterUtil;

/**
 * Created by warm on 17/6/8.
 */

public class LView extends FrameLayout {
    private static final String TAG = "LView";
    private RecyclerView rv;
    private RecyAdapter recyAdapter;
    private TextView showTv;
    private LinearLayoutManager linearLayoutManager;
    private QuickBar quickBar;
    private List<Contact> contentList;

    private int nowFirstItem = 0;
    private int suspensionTvHight;

    public LView(Context context) {
        this(context, null);
    }

    public LView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.lview, this);


    }

    public void setContactsList(List<Contact> contentList) {
        this.contentList = contentList;
        recyAdapter = new RecyAdapter(contentList);
        rv.setAdapter(recyAdapter);
        rv.setLayoutManager(linearLayoutManager);
        refreshSuspensionTv();
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RelativeLayout relativeLayout = (RelativeLayout) linearLayoutManager.findViewByPosition(nowFirstItem + 1);
                if (relativeLayout != null) {
                    if (relativeLayout.findViewById(R.id.tv_firstLetter).isShown()) {
                        if (relativeLayout.getTop() < suspensionTvHight) {
                            showTv.setTranslationY(-Math.abs(suspensionTvHight - relativeLayout.getTop()));
                        }
                    }
                }

                if (linearLayoutManager.findFirstVisibleItemPosition() != nowFirstItem) {
                    nowFirstItem = linearLayoutManager.findFirstVisibleItemPosition();
                    refreshSuspensionTv();
                }


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        Map<String, Integer> firstLetterMap = new LinkedHashMap<>();
        for (int i = 0; i < contentList.size(); i++) {
            //LinkedHashMap是可以按序的Map
            // 得到字母-位置，通过这个Map，在RecyclerView滑动时，根据字母，滑动到指定的位置上
            String firstLetter = FirstLetterUtil.getFirstLetter(contentList.get(i).getPinYin().substring(0, 1).toUpperCase());
            if (!firstLetterMap.containsKey(firstLetter)) {
                firstLetterMap.put(firstLetter, i);
            }
        }
        quickBar.setData(firstLetterMap);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        rv = (RecyclerView) this.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        showTv = (TextView) this.findViewById(R.id.suspensionTv);
        suspensionTvHight = showTv.getLayoutParams().height;

        quickBar = (QuickBar) this.findViewById(R.id.quickBar);
        quickBar.setOnTouchListener(new QuickBar.OnTouchListener() {
            @Override
            public void touch(int position) {
                linearLayoutManager.scrollToPositionWithOffset(position, 0);
            }
        });
    }

    private void refreshSuspensionTv() {
        showTv.setText(FirstLetterUtil.getFirstLetter(contentList.get(nowFirstItem).getPinYin().substring(0, 1)));
        showTv.setTranslationY(0);

    }

}
