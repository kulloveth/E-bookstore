package kulloveth.developer.com.e_bookshop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kulloveth.developer.com.e_bookshop.adapter.BookAdapter;
import kulloveth.developer.com.e_bookshop.databinding.ActivityMainBinding;
import kulloveth.developer.com.e_bookshop.models.Book;
import kulloveth.developer.com.e_bookshop.models.Category;

public class MainActivity extends AppCompatActivity {
    MainActivityViewModel viewModel;
    private ActivityMainBinding activityMainBinding;
    MainActivityClickHandlers activityClickHandlers;
    ArrayList<Category> categoryList;
    ArrayList<Book> bookArrayList;
    private Category selectedCategory;
    private RecyclerView booksRecyclerView;

    BookAdapter booksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityClickHandlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandlers(activityClickHandlers);


        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.getCategoriesLivedata().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {

                categoryList = (ArrayList<Category>) categories;
                for (Category category : categories) {
                    Log.d("My ", "onChanged: " + category.getCategoryName());

                    showOnSpinner();
                }
            }
        });

        viewModel.getBookLiveData(3).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                for (Book book : books) {
                    Log.d("My Tag", "onChanged: " + book.getBookName());
                }
            }
        });


    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this, R.layout.spinner_item, categoryList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    private void loadBookList(int selectedCategory) {
        viewModel.getBookLiveData(selectedCategory).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
//                for(Book book: books){
//                    Log.d("My Tag", "onChanged: " + book.getBookName());
//                }

                bookArrayList = (ArrayList<Book>) books;
                loadRecyclerView();

            }
        });
    }

    private void loadRecyclerView() {
        booksRecyclerView = activityMainBinding.secondaryLayout.rvBooks;
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksAdapter = new BookAdapter();
        booksRecyclerView.setAdapter(booksAdapter);
        booksAdapter.setBookArrayList(bookArrayList);
    }

    public class MainActivityClickHandlers {
        public void onFabClicked(View view) {
            Toast.makeText(MainActivity.this, "fab toasted", Toast.LENGTH_LONG).show();
        }

        public void selectedItem(AdapterView<?> parent, View view, int pos, long id) {
            selectedCategory = (Category) parent.getItemAtPosition(pos);
            String message = "id is" + selectedCategory.getId() + "\n name is " + selectedCategory.getCategoryName();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            loadBookList(selectedCategory.getId());

        }
    }

}
