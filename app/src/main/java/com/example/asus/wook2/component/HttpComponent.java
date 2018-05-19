package com.example.asus.wook2.component;

import com.example.asus.wook2.module.HttpModule;
import com.example.asus.wook2.ui.shopping.MainActivity;

import dagger.Component;

/**
 * Created by asus on 2018/5/19.
 */
@Component(modules = HttpModule.class)
public interface HttpComponent {
    void inject(MainActivity mainActivity);
}
