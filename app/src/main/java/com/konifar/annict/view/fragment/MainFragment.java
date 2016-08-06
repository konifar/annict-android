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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.annimon.stream.Stream;
import com.konifar.annict.R;
import com.konifar.annict.databinding.FragmentMainBinding;
import com.konifar.annict.model.Status;
import com.konifar.annict.viewmodel.MainViewModel;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MainFragment extends BaseFragment {

  public static final String TAG = MainFragment.class.getSimpleName();

  private static final String ARG_AUTH_CODE = "auth_code";

  @Inject MainViewModel viewModel;

  private MainPagerAdapter adapter;

  private FragmentMainBinding binding;

  private String authCode;

  public static MainFragment newInstance(@NonNull String authCode) {
    MainFragment fragment = new MainFragment();
    Bundle bundle = new Bundle();
    bundle.putString(ARG_AUTH_CODE, authCode);
    fragment.setArguments(bundle);
    return fragment;
  }

  public static MainFragment newInstance() {
    return new MainFragment();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentMainBinding.inflate(inflater, container, false);
    setHasOptionsMenu(true);

    initTab();

    return binding.getRoot();
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      authCode = getArguments().getString(ARG_AUTH_CODE);
    }
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    getComponent().inject(this);
  }

  private void initTab() {
    adapter = new MainPagerAdapter(getFragmentManager());

    binding.viewPager.setAdapter(adapter);
    binding.viewPager.setOffscreenPageLimit(3);
    binding.tabLayout.setupWithViewPager(binding.viewPager);
    binding.tabLayout.addOnTabSelectedListener(
        new CustomViewPagerOnTabSelectedListener(binding.viewPager));
  }

  @Override public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
    menuInflater.inflate(R.menu.menu_main, menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.item_search:
        viewModel.onClickSearchMenu();
        break;
      case R.id.item_settings:
        viewModel.onClickSettingMenu();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final List<String> titles = new ArrayList<>();
    private final List<TabPage> pages = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm) {
      super(fm);
      createPages();
    }

    private void createPages() {
      titles.add(getString(R.string.my_programs_title));
      pages.add(MyProgramsFragment.newInstance(authCode));

      Stream.of(Status.WATCHING, Status.WANNA_WATCH, Status.WATCHED).forEach(status -> {
        titles.add(getString(status.stringRes));
        pages.add(MyWorksFragment.newInstance(authCode, status));
      });
    }

    @Override @Nullable public Fragment getItem(int position) {
      if (position >= 0 && position < pages.size()) {
        return pages.get(position).getFragment();
      }
      return null;
    }

    @Override public int getCount() {
      return pages.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return titles.get(position);
    }
  }

  private class CustomViewPagerOnTabSelectedListener
      extends TabLayout.ViewPagerOnTabSelectedListener {

    public CustomViewPagerOnTabSelectedListener(ViewPager viewPager) {
      super(viewPager);
    }

    @Override public void onTabReselected(TabLayout.Tab tab) {
      super.onTabReselected(tab);
      TabPage page = (TabPage) adapter.getItem(tab.getPosition());
      if (page != null) page.scrollToTop();
    }
  }
}
