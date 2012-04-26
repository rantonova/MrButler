package dani.app;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class MrButlerActivity extends Activity {
	/** Called when the activity is first created. */

	final String URL = "http://www.techfortesco.com/groceryapi_b1/restservice.aspx?";

	//developerkey=&applicationkey=72F5487EB36C635180D4"


	private class LoginTask extends AsyncTask<String, Integer, Long> {
		final AndroidHttpClient client = AndroidHttpClient.newInstance(this.getClass().getSimpleName());
		HttpGet request;
		HttpResponse response;

		@Override
		protected Long doInBackground(String... url) {

			String email = "daniela.antonova@gmail.com";
			String pass = "ratada13";

			final List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			nvps.add(new BasicNameValuePair("command", "LOGIN"));
			nvps.add(new BasicNameValuePair("email", email));
			nvps.add(new BasicNameValuePair("password", pass));
			nvps.add(new BasicNameValuePair("developerkey", "E5p37O0TlSmjKuE7WVM9"));
			nvps.add(new BasicNameValuePair("applicationkey", "72F5487EB36C635180D4"));

			String params = URLEncodedUtils.format(nvps, "utf-8");
			request = new HttpGet(URL + params);

			try {
				response = client.execute(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return new Long(0);
		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(Long result) {
				HttpEntity responseEntity = response.getEntity();
				//		final InputSource inputSource = new InputSource(responseEntity.getContent());
				TextView text = (TextView) MrButlerActivity.this.findViewById(R.id.login_result);

				try {
					text.setText(EntityUtils.toString(responseEntity));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		new LoginTask().execute(URL);
	}
}