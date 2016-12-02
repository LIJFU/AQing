package com.xiaoyuan54.child.edu.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.xiaoyuan54.child.edu.app.adapter.SearchAdapter;
import com.xiaoyuan54.child.edu.app.api.remote.HttpClientApi;
import com.xiaoyuan54.child.edu.app.base.BaseListFragment;
import com.xiaoyuan54.child.edu.app.bean.SearchList;
import com.xiaoyuan54.child.edu.app.bean.SearchResult;
import com.xiaoyuan54.child.edu.app.ui.empty.EmptyLayout;
import com.xiaoyuan54.child.edu.app.util.UIHelper;
import com.xiaoyuan54.child.edu.app.util.XmlUtils;

import java.io.InputStream;
import java.io.Serializable;

public class SearchFragment extends BaseListFragment<SearchResult> {

    public static final String TAG = SearchFragment.class.getSimpleName();
    private static final String CACHE_KEY_PREFIX = "search_list_";
    public static final String BUNDLE_KEY_SEARCH_CATALOG = "BUNDLE_KEY_SEARCH_CATALOG";

    private String mSearchCatalog;
    private String mSearch;
    private boolean mRequestDataIfCreated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mSearchCatalog = args.getString(BUNDLE_KEY_SEARCH_CATALOG);
        }
        int mode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
        getActivity().getWindow().setSoftInputMode(mode);
    }

    public void search(String search) {
        mSearch = search;
        if (mErrorLayout != null) {
            mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
            mState = STATE_REFRESH;
            requestData(true);
        } else {
            mRequestDataIfCreated = true;
        }
    }

    @Override
    protected boolean requestDataIfViewCreated() {
        return mRequestDataIfCreated;
    }

    @Override
    protected SearchAdapter getListAdapter() {
        return new SearchAdapter();
    }

    @Override
    protected String getCacheKeyPrefix() {
        return CACHE_KEY_PREFIX + mSearchCatalog + mSearch;
    }

    @Override
    protected SearchList parseList(InputStream is) throws Exception {
        SearchList list = XmlUtils.toBean(SearchList.class, is);
        return list;
    }

    @Override
    protected SearchList readList(Serializable seri) {
        return ((SearchList) seri);
    }

    @Override
    protected void sendRequestData() {
        HttpClientApi.getSearchList(mSearchCatalog, mSearch, mCurrentPage, mHandler);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        SearchResult res = mAdapter.getItem(position);
        if (res != null) {
            if (res.getType().equalsIgnoreCase(SearchList.CATALOG_SOFTWARE)) {
                UIHelper.showSoftwareDetailById(getActivity(), res.getId());
            } else {
                UIHelper.showUrlRedirect(getActivity(), res.getUrl());
            }
        }
    }
}
