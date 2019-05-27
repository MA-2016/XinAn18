package com.mobileai.luncert.utils.mullog.appender;

import com.mobileai.luncert.utils.mullog.formatter.Formatter;

public interface Appender {

    public void log(int logLevel, String message) throws Exception;

	public Appender setFormatter(Formatter formatter);

}