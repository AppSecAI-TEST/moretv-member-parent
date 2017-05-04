package cn.whaley.moretv.member.notify.dto.cp;

import cn.whaley.moretv.member.model.cp.CpOrder;
import cn.whaley.moretv.member.model.cp.CpOrderItem;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Bob Jiang on 2017/4/13.
 */
public class CpOrderDto extends CpOrder {

    private static final long serialVersionUID = 2519895237588719451L;

    private List<CpOrderItem> cpOrderItems = Lists.newArrayList();

    public List<CpOrderItem> getCpOrderItems() {
        return cpOrderItems;
    }

    public void setCpOrderItems(List<CpOrderItem> cpOrderItems) {
        this.cpOrderItems = cpOrderItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" {id=").append(id);
        sb.append(", cpOrderCode=").append(getCpOrderCode());
        sb.append(", businessOrderCode=").append(getBusinessOrderCode());
        sb.append(", cpAccount=").append(getCpAccount());
        sb.append(", cpOrderStatus=").append(getCpOrderStatus());
        sb.append(", createTime=").append(getCreateTime());
        sb.append(", updateTime=").append(getUpdateTime());
        sb.append(", cpOrderItems=").append(cpOrderItems);
        sb.append("}");
        return sb.toString();
    }
}
