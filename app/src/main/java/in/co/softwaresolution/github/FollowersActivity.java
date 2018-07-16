package in.co.softwaresolution.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ListView listView;
    TextView textView;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    ArrayAdapter arrayAdapter;
    ArrayList<String> followersItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        linearLayout=findViewById(R.id.linearLayoutFollowersContentActivity);
        progressBar=findViewById(R.id.progressBarFollowersActivity);
        textView=findViewById(R.id.textviewToolBarIDFollowersActivity);
        listView=findViewById(R.id.listviewFollowers);
        arrayAdapter = new ArrayAdapter(this,R.layout.textview_layout,R.id.textviewListviewID,followersItems);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(this);

        final String username = bundle.getString(MainActivity.USERNAME);
        textView.setText(username);

        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GithubService service = retrofit.create(GithubService.class);

        Call<ArrayList<FollowerSearch>> call = service.getUserFollowers(username);

        call.enqueue(new Callback<ArrayList<FollowerSearch>>() {
            @Override
            public void onResponse(Call<ArrayList<FollowerSearch>> call, Response<ArrayList<FollowerSearch>> response) {

                ArrayList<FollowerSearch> followerSearches = response.body();
                followersItems.clear();
                for(int i=0;i<followerSearches.size();i++)
                {
                    followersItems.add(followerSearches.get(i).login);
                }
                arrayAdapter.notifyDataSetChanged();
                linearLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<ArrayList<FollowerSearch>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String name = followersItems.get(position);
        Intent intent = new Intent(FollowersActivity.this,MainActivity.class);
        intent.putExtra(MainActivity.USERNAME,name);
        startActivity(intent);

    }
}
