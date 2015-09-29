package edu.ntust.afda.task;

/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
import org.apache.http.entity.mime.MultipartEntity;
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
import edu.ntust.afda.R;
import edu.ntust.afda.activity.ApkFileActivity;
import edu.ntust.afda.domain.APKFingerPrintInfo;
import edu.ntust.afda.domain.APKManifest;
import edu.ntust.afda.modelImpl.APKFingerPrintModelImpl;
import edu.ntust.afda.modelImpl.APKInfoExtractorImpl;
import edu.ntust.afda.parameter.ServiceURL;

public class LightVerifyAsyncTask extends AsyncTask<Void, Integer, String> {

	private APKManifest apkManifest;
	private MultiValueMap<String, String> message;
	private HttpHeaders headers;
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		ApkFileActivity.showProgressDialog();
		APKInfoExtractorImpl apkInfoExtractorService=new  APKInfoExtractorImpl();
		apkManifest=apkInfoExtractorService.extractManifestInfo(ApkFileActivity.apkFilePath, ApkFileActivity.apkFileName, ApkFileActivity.versionCode);	
		message = new LinkedMultiValueMap<String, String>();
		message.add("apkFileName", apkManifest.getApkFileName());
		message.add("androidManifestXml", apkManifest.getShaForAndroidManifestXml());
		message.add("resourcesArsc", apkManifest.getShaForResourcesArsc());
		message.add("classesDex", apkManifest.getShaForClassesDex());
		message.add("mnftEntrySize", Integer.toString(apkManifest.getMnftEntrySize()));
		message.add("versionCode", apkManifest.getVersionCode());
		headers = new HttpHeaders();


	}

	@Override
	protected String doInBackground(Void... arg0) {
		
		ResponseEntity<String> response = null;
		try {
			RestTemplate restTemplate = new RestTemplate(true);  
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setConnectTimeout(4000);
			restTemplate.setRequestFactory(requestFactory);
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(message, headers);	
			try {
				response = restTemplate.exchange(ServiceURL.LIGHT_VERIFICATION,HttpMethod.POST, requestEntity, String.class);
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
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(result==null)
		{
			ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_orange);
			ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult0);
		}else if(result.equals("green")){
			ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_green);
			ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult1);	
		}else if(result.equals("red")){
			ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_red);
			ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult4);
		}else if(result.equals("yellow")){
			ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_yellow);
			ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult3);
		}	
		else if(result.equals("blue"))
		{
			ApkFileActivity.imageViewResult.setImageResource(R.drawable.result_blue);
			ApkFileActivity.textViewVerifyResult.setText(R.string.verifyResult2);
		}
		 ApkFileActivity.progressDialog.dismiss();
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

}