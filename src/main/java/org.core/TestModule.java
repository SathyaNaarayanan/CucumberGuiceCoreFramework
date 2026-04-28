package org.core;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.core.BaseDriver;

public class TestModule extends AbstractModule {

    protected void configure(){
        bind(BaseDriver.class).in(Singleton.class);
    }

}