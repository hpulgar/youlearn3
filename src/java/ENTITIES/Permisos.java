/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITIES;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Felipe
 */
@Entity
@Table(name = "permisos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Permisos.findAll", query = "SELECT p FROM Permisos p"),
    @NamedQuery(name = "Permisos.findByIdMenu", query = "SELECT p FROM Permisos p WHERE p.idMenu = :idMenu"),
    @NamedQuery(name = "Permisos.findByLogin", query = "SELECT p FROM Permisos p WHERE p.login = :login"),
    @NamedQuery(name = "Permisos.findByAdministrarCursos", query = "SELECT p FROM Permisos p WHERE p.administrarCursos = :administrarCursos"),
    @NamedQuery(name = "Permisos.findByVerCursos", query = "SELECT p FROM Permisos p WHERE p.verCursos = :verCursos"),
    @NamedQuery(name = "Permisos.findByPanelAdministrativo", query = "SELECT p FROM Permisos p WHERE p.panelAdministrativo = :panelAdministrativo"),
    @NamedQuery(name = "Permisos.findByPanelModerador", query = "SELECT p FROM Permisos p WHERE p.panelModerador = :panelModerador"),
    @NamedQuery(name = "Permisos.findByForoGeneral", query = "SELECT p FROM Permisos p WHERE p.foroGeneral = :foroGeneral"),
    @NamedQuery(name = "Permisos.findBySuscripcion", query = "SELECT p FROM Permisos p WHERE p.suscripcion = :suscripcion"),
    @NamedQuery(name = "Permisos.findByMensajeria", query = "SELECT p FROM Permisos p WHERE p.mensajeria = :mensajeria"),
    @NamedQuery(name = "Permisos.findByAccesoTablero", query = "SELECT p FROM Permisos p WHERE p.accesoTablero = :accesoTablero")})
public class Permisos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_menu")
    private Integer idMenu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Log_in")
    private boolean login;
    @Basic(optional = false)
    @NotNull
    @Column(name = "administrar_cursos")
    private boolean administrarCursos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ver_Cursos")
    private boolean verCursos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Panel_Administrativo")
    private boolean panelAdministrativo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Panel_Moderador")
    private boolean panelModerador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Foro_General")
    private boolean foroGeneral;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Suscripcion")
    private boolean suscripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Mensajeria")
    private boolean mensajeria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "acceso_tablero")
    private boolean accesoTablero;
    @JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil")
    @ManyToOne(optional = false)
    private Perfil idPerfil;

    public Permisos() {
    }

    public Permisos(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public Permisos(Integer idMenu, boolean login, boolean administrarCursos, boolean verCursos, boolean panelAdministrativo, boolean panelModerador, boolean foroGeneral, boolean suscripcion, boolean mensajeria, boolean accesoTablero) {
        this.idMenu = idMenu;
        this.login = login;
        this.administrarCursos = administrarCursos;
        this.verCursos = verCursos;
        this.panelAdministrativo = panelAdministrativo;
        this.panelModerador = panelModerador;
        this.foroGeneral = foroGeneral;
        this.suscripcion = suscripcion;
        this.mensajeria = mensajeria;
        this.accesoTablero = accesoTablero;
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public boolean getLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean getAdministrarCursos() {
        return administrarCursos;
    }

    public void setAdministrarCursos(boolean administrarCursos) {
        this.administrarCursos = administrarCursos;
    }

    public boolean getVerCursos() {
        return verCursos;
    }

    public void setVerCursos(boolean verCursos) {
        this.verCursos = verCursos;
    }

    public boolean getPanelAdministrativo() {
        return panelAdministrativo;
    }

    public void setPanelAdministrativo(boolean panelAdministrativo) {
        this.panelAdministrativo = panelAdministrativo;
    }

    public boolean getPanelModerador() {
        return panelModerador;
    }

    public void setPanelModerador(boolean panelModerador) {
        this.panelModerador = panelModerador;
    }

    public boolean getForoGeneral() {
        return foroGeneral;
    }

    public void setForoGeneral(boolean foroGeneral) {
        this.foroGeneral = foroGeneral;
    }

    public boolean getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(boolean suscripcion) {
        this.suscripcion = suscripcion;
    }

    public boolean getMensajeria() {
        return mensajeria;
    }

    public void setMensajeria(boolean mensajeria) {
        this.mensajeria = mensajeria;
    }

    public boolean getAccesoTablero() {
        return accesoTablero;
    }

    public void setAccesoTablero(boolean accesoTablero) {
        this.accesoTablero = accesoTablero;
    }

    public Perfil getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Perfil idPerfil) {
        this.idPerfil = idPerfil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permisos)) {
            return false;
        }
        Permisos other = (Permisos) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Permisos[ idMenu=" + idMenu + " ]";
    }
    
}
