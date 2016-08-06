package com.konifar.annict.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konifar.annict.databinding.FragmentSearchBinding;
import com.konifar.annict.model.SearchType;
import com.konifar.annict.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchFragment extends BaseFragment {

    private static final String ARG_AUTH_CODE = "auth_code";

    public static final String TAG = SearchFragment.class.getSimpleName();

    @Inject
    SearchViewModel viewModel;

    private SearchPagerAdapter adapter;
    private FragmentSearchBinding binding;

    private String authCode;

    public static SearchFragment newInstance(@NonNull String authCode) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_AUTH_CODE, authCode);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        initTab();

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            authCode = getArguments().getString(ARG_AUTH_CODE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    private void initTab() {
        adapter = new SearchPagerAdapter(getFragmentManager());

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.addOnTabSelectedListener(new CustomViewPagerOnTabSelectedListener(binding.viewPager));
    }

    private class SearchPagerAdapter extends FragmentStatePagerAdapter {

        private final List<String> titles = new ArrayList<>();
        private final List<TabPage> pages = new ArrayList<>();

        public SearchPagerAdapter(FragmentManager fm) {
            super(fm);
            createPages();
        }

        private void createPages() {
            titles.add(getString(SearchType.SEASON.nameResId));
            pages.add(SearchSeasonFragment.newInstance(authCode));
            titles.add(getString(SearchType.POPULAR.nameResId));
            pages.add(SearchPopularFragment.newInstance(authCode));
        }

        @Override
        @Nullable
        public Fragment getItem(int position) {
            if (position >= 0 && position < pages.size()) {
                return pages.get(position).getFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private class CustomViewPagerOnTabSelectedListener extends TabLayout.ViewPagerOnTabSelectedListener {

        public CustomViewPagerOnTabSelectedListener(ViewPager viewPager) {
            super(viewPager);
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            super.onTabReselected(tab);
            TabPage page = (TabPage) adapter.getItem(tab.getPosition());
            if (page != null) page.scrollToTop();
        }
    }

}
