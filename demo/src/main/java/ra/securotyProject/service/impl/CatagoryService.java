package ra.securotyProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.securotyProject.exception.AlreadyExistException;
import ra.securotyProject.exception.NotfoundException;
import ra.securotyProject.model.domain.Catagory;
import ra.securotyProject.model.dto.request.CatagoryRequest;
import ra.securotyProject.model.dto.response.CatagoryResponse;
import ra.securotyProject.repository.ICatagoryRepository;
import ra.securotyProject.service.ICatagoryService;
import ra.securotyProject.service.Mapper.CatagoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CatagoryService implements ICatagoryService {
@Autowired
private ICatagoryRepository catagoryRepository;
@Autowired
private CatagoryMapper catagoryMapper;

    @Override
    public List<CatagoryResponse> finAll() {
        List<Catagory> catagoryList = catagoryRepository.findAll();
        List<CatagoryResponse> CatagoryResponse = catagoryList.stream()
                .map(catagory -> catagoryMapper.toResponse(catagory))
                .collect(Collectors.toList());
        return CatagoryResponse;
    }

    @Override
    public CatagoryResponse findById(Long id) {
        Optional<Catagory> catagoryOptional = catagoryRepository.findById(id);
        if(!catagoryOptional.isPresent()){
            return catagoryMapper.toResponse(catagoryOptional.get());
        }
        return null;
    }

    @Override
    public CatagoryResponse save(CatagoryRequest catagoryRequest) throws AlreadyExistException {
        if (catagoryRepository.existsByNameCatagory(catagoryRequest.getCatagoryName())){
            throw new AlreadyExistException("Catagory của bạn đã tồn tại !!!");
        }
        Catagory catagory = catagoryMapper.toEntity(catagoryRequest);
        return catagoryMapper.toResponse(catagoryRepository.save(catagory));
    }

    @Override
    public CatagoryResponse update(CatagoryRequest catagoryRequest, Long id) throws NotfoundException {
        Optional<Catagory> check = catagoryRepository.findById(id);
        if(!check.isPresent()) {
           throw new  NotfoundException("Không tìm thấy Catagory cần sửa !!!");
        }
        Catagory catagory = catagoryMapper.toEntity(catagoryRequest);
        catagory.setId(id);
        catagoryRepository.save(catagory);
        return catagoryMapper.toResponse(catagory);
    }

    @Override
    public CatagoryResponse delete(Long id) throws NotfoundException {
        Optional<Catagory> catagory = catagoryRepository.findById(id);
        if (catagory.isPresent()) {
            catagoryRepository.delete(catagory.get());
          return  catagoryMapper.toResponse(catagory.get());
        }
        throw new NotfoundException("Không tìm thấy Catagory cần xóa !!!");
    }
}
