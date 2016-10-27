package com.example.andres_desarrollo2.psfull.Control;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by TSI on 27/05/2016.
 */
public class Session {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    Context ctx;

    public Session(Context ctx){
        this.ctx = ctx;
        prefs =ctx.getSharedPreferences(Constantes.REFERENCIA_USER,Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean loggedin){
        editor.putBoolean("loggerInMode", loggedin);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggerInMode", false);
    }
}
