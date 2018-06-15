package shaoqi.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class ScrollActivity extends AppCompatActivity implements ImageBannerFrameLayout.FrameLayoutListener{

    private static final String TAG = "ScrollActivity";
//    private ImageBannerFrameLayout frameLayout;
//    private int[] ids = new int[]{R.drawable.tb_01,R.drawable.tb_02,R.drawable.tb_03,R.drawable.tb_04};
//    private static ScrollActivity mainActivity;
//
//    public ScrollActivity() {
//        mainActivity = this;
//    }
//
//    public static ScrollActivity getMainActivity() {
//        return mainActivity;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scroll);
//        frameLayout = (ImageBannerFrameLayout) findViewById(R.id.img_group);
//        List<Bitmap> list = new ArrayList<>();
//        for(int i=0;i<ids.length;i++){
//            //得到资源图片
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),ids[i]);
//            list.add(bitmap);
//        }
//        //传递资源图片对象过去
//        frameLayout.addBitMaps(list);
//        //给主函数用户点击注册监听器
//        frameLayout.setFrameLayoutListener(this);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void clickImageIndex(int pos) {
        Toast.makeText(this,"position:"+pos,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }
}
