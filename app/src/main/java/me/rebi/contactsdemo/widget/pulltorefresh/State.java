package me.rebi.contactsdemo.widget.pulltorefresh;

/**
 * Created by warm on 17/6/9.
 */

public class State {

    /**
     * 触摸时
     */
    public static final int TOUCH=-1;
    /**
     * 拖动时
     */
    public static final int PUSHING=0;



    /**
     * 松开时
     */
    public static final int LOOSEN=1;





    /**
     * 回弹时
     */
    public static final int SPRING_BACK=2;

    /**
     * 所有结束
     */
    public static final int OVER=3;

    /**
     * 拉倒可以松开时
     */
    public static final int PUSH_OK=4;



}
