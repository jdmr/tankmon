package com.fruiz.tankmon

class Tanque implements Serializable {
    String asignacion
    String serie
    String nombre
    Date fechaFabricacion
    String tipo
    String producto
    String unidades = "Lts"
    BigDecimal capacidad = new BigDecimal('0')
    BigDecimal largo = new BigDecimal('0')
    BigDecimal ancho = new BigDecimal('0')
    BigDecimal profundo = new BigDecimal('0')
    BigDecimal diametro = new BigDecimal('0')
    BigDecimal capacidadLleno = new BigDecimal('0')
    BigDecimal capacidadVacio = new BigDecimal('0')
    BigDecimal latitud = new BigDecimal('0')
    BigDecimal longitud = new BigDecimal('0')

    static belongsTo = [empresa: Empresa]

    static constraints = {
        asignacion blank: false, maxSize: 64
        serie blank: false, maxSize: 64, unique: true
        tipo nullable: true, maxSize: 64
        producto blank: false, maxSize: 64
        unidades blank: false, maxSize: 64
    }

    static mapping = {
        table 'tanques'
        asignacion index:'tanque_filtro_idx'
        serie index:'tanque_filtro_idx'
        nombre index:'tanque_filtro_idx'
        tipo index:'tanque_filtro_idx'
        producto index:'tanque_filtro_idx'
        latitud scale:16
        longitud scale:16
    }

    static namedQueries = {
        buscaPorEmpresa { filtro ->
            empresa {
                idEq filtro.id
            }
        }

        buscaPorFiltro { filtro ->
            filtro = "%$filtro%"
            or {
                ilike 'asignacion', filtro
                ilike 'serie', filtro
                ilike 'nombre', filtro
                ilike 'tipo', filtro
                ilike 'producto', filtro
            }
        }
    }

    String toString() {
        return asignacion
    }
}
