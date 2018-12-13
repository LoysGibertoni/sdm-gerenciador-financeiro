package br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.presentation.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.scl.sdm.gerenciadorfinanceiro.BR;

/*
 *  Este adapter faz uso do Android Data Binding para preencher suas Views.
 *  Ele recebe o id do layout a ser utilizado para os items e uma lista de items e, então,
 *  atribui à variável "item" das Views o item correspondente. O Android Data Binding se encarrega do resto.
 */
public class BindableItemAdapter<T> extends RecyclerView.Adapter<BindableItemAdapter.ViewHolder> {

    private int mItemLayoutId;
    private List<T> mItems;
    private OnItemClickListener<T> mOnItemClickListener;

    public BindableItemAdapter(@LayoutRes int itemLayoutId) {
        mItemLayoutId = itemLayoutId;
    }

    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List<T> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        final int position = this.getItemCount();
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mItems.add(item);
        notifyItemInserted(position);
    }

    public void removeItem(T item) {
        if (mItems != null) {
            final int position = mItems.indexOf(item);
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BindableItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new BindableItemAdapter.ViewHolder(DataBindingUtil.inflate(inflater, mItemLayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BindableItemAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        private ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(getItem(getAdapterPosition()));
                }
            });
        }

        private void bind(Object item) {
            // Para realizar o bind, é muito simples, basta atribuir o item à variável "item" da view
            // O Android Data Binding atualizará todos os componentes que utilizam essa variável dentro do layout
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
        }

        @SuppressWarnings("unchecked")
        private  <V extends ViewDataBinding> V getBinding() {
            return (V) binding;
        }
    }

    public interface OnItemClickListener<T> {
        void onClick(T item);
    }
}

