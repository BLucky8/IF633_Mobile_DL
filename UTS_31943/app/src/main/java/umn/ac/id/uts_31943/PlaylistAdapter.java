package umn.ac.id.uts_31943;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Song> mLagu;

    PlaylistAdapter(Context mContext, ArrayList<Song> mLagu){
        this.mContext = mContext;
        this.mLagu = mLagu;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_lagu, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.music_name.setText(mLagu.get(position).getTitle());
        byte[] image = getAlbumImage(mLagu.get(position).getPath());
        if(image != null){
            Glide.with(mContext).asBitmap().load(image).into(holder.music_image);
        }else{
            Glide.with(mContext).load(R.drawable.cover_art).into(holder.music_image);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intentPlayMusic = new Intent(mContext, MPlayer.class);
            intentPlayMusic.putExtra("position", position);
            mContext.startActivity(intentPlayMusic);
        });
    }

    @Override
    public int getItemCount() {
        return mLagu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView music_name;
        ImageView music_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            music_name = itemView.findViewById(R.id.musicName);
            music_image = itemView.findViewById(R.id.musicImg);
        }
    }

    private byte[] getAlbumImage(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] img = retriever.getEmbeddedPicture();
        retriever.release();
        return img;
    }
}
