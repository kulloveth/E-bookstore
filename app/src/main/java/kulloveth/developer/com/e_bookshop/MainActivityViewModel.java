package kulloveth.developer.com.e_bookshop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import kulloveth.developer.com.e_bookshop.models.Book;
import kulloveth.developer.com.e_bookshop.models.Category;

public class MainActivityViewModel extends AndroidViewModel {

    private EBookstoreRepository repository;
    private LiveData<List<Category>> categoriesLivedata;
    private LiveData<List<Book>> bookLiveData;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new EBookstoreRepository(application);
    }

    public LiveData<List<Category>> getCategoriesLivedata() {
        categoriesLivedata = repository.getCategories();
        return categoriesLivedata;
    }

    public LiveData<List<Book>> getBookLiveData(int categoryId) {
        bookLiveData = repository.getBooks(categoryId);
        return bookLiveData;
    }

    public void insertNewBook(Book book){
        repository.insertBook(book);
    }
    public void updateBook(Book book){
        repository.updateBook(book);
    }
    public void deleteBook(Book book){
        repository.deleteBook(book);
    }
}
