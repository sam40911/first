package edu.ntust.afda.domain;

import java.io.Serializable;

/**
 * 
 * @author Ssu-Wei,Tang
 *
 */
public class FileChooserItem implements Serializable,
		Comparable<FileChooserItem> {

	private static final long serialVersionUID = -8829119527625686266L;
	private String name;
	private String data;
	private String date;
	private String path;

	private boolean folder;
	private boolean parent;

	public FileChooserItem(String name, String item, String date, String path,
			boolean folder, boolean parent) {
		this.name = name;
		this.data = item;
		this.date = date;
		this.path = path;
		this.folder = folder;
		this.parent = parent;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public boolean isParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int compareTo(FileChooserItem o) {
		if (this.name != null)
			return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
		else
			throw new IllegalArgumentException();
	}

}
