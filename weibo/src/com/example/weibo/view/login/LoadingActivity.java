/*
 * 新浪微博系统 Copyright (c) 2013 FZT All Rights Reserved.
 */
package com.example.weibo.view.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.weibo.R;
import com.example.weibo.common.GlobalConstant;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;
import com.weibo.sdk.android.util.AccessTokenKeeper;

/**
 * Loading View
 * 
 * @author <a href="mailto:fengzhongtian@163.com">Jason Feng</a>
 * @version 1.0
 * @since 2013年9月28日
 */
public class LoadingActivity extends Activity {

	/** 微博API接口类，提供登陆等功能 */
	private Weibo mWeibo;

	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;

	/** 注意：SsoHandler 仅当sdk支持sso时有效 */
	private SsoHandler mSsoHandler;

	/* (non-Javadoc)
	* @see android.app.Activity#onCreate(android.os.Bundle)
	*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_loading);
		/**
		 * 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息， 第一次启动本应用，AccessToken
		 * 不可用
		 */
		Oauth2AccessToken mAccessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
		if (mAccessToken.isSessionValid()) {
			Toast.makeText(getApplicationContext(), "未失效", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "已失效", Toast.LENGTH_SHORT).show();
			/*Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);*/
			mWeibo = Weibo.getInstance(GlobalConstant.APP_KEY, GlobalConstant.REDIRECT_URL, GlobalConstant.SCOPE);
			mSsoHandler = new SsoHandler(LoadingActivity.this, mWeibo);
			mSsoHandler.authorize(new AuthDialogListener(), null);
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * 微博认证授权回调类。 1. SSO登陆时，需要在{@link #onActivityResult}
	 * 中调用mSsoHandler.authorizeCallBack后， 该回调才会被执行。 2. 非SSO登陆时，当授权后，就会被执行。
	 * 当授权成功后，请保存该access_token、expires_in等信息到SharedPreferences中。
	 */
	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {

			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			mAccessToken = new Oauth2AccessToken(token, expires_in);
			if (mAccessToken.isSessionValid()) {
				AccessTokenKeeper.keepAccessToken(getApplicationContext(), mAccessToken);
			}
		}

		@Override
		public void onError(WeiboDialogError e) {
			Toast.makeText(getApplicationContext(), "Auth error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(getApplicationContext(), "Auth cancel", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(getApplicationContext(), "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// SSO 授权回调
		// 重要：发起 SSO 登陆的Activity必须重写onActivityResult
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
}
