package com.dumv.appdoctruyentranh.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.dumv.appdoctruyentranh.R;
import com.dumv.appdoctruyentranh.adapter.AdapterYeuThich;
import com.dumv.appdoctruyentranh.api.ApiGetYeuThich;
import com.dumv.appdoctruyentranh.api.RetrofitClient;
import com.dumv.appdoctruyentranh.databinding.ActivityYeuThichBinding;
import com.dumv.appdoctruyentranh.model.YeuThich;
import com.dumv.appdoctruyentranh.object.TruyenTranh;
import com.dumv.appdoctruyentranh.util.StoreUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class YeuThichActivity extends AppCompatActivity {

    List<TruyenTranh> list = new ArrayList<>();
    ActivityYeuThichBinding binding;
    CompositeDisposable compositeDisposable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityYeuThichBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        compositeDisposable = new CompositeDisposable();
        Disposable disposable = RetrofitClient.getClient()
                .create(ApiGetYeuThich.class)
                .listYeuThich(StoreUtil.get(getApplicationContext(),"id"))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<YeuThich>>() {
                    @Override
                    public void accept(List<YeuThich> yeuThiches) throws Exception {
                        for (YeuThich yeuThich : yeuThiches) {
                            if (yeuThich.getIdUser().equals(StoreUtil.get(getApplicationContext(), "id"))) {
                                TruyenTranh truyenTranh = new TruyenTranh(yeuThich.getTenTruyen(), yeuThich.getTenChap(),
                                        yeuThich.getLinkAnh(), yeuThich.getIdTruyen());
                                list.add(truyenTranh);
                            }
                        }

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                AdapterYeuThich adapterYeuThich = new AdapterYeuThich(getApplicationContext(), list);
                                binding.recyclerView.setAdapter(adapterYeuThich);
                                binding.recyclerView.setHasFixedSize(true);
                                binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

                            }
                        });

                    }
                });
        compositeDisposable.add(disposable);

    }
}