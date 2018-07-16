package in.co.softwaresolution.github;

import android.content.Intent;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText searchBar;
    TextView textView;
    Button searchButton;
    String searchItem;
    GithubService service;
    ImageView imageView;
    ProgressBar progressBar;
    ConstraintLayout constraintLayout;
    Bundle bundle = new Bundle();
    Button reposButton;
    Button followersButton;
    Toolbar toolbar;

    public static final String USERNAME="username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra(USERNAME);
        searchItem = username;

        constraintLayout=findViewById(R.id.constraintLayoutIDMainActivity);
        progressBar=findViewById(R.id.progressBarMainActivity);
        searchBar=findViewById(R.id.searchbarIDActivityMain);
        textView=findViewById(R.id.textviewIDContentMain);
        searchButton=findViewById(R.id.buttonIDContentMain);
        imageView=findViewById(R.id.imageIDContentMain);
        reposButton=findViewById(R.id.reposIDContentMain);
        followersButton=findViewById(R.id.followersIDContentMain);

        searchBar.setText(username);

        searchButton.setOnClickListener(this);
        reposButton.setOnClickListener(this);
        followersButton.setOnClickListener(this);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        service = retrofit.create(GithubService.class);

        constraintLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);

        searchUserName();

    }

    @Override
    public void onClick(View v) {

        final Button button = (Button) v;

        if (button == searchButton) {

            searchItem = searchBar.getText().toString();
            searchItem = searchItem.trim();

            if (!searchItem.isEmpty()) {

                searchUserName();

            } else {
                searchBar.setError("Type Something");
            }

        }
        else if(button == reposButton)
        {
            Intent intent = new Intent(this,RepositoryActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if(button == followersButton)
        {
            Intent intent = new Intent(this,FollowersActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void searchUserName()
    {
        Call<GithubSearch> call = service.getRepos(searchItem);

        call.enqueue(new Callback<GithubSearch>() {
            @Override
            public void onResponse(Call<GithubSearch> call, Response<GithubSearch> response) {

                GithubSearch githubSearch = response.body();
                if(githubSearch!=null) {
                    Picasso.get().load(githubSearch.avatar_url).into(imageView);
                    textView.setText(githubSearch.login);
                    constraintLayout.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
                bundle.putString(USERNAME, searchItem);
            }

            @Override
            public void onFailure(Call<GithubSearch> call, Throwable t) {

            }
        });
    }

}
