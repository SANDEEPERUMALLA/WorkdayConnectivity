package com.workday;


import com.workday.client.WorkdayClient;
import com.workday.security.Security;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import workday_hr.wsdl.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    void getMetaData(Class clazz,List<String> metaData) throws ClassNotFoundException {


       Field fields[] =  clazz.getDeclaredFields();

       for(Field field : fields){
           if(ClassUtils.isPrimitiveOrWrapper(field.getType()) || field.getType().equals(String.class)){
               metaData.add(field.getName());
           }
           else {

               metaData.add(field.getName());
               getMetaData(field.getType(), metaData);
           }
       }

    }

    @Bean
    CommandLineRunner lookup(WorkdayClient workdayClient) {
        return args -> {

            GetWorkersResponseRootType response = workdayClient.getWorker("3aa5550b7fe348b98d7b5741afc65534");
            WorkersResponseDataType responseData = response.getResponseData();

            System.out.println(responseData);
            System.out.println(responseData.getWorker().get(0).getWorkerData().getPersonalData().getBirthDate());
            System.out.println(responseData);


            List<String> metaDataList = new ArrayList<>();

            getMetaData(PersonDataType.class, metaDataList);

            System.out.println(metaDataList);

        };
    }

}
