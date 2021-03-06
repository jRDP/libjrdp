
package de.coderarea.jrdp.protocol.MCS.BN_BER;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import org.bn.CoderFactory;
import org.bn.annotations.ASN1Enum;
import org.bn.annotations.ASN1EnumItem;
import org.bn.annotations.ASN1PreparedElement;
import org.bn.coders.IASN1PreparedElement;
import org.bn.coders.IASN1PreparedElementData;


@ASN1PreparedElement
@ASN1Enum(
        name = "Result"
)
public class Result implements IASN1PreparedElement {
    public enum EnumType {

        @ASN1EnumItem(name = "rt-successful", hasTag = true, tag = 0)
        rt_successful,
        @ASN1EnumItem(name = "rt-domain-merging", hasTag = true, tag = 1)
        rt_domain_merging,
        @ASN1EnumItem(name = "rt-domain-not-hierarchical", hasTag = true, tag = 2)
        rt_domain_not_hierarchical,
        @ASN1EnumItem(name = "rt-no-such-channel", hasTag = true, tag = 3)
        rt_no_such_channel,
        @ASN1EnumItem(name = "rt-no-such-domain", hasTag = true, tag = 4)
        rt_no_such_domain,
        @ASN1EnumItem(name = "rt-no-such-user", hasTag = true, tag = 5)
        rt_no_such_user,
        @ASN1EnumItem(name = "rt-not-admitted", hasTag = true, tag = 6)
        rt_not_admitted,
        @ASN1EnumItem(name = "rt-other-user-id", hasTag = true, tag = 7)
        rt_other_user_id,
        @ASN1EnumItem(name = "rt-parameters-unacceptable", hasTag = true, tag = 8)
        rt_parameters_unacceptable,
        @ASN1EnumItem(name = "rt-token-not-available", hasTag = true, tag = 9)
        rt_token_not_available,
        @ASN1EnumItem(name = "rt-token-not-possessed", hasTag = true, tag = 10)
        rt_token_not_possessed,
        @ASN1EnumItem(name = "rt-too-many-channels", hasTag = true, tag = 11)
        rt_too_many_channels,
        @ASN1EnumItem(name = "rt-too-many-tokens", hasTag = true, tag = 12)
        rt_too_many_tokens,
        @ASN1EnumItem(name = "rt-too-many-users", hasTag = true, tag = 13)
        rt_too_many_users,
        @ASN1EnumItem(name = "rt-unspecified-failure", hasTag = true, tag = 14)
        rt_unspecified_failure,
        @ASN1EnumItem(name = "rt-user-rejected", hasTag = true, tag = 15)
        rt_user_rejected,
    }

    private EnumType value;
    private Integer integerForm;

    public EnumType getValue() {
        return this.value;
    }

    public void setValue(EnumType value) {
        this.value = value;
    }

    public Integer getIntegerForm() {
        return integerForm;
    }

    public void setIntegerForm(Integer value) {
        integerForm = value;
    }

    public void initWithDefaults() {
    }

    private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(Result.class);

    public IASN1PreparedElementData getPreparedData() {
        return preparedData;
    }


}
            