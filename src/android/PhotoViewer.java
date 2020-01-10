package com.sjwiq200.plugin.multiphotoviewer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class echoes a string called from JavaScript.
 */
public class PhotoViewer extends CordovaPlugin {

    protected JSONArray args;
    public static CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("show")) {
            this.args = args;
            this.callbackContext = callbackContext;

            this.launchActivity();
            return true;
        }
        return false;
    }

    private void launchActivity() {
        if (this.args != null) {


            Intent intent = new Intent(this.cordova.getActivity(), com.sjwiq200.plugin.multiphotoviewer.MultiPhotoActivity.class);
            try {
                ArrayList listData = new ArrayList();
                JSONArray jArray = this.args.getJSONObject(0).getJSONArray("url");
                if (jArray != null) {
                    for (int i=0;i<jArray.length();i++){
                        listData.add(jArray.getString(i));
                    }
                }
                MultiPhotoActivity.source = listData;
                this.cordova.getActivity().startActivity(intent);
                this.callbackContext.success("success launchActivity");
            } catch (Exception e) {
                callbackContext.error("catch message :: "+ e.getMessage());
            }
        } else {
            callbackContext.error("launchActivity error and json args error");
        }
    }
}
