/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Santiago Fernandez - Fabian Olarte - Andres Vasquez
 */
public class QuestionDns {
    
    private byte[] qname;
    private byte[] qtype;
    private byte[] qclass;

    public QuestionDns() {
        this.qtype = new byte[2];
        this.qclass = new byte[2];
    }

    public QuestionDns(byte[] qname, byte[] qtype, byte[] qclass) {
        this.qname = qname;
        this.qtype = qtype;
        this.qclass = qclass;
    }

    public byte[] getQname() {
        return qname;
    }

    public void setQname(byte[] qname) {
        this.qname = qname;
    }

    public byte[] getQtype() {
        return qtype;
    }

    public void setQtype(byte[] qtype) {
        this.qtype = qtype;
    }

    public byte[] getQclass() {
        return qclass;
    }

    public void setQclass(byte[] qclass) {
        this.qclass = qclass;
    }
    
}
