package networkexample.md.com.networkexample;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.dm.dmnetworking.api_client.base.DMBaseRequestConfig;
import com.dm.dmnetworking.api_client.base.DMLiveDataBag;
import com.dm.dmnetworking.api_client.constants.DMINetworkingConstants;
import com.dm.dmnetworking.api_client.listeners.DMINetworkListener;
import com.dm.dmnetworking.parser.DMParserConfigs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import networkexample.md.com.networkexample.model.Configs;
import networkexample.md.com.networkexample.model.RequestError;
import networkexample.md.com.networkexample.networking.ExampleNetworking;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ArrayList<String> list = getAllShownImagesPath(this);

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

//        final String url = "http://www.mocky.io/v2/5bcdd38e2f00007600c855b8"; //object
//        final String url = "http://www.mocky.io/v2/5bcdd4ab2f00005900c855c4"; //array
//        final String url = "http://www.mocky.io/v2/5bcdd7c02f00006100c855d6"; //object empty
        final String url = "http://www.mocky.io/v2/5bcf2bf33300008200c2486a"; //error list


//        File file = new File("/storage/emulated/0/DCIM/Camera/IMG_20181025_184604.jpg");
//
//        final Map<String, Object> params = new HashMap<>();
//        params.put("a", "aaaaa");
//        params.put("b", file);


//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);


        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final DMBaseRequestConfig<Configs, RequestError> config = new DMBaseRequestConfig<Configs, RequestError>(getApplicationContext())
                .setUrl(url)
                .setJson(jsonObject)
                .setMethod(DMINetworkingConstants.Method.POST)
//                .setParams(params)

                .setParserConfigs(new DMParserConfigs<>(Configs.class, "data"))
                .setErrorParserConfigs(new DMParserConfigs<>(RequestError.class));


//                .setMethod(INetworkingConstants.Method.POST)
//                .setUrl(url).setParams(new HashMap<>())
//                .setParserConfigs(new ParserConfigs<>(Configs.class, "data"));


        final DMLiveDataBag<Configs, RequestError> request = ExampleNetworking.getInstance().request(config);

        request.getSuccessT().observe(this, configsSuccessT -> {

        });

        request.getSuccessJsonResponse().observe(this, jsonObject1 -> {

        });

        request.getSuccessListT().observe(this, configsSuccessListT -> {

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


        ExampleNetworking.getInstance().request(config, new DMINetworkListener<Configs, RequestError>() {

            @Override
            public void onComplete(final String status, final JSONObject response) {

            }

            @Override
            public void onComplete(final String status, final Configs configs) {

            }

            @Override
            public void onComplete(final String status, final List<Configs> configs) {

            }

            @Override
            public void onError(final String status, final JSONObject response) {

            }

            @Override
            public void onError(final String status, final RequestError error) {

            }

            @Override
            public void onError(final JSONObject response) {

            }

            @Override
            public void onNoInternetConnection() {

            }
        });
    }
}
