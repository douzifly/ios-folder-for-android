/**
 * Author:XiaoyuanLiu
 * Date: Jun 26, 2013
 * 深圳快播科技
 */
package douzifly.android.springboardfolder4android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author XiaoyuanLiu
 *
 */
public class MockView extends View{

	// TODO use weakreference
	private View mTargetView;
	
	private int mClipYOffset;
	
	/**
	 * @param context
	 * @param attrs
	 */
	public MockView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	
	public void setTargetView(View v){
		mTargetView = v;
	} 
	
	Matrix m = new Matrix();
	@Override
	public void draw(Canvas canvas) {
		if(mTargetView != null){
			canvas.save();
			m.reset();
			m.setTranslate(0, -mClipYOffset);
			canvas.concat(m);
			mTargetView.draw(canvas);
			canvas.restore();
		}
	}
	
	public void setClipYOffset(int clipY){
		mClipYOffset = clipY;
	}
	
}
