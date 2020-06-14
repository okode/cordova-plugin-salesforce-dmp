package com.okode.dmp;

import android.util.Log;
import android.os.Bundle;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.krux.androidsdk.aggregator.KruxConsentCallback;
import com.krux.androidsdk.aggregator.KruxEventAggregator;
import com.krux.androidsdk.aggregator.KruxSegments;

public class DMPPlugin extends CordovaPlugin {

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        if ("initialize".equals(action)) {
            KruxSegments kruxSegmentsCallback = new KruxSegments() {
                @Override
                public void getSegments(final String segments) {
                    Log.d("KRUX", "Krux formatted segments: " + segments);
                }
            };
            KruxConsentCallback consentCallback = new KruxConsentCallbackImpl();
            JSONObject argsObject = args.getJSONObject(0);
            String apiKey = (String) argsObject.get("apiKey");
            KruxEventAggregator.initialize(this.cordova.getContext(), apiKey, kruxSegmentsCallback, true, consentCallback);
            // TODO
            // this.cordova.getActivity().runOnUiThread(() -> callbackContext.success("Test OK"));
            Log.d("KRUX", "Initialize OK: " + apiKey);
            callbackContext.success("Initialize OK " + apiKey);
            return true;
        }

        if ("sendRequests".equals(action)) {
            Bundle consentSetAttributes = getConsentAttributes();
            Bundle consentGetAttributes = getIdParameters();
            Bundle idAttributes = getIdParameters();
            addPolicyRegimeParameter(consentGetAttributes);
            addPolicyRegimeParameter(consentSetAttributes);

            KruxEventAggregator.consentGetRequest(consentGetAttributes);
            KruxEventAggregator.consentSetRequest(consentSetAttributes);
            KruxEventAggregator.consumerRemoveRequest(idAttributes);
            KruxEventAggregator.consumerPortabilityRequest(idAttributes);
            // TODO
            // this.cordova.getActivity().runOnUiThread(() -> callbackContext.success("Test OK"));
            Log.d("KRUX", "Send requests OK");
            callbackContext.success("Send requests OK");
            return true;
        }

        if ("trackPage".equals(action)) {
            Bundle pageAttrs = getPageAttributes(args);
            KruxEventAggregator.trackPageView("Main", pageAttrs, getUserAttributes(args));
            // TODO
            // this.cordova.getActivity().runOnUiThread(() -> callbackContext.success("Test OK"));
            Log.d("KRUX", "Page View OK: " + pageAttrs.getString("path"));
            callbackContext.success("Tracking page view OK " + pageAttrs.getString("path"));
            return true;
        }

        if ("fireEvent".equals(action)) {
            Bundle eventAttrs = getEventAttributes(args);
            KruxEventAggregator.fireEvent("Main", eventAttrs);
            // TODO
            //this.cordova.getActivity().runOnUiThread(() -> callbackContext.success("Test OK"));
            Log.d("KRUX", "Fire Event OK: " + eventAttrs.getString("action"));
            callbackContext.success("Tracking fire event OK " + eventAttrs.getString("action"));
            return true;
        }

        Log.d("Cordova Locale Plugin", "Action not implemeted: " + action + ", Args: " + args);
        callbackContext.error("Test error");
        return false;
    }

    public Bundle getPageAttributes(JSONArray args) throws JSONException {
        JSONObject argsObject = args.getJSONObject(0);
        Bundle pageAttributes = new Bundle();
        pageAttributes.putString("type", (String) argsObject.get("pageType"));
        pageAttributes.putString("path", (String) argsObject.get("path"));
        return pageAttributes;
    }

    public Bundle getUserAttributes(JSONArray args) throws JSONException {
        JSONObject argsObject = args.getJSONObject(0);
        Bundle userAttributes = new Bundle();
        userAttributes.putBoolean("logged", (Boolean) argsObject.get("logged"));
        userAttributes.putString("email", (String) argsObject.get("email"));
        return userAttributes;
    }

    public Bundle getEventAttributes(JSONArray args) throws JSONException {
        JSONObject argsObject = args.getJSONObject(0);
        Bundle eventAttributes = new Bundle();
        eventAttributes.putString("action", (String) argsObject.get("action"));
        eventAttributes.putString("category", (String) argsObject.get("category"));
        eventAttributes.putString("label", (String) argsObject.get("label"));
        return eventAttributes;
    }

    private void addPolicyRegimeParameter(Bundle attributes) {
        attributes.putString("pr", "gdpr");
    }

    private Bundle getIdParameters(){
        Bundle attributeBundle = new Bundle();
        attributeBundle.putString("idv","00000000T");
        attributeBundle.putString("dt","aaid");
        attributeBundle.putString("idt","device");
        return attributeBundle;
    }

    private Bundle getConsentAttributes() {
        Bundle attributeBundle = new Bundle();
        attributeBundle.putInt("dc", 1);
        attributeBundle.putInt("cd", 1);
        attributeBundle.putInt("tg", 1);
        attributeBundle.putInt("al", 1);
        attributeBundle.putInt("sh", 0);
        attributeBundle.putInt("re", 0);
        return attributeBundle;
    }

}

class KruxConsentCallbackImpl implements KruxConsentCallback {
    private final static String TAG = KruxConsentCallbackImpl.class.getSimpleName();

    @Override
    public void handleConsentGetResponse(String consentGetResponse) {
        Log.i(TAG,"Consent get response: "+consentGetResponse);
    }

    @Override
    public void handleConsentGetError(String consentGetError) {
        Log.i(TAG,"Consent get error: "+consentGetError);
    }

    @Override
    public void handleConsentSetResponse(String consentSetResponse) {
        Log.i(TAG,"Consent set response: "+consentSetResponse);
    }

    @Override
    public void handleConsentSetError(String consentSetError) {
        Log.i(TAG,"Consent set error: "+consentSetError);
    }

    @Override
    public void handleConsumerRemoveResponse(String consumerRemoveResponse) {
        Log.i(TAG,"Consumer remove response: "+consumerRemoveResponse);
    }

    @Override
    public void handleConsumerRemoveError(String consumerRemoveError) {
        Log.i(TAG,"Consumer remove error: "+consumerRemoveError);
    }

    @Override
    public void handleConsumerPortabilityResponse(String consumerPortabilityResponse) {
        Log.i(TAG,"Consumer portability response: "+consumerPortabilityResponse);
    }

    @Override
    public void handleConsumerPortabilityError(String consumerPortabilityError) {
        Log.i(TAG,"Consumer portability error: "+consumerPortabilityError);
    }
}
