package com.fruiz.tankmon

import groovy.xml.dom.DOMCategory
import javax.xml.soap.*
import uk.co.desirableobjects.sendgrid.*

class CenteronDaemonService  {

    def sendGridService

    def process() {
        log.debug("Ejecutando demonio de sincronizacion con tanques de Centeron")
        try {
            SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection connection = soapConnFactory.createConnection();
            
            //Next, create the actual message
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPMessage message = messageFactory.createMessage();
            
            //Create objects for the message parts            
            MimeHeaders headers = message.getMimeHeaders();
            headers.addHeader("SOAPAction", "http://webviewctl.centeron.net/GetTankAssets");
            SOAPPart soapPart = message.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            SOAPBody body = envelope.getBody();

            //Populate the body
            //Create the main element and namespace
            SOAPElement bodyElement =
                    body.addChildElement(envelope.createName("GetTankAssets",
                    "tns",
                    "http://webviewctl.centeron.net/"));
            //Add content
            bodyElement.addChildElement(envelope.createName("Username","tns","http://webviewctl.centeron.net/")).addTextNode("petroleum");
            bodyElement.addChildElement(envelope.createName("Password","tns","http://webviewctl.centeron.net/")).addTextNode("petroleum");

            //Save the message
            message.saveChanges();

            //Set the destination
            String destination =
                    "https://www.centeron.net/GetXMLData/webviewctl.asmx";
            //Send the message
            SOAPMessage reply = connection.call(message, destination);

            def doc = reply.getSOAPBody().extractContentAsDocument()
            def records = doc.documentElement

            def empresa = Empresa.findByNombre('SIN ASIGNAR')
            use(DOMCategory) {
                def tanques = doc.getElementsByTagName('Table');
                tanques.each { remoto ->
                    def tanque = Tanque.findByAsignacion(remoto.AssetUID.text())
                    if (!tanque) {
                        tanque = new Tanque(
                            asignacion: remoto.AssetUID.text()
                            , serie: remoto.AssetUID.text()
                            , nombre: remoto.AssetName.text()
                            , fechaFabricacion: new Date()
                            , tipo : remoto.Product.text()
                            , producto : remoto.Product.text()
                            , unidades : remoto.UnitDesc.text()
                            , capacidadLleno : new BigDecimal(remoto.PercentFull.text())
                            , capacidadVacio : new BigDecimal(remoto.PercentEmpty.text())
                            , capacidad : (remoto.Capacity.text())?new BigDecimal(remoto.Capacity.text()): new BigDecimal('0')
                            , empresa : empresa
                        ).save(flush:true)

                        def xtanque = new XTanque(tanque.properties)
                        xtanque.id = null
                        xtanque.capacidadLleno = new BigDecimal(remoto.PercentFull.text())
                        xtanque.capacidadVacio = new BigDecimal(remoto.PercentEmpty.text())
                        xtanque.capacidad = (remoto.Capacity.text())?new BigDecimal(remoto.Capacity.text()): new BigDecimal('0')
                        xtanque.tanqueId = tanque.id
                        xtanque.empresaId = empresa.id
                        xtanque.save()
                    } else {
                        tanque.capacidadLleno = new BigDecimal(remoto.PercentFull.text())
                        tanque.capacidadVacio = new BigDecimal(remoto.PercentEmpty.text())
                        tanque.capacidad = (remoto.Capacity.text())?new BigDecimal(remoto.Capacity.text()): new BigDecimal('0')
                        tanque.save(flush:true)

                        def xtanque = new XTanque(tanque.properties)
                        xtanque.id = null
                        xtanque.capacidadLleno = new BigDecimal(remoto.PercentFull.text())
                        xtanque.capacidadVacio = new BigDecimal(remoto.PercentEmpty.text())
                        xtanque.capacidad = (remoto.Capacity.text())?new BigDecimal(remoto.Capacity.text()): new BigDecimal(tanque.capacidad)
                        xtanque.tanqueId = tanque.id
                        xtanque.empresaId = empresa.id
                        xtanque.save()

                        if (tanque.capacidadLleno < tanque.alerta) {
                            if (tanque.empresa.nombre != 'SIN ASIGNAR') {
                                enviaCorreo(tanque, 'ALERTA')
                            }
                        } else if (tanque.capacidadLleno < tanque.precaucion) {
                            if (tanque.empresa.nombre != 'SIN ASIGNAR') {
                                enviaCorreo(tanque, 'PRECAUCIÓN')
                            }
                        }
                    }
                }
            }

            connection.close();
        } catch(Exception e) {
            log.error("Error con el webservice", e)
        }

        log.debug('Termino sincronizacion')
    }

    def enviaCorreo(tanque, tipo) {
        def usuarios = tanque.empresa.usuarios
        /*
        sendGridService.sendMail {
            from 'jdmendoza@um.edu.mx'
            for(usuario in usuarios) {
                to "${usuario.correo}"
            }
            subject "${tanque.nombre}(${tanque.asignacion}) en ${tipo}"
            body "El tanque\n Nombre: ${tanque.nombre}\n Asignación: ${tanque.asignacion}\n Serie: ${tanque.serie}\n Tiene está solo al ${tanque.capacidadLleno * 100}% cuando el porcentaje válido es ${tanque.precaucion * 100}%.\n\nEquipo FRuiz"
        }
        */

        def email = new SendGridEmailBuilder()
        email.from('David Mendoza', 'jdmendoza@um.edu.mx')
        for(usuario in usuarios) {
            email.to("${usuario.nombre} ${usuario.apellido}", "${usuario.correo}")
        }
        email.replyTo('jdmendoza@um.edu.mx')
        email.subject("$tipo ${tanque.nombre}(${tanque.asignacion})")
        email.withText("El tanque\n Nombre: ${tanque.nombre}\n Asignación: ${tanque.asignacion}\n Serie: ${tanque.serie}\n Está solo al ${tanque.capacidadLleno * 100}% cuando el porcentaje mínimo es ${tanque.precaucion * 100}%.\n\nFRuiz e Hijos")
        email.withHtml("<html><head><title>Tanque ${tanque.nombre}</title></head><body><p>El tanque<br/>Nombre: ${tanque.nombre}<br/>Asignación: ${tanque.asignacion}<br/>Serie: ${tanque.serie}<br/>Está solo al ${tanque.capacidadLleno * 100}% cuando el porcentaje mínimo es ${tanque.precaucion * 100}.</p><br/><br/><h3>FRuiz e Hijos</h3></body></html>")
        sendGridService.send(email.build())
    }
}
