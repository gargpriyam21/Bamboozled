package com.example.neera.bamboozled.activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.neera.bamboozled.HistoryDB.HistoryDBHelper;
import com.example.neera.bamboozled.HistoryDB.Model.HistoryData;
import com.example.neera.bamboozled.HistoryDB.Tables.HistoryTable;
import com.example.neera.bamboozled.R;

import java.util.ArrayList;

public class Activity_History extends AppCompatActivity {

    static ArrayList<HistoryData> history = new ArrayList<>();
    RecyclerView rvHistory;
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final SQLiteDatabase historyDb = new HistoryDBHelper(this).getWritableDatabase();

        rvHistory = (RecyclerView) findViewById(R.id.rvHistory);
        btnReset = (Button) findViewById(R.id.btnReset);

        final HistoryAdapter historyAdapter = new HistoryAdapter(this);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(historyAdapter);

        history = HistoryTable.getHistory(historyDb);
        historyAdapter.notifyDataSetChanged();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < history.size(); i++) {
                    HistoryTable.deleteHistory(
                            historyDb,
                            history.get(i).getId()
                    );
                    history.remove(i);
                    i--;

                }
                history = HistoryTable.getHistory(historyDb);

                historyAdapter.notifyDataSetChanged();
            }
        });
    }


    class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
        Context context;

        public HistoryAdapter(Context context) {
            this.context = context;
        }

        @Override
        public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HistoryViewHolder(getLayoutInflater().inflate(R.layout.list_item_history, parent, false));
        }

        @Override
        public void onBindViewHolder(HistoryViewHolder holder, int position) {
            holder.bindView(history.get(position));
        }

        @Override
        public int getItemCount() {
            return history.size();
        }

        class HistoryViewHolder extends RecyclerView.ViewHolder {
            TextView tvScore, tvDate;

            public HistoryViewHolder(View itemView) {
                super(itemView);
                tvScore = itemView.findViewById(R.id.tvScore);
                tvDate = itemView.findViewById(R.id.tvDate);
            }

            void bindView(final HistoryData historyData) {
                tvScore.setText(historyData.getScore());
                tvDate.setText(historyData.getDate());
            }
        }
    }
}
