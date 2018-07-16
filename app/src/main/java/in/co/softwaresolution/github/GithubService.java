package in.co.softwaresolution.github;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    @GET("{user}")
    Call<GithubSearch> getRepos(@Path("user") String user);

    @GET("{user}/repos")
    Call<ArrayList<RepositorySearch>> getUserRepos(@Path("user") String user);

    @GET("{user}/followers")
    Call<ArrayList<FollowerSearch>> getUserFollowers(@Path("user") String user);

}
