package org.springframework.beans.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.springframework.util.StringUtils;
import java.text.ParseException;

public class CustomTimestampEditor extends PropertyEditorSupport {

	private final SimpleDateFormat[] dateFormats;
	private final boolean allowEmpty;
	private final int exactDateLength;

	public CustomTimestampEditor(SimpleDateFormat[] dateFormats, boolean allowEmpty) {
		this.dateFormats = dateFormats;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = -1;
	}

	public CustomTimestampEditor(SimpleDateFormat[] dateFormats, boolean allowEmpty, int exactDateLength) {
		this.dateFormats = dateFormats;
		this.allowEmpty = allowEmpty;
		this.exactDateLength = exactDateLength;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		} else {
			if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
				throw new IllegalArgumentException("Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
			}
			boolean flag = false;
			for(SimpleDateFormat dateFormat : this.dateFormats){
				try {
					setValue(new Timestamp(dateFormat.parse(text).getTime()));
					flag = true;
				} catch (ParseException ex) {
					continue;
				}
			}
			if(!flag){
				throw new IllegalArgumentException("Could not parse date: " + text);
			}
			
		}
	}

	@Override
	public String getAsText() {
		Timestamp value = (Timestamp) getValue();
		for(SimpleDateFormat dateFormat : this.dateFormats){
			try {
				return value != null ? dateFormat.format(value) : "";
			} catch (Exception ex) {
				continue;
			}
		}
		return null;
		
	}
}