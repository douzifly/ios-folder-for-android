/**
 * Author:douzifly@gmail.com
 * Date: Jun 25, 2013
 * 
 */
package douzifly.android.springboardfolder4android;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

/**
 * 
 * SBFolderLayout is iOS SpringBoard folder implementation for Android
 * 
 * @author douzifly 
 *
 */
public class SBFolderLayout1 extends FrameLayout{
	
	final static String TAG = "SBFolderLayout" ;

	private View mFolderView;
	private View mContentView;
	
	/**
	 * view which contains cover view and mock content view
	 */
	private FrameLayout mTopContainer;
	private FrameLayout mFolderContainer; 
	private FrameLayout mContentContainer;
	private MockView mMockView;
	
	/**
	 * @param context
	 * @param attrs
	 */
	public SBFolderLayout1(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater infl = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		infl.inflate(R.layout.sbf_layout, this);
		View sbfLayout = findViewById(R.id.sbf_layout);
		mTopContainer = (FrameLayout)sbfLayout.findViewById(R.id.sbf_top_container);
		mFolderContainer = (FrameLayout)sbfLayout.findViewById(R.id.sbf_folder_container);
		mContentContainer = (FrameLayout) sbfLayout.findViewById(R.id.sbf_content_container);
		mMockView = (MockView)sbfLayout.findViewById(R.id.sbf_mock_view);
		mTopContainer.setVisibility(View.GONE);
		
		mMockView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				// close folder when touch on mock view 
				if(arg1.getY() > mFolderHeight){
					hideCoverView();
					return true;
				}
				return false;
			}
		});
	}
	
	
	/**
	 * set content view , notice that content view must have background
	 * @param contentView
	 */
	public void setContentView(View contentView){
		mContentView = contentView;
		if(contentView.getLayoutParams() == null){
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			contentView.setLayoutParams(lp);
		}
		mMockView.setTargetView(contentView);
		mContentContainer.addView(contentView);
	}
	
	public void setFolderView(View folderView){
		mFolderView = folderView;
		mFolderContainer.addView(folderView);
		folderView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
	}
	
	
	Animation mAnimDown;
	Animation mAnimUp;
	
	int mFolderHeight = 0;
	// mockView slide down then folder view disappeared.
	public void showFolderView(int y){
		if(mFolderView == null){
			return;
		}
		
		if(isShowCoverView()){
			Log.d(TAG, "showing");
			return;
		}
		
		// measure height of folder view
		mFolderView.measure(getWidth(), getHeight());
		mFolderHeight = mFolderView.getMeasuredHeight();
		Log.d(TAG, "folderHeight:" + mFolderHeight);
		
		// set height of top container
		LayoutParams lp = (FrameLayout.LayoutParams)mTopContainer.getLayoutParams();
		lp.topMargin = y;
		mTopContainer.setLayoutParams(lp);
		
		// set mock view clip offset
		mMockView.setClipYOffset(y);
		
		mTopContainer.setVisibility(View.VISIBLE);
		if(mAnimDown == null){
			mAnimDown = new TranslateAnimation(0, 0, 0, mFolderHeight);
			mAnimDown.setDuration(400);
			mAnimDown.setInterpolator(new AccelerateInterpolator());
			mAnimDown.setFillAfter(true);
		}
		
		mMockView.startAnimation(mAnimDown);
		
	}
	
	public void hideCoverView(){
		if(mFolderView == null) return;
		
		if(mAnimUp == null){
			mAnimUp = new TranslateAnimation(0, 0, mFolderHeight, 0);
			mAnimUp.setDuration(400);
			mAnimUp.setInterpolator(new DecelerateInterpolator());
			mAnimUp.setFillAfter(true);
			mAnimUp.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation arg0) {
				}
				
				@Override
				public void onAnimationRepeat(Animation arg0) {
				}
				
				@Override
				public void onAnimationEnd(Animation arg0) {
					// hide top container when animation finished
					mTopContainer.setVisibility(View.GONE);
				}
			});
		}
		
		mMockView.startAnimation(mAnimUp);
	}
	
	public boolean isShowCoverView(){
		return mTopContainer == null ? false : mTopContainer.getVisibility() == View.VISIBLE;
	}
	
}
