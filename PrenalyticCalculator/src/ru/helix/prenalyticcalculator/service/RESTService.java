package ru.helix.prenalyticcalculator.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class RESTService extends IntentService {
	private static final String TAG = RESTService.class.getName();

	public static final int GET = 0x1;
	public static final int POST = 0x2;
	public static final int PUT = 0x3;
	public static final int DELETE = 0x4;
	
	public static final String EXTRA_HTTP_VERB = "ru.helix.preanalyticcalculator.EXTRA_HTTP_VERB";
	public static final String EXTRA_PARAMS = "ru.helix.preanalyticcalculator.EXTRA_PARAMS";
	public static final String EXTRA_RESULT_RECEIVER = "ru.helix.preanalyticcalculator.EXTRA_RESULT_RECIEVER";
	
	public static final String REST_RESULT = "ru.helix.preanalyticcalculator.REST_RESULT";
	
	public RESTService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Uri action = intent.getData();
		Bundle extras = intent.getExtras();
		
		if (extras == null || action == null || !extras.containsKey(EXTRA_RESULT_RECEIVER)) {
			Log.e(TAG, "Данные запроса не обнаружены");
			return;
		}
		
		int verb = extras.getInt(EXTRA_HTTP_VERB);
		Bundle params = extras.getParcelable(EXTRA_PARAMS);
		ResultReceiver receiver = extras.getParcelable(EXTRA_RESULT_RECEIVER);
		
		try {
			HttpRequestBase request = null;
			
			switch(verb) {
				case GET: {
					request = new HttpGet();
					attachUriWithQuery(request, action, params);
				}
				break;
				
				case DELETE: {
					request = new HttpDelete();
					attachUriWithQuery(request, action, params);
				}
				break;
				
				case POST: {
					request = new HttpPost();
					request.setURI(new URI(action.toString()));
					
					HttpPost postRequest = (HttpPost) request;
					
					if(params != null) {
						UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsToList(params));
						postRequest.setEntity(formEntity);
					}
				}
				break;
				
				case PUT: {
					request = new HttpPut();
					request.setURI(new URI(action.toString()));
					
					HttpPut putRequest = (HttpPut) request;
					
					if(params != null) {
						UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsToList(params));
						putRequest.setEntity(formEntity);
					}
				}
				break;
			}
			
			if(request != null) {
				HttpClient client = new DefaultHttpClient();
				
				Log.d(TAG, "Выполняется запрос: " + verbToString(verb) + " : " + action.toString());
				
				HttpResponse response = client.execute(request);
				
				HttpEntity responseEntity = response.getEntity();
				StatusLine responseStatus = response.getStatusLine();
				int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;
				
				if(responseEntity != null) {
					Bundle resultData = new Bundle();
					resultData.putString(REST_RESULT, EntityUtils.toString(responseEntity));
					receiver.send(statusCode, resultData);
				}
				else {
					receiver.send(statusCode, null);
				}
			}
		}
		catch (URISyntaxException e) {
			Log.e(TAG, "Неправильный синтаксис запроса: " + verbToString(verb) + " : " + action.toString(), e);
			receiver.send(0, null);
		}
		catch (UnsupportedEncodingException e) {
			Log.e(TAG, "UrlEncodedFormEntity создана с неподдерживаемой кодировкой.", e);
			receiver.send(0, null);
		}
		catch (ClientProtocolException e) {
			Log.e(TAG, "Проблема при выполнении запроса.", e);
			receiver.send(0, null);
		}
		catch (IOException e) {
			Log.e(TAG, "Проблема при выполнении запроса.", e);
			receiver.send(0, null);
		}
	}
	
	private static void attachUriWithQuery(HttpRequestBase request, Uri uri, Bundle params) {
		try {
			if(params == null) {
				request.setURI(new URI(uri.toString()));
			}
			else {
				Uri.Builder uriBuilder = uri.buildUpon();
				
				for(BasicNameValuePair param: paramsToList(params)) {
					uriBuilder.appendQueryParameter(param.getName(), param.getValue());
				}
				
				uri = uriBuilder.build();
				request.setURI(new URI(uri.toString()));
			}
		}
		catch(URISyntaxException e) {
			Log.e(TAG, "Синтаксис адреса запроса содержит ошибки: " + uri.toString(), e);
		}
	}
	
	private static String verbToString(int verb) {
		switch(verb) {
		case GET:
			return "GET";
			
		case POST:
			return "POST";
			
		case PUT:
			return "PUT";
			
		case DELETE:
			return "DELETE";
		}
		
		return "";
	}
	
	private static List<BasicNameValuePair> paramsToList(Bundle params) {
		ArrayList<BasicNameValuePair> formList = new ArrayList<BasicNameValuePair>(params.size());
		
		for (String key : params.keySet()) {
			Object value = params.get(key);
			if(value != null) formList.add(new BasicNameValuePair(key, value.toString()));
		}
		
		return formList;
	}
}
