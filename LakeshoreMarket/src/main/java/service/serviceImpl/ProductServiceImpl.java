package main.java.service.serviceImpl;

import java.util.List;

import main.java.DAO.ProductDAO;
import main.java.DAO.daoImpl.ProductDAOImpl;
import main.java.model.bean.ProductBean;
import main.java.service.service.ProductService;
import main.java.util.ElementUtil;


/**
 * This service layer class corresponds to the activity layer presented in class. 
 * @author lbo
 *
 */
public class ProductServiceImpl implements ProductService {
	
	private ProductDAO productDAO = new ProductDAOImpl();

	@Override
	public ProductBean get(int productId) {
		return ElementUtil.buildProductBean(productDAO.find(productId));
	}

	@Override
	public void update(ProductBean productBean) {
		productDAO.update(ElementUtil.buildProduct(productBean));
	}
	
	@Override
	public void add(ProductBean productBean){
		productDAO.add(ElementUtil.buildProduct(productBean));
	}

	@Override
	public void delete(int productID) {
		productDAO.delete(productID);
	}

	@Override
	public List<ProductBean> findByPartner(int partnerId) {
		return ElementUtil.buildProductBeanList(productDAO.findByPartner(partnerId));
	}

}
