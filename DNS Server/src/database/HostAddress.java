/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Santiago Fernandez - Fabian Olarte - Andres Vasquez
 */
@Entity
@Table(name = "HostAddress")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HostAddress.findAll", query = "SELECT h FROM HostAddress h"),
    @NamedQuery(name = "HostAddress.findByIp", query = "SELECT h FROM HostAddress h WHERE h.ip = :ip"),
    @NamedQuery(name = "HostAddress.findByDominio", query = "SELECT h FROM HostAddress h WHERE h.dominio = :dominio")})
public class HostAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ip")
    private String ip;
    @Basic(optional = false)
    @Column(name = "dominio")
    private String dominio;

    public HostAddress() {
    }

    public HostAddress(String ip) {
        this.ip = ip;
    }

    public HostAddress(String ip, String dominio) {
        this.ip = ip;
        this.dominio = dominio;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ip != null ? ip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HostAddress)) {
            return false;
        }
        HostAddress other = (HostAddress) object;
        if ((this.ip == null && other.ip != null) || (this.ip != null && !this.ip.equals(other.ip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.HostAddress[ ip=" + ip + " ]";
    }
    
}
