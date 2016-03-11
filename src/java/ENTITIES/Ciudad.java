/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITIES;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "ciudad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c"),
    @NamedQuery(name = "Ciudad.findByIdCiudad", query = "SELECT c FROM Ciudad c WHERE c.idCiudad = :idCiudad"),
    @NamedQuery(name = "Ciudad.findByNombreCiudad", query = "SELECT c FROM Ciudad c WHERE c.nombreCiudad = :nombreCiudad")})
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ciudad")
    private Integer idCiudad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombre_ciudad")
    private String nombreCiudad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCiudad")
    private List<Persona> personaList;
    @JoinColumn(name = "id_pais", referencedColumnName = "id_pais")
    @ManyToOne(optional = false)
    private Pais idPais;

    public Ciudad() {
    }

    public Ciudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Ciudad(Integer idCiudad, String nombreCiudad) {
        this.idCiudad = idCiudad;
        this.nombreCiudad = nombreCiudad;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    @XmlTransient
    public List<Persona> getPersonaList() {
        return personaList;
    }

    public void setPersonaList(List<Persona> personaList) {
        this.personaList = personaList;
    }

    public Pais getIdPais() {
        return idPais;
    }

    public void setIdPais(Pais idPais) {
        this.idPais = idPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCiudad != null ? idCiudad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.idCiudad == null && other.idCiudad != null) || (this.idCiudad != null && !this.idCiudad.equals(other.idCiudad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Ciudad[ idCiudad=" + idCiudad + " ]";
    }
    
}
