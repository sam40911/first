package edu.ntust.afda.activity;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ntust.afda.R;
import edu.ntust.afda.adapter.FileChooserArrayAdapter;
import edu.ntust.afda.domain.FileChooserItem;
import edu.ntust.afda.util.APKFileHandler;
import edu.ntust.afda.util.FileHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author Ssu-Wei,Tang
 *
 */

public class ApkFileChooserActivity extends Activity {

	private ListView listViewFileChooser;
	private File currentDir;
	private FileChooserArrayAdapter adapterFileChooserArray;
	private File fileSelected;
	private FileFilter fileFilter;
	private ArrayList<String> extensions;
	private TextView textViewPath;
	private TextView textViewHeadTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_apkfile_chooser);
		findView();
		filter();
		currentDir = new File("/sdcard/");
		fill(new File("/sdcard/"));
	}

	private void filter() {
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.getStringArrayList("filterFileExtension") != null) {
				extensions = extras.getStringArrayList("filterFileExtension");
				fileFilter = new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return (pathname.getName().contains(".")?extensions.contains(pathname.getName().substring(pathname.getName().lastIndexOf("."))):true);
					}
				};
			}
		}
		
	}

	private void fill(File currentDir) {
		
		File[] dirs = null;
		List<FileChooserItem> dir = new ArrayList<FileChooserItem>();
		List<FileChooserItem> fls = new ArrayList<FileChooserItem>();
		if (fileFilter != null)
			dirs = currentDir.listFiles(fileFilter);
		else 
			dirs = currentDir.listFiles();
		textViewPath.setText(getString(R.string.currentDir) + ": "	+ currentDir.getName());
			
//		this.setTitle(getString(R.string.currentDir) + ": "	+ currentDir.getName());
		try {
			for (File ff : dirs) {
				if (ff.isDirectory() && !ff.isHidden()) {
					dir.add(new FileChooserItem(ff.getName(),getString(R.string.folder)	+ ":"+ String.valueOf(FileHandler.getFilelistSize(ff,fileFilter)), FileHandler.getFileLastDate(ff), ff.getAbsolutePath(),true, false));
				} else {
					if (!ff.isHidden()) {
						fls.add(new FileChooserItem(ff.getName(),getString(R.string.fileSize) + ": "+ ff.length(), FileHandler.getFileLastDate(ff), ff.getAbsolutePath(), false, false));
					}
				}
			}
		} catch (Exception e) {
		}
		Collections.sort(dir);
		Collections.sort(fls);
		dir.addAll(fls);

		if (!currentDir.getName().equalsIgnoreCase("sdcard")) {
			if (currentDir.getParentFile() != null)
				dir.add(0,new FileChooserItem("..",getString(R.string.parentDirectory), "",currentDir.getParent(), false, true));
		}
		adapterFileChooserArray = new FileChooserArrayAdapter(ApkFileChooserActivity.this,R.layout.list_item_apkfile_chooser, dir);
		listViewFileChooser.setAdapter(adapterFileChooserArray);

	}

	private void findView() {
		listViewFileChooser = (ListView) findViewById(R.id.listViewFileChooser);
		textViewPath=(TextView)findViewById(R.id.textViewPath);
		listViewFileChooser	.setOnItemClickListener(fileChooserItemClickListener);
		textViewHeadTitle=(TextView)findViewById(R.id.textViewHeaderTitle);
		textViewHeadTitle.setText(R.string.textViewHeadTitleMyFile);

	}

	private OnItemClickListener fileChooserItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> a, View v, int position, long id) {
			FileChooserItem fileChooserItem = adapterFileChooserArray
					.getItem(position);
			if (fileChooserItem.isFolder() || fileChooserItem.isParent()) {
				currentDir = new File(fileChooserItem.getPath());
				fill(currentDir);
			} else {

				if (fileChooserItem.getName().endsWith(".apk")) {
					Toast.makeText(ApkFileChooserActivity.this,	"File Clicked: " + fileChooserItem.getPath(),Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.putExtra("fileName", fileChooserItem.getName());
					intent.putExtra("filePath", fileChooserItem.getPath());
					intent.putExtra("versionCode", APKFileHandler.getAPKVersionCode(ApkFileChooserActivity.this,fileChooserItem.getPath()));
					setResult(Activity.RESULT_OK, intent);
					finish();

				} else {
					Toast.makeText(ApkFileChooserActivity.this,	"Please a apk file.", Toast.LENGTH_SHORT).show();
				}

			}

		}
	};

}
