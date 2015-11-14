package com.aking.speak;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.aking.speak.presenter.RipplePresenter;
import com.aking.speak.view.IRippleView;

public class MainActivity extends Activity implements View.OnClickListener, IRippleView {
    /**
     * Called when the activity is first created.
     */
    private RippleBackground rippleReal;
    private RippleView speakImage;
    private RipplePresenter presenter;
    private Button close,random;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        assignViews();
        presenter = new RipplePresenter(this);
        presenter.startBaAnimition();
    }

    private void assignViews() {
        rippleReal = (RippleBackground) findViewById(R.id.ripple_real);
        speakImage = (RippleView) findViewById(R.id.speak_image);
        close = (Button)findViewById(R.id.close);
        random = (Button)findViewById(R.id.random);
        close.setOnClickListener(this);
        random.setOnClickListener(this);
    }

    @Override
    public void startSpeak(int volum) {
//        模拟语音音量来模拟动画
        speakImage.volumChanged(volum);
    }

    @Override
    public void startRippleBackgroud() {
        rippleReal.startRippleAnimation();
    }

    @Override
    public void stopRippleBackgroud() {
        rippleReal.stopRippleAnimation();
    }

    @Override
    public void stopSpeak() {
        speakImage.stopAnimation();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.speak_image){
            presenter.startSpeakAnimition();
        }else if (v.getId() == R.id.close){
            presenter.stopAnimitions();
        }else if (v.getId() == R.id.random){
            presenter.startSpeakAnimition();
        }
    }
}
