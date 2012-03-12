package com.fruiz.tankmon

class XTanque implements Serializable {
    String asignacion
    String serie
    String nombre
    Date fechaFabricacion
    String tipo
    String producto
    String unidades
    BigDecimal capacidad
    BigDecimal largo
    BigDecimal ancho
    BigDecimal profundo
    BigDecimal diametro
    BigDecimal capacidadLleno
    BigDecimal capacidadVacio
    BigDecimal latitud
    BigDecimal longitud
    BigDecimal precaucion
    BigDecimal alerta
    Long tanqueId
    Long empresaId
    Date dateCreated

    static constraints = {
        asignacion blank: false, maxSize: 64
        serie blank: false, maxSize: 64
        tipo nullable: true, maxSize: 64
        producto blank: false, maxSize: 64
        unidades blank: false, maxSize: 64
        latitud scale:16
        longitud scale:16
    }

    static mapping = {
        table 'xtanques'
        tanqueId index:'xtanque_tanque_id_idx'
        empresaId index:'xtanque_empresa_id_idx'
    }

    static namedQueries = {
        buscaPorEmpresa { filtro ->
            eq empresaId, filtro.id
        }
    }

    String toString() {
        return asignacion
    }
}
