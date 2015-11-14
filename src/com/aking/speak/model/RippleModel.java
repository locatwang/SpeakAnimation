package com.aking.speak.model;

import java.util.Random;

/**
 * Created by Aking on 2015/11/14.
 */
public class RippleModel implements IRippleModel {

    @Override
    public int getIntVolum() {
        Random random = new Random();
        int volum = random.nextInt(30);
        return volum;
    }
}
