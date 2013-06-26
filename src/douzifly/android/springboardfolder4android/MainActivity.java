package douzifly.android.springboardfolder4android;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	SBFolderLayout1 mFolderLayout;
	
	GridView mGridView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFolderLayout = (SBFolderLayout1) findViewById(R.id.sbFolder);
		mFolderLayout.setBackgroundColor(Color.GRAY);
	
		View content = getLayoutInflater().inflate(R.layout.grid_content, null);
		mGridView = (GridView) content.findViewById(R.id.grid);
		mGridView.setAdapter(new GridAdapter());
		mGridView.setOnItemClickListener(new GridItemClickListener());
		
		mFolderLayout.setContentView(content);
		
		View cover = getLayoutInflater().inflate(R.layout.cover, null);
		cover.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 180));
		
		mFolderLayout.setFolderView(cover);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(mFolderLayout.isShowCoverView()){
				mFolderLayout.hideCoverView();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	class GridItemClickListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			mFolderLayout.showFolderView(arg1.getBottom());
		}
	}
	
	class GridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null){
				ImageView img = new ImageView(MainActivity.this);
				convertView = img;
				img.setPadding(20, 20, 20, 20);
				img.setImageResource(R.drawable.ic_launcher);
			}
			return convertView;
		}
	}

}
