package com.xiang.main.utils;

import android.util.Log;
import com.google.gson.Gson;
import com.xiang.lib.utils.Constant;
import com.xiang.lib.listener.ProgressRequestListener;
import com.xiang.lib.utils.ProgressRequestBody;
import com.xiang.lib.allbean.UpLoadFileBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * author : fengzhangwei
 * date : 2019/11/14
 */
public class FileUpLoadManager {

    private String FileType_Image = "image";
    private String FileType_Video = "video";

    public interface FileUpLoadCallBack {
        void onError(Throwable e);

        void onSuccess(String url);

        void onProgress(int pro, int position);
    }

    private String UPLOAD_URL = Constant.BASE_TOMACT_URL + "/upLoadFile";


    public OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .connectTimeout(1000L * 60 * 3, TimeUnit.MILLISECONDS)
                .readTimeout(1000L * 60 * 3, TimeUnit.MILLISECONDS)
                .build();
    }

    public void upLoadImageFile(List<String> imagePathList, final FileUpLoadCallBack callBack) {
        MultipartBody.Builder muBuilder = new MultipartBody.Builder();
        muBuilder.setType(MultipartBody.FORM);
        for (int i = 0; i < imagePathList.size(); i++) {
            File file = new File(imagePathList.get(i));
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
            final int finalI = i;
            ProgressRequestBody requestBody = new ProgressRequestBody(fileBody, new ProgressRequestListener() {
                @Override
                public void onRequestProgress(int pro, long contentLength, boolean done) {
                    Log.d("TAG", "pro=====" + pro + "--------position-------" + finalI);
                    if (callBack != null) {
                        callBack.onProgress(pro, finalI);
                    }
                }
            });
            muBuilder.addFormDataPart(FileType_Image, file.getName(), requestBody);
        }
        Log.d("TAG", "参数设置完毕");
        sendRequest(muBuilder.build(), callBack);
    }

    public void upLoadImageFile(String imagePath, final FileUpLoadCallBack callBack) {
        MultipartBody.Builder muBuilder = new MultipartBody.Builder();
        muBuilder.setType(MultipartBody.FORM);
        File file = new File(imagePath);
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        ProgressRequestBody requestBody = new ProgressRequestBody(fileBody, new ProgressRequestListener() {
            @Override
            public void onRequestProgress(int pro, long contentLength, boolean done) {
                Log.d("TAG", "pro=====" + pro );
                if (callBack != null) {
                    callBack.onProgress(pro, 0);
                }
            }
        });
        muBuilder.addFormDataPart(FileType_Image, file.getName(), requestBody);
        Log.d("TAG", "参数设置完毕");
        sendRequest(muBuilder.build(), callBack);
    }

    public void upLoadVideoFile(List<String> imagePathList, final FileUpLoadCallBack callBack) {
        MultipartBody.Builder muBuilder = new MultipartBody.Builder();
        muBuilder.setType(MultipartBody.FORM);
        for (int i = 0; i < imagePathList.size(); i++) {
            File file = new File(imagePathList.get(i));
            RequestBody fileBody = RequestBody.create(MediaType.parse("video/mp4"), file);
            final int finalI = i;
            ProgressRequestBody requestBody = new ProgressRequestBody(fileBody, new ProgressRequestListener() {
                @Override
                public void onRequestProgress(int pro, long contentLength, boolean done) {
                    Log.d("TAG", "pro=====" + pro + "--------position-------" + finalI);
                    if (callBack != null) {
                        callBack.onProgress(pro, finalI);
                    }
                }
            });
            muBuilder.addFormDataPart(FileType_Image, file.getName(), requestBody);
        }
        Log.d("TAG", "参数设置完毕");
        sendRequest(muBuilder.build(), callBack);
    }

    public void upLoadFile(List<String> imagePathList, final FileUpLoadCallBack callBack) {
        MultipartBody.Builder muBuilder = new MultipartBody.Builder();
        muBuilder.setType(MultipartBody.FORM);
        for (int i = 0; i < imagePathList.size(); i++) {
            String path = imagePathList.get(i);
            File file = new File(path);
            if (path.endsWith(".PNG") || path.endsWith(".png") ||
                    path.endsWith(".JPG") || path.endsWith(".jpg") ||
                    path.endsWith(".JPEG") || path.endsWith(".jpeg")) {
                RequestBody   fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                final int finalI = i;
                ProgressRequestBody requestBody = new ProgressRequestBody(fileBody, new ProgressRequestListener() {
                    @Override
                    public void onRequestProgress(int pro, long contentLength, boolean done) {
                        Log.d("TAG", "pro=====" + pro + "--------position-------" + finalI);
                        if (callBack != null) {
                            callBack.onProgress(pro, finalI);
                        }
                    }
                });
                muBuilder.addFormDataPart(FileType_Image, file.getName(), requestBody);
            } else if (path.endsWith(".rm") || path.endsWith(".rmvb") ||
                    path.endsWith(".mpeg1-4") || path.endsWith(".mov") ||
                    path.endsWith(".dat") || path.endsWith(".wmv") ||
                    path.endsWith(".avi") || path.endsWith(".3gp") ||
                    path.endsWith(".amv") || path.endsWith(".dmv") ||
                    path.endsWith(".flv") || path.endsWith(".mp4")) {
                RequestBody  fileBody = RequestBody.create(MediaType.parse("video/mp4"), file);
                final int finalI = i;
                ProgressRequestBody requestBody = new ProgressRequestBody(fileBody, new ProgressRequestListener() {
                    @Override
                    public void onRequestProgress(int pro, long contentLength, boolean done) {
                        Log.d("TAG", "pro=====" + pro + "--------position-------" + finalI);
                        if (callBack != null) {
                            callBack.onProgress(pro, finalI);
                        }
                    }
                });
                muBuilder.addFormDataPart(FileType_Video, file.getName(), requestBody);
            }

        }
        Log.d("TAG", "参数设置完毕");
        sendRequest(muBuilder.build(), callBack);
    }

    private void sendRequest(MultipartBody build, FileUpLoadCallBack callBack) {
        final Request request = new Request.Builder().url(UPLOAD_URL).post(build).build();
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                Response response = getHttpClient().newCall(request).execute();
                if (response.isSuccessful()) {
                    String json = response.body().string();
                    Log.d("TAG", "====body========" + json);
                    ArrayList<String> list = new ArrayList<>();
                    UpLoadFileBean bean = new Gson().fromJson(json, UpLoadFileBean.class);
                    if (bean.getCode() == 1) {
                        for (int i = 0; i < bean.getData().size(); i++) {
                            list.add(bean.getData().get(i).getUrl());
                        }
                        emitter.onNext(list);
                    }
                } else {
                    emitter.onError(new IllegalStateException(response.message()));
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
    }

    public Observer<List<String>> getObserver(final FileUpLoadCallBack callBack) {
        Observer<List<String>> observer = new Observer<List<String>>() {

            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(List<String> strings) {
                if (callBack != null) {
                    Log.d("TAG", "====bodyNextSize========" + strings.size());
                    for (int i = 0; i < strings.size(); i++) {
                        callBack.onSuccess(strings.get(i));
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (callBack != null) {
                    callBack.onError(e);
                }
            }

            @Override
            public void onComplete() {

            }
        };
        return observer;
    }

}
