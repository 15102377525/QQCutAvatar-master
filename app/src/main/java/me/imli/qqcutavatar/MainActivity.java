package me.imli.qqcutavatar;

import me.imli.qqcutavatar.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private final int REQUEST_CODE = 1;

    private ImageView mIvAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        mIvAvatar.setImageResource(R.drawable.ic_launcher);
        mIvAvatar.setOnClickListener(clickAvatar());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 159) {
            mIvAvatar.setImageBitmap(CutAvatarActivity.bitmap);
            return;
        }


        try {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            // 相册的路径
            String picturePath = c.getString(columnIndex);
            Intent intent = new Intent(MainActivity.this, CutAvatarActivity.class);
            intent.putExtra("picturePath", picturePath);
            Log.e("image_path", "-------" + picturePath);
            startActivityForResult(intent, REQUEST_CODE);
            c.close();
        } catch (NullPointerException e) {

        }
    }

    private View.OnClickListener clickAvatar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用系统相册
                Intent picture = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, 2);
            }
        };
    }

}