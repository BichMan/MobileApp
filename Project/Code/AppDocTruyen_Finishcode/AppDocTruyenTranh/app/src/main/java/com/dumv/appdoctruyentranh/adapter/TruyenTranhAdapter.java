package com.dumv.appdoctruyentranh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dumv.appdoctruyentranh.R;
import com.dumv.appdoctruyentranh.SendData;
import com.dumv.appdoctruyentranh.SendDataDelete;
import com.dumv.appdoctruyentranh.api.ApiDangKy;
import com.dumv.appdoctruyentranh.api.ApiGetYeuThich;
import com.dumv.appdoctruyentranh.api.RetrofitClient;
import com.dumv.appdoctruyentranh.model.ResponseBodyDTO;
import com.dumv.appdoctruyentranh.model.YeuThich;
import com.dumv.appdoctruyentranh.object.TruyenTranh;
import com.dumv.appdoctruyentranh.util.StoreUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TruyenTranhAdapter extends ArrayAdapter<TruyenTranh> {
    private Context ct;
    private ArrayList<TruyenTranh> arr;

    SendData sendData;
    SendDataDelete sendDataDelete;

    public TruyenTranhAdapter(Context context, int resource, List<TruyenTranh> objects, SendData sendData,
                              SendDataDelete sendDataDelete) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
        this.sendData = sendData;
        this.sendDataDelete = sendDataDelete;

    }

    public void sortTruyen(String s) {
        s = s.toUpperCase();
        int k = 0;
        for (int i = 0; i < arr.size(); i++) {
            TruyenTranh t = arr.get(i);
            String ten = t.getTenTruyen().toUpperCase();
            if (ten.indexOf(s) >= 0) {
                arr.set(i, arr.get(k));
                arr.set(k, t);
                k++;
            }
        }
        notifyDataSetChanged();
    }

    boolean check = true;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_truyen, null);
        }

        if (arr.size() > 0) {
            TruyenTranh truyenTranh = this.arr.get(position);

            TextView tenTenTruyen = convertView.findViewById(R.id.txvTenTruyen);
            TextView tenTenChap = convertView.findViewById(R.id.txvTenChap);
            ImageView imgAnhtruyen = convertView.findViewById(R.id.imgAnhTruyen);
            ImageView imgStar = convertView.findViewById(R.id.imgStar);

            CompositeDisposable compositeDisposable = new CompositeDisposable();
            Disposable disposable = RetrofitClient.getClient()
                    .create(ApiGetYeuThich.class)
                    .listYeuThich(StoreUtil.get(getContext(),"id"))
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(new Consumer<List<YeuThich>>() {
                        @Override
                        public void accept(List<YeuThich> yeuThiches) throws Exception {
                            for (YeuThich yeuThich : yeuThiches) {
                                if (yeuThich.getIdUser().equals(StoreUtil.get(getContext(), "id"))
                                        && yeuThich.getIdTruyen().equals(truyenTranh.getId())) {

                                    imgStar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icons8_star_yesllow));
                                    check = false;
                                }
                            }
                        }
                    });
            compositeDisposable.add(disposable);

            imgStar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (check) {
                        check = false;
                        sendData.send(truyenTranh);
                        imgStar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icons8_star_yesllow));
                    } else {
                        check = true;
                        sendDataDelete.sendData(truyenTranh);
                        imgStar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icons8_star_black));
                    }
                }
            });

            tenTenTruyen.setText(truyenTranh.getTenTruyen());
            tenTenChap.setText(truyenTranh.getTenChap());
            Glide.with(this.ct).load(truyenTranh.getLinkAnh()).into(imgAnhtruyen);
        }
        return convertView;
    }
}
