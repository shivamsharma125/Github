package in.co.softwaresolution.github;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    ArrayList<String> items;

    MyCustomAdapter(Context context , ArrayList<String> items)
    {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.textview_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String repos = items.get(position);
        holder.textView.setText(repos);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
