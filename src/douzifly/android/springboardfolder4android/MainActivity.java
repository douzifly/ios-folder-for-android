package douzifly.android.springboardfolder4android;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	SBFolderLayout mFolderLayout;
	
	GridView mGridView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFolderLayout = (SBFolderLayout) findViewById(R.id.sbFolder);
		mFolderLayout.setBackgroundColor(Color.GRAY);
	
		View content = getLayoutInflater().inflate(R.layout.grid_content, null);
		mGridView = (GridView) content.findViewById(R.id.grid);
		mGridView.setAdapter(new GridAdapter());
		mGridView.setOnItemClickListener(new GridItemClickListener());
		
		mFolderLayout.setContentView(content);
		
		View folder = getLayoutInflater().inflate(R.layout.folder, null);
		folder.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 200));
		Button btnClose = (Button)folder.findViewById(R.id.btn_close);
		btnClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mFolderLayout.hideFolderView();
			}
		});
		
		mFolderLayout.setFolderView(folder);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if(item.getItemId() == R.id.action_about){
			Toast.makeText(this, "douzifly@gmail.com", Toast.LENGTH_SHORT).show();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(mFolderLayout.isShowFolderView()){
				mFolderLayout.hideFolderView();
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
			return 30;
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
