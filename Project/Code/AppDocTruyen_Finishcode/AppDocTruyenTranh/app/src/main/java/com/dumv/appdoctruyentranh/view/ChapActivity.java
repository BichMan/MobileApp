package com.dumv.appdoctruyentranh.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dumv.appdoctruyentranh.R;
import com.dumv.appdoctruyentranh.adapter.ChapTruyenAdapter;
import com.dumv.appdoctruyentranh.adapter.MessageAdapter;
import com.dumv.appdoctruyentranh.api.ApiChapTruyen;
import com.dumv.appdoctruyentranh.api.ApiCommentInsert;
import com.dumv.appdoctruyentranh.api.ApiGetBL;
import com.dumv.appdoctruyentranh.api.ApiInsertYeuThich;
import com.dumv.appdoctruyentranh.api.RetrofitClient;
import com.dumv.appdoctruyentranh.interfaces.LayChapVe;
import com.dumv.appdoctruyentranh.model.Message;
import com.dumv.appdoctruyentranh.model.ResponseBodyDTO;
import com.dumv.appdoctruyentranh.object.ChapTruyen;
import com.dumv.appdoctruyentranh.object.TruyenTranh;
import com.dumv.appdoctruyentranh.util.StoreUtil;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChapActivity extends AppCompatActivity implements LayChapVe {
    TextView txvTenTruyens;
    ImageView imgAnhTruyens;
    TruyenTranh truyenTranh;
    ListView lsvDanhSachChap;
    ArrayList<ChapTruyen> arrChap;
    ChapTruyenAdapter chapTruyenAdapter;
    FloatingActionButton floatingActionButton;
    RecyclerView recyclerViewComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiChapTruyen(this, truyenTranh.getId()).execute();
        recyclerViewComment = findViewById(R.id.recyclerViewComment);
        floatingActionButton = findViewById(R.id.fabComment);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogComment();
            }
        });

        getData();


    }

    private void init() {
        Bundle b = getIntent().getBundleExtra("data");
        truyenTranh = (TruyenTranh) b.getSerializable("truyen");

        //tao du lieu ao
        arrChap = new ArrayList<>();
      /*  for(int i=0;i<20;i++){
            arrChap.add(new ChapTruyen("Chapter "+i,"06 - 10 -2019"));
        }
        chapTruyenAdapter= new ChapTruyenAdapter(this,0,arrChap);
        */
    }

    private void anhXa() {
        imgAnhTruyens = findViewById(R.id.imgAnhTruyens);
        txvTenTruyens = findViewById(R.id.txvTenTruyens);
        lsvDanhSachChap = findViewById(R.id.lsvDanhSachChap);
    }

    private void setUp() {
        txvTenTruyens.setText(truyenTranh.getTenTruyen());
        Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgAnhTruyens);

        // lsvDanhSachChap.setAdapter(chapTruyenAdapter);
    }

    private void setClick() {
        lsvDanhSachChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle b = new Bundle();
                b.putString("idChap", arrChap.get(i).getId());
                Intent intent = new Intent(ChapActivity.this, DocTruyenActivity.class);
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
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                ChapTruyen chapTruyen = new ChapTruyen(array.getJSONObject(i));
                arrChap.add(chapTruyen);
            }
            chapTruyenAdapter = new ChapTruyenAdapter(this, 0, arrChap);
            lsvDanhSachChap.setAdapter(chapTruyenAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void biLoi() {

    }

    String date = "";

    private void showDialogComment() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_comment);
        dialog.show();

        EditText edtComment = dialog.findViewById(R.id.edtComment);
        Button btnComment = dialog.findViewById(R.id.btnComment);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = edtComment.getText().toString();
                if (comment.isEmpty()) {
                    Toast.makeText(ChapActivity.this, "Nhập bình luận", Toast.LENGTH_SHORT).show();
                } else {
                    LocalDateTime ldt = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        ldt = LocalDateTime.now();
                        date = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss", Locale.ENGLISH).format(ldt);
                    }

                    CompositeDisposable compositeDisposable = new CompositeDisposable();
                    Disposable disposable = RetrofitClient.getClient()
                            .create(ApiCommentInsert.class)
                            .insertData(StoreUtil.get(getApplicationContext(), "id"),
                                    StoreUtil.get(getApplicationContext(), "user"),
                                    edtComment.getText().toString(), truyenTranh.getId(),
                                    date)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Consumer<ResponseBodyDTO>() {
                                @Override
                                public void accept(ResponseBodyDTO responseBodyDTO) throws Exception {
                                    getData();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    getData();
                                    throwable.printStackTrace();

                                }
                            });
                    compositeDisposable.add(disposable);

                    dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }

        });
    }

    List<Message> list = new ArrayList<>();

    public void getData() {
        list.clear();
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Disposable disposable = RetrofitClient.getClient()
                .create(ApiGetBL.class)
                .getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<List<Message>>() {
                    @Override
                    public void accept(List<Message> messages) throws Exception {
                        for (Message message : messages) {
                            if (message.getIdTruyen().equals(truyenTranh.getId())) {
                                list.add(message);
                            }
                        }
                        MessageAdapter messageAdapter = new MessageAdapter(getApplicationContext(), list);
                        recyclerViewComment.setAdapter(messageAdapter);
                        recyclerViewComment.setHasFixedSize(true);
                        recyclerViewComment.setLayoutManager(new LinearLayoutManager(ChapActivity.this));

                    }
                });
        compositeDisposable.add(disposable);
    }

}
