package com.example.backfire.myapp.presenter.implPresenter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.presenter.implView.ILocalBookFragment;
import com.example.backfire.myapp.utils.FileUtil;
import com.example.backfire.myapp.utils.StaticUtil;

import java.io.File;
import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by backfire on 2018/6/18.
 */

public class LocalBookPresenterImlp extends BasePresenterImpl {
    private ArrayList<BookBean> localEpubBooks ;
    private Context context;
    private ILocalBookFragment iLocalBookFragment;

    public LocalBookPresenterImlp(Context context, ILocalBookFragment iLocalBookFragment){
        this.context = context;
        this.iLocalBookFragment = iLocalBookFragment;
    }

    /**
     * 传入本地epub文件地址
     * @param path
     */
    private void getLocalEpubBooks(String path){
        File localBooksFile = new File(path);
        if(localBooksFile.exists() && localBooksFile.isDirectory()){
            for(File file : localBooksFile.listFiles()){
                String bookPath = file.getAbsolutePath();
                String bookName = bookPath.substring(bookPath.lastIndexOf("/"), bookPath.lastIndexOf("."));
                BookBean bookBean = new BookBean(bookName,bookPath,"");
                localEpubBooks.add(bookBean);
            }
        }
    }


    /**
     * 查找手机里的epub文件，并存进待处理文件夹
     * 耗时方法
     */
    public void copyEpubBooks() {
        Subscription subscription = Observable.create(new rx.Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                        MediaStore.Files.FileColumns.DATA,
                        MediaStore.Files.FileColumns.SIZE
                };
                //查询后缀为epub格式的文件
                Cursor cursor = context.getContentResolver().query(
                        Uri.parse("content://media/external/file"),
                        projection,
                        MediaStore.Files.FileColumns.DATA + " not like ? and (" + MediaStore.Files.FileColumns.DATA + " like ? )",
                        new String[]{"%" + FileUtil.getLocalBooksFilePath() + "%",
                                "%" + StaticUtil.SUFFIX_EPUB}, null);

                if (cursor != null && cursor.moveToFirst()) {
                    int idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
                    int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
                    int sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);

                    do {
                        String path = cursor.getString(dataindex);
                        int dot = path.lastIndexOf("/");
                        String name = path.substring(dot + 1);
                        boolean isCopy = FileUtil.copyFile(new File(path),name);
                    } while (cursor.moveToNext());

                    cursor.close();
                    subscriber.onNext(true);

                } else {
                }
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean s) {
                        iLocalBookFragment.updataList(s);
                    }
                });

        addSubscription(subscription);
    }
}
