package com.fruiz.tankmon

class Usuario implements Serializable {

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired = false
	boolean accountLocked = false
	boolean passwordExpired = false
    String nombre
    String apellido
    String puesto
    String telefono

    static belongsTo = [empresa: Empresa]

	static constraints = {
		username blank: false, unique: true, email: true
		password blank: false
        nombre blank: false, maxSize: 64
        apellido blank: false, maxSize: 64
        puesto nullable: true, maxSize: 128
        telefono nullable: true, maxSize: 32
	}

	static mapping = {
        table 'usuarios'
		password column: '`password`'
	}

	Set<Rol> getAuthorities() {
		UsuarioRol.findAllByUsuario(this).collect { it.rol } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}

    String toString() {
        return "$apellido, $nombre"
    }
}
