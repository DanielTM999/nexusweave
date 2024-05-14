package nexusweave.server.http;

import java.util.HashMap;
import java.util.Map;

import nexusweave.server.http.core.HttpSession;


public class HttpServerSession implements HttpSession{

    private Map<String, Object> session;

    public HttpServerSession(){
        session = new HashMap<>();
    }

    @Override
    public void putSession(String key, Object value) {
        session.put(key, value);
    }

    @Override
    public Object getSession(String key) {
        if(session.containsKey(key)){
            return session.get(key);
        }

        return null;
    }
    
}
