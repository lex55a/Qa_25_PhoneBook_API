package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdOkhttp {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyYUBnbWFpbC5jb20iLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTcyMDcwOTk3MywiaWF0IjoxNzIwMTA5OTczfQ.d3pWeHDg4qgRwBmJkTVPdQxv_OWTR0mggCTIE_yqn0M";
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    String id;

    @BeforeMethod
    public void preCondition() throws IOException {
        //create contact
        int i = new Random().nextInt(1000) + 1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Maya")
                .lastName("Sow")
                .phone("12345446" + i)
                .email("maya" + i + "@gmail.com")
                .address("NY")
                .description("Friend")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        MessageDTO messageDTO = gson.fromJson(response.body().string(), MessageDTO.class);
        //get id from message: "Contact was added! ID: 1016f5c7-bb58-4a2a-b40e-29043885324e"
        String message = messageDTO.getMessage(); //"Contact was added! ID: 1016f5c7-bb58-4a2a-b40e-29043885324e"
        String[] all = message.split(": ");
        //id=""
        id = all[1];
        System.out.println(id);

    }


    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)
                .delete()
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 200);
        MessageDTO dto = gson.fromJson(response.body().string(), MessageDTO.class);
        System.out.println(dto.getMessage());
        Assert.assertEquals(dto.getMessage(), "Contact was deleted!");
    }


}

//
//245a057e-2345-4acd-b44b-1f4ad80b7968
//stark1465@gmail.com
//=============================================
//        90c2a2e7-4fd2-4702-9441-cd119cf57d44
//stark1268@gmail.com
//=============================================
//        80c083e9-7c1d-42ee-aed6-3aecfc56ac51
//stark2239@gmail.com
//=============================================
//        3fefbcd6-ae71-4fd4-9766-12d3805514c7
//stark2279@gmail.com
//=============================================