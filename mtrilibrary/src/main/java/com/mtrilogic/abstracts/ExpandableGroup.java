package com.mtrilogic.abstracts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.view.View;
import android.viewbinding.ViewBinding;

import com.mtrilogic.adapters.ExpandableAdapter;
import com.mtrilogic.classes.Listable;
import com.mtrilogic.interfaces.ExpandableAdapterListener;
import com.mtrilogic.views.ExpandableView;

@SuppressWarnings({"unused","WeakerAccess"})
public abstract class ExpandableGroup<M extends Modelable, VB extends ViewBinding>
        extends LiveData<M> implements Observer<M> {

    protected final ExpandableAdapterListener listener;
    protected final View itemView;
    protected Listable<Modelable> childListable;
    protected int groupPosition;
    protected boolean expanded;
    protected VB binding;
    protected M model;

    // ================< PROTECTED ABSTRACT METHODS >===============================================

    protected abstract void onBindHolder(@NonNull Modelable modelable);

    // ================< PUBLIC CONSTRUCTORS >======================================================

    public ExpandableGroup(@NonNull VB binding, @NonNull ExpandableAdapterListener listener){
        itemView = binding.getRoot();
        this.listener = listener;
        this.binding = binding;
    }

    // ================< PUBLIC METHODS >===========================================================

    public final View getItemView() {
        return itemView;
    }

    public final void bindModel(@NonNull Modelable modelable, int groupPosition, boolean expanded){
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        childListable = adapter != null ? adapter.getChildListable(modelable) : null;
        this.groupPosition = groupPosition;
        this.expanded = expanded;
        onBindHolder(modelable);
    }

    // ================< PROTECTED METHODS >========================================================

    protected final void autoDelete(){
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        if (adapter != null && adapter.deleteGroupModelable(model)){
            adapter.notifyDataSetChanged();
        }
    }

    protected final void addNewChildModelable(@NonNull Modelable childModelable, long idx){
        ExpandableAdapter adapter = listener.getExpandableAdapter();
        if (adapter != null && adapter.appendChildModelable(model, childModelable)) {
            adapter.notifyDataSetChanged();
            childListable.setIdx(++idx);
            if (adapter.getChildrenCount(groupPosition) == 1){
                ExpandableView lvwItems = listener.getExpandableView();
                if (lvwItems != null && !lvwItems.isGroupExpanded(groupPosition)){
                    lvwItems.expandGroup(groupPosition);
                }
            }
        }
    }
}
