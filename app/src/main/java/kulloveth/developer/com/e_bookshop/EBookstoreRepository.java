package kulloveth.developer.com.e_bookshop;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import kulloveth.developer.com.e_bookshop.database.BookDao;
import kulloveth.developer.com.e_bookshop.database.BookDatabase;
import kulloveth.developer.com.e_bookshop.database.CategoryDao;
import kulloveth.developer.com.e_bookshop.models.Book;
import kulloveth.developer.com.e_bookshop.models.Category;

public class EBookstoreRepository {

    private CategoryDao categoryDao;
    private BookDao bookDao;

    private LiveData<List<Category>> categories;
    private LiveData<List<Book>> books;

    public EBookstoreRepository(Application application) {
        BookDatabase bookDatabase = BookDatabase.getInstance(application);
        categoryDao = bookDatabase.categoryDao();
        bookDao = bookDatabase.bookDao();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDao.getAllCategories();
    }

    public LiveData<List<Book>> getBooks(int categoryId) {
        return bookDao.getBooks(categoryId);
    }

    public  void insertCategory(Category category){
        new InsertCategoryAsyncTask(categoryDao).execute(category);
    }

    public  void insertBook(Book book){
        new InsertBookAsyncTask(bookDao).execute(book);
    }

    public  void deleteCategory(Category category){
        new DeleteCategoryAsyncTask(categoryDao).execute(category);
    }

    public  void deleteBook(Book book){
        new DeleteBookAsyncTask(bookDao).execute(book);
    }
    public  void updateCategory(Category category){
        new UpdateCategoryAsyncTask(categoryDao).execute(category);
    }

    public  void updateBook(Book book){
        new UpdateBookAsyncTask(bookDao).execute(book);
    }

    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        public InsertCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.insertCategory(categories[0]);
            return null;
        }
    }

    private static class InsertBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao bookDao;
        public InsertBookAsyncTask(BookDao bookDao) {
            this.bookDao=bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.insertBook(books[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        public DeleteCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.deleteCategory(categories[0]);
            return null;
        }
    }

    private static class DeleteBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao bookDao;
        public DeleteBookAsyncTask(BookDao bookDao) {
            this.bookDao=bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.deleteBook(books[0]);
            return null;
        }
    }


    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        public UpdateCategoryAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDao.updateCategory(categories[0]);
            return null;
        }
    }

    private static class UpdateBookAsyncTask extends AsyncTask<Book, Void, Void> {
        private BookDao bookDao;
        public UpdateBookAsyncTask(BookDao bookDao) {
            this.bookDao=bookDao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            bookDao.updateBook(books[0]);
            return null;
        }
    }
}
