package kulloveth.developer.com.e_bookshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import kulloveth.developer.com.e_bookshop.databinding.ActivityAddEditBinding;
import kulloveth.developer.com.e_bookshop.models.Book;

public class AddEditActivity extends AppCompatActivity {

    private Book book;
    public static final String BOOK_ID = "bookId";
    public static final String BOOK_NAME = "bookName";
    public static final String UNIT_PRICE = "unitPrice";

    private ActivityAddEditBinding activityAddEditBinding;
    private AddEditClickHandler addEditClickHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        book = new Book();
        activityAddEditBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit);
        activityAddEditBinding.setBook(book);
        addEditClickHandler = new AddEditClickHandler(this);
        activityAddEditBinding.setClickHandler(addEditClickHandler);

        Intent intent = getIntent();

        if (intent.hasExtra(BOOK_ID)) {
           getSupportActionBar().setTitle("Edit Text");
            book.setBookName(intent.getStringExtra(BOOK_NAME));
            book.setUnitPrice(intent.getStringExtra(UNIT_PRICE));
        } else {
           getSupportActionBar(). setTitle("New Book");
        }


    }


    public class AddEditClickHandler {
        Context context;

        public AddEditClickHandler(Context context) {
            this.context = context;
        }

        public void onSubmitButtonClicked(View view) {
            if (book.getBookName() == null) {
                Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra(BOOK_NAME,book.getBookName());
                intent.putExtra(UNIT_PRICE,book.getUnitPrice());
                setResult(RESULT_OK,intent);
                finish();
            }
        }
    }
}
