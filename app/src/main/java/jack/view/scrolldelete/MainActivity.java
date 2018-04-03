package jack.view.scrolldelete;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jack.view.ScrollDeleteLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRv;
    private ItemAdapter mItemAdapter;
    private ScrollDeleteLayout mDeleteLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mItemAdapter = new ItemAdapter();
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mItemAdapter);
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mDeleteLayout != null) {
                    /***
                     * 当外面的布局滑动时，使ScrollDeleteLayout收缩布局。
                     */
                    mDeleteLayout.shrink();
                }
            }
        });
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
        private ScrollDeleteLayout layout;

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            layout = (ScrollDeleteLayout) LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.item_layout, parent, false);
            return new ItemViewHolder(layout);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            holder.bindView(position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder implements ScrollDeleteLayout.OnExtendListener {
            TextView tv;
            Button btn1;
            Button btn2;
            ScrollDeleteLayout layout;

            ItemViewHolder(View itemView) {
                super(itemView);
                layout = (ScrollDeleteLayout) itemView;
                /***
                 * 设置展开监听器.
                 */
                layout.setOnExtendListener(this);

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

            @Override
            public void onExtend(ScrollDeleteLayout layout) {
                mDeleteLayout = null;
                mDeleteLayout = layout;
            }
        }
    }
}
