package com.workday.client;

import com.workday.config.PropertyConfig;
import com.workday.model.WorkerIDType;
import com.workday.security.Password;
import com.workday.security.Security;
import com.workday.security.UsernameToken;
import com.workday.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapEnvelope;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import workday_hr.wsdl.*;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;
import java.io.IOException;


@Component
public class WorkdayClient extends WebServiceGatewaySupport {


    @Autowired
    Util util;

    @Autowired
    PropertyConfig propertyConfig;

    @Autowired
    private Jaxb2Marshaller marshaller;

    @PostConstruct
    void init(){
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);
        setDefaultUri(propertyConfig.getConfig().getDefaultUri());

        System.out.println("Workday Client Initialized");
    }

    public GetWorkersResponseRootType getWorker(String workerId) {
        GetWorkersRequestType request = new GetWorkersRequestType();
        request.setVersion(propertyConfig.getConfig().getVersion());

        WorkerRequestReferencesType referencesType = new WorkerRequestReferencesType();
        referencesType.setSkipNonExistingInstances(false);

        WorkerObjectType workerObjectType = new WorkerObjectType();

        WorkerObjectIDType workerObjectIDType = new WorkerObjectIDType();
        workerObjectIDType.setType(WorkerIDType.WID.name());
        workerObjectIDType.setValue(workerId);
        workerObjectType.getID().add(workerObjectIDType);

        referencesType.getWorkerReference().add(workerObjectType);
        request.setRequestReferences(referencesType);

        JAXBElement<GetWorkersResponseRootType> element = (JAXBElement<GetWorkersResponseRootType>) getWebServiceTemplate().marshalSendAndReceive("https://wd2-impl-services1.workday.com/ccx/service/informatica_pt1/Human_Resources/v25.0/", request, new WebServiceMessageCallback() {
            public void doWithMessage(WebServiceMessage message) {

               util.addSecurityHeaderToMessage(message);

            }
        });

        return element.getValue();
    }


    public OrganizationType getOrganisation(String organisationId){

        IDType idType = new IDType();
        idType.setSystemID("");
        idType.setValue(organisationId);

        ExternalIntegrationIDReferenceDataType externalIntegrationIDReferenceDataType = new ExternalIntegrationIDReferenceDataType();
        externalIntegrationIDReferenceDataType.setID(idType);

        OrganizationReferenceType organizationReferenceType = new OrganizationReferenceType();
        organizationReferenceType.setIntegrationIDReference(externalIntegrationIDReferenceDataType);

        OrganizationGetType organizationGetType = new OrganizationGetType();
        organizationGetType.setOrganizationReference(organizationReferenceType);


        JAXBElement<OrganizationType> organizationType = (JAXBElement<OrganizationType>) getWebServiceTemplate().marshalSendAndReceive(getDefaultUri(), organizationGetType, new WebServiceMessageCallback() {
            @Override
            public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
                util.addSecurityHeaderToMessage(webServiceMessage);
            }
        });


        return organizationType.getValue();
    }


}
