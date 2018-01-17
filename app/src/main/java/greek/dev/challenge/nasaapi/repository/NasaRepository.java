package greek.dev.challenge.nasaapi.repository;


import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import greek.dev.challenge.nasaapi.model.Datum;
import greek.dev.challenge.nasaapi.model.Link;
import greek.dev.challenge.nasaapi.model.MyNasaItem;
import greek.dev.challenge.nasaapi.model.NasaItem;
import greek.dev.challenge.nasaapi.model.NasaResponse;
import greek.dev.challenge.nasaapi.model.ResponseCollection;
import greek.dev.challenge.nasaapi.room.NasaDAO;
import greek.dev.challenge.nasaapi.room.NasaDB;
import greek.dev.challenge.nasaapi.webservice.NasaService;
import greek.dev.challenge.nasaapi.webservice.NasaUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by programbench on 1/14/2018.
 */

public class NasaRepository {
    private static NasaRepository mNasaRepository;
    //retrofit
    private NasaService mNasaService;
    //room
    private NasaDAO mNasaDAO;

    private NasaRepository() {
        mNasaService = NasaUtils.getNasaService();
        mNasaDAO = NasaDB.getDatabase().getNasaDAO();
    }

    public synchronized static NasaRepository getInstance() {
        //TODO  implement this singleton with Dagger2
        if (mNasaRepository == null) {
            mNasaRepository = new NasaRepository();
        }
        return mNasaRepository;
    }

    public MutableLiveData<List<MyNasaItem>> getItemsListFromWeb(String nasaQuery) {
        final MutableLiveData<List<MyNasaItem>> retlist = new MutableLiveData<>();
        mNasaService.getItems(nasaQuery).enqueue(new Callback<NasaResponse>() {
            List<MyNasaItem> itemsData = new ArrayList<MyNasaItem>();
            List<NasaItem> data = new ArrayList<NasaItem>();
            Datum datum = new Datum();
            Link link = new Link();

            @Override
            public void onResponse(Call<NasaResponse> call, Response<NasaResponse> response) {
                ResponseCollection resp = response.body().getCollection();
                data.addAll(resp.getItems());
                for (NasaItem item : data) {
                    datum = item.getData().get(0);
                    try {
                        link = item.getLinks().get(0);
                    } catch (NullPointerException e) {
                        link.setHref("");
                        link.setRel("");
                    }
                    if (link.getRel().equalsIgnoreCase("preview")) {
                        MyNasaItem mnasaItem = new MyNasaItem(datum.getNasaId(),
                                datum.getDescription(),
                                datum.getTitle(),
                                datum.getCenter(),
                                datum.getDateCreated(),
                                link.getHref());
                        itemsData.add(mnasaItem);
                    }
                }
                retlist.setValue(itemsData);
                addAllToDB(itemsData);
            }

            @Override
            public void onFailure(Call<NasaResponse> call, Throwable t) {
                Log.v("main", t.getLocalizedMessage());
            }
        });

        return retlist;
    }

    private MutableLiveData<List<MyNasaItem>> getItemsListFromDB(final String nasaQuery) {
        final MutableLiveData<List<MyNasaItem>> retlist = new MutableLiveData<>();
        new AsyncTask<Void, Void, List<MyNasaItem>>() {
            @Override
            protected List<MyNasaItem> doInBackground(Void... params) {
                // return mNasaDAO.getAllEntries();
                return mNasaDAO.getEntriesContaining(nasaQuery);
            }

            @Override
            protected void onPostExecute(List<MyNasaItem> entries) {

                retlist.setValue(entries);
            }
        }.execute();
        return retlist;
    }

    public void addAllToDB(List<MyNasaItem> items) {
        new AsyncTask<List<MyNasaItem>, Void, Void>() {
            @Override
            protected Void doInBackground(List<MyNasaItem>... params) {
                for (MyNasaItem item : params[0]) {
                    mNasaDAO.insertEntry(item);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void a) {

                //loadEntries();
            }
        }.execute(items);
    }

    public MutableLiveData<List<MyNasaItem>> getMyNasaItemsList(String query, boolean internetState) {
        //final  MutableLiveData<List<MyNasaItem>> data = new MutableLiveData<>();
        //data.setValue(getItemsListFromWeb(query));
        if (internetState) {
            return getItemsListFromWeb(query);
        } else {
            return getItemsListFromDB("%" + query + "%");
        }
    }
}
