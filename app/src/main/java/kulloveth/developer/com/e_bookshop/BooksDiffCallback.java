package kulloveth.developer.com.e_bookshop;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;

import kulloveth.developer.com.e_bookshop.models.Book;

public class BooksDiffCallback extends DiffUtil.Callback {
    ArrayList<Book> newList = new ArrayList<>();
    ArrayList<Book> oldList = new ArrayList<>();

    public BooksDiffCallback() {
    }

    public BooksDiffCallback(ArrayList<Book> newList, ArrayList<Book> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {

        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getBookId() == newList.get(newItemPosition).getBookId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
