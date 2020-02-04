package kulloveth.developer.com.e_bookshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kulloveth.developer.com.e_bookshop.R;
import kulloveth.developer.com.e_bookshop.databinding.BookListItemBinding;
import kulloveth.developer.com.e_bookshop.models.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private OnItemClickListener listener;
    private ArrayList<Book> bookArrayList = new ArrayList<>();

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        BookListItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.book_list_item, parent, false);
        return new BookViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookArrayList.get(position);
        holder.bookListItemBinding.setBook(book);
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

    public void setBookArrayList(ArrayList<Book> bookArrayList) {
        this.bookArrayList = bookArrayList;
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {

        private BookListItemBinding bookListItemBinding;

        public BookViewHolder(BookListItemBinding bookListItemBinding) {
            super(bookListItemBinding.getRoot());

            this.bookListItemBinding = bookListItemBinding;
            bookListItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = getAdapterPosition();
                    if (listener != null && clickedPosition != RecyclerView.NO_POSITION) {
                        listener.onItemCLicked(bookArrayList.get(clickedPosition));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemCLicked(Book book);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
