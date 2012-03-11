package com.fruiz.tankmon

import grails.plugins.springsecurity.Secured
import grails.validation.ValidationException
import groovy.xml.dom.DOMCategory
import javax.xml.soap.*
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMIN'])
class CenteronController {
    
    def springSecurityService

    def index() {
    }

    def actualiza() {
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

            def empresa = Empresa.findByNombre('CENTERON')
            use(DOMCategory) {
                log.debug("Registros ${records.'*'.size()}")
                def tanques = doc.getElementsByTagName('Table');
                log.debug("Tanques ${tanques.size()}")
                tanques.each { remoto ->
                    log.debug("AssetUID: ${remoto.AssetUID.text()}")
                    log.debug("Capacidad: ${remoto.Capacity.text()}")
                    def tanque = Tanque.findByAsignacion(remoto.AssetUID.text())
                    if (!tanque) {
                        tanque = new Tanque(
                            asignacion: remoto.AssetUID.text()
                            , serie: remoto.AssetUID.text()
                            , nombre: remoto.AssetName.text()
                            , fechaFabricacion: new Date()
                            , producto : remoto.Product.text()
                            , unidades : remoto.UnitDesc.text()
                            , capacidadLleno : new BigDecimal(remoto.PercentFull.text())
                            , capacidadVacio : new BigDecimal(remoto.PercentEmpty.text())
                            , capacidad : (remoto.Capacity.text())?new BigDecimal(remoto.Capacity.text()): new BigDecimal('0')
                            , tipo : remoto.Product.text()
                            , empresa : empresa
                        ).save()

                        def xtanque = new XTanque(tanque.properties)
                        xtanque.id = null
                        xtanque.empresaId = empresa.id
                        xtanque.save()
                    }
                }
            }

            connection.close();
        } catch(Exception e) {
            log.error("Error con el webservice", e)
        }

        flash.message = 'Termino de actualizar tanques'
        redirect action:'index'
    }
}
