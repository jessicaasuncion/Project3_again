package com.example.cs401_project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MatchActivity extends AppCompatActivity {


    private EditText editText, etd;
    private Button button;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        editText = findViewById(R.id.et);
        etd = findViewById(R.id.etd);
        button = findViewById(R.id.btn);
        recyclerView = findViewById(R.id.list);
    }


    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("posts").push();
            Map<String, Object> map = new HashMap<>();
            map.put("id", databaseReference.getKey());
            map.put("title", editText.getText().toString());
            map.put("desc", etd.getText().toString());

            databaseReference.setValue(map);

        }
    });
    linearLayoutManager = new LinearLayoutManager(this);
recyclerView.setLayoutManager(linearLayoutManager);
recyclerView.setHasFixedSize(true);
    fetch();

}

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("posts");

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, new SnapshotParser<Model>() {
                            @NonNull
                            @Override
                            public Model parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Model(snapshot.child("id").getValue().toString(),
                                        snapshot.child("title").getValue().toString(),
                                        snapshot.child("desc").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, Model model) {
                holder.setTxtTitle(model.getmTitle());
                holder.setTxtDesc(model.getmDesc());

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        };
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

public class ViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public TextView txtTitle;
    public TextView txtDesc;

    public ViewHolder(View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.list_root);
        txtTitle = itemView.findViewById(R.id.list_title);
        txtDesc = itemView.findViewById(R.id.list_desc);
    }

    public void setTxtTitle(String string) {
        txtTitle.setText(string);
    }


    public void setTxtDesc(String string) {
        txtDesc.setText(string);
    }
}
}