package com.fruiz.tankmon

class XTanque implements Serializable {
    String asignacion
    String serie
    String nombre
    Date fechaFabricacion
    String producto
    String unidades = "Lts"
    BigDecimal capacidad = new BigDecimal('0')
    BigDecimal largo = new BigDecimal('0')
    BigDecimal ancho = new BigDecimal('0')
    BigDecimal profundo = new BigDecimal('0')
    BigDecimal diametro = new BigDecimal('0')
    BigDecimal capacidadLleno = new BigDecimal('0')
    BigDecimal capacidadVacio = new BigDecimal('0')
    String tipo
    Long tanqueId
    Long empresaId
    Date dateCreated

    static constraints = {
        asignacion blank: false, maxSize: 64
        serie blank: false, maxSize: 64
        producto blank: false, maxSize: 64
        unidades blank: false, maxSize: 64
        tipo nullable: true, maxSize: 64
    }

    static mapping = {
        table 'xtanques'
        empresaId column:'empresa_id'
        fechaFabricacion column:'fecha_fabricacion'
        capacidadLleno column:'capacidad_lleno'
        capacidadVacio column:'capacidad_vacio'
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