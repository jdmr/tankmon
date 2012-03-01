package com.fruiz.tankmon

class Tanque implements Serializable {
    String asignacion
    String serie
    Date fechaFabricacion
    BigDecimal capacidad = new BigDecimal('0')
    BigDecimal largo = new BigDecimal('0')
    BigDecimal ancho = new BigDecimal('0')
    BigDecimal profundo = new BigDecimal('0')
    BigDecimal diametro = new BigDecimal('0')
    String producto
    BigDecimal capacidadLleno = new BigDecimal('0')
    BigDecimal capacidadVacio = new BigDecimal('0')
    String tipo

    static belongsTo = [empresa: Empresa]

    static constraints = {
        asignacion blank: false, maxSize: 64
        serie blank: false, maxSize: 64, unique: true
        producto blank: false, maxSize: 64
        tipo nullable: true, maxSize: 64
    }

    static mapping = {
        table 'tanques'
        fechaFabricacion column:'fecha_fabricacion'
        capacidadLleno column:'capacidad_lleno'
        capacidadVacio column:'capacidad_vacio'
    }

    String toString() {
        return asignacion
    }
}
