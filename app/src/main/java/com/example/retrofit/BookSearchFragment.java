package com.example.retrofit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.retrofit.models.BookSearchResultsAdapter;
import com.example.retrofit.models.VolumesResponse;
import com.example.retrofit.models.viewmodels.BookSearchViewModel;
import com.google.android.material.textfield.TextInputEditText;


public class BookSearchFragment extends Fragment {
    private BookSearchViewModel viewModel;
    private BookSearchResultsAdapter adapter;

    private TextInputEditText keywordEditText, authorEditText;
    private Button searchButton;
    Bundle bundle;
    String loggedInUserByUserName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        adapter = new BookSearchResultsAdapter();

        viewModel = new ViewModelProvider(this).get(BookSearchViewModel.class);// had to update
        viewModel.init();
        viewModel.getVolumesResponseLiveData().observe(this, volumesResponse -> {
            if (volumesResponse != null) {
                adapter.setResults(volumesResponse.getItems());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_search, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.fragment_booksearch_searchResultsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        keywordEditText = view.findViewById(R.id.fragment_booksearch_keyword);
        authorEditText = view.findViewById(R.id.fragment_booksearch_author);
        searchButton = view.findViewById(R.id.fragment_booksearch_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        return view;
    }

    public void performSearch() {
        String keyword = keywordEditText.getEditableText().toString();
        String author = authorEditText.getEditableText().toString();

        viewModel.searchVolumes(keyword, author);
    }

}