package haoke.ui.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;

public class Util_Global {
	// 按键音
	public void Beep(Context context) {
		// final AudioManager audioManager =
		// (AudioManager)context.getSystemService(context.AUDIO_SERVICE);
		// audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK);
		Log.v("Util_Global", "beep");
		Intent intent = new Intent("com.haoke.view.click");
		context.sendBroadcast(intent);
	}

	// 根据屏幕密度获取字体实际尺寸
	@SuppressLint("NewApi")
	public float getFontSizeDp(Context context, float fontSize) {
		Configuration config = context.getResources().getConfiguration();
		float dpSize = 0;
		try {
			dpSize = fontSize * 160 / config.densityDpi;
		} catch (Exception e) {
		}
		return dpSize;
	}
}
