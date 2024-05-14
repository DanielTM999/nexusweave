package nexusweave.server.http.core;

import java.util.Map;

public interface HttpRequest {
    String getHtttpMethod();
    String getRoute();
    Map<String, String> getHeaders();
    String getHeader(String key);
    String getBody();
    HttpSession getSession();
}
