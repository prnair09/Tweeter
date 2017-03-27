package com.rakesh.tweeter;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by rparuthi on 3/24/2017.
 */

public class TweeterApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);

        //Enable chrome Dev tools
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this));

        //Enable command line interface
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(getApplicationContext()));

        //Use the InitializerBuuilder to generate an initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        //Initialize Stetho with intializer
        Stetho.initialize(initializer);

        //FlowManager.init(new FlowConfig.Builder(this).build());
        //FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

        TweeterApplication.context = this;
    }

    public static TweeterClient getRestClient() {
        return (TweeterClient) TweeterClient.getInstance(TweeterClient.class, TweeterApplication.context);
    }
}
