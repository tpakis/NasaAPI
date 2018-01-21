package greek.dev.challenge.nasaapi.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.util.Log;

import java.util.List;

import greek.dev.challenge.nasaapi.model.MyNasaItem;
import greek.dev.challenge.nasaapi.repository.NasaRepository;
import timber.log.Timber;

/**
 * Created by programbench on 1/14/2018.
 */

//android viewmodel to get application context
public class MainListViewModel extends AndroidViewModel {
    private static boolean internetState = false;
    private final NasaRepository nasaRepository;
    private MediatorLiveData<List<MyNasaItem>> itemsListObservable;

    public MainListViewModel(Application application) {
        super(application);
        itemsListObservable = new MediatorLiveData<>();
        nasaRepository = NasaRepository.getInstance();

    }

    public LiveData<List<MyNasaItem>> getMyNasaItemsList(String query) {
        itemsListObservable.addSource(
                nasaRepository.getMyNasaItemsList(query, internetState),
                apiResponse -> itemsListObservable.setValue(apiResponse)
        );
        return itemsListObservable;
        //    itemsListObservable = nasaRepository.getMyNasaItemsList(query,internetState);
    }

    public void setInternetState(boolean internetState) {
        this.internetState = internetState;
        Timber.v(String.valueOf(internetState));
    }

    public LiveData<List<MyNasaItem>> getItemsListObservable() {
        return itemsListObservable;
    }

}

