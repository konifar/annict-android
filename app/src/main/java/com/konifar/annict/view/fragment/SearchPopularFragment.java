package com.konifar.annict.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.annimon.stream.Stream;
import com.konifar.annict.R;
import com.konifar.annict.databinding.FragmentSearchPopularBinding;
import com.konifar.annict.databinding.ItemSearchBinding;
import com.konifar.annict.pref.DefaultPrefs;
import com.konifar.annict.view.widget.ArrayRecyclerAdapter;
import com.konifar.annict.view.widget.BindingHolder;
import com.konifar.annict.view.widget.InfiniteOnScrollChangeListener;
import com.konifar.annict.view.widget.itemdecoration.DividerItemDecoration;
import com.konifar.annict.viewmodel.SearchItemViewModel;
import com.konifar.annict.viewmodel.SearchPopularViewModel;
import javax.inject.Inject;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class SearchPopularFragment extends BaseFragment implements TabPage {

  private static final String TAG = SearchPopularFragment.class.getSimpleName();

  @Inject SearchPopularViewModel viewModel;

  @Inject CompositeSubscription compositeSubscription;

  private String authCode;

  private FragmentSearchPopularBinding binding;

  private SearchItemsAdapter adapter;

  public static SearchPopularFragment newInstance(@NonNull String authCode) {
    SearchPopularFragment fragment = new SearchPopularFragment();
    Bundle bundle = new Bundle();
    bundle.putString(ARG_AUTH_CODE, authCode);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    getComponent().inject(this);
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      authCode = getArguments().getString(ARG_AUTH_CODE);
    }
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentSearchPopularBinding.inflate(inflater, container, false);
    binding.setViewModel(viewModel);

    initRecyclerView();
    showWithAuth();

    return binding.getRoot();
  }

  private void showWithAuth() {
    Subscription sub =
        viewModel.showWithAuth(DefaultPrefs.get(getContext()).getAccessToken(), authCode)
            .subscribe(workViewModels -> {
              viewModel.recyclerViewVisibility.set(View.VISIBLE);
              adapter.addAllWithNotify(workViewModels);
            }, throwable -> {
              viewModel.progressBarVisibility.set(View.GONE);
              Log.e(TAG, "load auth token error occurred.", throwable);
            });
    compositeSubscription.add(sub);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    compositeSubscription.unsubscribe();
    adapter.destroy();
    viewModel.destroy();
  }

  private void initRecyclerView() {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    adapter = new SearchItemsAdapter(getContext());

    binding.recyclerView.setAdapter(adapter);
    binding.recyclerView.setLayoutManager(linearLayoutManager);
    binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));

    binding.nestedScrollView.setOnScrollChangeListener(
        new InfiniteOnScrollChangeListener(binding.recyclerView, linearLayoutManager) {
          @Override public void onLoadMore() {
            showNext();
          }
        });
  }

  private void showNext() {
    Subscription sub = viewModel.showNext().subscribe(workViewModels -> {
      viewModel.incremantePage();
      adapter.addAllWithNotify(workViewModels);
    }, throwable -> {
      viewModel.footerProgressBarVisibility.set(View.GONE);
      Log.e(TAG, "load works error occurred.", throwable);
    });
    compositeSubscription.add(sub);
  }

  @Override public void scrollToTop() {
    binding.recyclerView.smoothScrollToPosition(0);
  }

  @Override public Fragment getFragment() {
    return this;
  }

  public class SearchItemsAdapter
      extends ArrayRecyclerAdapter<SearchItemViewModel, BindingHolder<ItemSearchBinding>> {

    public SearchItemsAdapter(@NonNull Context context) {
      super(context);
    }

    @Override
    public BindingHolder<ItemSearchBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
      return new BindingHolder<>(getContext(), parent, R.layout.item_search);
    }

    @Override public void onBindViewHolder(BindingHolder<ItemSearchBinding> holder, int position) {
      SearchItemViewModel viewModel = getItem(position);
      holder.binding.setViewModel(viewModel);
    }

    public void destroy() {
      Stream.of(list).forEach(SearchItemViewModel::destroy);
    }
  }
}
