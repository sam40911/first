package edu.ntust.afda.task;


import java.io.File;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import edu.ntust.afda.R;
import edu.ntust.afda.activity.ApkFileActivity;
import edu.ntust.afda.domain.APKFingerPrintInfo;
import edu.ntust.afda.domain.APKManifest;
import edu.ntust.afda.model.APKInfoExtractor;
import edu.ntust.afda.modelImpl.APKInfoExtractorImpl;
import edu.ntust.afda.parameter.ServiceURL;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class VerifyAsyncTask extends AsyncTask<Void, Integer, String> {
	private APKManifest apkManifest;
	private MultiValueMap<String, Object> map;
	private HttpHeaders headers;
	public static APKFingerPrintInfo apkFingerPrintInfo;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		APKInfoExtractorImpl apkInfoExtractorService=new  APKInfoExtractorImpl();
		apkManifest=apkInfoExtractorService.extractManifestInfo(ApkFileActivity.apkFilePath, ApkFileActivity.apkFileName, ApkFileActivity.versionCode);	
		map = new LinkedMultiValueMap<String, Object>();
		map.add("file", new  FileSystemResource(new File(ApkFileActivity.apkFilePath)));
		map.add("filename", ApkFileActivity.apkFileName);
		map.add("androidManifestXml", apkManifest.getShaForAndroidManifestXml());
		map.add("resourcesArsc", apkManifest.getShaForResourcesArsc());
		map.add("classesDex", apkManifest.getShaForClassesDex());
		map.add("mnftEntrySize", Integer.toString(apkManifest.getMnftEntrySize()));
		map.add("versionCode", apkManifest.getVersionCode());
		headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		ApkFileActivity.showProgressDialog();
		
	}

	@Override
	protected String doInBackground(Void... arg0) {
		 ResponseEntity<String> response = null;
		try {
			RestTemplate restTemplate = new RestTemplate(true); 
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(4000);
			restTemplate.setRequestFactory(requestFactory);
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);	
			try {
				response = restTemplate.exchange(ServiceURL.SHA1ANDFINGERPRINT_VERIFICATION,HttpMethod.POST, requestEntity, String.class);
				 if (response.getStatusCode().equals(HttpStatus.OK)) 
					 return response.getBody();
			} catch (HttpClientErrorException e) {
				Log.e("Network Error ", e.getLocalizedMessage(), e);
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		String warm = "";
		 try {
			JSONObject jsonResponse=null;
		
			if(result==null)
			{
				ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_orange);
				ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult0);	
			}
			else
			{
				jsonResponse=new JSONObject(result);
				 if(jsonResponse.getString("upload").equals("success"))
					{
						if(jsonResponse.getInt("verifyResult")==1)
						{
							ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_green);
							ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult1);
						}
						else if(jsonResponse.getInt("verifyResult")==3)
						{
							ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_yellow);	
							ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult3);
						}
						else if(jsonResponse.getInt("verifyResult")==2)
						{
							ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_blue);	
							ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult2);
						}
						else if(jsonResponse.getInt("verifyResult")==4)
						{
							String[] AfterSplitError = jsonResponse.getString("verifyError").split(",");
							for (int i = 0; i < AfterSplitError.length; i++)
							{
								warm+=AfterSplitError[i]+"、";
							}
							warm=warm.substring(0,warm.length()-1);
								ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_red);	
								ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult4);
								ApkFileActivity.textVerifyResultDetail.setText(warm);
								ApkFileActivity.textVerifyResultDetailTitle.setText("被修改的部分: \n");
								if(ApkFileActivity.apkFilePath.contains("/data/app"))
									ApkFileActivity.buttonDelete.setVisibility(View.VISIBLE);
						}		
					}
					else
					{
						 ApkFileActivity.textViewVerifyResult.setText("Apk file upload failed.");
						 ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_orange);
					}	
			}
	
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		 ApkFileActivity.progressDialog.dismiss();

	}
	@Override
	protected void onCancelled() {
		super.onCancelled();

	}
	}