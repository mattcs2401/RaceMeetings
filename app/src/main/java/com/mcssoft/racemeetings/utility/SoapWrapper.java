package com.mcssoft.racemeetings.utility;


import android.content.Context;
import android.os.StrictMode;

import com.mcssoft.racemeetings.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SoapWrapper {

    public SoapWrapper(Context context) {
        this.context = context;
    }

    public String remoteRequest() {

        String result = "";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SoapObject request = new SoapObject(context.getResources().getString(R.string.TARGET_NAMESPACE),
                context.getResources().getString(R.string.OPERATION_NAME));

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(context.getResources().getString(R.string.SOAP_ADDRESS));

        try {
            httpTransport.call(context.getResources().getString(R.string.SOAP_ACTION), envelope);

            Object response = envelope.getResponse();

            result = response.toString();
        }
        catch (Exception exception) {
            result = exception.toString();
        }

        return result;
    }

    private Context context;
}
