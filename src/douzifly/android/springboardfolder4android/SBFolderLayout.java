/**
 * Author:douzifly@gmail.com
 * Date: Jun 25, 2013
 * 
 */
package douzifly.android.springboardfolder4android;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

/**
 * 
 * SBFolderLayout is iOS SpringBoard folder implementation for Android
 * @author douzifly 
 *
 */
public class SBFolderLayout extends FrameLayout{

	private View mCoverView;
	private View mContentView;
	
	/**
	 * @param context
	 * @param attrs
	 */
	public SBFolderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setContentView(View contentView){
		mContentView = contentView;
		if(contentView.getLayoutParams() == null){
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			contentView.setLayoutParams(lp);
		}
		addView(contentView);
	}
	
	public void setCoverView(View coverView){
		mCoverView = coverView;
		coverView.setVisibility(View.GONE);
		addView(coverView);
	}
	
	public void showCoverView(int x, int y){
		if(mCoverView == null){
			return;
		}
		
		mCoverView.setVisibility(View.VISIBLE);
		// set cover view heigh
		int h = getHeight() - y;
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, h);
		lp.topMargin = y;
		mCoverView.setLayoutParams(lp);
		
		mContentView.setVisibility(View.VISIBLE);
	}
	
	public void hideCoverView(){
		if(mCoverView == null) return;
		mCoverView.setVisibility(View.GONE);
	}
	
	public boolean isShowCoverView(){
		return mCoverView == null ? false : mCoverView.getVisibility() == View.VISIBLE;
	}
	

}
