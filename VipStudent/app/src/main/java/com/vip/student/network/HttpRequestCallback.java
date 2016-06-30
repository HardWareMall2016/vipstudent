package com.vip.student.network;

public interface HttpRequestCallback {
	void onRequestFailed(String errorMsg);
	void onRequestFailedNoNetwork();
	void onTimeout();
	void onRequestCanceled();
	void onRequestSucceeded(String content);
}
