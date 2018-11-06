# DMNetworking

### About DMNetworking Library
DMNetworking Library is a powerful library for doing any type of networking in Android applications which is made on top of [AsyncHttpClient](http://loopj.com/android-async-http/) and [Jackson JSON parser](https://github.com/FasterXML/jackson)

### How to integrate
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
Add the dependency

		dependencies {
		   implementation 'com.github.pmbfish40:DMNetworking:1.1.1'
		}
	
If you're using Maven:

Add the JitPack repository to your build file

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
Add the dependency
	
	<dependency>
	    <groupId>com.github.pmbfish40</groupId>
	    <artifactId>DMNetworking</artifactId>
	    <version>1.1.1</version>
	</dependency>
	
### Introduction

DMNetworking Library takes care of everything. All you have to do just make request and listen response.
You can listen response in different ways:

* Simple JSON response
* Parsed Object response
* LiveData response
* Error JSON response
* Error Object response
* Error LiveData response
* No internet connection handling

If you don't know about [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) I suggest to read about this.

### Why use DMNetworking?
* Build your request by creating your own request **config**
* Google announced **LiveData** and DMNetworking library gives you fastest way to get parse data converted to LiveData.
* You can get response as an **Object** as well without LiveData.
* If you want to do your own parsing you can do it as well using **JSONObject** response.
* Error handling is easier than you think, all you have to do just create a class and DMNetworking will give your **parsed error Object** as a response.
* If you want to hande error response by yourself you can do it by using **error JSONObject response**.
* You can set request parameters as **HashMap** or as **JSONObject**
* If you have *nested or multi level JSONResponse** you can set your path to parse exactly onto object. You don't need to create a lot of class at all. Create only **one class** for parsing object
* **Custom status handling:** Sometimes all response has status code 200 and all logic using custom statuses. DMNetworking gives you a way to handle this as well.
* If there is **no internet connection** before and during the request DMNetworking can tell you about this. You don't need to write code for checking is there an internet do a request or something else. If you want load your data from database if there is no internet connection you can do it using **no internet connection** listener.
* You can **upload** and **download** file in a simple way.
* During the upload and download you can get **realtime updates by percentage.** It can help you to show progress directly.
* Automatically updating **the token(API token or JWT token)**
* If all your requests shoud send **same params** you can set it up as well. For example *deviceType=android* or *deviceType=ios* 
* Easily set ***headers*** and remove ***headers***
* Cancell request by tag
* Cancell all request


### Find this project useful?
Support us by clicking star button on the upper right of this page.

### Usage

1. Create your networking class it should be extended from ***DMBaseRequest***

			public class ExampleNetworking extends DMBaseRequest {
		
		    private static ExampleNetworking ourInstance;
		
		    private ExampleNetworking() {
		    }
		
		    public static ExampleNetworking getInstance() {
		        if (ourInstance == null) {
		            ourInstance = new ExampleNetworking();
		        }
		
		        return ourInstance;
		    }
		
		    @Override
		    protected void handleStatuses(final Context context, final int statusCode, final JSONObject jsonObject, final DMIStatusHandleListener listener) {
		        try {
		            String status = "";
		            if (jsonObject != null) {
		                status = jsonObject.getString("status");
		            }
		            switch (status) {
		                case "INVALID_DATA":
		                    listener.onError(status, jsonObject);
		                    break;
		                case "REFRESH_TOKEN":
		                    listener.onTokenUpdate();
		                    break;
		                default:
		                    listener.onComplete(status, jsonObject);
		                    break;
		            }
		        } catch (JSONException e) {
		            e.printStackTrace();
		            listener.onError(e.getMessage(), jsonObject);
		        }
		    }
		
		    @Override
		    protected boolean isNeedToMakeRequest(final Context context, final DMINetworkListener listener) {
		        return super.isNeedToMakeRequest(context, listener);
		    }
		
		    @Override
		    protected int getRequestTimeOut() {
		        return 20000;
		    }
		
		    @Override
		    protected String getFullUrl(final String methodUrl) {
		//        final String fullUrl = "BASE_URL" + "/api/" + methodUrl + "?deviceType=android&applicationId=123&applicationVersion=123&deviceScale=3x";
		        final String fullUrl = methodUrl;
		        if (true) {     // isUserLoggedIn
		            return fullUrl + "&jwt=" + "DLDHNRGSKCHNDKKD";          //token
		        }
		
		        return fullUrl;
		    }
		
		    @Override
		    public String getTagForLogger() {
		        return "API";
		    }
		
		    @Override
		    public boolean isEnableLogger() {
		        return true;
		    }
		
		    @Override
		    public DMBaseTokenHandler onTokenRefresh() {
		        return new DMBaseTokenHandler("refreshUrl") {
		
		            @Override
		            protected void onTokenRefreshed(final Context context, final JSONObject jsonObject) {
		                //save token in preference
		            }
		
		            @Override
		            protected void onTokenRefreshFailure(final Context context, final JSONObject jsonObject) {
		
		            }
		
		            @Override
		            protected void onNoInternetConnection(final Context context) {
		
		            }
		        };
	    	}
		}
		
	
### Overeide Method description
Set your request timeout in milliseconds

	@Override
    protected int getRequestTimeOut() {
        return 20000;
    }

You can set it your own logic when do request. If you do not have custom cases you can let it default.

	 @Override
	    protected boolean isNeedToMakeRequest(final Context context, final DMINetworkListener listener) {
	        return super.isNeedToMakeRequest(context, listener);
	    }
	    
	
You can enable/disable logger and set your own tag. You can change your logger string by calling ***setRequestTag("your-tag")*** from configs

	@Override
	    public String getTagForLogger() {
	        return "API";
	    }
	
	    @Override
	    public boolean isEnableLogger() {
	        return true;
	    }
	    
	    
	    
Handle your status in handleStatuses method: Here you can find custom handling example:

	  @Override
	    protected void handleStatuses(final Context context, final int statusCode, final JSONObject jsonObject, final DMIStatusHandleListener listener) {
	        try {
	            String status = "";
	            if (jsonObject != null) {
	                status = jsonObject.getString("status");
	            }
	            switch (status) {
	                case "INVALID_DATA":
	                    listener.onError(status, jsonObject);
	                    break;
	                case "REFRESH_TOKEN":
	                    listener.onTokenUpdate();
	                    break;
	                default:
	                    listener.onComplete(status, jsonObject);
	                    break;
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();
	            listener.onError(e.getMessage(), jsonObject);
	        }
	    }
	    
	    
	    
Build your own URL: You can set some GET params to send with every request

	@Override
    protected String getFullUrl(final String url) {

        final String fullUrl = "BASE_URL" + "/api/" + url + "?deviceType=android&applicationId=123&applicationVersion=123&deviceScale=3x";

        return fullUrl;
    }
    
If you want to use default url building just return the url:

		@Override
	    protected String getFullUrl(final String url) {	
	        return url;
	    }
	    
if you're setting url from configs **setUrl("your-url")** than DMNetworking will call **getFullUrl()** and it will build your request url.

If you're setting **setFullUrl("your-url")** than DMNetworking **NOT** calling **getFullUrl()**. It gives you chance to set you're own url if you want.

### Setup configs 

For example if we have this json response

	{
		{
		  	"status": "OK",
	  		"data": {
	    	"first_name": "John";
	    	"last_name": "Smith";
	    	"id":"1"
	  	}
	}
	
and we want to parse it, we have to create  User class

	public class User {

    private String first_name;
    private String last_name;
    private int id;

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getId() {
        return id;
    }
}


and call  **setParserConfigs(new DMParserConfigs<>(User.class, "data"))**

Notice that we passing *'data'* as params. If you have nested or multi-level JSONResponse you can set array value like shown below:

**setParserConfigs(new DMParserConfigs<>(User.class, "data", "user", "value"))**

As you see you don't need to change or create any class to parse nested JSON data. DMNetworking automatically detecting JSONObject type and parsing it on User.class object if you set to right path ***DMParserConfigs***.

***Note: DMNetworking's parsing automitacally detecting json response(you have to set right path for parsing) is it JSONObject or JSONArray and give you back the result*** 

Same behaviour goes to on Error handling. The only difference is you have set configs by calling

**setErrorParserConfigs(new DMParserConfigs<>(RequestError.class, "error","data"));**


Now we can see more detailed examples in below:

***Making a GET request***

	final String url = "your-url";
	final DMBaseRequestConfig<User, RequestError> config = new DMBaseRequestConfig<User, RequestError>(getApplicationContext())
	                .setUrl(url)
	                .setMethod(DMINetworkingConstants.Method.GET)
	                .setParserConfigs(new DMParserConfigs<>(User.class, "data"))
	                .setErrorParserConfigs(new DMParserConfigs<>(RequestError.class));

	//set configs to requester class	                
	final DMLiveDataBag<User, RequestError> request = ExampleNetworking.getInstance().request(config);


***Making a POST request with params***

	final String url = "your-url";
	 final Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        params.put("name", "John Smith");
        
     final DMBaseRequestConfig<User, RequestError> config = new DMBaseRequestConfig<User, RequestError>(getApplicationContext())
                .setUrl(url)
                .setMethod(DMINetworkingConstants.Method.POST)
                .setParams(params)
                .setParserConfigs(new DMParserConfigs<>(User.class, "data"))
                .setErrorParserConfigs(new DMParserConfigs<>(RequestError.class));
                
                //set configs to requester class	                
	ExampleNetworking.getInstance().request(config, new DMINetworkListener<User, RequestError>() {});


***Making a POST request with JSON body***

	final JSONObject jsonObject = new JSONObject();
	        try {
	            jsonObject.put("id", "1");
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        
	         final DMBaseRequestConfig<User, RequestError> config = new DMBaseRequestConfig<User, RequestError>(getApplicationContext())
                .setUrl(url)
                .setJson(jsonObject)
                .setMethod(DMINetworkingConstants.Method.POST)
                .setParserConfigs(new DMParserConfigs<>(User.class, "data","obj"))
                .setErrorParserConfigs(new DMParserConfigs<>(RequestError.class));
                
          //set configs to requester class	                
	final DMLiveDataBag<User, RequestError> request = ExampleNetworking.getInstance().request(config);
	

***Making a Multipart request with other params***

		File file = new File("file_path);
		final Map<String, Object> params = new HashMap<>();
		params.put("imageName", "hello_image_file");
		params.put("image", file);
		
	final DMBaseRequestConfig<User, RequestError> config = new DMBaseRequestConfig<User, RequestError>(getApplicationContext())
	                .setUrl(url)
	                .setParams(params)
	                .setMethod(DMINetworkingConstants.Method.POST)
	                .setParserConfigs(new DMParserConfigs<>(User.class, "data"))
	                .setErrorParserConfigs(new DMParserConfigs<>(RequestError.class));
	                
	final DMLiveDataBag<User, RequestError> request = ExampleNetworking.getInstance().request(config);
	
	
***Download file and see progress of downloading***
											
	final String imageUrl = "file-url";

	 final DMBaseRequestConfig<User, RequestError> config = new DMBaseRequestConfig<User, RequestError>(getApplicationContext())
	                .setFullUrl(imageUrl)
	                .setParams(params)
	                .setEnableDownload(true)
	                .setMethod(DMINetworkingConstants.Method.POST);
	                
	 ExampleNetworking.getInstance().request(config, new DMINetworkListener<User, RequestError>() {
	  	   @Override
            public void onFileProgress(final FileProgress fileProgress) {
					System.out.println(fileProgress.getPercent());
               	    System.out.println(fileProgress.getPercentString());
            }
      });
      
      

If you're using ***LiveData*** you can see how to handle the responses.

Full example of making a request and get responses.

	final DMBaseRequestConfig<User, RequestError> config = new DMBaseRequestConfig<User, RequestError>(getApplicationContext())
	                .setUrl(url)
	                .setMethod(DMINetworkingConstants.Method.POST)
	                .setParserConfigs(new DMParserConfigs<>(User.class, "data"))
	                .setErrorParserConfigs(new DMParserConfigs<>(RequestError.class))
	                .setRequestTag("myTag");

	final DMLiveDataBag<User, RequestError> request = ExampleNetworking.getInstance().request(config);

        request.getSuccessT().observe(this, userSuccessT -> {

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



If you do not want get results in LiveData you can get it on a simple way as shown below:

Full example of making a request and get responses.

	 final Map<String,String> params = new HashMap<>();
	        params.put("id","2");
	        params.put("name","John");

	final DMBaseRequestConfig<User, RequestError> config = new DMBaseRequestConfig<User, RequestError>(getApplicationContext())
	                .setFullUrl(url)
	                .setMethod(DMINetworkingConstants.Method.POST)
	                .setParams(params)
	                .setRequestTag("tag")
	                .setParserConfigs(new DMParserConfigs<>(User.class, "data"))
	                .setErrorParserConfigs(new DMParserConfigs<>(RequestError.class));
	                
	                
	                   ExampleNetworking.getInstance().request(config, new DMINetworkListener<User, RequestError>() {

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
            public void onComplete(final int statusCode, final JSONObject response) {

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
