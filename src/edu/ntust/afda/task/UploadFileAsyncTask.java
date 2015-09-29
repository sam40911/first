package edu.ntust.afda.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;






























import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import edu.ntust.afda.activity.ApkFileActivity;
import edu.ntust.afda.domain.APKFingerPrintInfo;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;
/**
 * 
 * @author Ssu-Wei Tang
 *
 */
public class UploadFileAsyncTask extends AsyncTask<Void, Integer, String> {
	
	private MultiValueMap<String, Object> map;
	private HttpHeaders headers;
	public static APKFingerPrintInfo apkFingerPrintInfo;
	long start;
	long end;
	long cost;
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		 start = System.currentTimeMillis();
		map = new LinkedMultiValueMap<String, Object>();
		map.add("file", new  FileSystemResource(new File(ApkFileActivity.apkFilePath)));
		map.add("filename", ApkFileActivity.apkFileName);
		headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		
		

	}

	@Override
	protected String doInBackground(Void... arg0) {
		 ResponseEntity<String> response = null;
		try {
			
			final String url = "http://140.118.110.68:21235/verifyapkfingerprint";
			
//			final String url = "http://140.118.110.68:8080/verifyapkfingerprint";
			RestTemplate restTemplate = new RestTemplate(true);  
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);	
			 response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,String.class);
					
//			if (HttpStatus.OK == response.getStatusCode()) {
//				return response.getBody();
//		    } else {
//		    	return null;
//		    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		return response.getBody();
	}
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);

	}

	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		
		 try {
			JSONObject jsonResponse=new JSONObject(result);
//			if(jsonResponse.getString("uploadFileInfo").equals("success"))
//			{
				apkFingerPrintInfo=new  APKFingerPrintInfo();
				apkFingerPrintInfo.setFingerPrintForAndroidManifestXml(jsonResponse.getString("fingerPrintForResXml"));
				
//			}
			
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}

		end = System.currentTimeMillis();
		cost = (end - start) ;
//		System.out.println("cost"+cost);

	}

	
	@Override
	protected void onCancelled() {
		super.onCancelled();

	}



}
