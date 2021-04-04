package com.example.util.simpletimetracker.di

import com.example.util.simpletimetracker.data_local.di.DataLocalModule
import com.example.util.simpletimetracker.feature_notification.di.NotificationModule
import com.example.util.simpletimetracker.feature_widget.di.WidgetModule
import com.example.util.simpletimetracker.utils.BaseUiTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        DataLocalModule::class,
        WidgetModule::class,
        NotificationModule::class
    ]
)
interface TestAppComponent : AppComponent {

    fun inject(into: BaseUiTest)
}