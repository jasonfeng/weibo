package com.example.weibo.view.login;

import java.text.SimpleDateFormat;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weibo.dao.login.AccessTokenDao;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;

/**
 * 登录Activity 
 * @author fengzhongtian@163.com
 * @version 1.0
 * @since 2013年8月26日
 */
public class LoginActivity extends Activity {
	private Weibo mWeibo;

	private TextView mText;

	public static Oauth2AccessToken accessToken;

	/**SsoHandler 仅当sdk支持sso时有效，*/
	private SsoHandler mSsoHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);
		mWeibo = Weibo.getInstance(ConstantS.APP_KEY, ConstantS.REDIRECT_URL, ConstantS.SCOPE);
		accessToken = AccessTokenDao.readAccessToken(this);
		mWeibo.anthorize(getApplicationContext(), new AuthDialogListener());

	}

	class AuthDialogListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {

			String code = values.getString("code");
			if (code != null) {
				mText.setText("取得认证code: \r\n Code: " + code);
				Toast.makeText(MainActivity.this, "认证code成功", Toast.LENGTH_SHORT).show();
				return;
			}
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			accessToken = new Oauth2AccessToken(token, expires_in);
			if (accessToken.isSessionValid()) {
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(accessToken
						.getExpiresTime()));
				mText.setText("认证成功: \r\n access_token: " + token + "\r\n" + "expires_in: " + expires_in + "\r\n有效期："
						+ date);

				AccessTokenKeeper.keepAccessToken(MainActivity.this, accessToken);
				Toast.makeText(MainActivity.this, "认证成功", Toast.LENGTH_SHORT).show();
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
		// sso 授权回调
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
}
