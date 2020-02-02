package kulloveth.developer.com.e_bookshop.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import kulloveth.developer.com.e_bookshop.models.Category;

@Dao
public interface CategoryDao {


    @Insert
    void insertCategory(Category  category);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("Select * from categories_table")
    LiveData<List<Category>> getAllCategories();
}
