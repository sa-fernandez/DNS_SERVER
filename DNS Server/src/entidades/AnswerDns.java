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
public class AnswerDns {
    
    private byte[] name;
    private byte[] type;
    private byte[] classA;
    private int ttl;
    private byte[] rdlenght;
    private byte[] rdata;

    public AnswerDns() {
        this.type = new byte[2];
        this.classA = new byte[2];
        this.rdlenght = new byte[2];
    }

    public AnswerDns(byte[] name, byte[] type, byte[] classA, int ttl, byte[] rdlenght, byte[] rdata) {
        this.name = name;
        this.type = type;
        this.classA = classA;
        this.ttl = ttl;
        this.rdlenght = rdlenght;
        this.rdata = rdata;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public byte[] getType() {
        return type;
    }

    public void setType(byte[] type) {
        this.type = type;
    }

    public byte[] getClassA() {
        return classA;
    }

    public void setClassA(byte[] classA) {
        this.classA = classA;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public byte[] getRdlenght() {
        return rdlenght;
    }

    public void setRdlenght(byte[] rdlenght) {
        this.rdlenght = rdlenght;
    }

    public byte[] getRdata() {
        return rdata;
    }

    public void setRdata(byte[] rdata) {
        this.rdata = rdata;
    }
    
}
