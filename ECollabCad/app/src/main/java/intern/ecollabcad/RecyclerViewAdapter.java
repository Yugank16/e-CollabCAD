package intern.ecollabcad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.FileHandler;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewholder>{

    private ArrayList<FileData> arrayList = new ArrayList<FileData>();
    Context mContext;
    public RecyclerViewAdapter(ArrayList<FileData> arrayList, Context mContext)
    {
        this.arrayList= arrayList;
        this.mContext= mContext;
    }

    @Override
    public RecyclerViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        RecyclerViewholder recyclerViewholder= new RecyclerViewholder(view, mContext, arrayList);
        return recyclerViewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewholder holder, int position) {

        FileData fileData= arrayList.get(position);

        holder.file_name_tv.setText(fileData.getFileName());
        holder.file_type_tv.setText(fileData.getFileType());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewholder extends RecyclerView.ViewHolder implements  View.OnClickListener{


        TextView file_name_tv, file_type_tv;
        ArrayList<FileData> items= new ArrayList<FileData>();
        Context ctx;

        public RecyclerViewholder(View itemView, Context ctx, ArrayList<FileData> items) {
            super(itemView);

            itemView.setOnClickListener(this);
            this.items= items;
            this.ctx= ctx;
            file_name_tv= (TextView) itemView.findViewById(R.id.item_name);
            file_type_tv= (TextView) itemView.findViewById(R.id.item_type);
        }


        @Override
        public void onClick(View v) {

            Toast.makeText(ctx, "File cannot be opened", Toast.LENGTH_SHORT).show();


        }
    }

}
