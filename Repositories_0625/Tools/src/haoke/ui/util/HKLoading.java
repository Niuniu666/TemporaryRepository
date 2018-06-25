package haoke.ui.util;

import haoke.lib.tools.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.BitmapFactory.Options;
import android.util.AttributeSet;
import android.view.View;

public class HKLoading extends View {
	
	// ------------------------------外部接口 start------------------------------
	/**
	 * 重新开始动画
	 */
	public void RestartAnim() {
		mCurNum = 0;
		mTimer.SetTimer(mFrequency);
		PrepareAllBitmap();
	}

	/**
	 * 开始动画
	 */
	public void StartAnim() {
		mTimer.SetTimer(mFrequency);
		PrepareAllBitmap();
	}

	/**
	 * 获取控件宽度
	 * 
	 * @return int
	 */
	public void StopAnim() {
		mTimer.StopTimer();
	}

	/**
	 * 获取控件宽度
	 * 
	 * @return int
	 */
	public int GetWidth() {
		return mLayoutWidth;
	}

	/**
	 * 获取控件高度
	 * 
	 * @return int
	 */
	public int GetHeight() {
		return mLayoutHeight;
	}

	/**
	 * 设置控件在XML中的宽度和高度
	 * 
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 */
	public void SetLayoutSize(int width, int height) {
		mLayoutWidth = width;
		mLayoutHeight = height;
	}
	// ------------------------------外部接口 end------------------------------

	// 属性
	public int mPicId1 = 0;
	public int mPicId2 = 0;
	public int mPicId3 = 0;
	public int mPicId4 = 0;
	public int mPicId5 = 0;
	public int mPicId6 = 0;
	public int mPicId7 = 0;
	public int mPicId8 = 0;
	public int mPicId9 = 0;
	public int mPicId10 = 0;
	public int mPicId11 = 0;
	public int mPicId12 = 0;
	public Bitmap mPic1 = null;
	public Bitmap mPic2 = null;
	public Bitmap mPic3 = null;
	public Bitmap mPic4 = null;
	public Bitmap mPic5 = null;
	public Bitmap mPic6 = null;
	public Bitmap mPic7 = null;
	public Bitmap mPic8 = null;
	public Bitmap mPic9 = null;
	public Bitmap mPic10 = null;
	public Bitmap mPic11 = null;
	public Bitmap mPic12 = null;
	public boolean mIsScale = false; // 是否根据屏幕密度缩放图片
	public int mFrequency = 50; // 图片切换的频率（毫秒）
	// 内部变量
	private Context mContext;
	private int mLayoutWidth = 0;
	private int mLayoutHeight = 0;
	private HKTimer mTimer;
	private int mTotal = 0;
	private int mCurNum = 0;

