package edu.ntust.afda.task;
import android.os.AsyncTask;
import edu.ntust.afda.activity.ApkFileActivity;
import edu.ntust.afda.domain.APKFingerPrintInfo;
import edu.ntust.afda.domain.APKManifest;
import edu.ntust.afda.modelImpl.APKFingerPrintModelImpl;

/**
 * 
 * @author Ssu-Wei,Tang
 *
 */	
	
	public class GetFingerPrintAsyncTask extends AsyncTask<Void, Integer, String> {
		APKManifest apkManifest;
		APKFingerPrintModelImpl service;
		APKFingerPrintInfo apkfingerprint;
		long start;
		long end;
		long cost;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		 start = System.currentTimeMillis();
		 service = new APKFingerPrintModelImpl();
		}
		@Override
		protected String doInBackground(Void... arg0) {

//			 apkManifest=APKInfoExtractor.extractManifestInfo(apkFilePath, apkFileName, versionCode);
			
			 apkfingerprint=service.generateAPKFingerPrintFromAPKFile(ApkFileActivity.apkFilePath);
			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
//			System.out.println("SIZE"+apkManifest.getMnftEntrySize());
//			System.out.println("AM"+apkManifest.getShaForAndroidManifestXml());
//			System.out.println("ARSC"+apkManifest.getShaForResourcesArsc());
			
			
//			System.out.println("f_classes  " + apkfingerprint.getFingerPrintForClassesDex());
//			System.out.println("f_resources  "
//					+ apkfingerprint.getFingerPrintForResourcesArsc());
//			System.out.println("f_AndroidManifest  "
//					+ apkfingerprint.getFingerPrintForAndroidManifestXml());
//
//			System.out.println("f_.xml  " + apkfingerprint.getFingerPrintForResXml());
//			System.out.println("f_.png  " + apkfingerprint.getFingerPrintForResImg());
//
//			System.out.println("f_res   " + apkfingerprint.getFingerPrintForRes());
//
//			System.out.println("------------------------------------------");
//
//			System.out.println("f_all  " + apkfingerprint.getFingerPrintForEntireAPKFile());
			
			end = System.currentTimeMillis();
			cost = (end - start) ;
//			System.out.println("cost"+cost);
		}
		@Override
		protected void onCancelled() {
			super.onCancelled();
			 
			
		}
	
}
