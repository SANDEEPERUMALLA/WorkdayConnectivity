package com.workday;


import com.workday.client.WorkdayClient;
import com.workday.model.MetaNode;
import com.workday.security.Security;
import com.workday.utility.Util;
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

    @Bean
    CommandLineRunner lookup(WorkdayClient workdayClient, Util util) {
        return args -> {
            GetWorkersResponseRootType response = workdayClient.getWorker("3aa5550b7fe348b98d7b5741afc65534");
            WorkersResponseDataType responseData = response.getResponseData();

            System.out.println(responseData);
            System.out.println("Worker Birth Date: " + responseData.getWorker().get(0).getWorkerData().getPersonalData().getBirthDate());
            System.out.println(responseData);

            List<String> metaDataList = new ArrayList<>();

            MetaNode personMetaNode = new MetaNode(PersonDataType.class.getSimpleName(), PersonDataType.class.getSimpleName());
            util.getMetaData(PersonDataType.class, personMetaNode);

            System.out.println("PersonData MetaData" + personMetaNode);


            OrganizationType organization = workdayClient.getOrganisation("034e2640b4bd45f8b003dd349d6e4e6f");

            System.out.println("Organization Name: " + organization.getOrganizationData().getOrganizationName());

            GetOrganizationsResponseType organisations = workdayClient.getOrganisations();

            for (OrganizationWWSType organizationWWSType : organisations.getResponseData().getOrganization()) {
                System.out.println(organizationWWSType.getOrganizationData().getName());
            }

            MetaNode rootNode = new MetaNode(OrganizationWWSType.class.getSimpleName(), OrganizationWWSType.class.getSimpleName());
            util.getMetaData(OrganizationWWSType.class, rootNode);

            System.out.println("Organization MetaData");
            System.out.println(rootNode);


        };
    }

}
