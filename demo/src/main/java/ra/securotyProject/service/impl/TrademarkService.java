package ra.securotyProject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.securotyProject.exception.AlreadyExistException;
import ra.securotyProject.exception.NotfoundException;
import ra.securotyProject.model.domain.Catagory;
import ra.securotyProject.model.domain.Trademark;
import ra.securotyProject.model.dto.request.TrademarkRequest;
import ra.securotyProject.model.dto.response.TrademarkResponse;
import ra.securotyProject.repository.ITrademarkRepository;
import ra.securotyProject.service.ITrandemarkService;
import ra.securotyProject.service.Mapper.TrademarkMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrademarkService implements ITrandemarkService {
    @Autowired
    private ITrademarkRepository trademarkRepository;
    @Autowired
    private TrademarkMapper trademarkMapper;
    @Override
    public List<TrademarkResponse> finAll() {
        List<Trademark> trademarkList = trademarkRepository.findAll();
        List<TrademarkResponse> trademarkResponses = trademarkList.stream()
                .map(trademark -> trademarkMapper.toResponse(trademark))
                .collect(Collectors.toList());
        return trademarkResponses;
    }

    @Override
    public TrademarkResponse findById(Long id) {
        Optional<Trademark> trademarkOptional = trademarkRepository.findById(id);
        if(!trademarkOptional.isPresent()){
            return trademarkMapper.toResponse(trademarkOptional.get());
        }
        return null;
    }

    @Override
    public TrademarkResponse save(TrademarkRequest trademarkRequest) throws AlreadyExistException {
        if (trademarkRepository.existsByNameTrademark(trademarkRequest.getNameTrademark())) {
            throw new AlreadyExistException("Thương hiệu này của bạn đã tồn tại !!!");
        }
        Trademark trademark = trademarkMapper.toEntity(trademarkRequest);
        return trademarkMapper.toResponse(trademarkRepository.save(trademark));
    }

    @Override
    public TrademarkResponse update(TrademarkRequest trademarkRequest, Long id) throws NotfoundException {
        Optional<Trademark> check = trademarkRepository.findById(id);
        if(!check.isPresent()) {
            throw new  NotfoundException("Không tìm thấy thương hiệu cần sửa !!!");
        }
        Trademark trademark = trademarkMapper.toEntity(trademarkRequest);
        trademark.setId(id);
        return trademarkMapper.toResponse(trademark);
    }

    @Override
    public TrademarkResponse delete(Long id) throws NotfoundException {
        Optional<Trademark> trademark = trademarkRepository.findById(id);
        if (!trademark.isPresent()) {
            trademarkRepository.delete(trademark.get());
            return trademarkMapper.toResponse(trademark.get());
        }
        throw new NotfoundException("Không tìm thấy Thương hiệu cần xóa !!!");
    }
}
