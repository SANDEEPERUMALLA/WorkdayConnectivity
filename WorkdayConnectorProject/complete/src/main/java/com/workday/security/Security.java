package com.workday.security;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Security", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
@XmlAccessorType(XmlAccessType.FIELD)
public class Security {

    @XmlElement(name = "UsernameToken", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
    private UsernameToken usernameToken;

    public UsernameToken getUsernameToken() {
        return usernameToken;
    }

    public void setUsernameToken(UsernameToken usernameToken) {
        this.usernameToken = usernameToken;
    }
}
