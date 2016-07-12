package in.ureport.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.ureport.BuildConfig;
import in.ureport.flowrunner.models.FlowDefinition;
import in.ureport.flowrunner.models.FlowRun;
import in.ureport.flowrunner.models.FlowStepSet;
import in.ureport.helpers.GsonDateTypeAdapter;
import in.ureport.helpers.HashMapTypeAdapter;
import in.ureport.models.rapidpro.Boundary;
import in.ureport.flowrunner.models.Contact;
import in.ureport.models.rapidpro.Field;
import in.ureport.models.rapidpro.Group;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by johncordeiro on 18/08/15.
 */
public class RapidProServices {

    private static final String TAG = "RapidProServices";

    private final String endpoint;
    private final RapidProApi service;

    private GsonDateTypeAdapter gsonDateTypeAdapter;

    public RapidProServices(String endpoint) {
        this.endpoint = endpoint;
        RestAdapter restAdapter = buildRestAdapter();
        if(BuildConfig.DEBUG) restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        service = restAdapter.create(RapidProApi.class);
    }

    public Response<Boundary> loadBoundaries(String apiKey, Integer page) {
        return service.listBoundaries(apiKey, page, true);
    }

    public List<Field> loadFields(String apiKey) {
        Response<Field> response = service.listFields(apiKey);
        return response.getResults();
    }

    public List<FlowRun> loadRuns(String apiKey, String userUuid, Date after) {
        Response<FlowRun> response = service.listRuns(apiKey, userUuid, gsonDateTypeAdapter.serializeDate(after));
        return response.getResults();
    }

    public FlowDefinition loadFlowDefinition(String apiKey, String flowUuid) {
        FlowDefinition flowDefinition = service.loadFlowDefinition(apiKey, flowUuid);
        flowDefinition.getMetadata().setUuid(flowUuid);
        return flowDefinition;
    }

    public Contact loadContact(String apiKey, String urn) {
        Response<Contact> response = service.loadContact(apiKey, urn);
        return response.getCount() > 0 ? response.getResults().get(0) : null;
    }

    public List<Group> loadGroups(String apiKey, String contact) {
        Response<Group> response = service.listGroups(apiKey, contact);
        return response.getResults();
    }

    public void sendReceivedMessage(String apiKey, String channel, String from, String text) {
        service.sendReceivedMessage(apiKey, channel, from, text);
    }

    public void saveFlowStepSet(String apiKey, FlowStepSet flowStepSet) {
        service.saveFlowStepSet(apiKey, flowStepSet);
    }

    public Contact saveContact(String apiKey, Contact contact) {
        return service.saveContact(apiKey, contact);
    }

    private RestAdapter buildRestAdapter() {
        gsonDateTypeAdapter = new GsonDateTypeAdapter();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, gsonDateTypeAdapter)
                .registerTypeAdapter(HashMap.class, new HashMapTypeAdapter())
                .create();

        return new RestAdapter.Builder()
                    .setEndpoint(endpoint)
                    .setConverter(new GsonConverter(gson))
                    .build();
    }

    private String getRapidproUserId(String userId) {
        return userId.replace(":", "").replace("-", "");
    }

}
