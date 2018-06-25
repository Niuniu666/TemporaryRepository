package haoke.ui.util;

import haoke.lib.tools.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HKButtonText extends FrameLayout {

	// ------------------------------外部接口 start------------------------------
	/**
	 * 设置控件的触摸监听函数
	 * 
	 * @param OnHKTouchListener
	 */
	public void SetHKTouchListener(OnHKTouchListener listener) {
		mTouchedListener = listener;
	}

	/**
	 * 设置控件有效性
	 * 
	 * @param enable
	 */
	public void SetEnable(boolean enable) {
		if (GetEnable() == enable)
			return;

		if (enable)
			mTextView.setTextColor(mTextColorUp);
		else
			mTextView.setTextColor(mTextColorDisable);
		mButton.SetEnable(enable);
	}

	/**
	 * 获取控件有效性
	 * 
	 * @return boolean
	 */
	public boolean GetEnable() {
		return mButton.GetEnable();
	}

	/**
	 * 设置控件焦点状态
	 * 
	 * @param focus
	 */
	public void SetFocus(boolean focus) {
		if (GetFocus() == focus)
			return;

		if (focus)
			mTextView.setTextColor(mTextColorFocus);
		else
			mTextView.setTextColor(mTextColorUp);
		mButton.SetFocus(focus);
	}

	/**
	 * 获取控件焦点状态
	 * 
	 * @return boolean
	 */
	public boolean GetFocus() {
		return mButton.GetFocus();
	}

	/**
	 * 获取控件显示区域
	 * 
	 * @return Area
	 */
	public Area GetShowArea() {
		return mButton.GetShowArea();
	}

	/**
	 * 获取控件宽度
	 * 
	 * @return int
	 */
	public int GetWidth() {
		return mButton.GetWidth();
	}

	/**
	 * 获取控件高度
	 * 
	 * @return int
	 */
	public int GetHeight() {
		return mButton.GetHeight();
	}

	/**
	 * 获取控件在XML中的宽度和高度
	 * 
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 */
	public void SetLayoutSize(int width, int height) {
		mButton.SetLayoutSize(width, height);
	}

	/**
	 * 设置按钮文本
	 * 
	 * @param text
	 */
	public void SetText(String text) {
		if (!mText.equals(text)) {
			mTextWidth = mTextOriWidth;
			mTextHeight = mTextOriHeight;
			if (mText == null || mText.equals("")) {
				UpdateTextLayout();
				this.requestLayout();
			}
			mText = text;
			mTextView.setText(mText);
		}
	}

	/**
	 * 获取按钮文本
	 * 
	 * @return String
	 */
	public String GetText() {
		return mText;
	}

	/**
	 * 设置按钮文本大小
	 * 
	 * @param size
	 */
	public void SetTextSize(float size) {
		mTextSize = size;
		mTextView.setTextSize(mTextSize);
	}

	/**
	 * 获取按钮文本大小
	 * 
	 * @return float
	 */
	public float GetTextSize() {
		return mTextSize;
	}

	/**
	 * 设置控件松开图片
	 * 
	 * @param bmp
	 */
	public void SetPicUp(Bitmap bmp) {
		mButton.SetPicUp(bmp);
	}

	/**
	 * 设置控件按下图片
	 * 
	 * @param bmp
	 */
	public void SetPicDown(Bitmap bmp) {
		mButton.SetPicDown(bmp);
	}

	/**
	 * 设置控件焦点图片
	 * 
	 * @param bmp
	 */
	public void SetPicFocus(Bitmap bmp) {
		mButton.SetPicFocus(bmp);
	}

	/**
	 * 设置控件无效图片
	 * 
	 * @param bmp
	 */
	public void SetPicDisable(Bitmap bmp) {
		mButton.SetPicDisable(bmp);
	}

	/**
	 * 设置控件松开图标
	 * 
	 * @param bmp
	 */
	public void SetIconUp(Bitmap bmp) {
		mButton.SetIconUp(bmp);
	}

	/**
	 * 设置控件按下图标
	 * 
	 * @param bmp
	 */
	public void SetIconDown(Bitmap bmp) {
		mButton.SetIconDown(bmp);
	}

	/**
	 * 设置控件焦点图标
	 * 
	 * @param bmp
	 */
	public void SetIconFocus(Bitmap bmp) {
		mButton.SetIconFocus(bmp);
	}

	/**
	 * 设置控件无效图标
	 * 
	 * @param bmp
	 */
	public void SetIconDisable(Bitmap bmp) {
		mButton.SetIconDisable(bmp);
	}

	// 返回状态图片
	public Bitmap GetPicUp() {
		return mButton.GetPicUp();
	}

	// 返回状态图片
	public Bitmap GetPicDown() {
		return mButton.GetPicDown();
	}

	// 返回状态图片
	public Bitmap GetPicFocus() {
		return mButton.GetPicFocus();
	}

	// 返回状态图片
	public Bitmap GetPicDisable() {
		return mButton.GetPicDisable();
	}

	// 返回状态图片
	public Bitmap GetIconUp() {
		return mButton.GetIconUp();
	}

	// 返回状态图片
	public Bitmap GetIconDown() {
		return mButton.GetIconDown();
	}

	// 返回状态图片
	public Bitmap GetIconFocus() {
		return mButton.GetIconFocus();
	}

	// 返回状态图片
	public Bitmap GetIconDisable() {
		return mButton.GetIconDisable();
	}

	// ------------------------------外部接口 end------------------------------

	// 属性（这里只显示文本的属性，按钮的属性请查看"HKButton"类）
	public String mText = null; // 文本内容
	public float mTextSize = 20; // 文本大小
	public int mTextColorUp = Color.BLACK; // 文本颜色（松开）
	public int mTextColorDown = Color.BLACK; // 文本颜色（按下）
	public int mTextColorFocus = Color.BLACK; // 文本颜色（焦点）
	public int mTextColorDisable = Color.BLACK; // 文本颜色（无效）
	public float mTextX; // 文本位置X
	public float mTextY; // 文本位置Y
	public int mTextWidth = -1; // 文本宽度
	public int mTextHeight = -1; // 文本高度
	public int mTextOriWidth = -1; // 文本宽度
	public int mTextOriHeight = -1; // 文本高度
	public int mTextGravity = Gravity.CENTER; // 对齐方式
	public int mTextLayoutGravity = Gravity.LEFT; // 对齐方式
	public int mLineNum = 1; // 行数
	public boolean mTextInBtn = false; // 文本是否在按钮内部
	// 内部变量
	private Context mContext;
	private HKButton mButton;
	private TextView mTextView;
	private OnHKTouchListener mTouchedListener = null;

	public HKButtonText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Init(context);
	}

	public HKButtonText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		Init(context);
		// 获取属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,
				R.styleable.HKButton);
		mButton.mPicUpId = typedArray.getResourceId(
				R.styleable.HKButton_btn_pic_up, 0);
		mButton.mPicDownId = typedArray.getResourceId(
				R.styleable.HKButton_btn_pic_down, 0);
		mButton.mPicFocusId = typedArray.getResourceId(
				R.styleable.HKButton_btn_pic_focus, 0);
		mButton.mPicDisableId = typedArray.getResourceId(
				R.styleable.HKButton_btn_pic_disable, 0);
		mButton.mIconUpId = typedArray.getResourceId(
				R.styleable.HKButton_btn_icon_up, 0);
		mButton.mIconDownId = typedArray.getResourceId(
				R.styleable.HKButton_btn_icon_down, 0);
		mButton.mIconFocusId = typedArray.getResourceId(
				R.styleable.HKButton_btn_icon_focus, 0);
		mButton.mIconDisableId = typedArray.getResourceId(
				R.styleable.HKButton_btn_icon_disable, 0);
		float iconX = typedArray.getDimension(R.styleable.HKButton_btn_icon_x,
				0); // 图标X
		float iconY = typedArray.getDimension(R.styleable.HKButton_btn_icon_y,
				0); // 图标Y
		boolean isScale = typedArray.getBoolean(
				R.styleable.HKButton_btn_pic_scale, false);
		String text = typedArray.getString(R.styleable.HKButton_btn_text);
		float textSize = typedArray.getDimension(
				R.styleable.HKButton_btn_text_size, 20);
		int textColorUp = typedArray.getColor(
				R.styleable.HKButton_btn_text_color, Color.BLACK);
		int textColorDown = typedArray.getColor(
				R.styleable.HKButton_btn_text_color_down, 0);
		int textColorFocus = typedArray.getColor(
				R.styleable.HKButton_btn_text_color_focus, 0);
		int textColorDisable = typedArray.getColor(
				R.styleable.HKButton_btn_text_color_disable, 0);
		boolean isInBtn = typedArray.getBoolean(
				R.styleable.HKButton_btn_text_inbtn, false);
		float textX = typedArray.getDimension(R.styleable.HKButton_btn_text_x,
				0);
		float textY = typedArray.getDimension(R.styleable.HKButton_btn_text_y,
				0);
		float textWidth = typedArray.getDimension(
				R.styleable.HKButton_btn_text_width, -1);
		float textHeight = typedArray.getDimension(
				R.styleable.HKButton_btn_text_height, -1);
		int textGravity = typedArray.getInt(
				R.styleable.HKButton_btn_text_gravity, 4);
		int textLayoutGravity = typedArray.getInt(
				R.styleable.HKButton_btn_text_layout_gravity, 4);
		int textLine = typedArray.getInt(R.styleable.HKButton_btn_text_line, 1);
		int longDownTime = typedArray.getInt(
				R.styleable.HKButton_btn_longdown_time, 0);
		int clickSpace = typedArray.getInt(
				R.styleable.HKButton_btn_click_space, 0);
		typedArray.recycle();
		// 将属性值分配到相应变量
		mButton.mIsScale = isScale;
		mButton.mLongDownTime = longDownTime;
		mButton.mClickSpace = clickSpace;
		Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inScaled = mButton.mIsScale;
		if (mButton.mPicUpId != 0) {
			// 松开图片
			mButton.mPicUp = BitmapFactory.decodeResource(getResources(),
					mButton.mPicUpId, bitmapOptions);
		}
		if (mButton.mIconUpId != 0) {
			// 松开图标
			mButton.mIconUp = BitmapFactory.decodeResource(getResources(),
					mButton.mIconUpId, bitmapOptions);
		}
		mButton.mIconX = iconX; // 图标X
		mButton.mIconY = iconY; // 图标Y

		mText = text;
		mTextSize = new Util_Global().getFontSizeDp(context, textSize);
		mTextColorUp = textColorUp; // 文本颜色（松开）
		if (textColorDown == 0)
			mTextColorDown = mTextColorUp;
		else
			mTextColorDown = textColorDown; // 文本颜色（按下）
		if (textColorFocus == 0)
			mTextColorFocus = mTextColorUp;
		else
			mTextColorFocus = textColorFocus; // 文本颜色（焦点）
		if (textColorDisable == 0)
			mTextColorDisable = mTextColorUp;
		else
			mTextColorDisable = textColorDisable; // 文本颜色（无效）
		mTextInBtn = isInBtn;
		mTextX = textX; // 文本位置X
		mTextY = textY; // 文本位置Y
		mTextWidth = (int) textWidth; // 文本宽度
		mTextHeight = (int) textHeight; // 文本高度
		mTextOriWidth = (int) textWidth; // 文本宽度
		mTextOriHeight = (int) textHeight; // 文本高度
		mTextGravity = ConvertGravity(textGravity); // 对齐方式
		mTextLayoutGravity = textLayoutGravity; // 对齐方式
		mLineNum = textLine; // 行数

		// 设置布局
		this.addView(mButton);
		UpdateTextLayout();
	}

	private void UpdateTextLayout() {
		if (mText != null && !mText.equals("")) {
			int layoutWidth = 0;
			int layoutHeight = 0;
			int textLayoutWidth = 0;
			int textLayoutHeight = 0;
			if (mTextInBtn) { // 文本不能超出按钮区域
				layoutWidth = mButton.GetWidth();
				layoutHeight = mButton.GetHeight();
				textLayoutWidth = mButton.GetWidth();
				textLayoutHeight = mButton.GetHeight();

			} else { // 文本在按钮之外
				if (mTextWidth == -1)
					mTextWidth = mButton.GetWidth();
				if (mTextHeight == -1)
					mTextHeight = mButton.GetHeight();
				textLayoutWidth = mTextWidth;
				textLayoutHeight = mTextHeight;

				switch (mTextLayoutGravity) {
				case 0: // 左上
					layoutWidth = mButton.GetWidth() + mTextWidth;
					layoutHeight = mButton.GetHeight() + mTextHeight;
					mButton.setX(mTextWidth);
					mButton.setY(mTextHeight);
					break;
				case 1: // 左中
					layoutWidth = mButton.GetWidth() + mTextWidth;
					layoutHeight = mButton.GetHeight() > mTextHeight ? mButton
							.GetHeight() : mTextHeight;
					mButton.setX(mTextWidth);
					mButton.setY((layoutHeight - mButton.GetHeight()) / 2);
					mTextView.setY((layoutHeight - mTextHeight) / 2);
					break;
				case 2: // 左下
					layoutWidth = mButton.GetWidth() + mTextWidth;
					layoutHeight = mButton.GetHeight() + mTextHeight;
					mButton.setX(mTextWidth);
					mTextView.setY(mButton.GetHeight());
					break;
				case 3: // 中上
					layoutWidth = mButton.GetWidth() > mTextWidth ? mButton
							.GetWidth() : mTextWidth;
					layoutHeight = mButton.GetHeight() + mTextHeight;
					mButton.setX((layoutWidth - mButton.GetWidth()) / 2);
					mButton.setY(mTextHeight);
					mTextView.setX((layoutWidth - mTextWidth) / 2);
					break;
				case 4: // 正中
					layoutWidth = mButton.GetWidth() > mTextWidth ? mButton
							.GetWidth() : mTextWidth;
					layoutHeight = mButton.GetHeight() > mTextHeight ? mButton
							.GetHeight() : mTextHeight;
					mButton.setX((layoutWidth - mButton.GetWidth()) / 2);
					mButton.setY((layoutHeight - mButton.GetHeight()) / 2);
					mTextView.setX((layoutWidth - mTextWidth) / 2);
					mTextView.setY((layoutHeight - mTextHeight) / 2);
					break;
				case 5: // 中下
					layoutWidth = mButton.GetWidth() > mTextWidth ? mButton
							.GetWidth() : mTextWidth;
					layoutHeight = mButton.GetHeight() + mTextHeight;
					mButton.setX((layoutWidth - mButton.GetWidth()) / 2);
					mTextView.setX((layoutWidth - mTextWidth) / 2);
					mTextView.setY(mButton.GetHeight());
					break;
				case 6: // 右上
					layoutWidth = mButton.GetWidth() + mTextWidth;
					layoutHeight = mButton.GetHeight() + mTextHeight;
					mButton.setY(mTextHeight);
					mTextView.setX(mButton.GetWidth());
					break;
				case 7: // 右中
					layoutWidth = mButton.GetWidth() + mTextWidth;
					layoutHeight = mButton.GetHeight() > mTextHeight ? mButton
							.GetHeight() : mTextHeight;
					mButton.setY((layoutHeight - mButton.GetHeight()) / 2);
					mTextView.setX(mButton.GetWidth());
					mTextView.setY((layoutHeight - mTextHeight) / 2);
					break;
				case 8: // 右下
					layoutWidth = mButton.GetWidth() + mTextWidth;
					layoutHeight = mButton.GetHeight() + mTextHeight;
					mTextView.setX(mButton.GetWidth());
					mTextView.setY(mButton.GetHeight());
					break;
				}
			}
			mTextView.setText(mText);
			mTextView.setTextSize(mTextSize);
			mTextView.setTextColor(mTextColorUp);
			mTextView.setGravity(mTextGravity);
			if (mTextX != 0)
				mTextView.setX(mTextView.getX() + mTextX);
			if (mTextY != 0)
				mTextView.setY(mTextView.getY() + mTextY);
			mTextView.setMaxLines(mLineNum);
			mTextView.setEllipsize(TruncateAt.END);
			mTextView.setLayoutParams(new LayoutParams(textLayoutWidth,
					textLayoutHeight));

			if (this.getChildCount() < 2)
				this.addView(mTextView);

			mButton.SetLayoutSize(layoutWidth, layoutHeight);
		} else {
			mButton.SetLayoutSize(mButton.GetWidth(), mButton.GetHeight());
		}
	}

	// 初始化控件
	private void Init(Context context) {
		mContext = context;
		mButton = new HKButton(context);
		mTextView = new TextView(context);
		mButton.SetHKTouchListener(new OnHKTouchListener() {
			@Override
			public void OnHKTouchEvent(View view, TOUCH_ACTION action) {
				// TODO Auto-generated method stub
				if (action == TOUCH_ACTION.BTN_DOWN) {
					if (mText != null && !mText.equals(""))
						mTextView.setTextColor(mTextColorDown);
				} else if (action == TOUCH_ACTION.MOUSE_MOVE) {
					if (mText != null && !mText.equals(""))
						mTextView.setTextColor(mTextColorUp);
				} else if (action == TOUCH_ACTION.BTN_UP) {
					if (mText != null && !mText.equals(""))
						mTextView.setTextColor(mTextColorUp);
				}
				// 传递消息给父级
				if (mTouchedListener != null)
					mTouchedListener.OnHKTouchEvent(HKButtonText.this, action);
			}
		});
	}

	// 将Gravity值转换为系统可识别值
	private int ConvertGravity(int localValue) {
		int gravity = Gravity.CENTER;
		switch (localValue) {
		case 0: // 左上
			gravity = Gravity.LEFT;
			break;
		case 1: // 左中
			gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
			break;
		case 2: // 左下
			gravity = Gravity.BOTTOM;
			break;
		case 3: // 中上
			gravity = Gravity.CENTER_HORIZONTAL;
			break;
		case 4: // 正中
			gravity = Gravity.CENTER;
			break;
		case 5: // 中下
			gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
			break;
		case 6: // 右上
			gravity = Gravity.RIGHT;
			break;
		case 7: // 右中
			gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
			break;
		case 8: // 右下
			gravity = Gravity.RIGHT | Gravity.BOTTOM;
			break;
		}
		return gravity;
	}

	// 释放图片资源
	public void ReleaseBitmap() {
		mButton.ReleaseBitmap();
	}
}
