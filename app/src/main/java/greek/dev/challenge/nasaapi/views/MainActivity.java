package greek.dev.challenge.nasaapi.views;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import greek.dev.challenge.nasaapi.R;
import greek.dev.challenge.nasaapi.adapter.ResultsAdapter;
import greek.dev.challenge.nasaapi.model.MyNasaItem;
import greek.dev.challenge.nasaapi.viewmodel.MainListViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ResultsAdapter.NasaResultsAdapterOnClickHandler {
    private MainListViewModel viewModel;
    @BindView(R.id.rv_results)
    public RecyclerView rvNasa;
    @BindView(R.id.todo_list_empty_view)
    public LinearLayout emptyView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;
    @BindView(R.id.nav_view)
    public NavigationView navigationView;
    @BindView(R.id.search_button)
    public Button searchButton;
    @BindView(R.id.edit_query)
    public EditText searchEdit;
    @BindString(R.string.nasa_api_link)
    public String nasaLink;

    private ResultsAdapter mNasaAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //recyclerview
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        rvNasa.setLayoutManager(mStaggeredGridLayoutManager);
        mNasaAdapter = new ResultsAdapter(this);
        rvNasa.setAdapter(mNasaAdapter);

        //navigation drawer and actionbar settings
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayShowHomeEnabled(false);


        viewModel = ViewModelProviders.of(this).get(MainListViewModel.class);
        checkForInternet();
        viewModel.getItemsListObservable().observe(MainActivity.this, new Observer<List<MyNasaItem>>() {
            @Override
            public void onChanged(@Nullable List<MyNasaItem> myNasaItemsList) {
                if (myNasaItemsList.size() > 0) {
                    emptyView.setVisibility(View.GONE);
                }else{
                    emptyView.setVisibility(View.VISIBLE);
                }
                    //Log.v("main",myNasaItemsList.toString());
                mNasaAdapter.setNasaRvResults(myNasaItemsList);
                runLayoutAnimation(rvNasa);
            }
        });
    }
    @OnClick(R.id.search_button)
    public void startQuery(View view){
        checkForInternet();
        viewModel.getMyNasaItemsList(searchEdit.getText().toString());
    }
    // Animation RecyclerView
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.rv_layout_animation);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_goto_nasa) {
            goToNasa();
        } else if (id == R.id.nav_share) {
            shareApp(this);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(MyNasaItem selectedNasaItem) {
        Context context = this;
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("item", selectedNasaItem);
        //intent.putExtra(Intent.EXTRA_TEXT, ""+selectedNasaItem.getNasaId());
        startActivity(intent);
    }

    private void goToNasa(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(nasaLink));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        final String appName = context.getString(R.string.app_name);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBodyText = context.getString(R.string.google_play_prefix) +
                appPackageName;
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_text)));
    }

    private void  checkForInternet(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        viewModel.setInternetState( netInfo != null && netInfo.isConnectedOrConnecting());
        //viewModel.getMyNasaItemsList("apollo");
    }
}
