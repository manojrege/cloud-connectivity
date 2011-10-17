package com.oauth2;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.api.client.auth.oauth2.draft10.AccessTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessTokenRequest.GoogleAuthorizationCodeGrant;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAuthorizationRequestUrl;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.GenericData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
public class Auth extends Activity {
	
		private static final String SCOPE = "https://www.googleapis.com/auth/latitude.current.best";
		private static final String CALLBACK_URL = "urn:ietf:wg:oauth:2.0:oob";
		private static final HttpTransport TRANSPORT = new NetHttpTransport();
		private static final JsonFactory JSON_FACTORY = new JacksonFactory();
		private static final String AUTH_URL="http://cloudadhocconnectivity.appspot.com/auth";
	 	// FILL THESE IN WITH YOUR VALUES FROM THE API CONSOLE
		private static final String CLIENT_ID = "988023935305-nlvt2ttk32do3j7ipclnud301qkh96ed.apps.googleusercontent.com";
		private static final String CLIENT_SECRET ="DR2lHHAPs6iy3UtZXrg6TRvk";
		public static final String API_KEY="AIzaSyD9gVCXctC4OeCB5MphAigaEHL0nT2yQm8";
		public static String registration_id;
		
		SharedPreferences prefs;
		AccessTokenResponse accessTokenResponse;
		CredentialStore credentialStore;
		
		
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			this.prefs=PreferenceManager.getDefaultSharedPreferences(this);
			Bundle extras=getIntent().getExtras();
			registration_id=extras.getString("registration_id");
			System.out.println(registration_id);
		}
	
		@Override
		public void onResume()
		{
			super.onResume();
			final WebView web =new WebView(this);
			web.getSettings().setJavaScriptEnabled(true);
			web.setVisibility(View.VISIBLE);
			setContentView(web);
			String authorizeUrl=new GoogleAuthorizationRequestUrl(CLIENT_ID,CALLBACK_URL,SCOPE).build();
			web.loadUrl(authorizeUrl);
			web.setWebViewClient(new WebViewClient()
			{
				@Override
				public void onPageFinished(WebView view, String url)
				{
					if(url.indexOf("xsrf")!=-1)
					{
						
						try
						{
							String title=web.getTitle();
							String authorizationCode=title.substring(title.indexOf("code=")+5,title.length());
							System.out.println(authorizationCode);
							GoogleAuthorizationCodeGrant authRequest = new GoogleAuthorizationCodeGrant(TRANSPORT,JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, authorizationCode, CALLBACK_URL);
							authRequest.useBasicAuthorization = false;
							AccessTokenResponse accessTokenResponse = authRequest.execute();
							String accessToken = accessTokenResponse.accessToken;
							System.out.println(accessTokenResponse);
							GoogleAccessProtectedResource access = new GoogleAccessProtectedResource(accessToken,TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, accessTokenResponse.refreshToken);
							HttpRequestFactory rf = TRANSPORT.createRequestFactory(access);
	    	    			StringBuilder data = new StringBuilder();
	        				/*data.append("token").append("=").append(accessTokenResponse.accessToken).append("&");
	        				data.append("expiry").append("=").append(accessTokenResponse.expiresIn.toString()).append("&");
	        				data.append("refresh").append("=").append(accessTokenResponse.refreshToken).append("&");*/
	        				data.append("scope").append("=").append(SCOPE).append("&");
	        				data.append("client_id").append("=").append(CLIENT_ID);
	        				
	        				GenericData generic = new GenericData();
	        				generic.put("accessToken",accessTokenResponse.accessToken);
							generic.put("expiresIn",accessTokenResponse.expiresIn);
							generic.put("refresh_token",accessTokenResponse.refreshToken);
							generic.put("registration_id",registration_id);
							//generic.put("token_type",accessTokenResponse.token_type);
							generic.put("scope",accessTokenResponse.scope);
	        				GenericUrl shortenEndpoint = new GenericUrl(AUTH_URL+"?"+data.toString());
							//System.out.println("The auth url is " + shortenEndpoint.toString());
							HttpHeaders header =new HttpHeaders();
							header.setContentType("application/json");
							HttpRequest request = rf.buildPostRequest(shortenEndpoint,null);
							request.setContent(new JsonHttpContent(new JacksonFactory(),generic));
							System.out.println("Get Request Built");
							request.setHeaders(header);
							System.out.println("Headers Set");
							HttpResponse response = request.execute();
							System.out.println("Request Executed");
							BufferedReader output = new BufferedReader(new InputStreamReader(response.getContent()));
							String input;
							while((input=output.readLine())!=null)
							{
								System.out.println(input);
							}
						} 
						catch (IOException e) 
						{
						// TODO Auto-generated catch block
							StringWriter sw = new StringWriter();
							e.printStackTrace(new PrintWriter(sw));
							System.out.println(sw.toString());

						}
    		    }
	    	}
	    });
	  }
}