package intern.ecollabcad;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class changeRequest extends StringRequest {
    private static final String CHANGE_URL ="http://192.168.43.196:8080/Server/changePassword";
    private Map<String, String> params;


    public changeRequest(String userid, String password, Response.Listener<String> listener)
    {
        super(Method.POST, CHANGE_URL, listener, null);
        params= new HashMap<>();

        params.put("userid",userid);
        params.put("new_password",password);

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
