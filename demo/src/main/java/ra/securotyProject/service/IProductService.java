package ra.securotyProject.service;

import ra.securotyProject.exception.AlreadyExistException;
import ra.securotyProject.model.dto.request.FormProductRequestSeller;
import ra.securotyProject.model.dto.response.ProductReponseSeller;

import java.util.List;

public interface IProductService extends IGenericService<ProductReponseSeller, FormProductRequestSeller,Long>{
    ProductReponseSeller saveADD(FormProductRequestSeller formProductRequestSeller,Long id) throws AlreadyExistException;
    List<ProductReponseSeller> ShowAllPruct(Long Id);
}
