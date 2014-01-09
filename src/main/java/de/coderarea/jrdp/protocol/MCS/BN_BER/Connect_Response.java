
package de.coderarea.jrdp.protocol.MCS.BN_BER;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import org.bn.CoderFactory;
import org.bn.annotations.*;
import org.bn.coders.IASN1PreparedElement;
import org.bn.coders.IASN1PreparedElementData;
import org.bn.coders.TagClass;


@ASN1PreparedElement
@ASN1BoxedType(name = "Connect_Response")
public class Connect_Response implements IASN1PreparedElement {


    @ASN1PreparedElement
    @ASN1Sequence(name = "Connect-Response", isSet = false)
    public static class Connect_ResponseSequenceType implements IASN1PreparedElement {

        @ASN1Element(name = "result", isOptional = false, hasTag = false, hasDefaultValue = false)

        private Result result = null;

        @ASN1Integer(name = "")

        @ASN1Element(name = "calledConnectId", isOptional = false, hasTag = false, hasDefaultValue = false)

        private Long calledConnectId = null;


        @ASN1Element(name = "domainParameters", isOptional = false, hasTag = false, hasDefaultValue = false)

        private DomainParameters domainParameters = null;

        @ASN1OctetString(name = "")

        @ASN1Element(name = "userData", isOptional = false, hasTag = false, hasDefaultValue = false)

        private byte[] userData = null;


        public Result getResult() {
            return this.result;
        }


        public void setResult(Result value) {
            this.result = value;
        }


        public Long getCalledConnectId() {
            return this.calledConnectId;
        }


        public void setCalledConnectId(Long value) {
            this.calledConnectId = value;
        }


        public DomainParameters getDomainParameters() {
            return this.domainParameters;
        }


        public void setDomainParameters(DomainParameters value) {
            this.domainParameters = value;
        }


        public byte[] getUserData() {
            return this.userData;
        }


        public void setUserData(byte[] value) {
            this.userData = value;
        }


        public void initWithDefaults() {

        }

        public IASN1PreparedElementData getPreparedData() {
            return preparedData_Connect_ResponseSequenceType;
        }

        private static IASN1PreparedElementData preparedData_Connect_ResponseSequenceType = CoderFactory.getInstance().newPreparedElementData(Connect_ResponseSequenceType.class);

    }


    @ASN1Element(name = "Connect-Response", isOptional = false, hasTag = true, tag = 102,
            tagClass = TagClass.Application, hasDefaultValue = false)

    private Connect_ResponseSequenceType value;


    public Connect_Response() {
    }


    public void setValue(Connect_ResponseSequenceType value) {
        this.value = value;
    }


    public Connect_ResponseSequenceType getValue() {
        return this.value;
    }


    public void initWithDefaults() {
    }

    private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(Connect_Response.class);

    public IASN1PreparedElementData getPreparedData() {
        return preparedData;
    }


}
            