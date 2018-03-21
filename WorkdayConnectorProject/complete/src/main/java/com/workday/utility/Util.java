package com.workday.utility;

import com.workday.config.PropertyConfig;
import com.workday.model.MetaNode;
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
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
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

     public void getMetaData(Class clazz, MetaNode node) throws ClassNotFoundException {

        Field fields[] = clazz.getDeclaredFields();

        for (Field field : fields) {

            MetaNode tNode = new MetaNode(field.getName(), field.getType().getSimpleName());
            if (ClassUtils.isPrimitiveOrWrapper(field.getType()) || field.getType().equals(String.class)) {

                node.getNodeList().add(tNode);
            } else if (field.getType().equals(List.class)) {
                Class clazzz = getElementType(field);
                MetaNode listTypeNode = new MetaNode(clazz.getSimpleName(), clazz.getSimpleName());
                node.nodeList.add(tNode);
                tNode.nodeList.add(listTypeNode);
                getMetaData(clazzz, listTypeNode);

            } else {
                node.nodeList.add(tNode);
                getMetaData(field.getType(), tNode);
            }
        }

    }



    private Class getElementType(Field field) {

        List<String> l = new ArrayList<>();

        ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
        Class<?> clazz = (Class<?>) stringListType.getActualTypeArguments()[0];

        return clazz;

    }
}
