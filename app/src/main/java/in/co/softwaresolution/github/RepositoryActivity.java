package in.co.softwaresolution.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    MyCustomAdapter arrayAdapter;
    ArrayList<String > repositoryItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        linearLayout=findViewById(R.id.linearLayoutRepositoryContentActivity);
        progressBar=findViewById(R.id.progressBarRepositoryActivity);
        textView = findViewById(R.id.textviewToolBarRepositoryActivity);
        recyclerView=findViewById(R.id.recyclerviewRepository);
//        arrayAdapter = new ArrayAdapter(this,R.layout.textview_layout,R.id.textviewListviewID,repositoryItems);
//        listView.setAdapter(arrayAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        arrayAdapter = new MyCustomAdapter(this,repositoryItems);

        recyclerView.setAdapter(arrayAdapter);
        recyclerView.setLayoutManager(manager);

        final String username = bundle.getString(MainActivity.USERNAME);

        textView.setText(username);

        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GithubService service = retrofit.create(GithubService.class);

        Call<ArrayList<RepositorySearch>> call = service.getUserRepos(username);

        call.enqueue(new Callback<ArrayList<RepositorySearch>>() {
            @Override
            public void onResponse(Call<ArrayList<RepositorySearch>> call, Response<ArrayList<RepositorySearch>> response) {

                ArrayList<RepositorySearch> repositorySearches = response.body();
                repositoryItems.clear();
                for(int i=0;i<repositorySearches.size();i++)
                {
                    repositoryItems.add(repositorySearches.get(i).name);
                }
                arrayAdapter.notifyDataSetChanged();
                linearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<RepositorySearch>> call, Throwable t) {

            }
        });

    }
}
