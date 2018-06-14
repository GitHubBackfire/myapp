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
import android.widget.Toast;

import com.example.backfire.myapp.R;
import com.example.backfire.myapp.adapter.BookStoreAdapter;
import com.example.backfire.myapp.bean.BookBean;
import com.example.backfire.myapp.utils.DensityUtil;
import com.example.backfire.myapp.utils.FileUtil;
import com.example.backfire.myapp.utils.StaticUtil;
import com.example.backfire.myapp.utils.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;

/**
 * just test open epub book...
 * Created by backfire on 2018/6/12
 *
 */

public class LocalBookFragment extends BaseFragment{
    private View view;
    @BindView(R.id.recycle_local)
    RecyclerView recyclerLocal;

    private BookStoreAdapter bookStoreAdater;
    private LinearLayoutManager mLinearLayoutManager;

    private ArrayList<BookBean> epubBooks;


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
        createLocalEpubBookFile();
        //queryLocalFile();
        getLocalEpubBooks();

    }

    private void createLocalEpubBookFile() {
        boolean isCreateLocalFile = FileUtil.createLocalFile(getContext(), StaticUtil.LOCAL_BOOKS_FILE_NAME);
        Log.i("iscreate",isCreateLocalFile+",");
    }


    private void getLocalEpubBooks(){
        epubBooks = new ArrayList<>();
        String[] projection = new String[]{MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };
        //查询本地epub文件
        Cursor cursor = getContext().getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " not like ? and (" + MediaStore.Files.FileColumns.DATA + " like ? )",
                new String[]{"%" + FileUtil.getLocalBooksFilePath() + "%",
                        "%" + StaticUtil.SUFFIX_EPUB}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            do {
                String path = cursor.getString(dataindex);
                int dotStart = path.lastIndexOf("/");
                int dotEnd = path.lastIndexOf(".");
                String name = path.substring(dotStart + 1,dotEnd);
                BookBean bookBean = new BookBean(name,path,"");
                epubBooks.add(bookBean);
                //show local epub books in ui.
            } while (cursor.moveToNext());

            cursor.close();

        } else {

        }
        if(epubBooks.size() >= 0){
            bookStoreAdater.addItems(epubBooks);
        }
    }


    /**
     * 耗时方法
     */
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
                new String[]{"%" + FileUtil.getLocalBooksFilePath() + "%",
                        "%" + StaticUtil.SUFFIX_EPUB}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idindex = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
            int dataindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
            int sizeindex = cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE);

            do {
                String path = cursor.getString(dataindex);
                int dot = path.lastIndexOf("/");
                int dotEnd = path.lastIndexOf(".");
                String name = path.substring(dot + 1,dotEnd)+".zip";
                boolean isCopy = FileUtil.copyFile(new File(path),name);
                Log.i("isCopy",isCopy+",");
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
                //unzipEpubBook(epubBooks.get(position).getUrl(),epubBooks.get(position).getTitle());
                openEpubBook(epubBooks.get(position).getUrl());
            }
        });
    }

    private void openEpubBook(String path){
        Log.i("path1",path);
        File file = new File(path);
        try {
            EpubReader epubReader = new EpubReader();
            InputStream  inputStream = new FileInputStream(file);
            Book book = epubReader.readEpub(inputStream);
            Spine spine = book.getSpine();

            List<SpineReference> spineReferences = spine.getSpineReferences();
            if(spineReferences != null && spineReferences.size() > 0){
               /* Resource resource = spineReferences.get(1).getResource();
                byte[] data = resource.getData();
                String strHtml = StringUtil.bytes2Hex(data);
                Log.i("spine",resource.getData().toString());
                Log.i("spine2",strHtml);*/
               for(SpineReference spineReference:spineReferences){
                   byte[] data = spineReference.getResource().getData();
                   String strHtml = StringUtil.bytes2Hex(data);
               }
            }

            List<Resource> contents = book.getContents();
            if (contents != null && contents.size() > 0) {
                try {
                    for(Resource resource:contents){
                        InputStream inputStreamContent = resource.getInputStream();
                        String str = StringUtil.convertStreamToString(inputStreamContent);
                        Log.i("content",str);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
            }

            Toast.makeText(getContext(),book.getTitle(),Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void unzipEpubBook(String path,String name){
        String targetPath = FileUtil.getLocalBooksFilePath()+"/"+name;
        FileUtil.singleZip(path,targetPath);
        parseEpubBook(targetPath);
    }


    private void getSomething(List<Resource> contents){
    }

    private void parseEpubBook(String path){

    }




}
