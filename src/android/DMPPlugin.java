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

import java.util.Iterator;

public class DMPPlugin extends CordovaPlugin {

    String mSegments = "";

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

           if ("initialize".equals(action)) {
            JSONObject argsObject = args.getJSONObject(0);
            String apiKey = (String) argsObject.get("apiKey");
            Log.d("KRUX", "Initializing Krux with key: " + apiKey);
            KruxSegments kruxSegmentsCallback = new KruxSegments() {
                @Override
                public void getSegments(final String segments) {
                    Log.d("KRUX", "Krux formatted segments: " + segments);
                    mSegments = segments;
                    callbackContext.success("Initialized Krux with key: " + apiKey);
                }
            };
            KruxConsentCallback consentCallback = new KruxConsentCallbackImpl();
            KruxEventAggregator.initialize(this.cordova.getContext(), apiKey, kruxSegmentsCallback, true, consentCallback);

            return true;
        }

        if ("sendRequests".equals(action)) {
            JSONObject argsObject = args.getJSONObject(0);
            Bundle consentSetAttributes = getConsentAttributes(argsObject);
            Bundle consentGetAttributes = getIdParameters(argsObject);
            Bundle idAttributes = getIdParameters(argsObject);
            addPolicyRegimeParameter(consentGetAttributes, argsObject);
            addPolicyRegimeParameter(consentSetAttributes, argsObject);

            KruxEventAggregator.consentGetRequest(consentGetAttributes);
            KruxEventAggregator.consentSetRequest(consentSetAttributes);
            KruxEventAggregator.consumerRemoveRequest(idAttributes);
            KruxEventAggregator.consumerPortabilityRequest(idAttributes);

            Log.d("KRUX", "Sending requests");
            callbackContext.success("Sending requests");
            return true;
        }

        if ("getSegments".equals(action)) {
            Log.d("KRUX", "Gettting segments: " + mSegments);
            callbackContext.success(mSegments);
            return true;
        }

        if ("trackPage".equals(action)) {
            Bundle pageAttrs = getPageAttributes(args);
            KruxEventAggregator.trackPageView("Main", pageAttrs, getUserAttributes(args));

            Log.d("KRUX", "Tracking page: " + pageAttrs.getString("path"));
            callbackContext.success("Tracking page: " + pageAttrs.getString("path"));
            return true;
        }

        if ("fireEvent".equals(action)) {
            Bundle eventAttrs = getEventAttributes(args);
            KruxEventAggregator.fireEvent("Main", eventAttrs);

            Log.d("KRUX", "Firing event: " + eventAttrs.getString("action"));
            callbackContext.success("Firing event: " + eventAttrs.getString("action"));
            return true;
        }

        Log.d("Cordova Locale Plugin", "Action not implemeted: " + action + ", Args: " + args);
        callbackContext.error("Krux invocation error");
        return false;
    }

    public Bundle getPageAttributes(JSONArray args) throws JSONException {
        JSONObject argsObject = args.getJSONObject(0);
        Bundle pageAttributes = new Bundle();
        pageAttributes.putString("type", (String) argsObject.get("type"));
        pageAttributes.putString("path", (String) argsObject.get("path"));
        return pageAttributes;
    }

    public Bundle getUserAttributes(JSONArray args) throws JSONException {
        JSONObject argsObject = args.getJSONObject(0);
        Bundle userAttributes = new Bundle();
        userAttributes.putBoolean("logged", (Boolean) argsObject.get("logged"));
        userAttributes.putString("email", (String) argsObject.get("email"));
        userAttributes.putString("userInfo.cod_ric", (String) argsObject.get("cod_ric"));
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

    private void addPolicyRegimeParameter(Bundle attributes, JSONObject argsObject) throws JSONException {
        attributes.putString("pr", (String) argsObject.get("policyRegime"));
    }

    private Bundle getIdParameters(JSONObject argsObject) throws JSONException {
        Bundle attributeBundle = new Bundle();
        if (argsObject.has("identity")) {
            JSONObject identityObj = (JSONObject) argsObject.get("identity");
            Iterator<String> keys = identityObj.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                if (identityObj.get(key) instanceof String) {
                    attributeBundle.putString(key, (String) identityObj.get(key));
                } else {
                    Log.e("Parsing Error", key + " attribute must be of type string");
                }
            }
        }
        return attributeBundle;
    }

    private Bundle getConsentAttributes(JSONObject argsObject) throws JSONException {
        Bundle attributeBundle = new Bundle();
        JSONObject identityObj = (JSONObject) argsObject.get("consent");
        Iterator<String> keys = identityObj.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            if (identityObj.get(key) instanceof Integer) {
                attributeBundle.putInt(key, (Integer) identityObj.get(key));
            } else {
                Log.e("Parsing Error", key + " attribute must be of type integer");
            }
        }
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
