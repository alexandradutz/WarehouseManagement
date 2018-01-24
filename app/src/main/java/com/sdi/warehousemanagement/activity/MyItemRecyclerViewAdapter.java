package com.sdi.warehousemanagement.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdi.warehousemanagement.R;
import com.sdi.warehousemanagement.entities.Product;
import com.sdi.warehousemanagement.fragment.ItemFragment.OnListFragmentInteractionListener;
import com.sdi.warehousemanagement.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final HashMap<String, Product> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public MyItemRecyclerViewAdapter(HashMap<String, Product> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        List<String> keys = new ArrayList<>(mValues.keySet());
        final String prodID = keys.get(position);
        holder.mItem = mValues.get(prodID);
        holder.mIdView.setText(prodID);
        holder.mContentView.setText(mValues.get(prodID).getName());
        holder.mQuantityView.setText(String.valueOf(mValues.get(prodID).getQuantity()));
        holder.mPriceView.setText(String.valueOf(mValues.get(prodID).getPrice()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Edit.class);
                intent.putExtra("prodid", prodID);
                intent.putExtra("name", holder.mItem.getName());
                intent.putExtra("quantity", holder.mItem.getQuantity());
                intent.putExtra("category", holder.mItem.getCategory().getName());
                intent.putExtra("price", holder.mItem.getPrice());
                intent.putExtra("description", holder.mItem.getDescription());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mQuantityView;
        public final TextView mPriceView;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mQuantityView = (TextView) view.findViewById(R.id.quantity);
            mPriceView = (TextView) view.findViewById(R.id.price);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
