/**
 * Author:douzifly@gmail.com
 * Date: Jun 25, 2013
 * 
 */
package douzifly.android.springboardfolder4android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * 
 * SBFolderLayout is iOS SpringBoard folder implementation for Android
 * 
 * @author douzifly 
 *
 */
public class SBFolderLayout extends FrameLayout{
	
	final static String TAG = "SBFolderLayout" ;

	private View mCoverView;
	private View mContentView;
	
	/**
	 * view which contains cover view and mock content view
	 */
	private FrameLayout mCoverViewLayout;
	
	private FrameLayout mCoverContainer; 
	private View mMockView;
	
	/**
	 * @param context
	 * @param attrs
	 */
	public SBFolderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private void installConverViewContainer(final View contentView){
		mCoverViewLayout = new FrameLayout(getContext());
//		mCoverViewLayout.setOrientation(LinearLayout.VERTICAL);
		mCoverViewLayout.setVisibility(View.GONE);
		mCoverViewLayout.setBackgroundColor(Color.GRAY);
		
		mCoverContainer = new FrameLayout(getContext());
		mCoverContainer.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mCoverViewLayout.addView(mCoverContainer);
		
		mMockView = new FrameLayout(getContext()){
			@Override
			public void draw(Canvas canvas) {
				contentView.draw(canvas);
			}
			
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				hideCoverView();
				return true;
			}
		};
		mMockView.setBackgroundColor(Color.TRANSPARENT);
		
		mCoverViewLayout.addView(mMockView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(mCoverViewLayout);
	}
	
	public void setContentView(View contentView){
		mContentView = contentView;
		if(contentView.getLayoutParams() == null){
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			contentView.setLayoutParams(lp);
		}
		addView(contentView);
		installConverViewContainer(contentView);
	}
	
	public void setCoverView(View coverView){
		if(mContentView == null){
			throw new IllegalStateException("you must set content view first");
		}
		mCoverView = coverView;
		mCoverContainer.addView(coverView);
		coverView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
	}
	
	
	Animation mAnimDown;
	Animation mAnimUp;
	
	
	// mockView 盖住coverContainer, 然后往下滑动，呈现coverContainer
	public void showCoverView(int x, int y){
		if(mCoverView == null){
			return;
		}
		
		if(isShowCoverView()){
			Log.d(TAG, "showing");
			return;
		}
		
		mCoverViewLayout.setVisibility(View.VISIBLE);
		// set cover view heigh
		int h = getHeight() - y;
		Log.d(TAG, "showCoverView y:" + y + " totalH:" + getHeight() + " cover height:" + h);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, h);
		lp.topMargin = y;
		mCoverViewLayout.setLayoutParams(lp);
		mCoverViewLayout.setVisibility(View.VISIBLE);
//		mCoverContainer.setVisibility(View.GONE);
		if(mAnimDown == null){
			mAnimDown = new TranslateAnimation(0, 0, 0, mCoverView.getHeight());
			mAnimDown.setDuration(1000);
			mAnimDown.setInterpolator(new AccelerateInterpolator());
			mAnimDown.setFillAfter(true);
			mAnimDown.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation arg0) {
				}
				
				@Override
				public void onAnimationRepeat(Animation arg0) {
				}
				
				@Override
				public void onAnimationEnd(Animation arg0) {
				}
			});
		}
		
		mMockView.startAnimation(mAnimDown);
		
	}
	
	public void hideCoverView(){
		if(mCoverView == null) return;
		mCoverViewLayout.setVisibility(View.GONE);
	}
	
	public boolean isShowCoverView(){
		return mCoverViewLayout == null ? false : mCoverViewLayout.getVisibility() == View.VISIBLE;
	}
	
}
