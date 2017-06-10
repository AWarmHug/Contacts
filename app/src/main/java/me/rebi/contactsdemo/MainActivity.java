package me.rebi.contactsdemo;

import android.Manifest;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import me.rebi.contactsdemo.bean.Contact;
import me.rebi.contactsdemo.widget.LView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private AsyncQueryHandler asyncQueryHandler; // 异步查询数据库类对象
    private List<Contact> contentList;
    private LView lView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    private void initView() {
        lView= (LView) this.findViewById(R.id.lView);

    }


    private AlertDialog alertDialog;
    private final int reqCode = 0x110;

    private void initData() {
        contentList = new ArrayList<>();

        check();

    }

    private void check() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    alertDialog = new AlertDialog.Builder(this)
                            .setMessage("需要赋予查看通讯录权限!")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, reqCode);
                                    }
                                }
                            })
                            .setCancelable(false)
                            .create();
                    alertDialog.show();
                }
            } else {
                queryPhones();
            }
        } else {
            queryPhones();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == reqCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许
                queryPhones();
            } else {
                alertDialog = new AlertDialog.Builder(this)
                        .setMessage("您已取消权限,程序将退出!")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).setPositiveButton("重新申请", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, reqCode);
                                }
                            }
                        })
                        .setCancelable(false)
                        .create();
                alertDialog.show();
            }
        }

    }

    private void queryPhones() {

        asyncQueryHandler = new MyAsyncQueryHandler(getContentResolver());

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        // 查询的字段
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY
        };

        asyncQueryHandler.startQuery(0, null, uri, projection, null, null, "sort_key COLLATE LOCALIZED asc");
    }

    private class MyAsyncQueryHandler extends AsyncQueryHandler {

        MyAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            super.onQueryComplete(token, cookie, cursor);
            if (cursor != null && cursor.getCount() != 0) {
                contentList.clear();
                cursor.moveToFirst();
                do {
                    String _id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String PinYin = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY));
                    Contact contact = new Contact(_id, PinYin, name, phoneNum);
                    contentList.add(contact);
                } while (cursor.moveToNext());
                cursor.close();
            }
            lView.setContactsList(contentList);

        }
    }


}
