package com.workday;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import com.workday.security.Password;
import com.workday.security.Security;
import com.workday.security.UsernameToken;
import org.apache.commons.lang3.ClassUtils;
import workday_hr.wsdl.GetWorkersRequestType;
import workday_hr.wsdl.PersonDataType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.xml.bind.JAXB.marshal;

public class Test {

//    public static Field[] getAllFields(Class klass) {
//        List<Field> fields = new ArrayList<Field>();
//        fields.addAll(Arrays.asList(klass.getDeclaredFields()));
//        if (klass.getSuperclass() != null) {
//            fields.addAll(Arrays.asList(getAllFields(klass.getSuperclass())));
//        }
//        return fields.toArray(new Field[] {});
//    }


    static void getMetaData(Class clazz, List<String> metaData) throws ClassNotFoundException {


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

    public static void main(String args[]) throws JAXBException, ClassNotFoundException {


        List<String> metaData = new ArrayList<>();

        getMetaData(PersonDataType.class, metaData);

        System.out.println(metaData);


        System.out.println(ClassUtils.isPrimitiveOrWrapper(String.class));
        JAXBContext context = JAXBContext.newInstance(Security.class);
        Security security = new Security();

        UsernameToken usernameToken = new UsernameToken();

        usernameToken.setUserName("ragarwal@informatica_pt1");
        usernameToken.setPassword(new Password("1qazXSW@", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText"));

        security.setUsernameToken(usernameToken);

        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
//            @Override
//            public String getPreferredPrefix(String s, String s1, boolean b) {
//                return "wsse";
//            }
//        });
        marshaller.marshal(security, System.out);
    }
}
