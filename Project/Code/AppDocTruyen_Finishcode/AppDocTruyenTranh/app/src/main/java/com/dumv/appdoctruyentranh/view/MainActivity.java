package com.dumv.appdoctruyentranh.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.dumv.appdoctruyentranh.R;
import com.dumv.appdoctruyentranh.SendData;
import com.dumv.appdoctruyentranh.SendDataDelete;
import com.dumv.appdoctruyentranh.adapter.TruyenTranhAdapter;
import com.dumv.appdoctruyentranh.api.ApiDeleteYeuThich;
import com.dumv.appdoctruyentranh.api.ApiInsertYeuThich;
import com.dumv.appdoctruyentranh.api.ApiLayTruyen;
import com.dumv.appdoctruyentranh.api.RetrofitClient;
import com.dumv.appdoctruyentranh.interfaces.LayTruyenVe;
import com.dumv.appdoctruyentranh.model.ResponseBodyDTO;
import com.dumv.appdoctruyentranh.model.YeuThich;
import com.dumv.appdoctruyentranh.object.TruyenTranh;
import com.dumv.appdoctruyentranh.util.StoreUtil;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements LayTruyenVe {
    GridView gdvDSTruyen;
    TruyenTranhAdapter adapter;
    ArrayList<TruyenTranh> truyenTranhArrayList;
    EditText edtTimKiem;
    ImageView imgStar;
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        anhXa();
        setUp();
        setClik();
        new ApiLayTruyen(this).execute();
        toolbar = findViewById(R.id.toolBarMain);
        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle("");

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menuYeuThich:
                                Intent intent = new Intent(getApplicationContext(), YeuThichActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.menuDangXuat:
                                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent1);
                            case R.id.menuTrangChu:
                                drawerLayout.closeDrawers();
                                return true;
                        }
                        return false;
                    }
                });

        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        imgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), YeuThichActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        truyenTranhArrayList = new ArrayList<>();
        imgStar = findViewById(R.id.imgYeuThich);
        adapter = new TruyenTranhAdapter(this, 0, truyenTranhArrayList, new SendData() {
            @Override
            public void send(TruyenTranh truyenTranh) {
                insertYeuThich(truyenTranh);
            }
        }, new SendDataDelete() {
            @Override
            public void sendData(TruyenTranh truyenTranh) {
                delete(truyenTranh);
            }
        });
    }

    private void anhXa() {
        gdvDSTruyen = findViewById(R.id.gdvDSTruyen);
        edtTimKiem = findViewById(R.id.edtTimKiem);
    }

    private void setUp() {
        gdvDSTruyen.setAdapter(adapter);
    }

    private void setClik() {
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = edtTimKiem.getText().toString();
                adapter.sortTruyen(s);
            }
        });
        gdvDSTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TruyenTranh truyenTranh = truyenTranhArrayList.get(i);
                Bundle b = new Bundle();
                b.putSerializable("truyen", truyenTranh);
                Intent intent = new Intent(MainActivity.this, ChapActivity.class);
                intent.putExtra("data", b);
                startActivity(intent);
            }
        });
    }

    @Override
    public void batDau() {
        Toast.makeText(this, "Loading!!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        try {
            truyenTranhArrayList.clear();
            JSONArray arr = new JSONArray(data);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                truyenTranhArrayList.add(new TruyenTranh(o));
            }
            adapter = new TruyenTranhAdapter(this, 0, truyenTranhArrayList, new SendData() {
                @Override
                public void send(TruyenTranh truyenTranh) {
                    insertYeuThich(truyenTranh);
                }
            }, new SendDataDelete() {
                @Override
                public void sendData(TruyenTranh truyenTranh) {
                    delete(truyenTranh);
                }
            });
            gdvDSTruyen.setAdapter(adapter);
        } catch (JSONException e) {

        }
    }

    @Override
    public void biLoi() {
        Toast.makeText(this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();

    }

    public void insertYeuThich(TruyenTranh truyenTranh) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = RetrofitClient.getClient()
                .create(ApiInsertYeuThich.class)
                .insertYeuThich(truyenTranh.getTenTruyen(), truyenTranh.getTenChap(), truyenTranh.getLinkAnh(),
                        truyenTranh.getId(), StoreUtil.get(getApplicationContext(), "id"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<ResponseBodyDTO>() {
                    @Override
                    public void accept(ResponseBodyDTO responseBodyDTO) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        Toast.makeText(getApplicationContext(), "Thêm thành công!!!", Toast.LENGTH_SHORT).show();
        compositeDisposable.add(disposable);
    }

    public void delete(TruyenTranh truyenTranh) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = RetrofitClient.getClient()
                .create(ApiDeleteYeuThich.class)
                .deleteYeuThich(truyenTranh.getId(), StoreUtil.get(getApplicationContext(), "id"))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<ResponseBodyDTO>() {
                    @Override
                    public void accept(ResponseBodyDTO responseBodyDTO) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
        Toast.makeText(getApplicationContext(), "Xóa thành công!!!", Toast.LENGTH_SHORT).show();
        compositeDisposable.add(disposable);

    }

    public void update(View view) {
        edtTimKiem.setText("");
        new ApiLayTruyen(this).execute();
    }
}
