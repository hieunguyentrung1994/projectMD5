package ra.securotyProject.service.Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.securotyProject.model.domain.Catagory;
import ra.securotyProject.model.domain.Color;
import ra.securotyProject.model.domain.Products;
import ra.securotyProject.model.domain.Trademark;
import ra.securotyProject.model.dto.request.FormProductRequestSeller;
import ra.securotyProject.model.dto.response.ProductReponseSeller;
import ra.securotyProject.repository.ICatagoryRepository;
import ra.securotyProject.repository.IColorRepository;
import ra.securotyProject.repository.ITrademarkRepository;
import ra.securotyProject.service.IGenericMapper;
import ra.securotyProject.service.ITrandemarkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductMapper implements IGenericMapper<Products, FormProductRequestSeller, ProductReponseSeller> {
    @Autowired
    private ITrademarkRepository trandemarkService;
    @Autowired
    private ICatagoryRepository catagoryRepository;
    @Autowired
    private IColorRepository colorRepository;
    @Override
    public Products toEntity(FormProductRequestSeller form) {
        Optional<Trademark> trademark = trandemarkService.findById(form.getTrademark());
        List<Catagory> listCatagory = catagoryRepository.findAllById(form.getCatagoris());
        List<Color> colorList = colorRepository.findAllById(form.getColors());
        return Products.builder().name(form.getName()).price(form.getPrice()).title(form.getTitle())
                .content(form.getContent()).img(form.getImg()).trademark(trademark.get())
                .seller(form.getSeller())
                .seller(form.getSeller()).catagoris(listCatagory).colors(colorList).build();
    }

    @Override
    public ProductReponseSeller toResponse(Products products) {
        return ProductReponseSeller.builder().id(products.getId()).name(products.getName()).price(products.getPrice())
                .title(products.getTitle()).content(products.getContent()).img(products.getImg())
                .trademark(products.getTrademark()).seller(products.getSeller())
                .seller(products.getSeller()).catagoris(products.getCatagoris()).colors(products.getColors()).build();
    }
}
