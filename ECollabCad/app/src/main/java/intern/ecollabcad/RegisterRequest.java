package intern.ecollabcad;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{

    private static final String REGISTER_URL ="http://192.168.43.196:8080/Server/Signup";
    private Map<String, String> params;


    public RegisterRequest(String email, String userid, String password, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_URL, listener, null);
        params= new HashMap<>();
        params.put("email",email);
        params.put("userid",userid);
        params.put("password",password);
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
