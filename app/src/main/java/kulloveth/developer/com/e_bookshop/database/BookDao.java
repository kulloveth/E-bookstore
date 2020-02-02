package kulloveth.developer.com.e_bookshop.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kulloveth.developer.com.e_bookshop.models.Book;
import kulloveth.developer.com.e_bookshop.models.Category;

@Dao
public interface BookDao {

    @Insert
    void insertBook(Category category);

    @Update
    void updateBook(Category category);

    @Delete
    void deleteBook(Category category);

    @Query("Select * from books_table where category_id = :categoryId")
    LiveData<List<Book>> getBooks(int categoryId);

}
