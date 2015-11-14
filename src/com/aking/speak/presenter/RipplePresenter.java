package com.aking.speak.presenter;


import com.aking.speak.model.IRippleModel;
import com.aking.speak.model.RippleModel;
import com.aking.speak.view.IRippleView;

/**
 * Created by Aking on 2015/11/14.
 */
public class RipplePresenter {

    private IRippleView rippleView;
    private IRippleModel rippleModel;
    public RipplePresenter(IRippleView rippleView) {
        this.rippleView = rippleView;
        rippleModel = new RippleModel();
    }


    public void stopAnimitions(){
        rippleView.stopRippleBackgroud();
        rippleView.stopSpeak();
    }
    // 开启背景动画
    public void startBaAnimition(){
        rippleView.startRippleBackgroud();
    }

    // 声波动画的逻辑处理
    public void startSpeakAnimition(){
        rippleView.startSpeak(rippleModel.getIntVolum());
    }


}
