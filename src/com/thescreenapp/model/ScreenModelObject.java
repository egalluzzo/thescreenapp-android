package com.thescreenapp.model;

import java.util.Date;

public interface ScreenModelObject {
	long getId();
	void setId(long id);
	String getUuid();
	Date getCreationDate();
	Date getUpdateDate();
	void setUpdateDate(Date updateDate);
}
