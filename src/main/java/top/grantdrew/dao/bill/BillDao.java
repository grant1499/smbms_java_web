package top.grantdrew.dao.bill;


import top.grantdrew.pojo.Bill;

import java.sql.Connection;
import java.util.List;

public interface BillDao {
    public int add(Connection connection, Bill bill)throws Exception;

    public List<Bill> getBillList(Connection connection, Bill bill)throws Exception;

    public int deleteBillById(Connection connection, String delId)throws Exception;

    public Bill getBillById(Connection connection, String id)throws Exception;

    public int modify(Connection connection, Bill bill)throws Exception;

    public int getBillCountByProviderId(Connection connection, String providerId)throws Exception;
}
