/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import database.HostAddress;
import database.HostAddressJpaController;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Santiago Fernandez - Fabian Olarte - Andres Vasquez
 */
public class ServerDns {
    
    private int puerto;
    private DatagramSocket serverSocket;

    public ServerDns() throws SocketException {
        this.puerto = 53;//Puerto local
        this.serverSocket = new DatagramSocket(this.puerto);//Socket local
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }
    
    public void server() throws SocketException{
        try {
            byte[] receiveData = new byte[65507];
            byte[] realData;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                this.serverSocket.receive(receivePacket);
                realData = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
                MessageDns mensajeRecibido = this.particionMensaje(realData);
                byte[] dataEnvio = this.creacionRespuesta(mensajeRecibido);
                DatagramPacket packet = new DatagramPacket(dataEnvio, dataEnvio.length, receivePacket.getAddress(), receivePacket.getPort());
                this.serverSocket.send(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String conversorDominio(byte[] data){
        String dominio = new String();
        for(int i = 1; i < data.length; i++){
            if(data[i] == 0x00){
                
            }else if(data[i] > 0x09){
                dominio += (char)data[i];
            }else{
                dominio += '.';
            }
        }
        return dominio;
    }
    
    public MessageDns particionMensaje(byte[] data){//Construccion de mensaje (con header y question)
        MessageDns mensaje = new MessageDns();
        HeaderDns header = new HeaderDns();
        QuestionDns question = new QuestionDns();
        int index = 0;
        int qIndex = 0;
        int pIndex = 0;
        boolean q = false;
        short qd = 0;
        short an = 0;
        short ns = 0;
        short ar = 0;
        for(int i = 0; i < data.length; i++){
            
            if(index > 1){
                index = 0;
            }
            
            //Construccion del header
            
            if(i >= 0 && i < 2){
                header.getId()[index] = data[i];
            }else if(i >= 2 && i < 4){
                header.getFlags()[index] = data[i];
            }else if(i >= 4 && i < 6){
                qd += (short)data[i];
            }else if(i >= 6 && i < 8){
                an += (short)data[i];
            }else if(i >= 8 && i < 10){
                ns += (short)data[i];
            }else if(i >= 10 && i < 12){
                ar += (short)data[i];
            }
            
            header.setQdcount(qd);
            header.setAncount(an);
            header.setNscount(ns);
            header.setArcount(ar);
            
            //Fin de construccion del header
            
            //Construccion del question
            
            if(i == 12){
                q = true;
            }
            
            if(q){
                List<Byte> arr = new ArrayList<Byte>();
                while(data[i] != 0x00){
                    arr.add(data[i]);
                    i += 1;
                }
                arr.add(data[i]);//Se annade el 0x00 del final del qname
                byte[] name = new byte[arr.size()];
                int nIndex = 0;
                for(Byte b : arr){
                    name[nIndex] = b.byteValue();
                    nIndex += 1;
                }
                question.setQname(name);
                pIndex += 1;
                q = false;
                i += 1;
            }
            
            if(pIndex >= 1){
                if(pIndex >= 1 && pIndex < 3){
                    question.getQtype()[index] = data[i];
                }else if(pIndex >= 3 && pIndex < 5){
                    question.getQclass()[index] = data[i];
                }
                pIndex += 1;
            }
            
            //Fin de construccion del header
            
            index += 1;
        }
        mensaje.setHeader(header);
        mensaje.setQuestion(question);
        return mensaje;
    }
    
    public byte[] creacionRespuesta(MessageDns mensajeRecibido) throws UnknownHostException, SocketException{
        byte[] datos;
        MessageDns respuesta = new MessageDns();
        HeaderDns header = new HeaderDns();
        List<AnswerDns> answer = new ArrayList<AnswerDns>();//Lista de posibles respuestas
        header.setId(mensajeRecibido.getHeader().getId());//Se le agrega el mismo id del mensaje recibido
        String dominio = this.conversorDominio(mensajeRecibido.getQuestion().getQname());//Convierte los bytes a string
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DNS_ServerPU");
        HostAddressJpaController controlador = new HostAddressJpaController(emf);
        List<HostAddress> dirs = new ArrayList<HostAddress>();//Direcciones ip con base en el masterfile
        List<HostAddress> tDirs = controlador.findHostAddressEntities();
        for(HostAddress d : tDirs){//Consulta en base de datos MySQL
            if(d.getDominio().equals(dominio)){
                dirs.add(d);//Encontramos las direcciones cuyo dominio sea el pedido
            }
        }
        if(dirs.isEmpty()){//No encuentra resultado en base de datos (NO AUTORITATIVA)
            datos = this.consultaExterna(mensajeRecibido);
        }else{//Encuentra resultado en base de datos (AUTORITATIVA)
            //Construccion del header
            
            header.setFlags(mensajeRecibido.getHeader().getFlags());
            header.getFlags()[0] += 0x084;//Sumamos 132 para que QR->1 y AA->1
            short s1 = (short) dirs.size();//ANCOUNT
            header.setQdcount(mensajeRecibido.getHeader().getQdcount());
            header.setAncount(s1);
            short cero = 0;
            header.setNscount(cero);
            header.setArcount(cero);
            
            //Fin de construccion del header
            
            //Construccion de las answers
            
            for(HostAddress h : dirs){
                AnswerDns res = new AnswerDns();
                byte[] apName = new byte[2];//NAME
                apName[0] = (byte) 0xc0;
                apName[1] = (byte) 0x0c;
                res.setName(apName);
                byte[] tipo = new byte[2];//TYPE: A
                tipo[0] = 0x00;
                tipo[1] = 0x01;
                res.setType(tipo);
                byte[] clase = new byte[2];//CLASS: IN
                clase[0] = 0x00;
                clase[1] = 0x01;
                res.setClassA(clase);
                Random rand = new Random();//TTL
                int random = rand.nextInt(254);
                res.setTtl(random);
                byte[] dataL = new byte[2];//RDLENGHT: 4
                dataL[0] = 0x00;
                dataL[1] = 0x04;
                res.setRdlenght(dataL);
                String ip = h.getIp();//RDATA(IP)
                String[] n = ip.split("\\.");
                byte[] data = new byte[n.length];
                char[] c = new char[n.length];
                int nIndex = 0;
                for(int i = 0; i < n.length; i++){
                    c[i] = (char)Integer.parseInt(n[i]);
                }
                for(int i = 0; i < n.length; i++){
                    data[i] = (byte)c[i];
                }
                res.setRdata(data);
                answer.add(res);//Annadimos la respuesta a la lista de answers
            }
            
            //Fin de construccion de las answers
            respuesta.setHeader(header);
            respuesta.setQuestion(mensajeRecibido.getQuestion());
            respuesta.setAnswer(answer);
            datos = this.dataEnvio(respuesta);
        }
        return datos;
    }
    
    public byte[] dataEnvio(MessageDns respuesta){
        HeaderDns header = respuesta.getHeader();
        QuestionDns question = respuesta.getQuestion();
        List<AnswerDns> answer = respuesta.getAnswer();
        List<Byte> dat = new ArrayList<Byte>();
        Byte cero = 0x00;
        dat.add(header.getId()[0]);//H::ID
        dat.add(header.getId()[1]);
        dat.add(header.getFlags()[0]);//H::FLAGS
        dat.add(header.getFlags()[1]);
        dat.add(cero);
        dat.add((byte)header.getQdcount());//H::QDCOUNT
        dat.add(cero);
        dat.add((byte)header.getAncount());//H::ANCOUNT
        dat.add(cero);
        dat.add((byte)header.getNscount());//H::NSCOUNT
        dat.add(cero);
        dat.add((byte)header.getArcount());//H::ARCOUNT
        for(byte b1 : question.getQname()){//Q::QNAME
            dat.add(b1);
        }
        dat.add(question.getQtype()[0]);//Q::QTYPE
        dat.add(question.getQtype()[1]);
        dat.add(question.getQclass()[0]);//Q::QCLASS
        dat.add(question.getQclass()[1]);
        for(AnswerDns ans : answer){
            for(byte b2 : ans.getName()){
                dat.add(b2);
            }
            dat.add(ans.getType()[0]);//A::TYPE
            dat.add(ans.getType()[1]);
            dat.add(ans.getClassA()[0]);//A::CLASS
            dat.add(ans.getClassA()[1]);
            dat.add(cero);
            dat.add(cero);
            dat.add(cero);
            dat.add((byte)ans.getTtl());//A:TTL
            dat.add(ans.getRdlenght()[0]);//A::RDLENGHT
            dat.add(ans.getRdlenght()[1]);
            for(byte b3 : ans.getRdata()){//A:RDATA
                dat.add(b3);
            }
        }
        byte[] data = new byte[dat.size()];
        int index = 0;
        for(Byte bi : dat){
            data[index] = bi;
            index += 1;
        }
        return data;
    }
    
    public byte[] dataEnvioExt(MessageDns respuesta){
        HeaderDns header = respuesta.getHeader();
        QuestionDns question = respuesta.getQuestion();
        List<AnswerDns> answer = respuesta.getAnswer();
        List<Byte> dat = new ArrayList<Byte>();
        Byte cero = 0x00;
        dat.add(header.getId()[0]);//H::ID
        dat.add(header.getId()[1]);
        dat.add(header.getFlags()[0]);//H::FLAGS
        dat.add(header.getFlags()[1]);
        dat.add(cero);
        dat.add((byte)header.getQdcount());//H::QDCOUNT
        dat.add(cero);
        dat.add((byte)header.getAncount());//H::ANCOUNT
        dat.add(cero);
        dat.add((byte)header.getNscount());//H::NSCOUNT
        dat.add(cero);
        dat.add((byte)header.getArcount());//H::ARCOUNT
        for(byte b1 : question.getQname()){//Q::QNAME
            dat.add(b1);
        }
        dat.add(question.getQtype()[0]);//Q::QTYPE
        dat.add(question.getQtype()[1]);
        dat.add(question.getQclass()[0]);//Q::QCLASS
        dat.add(question.getQclass()[1]);
        byte[] data = new byte[dat.size()];
        int index = 0;
        for(Byte bi : dat){
            data[index] = bi;
            index += 1;
        }
        return data;
    }
    
    public byte[] consultaExterna(MessageDns mensajeRecibido) throws UnknownHostException, SocketException{
        MessageDns consulta = new MessageDns();
        InetAddress adGoogle = InetAddress.getByName("8.8.8.8");//Servidor DNS Google
        byte[] receiveData = new byte[65507];
        byte[] realData = new byte[65507];
        try {
            byte[] dataEnvio = this.dataEnvioExt(mensajeRecibido);
            DatagramPacket packet = new DatagramPacket(dataEnvio, dataEnvio.length, adGoogle, this.puerto);
            this.serverSocket.send(packet);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            this.serverSocket.receive(receivePacket);
            realData = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return realData;
    }
    
}
