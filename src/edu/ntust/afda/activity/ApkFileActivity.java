package edu.ntust.afda.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.ntust.afda.R;
import edu.ntust.afda.Application.MyApplication;
import edu.ntust.afda.domain.APKFingerPrintInfo;
import edu.ntust.afda.domain.APKManifest;
import edu.ntust.afda.fingerprint.APKFingerPrintHandler;
import edu.ntust.afda.modelImpl.APKFingerPrintModelImpl;
import edu.ntust.afda.modelImpl.APKInfoExtractorImpl;
import edu.ntust.afda.task.GetFingerPrintAsyncTask;
import edu.ntust.afda.task.HeavyVerifyAPKAsyncTask;
import edu.ntust.afda.task.LightVerifyAsyncTask;
import edu.ntust.afda.task.VerifyAsyncTask;
import edu.ntust.afda.util.APKFileHandler;
import edu.ntust.afda.util.ButtonHandler;
import edu.ntust.afda.util.ImageTransformer;
/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class ApkFileActivity  extends Activity{
	
	private Button buttonHeacyVerify;
	private Button buttonLightVerify;
	private TextView textViewApkFileName;
	private ImageView imageViewApkIcon;
	private static final int REQUEST_PATH = 1;
	public static String apkFileName;
	public static String apkFilePath;
	public static int versionCode;
	public int s=0;
	private APKManifest apkManifest;
	public static TextView textViewVerifyResult;
	public static TextView textVerifyResultDetail;
	public static ImageView imageViewResult;
	public static ProgressDialog progressDialog;
	private WifiManager wifiManager;
	private DisplayMetrics metrics;
	private static Context mContext;
	public static TextView textVerifyResultDetailTitle;
	private ImageView imageViewFolder;
	public static Button buttonVerify;
	public static ImageButton buttonSearch;
	public EditText editTextApkFile;
	public static ImageButton buttonDelete;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_apkfile);
		mContext=this;
		findView();
		setListeners();
		
	}

	private void setListeners() {
		buttonHeacyVerify.setOnClickListener(VerifyClickListener);
		buttonLightVerify.setOnClickListener(VerifyClickListener);
		buttonVerify.setOnClickListener(VerifyClickListener);
		imageViewApkIcon.setOnClickListener(BrowserClickListener);
		buttonSearch.setOnClickListener(SearchClickListener);
		buttonDelete.setOnClickListener(DeleteClickListener);
		imageViewApkIcon.setBackground(null);
	}

	private void findView() {

	    getDisplay();
	    buttonSearch=(ImageButton)findViewById(R.id.buttonSearch);
	    editTextApkFile=(EditText)findViewById(R.id.editTextApkFile);
		buttonHeacyVerify=(Button)findViewById(R.id.buttonHeavyVerify);
		buttonLightVerify=(Button)findViewById(R.id.buttonLightVerify);
		buttonVerify=(Button)findViewById(R.id.buttonVerify);
		textViewApkFileName=(TextView)findViewById(R.id.textViewApkFileName);
		imageViewApkIcon=(ImageView)findViewById(R.id.imageViewApkIcon);
		imageViewFolder=(ImageView)findViewById(R.id.imageViewFolder);
		textViewVerifyResult=(TextView)findViewById(R.id.textVerifyResult);
		imageViewResult=(ImageView)findViewById(R.id.imageViewResult);
		textVerifyResultDetail=(TextView)findViewById(R.id.textVerifyResultDetail);
		textVerifyResultDetailTitle=(TextView)findViewById(R.id.textVerifyResultDetailTitle);
		buttonDelete=(ImageButton)findViewById(R.id.buttonDelete);
		setView();
		intiView();	
		
        
	}
	
	
	
	private void getDisplay() {
		 	metrics = new DisplayMetrics();  
		    getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		    System.out.println("dp"+getApplicationContext().getResources().getDisplayMetrics().density);
	}
	
	public static List<PackageInfo> getAllApps(Context coNtext) {
		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		PackageManager packageManager = coNtext.getPackageManager();
		List<PackageInfo> paklist = packageManager.getInstalledPackages(0);
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pak = (PackageInfo) paklist.get(i);
			if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
				apps.add(pak);
			}
		}
		return apps;
	}

	private Button.OnClickListener SearchClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(!editTextApkFile.getText().toString().equals(""))
			{
				PackageManager packageManager = ApkFileActivity.this.getPackageManager();
				List<PackageInfo> appList = getAllApps(ApkFileActivity.this);
				PackageInfo  packageinfo;
				boolean found=false;
				for(int i=0;i<appList.size();i++) {
					 packageinfo = appList.get(i);
					 
					 if(packageManager.getApplicationLabel(packageinfo.applicationInfo).toString().indexOf(editTextApkFile.getText().toString())==-1)
						{
							
						}
						else
						{
							found=true;
							textViewApkFileName.setText(packageinfo.applicationInfo.packageName+".apk");
							Bitmap apkBitmap=ImageTransformer.drawableToBitmap(packageManager.getApplicationIcon(packageinfo.applicationInfo));
							imageViewApkIcon.setImageBitmap(apkBitmap);
							
							try {
								ApplicationInfo AI = packageManager.getApplicationInfo(packageinfo.packageName, PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES);
//								System.out.println("PATH"+ AI.sourceDir);	
								apkFilePath=AI.sourceDir;
								apkFileName=packageinfo.applicationInfo.packageName+".apk";
								
							} catch (NameNotFoundException e) {
								e.printStackTrace();
							}
							versionCode=APKFileHandler.getAPKVersionCode(mContext, apkFilePath);
//							System.out.println("nameSS"+ packageManager.getApplicationLabel(packageinfo.applicationInfo).toString());
//							System.out.println("VERSION"+ versionCode);
//							System.out.println("NAME"+ packageinfo.applicationInfo.packageName+".apk");
//							System.out.println("ICON"+packageManager.getApplicationIcon(packageinfo.applicationInfo));
							 break;
							}
					
				}
				if(found==false)
				{
					getOkDialog(ApkFileActivity.this,R.string.dialogWarm,R.string.dialogNoInstall,R.string.dialogOK);
				}	
			}
			else
			{
				getOkDialog(ApkFileActivity.this,R.string.dialogWarm,R.string.dialogEnterAppName,R.string.dialogOK);	
			}
			
		}
	};
	private Button.OnClickListener BrowserClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			intiView();	
			ArrayList<String> extensions = new ArrayList<String>();
			extensions.add(".apk");
			Intent intent1 = new Intent(ApkFileActivity.this.getApplicationContext(),ApkFileChooserActivity.class);
			intent1.putStringArrayListExtra((String) ApkFileActivity.this.getResources().getText(R.string.filter), extensions);
			startActivityForResult(intent1, REQUEST_PATH);
		}
	};
	
	private Button.OnClickListener DeleteClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) { 
			try {
				Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
				intent.setData(Uri.parse("package:" + apkFileName.replace(".apk", "")));
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}	
	};
	private Button.OnClickListener VerifyClickListener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.buttonHeavyVerify:		
				if(check())
				{	
					intiView();
					new HeavyVerifyAPKAsyncTask().execute();
				}
				break;
			case R.id.buttonLightVerify:
				if(check())
				{
					intiView();	
					new LightVerifyAsyncTask().execute();
				}
				break;
			case R.id.buttonVerify:
				if(check())
				{
					intiView();	
					
					new VerifyAsyncTask().execute();
				
					
				}
				break;
			}			
		}	
	};
	
	 
	private boolean checkNetWorkConnect() {	
		boolean checkCon=true;
		ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if(mNetworkInfo==null||!mNetworkInfo.isConnected())
		{	checkCon=false;
			 wifiManager = (WifiManager) ApkFileActivity.this.getSystemService(ApkFileActivity.WIFI_SERVICE);	
			if (!wifiManager.isWifiEnabled()) {
				getOkAndCancelDialog(ApkFileActivity.this,R.string.dialogWarm,R.string.dialogWifiOpen,R.string.dialogOpen,R.string.dialogCancel);
			}
			else
			{
				getOkDialog(ApkFileActivity.this,R.string.dialogWarm,R.string.dialogChecknetwork,R.string.dialogOK);
			}
		}
		return checkCon;
	}
	protected void intiView() {
		textVerifyResultDetailTitle.setText("");
		imageViewResult.setImageResource(0);
		textViewVerifyResult.setText("");
		textVerifyResultDetail.setText("");	
		 buttonDelete.setVisibility(View.GONE);
		 
	}

	protected boolean check() {
		boolean check=false;
		if(textViewApkFileName.getText().toString().equals(""))
			getOkDialog(ApkFileActivity.this,R.string.dialogWarm,R.string.selectApkFile,R.string.dialogOK);
		else if(!textViewApkFileName.getText().toString().endsWith(".apk"))
			getOkDialog(ApkFileActivity.this,R.string.dialogWarm,R.string.selectApkFile,R.string.dialogOK);
		else if(textViewApkFileName.getText().toString().equals("apkFileName.apk"))
			getOkDialog(ApkFileActivity.this,R.string.dialogWarm,R.string.selectApkFile,R.string.dialogOK);
		else
			check=checkNetWorkConnect();
		return check;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_PATH) {
			if (resultCode == RESULT_OK) {
				apkFileName = data.getStringExtra("fileName");
				apkFilePath = data.getStringExtra("filePath");
				versionCode=data.getIntExtra("versionCode", -1);
				textViewApkFileName.setText(apkFileName);
				Bitmap apkBitmap=ImageTransformer.drawableToBitmap(APKFileHandler.getApkIcon(ApkFileActivity.this, apkFilePath));
//				Bitmap reSizeapkBitmap=ImageTransformer.reSize(apkBitmap, metrics.heightPixels/10, metrics.heightPixels/10);
//						imageViewApkIcon.setImageBitmap(ImageTransformer.getRoundedCornerBitmap(apkBitmap,100.f));
				imageViewApkIcon.setImageBitmap(apkBitmap);
//				imageViewApkIcon.setBackground(APKFileHandler.getApkIcon(ApkFileActivity.this, apkFilePath));
			}
		}
	}
	
	public static  ProgressDialog showProgressDialog() {
		progressDialog = new ProgressDialog(mContext);
		progressDialog.setMessage(mContext.getResources().getString(R.string.conServer));
		progressDialog.setCancelable(false);
		progressDialog.show();
		return progressDialog;
	}
	
	public void getOkDialog(Context context,int title, int message, int dialogok) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton(dialogok,	new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialog,int whichButton) {
						setResult(RESULT_OK);			
					}
				});
		builder.create();
		builder.show();
	}
	private void getOkAndCancelDialog(Context context,int title, int message, int dialogok, int dialogcnacel) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setCancelable(false);
		builder.setPositiveButton(dialogok,	new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialog,int i) {
						setResult(RESULT_OK);
						wifiManager.setWifiEnabled(true);
					}
				});
		builder.setNegativeButton(dialogcnacel,null);
		builder.create();
		builder.show();
	}
	private void setView() {
		imageViewApkIcon.setImageBitmap( BitmapFactory.decodeResource( getResources(), R.drawable.apk));	
//		 imageViewApkIcon.getLayoutParams().width=metrics.widthPixels/4;
//		 imageViewApkIcon.getLayoutParams().height=metrics.widthPixels/4;
//		 System.out.println(metrics.widthPixels+" X "+metrics.heightPixels+"sss"+metrics.widthPixels+"pp"+metrics.heightPixels/8);
//		 imageViewFolder.getLayoutParams().width=(metrics.heightPixels/9)/3;
//		 imageViewFolder.getLayoutParams().height=(metrics.heightPixels/9)/3;	
		 imageViewResult.getLayoutParams().width=metrics.heightPixels/10;
		 imageViewResult.getLayoutParams().height=metrics.heightPixels/10;	
		 buttonSearch.setImageBitmap( BitmapFactory.decodeResource( getResources(), R.drawable.search));
		 buttonDelete.setImageBitmap( BitmapFactory.decodeResource( getResources(), R.drawable.delete));	
	}
	
}
