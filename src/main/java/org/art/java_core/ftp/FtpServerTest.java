package org.art.java_core.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.*;
import org.apache.ftpserver.listener.Listener;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.PropertiesUserManager;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A runner for embedded FTP server.
 */
public class FtpServerTest {

    public static void main(String[] args) throws FtpException {

        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(8085);
        Listener listener = factory.createListener();
        serverFactory.addListener("default", listener);

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();

        BaseUser user = new BaseUser();
        user.setName("test");
        user.setPassword("test");
        user.setHomeDirectory("/home/ftp/test");
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new WritePermission());
        UserManager userManager = userManagerFactory.createUserManager();
        userManager.save(user);
        serverFactory.setUserManager(userManager);
        Map<String, Ftplet> ftplets = new HashMap<>();

        ftplets.put("testFtplet", new Ftplet() {
            @Override
            public void init(FtpletContext ftpletContext) throws FtpException {
                System.out.println("*** Init");
            }

            @Override
            public void destroy() {
                System.out.println("*** Destroy");
            }

            @Override
            public FtpletResult beforeCommand(FtpSession ftpSession, FtpRequest ftpRequest) throws FtpException, IOException {
                System.out.println("*** Before Command");

                return FtpletResult.DEFAULT;
            }

            @Override
            public FtpletResult afterCommand(FtpSession ftpSession, FtpRequest ftpRequest, FtpReply ftpReply) throws FtpException, IOException {
                System.out.println("*** After Command");

                return FtpletResult.NO_FTPLET;
            }

            @Override
            public FtpletResult onConnect(FtpSession ftpSession) throws FtpException, IOException {
                System.out.println("*** On Connect");
                return FtpletResult.DEFAULT;
            }

            @Override
            public FtpletResult onDisconnect(FtpSession ftpSession) throws FtpException, IOException {
                System.out.println("*** On Disconnect");

                return FtpletResult.DEFAULT;
            }
        });
        serverFactory.setFtplets(ftplets);
        FtpServer server = serverFactory.createServer();
        server.start();
    }
}
