/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dns.server;

import entidades.ServerDns;
import java.net.SocketException;

/**
 *
 * @author Santiago Fernandez - Fabian Olarte - Andres Vasquez
 */
public class DNSServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException{
        // TODO code application logic here
        ServerDns server = new ServerDns();
        server.server();
    }
}
