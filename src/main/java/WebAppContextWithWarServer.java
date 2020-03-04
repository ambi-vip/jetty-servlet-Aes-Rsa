import Filter.FileFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import servlet.DownLoad;
import servlet.FileLiset;
import servlet.Upload;

import javax.servlet.DispatcherType;
import javax.servlet.annotation.WebFilter;
import java.io.File;
import java.net.InetSocketAddress;
import java.util.EnumSet;

/**
 * @author Ambi
 * @version 1.0
 * @date 2020/2/29 19:21
 */
public class WebAppContextWithWarServer {
    private static String host = "127.0.0.1";
    private static String port = "8080";
    public static void main(String[] args) throws Exception {
        InetSocketAddress address = new InetSocketAddress(host,Integer.parseInt(port));
        Server server = new Server(address);

        ServletContextHandler handler = new ServletContextHandler();

        handler.addServlet(Upload.class,"/Upload");
        handler.addServlet(FileLiset.class,"/FileList");
        handler.addServlet(DownLoad.class,"/DownLoad");
        handler.addFilter(FileFilter.class,"/*", EnumSet.of(DispatcherType.REQUEST));
        server.setHandler(handler);

        server.start();
        server.join();
    }
}
