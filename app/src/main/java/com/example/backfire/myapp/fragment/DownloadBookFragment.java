package com.example.backfire.myapp.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.adapter.BookStoreAdapter;
import com.example.backfire.myapp.utils.DensityUtil;
import com.example.backfire.myapp.utils.FileUtil;
import com.example.backfire.myapp.utils.StaticUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by backfire on 2017/11/24.
 */

public class DownloadBookFragment extends BaseFragment{
    private View view;
    @BindView(R.id.recycle_local)
    RecyclerView recyclerLocal;

    private BookStoreAdapter bookStoreAdater;
    private LinearLayoutManager mLinearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment_book_download, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initalData();
        queryLocalFile();
    }

    private void queryLocalFile() {
        String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };
        //查询后缀为epub格式的文件
        Cursor cursor = getContext().getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " not like ? and (" + MediaStore.Files.FileColumns.DATA + " like ? )",
                new String[]{"%" + FileUtil.createRootPath(getContext()) + "%",
                        "%" + StaticUtil.SUFFIX_EPUB}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
            int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);

            do {
                String path = cursor.getString(dataindex);
                int dot = path.lastIndexOf("/");
                String name = path.substring(dot + 1);
                if (name.lastIndexOf(".") > 0)
                    name = name.substring(0, name.lastIndexOf("."));
                Log.i("bookname",name);

            } while (cursor.moveToNext());

            cursor.close();

        } else {

        }
    }

    private void initalData() {
        int mImageWidth = (int) (DensityUtil.getDeviceInfo(getContext())[0] / (3.4f));
        int mImageHeigh = (int) (1.6 * mImageWidth);
        bookStoreAdater = new BookStoreAdapter(getContext(),mImageWidth,mImageHeigh);
        mLinearLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerLocal.setLayoutManager(mLinearLayoutManager);
        recyclerLocal.setAdapter(bookStoreAdater);
        bookStoreAdater.setMyOnItemClickListener(new BookStoreAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Open book or film ?
            }
        });
    }

}
