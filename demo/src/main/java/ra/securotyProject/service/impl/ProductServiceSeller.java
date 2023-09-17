package ra.securotyProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ra.securotyProject.exception.AlreadyExistException;
import ra.securotyProject.exception.NotfoundException;
import ra.securotyProject.model.domain.Catagory;
import ra.securotyProject.model.domain.ImageProduct;
import ra.securotyProject.model.domain.Products;
import ra.securotyProject.model.domain.Users;
import ra.securotyProject.model.dto.request.FormProductRequestSeller;
import ra.securotyProject.model.dto.response.CatagoryResponse;
import ra.securotyProject.model.dto.response.ProductReponseSeller;
import ra.securotyProject.repository.IProductRepository;
import ra.securotyProject.repository.IUserRepository;
import ra.securotyProject.service.IGenericService;
import ra.securotyProject.service.IProductService;
import ra.securotyProject.service.Mapper.ProductMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceSeller implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private IUserRepository userRepository;
    @Override
    public List<ProductReponseSeller> finAll() {
        List<Products> productsList = productRepository.findAll();
        List<ProductReponseSeller> productReponseSellers = productsList.stream()
                .map(product -> productMapper.toResponse(product))
                .collect(Collectors.toList());
        return productReponseSellers;
    }

    @Override
    public ProductReponseSeller findById(Long id) {
        Optional<Products> productsOptional= productRepository.findById(id);
        if(!productsOptional.isPresent()){
            return productMapper.toResponse(productsOptional.get());
        }
        return null;
    }

    @Override
    public ProductReponseSeller save(FormProductRequestSeller formProductRequestSeller) throws AlreadyExistException {
        return null;
    }

    @Override
    public ProductReponseSeller saveADD(FormProductRequestSeller formProductRequestSeller,Long idUser) throws AlreadyExistException {
        Optional<Users> user = userRepository.findById(idUser);
        Users seller = user.get();
        if (productRepository.existsByNameAndId(formProductRequestSeller.getName(),idUser)){
            throw new AlreadyExistException("Tên sản phẩm này của bạn đã tồn tại trong shop của bạn !!!");
        }
        Products product = productMapper.toEntity(formProductRequestSeller);
        List<String> listUrl = new ArrayList<>();
        for (MultipartFile m : formProductRequestSeller.getFile()) {
            listUrl.add(uploadService.uploadFile(m));
        }
        // lấy listUrl từ vị vào cái ảnh đầu tiên vào ảnh img làm ảnh đại diện
        product.setImg(listUrl.get(0));
        List<ImageProduct> images = new ArrayList<>();
        for (String url : listUrl) {
            images.add(ImageProduct.builder().image(url).product(product).build());
        }
        // set list img vào product
        product.setImages(images);
        product.setSeller(seller);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductReponseSeller update(FormProductRequestSeller formProductRequestSeller, Long id) throws NotfoundException {
        Optional<Products> check = productRepository.findById(id);
        if(!check.isPresent()) {
            throw new  NotfoundException("Không tìm thấy sản phẩm cần sửa !!!");
        }

        Products product = productMapper.toEntity(formProductRequestSeller);
        List<String> listUrl = new ArrayList<>();
        for (MultipartFile m : formProductRequestSeller.getFile()) {
            listUrl.add(uploadService.uploadFile(m));
        }
        // lấy listUrl từ vị vào cái ảnh đầu tiên vào ảnh img làm ảnh đại diện
        product.setImg(listUrl.get(0));
        List<ImageProduct> images = new ArrayList<>();
        for (String url : listUrl) {
            images.add(ImageProduct.builder().image(url).product(product).build());
        }
        // set list img vào product
        product.setImages(images);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductReponseSeller delete(Long id) throws NotfoundException {
        Optional<Products> products = productRepository.findById(id);
        if (products.isPresent()) {
            productRepository.delete(products.get());
            return  productMapper.toResponse(products.get());
        }
        throw new NotfoundException("Không tìm thấy sản phẩm cần xóa !!!");
    }

    @Override
    public List<ProductReponseSeller> ShowAllPruct(Long id) {
        List<Products> list = productRepository.findAllBySellerId(id);
        List<ProductReponseSeller> reponseList= new ArrayList<>();
        for (Products p : list) {
            reponseList.add(productMapper.toResponse(p));
        }
        return reponseList;
    }
}
