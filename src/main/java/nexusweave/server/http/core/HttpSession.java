package nexusweave.server.http.core;

public interface HttpSession {
    void putSession(String key, Object value);
    Object getSession(String key);
}
