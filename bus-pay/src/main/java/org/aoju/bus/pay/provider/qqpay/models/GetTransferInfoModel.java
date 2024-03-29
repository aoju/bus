package org.aoju.bus.pay.provider.qqpay.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.aoju.bus.pay.magic.Property;

/**
 * 查询企业付款
 */
@Data
@Builder
@AllArgsConstructor
public class GetTransferInfoModel extends Property {

    private String mch_id;
    private String nonce_str;
    private String sign;
    private String out_trade_no;
    private String transaction_id;

}
