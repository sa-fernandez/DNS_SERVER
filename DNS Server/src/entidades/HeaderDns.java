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
public class HeaderDns {
    
    private byte [] id;
    private byte [] flags;
    private short qdcount;
    private short ancount;
    private short nscount;
    private short arcount;

    public HeaderDns() {
        this.id = new byte[2];
        this.flags = new byte[2];
    }

    public HeaderDns(byte[] id, byte[] flags, short qdcount, short ancount, short nscount, short arcount) {
        this.id = id;
        this.flags = flags;
        this.qdcount = qdcount;
        this.ancount = ancount;
        this.nscount = nscount;
        this.arcount = arcount;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public byte[] getFlags() {
        return flags;
    }

    public void setFlags(byte[] flags) {
        this.flags = flags;
    }

    public short getQdcount() {
        return qdcount;
    }

    public void setQdcount(short qdcount) {
        this.qdcount = qdcount;
    }

    public short getAncount() {
        return ancount;
    }

    public void setAncount(short ancount) {
        this.ancount = ancount;
    }

    public short getNscount() {
        return nscount;
    }

    public void setNscount(short nscount) {
        this.nscount = nscount;
    }

    public short getArcount() {
        return arcount;
    }

    public void setArcount(short arcount) {
        this.arcount = arcount;
    }
    
}
