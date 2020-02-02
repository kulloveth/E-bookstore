package kulloveth.developer.com.e_bookshop.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Locale;

import kulloveth.developer.com.e_bookshop.models.Book;
import kulloveth.developer.com.e_bookshop.models.Category;


@Database(entities = {Locale.Category.class, Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract BookDao bookDao();

    private static BookDatabase INSTANCE;

    public static synchronized BookDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BookDatabase.class,
                    "books_databse").fallbackToDestructiveMigration().addCallback(callback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataAsyncTask(INSTANCE).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private CategoryDao categoryDao;
        private BookDao bookDao;

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        public InitialDataAsyncTask(BookDatabase bookDatabase) {
            categoryDao = bookDatabase.categoryDao();
            bookDao = bookDatabase.bookDao();

            Category category1 = new Category();
            category1.setCategoryName("programing");
            category1.setCategoryDescription("Books to learn how to Code");


            Category category2 = new Category();
            category2.setCategoryName("inspirational");
            category2.setCategoryDescription("Books to learn help you get inspired");

            Category category3 = new Category();
            category3.setCategoryName("Others");
            category3.setCategoryDescription("Different Kind of Books not programming or Inspirational");

            categoryDao.insertCategory(category1);
            categoryDao.insertCategory(category2);
            categoryDao.insertCategory(category3);

            Book book1 = new Book();
            book1.setBookName("code with java");
            book1.setUnitPrice("$50.45");
            book1.setCategoryId(1);

            Book book2 = new Book();
            book1.setBookName("code with kotlin");
            book1.setUnitPrice("$150.45");
            book1.setCategoryId(1);

            Book book3 = new Book();
            book3.setBookName("Love Your Self");
            book3.setUnitPrice("$200.45");
            book3.setCategoryId(2);

            Book book4 = new Book();
            book4.setBookName("Never give up");
            book4.setUnitPrice("$150.45");
            book4.setCategoryId(2);

            Book book5 = new Book();
            book5.setBookName("Wonders of the Hills");
            book5.setUnitPrice("$200.45");
            book5.setCategoryId(3);

            Book book6 = new Book();
            book6.setBookName("Stories of Tomorrow");
            book3.setUnitPrice("$100.45");
            book3.setCategoryId(3);

            bookDao.insertBook(book1);
            bookDao.insertBook(book2);
            bookDao.insertBook(book3);
            bookDao.insertBook(book4);
            bookDao.insertBook(book5);
            bookDao.insertBook(book6);

        }
    }
}
