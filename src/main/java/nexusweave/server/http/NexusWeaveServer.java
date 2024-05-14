package nexusweave.server.http;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import nexusweave.server.http.core.HttpAction;
import nexusweave.server.http.core.HttpSession;
import nexusweave.server.http.core.NexusWeave;


public class NexusWeaveServer implements NexusWeave{

    private ServerSocketChannel listener;
    private InetSocketAddress address;
    private String host;
    private int port;
    private Map<String, HttpSession> cashServer;

    public NexusWeaveServer(){
        startProps(null, null);
    }

    public NexusWeaveServer(int port){
        startProps(null, port);
    }

    public NexusWeaveServer(String host, int port){
        startProps(host, port);
    }

    @Override
    public void start(HttpAction action){
        address = new InetSocketAddress(this.host, this.port);
        try {
            listener = ServerSocketChannel.open();
            listener.bind(address);
            String hostServer = listener.getLocalAddress().toString();
            printServerRunning();
            while (true) {
                Thread mainServerAction = new Thread(() -> {
                    try {
                        SocketChannel cliente = listener.accept();
                        InetSocketAddress remoteAddress = (InetSocketAddress) cliente.getRemoteAddress();
                        String ip = remoteAddress.getHostName();
                        HttpSession session = putCashIfNotExists(ip);
                        String reqBase = readRequestBase(cliente);
                        Map<String, String> headers = formatHeaders(reqBase);
                        String body = reqBase.substring(reqBase.indexOf("\r\n\r\n"), reqBase.length());
                        String htttpMethod = getMethod(reqBase);
                        String route = getRoute(reqBase);
                        action.actionCalback(new HttpServerRequest(headers, body, htttpMethod, route, session), new HttpServerResponse(cliente, hostServer));
                        cliente.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
                mainServerAction.start();
            }
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    private void startProps(String host, Integer port){
        cashServer = new HashMap<>();
        if(host == null){
            this.host = "127.0.0.1";
        }
        if(port == null){
            this.port = 8080;
        }
    }

    private String readRequestBase(SocketChannel cliente) throws Exception{
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = cliente.read(buffer);
        if (bytesRead == -1) {
            cliente.close();
            return null;
        }
        buffer.flip();
        String requestData = StandardCharsets.UTF_8.decode(buffer).toString();
        buffer.clear();
        return requestData;
    }

    private Map<String, String> formatHeaders(String requestData){
        requestData.substring(0, requestData.indexOf("\r\n\r\n"));
        Map<String, String> headers = new HashMap<>(); 
        int tail = 0;
        int current = tail + 1;
        String[] headersSplip = requestData.split(":");
        
        if(headersSplip.length % 2 == 0){
            for (int i = 0; i < (headersSplip.length / 2); i++) {
                headers.put(headersSplip[tail], headersSplip[current]);
                tail++;
                current++;
            }
        }


        return headers;
    }

    private String getMethod(String reqBase){
        String httpMethodName = reqBase.substring(0, reqBase.indexOf(" "));
        return httpMethodName;
    }

    private String getRoute(String reqBase){
        String route = reqBase.substring(reqBase.indexOf("/"), reqBase.length());
        route = route.substring(0, route.indexOf(" "));
        return route;
    }

    private HttpSession putCashIfNotExists(String ip){
        HttpSession session = new HttpServerSession();
        synchronized(cashServer){
            if(!cashServer.containsKey(ip)){
                cashServer.put(ip, session);
            }else{
                session = cashServer.get(ip);
            }
        }

        return session;
    }

    private void printServerRunning(){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String dataString = date.format(formatter);
        System.out.println("Server running on [ "+host+":"+port+" ] : "+dataString);
    }
}
