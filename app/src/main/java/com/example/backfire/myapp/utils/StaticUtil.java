package com.example.backfire.myapp.utils;

/**
 * Created by backfire on 2017/10/2.
 */

public class StaticUtil {
    /**
     * http://www.shuwu.mobi/tag/azw3/page/3
     * <p>
     * http://www.shuwu.mobi/category/jpwlxs/lsjk
     * http://www.shuwu.mobi/category/jpwlxs/lsjk/page/2
     * <p>
     * http://www.shuwu.mobi/category/duokan
     * http://www.shuwu.mobi/category/duokan/page/2
     */
    public StaticUtil() {
    }

    //
    public static final int DATE_MAX_NUM = 17;
    public static final String ERROR_HTML = "<html><head><title>First parse</title></head>\"\n" +
            "  + \"<body><p>Error.</p></body></html>";

    public final static String BOOK_DETAIL_URL = "bookUrl";
    public final static String TEXT_DETAIL_URL = "textUrl";
    public final static String TEXT_TITLE = "textTitle";

    public final static int FICTION_PAGE_MAX = 10;

    public static final String BOOK_BASE_URL = "http://www.shuwu.mobi";
    public static final String BOOK_CATEGORY = "category";
    public static final String BOOK_TAG = "tag";
    public static final String BOOK_PAGE = "page";
    public static final String BOOK_JPWLXS = "jpwlxs";

    public static final String FICTION_BASE_URL = "http://www.wcsfa.com/";


    //书籍搜索
    public static final int REQUEST_CODE_SEARCH = 1;
    public static final int RESULT_CODE_SEARCH = 2;
    public static final String SEARCH_BOOK_NAME = "SEARCH_BOOK_NAME";


}
