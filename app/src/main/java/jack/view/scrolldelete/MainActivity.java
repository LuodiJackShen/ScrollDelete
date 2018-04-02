package jack.view.scrolldelete;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(new ItemAdapter());
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.item_layout, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            Button btn1;
            Button btn2;

            ItemViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.show_view);
                btn1 = itemView.findViewById(R.id.delete);
                btn2 = itemView.findViewById(R.id.collect);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "delete", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "collect", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            }

            void bindView(int position) {
                tv.setText(String.valueOf(position));
            }
        }
    }
}
