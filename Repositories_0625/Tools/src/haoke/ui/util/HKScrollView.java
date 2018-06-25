package haoke.ui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

public class HKScrollView extends ScrollView {
	
	// ------------------------------外部接口 start------------------------------
	/**
	 * 获取滚动条滑动范围（PS：单位非像素）
	 * 
	 * @return int
	 */
	public int GetRange() {
		return mRange;
	}

	/**
	 * 滚动条所表示的单页范围（PS：单位非像素）
	 * 
	 * @return int
	 */
	public int GetExtent() {
		return mExtent;
	}

	/**
	 * 滚动条当前位置（PS：单位非像素）
	 * 
	 * @return int
	 */
	public int GetOffset() {
		return mOffset;
	}

	/**
	 * 设置滚动条变化监听函数
	 * 
	 * @param OnHKTouchListener
	 */
	public void SetHKScrollViewBarListener(OnHKScrollViewBarListener listener) {
		mScrollBarViewListener = listener;
	}

	// 滚动条变化回调函数
	public interface OnHKScrollViewBarListener {
		abstract void OnHKScrollViewBarListener(View view);
	}
	// ------------------------------外部接口 end------------------------------

	private OnHKScrollViewBarListener mScrollBarViewListener = null;
	private int mRange = 0; // 滚动条滑动范围（PS：单位非像素）
	private int mExtent = 0; // 滚动条所表示的单页范围（PS：单位非像素）
	private int mOffset = 0; // 滚动条当前位置（PS：单位非像素）

	public HKScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public HKScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected int computeVerticalScrollRange() {
		return 0; // 去除原生scrollbar
	}

	@Override
	protected int computeVerticalScrollExtent() {
		return 0; // 去除原生scrollbar
	}

	@Override
	protected int computeVerticalScrollOffset() {
		mRange = super.computeVerticalScrollRange();
		mExtent = super.computeVerticalScrollExtent();
		mOffset = super.computeVerticalScrollOffset();
		if (mScrollBarViewListener != null)
			mScrollBarViewListener.OnHKScrollViewBarListener(this);
		return 0; // 去除原生scrollbar
	}
}
