package top.grantdrew.service.bill;

import top.grantdrew.dao.BaseDao;
import top.grantdrew.dao.bill.BillDao;
import top.grantdrew.dao.bill.BillDaoImpl;
import top.grantdrew.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BillServiceImpl implements BillService{
    private BillDao billDao;
    public BillServiceImpl(){
        billDao = new BillDaoImpl();
    }
    @Override
    public boolean add(Bill bill) {
        // TODO Auto-generated method stub
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            if(billDao.add(connection,bill) > 0) {
                flag = true;
            }
            connection.commit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        //finally{
        //    //在service层进行connection连接的关闭
        //    try {
        //        BaseDao.release(connection, null, null);
        //    } catch (SQLException throwables) {
        //        throwables.printStackTrace();
        //    }
        //}
        return flag;
    }

    @Override
    public List<Bill> getBillList(Bill bill) {
        // TODO Auto-generated method stub
        Connection connection = null;
        List<Bill> billList = null;
        System.out.println("query productName ---- > " + bill.getProductName());
        System.out.println("query providerId ---- > " + bill.getProviderId());
        System.out.println("query isPayment ---- > " + bill.getIsPayment());

        try {
            connection = BaseDao.getConnection();
            billList = billDao.getBillList(connection, bill);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                BaseDao.release(connection, null, null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return billList;
    }

    @Override
    public boolean deleteBillById(String delId) {
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if(billDao.deleteBillById(connection, delId) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                BaseDao.release(connection, null, null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return flag;
    }

    @Override
    public Bill getBillById(String id) {
        // TODO Auto-generated method stub
        Bill bill = null;
        Connection connection = null;
        try{
            connection = BaseDao.getConnection();
            bill = billDao.getBillById(connection, id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            bill = null;
        }finally{
            try {
                BaseDao.release(connection, null, null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return bill;
    }

    @Override
    public boolean modify(Bill bill) {
        // TODO Auto-generated method stub
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if(billDao.modify(connection,bill) > 0)
                flag = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                BaseDao.release(connection, null, null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return flag;
    }
}
