package haoke.ui.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class HKTimer extends View {
	
	// ------------------------------外部接口 start------------------------------
	/**
	 * 设置计时器到点通知函数
	 * 
	 * @param OnHKTimeUpListener
	 */
	public void SetHKTimeUpListener(OnHKTimeUpListener listener) {
		mTimeUpListener = listener;
	}

	/**
	 * 启动计时器
	 * 
	 * @param delayMs
	 *            延迟delayMs毫秒后开始计时
	 * @param totalMs
	 *            计时周期（毫秒）
	 */
	public void SetTimer(long delayMs, long totalMs) {
		mDelayMs = delayMs;
		mTotalMs = totalMs;
		// 等待定时器结束
		mHandler.removeMessages(TIME_UP);
		mTimerWorking = false;
		// 启动新的计时器
		if (totalMs > 0) {
			// 启动新的计时器
			mHandler.sendEmptyMessageDelayed(TIME_UP, mDelayMs);
			mTimerWorking = true;
		}
	}

	/**
	 * 启动计时器
	 * 
	 * @param totalMs
	 *            计时周期（毫秒）
	 */
	public void SetTimer(long totalMs) {
		SetTimer(totalMs, totalMs);
	}

	/**
	 * 停止计时器
	 */
	public void StopTimer() {
		SetTimer(0);
	}

	/**
	 * 判断计时器是否正在工作
	 * 
	 * @return boolean
	 */
	public boolean IsWorking() {
		return mTimerWorking;
	}
	// ------------------------------外部接口 end------------------------------

	private long mDelayMs = 0; // 延时计时（毫秒）
	private long mTotalMs = 0; // 总计时（毫秒）
	private boolean mTimerWorking = false;
	private final int TIME_UP = 1000;
	private OnHKTimeUpListener mTimeUpListener = null;
	private Handler mHandler = new Handler() { // 计时器通知句柄
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TIME_UP:
				if (mTimerWorking) {
					mHandler.sendEmptyMessageDelayed(TIME_UP, mTotalMs);
					if (mTimeUpListener != null)
						mTimeUpListener.OnHKTimeUp(HKTimer.this);
				}
				break;
			}
		}
	};

	public HKTimer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public HKTimer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		SetTimer(mDelayMs, mTotalMs);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		// 等待定时器结束
		mHandler.removeMessages(TIME_UP);
		mTimerWorking = false;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		this.setMeasuredDimension(0, 0);
	}
}
