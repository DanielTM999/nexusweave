package nexusweave.server.http.core;

public interface NexusWeave {
    void start(HttpAction action);
    void setPort(int port);
    void setHost(String host);
}
