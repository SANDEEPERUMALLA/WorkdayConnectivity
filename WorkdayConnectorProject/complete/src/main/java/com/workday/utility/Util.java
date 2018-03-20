package com.workday.utility;

import com.workday.config.PropertyConfig;
import com.workday.security.Password;
import com.workday.security.Security;
import com.workday.security.UsernameToken;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.SoapEnvelope;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.lang.reflect.Field;
import java.util.List;

@Component
public class Util {

    @Autowired
    PropertyConfig propertyConfig;


    public void addSecurityHeaderToMessage(WebServiceMessage message){

        SaajSoapMessage soapMessage = (SaajSoapMessage) message;
        SoapEnvelope envelope = soapMessage.getEnvelope();
        JAXBContext context = null;

        try {
            context = JAXBContext.newInstance(Security.class);
            Security security = new Security();
            UsernameToken usernameToken = new UsernameToken();

            usernameToken.setUserName(propertyConfig.getAuth().getUsername());
            usernameToken.setPassword(new Password(propertyConfig.getAuth().getPassword(),propertyConfig.getAuth().getPasswordType()));

            security.setUsernameToken(usernameToken);

            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(security, envelope.getHeader().getResult());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void getMetaData(Class clazz, List<String> metaData) throws ClassNotFoundException {


        Field fields[] = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (ClassUtils.isPrimitiveOrWrapper(field.getType()) || field.getType().equals(String.class)) {
                metaData.add(field.getName());
            } else {

                metaData.add(field.getName());
                getMetaData(field.getType(), metaData);
            }
        }

    }
}
