package dgsw.stac.knowledgender

import android.app.Application
import android.util.Log
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapsSdkInitializedCallback
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KnowledgenderApp: OnMapsSdkInitializedCallback, Application() {
    override fun onCreate() {
        super.onCreate()
        MapsInitializer.initialize(applicationContext, MapsInitializer.Renderer.LATEST, this)
    }
    override fun onMapsSdkInitialized(renderer: MapsInitializer.Renderer) {
        when(renderer){
            MapsInitializer.Renderer.LATEST -> Log.d("Maps","이거슨 LATEST 입니다. !")
            MapsInitializer.Renderer.LEGACY -> Log.d("Maps","이거슨 LEGACY 입니다. !")
        }
        Log.d("Maps", renderer.toString())
    }
}