	public HKLoading(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKLoading(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		mTotal = 0;
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKLoading);
		mPicId1 = typedArray.getResourceId(R.styleable.HKLoading_loading_pic1,
				0);
		if (mPicId1 != 0)
			mTotal++;
		mPicId2 = typedArray.getResourceId(R.styleable.HKLoading_loading_pic2,
				0);
		if (mPicId2 != 0)
			mTotal++;
		mPicId3 = typedArray.getResourceId(R.styleable.HKLoading_loading_pic3,
				0);
		if (mPicId3 != 0)
			mTotal++;
		mPicId4 = typedArray.getResourceId(R.styleable.HKLoading_loading_pic4,
				0);
		if (mPicId4 != 0)
			mTotal++;
		mPicId5 = typedArray.getResourceId(R.styleable.HKLoading_loading_pic5,
				0);
		if (mPicId5 != 0)
			mTotal++;
		mPicId6 = typedArray.getResourceId(R.styleable.HKLoading_loading_pic6,
				0);
		if (mPicId6 != 0)
			mTotal++;
		mPicId7 = typedArray.getResourceId(R.styleable.HKLoading_loading_pic7,
				0);
		if (mPicId7 != 0)
			mTotal++;
		mPicId8 = typedArray.getResourceId(R.styleable.HKLoading_loading_pic8,
				0);
		if (mPicId8 != 0)
			mTotal++;
		mPicId9 = typedArray.getResourceId(R.styleable.HKLoading_loading_pic9,
				0);
		if (mPicId9 != 0)
			mTotal++;
		mPicId10 = typedArray.getResourceId(
				R.styleable.HKLoading_loading_pic10, 0);
		if (mPicId10 != 0)
			mTotal++;
		mPicId11 = typedArray.getResourceId(
				R.styleable.HKLoading_loading_pic11, 0);
		if (mPicId11 != 0)
			mTotal++;
		mPicId12 = typedArray.getResourceId(
				R.styleable.HKLoading_loading_pic12, 0);
		if (mPicId12 != 0)
			mTotal++;
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKLoading_loading_pic_scale, false);
		int freq = typedArray.getInt(R.styleable.HKLoading_loading_frequency,
				mFrequency);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mIsScale = isScale;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mIsScale;
		if (mPicId1 != 0) {
			mPic1 = BitmapFactory.decodeResource(getResources(), mPicId1,
					bitmapOptions);
			SetLayoutSize(mPic1.getWidth(), mPic1.getHeight());
		}
		if (freq > 0)
			mFrequency = freq; // 图片切换的频率（毫秒）
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mTimer != null)
			mTimer.SetTimer(mFrequency);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mTimer != null)
			mTimer.StopTimer();
		ReleaseBitmap(); //释放资源 add by lyb 20160201
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 画图片
		Bitmap bmp = GetBitmap(mCurNum);
		if (bmp != null && !bmp.isRecycled())
			canvas.drawBitmap(bmp, 0, 0, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		this.setMeasuredDimension(mLayoutWidth, mLayoutHeight);
	}

	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mTimer = new HKTimer(context);
		mTimer.SetHKTimeUpListener(new OnHKTimeUpListener() {
			@Override
			public void OnHKTimeUp(View view) {
				// TODO Auto-generated method stub
				if (mCurNum >= mTotal - 1)
					mCurNum = 0;
				else
					mCurNum++;
				invalidate();
			}
		});
	}

	private void PrepareAllBitmap() {
		for (int i = 0; i < 12; i++) {
			GetBitmap(i);
		}
	}

	private Bitmap GetBitmap(int no) {
		Bitmap bmp = null;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = false;
		switch (no) {
		case 0:
			if (mPic1 == null && mPicId1 != 0)
				mPic1 = BitmapFactory.decodeResource(getResources(), mPicId1,
						bitmapOptions);
			bmp = mPic1;
			break;
		case 1:
			if (mPic2 == null && mPicId2 != 0)
				mPic2 = BitmapFactory.decodeResource(getResources(), mPicId2,
						bitmapOptions);
			bmp = mPic2;
			break;
		case 2:
			if (mPic3 == null && mPicId3 != 0)
				mPic3 = BitmapFactory.decodeResource(getResources(), mPicId3,
						bitmapOptions);
			bmp = mPic3;
			break;
		case 3:
			if (mPic4 == null && mPicId4 != 0)
				mPic4 = BitmapFactory.decodeResource(getResources(), mPicId4,
						bitmapOptions);
			bmp = mPic4;
			break;
		case 4:
			if (mPic5 == null && mPicId5 != 0)
				mPic5 = BitmapFactory.decodeResource(getResources(), mPicId5,
						bitmapOptions);
			bmp = mPic5;
			break;
		case 5:
			if (mPic6 == null && mPicId6 != 0)
				mPic6 = BitmapFactory.decodeResource(getResources(), mPicId6,
						bitmapOptions);
			bmp = mPic6;
			break;
		case 6:
			if (mPic7 == null && mPicId7 != 0)
				mPic7 = BitmapFactory.decodeResource(getResources(), mPicId7,
						bitmapOptions);
			bmp = mPic7;
			break;
		case 7:
			if (mPic8 == null && mPicId8 != 0)
				mPic8 = BitmapFactory.decodeResource(getResources(), mPicId8,
						bitmapOptions);
			bmp = mPic8;
			break;
		case 8:
			if (mPic9 == null && mPicId9 != 0)
				mPic9 = BitmapFactory.decodeResource(getResources(), mPicId9,
						bitmapOptions);
			bmp = mPic9;
			break;
		case 9:
			if (mPic10 == null && mPicId10 != 0)
				mPic10 = BitmapFactory.decodeResource(getResources(), mPicId10,
						bitmapOptions);
			bmp = mPic10;
			break;
		case 10:
			if (mPic11 == null && mPicId11 != 0)
				mPic11 = BitmapFactory.decodeResource(getResources(), mPicId11,
						bitmapOptions);
			bmp = mPic11;
			break;
		case 11:
			if (mPic12 == null && mPicId12 != 0)
				mPic12 = BitmapFactory.decodeResource(getResources(), mPicId12,
						bitmapOptions);
			bmp = mPic12;
			break;
		}
		return bmp;
	}

	// 释放图片资源
	public void ReleaseBitmap() {
		if (mPic1 != null && mPicId1 != 0) {
			mPic1.recycle();
			mPic1 = null;
		}
		if (mPic2 != null && mPicId2 != 0) {
			mPic2.recycle();
			mPic2 = null;
		}
		if (mPic3 != null && mPicId3 != 0) {
			mPic3.recycle();
			mPic3 = null;
		}
		if (mPic4 != null && mPicId4 != 0) {
			mPic4.recycle();
			mPic4 = null;
		}
		if (mPic5 != null && mPicId5 != 0) {
			mPic5.recycle();
			mPic5 = null;
		}
		if (mPic6 != null && mPicId6 != 0) {
			mPic6.recycle();
			mPic6 = null;
		}
		if (mPic7 != null && mPicId7 != 0) {
			mPic7.recycle();
			mPic7 = null;
		}
		if (mPic8 != null && mPicId8 != 0) {
			mPic8.recycle();
			mPic8 = null;
		}
		if (mPic9 != null && mPicId9 != 0) {
			mPic9.recycle();
			mPic9 = null;
		}
		if (mPic10 != null && mPicId10 != 0) {
			mPic10.recycle();
			mPic10 = null;
		}
		if (mPic11 != null && mPicId11 != 0) {
			mPic11.recycle();
			mPic11 = null;
		}
		if (mPic12 != null && mPicId12 != 0) {
			mPic12.recycle();
			mPic12 = null;
		}
	}
}
