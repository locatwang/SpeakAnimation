package com.aking.speak.view;

/**
 * Created by Aking on 2015/11/14.
 */
public interface IRippleView {
    void startSpeak(int volum);//声波波动动画大小开始 模拟

    void startRippleBackgroud();//背景循环动画开始

    void stopRippleBackgroud();

    void stopSpeak();//声波动画借宿

}
