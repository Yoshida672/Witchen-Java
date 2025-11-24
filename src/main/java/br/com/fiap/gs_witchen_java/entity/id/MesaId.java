package br.com.fiap.gs_witchen_java.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class MesaId implements Serializable {
    private Long id;
    private Long restauranteId;
    public boolean equals(Object o) { if (this == o) return true; if (o==null||getClass()!=o.getClass()) return false; MesaId m=(MesaId)o; return Objects.equals(id,m.id)&&Objects.equals(restauranteId,m.restauranteId); }
    public int hashCode(){ return Objects.hash(id, restauranteId); }
}