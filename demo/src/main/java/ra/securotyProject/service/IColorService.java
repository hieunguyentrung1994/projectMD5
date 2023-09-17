package ra.securotyProject.service;

import org.springframework.stereotype.Repository;
import ra.securotyProject.model.dto.request.ColorRequest;
import ra.securotyProject.model.dto.response.ColorResponse;


public interface IColorService extends IGenericService<ColorResponse, ColorRequest,Long> {
}
