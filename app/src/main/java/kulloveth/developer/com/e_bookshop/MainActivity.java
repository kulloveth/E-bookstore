package kulloveth.developer.com.e_bookshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
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
    private int selectedBookId;

    public static final int ADD_BOOK_REQUEST_CODE = 1;
    public static final int EDIT_BOOK_REQUEST_CODE = 2;

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
//                for (Category category : categories) {
//                    Log.d("My ", "onChanged: " + category.getCategoryName());
//
//                    showOnSpinner();
//                }
                showOnSpinner();
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
        booksAdapter.setListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemCLicked(Book book) {
                selectedBookId = book.getBookId();
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.BOOK_ID, selectedBookId);
                intent.putExtra(AddEditActivity.BOOK_NAME, book.getBookName());
                intent.putExtra(AddEditActivity.UNIT_PRICE, book.getUnitPrice());
                startActivityForResult(intent,EDIT_BOOK_REQUEST_CODE);


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Book bookToDelete = bookArrayList.get(viewHolder.getAdapterPosition());
                viewModel.deleteBook(bookToDelete);
            }
        }).attachToRecyclerView(booksRecyclerView);
    }

    public class MainActivityClickHandlers {
        public void onFabClicked(View view) {
            Toast.makeText(MainActivity.this, "fab toasted", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, ADD_BOOK_REQUEST_CODE);
        }

        public void selectedItem(AdapterView<?> parent, View view, int pos, long id) {
            selectedCategory = (Category) parent.getItemAtPosition(pos);
//            String message = "id is" + selectedCategory.getId() + "\n name is " + selectedCategory.getCategoryName();
//            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            loadBookList(selectedCategory.getId());

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int categoryId = selectedCategory.getId();
        if (requestCode  == ADD_BOOK_REQUEST_CODE && resultCode == RESULT_OK){
            Book book = new Book();
            book.setCategoryId(categoryId);
            book.setBookName(data.getStringExtra(AddEditActivity.BOOK_NAME));
            book.setUnitPrice(data.getStringExtra(AddEditActivity.UNIT_PRICE));
            viewModel.insertNewBook(book);

        }else if(requestCode  == EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK){

            Book book = new Book();
            book.setCategoryId(categoryId);
            book.setBookName(data.getStringExtra(AddEditActivity.BOOK_NAME));
            book.setUnitPrice(data.getStringExtra(AddEditActivity.UNIT_PRICE));
            book.setBookId(selectedBookId);
            viewModel.updateBook(book);

        }
    }

}
