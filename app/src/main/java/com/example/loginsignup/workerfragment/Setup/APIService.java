package com.example.loginsignup.workerfragment.Setup;

import com.example.loginsignup.model._Client;
import com.example.loginsignup.model._User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAmJQXyVs:APA91bH7P5fW88SSecFkZs40xXmp7etAYmhkxYc1Xusp8IcnzitCWD3dOUg9chsh7gUW20n5z8hO7mqYdRcYeo_DDRo7r9HDStbktwStzqlW1U4021BgcloO3R_FPFpENA5h0AHtL_np"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender sender);
}
