package com.mtrilogic.abstracts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mtrilogic.adapters.RecyclableAdapter;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.RecyclableAdapterListener;
import com.mtrilogic.interfaces.RecyclableItemListener;
import com.mtrilogic.items.DefaultRecyclable;

@SuppressWarnings({"unused"})
public abstract class RecyclableDialogFragment<P extends ListablePage<Model>> extends BaseDialogFragment<P> implements RecyclableAdapterListener, RecyclableItemListener {
    protected RecyclableAdapter adapter;
    protected RecyclerView lvwItems;

    /*==============================================================================================
    PUBLIC OVERRIDE METHODS
    ==============================================================================================*/

    @NonNull
    @Override
    public final Listable<Model> getModelListable() {
        return page.getListable();
    }

    @NonNull
    @Override
    public final RecyclableAdapter getRecyclableAdapter() {
        return adapter;
    }

    @NonNull
    @Override
    public final RecyclerView getRecyclerView() {
        return lvwItems;
    }

    @NonNull
    @Override
    public Recyclable<? extends Model> getRecyclable(int viewType, @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new DefaultRecyclable(inflater, parent, this);
    }

    @Override
    public boolean onRecyclableLongClick(@NonNull View itemView, @NonNull Model model, int position) {
        return false;
    }

    @Override
    public void onRecyclableClick(@NonNull View itemView, @NonNull Model model, int position) {

    }

    /*==============================================================================================
    PROTECTED METHOD
    ==============================================================================================*/

    /**
     * Inicializa el RecyclerView y el RecyclableAdapter
     * ATENCIÓN!!!: Este método debe llamarse dentro de onCreateView
     * @param lvwItems the recyclerView view.
     * @param manager the layout manager (LinearLayoutManager or GridLayoutManager)
     */
    protected final void initRecyclerViewAdapter(@NonNull RecyclerView lvwItems, @NonNull RecyclerView.LayoutManager manager){
        adapter = new RecyclableAdapter(getLayoutInflater(), this);
        lvwItems.setAdapter(adapter);
        lvwItems.setLayoutManager(manager);
        this.lvwItems = lvwItems;
    }
}
