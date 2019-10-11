package networkexample.md.com.networkexample;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.dm.dmnetworking.DMNetworkBaseRequestConfig;
import com.dm.dmnetworking.DMNetworkIConstants;
import com.dm.dmnetworking.DMNetworkIListener;
import com.dm.dmnetworking.DMNetworkLiveDataBag;
import com.dm.dmnetworking.DMNetworkParserConfigs;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import networkexample.md.com.networkexample.model.RequestError;
import networkexample.md.com.networkexample.model.User;
import networkexample.md.com.networkexample.networking.ExampleNetworking;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if ( ContextCompat.checkSelfPermission( this, Manifest.permission.READ_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED ) {
//        ArrayList<String> list = getAllShownImagesPath(this);
//        }

        init();
    }

    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

    private void init() {

//        final String url = "http://www.mocky.io/v2/5bf3ee4f3100008f00619a63"; //object
//        final String url = "http://www.mocky.io/v2/5bf3eef43100006400619a6c"; //array
        final String url = "5bf3ef643100006800619a6f"; //array deep
//        final String fullUrl = "http://www.mocky.io/v2/5bf3ef643100006800619a6f"; //array deep
//        final String url = "http://www.mocky.io/v2/5bcf2bf33300008200c2486a"; //error list
//        final String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/220px-Image_created_with_a_mobile_phone.png";

//        File file = new File("/storage/emulated/0/DCIM/Camera/IMG_20181025_184604.jpg");
//
//        final Map<String, Object> params = new HashMap<>();
//        params.put("a", "aaaaa");
//        params.put("b", file);

        final Map<String, Object> params = new HashMap<>();
        params.put("id", "2");
        params.put("name", "John");


//        final JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("id", "1");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        final DMNetworkBaseRequestConfig<User, RequestError> config = new DMNetworkBaseRequestConfig<User, RequestError>(getApplicationContext())
//                .setFullUrl(fullUrl)
//                .setFullUrl("http://www.mocky.io/v2/5d0ca9853500005200b899de")
                .setUrl(url)
                .setMethod(DMNetworkIConstants.Method.POST)
                .setParams(params)
                .setRequestTag("tag")
                .setParserConfigs(new DMNetworkParserConfigs<>(User.class, "data", "list"))
//                .setParserConfigs(new DMNetworkParserConfigs<>())
                .setErrorParserConfigs(new DMNetworkParserConfigs<>(RequestError.class));


        final DMNetworkLiveDataBag<User, RequestError> request = ExampleNetworking.getInstance().request(config);

        request.getSuccessT().observe(this, userSuccessT -> {

        });

        request.getSuccessMapT().observe(this, userSuccessMapT -> {

        });

        request.getSuccessJsonResponse().observe(this, jsonObject1 -> {

        });

        request.getSuccessListT().observe(this, userSuccessListT -> {

        });

        request.getSuccessResponse().observe(this, successResponse -> {

        });

        request.getErrorE().observe(this, requestErrorErrorE -> {

        });

        request.getErrorJsonResponse().observe(this, jsonObject12 -> {

        });

        request.getErrorResponse().observe(this, errorResponse -> {

        });

        request.getNoInternetConnection().observe(this, s -> {

        });

        request.getSuccessFile().observe(this, file -> {

        });

        request.getFileProgress().observe(this, fileProgress -> {

        });


        ExampleNetworking.getInstance().request(config, new DMNetworkIListener<User, RequestError>() {

            @Override
            public void onComplete(final int statusCode, final String status, final JSONObject response) {

            }

            @Override
            public void onComplete(final int statusCode, final String status, final User user) {

            }

            @Override
            public void onComplete(final int statusCode, final String status, final List<User> userList) {

            }

            @Override
            public void onComplete(final int statusCode, final String status, final Map<String, User> stringUserMap) {

            }

            @Override
            public void onComplete(final int statusCode, final JSONObject response) {

            }

            @Override
            public void onComplete(final int statusCode, final File file) {

            }


            @Override
            public void onError(final int statusCode, final String status, final JSONObject response) {

            }

            @Override
            public void onError(final int statusCode, final String status, final RequestError error) {

            }

            @Override
            public void onError(final int statusCode, final JSONObject response) {

            }

            @Override
            public void onNoInternetConnection() {

            }
        });
    }
}
