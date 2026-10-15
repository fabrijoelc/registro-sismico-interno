package pe.isil.sismos.model;
import jakarta.persistence.*;
@Entity
@Table(name="ubicacion", uniqueConstraints=@UniqueConstraint(name="uk_ubicacion_codigo", columnNames="codigo"))
public class Ubicacion {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false, length=20)
    private String codigo;
    @Column(nullable=false, length=80)
    private String departamento;
    @Column(nullable=false, length=80)
    private String provincia;
    @Column(nullable=false, length=80)
    private String distrito;
    @Column(nullable=false)
    private boolean activo=true;
    public Ubicacion() { }
    public Ubicacion(String codigo,String departamento,String provincia,String distrito) {
        this.codigo=codigo; this.departamento=departamento; this.provincia=provincia; this.distrito=distrito;
    }
    public long getId() { return id; }
    public void setId(long valor) { id=valor; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String valor) { codigo=valor; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String valor) { departamento=valor; }
    public String getProvincia() { return provincia; }
    public void setProvincia(String valor) { provincia=valor; }
    public String getDistrito() { return distrito; }
    public void setDistrito(String valor) { distrito=valor; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean valor) { activo=valor; }
    public String getNombreCompleto() { return departamento+" / "+provincia+" / "+distrito; }
}
