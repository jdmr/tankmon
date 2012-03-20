package com.fruiz.tankmon

class Empresa implements Serializable {

    String nombre
    String razonSocial
    String rfc
    String sitioWeb
    Integer maximoUsuarios = 5

    static hasMany = [usuarios: Usuario, imagenes: Imagen]

    static constraints = {
        nombre(blank: false, unique: true)
        razonSocial(blank: false, maxSize: 128)
        rfc(blank: false, size: 12..13)
        sitioWeb(nullable: true, maxSize: 128)
    }

    static mapping = {
        table 'empresas'
        nombre index:'empresa_nombre_idx'
        razonSocial column:'razon_social'
        sitioWeb column:'sitio_web'
        maximoUsuarios column:'maximo_usuarios'
    }

    String toString() {
        return nombre
    }

}
